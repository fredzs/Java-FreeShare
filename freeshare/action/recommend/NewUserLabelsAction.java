package com.free4lab.freeshare.action.recommend;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.TagsManager;

/**
 * 针对新用户,推荐标签为用户选择,并对用户所选择标签进行记录
 * 
 * @author zhaowei
 * 
 */
public class NewUserLabelsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3701210239395174786L;
	private List<TagObject> toList;
	private String tags;

	public String allTags() {
		toList = new ArrayList<TagObject>();
		toList = new TagsManager().valLabelList(new JSONObject().toString());
		return SUCCESS;
	}

	public String addTags() {
		new TagsManager().addLabels(getSessionUID(), tags);
		return SUCCESS;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<TagObject> getToList() {
		return toList;
	}

	public void setToList(List<TagObject> toList) {
		this.toList = toList;
	}

}
