package com.free4lab.freeshare.action.group.administration;


import java.util.*;

import org.apache.log4j.Logger;
import org.hamcrest.core.IsInstanceOf;

import com.free4lab.freeshare.Const;
import com.free4lab.freeshare.URLConst;
import com.free4lab.freeshare.UserType;
import com.free4lab.freeshare.action.BaseAction;


import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.VerifyUserManager;
import com.free4lab.freeshare.model.dao.*;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;

public class UserAdminAction extends BaseAction {


	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserAdminAction.class);
	private Integer num; //表示企业版前端使用，0为没有企业，1为有企业
	private Integer groupId;
	private String name;
	private String groupInfo;
	private Integer type; // 用户类型-1 0 1 2
	private Integer kind;	
	private String groupAvatar;
	private List<UserInfo> userlist;// 已经属于该组的用户
	private List<UserInfo> creator;
	private List<UserInfo> adminlist;
	private final static String DOWNLOAD_URL = URLConst.APIPrefix_FreeDisk + "/download?uuid=";
	private String company;
	
	public String execute() {
		initial();
		company = null;
		num = 1;
		int flag = 0;
		List<Integer> groupIds = getOwnCompanyGroupsIds();
		Integer userId = new BaseAction().getSessionUID();
		String token = new BaseAction().getSessionToken();
		List<Integer> userList = new ArrayList<Integer>();
		userList.add(userId);
		List<UserInfo> m = UserInfoUtil.getUserInfoByUid(token, userList);
		if(m != null)
		{
		    company= m.get(0).getCompany();
		}
		logger.info(company instanceof String);
		logger.info("company:"+company);
		if(company.equals("null")){
			num = 0;
		}
		
		logger.info("groupIds。。。:"+groupIds);
		for(Integer gi:groupIds)
		{
			Group group = GroupManager.getFullGroup(gi);
			if(group == null)
			{
				return INPUT;
			}
			List<Integer> memberList = group.getMemberList();
			if (memberList != null && memberList.size() >= 0) {
				List<UserInfo> ul = UserInfoUtil.getUserInfoByUid(getSessionToken(), memberList);
				for(UserInfo u : ul)
				{
					if(u.getEmail().length() >20){
						u.setEmail(u.getEmail().substring(0, 20));
					}
					for(UserInfo ui :userlist)
					{
						if(ui.getUserId().equals(u.getUserId()))
						{
							flag = 1;
						}
					}			
					if(flag == 0)
					{
						userlist.add(u);
					}
					flag = 0;

				}
			}
			/*if(userlist.size() == 0 || userlist.size() == 1)
			{
				num = 0;
			}*/
			logger.info("UserAdminAction:userlist:"+userlist.size());
			setName(group.getGroupInfo().get("name"));
			setGroupInfo(group.getGroupInfo().get("desc"));
		
			if(group.getGroupInfo().toString().contains("avatar"))
			{
				setGroupAvatar(DOWNLOAD_URL+group.getGroupInfo().get("avatar"));	
			}	
		}
		
		logger.info("userlist.size():"+userlist.size());
		logger.info("num:"+num);
		return SUCCESS;
		
	}
	
	private void initial(){
		userlist = new ArrayList<UserInfo>();
		adminlist = new ArrayList<UserInfo>();
		creator = new ArrayList<UserInfo>();
	}

	public List<Integer> getOwnCompanyGroupsIds()
	{
		List<Group> groupList;
		List<Integer> groupIds = null;
		groupList = GroupManager.getCompanyGroups();
		if(groupList!=null){
			groupIds = new ArrayList<Integer>();
		for(Group gl:groupList)
		{
			groupIds.add(gl.getGroupId());
		}
		}
		else
		{
			logger.info("groupList为空");
		}
	//	System.out.println("得到我公司所有群组的id，共有："+groupIds.size());
		return groupIds;
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

	public String getGroupAvatar() {
		return groupAvatar;
	}

	public void setGroupAvatar(String groupAvatar) {
		this.groupAvatar = groupAvatar;
	}
	
	public List<UserInfo> getCreator() {
		return creator;
	}
	public void setCreator(List<UserInfo> creator) {
		this.creator = creator;
	}


	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}


}
