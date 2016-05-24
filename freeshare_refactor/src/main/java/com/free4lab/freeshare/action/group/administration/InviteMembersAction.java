package com.free4lab.freeshare.action.group.administration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.*;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.*;

import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.VerifyUserManager;

import com.free4lab.freeshare.model.dao.GroupModel;
import com.free4lab.freeshare.model.dao.GroupUser;

import com.free4lab.freeshare.model.data.Group;

import com.free4lab.freeshare.util.GroupUserListUtil;
import com.free4lab.freeshare.util.RedundancyDealUtil;

import com.free4lab.utils.http.HttpCrawler;
import com.free4lab.utils.account.UserInfo;
import com.opensymphony.xwork2.util.finder.ClassFinder.Info;



public class InviteMembersAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(InviteMembersAction.class);
	/* 首先要获取添加该组的id， */
	private Integer groupId;// 前端界面隐藏一个groupid的属性。
	private String groupName;
	private String myName;
	// 在前端增加成员页面上面需要将该组的成员首先显示出来。
	//private List<User> members;
	private List<UserInfo> members;
	private Map<Integer, List<UserInfo>> groupUserMap;
	
	private Map<Integer, String> groupNameMap;
	
	public Map<Integer, String> getGroupNameMap() {
		return groupNameMap;
	}
	public void setGroupNameMap(Map<Integer, String> groupNameMap) {
		this.groupNameMap = groupNameMap;
	}

	// 可选的成员。
	//private List<UserInfo> futureMembers;
	private String reason;
	private String invitation;
	private String mail;
	private String newUser;
	private Group group;
	String groupPermission;

    public String getGroupPermission() {
		return groupPermission;
	}
	public void setGroupPermission(String groupPermission) {
		this.groupPermission = groupPermission;
	}
	public  String inviteFriends(){
    	myName = getSessionUserName();
		group = GroupManager.getFullGroup(groupId);
		groupPermission = group.getExtend();
		setGroupName(group.getGroupInfo().get("name"));
		//获取本组的成员。
		members = GroupUserListUtil.getGroupUserListByGroupId(groupId,getSessionToken());
		return SUCCESS;
		
    }
	public List<UserInfo> getMembers() {
		return members;
	}
	public void setMembers(List<UserInfo> members) {
		this.members = members;
	}
	public String execute() {
		myName = getSessionUserName();
		group = GroupManager.getFullGroup(groupId);
		groupPermission = group.getExtend();
		setGroupName(group.getGroupInfo().get("name"));
		return SUCCESS;
	}
	public String	inviteOthers(){
		// 首先是对用户的id进行检测，判断该用户是否有邀请用户的权限。
		myName = getSessionUserName();
		group = GroupManager.getFullGroup(groupId);
		groupPermission = group.getExtend();
		setGroupName(group.getGroupInfo().get("name"));
	
		List<Group> groups = GroupUserManager.getMyGroups(getSessionUID());

		groupUserMap = GroupUserListUtil.getInviteUsers(groups,getSessionUID(),getSessionToken(),groupId);
		Iterator<Entry<Integer, List<UserInfo>>> it = groupUserMap.entrySet().iterator();
		Map groupUserNumMap = new HashMap<Integer,Integer>();
		while(it.hasNext()){
			Entry<Integer, List<UserInfo>> oneGroup = it.next();			
			groupUserNumMap.put(oneGroup.getKey(), oneGroup.getValue().size());
		}
		
		logger.info("groupUserMap:"+groupUserMap.toString()+" size: "+groupUserMap.size());
		//obtainGroupNames查询相应的群组名。
		obtainGroupNames(groupUserNumMap);

		return SUCCESS;
	}
	
	private void obtainGroupNames(Map groupUserNumMap){
		List<Group>  groups= GroupUserManager.getMyGroups(getSessionUID());
		Iterator<Group> it = groups.iterator();
		groupNameMap = new HashMap<Integer, String>();
		while(it.hasNext()){
			Group group = it.next(); 
			Integer num = (Integer) groupUserNumMap.get(group.getGroupId());
			logger.info("num"+num); 
			String name = group.getGroupInfo().get("name");
			if(name.length() > 10){
				name = name.substring(0, 10);
			}
			groupNameMap.put(group.getGroupId(), name+"("+num+")");
		}
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/*public List<UserInfo> getFutureMembers() {
		return futureMembers;
	}

	public void setFutureMembers(List<UserInfo> futureMembers) {
		this.futureMembers = futureMembers;
	}*/

	public String getNewUser() {
		return newUser;
	}

	public void setNewUser(String newUser) {
		this.newUser = newUser;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	public Map<Integer, List<UserInfo>> getGroupUserMap() {
		return groupUserMap;
	}
	public void setGroupUserMap(Map<Integer, List<UserInfo>> groupUserMap) {
		this.groupUserMap = groupUserMap;
	}


	

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}
	public String getInvitation() {
		return invitation;
	}

	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}
}
