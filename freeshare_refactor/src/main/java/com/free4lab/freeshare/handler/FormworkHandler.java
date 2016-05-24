package com.free4lab.freeshare.handler;

import java.util.List;

import com.free4lab.freeshare.model.dao.Formwork;
import com.free4lab.freeshare.model.dao.FormworkDAO;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

public class FormworkHandler {

	public ResourceWrapper getObject(Integer id) {
		Formwork formwork = new FormworkDAO().findByPrimaryKey(id);
		if(formwork == null){
			return null;
		}
		ResourceWrapper adapter = new ResourceWrapper(formwork);
		return adapter;
	}
	
	public List<ResourceWrapper> getObjects() {
		return null;
	}
	
	public ResourceWrapper save(ResourceWrapper adapter) {
		return adapter;
	}

	public void delete(Integer id) {
	}

	public ResourceWrapper update(ResourceWrapper adapter) {
		return adapter;
	}
	public List<Formwork> findAll(){
		return new FormworkDAO().findAll();
	}

	public void delete(ResourceWrapper adapter) {
		// TODO Auto-generated method stub
		
	}
}
