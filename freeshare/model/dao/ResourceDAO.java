package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.python.antlr.PythonParser.return_stmt_return;


import com.free4lab.freeshare.util.TimeUtil;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;



public class ResourceDAO extends AbstractDAO<Resource>
{

	public String getClassName() {
		return getEntityClass().getName();
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return Resource.class;
	}

	public static final String PU_NAME = "FreeSharePU";

	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}
	
	/*创建一个资源，同时将创建的资源返回。*/
	public Resource create(Resource resource){
		log("finding " + getClassName() + " instance: create a item",
				Level.INFO, null);
		Timestamp currentTime = TimeUtil.getCurrentTime();
		resource.setCreateTime(currentTime);
		resource.setRecentEditTime(currentTime);
		try{
			save(resource);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			return null;
		}
		return resource;
	}
	
	/*更新一个资源。*/
    public boolean updateResource(Resource resource){
    	Resource newResource = findByPrimaryKey(resource.getId());
    	resource.setCreateTime(newResource.getCreateTime());	
    	resource.setRecentEditTime(TimeUtil.getCurrentTime());
    	resource.setAuthorId(newResource.getAuthorId());
    	//TODO: 作者可能要修改
    	try{
    		update(resource);
    	}
    	catch(Exception e){
			logger.info(e.getMessage());
			return false;
		}
    	return true;
    }
    
    /*删除一个资源。*/
    public boolean deleteResource(Integer id) {
		try{
			deleteByPrimaryKey(id);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			return false;
		}
		return true;
	}
    //通过id获取单个资源
    public Resource getResource(Integer resourceId){
    	Resource resource = null;
    	try{
    		resource = findById(resourceId);
    	}
    	catch(Exception e){
    		logger.info(e.getMessage());
			return null;
    	}
    	return resource;
    }
    //TODO:没有测试过
    /*获取多个资源*/
    public List<Resource> getMutilResource(List<Integer> resourceIds){
    	List<Resource> resources = new LinkedList<Resource>();
    	try{
    		for(Integer resourceId:resourceIds){
    			resources.add(findById(resourceId));
    		}
    	}
    	catch (Exception e) {
    		logger.info(e.getMessage());
			return null;
		}
    	return resources;
    }
    public static void main(String[] args){
    	Resource resource = new Resource(1, "name", "description", 78,TimeUtil.getCurrentTime(),TimeUtil.getCurrentTime(), "attachment");
    	ResourceDAO dao = new ResourceDAO();
    	Resource copy = dao.create(resource);
    	copy.setName("name2");
    	dao.updateResource(copy);
    	
    	
    }
    
}
