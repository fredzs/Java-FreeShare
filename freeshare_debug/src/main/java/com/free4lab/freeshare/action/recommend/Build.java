package com.free4lab.freeshare.action.recommend;

import org.json.JSONException;

import com.free4lab.freeshare.model.adapter.ObjectAdapter;

public abstract class Build {
	public abstract String buildContent(ObjectAdapter adapter) throws JSONException;
}
