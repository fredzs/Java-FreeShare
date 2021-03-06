package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.free4lab.freeshare.util.TimeUtil;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.ThreadEntityManagerHelper;

public class ListRelationDAO extends AbstractDAO<ListRelation> {
	private static final String LIST_ID = "listId";
	private static final String RESOURCE_ID = "resourceId";
	public Class<ListRelation> getEntityClass() {
		return ListRelation.class;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new ThreadEntityManagerHelper();
	}

	public static final String PU_NAME = "FreeSharePU";
	public String getPUName() {
		return PU_NAME;
	}

	public String getClassName(){
		return getEntityClass().getName();
	}	
	/*add other methods behind*/
	
	/**
	 * 寻找关系
	 * @param itemId item id
	 * @param listId list id
	 * @return relation
	 */
	public ListRelation findRelation(Integer resourceId, Integer listId){
		log("finding " + getClassName() + " instance：" +
				"find a relation about resource" + resourceId + "and list" + listId,
				Level.INFO, null);
		try {
			 final String queryString = "select model from " + getClassName()
             + " model where model.resourceId = :resourceId and " +
             		" model.listId = :listId";
		     Query query = getEntityManager().createQuery(queryString);
		     query.setParameter("resourceId", resourceId);
		     query.setParameter("listId", listId);
		     ListRelation tmp;
		     try{
		    	 tmp = (ListRelation) query.getResultList().get(0);
		    	 return tmp;
		     }
		     catch(NoResultException re){
		    	log("find relation failed", Level.INFO, re);
		    	return null;
		     }
		    
		} catch (RuntimeException re) {
			log("find relation failed", Level.INFO, re);
			//throw re;
			return null;
		}		
	}
	
	/*根据列表id与资源在列表中的次序寻找relation*/
	private ListRelation findRelationByOrder(Integer resource_order, Integer listId){
		log("finding " + getClassName() + " instance：" +
				"find a relation about resource" + resource_order + "and list" + listId,
				Level.INFO, null);
		try {
			 final String queryString = "select model from " + getClassName()
             + " model where model.resourceOrder = :resourceOrder and " +
             		" model.listId = :listId";
		     Query query = getEntityManager().createQuery(queryString);
		     query.setParameter("resourceOrder", resource_order);
		     query.setParameter("listId", listId);
		     ListRelation tmp;
		     try{
		    	 tmp = (ListRelation) query.getResultList().get(0);
		    	 return tmp;
		     }
		     catch(NoResultException re){
		    	log("find relation failed1", Level.INFO, re);
		    	return null;
		     }
		    
		} catch (RuntimeException re) {
			log("find relation failed2", Level.INFO, re);
			//throw re;
			return null;
		}		
	}
	
