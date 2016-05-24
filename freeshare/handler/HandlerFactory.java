package com.free4lab.freeshare.handler;

import com.free4lab.freeshare.model.adapter.ObjectAdapter;

public class HandlerFactory {
	public static Handler getHandler(ObjectAdapter adapter) {
		Handler handler = createHandler(adapter.getType());
		return handler;
	}

	private static Handler createHandler(Integer type) {
		Handler handler;
		if (type == null || type != -1) {
			handler = new ItemHandler();
		}else if(type == 10){
        	handler = new FormworkHandler();
        }else {
			handler = new ListHandler();
		}
		return handler;
	}
}
