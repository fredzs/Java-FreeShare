package com.free4lab.freeshare.model.dao;

import java.util.logging.Level;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class UserLatestListDAO extends AbstractDAO<UserLatestList> {

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return UserLatestList.class;
	}

	public static final String PU_NAME = "FreeSharePU";

	public String getPUName() {
		return PU_NAME;
	}

	public String getClassName() {
		return getEntityClass().getName();
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}
	
	//在数据库中插入一条记录
    public void create(Integer uid, String latestList, String latestGroup){
    	UserLatestList userLatestList = new UserLatestList();
    	userLatestList.setUid(uid);
    	userLatestList.setLatestList(latestList);
    	userLatestList.setLatestGroup(latestGroup);
		save(userLatestList);
	}
	
    //在数据库中更新latestList
	public void updateLatestList(Integer uid, String latestList){
		UserLatestList userLatestList = findLatestListByUid(uid);
		userLatestList.setLatestList(latestList);
		update(userLatestList);
	}
	
	
    //在数据库中更新latestGroup
	public void updateLatestGroup(Integer uid, String latestGroup){
		UserLatestList userLatestList = findLatestListByUid(uid);
		userLatestList.setLatestGroup(latestGroup);
		update(userLatestList);
	}
	
	//返回某个用户的最近列表记录
    public UserLatestList findLatestListByUid(Integer uid){
    	log("finding " + getClassName() + " instance with uid: " + uid,
                Level.INFO, null);
        try {
            final String queryString = "select model from " + getClassName() + " model where model.uid = :uid";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("uid", uid);
            try {
            	return (UserLatestList)query.getSingleResult();
            } catch (NoResultException e) {
            	return null;
            }
        } catch (RuntimeException re) {
            log("find by uid failed",Level.SEVERE, re);
            throw re;
        }
    }
}
