package com.free4lab.freeshare.action.list;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.ListRelationManager;
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
	private Integer resourceId; //资源
	private Integer resourceType;
	
	
	NewIndexManager instance = NewIndexManager.getInstance();
	public String execute() {
		(new ListRelationManager()).deleteRelation(resourceId, id);
		//List<Integer> l = new ArrayList<Integer>();
		//l.add(listId);
		//记录用户行为日志
		//RecommendUtil.log(getSessionUID(), resourceId, resourceType, l, Constant.ITEM_TYPE_LIST, Constant.BEHAVIOR_TYPE_EDIT);
		logger.info("delete relation success.");
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}





	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}
}
