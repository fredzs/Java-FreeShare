package com.free4lab.freeshare.model.dao;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.python.antlr.PythonParser.return_stmt_return;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.ThreadEntityManagerHelper;
/**
 * 
 *
 *
 */
public class ResourcePermissionDAO extends AbstractDAO<ResourcePermission> {

	public String getClassName(){
		return getEntityClass().getName();
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return ResourcePermission.class;
	}

	private static String PU_NAME = "FreeSharePU";
	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new ThreadEntityManagerHelper();
	}

	
	/* 其他方法 */
	
	//设置资源和群组关系为可读写
	public void setReadWritePermission(Integer resourceId, Integer groupId){
		setPermission(resourceId,groupId,ResourcePermission.TYPE_READ_WRITE);
	}
	//设置资源和群组关系为仅可读
	public void setReadOnlyPermission(Integer resourceId, Integer groupId){
		setPermission(resourceId,groupId,ResourcePermission.TYPE_READ_ONLY);
		
	}
	//内部方法:设置资源和群组的权限
	private void setPermission(Integer resourceId, Integer groupId,String permission){
		ResourcePermission rp = obtainPermission(resourceId, groupId); 
		if (rp == null){		
			rp = new ResourcePermission(resourceId, groupId,permission);
			save(rp);
		}
		else{
			rp.setPermissionType(permission);
			update(rp);
		}
	}
	//获取资源和群组的权限关系。
	private ResourcePermission obtainPermission(Integer resourceId, Integer groupId){
		final String qlString ="select model from " +
				getClassName() + " model where " +
				"model.resourceId = :resourceId  and " +
				"model.groupId = :groupId";
		Query query = getEntityManager().createQuery(qlString);
		query.setParameter("resourceId", resourceId);
		query.setParameter("groupId", groupId);
		try {
			ResourcePermission result= new ResourcePermission();
			try{
				result = (ResourcePermission)query.getSingleResult();
			}
			catch(Exception e){
				//log(e.getMessage(), Level.INFO, e);
				return null;
			}
			return result;
		} catch (Exception e) {
			log(e.getMessage(), Level.INFO, e);
			return null;
		}
	}
	//删除资源和群组的权限关系。
	public boolean deletePermission(Integer resourceId, Integer groupId){
		log("deletePermission " + resourceId 
				+ "@" + getClassName(), Level.INFO, null);
		EntityManager em = getEntityManager();
		try{
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        em.getTransaction().begin();
			final String qlString = "delete from " + 
					getClassName() + " model where " +
					"model.resourceId = :resourceId and model.groupId = :groupId"; 
			Query query = em.createQuery(qlString);
			query.setParameter("resourceId", resourceId);
			query.setParameter("groupId", groupId);
			query.executeUpdate();
			em.getTransaction().commit();
			return true;
		}catch(Exception e){
			em.getTransaction().rollback();
			log(e.getMessage(),Level.INFO,e);
			return false;
		}
	}
	public boolean deletePermissionByGroupId(Integer groupId){
		log("deletePermissionByGroupId " + groupId 
				+ "@" + getClassName(), Level.INFO, null);
		EntityManager em = getEntityManager();
		try{
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        em.getTransaction().begin();
			final String qlString = "delete from " + 
					getClassName() + " model where model.groupId = :groupId"; 
			Query query = em.createQuery(qlString);
			query.setParameter("groupId", groupId);
			query.executeUpdate();
			em.getTransaction().commit();
			return true;
		}catch(Exception e){
			em.getTransaction().rollback();
			log(e.getMessage(),Level.INFO,e);
			return false;
		}
	}
	//检查一个资源是否属于公开资源，公开的资源群组id = 0。
	public boolean isTypePublic(Integer resourceId){
		log("check if a resource is public : resource id is " + resourceId 
				+ " @" + getClassName(), Level.INFO, null);
		final String qlString = "select model from " + 
				getClassName() + " model where " +
				"model.resourceId = :resourceId and model.groupId = :groupId"; 
		Query query = getEntityManager().createQuery(qlString);
		query.setParameter("resourceId", resourceId);
		query.setParameter("groupId", new Integer(0));
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
	//内部调用检查资源是否属于一个群组。
	private boolean isBelongToGroup(Integer resourceId,Integer groupId,String permission){
		log("check if a resource is public : resource id is " + resourceId 
				+ " @" + getClassName(), Level.INFO, null);
		final String qlString = "select model from " + 
				getClassName() + " model where " +
				"model.resourceId = :resourceId and model.permissionType = :permissionType and " +
				"model.groupId = :groupId"; 
		Query query = getEntityManager().createQuery(qlString);
		query.setParameter("resourceId", resourceId);
		query.setParameter("permissionType", permission);
		query.setParameter("groupId", groupId);
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
	
	//检查资源是否属于一个可读写群组。
	public boolean isReadWriteGroup(Integer resourceId, Integer groupId){
			log("check if a resource is ReadWriteGroup: resource id is " + resourceId 
					+ " @" + getClassName(), Level.INFO, null);
			return isBelongToGroup(resourceId,groupId,ResourcePermission.TYPE_READ_WRITE);
	}
	//检查资源是否属于一个可读群组。
	public boolean isReadOnlyGroup(Integer resourceId, Integer groupId){
		log("check if a resource is public : resource id is " + resourceId 
				+ " @" + getClassName(), Level.INFO, null);
		return isBelongToGroup(resourceId,groupId,ResourcePermission.TYPE_READ_ONLY);
		
	}
	
	private List<Integer> getGroups(Integer resourceId,String permission){
		log("find groups of resource " + resourceId 
				+ "@" + getClassName(), Level.INFO, null);
		try {
			final String qlString = "select model.groupId from " + 
				getClassName() + " model where " +
				"model.resourceId = :resourceId and model.permissionType = :permissionType"; 
			Query query = getEntityManager().createQuery(qlString);
			query.setParameter("resourceId", resourceId);
			query.setParameter("permissionType", permission);
			return (List<Integer>)query.getResultList();			
		} catch (RuntimeException re) {
			log(re.getMessage(),Level.INFO,re);
			return Collections.emptyList();
		}
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getWritableGroups(Integer resourceId){
		log("find writable teams of resource " + resourceId 
				+ "@" + getClassName(), Level.INFO, null);
		return getGroups(resourceId,ResourcePermission.TYPE_READ_WRITE);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getReadableGroups(Integer resourceId){
		log("find readable teams of resource " + resourceId 
				+ "@" + getClassName(), Level.INFO, null);
		return getGroups(resourceId,ResourcePermission.TYPE_READ_ONLY);
		
	}
	
	public static void main(String[] args){
		ResourcePermissionDAO rpDao = new ResourcePermissionDAO();
		Integer resourceId = 909;
		Integer groupId = 95;
		Integer groupId2 = 909;
		//System.out.println(rpDao.getWritableGroups(resourceId).get(0));
		//rpDao.setReadOnlyPermission(resourceId, groupId);
		//rpDao.setReadWritePermission(resourceId, groupId);
		//rpDao.setReadOnlyPermission(resourceId, groupId2);
		//rpDao.deletePermission(resourceId, groupId2);
		//rpDao.deletePermissionByGroupId(909);
		System.out.println(rpDao.isTypePublic(436));
		System.out.println(rpDao.isReadOnlyGroup(resourceId, groupId));
		System.out.println(rpDao.isReadWriteGroup(resourceId, groupId));
		//System.out.println(rpDao.getReadableTeams(resourceId).get(0));
	}
}
