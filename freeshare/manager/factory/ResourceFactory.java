package com.free4lab.freeshare.manager.factory;


import java.util.List;

import org.apache.log4j.Logger;
import org.python.antlr.PythonParser.return_stmt_return;


import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.model.dao.ResourceDAO;
import com.free4lab.freeshare.util.TimeUtil;


/*单例工厂形式创建,使用getInstance获取到实例*/
public class ResourceFactory {
	private static final Logger logger = Logger.getLogger(ResourceFactory.class);
	private static ResourceFactory rFactory = null;
	private static ResourceDAO resourceDAO = null;

	public static ResourceFactory getInstance(){
		if(rFactory == null){
			rFactory = new ResourceFactory();
			resourceDAO = new ResourceDAO();
		}
		return rFactory;
	}
	public Resource createResource(Resource resource) {
		resourceDAO.create(resource);
		return	resource;
	}
	
	public Resource createResource(Integer type, String name, String description,
			Integer authorId, String attachment){
		Resource resource = new Resource(type,name,description,authorId,attachment);
	    resourceDAO.create(resource);
		return	resource;
	}
	
	public Resource updateResource(Resource resource){
		resourceDAO.updateResource(resource);
		return resource;
	}
	public boolean deleteResource(Integer resourceId){
		return resourceDAO.deleteResource(resourceId);
	}
	//通过Id获取单个资源。
	public Resource getResource(Integer resourceId){
		Resource resource= resourceDAO.getResource(resourceId);
		return resource;
	}
	//通过Id获取多个资源。
	public List<Resource> getMutilResource(List<Integer> resourceIds){
		List<Resource> resources =  resourceDAO.getMutilResource(resourceIds);
		return resources;
	}
}
