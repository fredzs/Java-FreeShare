package com.free4lab.freeshare.action;

import java.util.List;
import org.apache.log4j.Logger;

import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.URLConst;
import com.free4lab.freeshare.manager.search.SearchManager;
import com.free4lab.freeshare.model.data.SrhResult;
import com.free4lab.freeshare.util.Tool;
import com.free4lab.utils.http.SearchClient;

public class SearchAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2389039604943766393L;
	private static final Logger logger = Logger.getLogger(SearchAction.class);
	private String query; // 搜索内容
	private String tips; // 根据一个词获取其搜素提示
	private String groupIds;
	private String resourceTypeList;
	private List<String> searchTips; //返回给前端页面的搜索提示词

	private SrhResult srhResult;// 搜索结果
	private Integer page; // 搜索第几页
	private Integer lastPage;
	private final static Integer SIZE = 10;// 搜索结果一页呈现多少个结果

	public String search() {
		if (query == null) {
			return INPUT;
		}
		// 无参数page时，默认为1
		if (null == page)
			this.setPage(1);
		String tags = "";
		if(resourceTypeList.equals(TagValuesConst.FMT_GROUP)){
			tags = TagValuesConst.FMT_GROUP;
		}else{
			if(resourceTypeList.equals("all")){
				resourceTypeList = "SHARE:FMT:LIST,SHARE:ITEM:TEXT,SHARE:ITEM:URL,"
				                  +"SHARE:ITEM:VIDEO,SHARE:ITEM:DOC,SHARE:ITEM:TOPIC,SHARE:FMT:GROUP";
			}
			tags = Tool.returnTags(getSessionUID(), groupIds, resourceTypeList);
		}
		try {
			setSrhResult(SearchManager.searchItems(query, page, SIZE, tags));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null == getSrhResult()) {
			logger.info("搜索结果为null");
			setLastPage(1);
		} else {
			if (srhResult.getTotalNum() % SIZE == 0) {
				setLastPage(srhResult.getTotalNum() / SIZE);
			} else
				setLastPage(srhResult.getTotalNum() / SIZE + 1);
		}
		return SUCCESS;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getResourceTypeList() {
		return resourceTypeList;
	}

	public void setResourceTypeList(String resourceTypeList) {
		this.resourceTypeList = resourceTypeList;
	}

	public String searchTips() {
		try {
			searchTips = new SearchClient(URLConst.APIPrefix_FreeSearch).getWordTip(tips, 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		// 如果query是资源或是是列表形式的就要进行一下字符串的处理
		this.query = query;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public List<String> getSearchTips() {
		return searchTips;
	}

	public void setSearchTips(List<String> searchTips) {
		this.searchTips = searchTips;
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

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
