package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.handler.FormworkHandler;
import com.free4lab.freeshare.handler.Handler;
import com.free4lab.freeshare.handler.HandlerFactory;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.HttpApiManager;
import com.free4lab.freeshare.manager.factory.PermissionFactory;
import com.free4lab.freeshare.model.adapter.ObjectAdapter;
import com.free4lab.freeshare.model.dao.Formwork;
import com.free4lab.freeshare.model.data.DraftObject;
import com.free4lab.utils.http.DiskClient;

public class UpdateItemInfoAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(UpdateItemInfoAction.class);
	private Integer id;
	private Integer type;
	private String link;
	private String name;
	private String enclosure;
	// 附件的名称
	private String attachment;
	private String extension;
	private String permission;
	private String description;
	private String selectedgroups;
	private String writeGroupNames;
	private String selectedvalue;
	private String toMySelf;// 标识是有归属群组还是仅自己可见
	private String configMySelf;// 标识是有归属群组还是仅自己可见

	public List<Formwork> fmList;
	public List<Formwork> view_fmList;
	public List<Formwork> hidden_fmList;

	// 进入编辑之前的一些数据预处理工作
	public String pre() {

		fmList = new FormworkHandler().findAll();
		view_fmList = new ArrayList<Formwork>();
		if (fmList != null) {
			for (int i = 0; i <= 9 && i < fmList.size(); i++) {
				Formwork fm = fmList.get(i);
				view_fmList.add(fm);
			}
		}

		ObjectAdapter adapter = new ObjectAdapter(0);
		Handler handler = HandlerFactory.getHandler(adapter);
		adapter = handler.getObject(id);
		if (null == adapter) {
			logger.info("找不到相关的资源");
			return INPUT;
		}
		List<Integer> idList = PermissionFactory.getItemPermissionManager()
				.findWriteTeams(id);
		if (idList == null || idList.size() == 0) {
			configMySelf = "self1";
		} else {
			configMySelf = "self0";
		}
		// 群组名称的显示，会在该函数里定义已选群组的selectedgroups，writeGroupNames，selectedvalue
		evaluteGroups(idList);
		// 资源类型
		setType(adapter.getType());
		// setLink(item.getContent());
		// 如果是链接、视频，则是url，如果是附件，则是附件的uuid
		setEnclosure(adapter.getContent());
		if (adapter.getType().equals(ResourceTypeConst.TYPE_DOC)) {
			evaluteAttachment(adapter.getContent());
		} else {
			setAttachment("");
		}
		// 资源id
		setId(id);
		// 资源名称
		setName(adapter.getName());
		// 资源描述
		setDescription(adapter.getDescription());
		return SUCCESS;
	}

	@Deprecated
	public String draft() {
		String url = "http://localhost:9090/";
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> l = new ArrayList<String>();
		l.add(id.toString());
		params.put("id", l);
		String result = HttpApiManager.invokeApi(url, params);
		try {
			if (result != null) {
				JSONArray array = new JSONArray(result);
				DraftObject draftObject = new DraftObject(array.get(0)
						.toString());
				if (draftObject.getToMySelf() != null
						&& draftObject.getToMySelf() == "") {
					configMySelf = "self1";
					// 群组名称的显示，会在该函数里定义已选群组的selectedgroups，writeGroupNames，selectedvalue
					evaluteGroups(idList(draftObject.getWritegroups()));
				} else {
					configMySelf = "self0";
				}
				// 资源类型
				setType(draftObject.getType());
				// setLink(item.getContent());
				// 如果是链接、视频，则是url，如果是附件，则是附件的uuid
				setEnclosure(draftObject.getEnclosure());
				if (draftObject.getType().equals(ResourceTypeConst.TYPE_DOC)) {
					evaluteAttachment(enclosure);
				} else {
					setAttachment("");
				}
				// 资源id
				setId(id);
				// 资源名称
				setName(draftObject.getName());
				// 资源描述
				setDescription(draftObject.getDescription());
			} else {
				logger.info("找不到相关的资源");
				return INPUT;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	// 设置附件的名称，用于前端显示
	private void evaluteAttachment(String docUuid) {
		try {
			String filename = DiskClient.getFileName(docUuid);
			setAttachment(filename);
		} catch (Exception ex) {
			logger.debug("upload error:" + ex.getMessage());
		}
	}

	private List<Integer> idList(String writegroups) {
		String[] writeIdArray = writegroups.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (String gid : writeIdArray) {
			Integer groupId = Integer.parseInt(gid);
			idList.add(groupId);
		}
		return idList;
	}

	public void preModifyBGroup() {
		logger.info("begain selectedvalue: " + selectedvalue);
		logger.info("id" + id);
		List<Integer> idList = new LinkedList<Integer>();
		evaluteGroups(idList);
	}

	// 群组名称的显示，会在该函数里定义已选群组的selectedgroups，writeGroupNames，selectedvalue
	private void evaluteGroups(List<Integer> idList) {
		selectedgroups = "";
		writeGroupNames = "";
		selectedvalue = "";
		if (idList != null && idList.size() > 0) {
			selectedvalue = ",";
			for (Integer i : idList) {
				selectedgroups = selectedgroups + Integer.toString(i) + ",";
				writeGroupNames = (String) (writeGroupNames
						+ GroupManager.getSimpGroup(i).getGroupInfo()
								.get("name") + ",");

				selectedvalue = (String) (selectedvalue
						+ Integer.toString(i)
						+ ":"
						+ GroupManager.getSimpGroup(i).getGroupInfo()
								.get("name") + ",");
			}
			writeGroupNames = writeGroupNames.substring(0,
					writeGroupNames.length() - 1);

		}
		logger.info("last selectedvalue: " + selectedvalue);

	}

	public void preModifyBList() {
		logger.info("modifyBelongedList:");
		logger.info("id" + id);

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getToMySelf() {
		return toMySelf;
	}

	public void setToMySelf(String toMySelf) {
		this.toMySelf = toMySelf;
	}

	public String getSelectedvalue() {
		return selectedvalue;
	}

	public void setSelectedvalue(String selectedvalue) {
		this.selectedvalue = selectedvalue;
	}

	public String getConfigMySelf() {
		return configMySelf;
	}

	public void setConfigMySelf(String configMySelf) {
		this.configMySelf = configMySelf;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
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
}
