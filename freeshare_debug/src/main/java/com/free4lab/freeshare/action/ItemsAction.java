package com.free4lab.freeshare.action;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.search.SearchManager;
import com.free4lab.freeshare.model.data.SrhResult;

/**
 * 获取我的全部资源，并根据用户选择资源类型进行相应筛选
 * @author zhaowei
 * 
 */
public class ItemsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4487948594123775045L;
	private static final Logger logger = Logger
			.getLogger(ItemsAction.class);
	private Integer page;
	private Integer lastPage;
	private SrhResult srhResult;
	private String resourceTypeList;
	private static final Integer SIZE = 10;

	public String execute() {
		page = (page == null) ? 1 : page;
		//TODO 重构 Tool
		String tags = returnTags(resourceTypeList);
		srhResult = SearchManager.searchItems(NULL_QUERY, page, SIZE, tags);
		if (null == srhResult) {
			logger.info("result is null");
			return INPUT;
		}

		setLastPage(srhResult.getTotalNum() / SIZE + 1);
		return SUCCESS;
	}
	
	private String returnTags(String rType) {
		String tags = "NOT " + TagValuesConst.FMT_GROUP + " NOT " + TagValuesConst.FMT_COMMENT + " " + TagValuesConst.pAUTHOR + getSessionUID(); 
		if (rType == null || rType.equals("all")) {
			return tags;
		} else {
			tags += " AND (" + rType.replaceAll(",", " OR ") + " )";
		}
		return tags;
	}
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getResourceTypeList() {
		return resourceTypeList;
	}

	public void setResourceTypeList(String resourceTypeList) {
		this.resourceTypeList = resourceTypeList;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	public SrhResult getSrhResult() {
		return srhResult;
	}

	public void setSrhResult(SrhResult srhResult) {
		this.srhResult = srhResult;
	}
}
