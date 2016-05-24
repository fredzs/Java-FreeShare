package com.free4lab.freeshare.handler;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.model.adapter.ObjectAdapter;
import com.free4lab.freeshare.model.dao.Item;
import com.free4lab.freeshare.model.dao.ItemDAO;

public class ItemHandler implements Handler {

	public ObjectAdapter save(ObjectAdapter adapter) {

		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		adapter.setPublicTime(currentTime);
		adapter.setEditTime(currentTime);
		Item item = adapter.getItem();
		new ItemDAO().save(item);
		adapter.setId(item.getId());
		return adapter;
	}

	public void delete(Integer id) {
		new ItemDAO().deleteByPrimaryKey(id);
	}

	public ObjectAdapter update(ObjectAdapter adapter) {
		if (adapter.getType() == ResourceTypeConst.TYPE_VERSION) {
			updateVersion(adapter);
		} else {
			Item item = adapter.getItem();
			item = (new ItemDAO()).updateItem(item);
			adapter.setPublicTime(item.getTime());
			adapter.setEditTime(item.getRecentEditTime());
		}
		return adapter;
	}

	/**
	 * 只针对文档的更新版本操作 更新数据库的存放文档下载地址字段 更新资源的最近编辑时间字段
	 */
	private ObjectAdapter updateVersion(ObjectAdapter adapter) {
		Item item = adapter.getItem();
		(new ItemDAO()).updateVersion(item);
		return adapter;
	}

	public ObjectAdapter getObject(Integer id) {
		Item item = new ItemDAO().findByPrimaryKey(id);
		if (item == null) {
			return null;
		}
		ObjectAdapter adapter = new ObjectAdapter(item);
		return adapter;
	}

	// 当使用简单属性的时候，使用此方法
	public Item getItem(Integer id) {
		Item item = new ItemDAO().findByPrimaryKey(id);
		if (item == null) {
			return null;
		}
		return item;
	}

	public List<Item> getItems() {
		return new ItemDAO().findAll();
	}
	
	public List<Item> getItems(Integer page, Integer size) {
		return new ItemDAO().getItems(page, size);
	}

	public List<ObjectAdapter> getObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(ObjectAdapter adapter) {
		new ItemDAO().deleteByPrimaryKey(adapter.getId());
	}
}
