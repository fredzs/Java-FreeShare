package com.free4lab.freeshare.model.data;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;


public class GroupInfo extends HashMap<String, String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GroupInfo.class);

	@SuppressWarnings("unchecked")
	public GroupInfo(String groupInfoJSON){
		if(groupInfoJSON != null){
			try {
				JSONObject groupInfo = new JSONObject(groupInfoJSON);
				Iterator<String> keys =  groupInfo.keys();
				while(keys.hasNext()){
					String key = keys.next();
					String value = groupInfo.getString(key);
					this.put(key, value);
				}
			} catch (JSONException e) {
				logger.error("Invalid String groupJSONInfo! " + groupInfoJSON);
			}
		}
	}
	
	public static void main(String[] args) {
		GroupInfo groupInfo = new GroupInfo(
				"{\"name\":\"王老师项目组\",\"desc\":\"王老师项目组\"}");
		try {
			System.out.println(groupInfo.get("name"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
