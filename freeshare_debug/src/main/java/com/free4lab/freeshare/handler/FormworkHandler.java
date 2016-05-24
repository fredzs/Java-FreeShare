package com.free4lab.freeshare.handler;

import java.util.List;

import com.free4lab.freeshare.model.adapter.ObjectAdapter;
import com.free4lab.freeshare.model.dao.Formwork;
import com.free4lab.freeshare.model.dao.FormworkDAO;

public class FormworkHandler implements Handler {

	public ObjectAdapter getObject(Integer id) {
		Formwork formwork = new FormworkDAO().findByPrimaryKey(id);
		if(formwork == null){
			return null;
		}
		ObjectAdapter adapter = new ObjectAdapter(formwork);
		return adapter;
	}
	
	public List<ObjectAdapter> getObjects() {
		return null;
	}
	
	public ObjectAdapter save(ObjectAdapter adapter) {
		return adapter;
	}

	public void delete(Integer id) {
	}

	public ObjectAdapter update(ObjectAdapter adapter) {
		return adapter;
	}
	public List<Formwork> findAll(){
		return new FormworkDAO().findAll();
	}

	public void delete(ObjectAdapter adapter) {
		// TODO Auto-generated method stub
		
	}
}
