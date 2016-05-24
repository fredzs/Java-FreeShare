package com.free4lab.freeshare.manager.factory;

import java.util.List;

import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.model.dao.ResourcePermission;
import com.free4lab.freeshare.model.dao.ResourcePermissionDAO;
/**
 * 
 * @author zhaowei
 *
 */
public class ResourcePermissionFactory {
	private static ResourcePermissionFactory rpFactory = null;
	private static ResourcePermissionDAO rpDAO = null;
	
	public synchronized static ResourcePermissionFactory getInstance(){
		if(rpFactory == null){
			rpFactory = new ResourcePermissionFactory();
			rpDAO = new ResourcePermissionDAO();
		}
		return rpFactory;
	}
	
	public ResourcePermission createResourcePermission(ResourcePermission rp) {
		rpDAO.save(rp);
		return rp;
	}
	
	/**
	 * 返回是否是该资源作者
	 * @param resourceId
	 * @param userId
	 * @return
	 */
	public boolean isAuthor(Integer resourceId, Integer userId){
		if (userId.equals(new ResourceManager().readResource(resourceId).getAuthorId())) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 返回归属groupId的所有resourceId
	 * @param groupId
	 * @return
	 */
	public List<Resource> selectResources(Integer groupId) {
		return rpDAO.selects(groupId);
		
	}
	/**
	 * 删除资源和群组的权限关系
	 * @param resourceId
	 * @param groupId
	 * @return
	 */
	public void deleteBelongGroup(Integer resourceId, Integer groupId){
		rpDAO.deleteBelongGroup(resourceId, groupId);
	}
	
	/**
	 * 删除资源的归属
	 * @param resourceId
	 * @param groupId
	 * @return
	 */
	public void deleteResourcePermission(Resource resource){
		rpDAO.deletePermissionByResourceId(resource);
	}
	public void deleteGroupPermission(Integer groupId){
		rpDAO.deletePermissionByGroupId(groupId);
	}
	
	/**
	 * 返回resourceId归属的所有的群组
	 * @param groupId
	 * @return
	 */
	public List<Integer> selectWritableGroups(Integer resourceId){
		return rpDAO.selectWritableGroups(resourceId);
	}
}
