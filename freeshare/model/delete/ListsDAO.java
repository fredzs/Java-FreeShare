package com.free4lab.freeshare.model.delete;

import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;

import javax.persistence.Query;

import org.hibernate.Session;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;
@Deprecated
public class ListsDAO extends AbstractDAO<Lists> {

    public String getClassName() {
        return getEntityClass().getName();
    }

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
        return Lists.class;
    }

    public static final String PU_NAME = "FreeSharePU";
    public String getPUName() {
        return PU_NAME;
    }

    public IEntityManagerHelper getEntityManagerHelper() {
        return new NoCacheEntityManagerHelper();
    }
    /*add other methods behind*/

    public Lists createList(String name, Integer authorId, 
    		Integer groupId, String description){
    	log("finding " + getClassName() + " instance: create a list",
    			Level.INFO, null);
    	Date date = new Date();
    	Timestamp currTime = new Timestamp(date.getTime());
    	//TODO 这个地方unused有问题，是不是应该都删除呀！
    	Lists list = new Lists(name, description, authorId,66,
    			currTime, currTime, null);
    	save(list);
    	return list;    	
    }
    
    public void edit(Integer listId, String name, String description){
    	log("finding " + getClassName() + " instance: edit a list",
    			Level.INFO, null);
    	Date date = new Date();
    	Timestamp currTime = new Timestamp(date.getTime());
    	Lists list = findById(listId);
    	list.setName(name);
    	list.setDescription(description);
    	list.setRecentEditTime(currTime);
    	update(list);
    }
    
    public Lists updateList(Lists list){
    	Lists newLists = findByPrimaryKey(list.getId());
    	list.setTime(newLists.getTime());
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		list.setAuthorId(newLists.getAuthorId());
		list.setRecentEditTime(currentTime);
		update(list);
		return list;
    }
    
    @SuppressWarnings("unchecked")
	public java.util.List<Lists> gets(java.util.List<String> listIds){
    	log("finding " + getClassName() + " instance: find lists ",
    			Level.INFO, null);
    	StringBuffer listConditions = new StringBuffer("");
    	for(int i = 0; i < listIds.size() - 1; i++){
    		listConditions.append(" model.lid = " 
    				+ listIds.get(i) + " or ");
    	}
    	listConditions.append(" model.lid = "
    			+ listIds.get(listIds.size()-1));
    	try {
			final String queryString = "select model from " + getClassName()
				+ " model where " + listConditions.toString();
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();			
		} catch (RuntimeException re) {
			log("find lists failed", Level.INFO, re);
			throw re;
		}
    }
    
    public Lists find(Integer id){
    	Query query = getEntityManager().createQuery("select model from " + getClassName() + " model where model.id = " + id);
    	if(query instanceof org.hibernate.ejb.QueryImpl){
    		((org.hibernate.ejb.QueryImpl) query).getHibernateQuery().setCacheable(true);
    		return (Lists) query.getSingleResult();
    	}
		return null;
    }
}
