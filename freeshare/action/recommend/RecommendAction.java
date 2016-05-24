package com.free4lab.freeshare.action.recommend;

import java.util.List;
import org.apache.log4j.Logger;
import org.json.JSONException;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.utils.recommend.RecommendObject;
import com.free4lab.utils.recommend.RecommendResult;
import com.free4lab.utils.recommend.RecommendResults;

/**
 * tuijian
 * 
 * @author Administrator
 * 
 */
public class RecommendAction extends BaseAction {
	private static final Logger logger = Logger
			.getLogger(RecommendAction.class);
	private static final long serialVersionUID = 8219440757744050045L;
	private Integer recoType;
	private Integer resourceType = Constant.ITEM_TYPE_UNKOWN;
	private Integer resourceId = 0;
	private Integer recoNum = 5;
	private Integer recByUser = 1;

	private List<RecommendResult> resultList;
	private String result;

	public String execute() {
		try {
			logger.info("resourceId: " + resourceId + " resourceType: "
					+ resourceType);
			long itemid = RecommendUtil.toRecommendId(resourceId, resourceType);
			RecommendResults results = RecommendUtil.recommend(getSessionUID(),
					itemid, recoType, recoNum,recByUser);
			resultList = results.getResultList();

			if (resultList == null || resultList.size() == 0) {
				// TODO 获取最热门的几个资源
			}

			result = results.toJSON(recoNum);
			logger.debug(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String feedback(){
	    //XXX 统一传递的type
		try {
            RecommendUtil.log(RecommendObject.toID(getSessionUID(),Constant.ITEM_TYPE_USER), 
                    RecommendObject.toID(resourceId, resourceType), Constant.BEHAVIOR_TYPE_DENY);
        } catch (JSONException e) {
            logger.error("推荐日志记录出错！！", e);
        }
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

	// public List<RecommendResult> getResultList() {
	// return resultList;
	// }
	//
	// public void setResultList(List<RecommendResult> resultList) {
	// this.resultList = resultList;
	// }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getRecoNum() {
		return recoNum;
	}

	public void setRecoNum(Integer recoNum) {
		this.recoNum = recoNum;
	}

    public Integer getRecByUser() {
        return recByUser;
    }

    public void setRecByUser(Integer recByUser) {
        this.recByUser = recByUser;
    }

}
