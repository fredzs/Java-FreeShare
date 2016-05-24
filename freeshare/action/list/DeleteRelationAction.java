package com.free4lab.freeshare.action.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.BehaviorConst;
import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.Constant;
import com.free4lab.freeshare.action.recommend.RecommendUtil;
import com.free4lab.freeshare.manager.RelationManager;
import com.free4lab.freeshare.manager.search.NewIndexManager;

/**
 * 删除资源与列表的归属关系
 * @author zhaowei
 *
 */
public class DeleteRelationAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8172488741353815158L;
	private static final Logger logger = Logger
			.getLogger(DeleteRelationAction.class);
	private Integer id; //列表
	private Integer itemId; //资源
	private Integer resourceType;
	
	
	NewIndexManager instance = NewIndexManager.getInstance();
	public String execute() {

		updateIndex();
		
		(new RelationManager()).deleteRelation(itemId, id);
		List<Integer> l = new ArrayList<Integer>();
		l.add(id);
		//记录用户行为日志
		RecommendUtil.log(getSessionUID(), itemId, resourceType, l, Constant.ITEM_TYPE_LIST, Constant.BEHAVIOR_TYPE_EDIT);
		logger.info("delete relation success.");
		return SUCCESS;
	}
	
	void updateIndex(){
		
		List<String> tags = new ArrayList<String>();
		tags.add(TagValuesConst.pITEM_IN_LIST + id);
		String url = "item?id=" + itemId;
		
		try {
			instance.removeTags(url, tags);
			
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("optype", BehaviorConst.TYPE_OUT_LIST);
			m.put("ids", id);
			m.put("userId", getSessionUID());
			instance.changeDocContent(url, m);
		} catch (Exception e) {
			logger.error("change doc failed", e);
		}
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}
}
