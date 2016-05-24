package com.free4lab.freeshare.action.resource;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.TagsManager;

/**
 * 添加标签
 * 
 * @author zhaowei
 * 
 */
public class AddLabelsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5144227914602104290L;
	private Integer id;
	private Integer resourceType;
	private String labels;

	public String execute() {
		new TagsManager().addLabels(getSessionUID(), labels, labels, id, resourceType);
		return SUCCESS;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

}
