package com.free4lab.freeshare.handler;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.free4lab.freeshare.model.adapter.ObjectAdapter;
import com.free4lab.freeshare.model.dao.Lists;
import com.free4lab.freeshare.model.dao.ListsDAO;

public class ListHandler implements Handler {

	public ObjectAdapter save(ObjectAdapter adapter) {
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		adapter.setPublicTime(currentTime);
		adapter.setEditTime(currentTime);
		Lists lists = adapter.getList();
		new ListsDAO().save(lists);
		adapter.setId(lists.getId());
		return adapter;
	}

	public void delete(Integer id) {
		new ListsDAO().deleteByPrimaryKey(id);

	}

	public ObjectAdapter update(ObjectAdapter adapter) {

		Lists lists = adapter.getList();
		lists = new ListsDAO().updateList(lists);
		adapter.setPublicTime(lists.getTime());
		adapter.setEditTime(lists.getRecentEditTime());

		return adapter;
	}

	public boolean update(Integer listId, String name, String description) {
		try {
			(new ListsDAO()).edit(listId, name, description);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public ObjectAdapter getObject(Integer id) {
		Lists lists = new ListsDAO().findByPrimaryKey(id);
		if (lists == null) {
			return null;
		}
		ObjectAdapter adapter = new ObjectAdapter(lists);
		return adapter;
	}

	// 当使用简单属性的时候，使用此方法
	public Lists getLists(Integer id) {
		Lists lists = new ListsDAO().find(id);
		if (lists == null) {
			return null;
		}
		return lists;
	}
//	public Lists getLists(List<Integer> ids) {
//		Lists lists = new ListsDAO().findByPrimaryKey(id);
//		if (lists == null) {
//			return null;
//		}
//		return lists;
//	}
	public List<Lists> getListss() {
		return new ListsDAO().findAll();
	}

	public List<ObjectAdapter> getObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(ObjectAdapter adapter) {
		// TODO Auto-generated method stub
		
	}

}
