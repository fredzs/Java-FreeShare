package com.free4lab.freeshare.model.delete;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.sql.Timestamp;

import javax.persistence.Query;


import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;
@Deprecated
public class ItemDAO extends AbstractDAO<Item> {
	public String getClassName() {
		return getEntityClass().getName();
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return Item.class;
	}

	public static final String PU_NAME = "FreeSharePU";

	public String getPUName() {
		return PU_NAME;
	}

	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

	/* add other methods behind */
	public Item createItem(String name, Integer type, Integer authorId,
			 String description, String content, String extend) {
		log("finding " + getClassName() + " instance: create a item",
				Level.INFO, null);
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		Item item = new Item(type, name, description, authorId,
				content, currentTime, currentTime, extend);
		save(item);
		return item;
	}

	public Item updateItem(Item item) {
		Item newItem = findByPrimaryKey(item.getId());
		item.setTime(newItem.getTime());
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		item.setRecentEditTime(currentTime);
		item.setAuthorId(newItem.getAuthorId());
		update(item);
		return item;
	}

	/* edit the version of the item */
	public Item updateVersion(Item item) {
		Item newItem = findByPrimaryKey(item.getId());
		item.setTime(newItem.getTime());
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		item.setRecentEditTime(currentTime);
		update(item);
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> selects(Integer page, Integer size){
		final String sqlQuery = "select model from " + getClassName() + " model ";
		Query query = getEntityManager().createQuery(sqlQuery);
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		List<Item> items = query.getResultList();
		if(items == null){
			items = new ArrayList<Item>();
		}
		return items;
	}
}
