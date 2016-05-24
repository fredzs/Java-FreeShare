package com.free4lab.freeshare.model.adapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.free4lab.freeshare.model.dao.Formwork;
import com.free4lab.freeshare.model.dao.Item;
import com.free4lab.freeshare.model.dao.Lists;

public class ObjectAdapter {
	private Integer id;
	private Integer type;
	private String name;
	private String description;
	private Integer authorId;
	private String content;
	private String extend;
	private List<Integer> groupIds;
	private List<String> lables;
	private Timestamp publicTime;
	private String image_path;
	private Timestamp editTime;
	private Item item;
	private Lists list;
	private Formwork formwork;

	public ObjectAdapter(Item item) {
		this.id = item.getId();
		this.type = item.getType();
		this.name = item.getName();
		this.description = item.getDescription();
		this.authorId = item.getAuthorId();
		this.content = item.getContent();
		this.extend = item.getExtend();
		this.publicTime = item.getTime();
		this.editTime = item.getRecentEditTime();
	}

	public ObjectAdapter(Formwork formwork) {
		this.id = formwork.getId();
		this.type = formwork.getType();
		this.name = formwork.getName();
		this.content = formwork.getContent();
		this.authorId = formwork.getAuthorId();
		this.description = formwork.getDescription();
		this.extend = formwork.getExtend();
		this.publicTime = formwork.getTime();
		this.image_path = formwork.getImage_path();
		this.editTime = formwork.getRecentEditTime();
	}

	public ObjectAdapter(Lists list) {
		this.id = list.getId();
		this.name = list.getName();
		this.description = list.getDescription();
		this.authorId = list.getAuthorId();
		this.extend = list.getExtend();
		this.publicTime = list.getTime();
		this.editTime = list.getRecentEditTime();
	}

	public ObjectAdapter(Integer type) {
		this.type = type;
	}

	public ObjectAdapter(Integer type, String name, String description,
			Integer authorId, String content, String extend) {
		this.type = type;
		this.name = name;
		this.description = description;
		this.authorId = authorId;
		this.content = content;
		this.extend = extend;

	}

	public Item getItem() {
		item = new Item();
		item.setId(id);
		item.setAuthorId(authorId);
		item.setContent(content);
		item.setDescription(description);
		item.setExtend(extend);
		item.setName(name);
		item.setType(type);
		item.setTime(publicTime);
		item.setRecentEditTime(editTime);
		item.setUnuse(0);
		return item;
	}

	public Formwork getFormwork() {
		formwork = new Formwork();
		formwork.setId(id);
		formwork.setAuthorId(authorId);
		formwork.setContent(content);
		formwork.setDescription(description);
		formwork.setExtend(extend);
		formwork.setName(name);
		formwork.setType(type);
		formwork.setTime(publicTime);
		formwork.setImage_path(image_path);
		formwork.setRecentEditTime(editTime);
		formwork.setUnuse(0);
		return formwork;
	}

	public Lists getList() {
		list = new Lists();
		list.setId(id);
		list.setAuthorId(authorId);
		list.setDescription(description);
		list.setExtend(extend);
		list.setName(name);
		list.setTime(publicTime);
		list.setRecentEditTime(editTime);
		list.setUnuse(0);
		return list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Integer> getGroupIds() {
		if(groupIds == null){
			return new ArrayList<Integer>();
		}
		return groupIds;
	}

	public void setGroupIds(List<Integer> groupIds) {
		this.groupIds = groupIds;
	}

	public Timestamp getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(Timestamp publicTime) {
		this.publicTime = publicTime;
	}

	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public List<String> getLables() {
		return lables;
	}

	public void setLables(List<String> lables) {
		this.lables = lables;
	}
}
