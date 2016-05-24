package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.free4lab.freeshare.model.dao.Relation;
import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.ThreadEntityManagerHelper;

public class RelationDAO extends AbstractDAO<Relation> {

	public Class<Relation> getEntityClass() {
		return Relation.class;
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
	public Relation findRelation(Integer itemId, Integer listId){
		log("finding " + getClassName() + " instance：" +
				"find a relation about item" + itemId + "and list" + listId,
				Level.INFO, null);
		try {
			 final String queryString = "select model from " + getClassName()
             + " model where model.itemId = :itemId and " +
             		" model.listId = :listId";
		     Query query = getEntityManager().createQuery(queryString);
		     query.setParameter("itemId", itemId);
		     query.setParameter("listId", listId);
		     Relation tmp = new Relation();
		     try{
		    	 tmp = (Relation) query.getSingleResult();
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
	
	public void createRelation(Integer itemId, Integer listId){
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());		
		Relation relation = new Relation(itemId, listId, currentTime);
		save(relation);
	}
	
	/**
	 * 删除关系
	 * @param itemId item id
	 * @param listId list id
	 * @return true or exception
	 */
	public boolean delRelation(Integer itemId, Integer listId){
		log("finding " + getClassName() + " instance：" +
				"del a relation about item" + itemId + "and list" + listId,
				Level.INFO, null);
		EntityManager em = getEntityManager();
		
		if (em.getTransaction().isActive()){
			em.getTransaction().rollback();
		}
		try {
			deleteByPrimaryKey(findRelation(itemId, listId).getId());			
			return true;
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw e;
		}		
	}

	public int delRelations(String propertyName, Integer value){
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
	private static final String LIST_ID = "listId";
	private static final String ITEM_ID = "itemId";
	public int delRelationsByItem(Integer itemId){		
		Integer delNumber = delRelations(ITEM_ID, itemId);		
		return delNumber;
	}
	public int delRelationsBylist(Integer listId){		
		Integer delNumber = delRelations(LIST_ID, listId);
		return delNumber;
	}
	
	public List<Relation> findByItem(Integer itemId){
		return findByProperty(ITEM_ID, itemId);
	}
	public List<Relation> findByList(Integer listId){
		return findByProperty(LIST_ID, listId); 
	}

}
