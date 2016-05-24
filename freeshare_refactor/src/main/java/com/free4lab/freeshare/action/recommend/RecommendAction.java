package com.free4lab.freeshare.action.recommend;

import org.apache.log4j.Logger;
import org.json.JSONException;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.utils.recommend.RecommendResults;

/**
 * tuijian
 * 
 * @author zhaowei
 * 
 */
public class RecommendAction extends BaseAction {
	private static final Logger logger = Logger
			.getLogger(RecommendAction.class);
	private static final long serialVersionUID = 8219440757744050045L;
	private String result;// 推荐返回結果
	private Integer recoType;
	private Integer resourceId;
	private Integer recByUser = 1;
	private Integer resourceType = Constant.ITEM_TYPE_UNKOWN;

	private final static Integer RECO_NUM = 5;

	public String execute() {
		try {
			logger.debug("resourceId: " + resourceId + " resourceType: "
					+ resourceType);
			long itemid = RecommendUtil.toRecommendId(resourceId, resourceType);
			RecommendResults results = RecommendUtil.recommend(getSessionUID(),
					itemid, recoType, RECO_NUM, recByUser);

			result = results.toJSON(RECO_NUM);
			logger.debug(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//发现
	public String disover() {
		try {
			long itemid = RecommendUtil.toRecommendId(resourceId, resourceType);
			RecommendResults results = RecommendUtil.recommend(getSessionUID(),
					itemid, recoType, RECO_NUM, recByUser);

			result = results.toJSON(RECO_NUM);
			logger.debug(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String feedback() {
		// XXX 统一传递的type
		RecommendUtil.log(getSessionUID(), resourceId, resourceType,
				Constant.BEHAVIOR_TYPE_DENY);
		return SUCCESS;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getRecoType() {
		return recoType;
	}

	public void setRecoType(Integer recoType) {
		this.recoType = recoType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getRecByUser() {
		return recByUser;
	}

	public void setRecByUser(Integer recByUser) {
		this.recByUser = recByUser;
	}

}
