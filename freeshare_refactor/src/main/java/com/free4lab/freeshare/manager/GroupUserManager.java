package com.free4lab.freeshare.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.free4lab.freeshare.model.dao.GroupPermission;
import com.free4lab.freeshare.model.dao.GroupPermissionDAO;
import com.free4lab.freeshare.model.dao.GroupUser;
import com.free4lab.freeshare.model.dao.GroupUserDAO;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;

public class GroupUserManager {

	/**
	 * @param Integer
	 *            userId <br/>
	 *            通过用户id获取用户所在的所有群组
	 * @return 包含有组描述和创建时间的群组对象的列表，这里没有群组的成员
	 */
	public static List<Group> getMyGroups(Integer userId) {
		List<GroupUser> gus = new GroupUserDAO().getGusByUID(userId);
		if (gus != null) {
			List<Integer> groupIds = new ArrayList<Integer>();
			for (GroupUser gu : gus) {
				groupIds.add(gu.getGroupId());
			}
			return GroupManager.getGroups(groupIds);
		}
		return null;
	}

	/**
	 * 通过用户id获取用户所在的所有群组的id
	 * 
	 * @param userId
	 * @return
	 */
	public static List<Integer> getMyGroupIds(Integer userId) {
		List<GroupUser> gus = new GroupUserDAO().getGusByUID(userId);
		List<Integer> groupIds = new ArrayList<Integer>();
		if (gus != null) {
			for (GroupUser gu : gus) {
				groupIds.add(gu.getGroupId());
			}
		}
		return groupIds;
	}

	/**
	 * 
	 * 根据组的信息,得到这些组所包含的组员关系。首先要判断,获取者是否是该组的成员
	 */
	public static Map<Integer, List<Integer>> getGroupUsers(List<Group> groups,
			Integer userId) {
		Map<Integer, List<Integer>> groupUserMap = new HashMap<Integer, List<Integer>>();
		for (Group g : groups) {
			if (checkMember(userId, g.getGroupId())) {
				groupUserMap.put(g.getGroupId(), getGroupUids(g.getGroupId()));
			}
		}
		return groupUserMap;
	}

	/**
	 * 
	 * 根据群组的id，获取该组的所有组员对象
	 */
	public static List<GroupUser> getGroupUsers(Integer groupId) {
		List<GroupUser> gus = new ArrayList<GroupUser>();
		gus.addAll(new GroupUserDAO().getGusByGroupId(groupId));
		return gus;
	}

	/**
	 * 
	 * 根据群组的id，获取该组的所有组员的id
	 */
	public static List<Integer> getGroupUids(Integer groupId) {
		return (new GroupUserDAO()).getUids(groupId);
	}

	/**
	 * 向某个组的添加用户, 要首先判断该操作者是否有权限添加用户
	 * 
	 * @param group
	 *            封装有要添加的memberlist的group对象
	 * @param userId
	 *            操作者,用以判断该用书是否有权限操作
	 */
	public static void addMembers(Group group, Integer userId) {
		// if(findPermission(userId, group.getGroupId()) != null){
		// List<Integer> uidList = group.getMemberList();
		List<GroupUser> gus = new ArrayList<GroupUser>();
		/*
		 * for (Integer uid : uidList) { gus.add(new
		 * GroupUser(group.getGroupId(), uid)); }
		 */
		gus.add(new GroupUser(group.getGroupId(), userId));
		new GroupUserDAO().save(gus);
		// }
	}

	/**
	 * 删除某个组的所有用户关系,要首先判断该用户是否有权限做此操作
	 * 
	 * @param groupId
	 * @param userId
	 *            调用本接口的用户id
	 */
	public static boolean delMembers(Integer groupId, Integer userId) {
		if (checkMember(userId, groupId) == true) {
			new GroupUserDAO().delMembers(groupId);
			return true;
		}
		return false;
	}

	/**
	 * 批量删除群组中的某些用户,必须传送authId,用以判断给用户是否有权限做此操作
	 * 
	 * @param groupId
	 * @param userIds
	 *            要删除的批量用户的id
	 * @param authId
	 */
	public static boolean delMembers(Integer groupId, List<Integer> userIds,
			Integer authId) {
		if (findPermission(authId, groupId) != null) {
			for (Integer userId : userIds) {
				new GroupUserDAO().delMember(groupId, userId);
			}
			return true;
		}
		return false;
	}

	/**
	 * 删除群组中的某些用户,必须传送authId,用以判断给用户是否有权限做此操作,仅判断是否是本组用户
	 * 
	 * @param groupId
	 * @param userId
	 * @param authId
	 */
	public static boolean delMember(Integer groupId, Integer userId) {
		if (checkMember(userId, groupId)) {
			new GroupUserDAO().delMember(groupId, userId);
			return true;
		}
		return false;
	}

	/**
	 * 如果是该组成员返回真
	 * 
	 * @param userId
	 * @param groupId
	 * @return boolean
	 */
	public static boolean checkMember(Integer userId, Integer groupId) {
		return new GroupUserDAO().isMember(groupId, userId);
	}

	/**** 针对管理员的操作 *****/
	public static GroupPermission setAdmin(Integer groupId, Integer user,
			Integer type) {
		return new GroupPermissionDAO().add(groupId, user, type);
	}

	public static GroupPermission findPermission(Integer user, Integer groupId) {
		return new GroupPermissionDAO().findPermission(user, groupId);
	}

	public static boolean removeUserPermission(Integer user, Integer groupId) {
		if (new GroupPermissionDAO().removeUserPermission(user, groupId)) {
			return true;
		} else
			return false;
	}

	public static boolean removeGroupPermission(Integer groupId) {
		if (new GroupPermissionDAO().removeGroupPermission(groupId)) {
			return true;
		} else
			return false;
	}

	public static List<UserInfo> getAdmin(Integer groupId, String accessToken) {
		List<GroupPermission> gl = new GroupPermissionDAO().findByProperty(
				"groupId", groupId);
		List<Integer> uidList = new ArrayList<Integer>();
		for (GroupPermission gp : gl) {
			uidList.add(gp.getUid());
		}
		return UserInfoUtil.getUserInfoByUid(accessToken, uidList);
	}

	public static Integer getCreator(Integer groupId) {
		List<GroupPermission> gl = new GroupPermissionDAO().findByProperty(
				"groupId", groupId);
		// List<Integer> uidList = new ArrayList<Integer>();
		for (GroupPermission gp : gl) {
			// uidList.add(gp.getUid());
			if (gp.getType().equals(2)) {
				return gp.getUid();
			}
		}
		return 0;
	}
}
