package com.free4lab.freeshare.action.group.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.SessionConstants;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.VerifyUserManager;
import com.free4lab.freeshare.model.data.Group;

/*import com.free4lab.utils.group.Group;
import com.free4lab.utils.group.Membership;*/
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;
import com.opensymphony.xwork2.ActionContext;

public class AddMembersAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 首先是检查检查该group是否存在，返回一个该组的id或者是authToken。 ？检查该组人员已经达到上限。 获取该组的所有的成员。
	 * 如果存在的话，就将该组的成员添加进来，需要给定一个
	 */
	private static final Logger logger = Logger
			.getLogger(AddMembersAction.class);
	private Integer groupId;
	private String uid;
	private String email;


	// 是应该是新的成员表，还是新的成员的id的表。还是两个呀？
	public String execute() {

		return SUCCESS;
	}

	public String applyadd() {
		Group group = GroupManager.getSimpGroup(groupId);
		if (uid.charAt(uid.length() - 1) == ',') {
			uid = uid.substring(0, uid.length() - 1);
		}
		String[] uids = uid.split(",");
		List<String> emails = new ArrayList<String>();
		List<Integer> userIdList = new ArrayList<Integer>();
		
		for (String one : uids) {
			String[] tmp = one.split(":");
            Integer userId = Integer.parseInt(tmp[0]);
			GroupUserManager.addMembers(group, userId);
			userIdList.add(userId);	
		}

		List<UserInfo> userInfos = UserInfoUtil.getUserInfoByUid(getSessionToken(), userIdList);
		for(UserInfo info:userInfos){
			emails.add(info.getEmail());
		}
		
		VerifyUserManager.delete(groupId, emails);
		return SUCCESS;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String agree() {
		Group group = GroupManager.getSimpGroup(groupId);
		List<String> list = new ArrayList<String>();
		List<Integer> list2 = new ArrayList<Integer>();

		list.add(uid);
		list2.add(Integer.parseInt(uid));
		List<UserInfo> one = UserInfoUtil.getUserInfoByUid(getSessionToken(),
				list2);
	
		boolean check = GroupManager.checkInGroup(groupId,getSessionUID());
		
		if(!check)
		{
			GroupUserManager.addMembers(group,getSessionUID());
		}

		VerifyUserManager.delete(groupId, one.get(0).getEmail());
		
		return SUCCESS;
	}


	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
