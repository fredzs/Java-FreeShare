package com.free4lab.freeshare.action.resource;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.Constant;
import com.free4lab.freeshare.action.recommend.RecommendUtil;
import com.free4lab.freeshare.manager.factory.ScoreManagerFactory;

/**
 * 处理对资源和列表的顶与踩
 * 
 * @author Administrator
 * 
 */
public class SupportResourceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5346634253124571163L;
	private Integer id;
	private Integer resourceType;
	private String upDown;

	public String execute() {
		ScoreManagerFactory.getScoreManager(resourceType).addUpDown(id, getSessionUID(),
				upDown);
		if(upDown.equals("up")){
			RecommendUtil.log(getSessionUID(), id, resourceType, Constant.BEHAVIOR_TYPE_LIKE);
		}else{
			RecommendUtil.log(getSessionUID(), id, resourceType, Constant.BEHAVIOR_TYPE_DISLIKE);
		}
		return SUCCESS;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUpDown() {
		return upDown;
	}

	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}


}
