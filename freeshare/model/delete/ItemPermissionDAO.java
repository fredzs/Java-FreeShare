package com.free4lab.freeshare.model.delete;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.ThreadEntityManagerHelper;
/**
 * 
 * @author wenlele
 *
 */
@Deprecated
public class ItemPermissionDAO extends AbstractDAO<ItemPermission> {

	public String getClassName(){
		return getEntityClass().getName();
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return ItemPermission.class;
	}

	private static String PU_NAME = "FreeSharePU";
	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new ThreadEntityManagerHelper();
	}


	
	/* 其他方法 */
	public void setReadWritePermission(Integer itemId, Integer teamId){
		ItemPermission ip = findPermission(itemId, teamId); 
		if (ip == null){		
			ip = new ItemPermission(itemId, teamId,
					ItemPermission.TYPE_READ_WRITE);
			save(ip);
		}else{
			ip.setType(ItemPermission.TYPE_READ_WRITE);
			update(ip);
		}
	}
	
	public void setReadOnlyPermission(Integer itemId, Integer teamId){
		ItemPermission ip = findPermission(itemId, teamId); 
		if (ip == null){		
			ip = new ItemPermission(itemId, teamId,
					ItemPermission.TYPE_READONLY);
			save(ip);
		}else{
			ip.setType(ItemPermission.TYPE_READONLY);
			update(ip);
		}
	}
	
	private ItemPermission findPermission(Integer itemId, Integer teamId){
		final String qlString ="select model from " +
				getClassName() + " model where " +
				"model.itemId = :itemId  and " +
				"model.teamId = :teamId";
		Query query = getEntityManager().createQuery(qlString);
		query.setParameter("itemId", itemId);
		query.setParameter("teamId", teamId);
		try {
			ItemPermission tmp= new ItemPermission();
			try{
				tmp = (ItemPermission)query.getSingleResult();
			}
			catch(Exception e){
				//log(e.getMessage(), Level.INFO, e);
				return null;
			}
			return tmp;
		} catch (Exception e) {
			log(e.getMessage(), Level.INFO, e);
			return null;
		}
	}
	public boolean deletePermission(Integer itemId, Integer teamId){
		EntityManager em = getEntityManager();
		try{
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        em.getTransaction().begin();
			final String qlString = "delete from " + 
					getClassName() + " model where " +
					"model.itemId = :itemId and model.teamId = :teamId"; 
			Query query = em.createQuery(qlString);
			query.setParameter("itemId", itemId);
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
	public boolean deleteGroupItemPermission(Integer teamId){
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
	public boolean isPublic(Integer itemId){
		log("check if a item is public : item id is " + itemId 
				+ " @" + getClassName(), Level.INFO, null);
		final String qlString = "select model from " + 
				getClassName() + " model where " +
				"model.itemId = :itemId and model.teamId = :teamId"; 
		Query query = getEntityManager().createQuery(qlString);
		query.setParameter("itemId", itemId);
		query.setParameter("teamId", new Integer(0));
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
	public boolean isReadable(Integer itemId, Integer teamId){
		log("check if a item is public : item id is " + itemId 
				+ " @" + getClassName(), Level.INFO, null);
		final String qlString = "select model from " + 
				getClassName() + " model where " +
				"model.itemId = :itemId and model.type = :type and " +
				"model.teamId = :teamId"; 
		Query query = getEntityManager().createQuery(qlString);
		query.setParameter("itemId", itemId);
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
	public List<Integer> findWritableTeams(Integer itemId){
		log("find writable teams of item " + itemId 
				+ "@" + getClassName(), Level.INFO, null);
		try {
			final String qlString = "select model.teamId from " + 
				getClassName() + " model where " +
				"model.itemId = :itemId and model.type = :type"; 
			Query query = getEntityManager().createQuery(qlString);
			query.setParameter("itemId", itemId);
			query.setParameter("type", ItemPermission.TYPE_READ_WRITE);
			return (List<Integer>)query.getResultList();			
		} catch (RuntimeException re) {
			log(re.getMessage(),Level.INFO,re);
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> findReadableTeams(Integer itemId){
		log("find writable teams of item " + itemId 
				+ "@" + getClassName(), Level.INFO, null);
		try {
			final String qlString = "select model.teamId from " + 
				getClassName() + " model where " +
				"model.itemId = :itemId and model.type = :type and model.teamId != ?1"; 
			Query query = getEntityManager().createQuery(qlString);
			query.setParameter("itemId", itemId);
			query.setParameter(1, new Integer(0));
			query.setParameter("type", ItemPermission.TYPE_READONLY);
			return (List<Integer>)query.getResultList();
		} catch (RuntimeException re) {
			log(re.getMessage(), Level.INFO, re);
			return Collections.emptyList();
		}
	}
}
