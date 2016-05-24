package com.free4lab.freeshare.manager.factory;

import org.json.JSONException;

import com.free4lab.freeshare.action.recommend.Build;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

public class Context {
	Build buildContent;
	public Context(Build buildContent){
		this.buildContent = buildContent;
	}
	public String buildContent(ResourceWrapper adapter){
		try {
			return buildContent.buildContent(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
}
