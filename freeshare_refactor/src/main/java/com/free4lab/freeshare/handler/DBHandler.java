package com.free4lab.freeshare.handler;


import org.apache.log4j.Logger;

import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.model.dao.ResourcePermission;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

public class DBHandler extends ChainHandler {
	Logger logger = Logger.getLogger(DBHandler.class);
	//TODO 对编辑的操作
	public void process(ResourceWrapper adapter, Chain chain) {
		if(adapter.getId() == null){
			Resource resource = adapter.getResource();
			for(Integer groupId : adapter.getGroupIds()){
				ResourcePermission rp = new ResourcePermission();
				rp.setGroupId(groupId);
				rp.setPermissionType("read_write");
				resource.addResourcePermission(rp);
			}
			new ResourceManager().createResource(resource);
			adapter.setId(resource.getId());
		}
		
		logger.info("DBHandler success, invoke next handler.");
		next(adapter, chain);
	}
}
