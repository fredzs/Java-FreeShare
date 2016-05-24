package com.free4lab.freeshare.manager;

import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.manager.factory.ResourcePermissionFactory;
import com.free4lab.freeshare.model.dao.Resource;

public class ResourcePermissionManager {
	private static final Logger logger = Logger
			.getLogger(ResourcePermissionManager.class);
	private static ResourcePermissionFactory rpf = null;
	static {
		rpf = getRPF();
	}

	private static ResourcePermissionFactory getRPF() {
		if (rpf == null) {
			rpf = ResourcePermissionFactory.getInstance();
		}
		return rpf;
	}

	public List<Resource> selectResources(Integer groupId) {
		return rpf.selectResources(groupId);
	}
	
	public List<Integer> selectWritableGroups(Integer resourceId) {
		return rpf.selectWritableGroups(resourceId);
	}

	public void deleteBelongGroup(Integer resourceId, Integer groupId) {
		rpf.deleteBelongGroup(resourceId, groupId);
	}

	public void deleteResourcePermission(Resource resource) {
		rpf.deleteResourcePermission(resource);
	}
	public void deleteGroupPermission(Integer groupId) {
		rpf.deleteGroupPermission(groupId);
	}
	

	public boolean isAuthor(Integer resourceId, Integer userId) {
		return rpf.isAuthor(resourceId, userId);
	}
}
