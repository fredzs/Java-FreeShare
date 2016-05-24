package com.free4lab.freeshare.handler;

import java.util.List;
import com.free4lab.freeshare.model.adapter.ObjectAdapter;

/**
 * 各个调用者应该修改为调用此类方法，用以屏蔽上层对下层的资源和列表的不同
 * @author zhaowei
 *
 */
public class ObjectAdapterHandler implements Handler{

	public ObjectAdapter save(ObjectAdapter adapter) {
		return HandlerFactory.getHandler(adapter).save(adapter);
	}

	public void delete(Integer id) {
		//do nothing
	}

	public ObjectAdapter update(ObjectAdapter adapter) {
		return HandlerFactory.getHandler(adapter).update(adapter);
	}

	public ObjectAdapter getObject(Integer id) {
		return null;
	}

	public List<ObjectAdapter> getObjects() {
		return null;
	}

	public void delete(ObjectAdapter adapter) {
		HandlerFactory.getHandler(adapter).delete(adapter);
	}

}
