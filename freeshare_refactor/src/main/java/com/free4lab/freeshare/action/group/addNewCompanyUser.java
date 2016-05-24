package com.free4lab.freeshare.action.group;
import java.util.ArrayList;
import java.util.List;

import com.free4lab.freeshare.action.*;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.util.*;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;

import org.apache.log4j.Logger;

public class addNewCompanyUser extends BaseAction{
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(addNewCompanyUser.class);
	private String email;
	private String pwdMD5;
	private String company;
	private String userIds; //在一个groupId中加入多个userIds。
	private Integer uid; //把一个uid加入多个群组中，groupIds。
	private List<UserInfo> userlist;
	private Integer groupId;
	private List<Integer> groupIds;
	private String groupIds2;
	private List<Group> groupList;
	private final static String DOWNLOAD_URL = "http://freedisk.free4lab.com/download?uuid=";
	
	public String execute()
	{	
		logger.info("email:"+email+" pwd:"+pwdMD5);
		Integer userId = getSessionUID();
		String token = getSessionToken();
		List<Integer> userList = new ArrayList<Integer>();
		userList.add(userId);
		List<UserInfo> ui = UserInfoUtil.getUserInfoByUid(token, userList);
		if(ui != null)
		{
			company = ui.get(0).getCompany();
		}
		//company = "baidu";
		new CompanyUser().addNewCompanyUser(email, pwdMD5, company);
		AddtoOneGroup();
		return SUCCESS;
	}
	
	public String allCompanyUsers(){
		groupList = GroupManager.getCompanyGroups();
		userlist = new ArrayList<UserInfo>();
		int flag = 0;
		groupIds = getOwnCompanyGroupsIds();
		System.out.println("groupIds。。。:"+groupIds);
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
					System.out.println(userlist.size());
					if(flag == 0)
					{
						userlist.add(u);
					}
					flag = 0;

				}
			}
		
/*			if(group.getGroupInfo().toString().contains("avatar"))
			{
				setGroupAvatar(DOWNLOAD_URL+group.getGroupInfo().get("avatar"));	
			}*/	
			System.out.println("userlist.size():"+userlist.size());
		}
		
		return SUCCESS;
		
	}
	public String AddManyUsers(){
		logger.info("AddManyUsers");
		logger.info("userIds:"+userIds+" grouId:"+groupId);
		Group group = GroupManager.getFullGroup(groupId);
		
		for(String str:userIds.split(","))
		{
			if(GroupUserManager.checkMember(Integer.parseInt(str), groupId) == false){
				GroupUserManager.addMembers(group, Integer.parseInt(str));
			}
		}
		
		return SUCCESS;
	}
	
	public String AddtoOneGroup(){
		logger.info("AddtoOneGroups");
		logger.info("userId:"+uid+" grouIds:"+groupId);
		//GroupUserManager.addMembers(GroupManager.getFullGroup(groupId), uid);
		return SUCCESS;
	}
	public String AddtoManyGroups(){
		logger.info("AddManyGroups");
		logger.info("userId:"+uid+" grouIds:"+groupIds);
		for(String str:groupIds2.split(","))
		{
			if(GroupUserManager.checkMember(uid, Integer.parseInt(str)) == false){
				GroupUserManager.addMembers(GroupManager.getFullGroup(Integer.parseInt(str)), uid);
			}
		}
		return SUCCESS;
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
		System.out.println("company的groupIds:"+groupIds);
		System.out.println("groupList.size():"+groupList.size());
		}
		else
		{
			System.out.println("groupList为空");
		}
	//	System.out.println("得到我公司所有群组的id，共有："+groupIds.size());
		return groupIds;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwdMD5() {
		return pwdMD5;
	}
	public void setPwdMD5(String pwdMD5) {
		this.pwdMD5 = pwdMD5;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}


	public List<UserInfo> getUserlist() {
		return userlist;
	}


	public void setUserlist(List<UserInfo> userlist) {
		this.userlist = userlist;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public List<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Integer> groupIds) {
		this.groupIds = groupIds;
	}


	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public String getGroupIds2() {
		return groupIds2;
	}

	public void setGroupIds2(String groupIds2) {
		this.groupIds2 = groupIds2;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
}
