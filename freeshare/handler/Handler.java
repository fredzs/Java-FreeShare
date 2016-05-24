package com.free4lab.freeshare.handler;

import java.util.List;

import com.free4lab.freeshare.model.adapter.ObjectAdapter;

public interface Handler {
	public ObjectAdapter save(ObjectAdapter adapter);
	
	public void delete(ObjectAdapter adapter);
	
	public void delete(Integer id);

	public ObjectAdapter update(ObjectAdapter adapter);

	public ObjectAdapter getObject(Integer id);

	public List<ObjectAdapter> getObjects();
}
