package com.free4lab.freeshare.manager.permission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.model.dao.ListPermissionDAO;
import com.free4lab.freeshare.model.dao.ListsDAO;

public class ListPermissionManager implements IPermissionManager{

	private static final Logger logger = Logger.getLogger(ListPermissionManager.class);
	/**
	 * @param listId  
	 * @param userId  
	 */
	public Integer getPermission(Integer listId, Integer userId) {
		logger.info("get permission value between item " + 
				listId + " user " + userId);
		
		if (isAuthor(listId, userId)){
			logger.info("具有作者权限");
			return PermissionUtil.AUTHOR_PERMISSION;
		} else if (inWritableTeam(listId, userId)){
			logger.info("具有读写权限");
			return PermissionUtil.WRITABLE_PERMISSION;
		}else {
			return PermissionUtil.AVOID_PERMISSION;
		}
	}
	/**
	 * @param id
	 * @return List 返回读写与分享组id，以建立索引
	 */
	public List<Integer> savePermission(Integer id, String permission,
			String writegroups, String readgroups) {
		List<Integer> writeIdList = new ArrayList<Integer>();
		List<Integer> readIdList = new ArrayList<Integer>();
		List<Integer> groupIds = new ArrayList<Integer>();
		if (permission.equals("public")) {
			setPublic(id);
		}
		if (writegroups != null) {
			logger.info("writegroups is " + writegroups);
			String[] writeIdArray = writegroups.split(",");

			for (String gid : writeIdArray) {
				writeIdList.add(Integer.parseInt(gid));
			}
			for (Integer groupId : writeIdList) {
				setWritablePermission(id, groupId);
			}
		}
		if (readgroups != null && readgroups.length() > 1) {
			String[] readIdArray = readgroups.split(",");

			for (String gid : readIdArray) {
				readIdList.add(Integer.parseInt(gid));
			}
			for (Integer groupId : readIdList) {
				setReadablePermission(id, groupId);
			}
		}
		groupIds.addAll(writeIdList);
		groupIds.addAll(readIdList);
		return groupIds;
	}
	//TODO 把含有可读组的接口统一换掉
	/**
	 * @param id 列表的id
	 * @param groups 归属群组的id组成的String
	 * @return List 返回读写与分享组id，以建立索引
	 */
	public List<Integer> savePermission(Integer id,
			String groups) {
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
	@Deprecated
	private boolean inReadableTeam(Integer listId, Integer userId) {
		return false;
	}

	private boolean inWritableTeam(Integer listId, Integer userId) {
		try {
			ListPermissionDAO permissionDAO = new ListPermissionDAO();
			List<Integer> teamIds = permissionDAO.findWritableTeams(listId);
			Iterator<Integer> it = teamIds.iterator();
			while(it.hasNext()){
				if(GroupUserManager.checkMember(userId, it.next())){
					return true;
				}
			}
			return false;
		} catch (RuntimeException re) {
			return false;
		}
	}

	private boolean isAuthor(Integer listId, Integer userId) {
		try {
			ListsDAO ListsDAO = new ListsDAO();
			if (userId.equals(ListsDAO.findById(listId).getAuthorId())){
				return true;
			}else {
				return false;
			}
		} catch (RuntimeException re) {
			return false;
		}
	}

	public boolean isPublic(Integer listId) {
		return (new ListPermissionDAO()).isPublic(listId);
	}

	public boolean isReadable(Integer id, Integer teamId) {
		return new ListPermissionDAO().isReadable(id, teamId);
	}
	public int setWritablePermission(Integer listId, Integer teamId) {
		try {
			(new ListPermissionDAO()).setReadWritePermission(listId, teamId);
			return 0;
		} catch (RuntimeException re) {
			return -1;
		} 
	}

	public int setReadablePermission(Integer listId, Integer teamId) {
		try{
			(new ListPermissionDAO()).setReadOnlyPermission(listId, teamId);
			return 0;
		} catch (RuntimeException re) {
			re.printStackTrace();
			return -1;
		}
	}

	public int setPublic(Integer listId) {
		return setReadablePermission(listId, 0);
	}

	public int setPrivate(Integer listId) {
		return setWritablePermission(listId, 0);
	}
	public boolean deletePermission(Integer id, Integer teamId) {
		try{
			new ListPermissionDAO().deletePermission(id, teamId);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public boolean deleteGroupPermission(Integer teamId) {
		try{
			new ListPermissionDAO().deleteGroupListPermission(teamId);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public List<Integer> findReadTeams(Integer id) {
		List<Integer> list = new ListPermissionDAO().findReadableTeams(id);
		return list;
	}

	public List<Integer> findWriteTeams(Integer id) {
		List<Integer> list = new ListPermissionDAO().findWritableTeams(id);
		return list;
	}
}
