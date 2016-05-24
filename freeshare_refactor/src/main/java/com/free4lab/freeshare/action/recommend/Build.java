package com.free4lab.freeshare.action.recommend;

import org.json.JSONException;

import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

public abstract class Build {
	public abstract String buildContent(ResourceWrapper adapter) throws JSONException;
}
