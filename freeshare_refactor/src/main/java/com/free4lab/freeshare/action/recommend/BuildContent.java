package com.free4lab.freeshare.action.recommend;

import org.json.JSONException;

import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

/**
 * 构造RecommendObject
 * @author zhaowei
 *
 */
public class BuildContent extends Build{

	public String buildContent(ResourceWrapper adapter) throws JSONException {
		String content = returnContent(adapter);
		return content;
	}
	
	public String returnContent(ResourceWrapper adapter) throws JSONException{
		String content = "";
		return content;
	}
	
}
