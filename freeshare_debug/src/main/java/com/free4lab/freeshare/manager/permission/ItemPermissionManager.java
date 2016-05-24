package com.free4lab.freeshare.manager.permission;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.model.dao.ItemDAO;
import com.free4lab.freeshare.model.dao.ItemPermissionDAO;

public class ItemPermissionManager implements IPermissionManager {

	private static final Logger logger = Logger
			.getLogger(ItemPermissionManager.class);

	/**
	 * @param itemId
	 * @param userId
	 */
	public Integer getPermission(Integer itemId, Integer userId) {
		logger.info("get permission value between item " + itemId + " user "
				+ userId);

		if (isAuthor(itemId, userId)) {
			logger.info("the permission is author");
			return PermissionUtil.AUTHOR_PERMISSION;
		} else if (inWritableTeam(itemId, userId)) {
			logger.info("the permission is write");
			return PermissionUtil.WRITABLE_PERMISSION;
		} else {
			return PermissionUtil.AVOID_PERMISSION;
		}
	}
	/**
	 * @param id
	 * @return List 返回读写与分享组id，以建立索引
	 */
	public List<Integer> savePermission(Integer id, String permission,
			String writegroups, String readgroups) {
		List<Integer> groupIds = new ArrayList<Integer>();
		if (permission.equals("public")) {
			setPublic(id);
		}
		if (writegroups != null && writegroups.length() > 0) {
			logger.info("writegroups is " + writegroups);
			String[] writeIdArray = writegroups.split(",");

			for (String gid : writeIdArray) {
				Integer groupId = Integer.parseInt(gid);
				setWritablePermission(id, groupId);
				groupIds.add(groupId);
			
			}
		}else
			groupIds.add(-1);
		return groupIds;
	}
	
	public List<Integer> savePermission(Integer id, String groups) {
		
		List<Integer> groupIds = new ArrayList<Integer>();
		if (groups != null) {
			String[] groupIdArray = groups.split(",");

			for (String gid : groupIdArray) {
				groupIds.add(Integer.parseInt(gid));
			}
			for (Integer groupId : groupIds) {
				setWritablePermission(id, groupId);
			}
		}
		return groupIds;
	}
	
	private boolean isAuthor(Integer itemId, Integer userId) {
		logger.info("check if the user is the author!");
		try {
			ItemDAO itemDAO = new ItemDAO();
			if (userId.equals(itemDAO.findById(itemId).getAuthorId())) {
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException re) {
			return false;
		}
	}

	public boolean deletePermission(Integer id, Integer teamId) {
		try {
			new ItemPermissionDAO().deletePermission(id, teamId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean deleteGroupPermission(Integer teamId) {
		try {
			new ItemPermissionDAO().deleteGroupItemPermission(teamId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean inWritableTeam(Integer itemId, Integer userId) {
		logger.info("check if the user in the writable teams!");
		try {
			ItemPermissionDAO permissionDAO = new ItemPermissionDAO();
			List<Integer> teamIds = permissionDAO.findWritableTeams(itemId);
			for(Integer id : teamIds){
				if(id != null && id != 0){
					if (GroupUserManager.checkMember(userId, id)) {
						return true;
					}
				}
			}
			return false;
		} catch (NullPointerException nullPoint) {
			logger.info(nullPoint.getStackTrace(), nullPoint);
			return false;
		} catch (RuntimeException re) {
			logger.info(re.getStackTrace(), re);
			return false;
		}
	}

	@SuppressWarnings("unused")
	private boolean inReadableTeam(Integer itemId, Integer userId) {
		logger.info("check if the user in the readable teams!");
		try {
			ItemPermissionDAO permissionDAO = new ItemPermissionDAO();
			List<Integer> teamIds = permissionDAO.findReadableTeams(itemId);
			if (teamIds.contains(new Integer(0))) {
				return true;
			}
			for(Integer id : teamIds){
				if(id != null){
					if (GroupUserManager.checkMember(userId, id)) {
						return true;
					}
				}
			}
			return false;
		} catch (NullPointerException nullPoint) {
			logger.info(nullPoint.getStackTrace(), nullPoint);
			return false;
		} catch (RuntimeException re) {
			logger.info(re.getStackTrace(), re);
			return false;
		}
	}

	public boolean isPublic(Integer itemId) {
		logger.info("check if item is public!");
		return (new ItemPermissionDAO()).isPublic(itemId);
	}

	public boolean isReadable(Integer id, Integer teamId) {
		return (new ItemPermissionDAO()).isReadable(id, teamId);
	}

	/**
	 * @see com.free4lab.freeshare.manager.basic.IPermissionManager#setWritablePermission(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	public int setWritablePermission(Integer itemId, Integer teamId) {
		try {
			(new ItemPermissionDAO()).setReadWritePermission(itemId, teamId);
			return 0;
		} catch (RuntimeException re) {
			return -1;
		}
	}

	/**
	 * @see com.free4lab.freeshare.manager.basic.IPermissionManager#setReadablePermission(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	public int setReadablePermission(Integer itemId, Integer teamId) {
		try {
			(new ItemPermissionDAO()).setReadOnlyPermission(itemId, teamId);
			return 0;
		} catch (RuntimeException re) {
			return -1;
		}
	}

	public int setPublic(Integer itemId) {
		return setReadablePermission(itemId, 0);
	}

	public int setPrivate(Integer itemId) {
		return setWritablePermission(itemId, 0);
	}
	
	@Deprecated
	public List<Integer> findReadTeams(Integer itemId) {
		List<Integer> list = new ItemPermissionDAO().findReadableTeams(itemId);
		return list;
	}

	public List<Integer> findWriteTeams(Integer itemId) {
		List<Integer> list = new ItemPermissionDAO().findWritableTeams(itemId);
		return list;
	}
}