	public Integer findCurrentOrder( Integer listId){
		log("finding " + getClassName() + " instance：" +
				"find a relation about list" + listId,
				Level.INFO, null);
		try {
			 final String queryString = "select model from " + getClassName()
             + " model where model.listId = :listId" ;
		     Query query = getEntityManager().createQuery(queryString);
		     //query.setParameter("resourceId", resourceId);
		     query.setParameter("listId", listId);
		    
		     try{
		    	 //tmp = (ListRelation) query
		    	 return query.getResultList().size();
		    	
		     }
		     catch(NoResultException re){
		    	log("find relation failed", Level.INFO, re);
		    	return 0;
		     }
		    
			} catch (RuntimeException re) {
			log("find relation failed", Level.INFO, re);
			//throw re;
			return null;
		}		
	}
	public void createRelation(Integer resourceId, Integer listId){
		Integer order = findCurrentOrder(listId);
		ListRelation relation = new ListRelation(resourceId, listId,order+1);
		save(relation);
	}
	/**
	 * 修改列表中资源次序，两资源互换
	 * @param listId,item_order,target_order
	 * @return null
	 */
	public boolean changeOrderInList(Integer resource_id,Integer target_order,Integer listId){
			log("finding " + getClassName() + " instance: update a relationRecord",
	    			Level.INFO, null);
			ListRelation relation1 = findRelationByOrder(target_order,listId);
			ListRelation relation2 = findRelation(resource_id,listId);
			if(relation1==null || relation2 == null){
				return false;
			}
			ListRelation new_relation1 = new ListRelation();
			new_relation1.setId(relation1.getId());
			new_relation1.setListId(relation1.getListId());
			new_relation1.setResourceId(relation1.getResourceId());
			new_relation1.setResourceOrder(relation2.getResourceOrder());
			new_relation1.setCreateTime(relation1.getCreateTime());
			update(new_relation1);
			
			ListRelation new_relation2 = new ListRelation();
			new_relation2.setId(relation2.getId());
			new_relation2.setListId(relation2.getListId());
			new_relation2.setResourceId(relation2.getResourceId());
			new_relation2.setResourceOrder(target_order);
			new_relation2.setCreateTime(relation2.getCreateTime());
			update(new_relation2);
			
		    return false;
	}
	/**
	 * 删除关系
	 * @param itemId item id
	 * @param listId list id
	 * @return true or exception
	 */
	public boolean deleteRelation(Integer resourceId, Integer listId){
		log("finding " + getClassName() + " instance：" +
				"del a relation about resource" + resourceId + "and list" + listId,
				Level.INFO, null);
		EntityManager em = getEntityManager();
		
		if (em.getTransaction().isActive()){
			em.getTransaction().rollback();
		}
		try {
			deleteByPrimaryKey(findRelation(resourceId, listId).getId());	
			return true;
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw e;
		}		
	}

	public int deleteRelations(String propertyName, Integer value){
		log("finding" + getClassName() + " instance: " +
				"del relations by " + propertyName + "=" + value, Level.INFO, null);
		EntityManager em = getEntityManager();
		if (em.getTransaction().isActive()){
			em.getTransaction().rollback();
		}
		em.getTransaction().begin();
		try {
			final String queryString = "delete from " + getClassName() + 
				" model where model." + propertyName + " = :propertyValue";
			Query query = em.createQuery(queryString);
			query.setParameter("propertyValue", value);
			int ans = query.executeUpdate();
			em.getTransaction().commit();
			logger.info("del " + ans + "relations by "+ propertyName +" = " 
					+ value);
			return ans;
		} catch (RuntimeException re) {
			em.getTransaction().rollback();
			log("del relations by " + propertyName + "=" + value + " failed!",
					Level.INFO, re);
			throw re;
		}
	}

	public int deleteRelationsByResource(Integer resourceId){		
		Integer delNumber = deleteRelations(RESOURCE_ID, resourceId);		
		return delNumber;
	}
	public int deleteRelationsByList(Integer listId){		
		Integer delNumber = deleteRelations(LIST_ID, listId);
		return delNumber;
	}
	
	public List<ListRelation> findByResource(Integer resourceId){
		return findByProperty(RESOURCE_ID, resourceId);
	}
	public List<ListRelation> findByList(Integer listId){
		//return findByProperty(LIST_ID, listId); 
		try {
			 final String queryString = "select model from " + getClassName()
            + " model where model.listId = :listId order by model.resourceOrder" ;
		     Query query = getEntityManager().createQuery(queryString);
		     query.setParameter("listId", listId);
		     
		     try{
		    	 List<ListRelation> result = query.getResultList();
		    	 return  result;
		     }
		     catch(NoResultException re){
		    	log("find relation failed", Level.INFO, re);
		    	return null;
		     }
		    
			} catch (RuntimeException re) {
			log("find relation failed", Level.INFO, re);
			return null;
		    }		
	}
	public static void  main(String[] arg) {
		/*ListRelationDAO dao = new ListRelationDAO();
		Integer resourceId = 909;
		Integer listId = 93;
		//System.out.println(dao.findByResource(resourceId).get(0).getListId());
		//System.out.println(dao.findByList(listId).get(0).getResourceId());
		System.out.println(dao.findCurrentOrder(listId));
		//dao.createRelation(resourceId,listId);
		//dao.deleteRelation(resourceId,listId);
		//dao.deleteRelations(LIST_ID,listId);
		//dao.changeOrderInList(54321,77, 9);
*/	}

}
