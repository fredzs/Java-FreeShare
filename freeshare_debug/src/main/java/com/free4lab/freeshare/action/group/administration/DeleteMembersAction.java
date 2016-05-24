package com.free4lab.freeshare.action.group.administration;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.SendMail;

import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.VerifyUserManager;
import com.free4lab.freeshare.model.dao.GroupModel;
import com.free4lab.freeshare.model.dao.GroupPermission;
import com.free4lab.freeshare.model.dao.VerifyUser;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.util.MessageUtil;

import com.free4lab.utils.userinfo.UserInfo;
import com.free4lab.utils.userinfo.UserInfoUtil;


/**
 * @author 
 * 删除成员，暂时实现单删除
 */
public class DeleteMembersAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DeleteMembersAction.class);
	private final static String url = "http://freeshare.free4lab.com/groups";
	private Integer groupId;
	private String uid;
	private List<Integer> members = new ArrayList<Integer>();
	
	public String execute(){
		//TODO 待抽离到manager，直接传值uid，groupId
		//TODO 后端也要判断是否是管理员
	
		List<String> mailList = deleteMember();
		Group model = GroupManager.getSimpGroup(groupId);
		String content = "用户你好，你由于某些原因被移出了 \""+ model.getGroupInfo().get("name")+"\"群组";
		try {			
			SendMail.sendMail(mailList, "你已经被移除兴趣组", content);
			//发送站内信
		    if(members.size() > 0){ 
				MessageUtil.sendMessage(content, url,members);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return SUCCESS;
	}
	public String quitGroup(){

		deleteMember();
		Group model = GroupManager.getSimpGroup(groupId);
		List<UserInfo> uiList = GroupUserManager.getAdmin(groupId,getSessionToken());
		List<String> mailList  = new ArrayList<String>();
		for(UserInfo userInfo:uiList){
			mailList.add(userInfo.getEmail());
		}
		
		try {			
			SendMail.sendMail(mailList, getSessionUserName()+"退出了兴趣组", "管理员你好，由于某些原因"+getSessionUserName()+"退出了"+ model.getGroupInfo().get("name"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	private List<String> deleteMember(){
		

		//List<Integer> members = new ArrayList<Integer>();
		if(uid.charAt(uid.length()-1) == ','){
			uid = uid.substring(0, uid.length()-1);
		}
		String[] uids = uid.split(",");
		for(String uid:uids){
			members.add(Integer.parseInt(uid));
		}

		List<UserInfo> userlist = UserInfoUtil.getUserInfoByUid(getSessionToken(), members);
		//首先做个Group的权限的查询，如果是符合其权限的话，就进行统一的批量的移除。
		for(Integer id:members){
			//Integer id = Integer.parseInt(uid);
			GroupPermission gp = GroupUserManager.findPermission(id, groupId);
			if(gp!=null){
				GroupUserManager.removeUserPermission(id, groupId);
				
			}
			GroupUserManager.delMember(groupId,id);
		}
		List<String> mailList = new ArrayList<String>();
		for(UserInfo userInfo:userlist){
			mailList.add(userInfo.getEmail());
		}
		return mailList;
		
	}
	//该忽略的时候就是。
	public String adminIgnore(){
		if (uid.charAt(uid.length() - 1) == ',') {
			uid = uid.substring(0, uid.length() - 1);
		}
		String[] uids = uid.split(",");
		List<String> emails = new ArrayList<String>();
		List<Integer> userIdList = new ArrayList<Integer>();
		
		for (String one : uids) {
			String[] tmp = one.split(":");
            Integer userId = Integer.parseInt(tmp[0]);
			userIdList.add(userId);	
		}

		List<UserInfo> userInfos = UserInfoUtil.getUserInfoByUid(getSessionToken(), userIdList);
		for(UserInfo info:userInfos){
			emails.add(info.getEmail());
		}
		
		VerifyUserManager.delete(groupId, emails);
		
		return SUCCESS;
	}
	public String ignore(){
		
		VerifyUserManager.delete(groupId,getSessionEmail());

		return SUCCESS;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
