package com.free4lab.freeshare.action.resource;
import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.model.dao.UserLatestList;
import com.free4lab.freeshare.model.dao.UserLatestListDAO;

public class AddtoGroupAction extends BaseAction {

	private static final long serialVersionUID = -7512181582687824094L;
	private static final Logger logger = Logger
			.getLogger(AddtoGroupAction.class);
	private String selectedGroup; // 要加入的列表id
	
	public String execute() {
		logger.info("AddtoGroupAction");
		logger.info("selectedGroupId:"+selectedGroup);
		String[] listArray = selectedGroup.split(",");
		updateUserLatestList(getSessionUID(), listArray);
		return SUCCESS;
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
			userLatestListDAO.create(uid, ",", "," + selectedGroup);
		} else {
			String newList = userLatestList.getLatestGroup();
			logger.info("oldList:" + newList + "----selectedList:"
					+ selectedGroup);
			// 把新操作的群组放在newList的最前面
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

	public void modifyGroup() {
		logger.info("selectedGroup:" + selectedGroup);
	}

	public String getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(String selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
	
/*	public static void main(String[] arg)
	{
		// 在user_latestlist查看更新 execute（）中修改uid=777 用后记得删除数据
		AddtoGroupAction ag = new AddtoGroupAction ();
		ag.setSelectedGroup("10,11");
		ag.execute();
		
	}*/
	
	
}
