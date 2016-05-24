package com.free4lab.freeshare.action.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.BehaviorConst;
import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.Constant;
import com.free4lab.freeshare.action.recommend.RecommendUtil;
import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

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
	private List<Integer> groupIds;
	private String readGroups;// 不需要就删除 to郄培
	private String writeGroups;

	NewIndexManager nim = NewIndexManager.getInstance();
	
	/**
	 * 更新版本预操作，获取资源基本信息
	 * @return
	 */
	public String pre() {
		//TODO:
	    ResourceWrapper wrapper = new ResourceManager().readResourceWrapper(id);
		setName(wrapper.getName());
		setDescription(wrapper.getDescription());
		setEnclosure(wrapper.getContent());
		setExtension(wrapper.getExtend());
		setGroupIds(wrapper.getGroupIds());
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
		//TODO:
		ResourceWrapper adapter = new ResourceWrapper(ResourceTypeConst.TYPE_DOC,
				name, description, userId, enclosure, extension,groupIds);
		adapter.setId(id);
		
		Resource resource = new ResourceManager().readResource(id);
		resource.setAttachment(enclosure);
		new ResourceManager().updateResource(resource);
		if (new ResourceManager().setDocVersions(userId,enclosure,description,id) == false) {
			logger.error("更新版本失败。");
			return "storerror";
		}

		updateIndex();		
		RecommendUtil.postLog(getSessionUID(), id, Constant.BEHAVIOR_TYPE_EDIT, adapter);
		return SUCCESS;
	}
	
	void updateIndex(){
		String redirectUrl = "resource?id=" + id;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("optype", BehaviorConst.TYPE_NEWVERSION);
		m.put("userId", getSessionUID());
		m.put("srcUrl", enclosure);
		try {
			nim.changeDocContent(redirectUrl, m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String updateDesc() {
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("description", description);
		try {
			nim.changeDocContent(enclosure, m);
			new ResourceManager().editDocVersions(enclosure, description);
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
    public List<Integer> getGroupIds() {
        return groupIds;
    }
    public void setGroupIds(List<Integer> groupIds) {
        this.groupIds = groupIds;
    }

}
