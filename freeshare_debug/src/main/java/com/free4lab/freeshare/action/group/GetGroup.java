package com.free4lab.freeshare.action.group;

import java.util.*;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.UserType;
import com.free4lab.freeshare.action.BaseAction;


import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.VerifyUserManager;
import com.free4lab.freeshare.model.dao.*;
import com.free4lab.freeshare.model.data.Group;

import com.free4lab.utils.userinfo.UserInfo;
import com.free4lab.utils.userinfo.UserInfoUtil;
/*
 * @param kind   		                        查看该群组的成员MEM/申请人APPLY/邀请人INVITE；
 * @param type   			            用户类型用于跳转页面的前端的操作显示
 * @param identity              是创建的时候才用到。用于设置管理员的时候的显示。
 * @param creator     			群组创建者；
 * @param adminlist   			群组管理员；
 * @param userlist   		            群组普通用户；
 * @param inviteOutsiders       使用邮箱进行的邀请的人。
 * 
 * @param applyReason     		申请原因；

 *
 * @param inviteOutsiders       使用邮箱进行的邀请的人。
 * */
public class GetGroup extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GetGroup.class);
	private Integer groupId;
	private String name;
	private String groupInfo;
	private Integer type; // 用户类型-1 0 1 2

	private Integer kind;	
	private Integer memberNum;
	private String groupAvatar;
	private Map<String,String> applyReason;
	
	private List<UserInfo> userlist;// 已经属于该组的用户
	private List<UserInfo> creator;
	private List<UserInfo> adminlist;
	private List<String> inviteOutsiders;

	//要查看的用户类型，查看申请者、邀请者
	private static final Integer MEM = 0;
	private static final Integer APPLY = 1;
	private static final Integer INVITE = 2;
	private final static String DOWNLOAD_URL = "http://freedisk.free4lab.com/download?uuid=";
	/*应该针对成员页面和管理页面将该函数进行拆分。还有就是通过传参的方式减小函数规模。*/
	public String execute() {
		/* 1、初始化显示的list。*/
		initial();
		/* 2、通过groupId查找Group，并获得group的member的id的list。*/
		Group group = GroupManager.getFullGroup(groupId);
		
		/*如果这个组不存在的时候，就直接返回INPUT。*/
		if(group == null){
			return INPUT;
		}
		List<Integer> memberList = group.getMemberList();
		//创建群组的时候，创建者就应该是该组的默认成员，if判断memberList只是保护代码。
		if (memberList != null && memberList.size() >= 0) {
	       
			//TODO：以后type是传给我的。就不用自己赋值了。
			
			/*3、使用session中用户的id，检查是否为该组成员。*/
			
			/*3.1、如果不是该组的成员，在VerifyUserManager中进行查找。
			 * 类型type检查，如果是邀请的成员invite类型的，就将type设为-1；其他就默认是申请的成员类型的，就将type设为0；*/
			if (!GroupUserManager.checkMember(getSessionUID(), groupId)) {
				if(new VerifyUserDAO().checkType(groupId, getSessionEmail(),UserType.TYPE_INVITED) != null){
					type = UserType.TYPE_INVITED;
				}
				else{
					type = UserType.TYPE_APPLY;
				}
			}
			/*3.2、如果是该组的成员，进行是成员还是申请成员，或者邀请成员的页面的判断；*/
			else{
				if(kind == null){
					kind = MEM;
				}
				//3.2.1、访问成员页面
				if (kind == MEM) {
			
					new GetList().getMem(memberList);
				}
		
				/*3.2.2、访问申请成员页面*/
				else if(kind == APPLY){
					//new GetList().getApplyMem();
					new GetList().getApplyOrInviteMem(UserType.TYPE_APPLY);
				}
				/*3.2.3、访问邀请成员页面*/
				else if(kind == INVITE){
					//new GetList().getInviteMem();
					new GetList().getApplyOrInviteMem(UserType.TYPE_INVITED);
			    }
			}
			
		}
		//本组成员页面中个数显示。
		setMemberNum(creator.size()+adminlist.size() + userlist.size()+inviteOutsiders.size());
		setName(group.getGroupInfo().get("name"));
		setGroupInfo(group.getGroupInfo().get("desc"));
		
		if(group.getGroupInfo().toString().contains("avatar")){
			setGroupAvatar(DOWNLOAD_URL+group.getGroupInfo().get("avatar"));	
		}		
		return SUCCESS;
	}
	private void initial(){
		applyReason = new HashMap<String,String>();
		inviteOutsiders = new ArrayList<String>();
		userlist = new ArrayList<UserInfo>();
		adminlist = new ArrayList<UserInfo>();
		creator = new ArrayList<UserInfo>();
	}
	private class GetList{
		private GroupPermission userType = new GroupPermissionDAO().findPermission(getSessionUID(), groupId);
		List<Integer> futures = new LinkedList<Integer>();
		public  void getMem(List<Integer> memberList){
			
			if(userType != null){
	
				type = userType.getType();
			}
			else{
				type = UserType.TYPE_MEM;
			}
	        /*一次将所有的用户的信息请求回来，分别加入到普通用户的列表userlist，创建者的列表creator，和管理员的列表adminlist*/
			List<UserInfo> ul = UserInfoUtil.getUserInfoByUid(getSessionToken(), memberList);
			for(UserInfo u : ul){
				//如果email长度过长，进行截断。
				if(u.getEmail().length() >20){
					u.setEmail(u.getEmail().substring(0, 20));
				}
				userType = new GroupPermissionDAO().findPermission(Integer.parseInt(u.getUserId()), groupId);
				if(userType == null){
					userlist.add(u);
				}
				else if(userType.getType().equals(UserType.TYPE_CREATOR)){
					creator.add(u);
				}
				else if(userType.getType().equals(UserType.TYPE_ADMIN)){
					adminlist.add(u);
				}
			}
		}
		
		
		public void getApplyOrInviteMem(Integer type){
			
			List<VerifyUser> vList = VerifyUserManager.findVerifyUserByGroupId(groupId,type);
			logger.info("vList size:"+vList.size());
			futures.clear();
			inviteOutsiders.clear();
			for(VerifyUser vu:vList){
				/*申请的人的id都应该存在的。*/					
				if(vu.getUserId() != null){
					futures.add(vu.getUserId());	
					
				}
				else{
					inviteOutsiders.add(vu.getEmail());
				}
				if(type.equals(UserType.TYPE_APPLY)){
					applyReason.put(vu.getEmail(), vu.getReason());
				}
				
			}
			logger.info("futures size:"+futures.size()+" inviteOutsiders size:"+inviteOutsiders.size());
			if(futures.size()>0){
				
				userlist = UserInfoUtil.getUserInfoByUid(getSessionToken(), futures);
			}
		}
		
	}
	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(String groupInfo) {
		this.groupInfo = groupInfo;
	}


	public Map<String,String> getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(Map<String,String> applyReason) {
		this.applyReason = applyReason;
	}
	

	public Integer getMemberNum() {
		return memberNum;
	}

	public List<UserInfo> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<UserInfo> userlist) {
		this.userlist = userlist;
	}

	public List<UserInfo> getAdminlist() {
		return adminlist;
	}

	public void setAdminlist(List<UserInfo> adminlist) {
		this.adminlist = adminlist;
	}

	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}

	public String getGroupAvatar() {
		return groupAvatar;
	}

	public void setGroupAvatar(String groupAvatar) {
		this.groupAvatar = groupAvatar;
	}
	public List<String> getInviteOutsiders() {
		return inviteOutsiders;
	}

	public void setInviteOutsiders(List<String> inviteOutsiders) {
		this.inviteOutsiders = inviteOutsiders;
	}
	public List<UserInfo> getCreator() {
		return creator;
	}
	public void setCreator(List<UserInfo> creator) {
		this.creator = creator;
	}

}
