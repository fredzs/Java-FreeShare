package com.free4lab.freeshare.action.resource;

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

import com.free4lab.freeshare.manager.ListRelationManager;
import com.free4lab.freeshare.manager.search.NewIndexManager;

import com.free4lab.freeshare.model.dao.UserLatestList;
import com.free4lab.freeshare.model.dao.UserLatestListDAO;

public class AddtoListAction extends BaseAction {

	private static final long serialVersionUID = -7532061950562146269L;
	private static final Logger logger = Logger
			.getLogger(AddtoListAction.class);
	private String selectedList; // 要加入的列表id
	private Integer id; // 资源ID
	private Integer resourceType; // 资源类型
	NewIndexManager newindexManager = NewIndexManager.getInstance();	
	
	public String execute() {
		String[] listArray = selectedList.split(",");
		updateIndex();
		logger.info(selectedList);
		for (int i = 0; i < listArray.length; i++) {
			logger.info(listArray[i]);
		}
		// 删除资源Id与已有归属列表的关系
		ListRelationManager listrelationManager = new ListRelationManager();
		listrelationManager.deleteResourceRelations(id);
		// addedListId中存放所有要加入的列表的id
		List<Integer> addedListId = new ArrayList<Integer>();
		for (String lid : listArray) {
			Integer listId = Integer.parseInt(lid);
			addedListId.add(listId);
		}
		// 保存资源Id和要加入的列表Id的关系
		listrelationManager.saveListsOfResource(id, addedListId);
		logger.info(addedListId);
		// 更新数据库中存放用户最近操作
		updateUserLatestList(getSessionUID(), listArray);
		// 记录用户行为日志
		RecommendUtil.log(getSessionUID(), id, resourceType,
				addedListId, Constant.ITEM_TYPE_LIST,
				Constant.BEHAVIOR_TYPE_EDIT);
		return SUCCESS;
	}
	
	/**
	 * 更新索引
	 */
	void updateIndex() {
		String redirectUrl = "resource?id=" + id;

		try {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("optype", BehaviorConst.TYPE_IN_LIST);
			m.put("ids", selectedList);
			m.put("userId", getSessionUID());
			// 根据Map中的key修改而且仅修改该url所标识的索引，对其他字段值没有影响
			newindexManager.changeDocContent(redirectUrl, m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新数据库中存放用户最近操作
	 */
	private void updateUserLatestList(Integer uid, String[] listArray) {
		UserLatestListDAO userLatestListDAO = new UserLatestListDAO();
		UserLatestList userLatestList = userLatestListDAO
				.findLatestListByUid(uid);
		// 若之前没有uid的记录，则插入一条
		if (userLatestList == null) {
			userLatestListDAO.create(uid, "," + selectedList, ",");
		} else {
			String newList = userLatestList.getLatestList();
			logger.info("oldList:" + newList + "----selectedList:"
					+ selectedList);
			for (String list : listArray) {
				if (newList.contains("," + list + ",")) {
					newList = newList.replace("," + list + ",", ",");
				}
				newList = "," + list + newList;
			}
			// 若newList最多于10否则删除最少使用的。存储格式",2,3,"
			String[] newListArray = newList.split(",");
			if (newListArray.length > 11) {
				newList = ",";
				for (int i = 1; i <= 10; i++) {
					newList += newListArray[i] + ",";
				}
			}
			userLatestListDAO.updateLatestList(uid, newList);
		}

	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(String selectedList) {
		this.selectedList = selectedList;
	}
	
/*	public static void main(String[] arg)
	{
		// 在list_relation表查看resourceid与listid对应关系 在user_latestlist查看更新 
		 * 更改execute中的uid=777 测试后删除数据
		AddtoListAction al = new AddtoListAction ();
		al.setResourceId(777);
		al.setSelectedList("1,2,3,4,5");
		al.execute();
		
	}*/
}
