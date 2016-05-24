package com.free4lab.freeshare.action.group.administration;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.BaseAction;

import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.model.dao.GroupPermission;

public class AdminAction  extends BaseAction  {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(AdminAction.class);
	private Integer groupId;
	private String uid;
	public String execute(){
		return SUCCESS;
	}
	public String setAdmin(){
		if(uid.charAt(uid.length()-1) == ','){
			uid = uid.substring(0, uid.length()-1);
		}
		String[] uids = uid.split(","); 
		List<String> list = new ArrayList<String>();
		for(String uid: uids){
			list.add(uid);
		}
		logger.info("把用户" + uid + "设为该组的管理员" + groupId);

		if(GroupUserManager.findPermission(getSessionUID(), groupId)!=null){
			for(String user : list){
				GroupUserManager.setAdmin(groupId, Integer.valueOf(user),1);
			}
			return SUCCESS;
		}else
			return INPUT;
	}
	public String removeAdmin(){
		logger.info("把用户" + uid + "取消管理员" + groupId);
		if(uid.charAt(uid.length()-1) == ','){
			uid = uid.substring(0, uid.length()-1);
		}
		String[] uids = uid.split(","); 
		List<String> list = new ArrayList<String>();
		for(String uid: uids){
			list.add(uid);
		}
		GroupPermission gp = GroupUserManager.findPermission(getSessionUID(), groupId);
		if(gp.getType()==2){
			for(String user : list){
				GroupUserManager.removeUserPermission(Integer.valueOf(user), groupId);
			}
			return SUCCESS;
		}else
			return INPUT;
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
