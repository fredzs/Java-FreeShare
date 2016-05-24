package com.free4lab.freeshare.model.dao;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.ThreadEntityManagerHelper;

public class ListPermissionDAO extends AbstractDAO<ListPermission> {

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return ListPermission.class;
	}

	private static final String PU_NAME = "FreeSharePU";
	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new ThreadEntityManagerHelper();
	}
	
	public String getClassName(){
		return getEntityClass().getName();
	}

	@SuppressWarnings("unchecked")
	public List<Integer> findReadableTeams(Integer listId) {
		log("find writable teams of list " + listId 
				+ "@" + getClassName(), Level.INFO, null);
		try {
			final String qlString = "select model.teamId from " + 
				getClassName() + " model where " +
				"model.listId = :listId and model.type = :type and model.teamId != ?1"; 
			Query query = getEntityManager().createQuery(qlString);
			query.setParameter("listId", listId);
			query.setParameter(1, new Integer(0));
			query.setParameter("type", ListPermission.TYPE_READONLY);
			return (List<Integer>)query.getResultList();
			
		} catch (RuntimeException re) {
			log(re.getMessage(), Level.INFO, re);
			return Collections.emptyList();
		}
	}

	public boolean isPublic(Integer listId) {
		log("check if a list is public : list id is " + listId 
				+ " @" + getClassName(), Level.INFO, null);
		try {
			final String qlString = "select model from " + 
				getClassName() + " model where " +
				"model.listId = :listId and " +
				"model.teamId = :teamId and (model.type = :type " +
				"or model.type = :type2)"; 
			Query query = getEntityManager().createQuery(qlString);
			query.setParameter("listId", listId);
			query.setParameter("type", ListPermission.TYPE_READONLY);
			query.setParameter("type2", ListPermission.TYPE_READ_WRITE);
			query.setParameter("teamId", new Integer(0));
			
			query.getSingleResult();
			return true;
		} catch (EntityNotFoundException e) {
			log("has no this entity", Level.INFO, null);
			return false;
		} catch (RuntimeException re) {
			log(re.getMessage(), Level.INFO, null);
			return false;
		}
	}
	public boolean isReadable(Integer listId, Integer teamId){
		log("check if a item is public : item id is " + listId 
				+ " @" + getClassName(), Level.INFO, null);
		final String qlString = "select model from " + 
				getClassName() + " model where " +
				"model.listId = :listId and model.type = :type and " +
				"model.teamId = :teamId"; 
		Query query = getEntityManager().createQuery(qlString);
		query.setParameter("listId", listId);
		query.setParameter("type", ItemPermission.TYPE_READONLY);
		query.setParameter("teamId", teamId);
		try {			
			query.getSingleResult();
			return true;
		} catch (EntityNotFoundException e) {
			log("has no this entity", Level.INFO, null);
			return false;
		} catch (RuntimeException re) {
			log(re.getMessage(), Level.INFO, null);
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	public List<Integer> findWritableTeams(Integer listId){
		log("find writable teams of list " + listId 
				+ "@" + getClassName(), Level.INFO, null);
		try {
			final String qlString = "select model.teamId from " + 
				getClassName() + " model where " +
				"model.listId = :listId and model.type = :type"; 
			Query query = getEntityManager().createQuery(qlString);
			query.setParameter("listId", listId);
			query.setParameter("type", ListPermission.TYPE_READ_WRITE);
			return (List<Integer>)query.getResultList();
			
		} catch (RuntimeException re) {
			log(re.getMessage(), Level.INFO, re);
			return Collections.emptyList();
		}
	}

	public void setReadWritePermission(Integer listId, Integer teamId) {
		ListPermission lp = findPermission(listId, teamId);
		if (lp == null){
			lp = new ListPermission(listId, teamId, 
					ListPermission.TYPE_READ_WRITE);
			save(lp);	
		}else{
			lp.setType(ListPermission.TYPE_READ_WRITE);
			update(lp);
		}	
	}
	
	public void setReadOnlyPermission(Integer listId, Integer teamId){
		log("set ReadOnlyPermission", Level.INFO, null);		
		try {
			ListPermission lp = findPermission(listId, teamId);
			if (lp == null){
				log("not this entity and new one", Level.INFO, null);
				lp = new ListPermission(listId, teamId, 
						ListPermission.TYPE_READONLY);
				save(lp);	
			}else{
				log("not null ", Level.INFO, null);
				lp.setType(ListPermission.TYPE_READONLY);
				update(lp);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private ListPermission findPermission(Integer listId, Integer teamId){		
		log("find permission", Level.INFO, null);
		try {
			final String qlString ="select model from " +
					getClassName() + " model where " +
					"model.listId = :listId  and " +
					"model.teamId = :teamId";
			Query query = getEntityManager().createQuery(qlString);
			query.setParameter("listId", listId);
			query.setParameter("teamId", teamId);
			return (ListPermission) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage(), Level.INFO, e);
			return null;
		}
	}
	public boolean deletePermission(Integer listId, Integer teamId){
		EntityManager em = getEntityManager();
		try{
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        em.getTransaction().begin();
			final String qlString = "delete from " + 
					getClassName() + " model where " +
					"model.listId = :listId and model.teamId = :teamId"; 
			Query query = em.createQuery(qlString);
			query.setParameter("listId", listId);
			query.setParameter("teamId", teamId);
			query.executeUpdate();
			em.getTransaction().commit();
			return true;
		}catch(Exception e){
			em.getTransaction().rollback();
			log(e.getMessage(),Level.INFO,e);
			return false;
		}
	}
	public boolean deleteGroupListPermission(Integer teamId){
		EntityManager em = getEntityManager();
		try{
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        em.getTransaction().begin();
			final String qlString = "delete from " + 
					getClassName() + " model where model.teamId = :teamId"; 
			Query query = em.createQuery(qlString);
			query.setParameter("teamId", teamId);
			query.executeUpdate();
			em.getTransaction().commit();
			return true;
		}catch(Exception e){
			em.getTransaction().rollback();
			log(e.getMessage(),Level.INFO,e);
			return false;
		}
	}
	//added by lzc，查找特定几个组中，有写权限的列表Id
	@SuppressWarnings("unchecked")
	public List<Integer> findWritableLists(List<Integer> teamIds){
		log("find writable lists of teams " + teamIds 
				+ "@" + getClassName(), Level.INFO, null);
		try {
			String qlString = "select distinct model.listId from " + getClassName() + " model where " +
				"model.teamId in (" + teamIds.get(0);
			for(int i = 1; i<teamIds.size(); i++){
				qlString += "," + teamIds.get(i);
			}
			qlString += ") and model.type = :type"; 
			Query query = getEntityManager().createQuery(qlString);
			query.setParameter("type", ListPermission.TYPE_READ_WRITE);
			return (List<Integer>)query.getResultList();
			
		} catch (RuntimeException re) {
			log(re.getMessage(), Level.INFO, re);
			return Collections.emptyList();
		}
	}
}
