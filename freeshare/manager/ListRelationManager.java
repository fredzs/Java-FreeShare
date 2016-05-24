/**
 * 
 */
package com.free4lab.freeshare.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.manager.factory.ResourceFactory;
import com.free4lab.freeshare.model.dao.ListRelation;
import com.free4lab.freeshare.model.dao.ListRelationDAO;
import com.free4lab.freeshare.model.dao.ResourceDAO;

/**
 * @author wenlele
 *when to add the resource(item) into a list or lists,
 *we need to create a ListRelation between item and list.
 *
 */
/*单例形式创建,使用getInstance获取到实例*/
public class ListRelationManager  {

	private static final Logger logger = Logger.getLogger(ListRelationManager.class);

	private static ListRelationManager manager = null;
	private static ListRelationDAO listRelationDAO = null;
	//
	public static ListRelationManager getInstance(){
		if(manager == null){
			manager = new ListRelationManager();
			listRelationDAO = new ListRelationDAO();
		}
		return manager;
	}
	public boolean isInList(Integer resourceId, Integer listId){
		logger.info("find ListRelation between resourceId=" + resourceId + " and listId=" + listId);
		if (null != obtainListRelation(resourceId, listId)){
			return true;
		}else {
			return false;
		}
	}
	
	public ListRelation obtainListRelation(Integer resourceId, Integer listId){
		ListRelation result = null;
		try {
			result = listRelationDAO.findRelation(resourceId, listId);
					
			return result;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
		
	}
	
	public List<ListRelation> obtainRelationByResource(Integer resourceId){
		logger.info("get ListRelations by resourceId=" + resourceId);
		try {
			return listRelationDAO.findByResource(resourceId);
					
		} catch (RuntimeException re) {
			logger.info(re.getMessage());
			return null;
		}
	}
	public List<ListRelation> obtainRelationByList(Integer listId){
		logger.info("get ListRelations by listId=" + listId);
		try {
			return listRelationDAO.findByList(listId);
					
		} catch (RuntimeException re) {
			logger.info(re.getMessage());
			return null;
		}
	}
	
	public boolean createListRelation(Integer resourceId, Integer listId) {		
		try{
			listRelationDAO.createRelation(resourceId, listId);
			return true;
		}catch(Exception e){
			logger.info(e.getMessage());
			return false;
		}		
	}

	public boolean deleteListRelation(Integer resourceId, Integer listId) {
		try{
			listRelationDAO.deleteRelation(resourceId, listId);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	public int deleteResourceRelations(Integer resourceId){
		try {
			return listRelationDAO.deleteRelationsByResource(resourceId);
					
		} catch (Exception e) {
			logger.info(e.getMessage());
			return 0;
		}
	}
	public int deleteListRelations(Integer listId){
		try {
			return listRelationDAO.deleteRelationsByList(listId);
					
		} catch (Exception e) {
			logger.info(e.getMessage());
			return 0;
		}
	}
/*	public List<ListRelation> getListRelations(){
		return listRelationDAO.findAll();
	}*/
	public void saveListsOfResource(Integer resourceId, List<Integer> listIds){
		List<ListRelation> rs = new ArrayList<ListRelation>();
		for(Integer id : listIds){
			rs.add(new ListRelation(resourceId, id));
		}
		try{
			listRelationDAO.save(rs);
		}
		catch(Exception e){
			logger.info(e.getMessage());
		}
	}
	//TODO:
	//修改资源在列表中的次序。
	public boolean changeOrderInList(Integer resourceId,Integer listId,Integer order) {
		return true;
	}
	public static void main(String[] arg){
		Integer resourceId = 100000;
		Integer listId = 100000;
		ListRelationManager test = ListRelationManager.getInstance();
		test.createListRelation(resourceId, listId);
		System.out.println(test.obtainListRelation(resourceId, listId).getResourceId());
		System.out.println(test.obtainRelationByResource(resourceId).get(0).getListId());
		test.deleteListRelation(resourceId, listId);
		test.deleteRelations(resourceId);
	}
}
