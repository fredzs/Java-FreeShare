package com.free4lab.freeshare.action;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.freeshare.action.recommend.TagObject;
import com.free4lab.freeshare.manager.TagsManager;

/**
 * 获取用户曾经所使用的tag
 * @author zhaowei
 *
 */
public class LabelsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4451030217273977094L;
	private List<TagObject> toList;

	public String execute() {
		toList = new ArrayList<TagObject>();
		toList = new TagsManager().valLabelList(new JSONObject().toString());
		JSONObject json = new JSONObject();
		try {
			json.put("userId", getSessionUID());
			toList = new TagsManager().valLabelList(json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public List<TagObject> getToList() {
		return toList;
	}

	public void setToList(List<TagObject> toList) {
		this.toList = toList;
	}

	

}
