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
		getInstance();
		Resource resource= resourceDAO.getResource(resourceId);
		return resource;
	}
	//通过Id获取多个资源。
	public List<Resource> getMutilResource(List<Integer> resourceIds){
		List<Resource> resources =  resourceDAO.getMutilResource(resourceIds);
		return resources;
	}
	// 通过资源类型获得多个Id
	public List<Resource> getResources(Integer page,List<Integer> typeList,Integer authorId) {
		List<Resource> resources = resourceDAO.getResource(page,typeList,authorId);
		return resources;
	}
	
	// 通过群组id和资源类型获取多个资源
		public List<Resource> getGroupResources(Integer page,List<Integer> typeList,Integer groupId) {
			List<Resource> resources = resourceDAO.getGroupResources(page,typeList,groupId);
			return resources;
		}
		
	// 通过群组id和资源类型获取资源总数	
	public Integer getGroupResourcesNum(List<Integer> typeList,Integer groupId) {
		Integer GroupResourcesNum = resourceDAO.getGroupResourcesNum(typeList,groupId);
		return GroupResourcesNum;
	}
	
	// 通过作者id和资源类型获取资源总数	
		public Integer getResourcesNum(List<Integer> typeList,Integer groupId) {
			Integer GroupResourcesNum = resourceDAO.getResourceNum(typeList,groupId);
			return GroupResourcesNum;
		}
		
}
