package com.free4lab.freeshare.manager.permission;
import java.util.List;
public interface IPermissionManager {
	public abstract List<Integer> savePermission(Integer id, String permission,
			String writegroups, String readgroups);
	
	public abstract List<Integer> savePermission(Integer id, String writegroups);
	
	public abstract Integer getPermission(Integer id, Integer userId);

	public abstract int setPublic(Integer id);
	
	public abstract int setPrivate(Integer id);
	
	public abstract int setWritablePermission(Integer id, Integer teamId);

	public abstract int setReadablePermission(Integer id, Integer teamId);
	
	public abstract boolean deletePermission(Integer id, Integer teamId);
	
	public abstract List<Integer> findReadTeams(Integer id);
	
	public abstract List<Integer> findWriteTeams(Integer id);
	
	public abstract boolean isReadable(Integer id, Integer teamId);
	
	public abstract boolean isPublic(Integer id);
	
}