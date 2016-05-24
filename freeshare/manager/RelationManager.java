/**
 * 
 */
package com.free4lab.freeshare.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.model.dao.Relation;
import com.free4lab.freeshare.model.dao.RelationDAO;

/**
 * @author wenlele
 *when to add the resource(item) into a list or lists,
 *we need to create a relation between item and list.
 *
 */
public class RelationManager  {

	private static final Logger logger = Logger.getLogger(RelationManager.class);

	public boolean isRelation(Integer itemId, Integer listId){
		logger.info("find relation between itemId=" + itemId + " and listId=" + listId);
		if (null != findRelation(itemId, listId)){
			return true;
		}else {
			return false;
		}
	}
	
	public Relation findRelation(Integer itemId, Integer listId){
		Relation tmp = null;
		try {
			tmp = (new RelationDAO()).findRelation(itemId, listId);
			return tmp;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
		
	}
	
	public List<Relation> getsByItem(Integer itemId){
		logger.info("get relations by itemId=" + itemId);
		try {
			return (new RelationDAO()).findByItem(itemId);
		} catch (RuntimeException re) {
			logger.info(re.getMessage());
			return null;
		}
	}
	
	public boolean createRelation(Integer itemId, Integer listId) {		
		try{
			(new RelationDAO()).createRelation(itemId, listId);
			return true;
		}catch(Exception e){
			logger.info(e.getMessage());
			return false;
		}		
	}

	public boolean deleteRelation(Integer itemId, Integer listId) {
		try{
			(new RelationDAO()).delRelation(itemId, listId);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	public int delelteItemRelations(Integer itemId){
		try {
			return (new RelationDAO()).delRelationsByItem(itemId);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return 0;
		}
	}

	public int deleltelistRelations(Integer listId){
		try {
			return (new RelationDAO()).delRelationsByItem(listId);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return 0;
		}
	}
	
	public List<Relation> getRelations(){
		return new RelationDAO().findAll();
	}
	public void save(Integer itemId, List<Integer> listIds){
		List<Relation> rs = new ArrayList<Relation>();
		for(Integer id : listIds){
			rs.add(new Relation(itemId, id));
		}
		new RelationDAO().save(rs);
	}
}
