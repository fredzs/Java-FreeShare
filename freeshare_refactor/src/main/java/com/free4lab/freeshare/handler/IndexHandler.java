package com.free4lab.freeshare.handler;


import org.apache.log4j.Logger;

import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

public class IndexHandler extends ChainHandler {
	Logger logger = Logger.getLogger(IndexHandler.class);

	@Override
	public void process(ResourceWrapper adapter, Chain chain) {
		NewIndexManager instance = NewIndexManager.getInstance();
		instance.delIndex(instance.indexUrl(adapter.getId()));
		instance.addIndex(adapter);
		logger.info("IndexHandler success, invoke next handler.");
		next(adapter, chain);
	}
}
