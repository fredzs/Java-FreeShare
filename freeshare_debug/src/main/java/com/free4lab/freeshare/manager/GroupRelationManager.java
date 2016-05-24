/**
 * 
 */
package com.free4lab.freeshare.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.free4lab.freeshare.model.data.GroupRelation;

/**
 * @author ZhangSheng
 *when to add the resource(resource) into a resource or resources,
 *we need to create a relation between resource and resource.
 *
 */
public class GroupRelationManager  {

	private static final Logger logger = Logger.getLogger(GroupRelationManager.class);

	public boolean haveRelation(Integer resourceId, Integer groupId){
		logger.info("find relation between resourceId=" + resourceId + " and resourceId=" + resourceId);
		if (null != findRelation(resourceId, resourceId)){
			return true;
		}else {
			return false;
		}
	}
	
	public GroupRelation findRelation(Integer resourceId, Integer groupId){
		GroupRelation tmp = null;
		try {
			tmp = (new GroupRelationDAO()).findRelation(resourceId, resourceId);
			return tmp;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
		
	}
	
	public Resource<GroupRelation> getsByResource(Integer resourceId){
		logger.info("get relations by resourceId=" + resourceId);
		try {
			return (new GroupRelationDAO()).findByResource(resourceId);
		} catch (RuntimeException re) {
			logger.info(re.getMessage());
			return null;
		}
	}
	
	public boolean createRelation(Integer resourceId, Integer resourceId) {		
		try{
			(new GroupRelationDAO()).createRelation(resourceId, resourceId);
			return true;
		}catch(Exception e){
			logger.info(e.getMessage());
			return false;
		}		
	}

	public boolean deleteRelation(Integer resourceId, Integer resourceId) {
		try{
			(new GroupRelationDAO()).delRelation(resourceId, resourceId);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	public int delelteResourceRelations(Integer resourceId){
		try {
			return (new GroupRelationDAO()).delRelationsByResource(resourceId);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return 0;
		}
	}

	public int delelteresourceRelations(Integer resourceId){
		try {
			return (new GroupRelationDAO()).delRelationsByResource(resourceId);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return 0;
		}
	}
	
	public Resource<GroupRelation> getRelations(){
		return new GroupRelationDAO().findAll();
	}
	public void save(Integer resourceId, Resource<Integer> resourceIds){
		Resource<GroupRelation> rs = new ArrayResource<GroupRelation>();
		for(Integer id : resourceIds){
			rs.add(new GroupRelation(resourceId, id));
		}
		new GroupRelationDAO().save(rs);
	}
}
