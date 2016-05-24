package com.free4lab.freeshare.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.freeshare.action.recommend.TagObject;

/**
 * 对资源与用户的标签进行操作
 * 暂时未使用，可不考虑修改
 * 
 * @author Administrator
 * 
 */
public class TagsManager {
//	final String FREETAG_URL = "http://localhost:9090/freetag/api/";
	 private static final String FREETAG_URL = "http://freetag.free4lab.com/api/";

	public void addLabels(Integer userId, String newLabels) {
		addLabels(userId, null, newLabels, null, null);
	}

	public void addLabels(Integer userId, String labels, String newLabels, Integer id,
			Integer resourceType) {
		String params = buildParams(userId, labels, newLabels, id, resourceType);
		String addUrl = FREETAG_URL + "add?";
		if (params != null) {
			HttpApiManager.invokeApi(addUrl, params);
		}

	}

	public List<TagObject> valLabelList(String param){
		String allTagsUrl = FREETAG_URL + "tags?";
		String result = HttpApiManager.invokeApi(allTagsUrl, param);
		List<TagObject> toList = new ArrayList<TagObject>();
		try {
			if (result != null) {
				JSONObject json = new JSONObject(result);
				JSONArray array = new JSONArray(new JSONObject(
						json.getString("result")).getString("results"));
				for (int i = 0; i < array.length(); i++) {
					JSONObject jo = (JSONObject) array.get(i);
					toList.add(new TagObject(jo));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return toList;
	}

	String buildParams(Integer userId, String labels, String newLabels, Integer id,
			Integer resourceType) {
		try {
			JSONObject json = new JSONObject();
			json.put("userId", userId);
			if (id != null) {
				json.put("resourceId", id);
				json.put("resourceType", resourceType);
			}
			json.put("labels", labels);
			json.put("newLabels", newLabels);
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
