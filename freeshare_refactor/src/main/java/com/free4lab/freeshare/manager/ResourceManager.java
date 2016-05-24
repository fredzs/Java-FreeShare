/**
 * 
 */
package com.free4lab.freeshare.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.free4lab.freeshare.manager.factory.ResourceFactory;
import com.free4lab.freeshare.model.dao.DocVersion;
import com.free4lab.freeshare.model.dao.DocVersionDAO;
import com.free4lab.freeshare.model.dao.ListRelation;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.model.dao.ResourceDAO;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

/**
 * @author wenlele
 *when to add the resource(item) into a list or lists,
 *we need to create a relation between item and list.
 *
 */
public class ResourceManager {

	private static final Logger logger = Logger.getLogger(ResourceManager.class);

	private static ResourceFactory resourceFactory = null;
	
	private synchronized ResourceFactory getResourceFactory(){
		if(resourceFactory == null){
			resourceFactory = ResourceFactory.getInstance();
		}
		return resourceFactory;
	}
	public ResourceWrapper readResourceWrapper(Integer resourceId){
		Resource resource = readResource(resourceId);
		return new ResourceWrapper(resource);
	}
	
	public Integer getGroupResourceWrapperNum(List<Integer> typeList,Integer groupId){
		Integer result;
		result = getResourceFactory().getGroupResourcesNum(typeList, groupId);
		
		return result;
	}
	
	public Integer getResourceWrapperNum(List<Integer> typeList,Integer authorId){
		Integer result;
		result = getResourceFactory().getResourcesNum(typeList, authorId);
		
		return result;
	}
	
	public List<ResourceWrapper> readResourceWrapper(Integer page,List<Integer> typeList,Integer authorId){
		List<ResourceWrapper> result = new ArrayList<ResourceWrapper>();
		List<Resource> resources = getResourceFactory().getResources(page,typeList, authorId);
		for(Resource re : resources){
			result.add(new ResourceWrapper(re));
		}
		return result;
	}
	
	public List<ResourceWrapper> readGroupResourceWrapper(Integer page,List<Integer> typeList,Integer groupId){
		List<ResourceWrapper> result = new ArrayList<ResourceWrapper>();
		List<Resource> resources = getResourceFactory().getGroupResources(page,typeList, groupId);
		for(Resource re : resources){
			result.add(new ResourceWrapper(re));
		}
		return result;
	}
	
	
	//创建资源，并返回创建的对象。
	public Resource createResource(Resource resource){
		return getResourceFactory().createResource(resource);
	}
	//更新资源
	public Resource updateResource(Resource resource){
		return getResourceFactory().updateResource(resource);
	}
	//根据id，删除资源。
	public boolean deleteResource(Integer resourceId){
		return getResourceFactory().deleteResource(resourceId);
	}
	//根据资源id，读取一个资源，并返回。
	public Resource readResource(Integer resourceId){
		return getResourceFactory().getResource(resourceId);
	}
	//根据多个资源id，读取多个资源，并返回。
	public List<Resource> readMutilResource(List<Integer> resourceIds){
		return getResourceFactory().getMutilResource(resourceIds);
	}
	
	
	//通过resourceId获取文档的版本信息
	public List<DocVersion> getDocVersions(Integer resourceId){
		return new DocVersionDAO().getVersionByResourceId(resourceId);
	}
	
	public Boolean setDocVersions(Integer userId, String uuid, String description,Integer itemId){
	    Boolean success = false;
        try {
            new DocVersionDAO().create(userId,  uuid,  description, itemId);
            logger.info(userId+uuid+description+itemId);
            success = true;
            return success;
        }
        catch (Exception e) {
            // TODO: handle exception
            return success;
        }
    }
	
	public Boolean editDocVersions(String uuid,String description){
		Boolean success = false;
		try{
			new DocVersionDAO().editDocDescByUuid(description, uuid);
			success = true ; 
			return success;
		}catch (Exception e) {
            // TODO: handle exception
            return success;
        }
		
	}
	
	
//	public static void main(String[] args){
//		Integer type = 0;
//		String name = "shuangtest2";
//		String description = "description";
//		Integer authorId = 78;
//		String attachment = "attachment";
//		ResourceManager rManager = new ResourceManager();
//	}
	
}
