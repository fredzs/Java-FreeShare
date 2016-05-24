package com.free4lab.freeshare.action.list;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.handler.HandlerBootstrap;

import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.ResourcePermissionManager;
import com.free4lab.freeshare.manager.factory.ResourcePermissionFactory;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

public class UpdateListInfoAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(UpdateListInfoAction.class);
	private Integer id;
	private String name;
	private String selectedgroups;
	private String writeGroupNames;
	private String selectedvalue;
	private String toMySelf;// 标识是有归属群组还是仅自己可见
	private String configMySelf;// 标识是有归属群组还是仅自己可见
	
	private String permission;
	private String description;
	private NewIndexManager instance = NewIndexManager.getInstance();

	public String execute() {
		//TODO:
		/*ResourceWrapper adapter = new ResourceWrapper(ResourceTypeConst.TYPE_LIST, name, delhtml(description), getSessionUID(), getImgSrc(description), "");
		adapter.setId(id);
		adapter.setGroupIds(updatePermission());
		HandlerBootstrap handler = HandlerFactory.getHandler(adapter);
		handler.update(adapter);
		instance.addIndex(adapter);*/
		logger.info("编辑列表成功。");
		return SUCCESS;
	}

	// TODO 待优化 即如何判断是否更改了归属群组，与更改后的操作应该是如何
	private List<Integer> updatePermission() {
		List<Integer> idList = new ArrayList<Integer>();// 更新后的所属群组
		if (!toMySelf.equals("self1") && selectedgroups != null) {	//不是自己可见，且存在已选群组		
			String[] writeIdArray = selectedgroups.split(",");
			for (String gid : writeIdArray) {
				idList.add(Integer.parseInt(gid));
			}			
		}
		List<Integer> list = new ResourcePermissionManager().selectWritableGroups(id);
		
		return updatePermission(list, idList);
	}
	private List<Integer> updatePermission(List<Integer> list, List<Integer> idList){
		List<Integer> groupIds = new ArrayList<Integer>();
		List<String> tagValues = new ArrayList<String>();
		if( !(idList.containsAll(list) && idList.size()==list.size()) ){
			//删除原有的所有的归属群组的tag
			//TODO:
			/*for(Integer groupId : list){
				tagValues.add(TagValuesConst.pGROUP + groupId); 
				tagValues.add(TagValuesConst.pGROUP_LIST + groupId);		
				PermissionFactory.getListPermissionManager().deletePermission(id, groupId);
			}
			instance.removeTags(new String("list?id=" + id), tagValues);
			tagValues.clear();
			//构造新的归属群组的tag
			for(Integer groupId : idList){
				groupIds.add(groupId);
				PermissionFactory.getListPermissionManager().setWritablePermission(id, groupId);
			}*/
			return groupIds;
		}
		return idList;
	}
	public String pre() {
		//TODO:
		/*Lists list =  new ListHandler().getObject(id).getList();
		if (null == list) {
			logger.info("找不到相关的列表");
			return INPUT;
		}
		// TODO 判断是进自己可见还是有归属群组
		List<Integer> idList = PermissionFactory.getListPermissionManager()
				.findWriteTeams(id);
		
		if(idList == null || idList.size() == 0){
			configMySelf = "self1";
		}else{
			configMySelf = "self0";
		}
		setId(id);
		evaluteGroups(idList);
		setName(list.getName());
		setDescription(list.getDescription());*/
		return SUCCESS;
	}
	//TODO 与更新资源中的重构
	private void evaluteGroups(List<Integer> idList) {
		selectedgroups = "";
		writeGroupNames = "";
		selectedvalue = "";
		if (idList != null && idList.size() > 0) {
			selectedvalue = ",";
			for (Integer i : idList) {
				selectedgroups = selectedgroups + Integer.toString(i) + ",";
				writeGroupNames = (String) (writeGroupNames
						+ GroupManager.getSimpGroup(i).getGroupInfo().get("name") + ",");
				
				selectedvalue = (String) (selectedvalue + Integer.toString(i) + ":"
						+ GroupManager.getSimpGroup(i).getGroupInfo().get("name") + ",");
			}
			writeGroupNames = writeGroupNames.substring(0,
					writeGroupNames.length() - 1);

		}
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSelectedvalue() {
		return selectedvalue;
	}

	public void setSelectedvalue(String selectedvalue) {
		this.selectedvalue = selectedvalue;
	}

	public String getToMySelf() {
		return toMySelf;
	}

	public void setToMySelf(String toMySelf) {
		this.toMySelf = toMySelf;
	}

	public String getConfigMySelf() {
		return configMySelf;
	}

	public void setConfigMySelf(String configMySelf) {
		this.configMySelf = configMySelf;
	}
	
}
