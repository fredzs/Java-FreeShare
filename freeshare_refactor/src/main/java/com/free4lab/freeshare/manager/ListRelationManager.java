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
	//TOOD 增加一个方法实例化  manager、 listRelationDAO 
	private static ListRelationManager manager = null;
	private static ListRelationDAO listRelationDAO = null;
	//
	private static ListRelationManager getInstance(){
		if(manager == null){
			manager = new ListRelationManager();
			listRelationDAO = new ListRelationDAO();
		}
		return manager;
	}
	
	//创建一个资源与列表的关系。
		public boolean createListRelation(Integer resourceId,Integer listId){
			getInstance();
			listRelationDAO.createRelation(resourceId, listId);
			return true;
		}
		//通过资源id和列表id获取它们的关系。
		public ListRelation obtainRelation(Integer resourceId,Integer listId){
			getInstance();
			return listRelationDAO.findRelation(resourceId, listId);
		}
		//根据资源的id获取资源所在的列表和资源关系。
		public List<ListRelation> obtainRelationByResource(Integer resourceId){
			getInstance();
			return listRelationDAO.findByResource(resourceId);
		}
		//通过列表的id获取该列表和资源关系。
		public List<ListRelation> obtainRelationByList(Integer listId){
			getInstance();
			return listRelationDAO.findByList(listId);
		}
		//判断资源是否在列表中。
		public boolean isListRelation(Integer resourceId,Integer listId){
			getInstance();
			if(null != listRelationDAO.findRelation(resourceId, listId)){
				return true;
			}
			else{
				return false;
			}
		}
		//通过资源id和列表id删除资源和列表的关系。
		public boolean deleteRelation(Integer resourceId, Integer listId){
			getInstance();
			return listRelationDAO.deleteRelation(resourceId, listId);
		}
		//通过资源id删除该资源和列表的关系。
		public int deleteResourceRelations(Integer resourceId){	
			getInstance();
			return listRelationDAO.deleteRelationsByResource(resourceId);
			
		}
		//通过列表id删除资源和列表的关系。
		public int deleteListRelations(Integer listId){	
			getInstance();
			return listRelationDAO.deleteRelationsByList(listId);
			
		}
	public void saveListsOfResource(Integer resourceId, List<Integer> listIds){
		getInstance();
		List<ListRelation> rs = new ArrayList<ListRelation>();
		for(Integer id : listIds){
			try{
				listRelationDAO.createRelation(resourceId, id);
			}
			catch(Exception e){
				logger.info(e.getMessage());
			}
		}
		
	}
	//修改资源在列表中的次序。
	public boolean changeOrderInList(Integer resource_id,Integer target_order,Integer listId) {
		try{
			listRelationDAO.changeOrderInList(resource_id,target_order,listId);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	/*public static void main(String[] arg){
		Integer target_order = 9;
		Integer resource_order = 7;
		Integer listId = 54321;
		List<Integer> listIds = new ArrayList();
		listIds.add(6115);
		//boolean test=ListRelationManager.manager.changeOrderInList(listId,resource_order,target_order);
		ListRelationManager test = new ListRelationManager();
		test.saveListsOfResource(6114, listIds);
		System.out.println("加入成功！！！！！");
	}*/
}
