package com.free4lab.freeshare.action.resource;
import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.model.dao.UserLatestList;
import com.free4lab.freeshare.model.dao.UserLatestListDAO;

public class AddtoGroupAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7512181582687824094L;
	private static final Logger logger = Logger
			.getLogger(AddtoGroupAction.class);
	private String selectedGroup; // 要加入的列表id

	public String execute() {
		logger.info("AddtoGroupAction");
		String[] listArray = selectedGroup.split(",");
		// 更新数据库内容UserLatestList表
		Integer uid = getSessionUID();
		updateUserLatestList(uid, listArray);
		return SUCCESS;
	}

	private void updateUserLatestList(Integer uid, String[] listArray) {

		UserLatestListDAO userLatestListDAO = new UserLatestListDAO();
		UserLatestList userLatestList = userLatestListDAO
				.findLatestListByUid(uid);
		// 若之前没有uid的记录，则插入一条
		if (userLatestList == null) {
			userLatestListDAO.create(uid, ",", "," + selectedGroup);
		} else {
			String newList = userLatestList.getLatestGroup();
			logger.info("oldList:" + newList + "----selectedList:"
					+ selectedGroup);
			for (String list : listArray) {
				if (list != "" && !list.equals("")) {
					if (newList.contains("," + list + ",")) {
						newList = newList.replace("," + list + ",", ",");
					}
					newList = "," + list + newList;
				}
			}
			// 若newList中有多于10个listId，则去掉后面的，只保留10个
			// 存储内容为:用,分隔得到的数组长度为3，第一个元素为空串 .例如:",2,3,"
			String[] newListArray = newList.split(",");
			if (newListArray.length > 11) {
				newList = ",";
				for (int i = 1; i <= 10; i++) {
					newList += newListArray[i] + ",";
				}
			}
			userLatestListDAO.updateLatestGroup(uid, newList);
		}
	}
	public void modifyGroup(){
		logger.info("selectedGroup:"+selectedGroup);
	}

	public String getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(String selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

}
