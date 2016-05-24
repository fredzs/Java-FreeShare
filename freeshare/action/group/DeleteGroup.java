package com.free4lab.freeshare.action.group;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.free4lab.freeshare.action.*;

import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.permission.ItemPermissionManager;
import com.free4lab.freeshare.manager.permission.ListPermissionManager;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.model.dao.GroupModel;
import com.free4lab.freeshare.model.dao.GroupModelDAO;
import com.free4lab.freeshare.model.dao.GroupPermission;
import com.free4lab.freeshare.model.dao.GroupPermissionDAO;
import com.free4lab.freeshare.model.data.Group;

public class DeleteGroup extends BaseAction{
	/**
	 * 首先要进行操作者的身份，即是否有权限删除群组。
	 * 只有创建者才有对群组的删除权限。
	 * 删除群组：
	 * 1、删除群组、删除群组和成员的关系。GroupModel中的记录
	 * 2、删除群组和成员之间的权限关系。
	 * 3、TODO：删除群组和资源、列表的关系。
	 */
	private static final long serialVersionUID = 1L;
	private Integer groupId;
	private String uuid;
	
	public String execute(){
		GroupModelDAO gmDao = new GroupModelDAO();
		System.out.println("uuid:"+uuid);
		//System.out.println("groupId:"+groupId);
		Group group = GroupManager.get(uuid);
		System.out.println("group.getGroupId()"+group.getGroupId());
		System.out.println("getSessionUID():"+getSessionUID());
		groupId = group.getGroupId();
		GroupPermission gp = GroupUserManager.findPermission(getSessionUID(), group.getGroupId());
		if(gp == null||gp.getType() != 2){
			System.out.println("wrong delete group!");
			return INPUT;
		}
		
		String uuid = group.getUuid();
		String authToken = group.getAuthToken();
		

		Group deleteGroup = new Group(uuid , authToken);
		
		GroupManager.delGroup(deleteGroup);
		System.out.println("GroupManager.delGroup");
		//同时删除了群组的成员。
		GroupUserManager.delMembers(groupId,getSessionUID());
		/* 2、删除本地的群组记录。GroupModel中的记录
		
		gmDao.deleteByPrimaryKey(groupId);
		System.out.println("GroupModelDAO delete");*/
		/*3、删除群组和成员之间的权限关系。*/
		GroupUserManager.removeGroupPermission(groupId);
		System.out.println("after remove group permission.");
		/* 4、删除群组和资源、列表的关系。*/
		ItemPermissionManager ipm = new ItemPermissionManager();
		ipm.deleteGroupPermission(groupId);
		System.out.println("after remove item permission.");
		
		ListPermissionManager lpm = new ListPermissionManager();
		lpm.deleteGroupPermission(groupId);
		System.out.println("after remove list permission.");
		System.out.println("delete index");
		// TODO：删除 groupPermission
		new NewIndexManager().delIndex("group/items?groupId="+group.getGroupId());
//		GroupManager.delGroup(l);
//		GroupManager.delGroupModel(uuid);
		//TODO GroupPermission删除相应数据。通知组员组已解散
		return SUCCESS;
	}
	public static void main(String[] args){
		DeleteGroup dg = new DeleteGroup();
		Integer groupId = 252;
		dg.setGroupId(groupId);
		/*dg.setUuid("");
		dg.setAuthToken("");*/
		dg.execute();
	}
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
