package com.free4lab.freeshare.action.recommend;

import org.json.JSONException;
import org.json.JSONObject;

public class TagObject {

	private Integer id;
	private Integer userId;
	private Integer resourceId;
	private Integer resourceType;
	private String name;
	private String description;
	private Integer count;

	public TagObject() {
	}

	public TagObject(JSONObject json){
		try {
			this.id = json.getInt("id");
			this.name = json.getString("name");
			this.description = json.getString("description");
			if(json.has("count")){
				this.count = json.getInt("count");
			}
			if (json.has("resourceId") && json.has("resourceType")) {
				this.resourceId = json.getInt("resourceId");
				this.resourceType = json.getInt("resourceType");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public TagObject fromReqJSON(String json) {

		try {
			JSONObject jsonObj = new JSONObject(json);
			TagObject to = new TagObject(jsonObj);
			return to;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
