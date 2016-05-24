package com.free4lab.freeshare.model.wrapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.free4lab.freeshare.model.dao.Formwork;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.util.TimeUtil;
/**
 将资源进行封装，来满足对不同资源的操作需求。是原ObjectAdapter
 */
public class ResourceWrapper {
	private Integer id;
	private Integer type;
	private String name;
	private String description;
	private Integer authorId;
	private String content;
	private String extend;
	private List<Integer> groupIds;
	private List<String> tags;
	private String image_path;
	private Timestamp publicTime;
	private Timestamp editTime;
	private Formwork formwork;
	private Resource resource;
	public ResourceWrapper(){}
	// TODO Builder模式
	public ResourceWrapper(Integer type, String name, String description,
			Integer authorId, String attachment, String extend,
			List<Integer> groupIds) {
		this.type = type;
		this.name = name;
		this.description = description;
		this.authorId = authorId;
		this.content = attachment;
		this.extend = extend;
		this.publicTime = TimeUtil.getCurrentTime();
		this.editTime = publicTime;
		this.groupIds = groupIds;
		this.resource = new Resource(type, name, description, authorId,
				publicTime, editTime, attachment, extend);
	}

	public ResourceWrapper(Resource resource) {
		this.id = resource.getId();
		this.name = resource.getName();
		this.type = resource.getType();
		this.description = resource.getDescription();
		this.authorId = resource.getAuthorId();
		this.content = resource.getAttachment();
		this.publicTime = resource.getCreateTime();
		this.editTime = resource.getRecentEditTime();
		this.extend = resource.getExtend();
	}

	public Resource getResource() {
		if (resource != null) {
			if(resource.getId() == null){
				resource.setId(id);
			}
			return resource;
		}
		resource = new Resource();
		resource.setId(id);
		resource.setAuthorId(authorId);
		resource.setAttachment(content);
		resource.setDescription(description);
		resource.setExtend(extend);
		resource.setName(name);
		resource.setType(type);
		resource.setCreateTime(publicTime);
		resource.setRecentEditTime(editTime);
		return resource;
	}

	public ResourceWrapper(Formwork formwork) {
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
		if (groupIds == null) {
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

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
