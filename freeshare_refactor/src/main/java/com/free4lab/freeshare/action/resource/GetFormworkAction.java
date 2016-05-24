package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.CollectionsAction;
import com.free4lab.freeshare.handler.FormworkHandler;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.model.dao.Formwork;

public class GetFormworkAction  extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(GetFormworkAction.class);
	
	private List<Formwork> fmList;
	
	private List<Formwork> view_fmList;
	private Integer groupId;
	private String selectedgroups;
	private String writeGroupNames;
	private String selectedvalue;
	
	public String execute(){
		logger.info("GetFormworkAction");
		fmList = new FormworkHandler().findAll();
		view_fmList = new ArrayList<Formwork>();
		if(fmList != null){
			for(int i = 0; i <= 9 && i < fmList.size(); i++)
			{ 
				Formwork fm = fmList.get(i);
				view_fmList.add(fm);
			}
		}
		if(null != groupId && !("").equals(groupId)){
			fillGroupValue(groupId);
		}
		return SUCCESS;
	}
	public String setGroupForUpLoadTopic(){
		if(null != groupId && !("").equals(groupId)){
			fillGroupValue(groupId);
		}
		return SUCCESS;
	}
	private void fillGroupValue(Integer groupId){
		selectedgroups = Integer.toString(groupId) + ",";
		writeGroupNames = GroupManager.getSimpGroup(groupId).getGroupInfo().get("name") ;
		selectedvalue = groupId + ":" + writeGroupNames+ ",";	
	}

	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public List<Formwork> getView_fmList() {
		return view_fmList;
	}

	public void setView_fmList(List<Formwork> view_fmList) {
		this.view_fmList = view_fmList;
	}

	public List<Formwork> getFmList() {
		return fmList;
	}

	public void setFmList(List<Formwork> fmList) {
		this.fmList = fmList;
	}
	public String getSelectedgroups() {
		return selectedgroups;
	}
	public void setSelectedgroups(String selectedgroups) {
		this.selectedgroups = selectedgroups;
	}
	public String getWriteGroupNames() {
		return writeGroupNames;
	}
	public void setWriteGroupNames(String writeGroupNames) {
		this.writeGroupNames = writeGroupNames;
	}
	public String getSelectedvalue() {
		return selectedvalue;
	}
	public void setSelectedvalue(String selectedvalue) {
		this.selectedvalue = selectedvalue;
	}

}
