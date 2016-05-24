package com.free4lab.freeshare.model.data;


import org.json.JSONException;
import org.json.JSONObject;

public class DraftObject {
	private Integer id;
	private String name;
	private String description;
	private Integer type;
	private String extention; // 资源的后缀名，如果存在的话
	private String enclosure;
	private String toMySelf; // 记录是否是自己保存的资源
	private String writegroups;
	private String time;

	public DraftObject(String recivedString) {
		try {
			JSONObject json = new JSONObject(recivedString);
			JSONObject contentJSON = new JSONObject(json.getString("draft_content"));
			this.id = json.getInt("id");
			this.time = json.getString("time");
			this.name = contentJSON.getString("name");
			this.description = contentJSON.getString("description");
			this.type = contentJSON.getInt("type");
			this.extention = contentJSON.getString("extention");
			this.enclosure = contentJSON.getString("enclosure");
			this.toMySelf = contentJSON.getString("toMySelf");
			this.writegroups = contentJSON.getString("writegroups");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}

	public String getToMySelf() {
		return toMySelf;
	}

	public void setToMySelf(String toMySelf) {
		this.toMySelf = toMySelf;
	}

	public String getWritegroups() {
		return writegroups;
	}

	public void setWritegroups(String writegroups) {
		this.writegroups = writegroups;
	}
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
