package com.free4lab.freeshare.model.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.*;

import com.free4lab.utils.sql.*;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * @author zhangsheng
 *
 */
public class DocVersionDAO extends AbstractDAO<DocVersion> {

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return DocVersion.class;
	}

	public String getClassName() {
		return getEntityClass().getName();
	}

	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		// TODO Auto-generated method stub
		return new NoCacheEntityManagerHelper();
	}

	public static final String PU_NAME = "FreeSharePU";

	@Override
	public String getPUName() {
		// TODO Auto-generated method stub
		return PU_NAME;
	}

	// 在数据库中插入一条文档版本
	public DocVersion create(Integer userId, String uuid, String description,Integer itemId) {
		log("finding " + getClassName() + " instance: create a docversion",
				Level.INFO, null);

		DocVersion docversion = new DocVersion();
		docversion.setUserId(userId);
		docversion.setUuid(uuid);
		docversion.setDescription(description);
		docversion.setItemId(itemId);
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		docversion.setEditTime(currentTime);
		save(docversion);
		return docversion;
	}

	/**
	 * 通过uuid删除文档版本
	 */

	public boolean deleteByUUid(String uuid) {
		log("finding" + getClassName() + " instance: "
				+ "del docversion by uuid" + uuid, Level.INFO, null);
		EntityManager em = getEntityManager();
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		em.getTransaction().begin();
		try {

			final String queryString = "delete from " + getClassName()
					+ " model where model.uuid = :uuid";
			Query query = em.createQuery(queryString);
			query.setParameter("uuid", uuid);

			query.executeUpdate();
			em.getTransaction().commit();
			return true;
		} catch (RuntimeException re) {
			log("del records by uuid and editTime = " + uuid + " failed!",
					Level.INFO, re);
			em.getTransaction().rollback();
			return false;
		}

	}

	// 查找所有的文档版本
	public List<DocVersion> findAll() {
		return super.findAll();
	}
	
	@SuppressWarnings("unchecked")
    public List<DocVersion> getVersionByResourceId(Integer resourceId){
		try {
			final String qlString = "select model from "+ getClassName()+" model WHERE model.itemId = :itemid order by model.editTime";
			Query query = getEntityManager().createQuery(qlString);
	        query.setParameter("itemid", resourceId);
	        return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
		
	   // 在数据库中插入一条文档版本
    public Integer updateUuidAfterDelete(Integer item_id) {
        final String qlString = "select max(id) from "+ getClassName()+" model2 WHERE model2.itemId = :itemid";
        Query query = getEntityManager().createQuery(qlString);
        query.setParameter("itemid", item_id);
        return (Integer)query.getSingleResult();
    }
    
    public DocVersion editDocDescByUuid(String description,String uuid){
    	List<DocVersion> docs = null;
    	DocVersion doc = null;
		try {
			docs = new DocVersionDAO().findByProperty("uuid", uuid);
			if(docs != null){
				doc = docs.get(0);
				doc.setDescription(description);
				update(doc);
			}
		} catch (Exception e) {
			return null;
		}
		return doc;
    }
	
}
