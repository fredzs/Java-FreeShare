package com.free4lab.freeshare.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HandlerInit {
	private static final String HANDLER_CONF= "handler.properties";
	private static String properties;
	static{
		InputStream is = HandlerInit.class.getClassLoader().getResourceAsStream(HANDLER_CONF);
		Properties p = new Properties();
		try {
			p.load(is);
			properties = p.getProperty("HANDLER");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Chain init(){
		Chain chain = null;
		try {
			LinkedList<String> chainList = new LinkedList<String>();
			JSONArray array = new JSONArray(properties);
			for(int i = 0; i < array.length(); i ++){
				JSONObject json = (JSONObject) array.get(i);
				String clz = json.getString("clz");
				chainList.add(clz);
			}
			chain = new Chain(chainList, true);
			Chain.set(chain);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return chain;
	}
}
