package com.free4lab.freeshare.handler;

import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.model.wrapper.ResourceWrapper;
import com.free4lab.freeshare.util.TagsUtil;

public class TagsHandler extends ChainHandler {
	Logger logger = Logger.getLogger(TagsHandler.class);

	public void process(ResourceWrapper adapter, Chain chain) {
		List<String> tags = TagsUtil.getInstance().getTags(adapter);
		adapter.setTags(tags);
		logger.info("TagsHandler success, inovke next chain.");
		next(adapter, chain);
	}
}
