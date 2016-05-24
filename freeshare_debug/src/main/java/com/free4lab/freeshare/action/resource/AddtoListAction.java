/*package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.free4lab.freeshare.model.dao.Relation;
import com.free4lab.freeshare.model.dao.UserLatestList;
import com.free4lab.freeshare.model.dao.UserLatestListDAO;

public class AddtoListAction111 extends BaseAction {

	private static final long serialVersionUID = -7532061950562146269L;
	private static final Logger logger = Logger
			.getLogger(AddtoListAction111.class);
	private String selectedList; // 要加入的列表id
	private Integer id; // 资源ID
	private Integer resourceType;
	
	public String execute() {
		String[] listArray = selectedList.split(",");
		updateIndex();

		// 向数据库查询当前资源已添加的列表
		List<Relation> relations = (new RelationManager()).getsByItem(id);
		List<Integer> addedlists = new ArrayList<Integer>();
		for (Relation r : relations) {
			addedlists.add(r.getListId());
		}
		//
		// 更新数据库中存放资源和列表的关系
		RelationManager rm = new RelationManager();
		for (String lid : listArray) {
			Integer listId = Integer.parseInt(lid);
			addedlists.remove(listId);
		//	rm.save(id, addedlists);
			
		}
		addedlists.toArray();
		relations.toArray();
		
		if(!relations.contains(addedlists))
		{
			//addedlists.remove(listId);
			rm.save(id, addedlists);
			// 更新数据库中存放用户最近操作
			updateUserLatestList(getSessionUID(), listArray);
			//记录用户行为日志
			RecommendUtil.log(getSessionUID(), id, resourceType, addedlists, Constant.ITEM_TYPE_LIST, Constant.BEHAVIOR_TYPE_EDIT);
			return SUCCESS;
		}
		else
		{
			rm.deleteRelation(id, addedlists.remove(0));
		// 更新数据库中存放用户最近操作
		updateUserLatestList(getSessionUID(), listArray);
		//记录用户行为日志
		RecommendUtil.log(getSessionUID(), id, resourceType, addedlists, Constant.ITEM_TYPE_LIST, Constant.BEHAVIOR_TYPE_EDIT);
		return SUCCESS;
		}
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	void updateIndex() {
		String redirectUrl = "item?id=" + id;
		String[] listArray = selectedList.split(",");

		List<String> tags = new ArrayList<String>();
		for (String lid : listArray) {
			tags.add(TagValuesConst.pITEM_IN_LIST + lid);
		}
		new NewIndexManager().addTags(redirectUrl, tags);

		try {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("optype", BehaviorConst.TYPE_IN_LIST);
			m.put("ids", selectedList);
			m.put("userId", getSessionUID());
			new NewIndexManager().changeDocContent(redirectUrl, m);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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
}
*/

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
import com.free4lab.freeshare.manager.RelationManager;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.model.dao.Relation;
import com.free4lab.freeshare.model.dao.UserLatestList;
import com.free4lab.freeshare.model.dao.UserLatestListDAO;

public class AddtoListAction extends BaseAction {

	private static final long serialVersionUID = -7532061950562146269L;
	private static final Logger logger = Logger
			.getLogger(AddtoListAction.class);
	private String selectedList; // 要加入的列表id
	private Integer id; // 资源ID
	private Integer resourceType;
	NewIndexManager instance = NewIndexManager.getInstance();
	public String execute() {
		String[] listArray = selectedList.split(",");
		updateIndex();
		logger.info(selectedList);
		for(int i=0;i<listArray.length;i++)
		{
			logger.info(listArray[i]);
		}
	//	List<Relation> relations = (new RelationManager()).getsByItem(id);
		List<Integer> addedlists = new ArrayList<Integer>();
//		for (Relation r : relations) {
//			addedlists.add(r.getListId());
//		}
		RelationManager rm = new RelationManager();
		rm.delelteItemRelations(id);
		for (String lid : listArray) {
			Integer listId = Integer.parseInt(lid);
			addedlists.add(listId);
		}
		rm.save(id, addedlists);
		logger.info(addedlists);
		/*
		// 向数据库查询当前资源已添加的列表
		List<Relation> relations = (new RelationManager()).getsByItem(id);
		List<Integer> addedlists = new ArrayList<Integer>();
		addedlists.add(316);
		for (Relation r : relations) {
			addedlists.add(r.getListId());
		}
		logger.info(addedlists);
		// 更新数据库中存放资源和列表的关系
		RelationManager rm = new RelationManager();
		for (String lid : listArray) {
			Integer listId = Integer.parseInt(lid);
			addedlists.remove(listId);
		}
		rm.save(id, addedlists);
		logger.info(addedlists);
		*/
		// 更新数据库中存放用户最近操作
		updateUserLatestList(getSessionUID(), listArray);
		//记录用户行为日志
		RecommendUtil.log(getSessionUID(), id, resourceType, addedlists, Constant.ITEM_TYPE_LIST, Constant.BEHAVIOR_TYPE_EDIT);
		return SUCCESS;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	void updateIndex() {
		String redirectUrl = "item?id=" + id;
		String[] listArray = selectedList.split(",");

		List<String> tags = new ArrayList<String>();
		for (String lid : listArray) {
			tags.add(TagValuesConst.pITEM_IN_LIST + lid);
		}
		instance.addTags(redirectUrl, tags);

		try {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("optype", BehaviorConst.TYPE_IN_LIST);
			m.put("ids", selectedList);
			m.put("userId", getSessionUID());
			instance.changeDocContent(redirectUrl, m);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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
}
