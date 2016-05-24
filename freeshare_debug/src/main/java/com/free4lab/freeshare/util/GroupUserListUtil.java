package com.free4lab.freeshare.util;

import java.util.*;
import java.util.Map.Entry;

import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.model.dao.GroupUser;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.utils.userinfo.UserInfo;
import com.free4lab.utils.userinfo.UserInfoUtil;

public class GroupUserListUtil {

	public static List<UserInfo> getAllGroupUserListByGroupId(Integer groupId,
			String token) {
		List<GroupUser> userList = GroupUserManager.getGroupUsers(groupId);
		if (userList != null && userList.size() >= 0) {
			List<Integer> idList = new ArrayList<Integer>();
			for (GroupUser one : userList) {
				idList.add(one.getUserId());
			}

			return UserInfoUtil.getUserInfoByUid(token, idList);
		}
		return null;
	}

	/**
	 * 管理员邀请成员，获取被邀请人员的信息,并移除groupId所代表的群组中的成员
	 * 
	 * @param groups
	 * @groupId
	 * @param userId
	 * @param token
	 * @return 群组id与该组成员的映射
	 */
	public static Map<Integer, List<UserInfo>> getInviteUsers(
			List<Group> groups, Integer userId, String token, Integer groupId) {
		
		Map<Integer, List<Integer>> groupUserMap = GroupUserManager
				.getGroupUsers(groups, userId);
		List<Integer> guList = GroupUserManager.getGroupUids(groupId);
		Map<Integer, List<UserInfo>> userInfoMap = new HashMap<Integer, List<UserInfo>>();
		if (groupUserMap != null) {
			Set<Integer> uidSet = new HashSet<Integer>();
			Iterator<Entry<Integer, List<Integer>>> iterator = groupUserMap
					.entrySet().iterator();
			
			while (iterator.hasNext()) {
				Entry<Integer, List<Integer>> entry = iterator.next();
				uidSet.addAll(entry.getValue());
			}
			uidSet.addAll(guList);
			uidSet.removeAll(guList);
			List<Integer> uidList = new ArrayList<Integer>(uidSet);
			List<UserInfo> userInfoList = UserInfoUtil.getUserInfoByUid(token,
					uidList);
			userInfoMap = getInviteUsers(groupUserMap, userInfoList);
		}
		
		return userInfoMap;
	}
	static Map<Integer, List<UserInfo>> getInviteUsers(
			Map<Integer, List<Integer>> groupUserMap,
			List<UserInfo> userInfoList) {
		Map<Integer, List<UserInfo>> userInfoMap = new HashMap<Integer, List<UserInfo>>();
		
		for(Integer key : groupUserMap.keySet()){
			List<UserInfo> uiList = new ArrayList<UserInfo>();
			List<Integer> groupUserList = groupUserMap.get(key);
			for (Integer uid : groupUserList) {
				for(UserInfo ui : userInfoList){
					if(uid == Integer.parseInt(ui.getUserId())){
						uiList.add(ui);
					}
				}
			}
			userInfoMap.put(key, uiList);
		}
		return userInfoMap;
	}
	
	public static List<UserInfo> getGroupUserListByGroupId(Integer groupId,
			String token) {

		List<GroupUser> userList = GroupUserManager.getGroupUsers(groupId);
		if (userList != null && userList.size() >= 0) {

			Set<Integer> idSet = new HashSet<Integer>();
			for (GroupUser one : userList) {
				idSet.add(one.getUserId());
			}
			List<Integer> idList = new ArrayList<Integer>(idSet);
			List<UserInfo> lu = UserInfoUtil.getUserInfoByUid(token, idList);
			return lu;
		} else {
			return null;
		}
	}

}
