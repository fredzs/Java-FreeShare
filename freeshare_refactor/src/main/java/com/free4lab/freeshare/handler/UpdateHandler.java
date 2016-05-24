package com.free4lab.freeshare.handler;



import org.apache.log4j.Logger;

import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.manager.ResourcePermissionManager;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.model.dao.ResourcePermission;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

public class UpdateHandler extends ChainHandler{
	Logger logger = Logger.getLogger(UpdateHandler.class);
	@Override
	public void process(ResourceWrapper adapter, Chain chain) {
		if(adapter.getId() != null){
			update(adapter);
		}
		logger.info("UpdateHandler success, invoke next handler.");
		next(adapter, chain);
	}
	
	private void update(ResourceWrapper adapter){
		ResourcePermissionManager rpm = new ResourcePermissionManager();
		Resource resource = new Resource();
		resource.setId(adapter.getId());
		rpm.deleteResourcePermission(resource);
		resource = adapter.getResource();
		for(Integer groupId : adapter.getGroupIds()){
			ResourcePermission rp = new ResourcePermission();
			rp.setGroupId(groupId);
			rp.setPermissionType("read_write");
			resource.addResourcePermission(rp);
		}
		resource = new ResourceManager().updateResource(resource);
		adapter.setPublicTime(resource.getCreateTime());
	}
}
