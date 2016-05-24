package com.free4lab.freeshare.action.group;

import java.util.ArrayList;
import java.util.List;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.model.dao.ReGroup;
import com.free4lab.freeshare.model.dao.ReGroupDAO;
import com.free4lab.freeshare.model.data.Group;

public class ReGroupAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String reGroupIds;
	private List<Integer> groupIds;
	private List<Group> groupList;
	private ReGroupDAO rgd = new ReGroupDAO();

	public String findAllRegroup() {
		if(reGroupIds != null || !reGroupIds.equals("")){
			List<ReGroup> rgl = rgd.findAll();
			groupList = new ArrayList<Group>();
			if (rgl != null && rgl.size() > 0) {
				for (ReGroup r : rgl) {
					Group g = GroupManager.getSimpGroup(r.getReGroupId());
					if (g != null) {
						groupList.add(g);
					}
				}
			}
		}else{
			obtainGroupIds();
			for (Integer groupId : groupIds) {
				Group g = GroupManager.getSimpGroup(groupId);
				if (g != null) {
					groupList.add(g);
				}
			}
		}
		
		return SUCCESS;
	}

	public String delReGroup() {
		if (id != null) {
			rgd.delByGroupId(id);
		}
		return SUCCESS;
	}

	public String addReGroup() {
		ReGroup rg = new ReGroup();
		Group group;
		for (Integer groupId : groupIds) {
			group = GroupManager.getSimpGroup(groupId);
			if (group != null) {
				id = group.getGroupId();
				if (id != null) {
					rg.setReGroupId(id);
					rg.setReNum(0);
				}
				rgd.save(rg);
			} else
				continue;
		}
		return SUCCESS;
	}
	private List<Integer> obtainGroupIds(){
		String[] idArray = reGroupIds.split(",");
		for (String gid : idArray) {
			Integer groupId = Integer.parseInt(gid);
			groupIds.add(groupId);
		}
		return groupIds;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReGroupIds() {
		return reGroupIds;
	}

	public void setReGroupIds(String reGroupIds) {
		this.reGroupIds = reGroupIds;
	}

	public List<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Integer> groupIds) {
		this.groupIds = groupIds;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}
}
