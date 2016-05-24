package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.BehaviorConst;
import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.Constant;
import com.free4lab.freeshare.action.recommend.RecommendUtil;
import com.free4lab.freeshare.handler.Handler;
import com.free4lab.freeshare.handler.HandlerFactory;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.model.adapter.ObjectAdapter;
import com.free4lab.freeshare.model.dao.DocVersionDAO;
import com.free4lab.freeshare.model.data.BasicContent;

public class VersionAction extends BaseAction {
	private static final long serialVersionUID = 9194313128703354941L;
	private static final Logger logger = Logger
			.getLogger(VersionAction.class);

	private Integer id;
	private Integer userId;
	private String name;
	private String description;
	private String enclosure;
	private String extension;
	private String readGroups;// 不需要就删除 to郄培
	private String writeGroups;

	NewIndexManager nim = NewIndexManager.getInstance();
	
	/**
	 * 更新版本预操作，获取资源基本信息
	 * @return
	 */
	public String pre() {
		ObjectAdapter adapter = new ObjectAdapter(0);
		Handler handler = HandlerFactory.getHandler(adapter);
		adapter = handler.getObject(id);
		setName(adapter.getName());
		return SUCCESS;
	}
	/**
	 * 处理新版请求
	 */
	public String execute() {
		if (enclosure == null || enclosure == "") {
			logger.error("获取上传文件的uuid失败。");
			return "null_uuid";
		}
		userId = getSessionUID();
		ObjectAdapter adapter = new ObjectAdapter(ResourceTypeConst.TYPE_DOC,
				name, description, userId, enclosure, extension);
		adapter.setId(id);
		Handler handler = HandlerFactory.getHandler(adapter);
		if (handler.update(adapter) == null) {
			logger.error("更新版本失败。");
			return "storerror";
		}

		updateIndex();
		versionOperate();
		
		RecommendUtil.postLog(getSessionUID(), id, Constant.BEHAVIOR_TYPE_EDIT, adapter);
		return SUCCESS;
	}
	
	void updateIndex(){
		String redirectUrl = "item?id=" + id;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("optype", BehaviorConst.TYPE_NEWVERSION);
		m.put("userId", getSessionUID());
		try {
			nim.changeDocContent(redirectUrl, m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void versionOperate() {
		List<String> versionTags = new ArrayList<String>();
		versionTags.add(TagValuesConst.pITEM_VERSION + id);
		new DocVersionDAO().create(userId, enclosure, description, id);
		BasicContent versionContent = new BasicContent(getSessionUID(),
				delhtml(description), ResourceTypeConst.TYPE_VERSION,
				BehaviorConst.TYPE_CREATE);
		nim.addIndex(enclosure, name, versionContent.toString(), versionTags);
	}

	public String updateDesc() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("description", description);
		try {
			nim.changeDocContent(enclosure, m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getReadGroups() {
		return readGroups;
	}

	public void setReadGroups(String readGroups) {
		this.readGroups = readGroups;
	}

	public String getWriteGroups() {
		return writeGroups;
	}

	public void setWriteGroups(String writeGroups) {
		this.writeGroups = writeGroups;
	}

}
