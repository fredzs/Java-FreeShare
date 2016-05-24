package com.free4lab.freeshare.action.group.administration;

import java.util.*;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.*;

import com.free4lab.freeshare.model.dao.*;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.util.GroupUserListUtil;

import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;

/**
 * @author 
 * 显示该组的所有成员
 */
public class GetMembersAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private static final Logger logger = Logger.getLogger(GetMembersAction.class);
	private Integer groupId;
	private String uuid;
	private String authToken;
	private List<UserInfo> userlist;
	
	public String execute(){
		logger.info("the groupid is " + groupId);
		userlist = GroupUserListUtil.getAllGroupUserListByGroupId(groupId,getSessionToken());
		logger.info("the userlists's size is " + userlist.size());
		return SUCCESS;
	}
	
	public List<UserInfo> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<UserInfo> userlist) {
		this.userlist = userlist;
	}

	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
