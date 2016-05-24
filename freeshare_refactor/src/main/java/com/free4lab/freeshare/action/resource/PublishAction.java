package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.Constant;
import com.free4lab.freeshare.action.recommend.RecommendUtil;
import com.free4lab.freeshare.handler.HandlerBootstrap;
import com.free4lab.freeshare.manager.FreeUserScoreManager;
import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

/**
 * 发布普通资源、列表还有编辑普通资源的处理类 <br/>
 * TODO 编辑列表的处理过程待重构到此类
 * 
 * @author zhaowei
 * @see com.free4lab.freeshare.action.list.UpdateListInfoAction
 */
public class PublishAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PublishAction.class);
	private Integer id;
	private Integer userId;
	private String name;
	private String description;
	private Integer type;
	private String extension; // 资源的后缀名，如果存在的话
	private String enclosure;
	private String toMySelf; // 标记是否近自己可见
	private String writegroups;
	private String status;
	private String labels; // 用户自定义的标签
	private String newLabels; // 用户的新添加的标签
	private List<Integer> groupIds = new ArrayList<Integer>();
	
	public String execute() {
		// 检查合法性
		if (check() == false) {
			return INPUT;
		}
		userId = getSessionUID();
		// writegroups -> groupIds
		if (!toMySelf.equals("self1") && writegroups != null) {
			String[] writeIdArray = writegroups.split(",");
			for (String gid : writeIdArray) {
				groupIds.add(Integer.parseInt(gid));
			}
		}

		// ResourceWrapper将资源进行封装，来满足对不同资源的操作需求。
		ResourceWrapper adapter = new ResourceWrapper(type, name, description,
				userId, enclosure, extension, groupIds);
		
		// id=null是新建 否则是更新
		boolean isedit = false;
		if (id != null) {
			adapter.setId(id);
			isedit = true;
		}
        
		// handler的链式结构进行调用 包括数据库操作-tags-index
		HandlerBootstrap.getInstance().handler(adapter);
		// 获取id
		this.setId(adapter.getId());
		// 日志
		RecommendUtil.postLog(userId, adapter.getId(),
				Constant.BEHAVIOR_TYPE_EDIT, adapter);
		// 更新积分
		FreeUserScoreManager.updateFus(userId, 0, 1);
		
		if(type==1 && !isedit){
			new ResourceManager().setDocVersions(userId,enclosure,description,id);
		}
		
		status = "success";
		logger.debug("创建成功。");
		return SUCCESS;
	}
	
	public void modifyGroup() {
		// 根据id读取resource
		Resource resource = new ResourceManager().readResource(id);
		resource.setAuthorId(getSessionUID());
		ResourceWrapper adapter = new ResourceWrapper(resource);
		toMySelf = "self0";

		// 设置resource的groupIds
		if (!toMySelf.equals("self1") && writegroups != null) {
			String[] writeIdArray = writegroups.split(",");
			for (String gid : writeIdArray) {
				groupIds.add(Integer.parseInt(gid));
			}
		}
		adapter.setGroupIds(groupIds);
		// handler的链式结构进行调用 包括数据库操作-tags-index
		HandlerBootstrap.getInstance().handler(adapter);
		// 日志
		RecommendUtil.postLog(userId, adapter.getId(),
				Constant.BEHAVIOR_TYPE_EDIT, adapter);
	}
	

	private boolean check() {
		if (toMySelf.equals("self0") && writegroups == null) {
			logger.info("不是保存给自己的资源，就一定要选择归属组。");
			return false;
		}
		if (type == null) {
			type = -1;
			return true;
		}
		if (type.equals(ResourceTypeConst.TYPE_TEXT)) {
			if (description == null) {
				return false;
			} else
				return true;
		} else {
			if (name == null || description == null) {
				return false;
			} else
				return true;
		}
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}

	public String getWritegroups() {
		return writegroups;
	}

	public void setWritegroups(String writegroups) {
		this.writegroups = writegroups;
	}

	public String getToMySelf() {
		return toMySelf;
	}

	public void setToMySelf(String toMySelf) {
		this.toMySelf = toMySelf;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getNewLabels() {
		return newLabels;
	}

	public void setNewLabels(String newLabels) {
		this.newLabels = newLabels;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
/*	public static void main(String[] arg)
	{
		// 修改群组只用传id和groups和myself0 虽然会报错但能更新
		PublishAction pa = new PublishAction();
		pa.setId(6076);
		pa.setType(-2);
		pa.setName("testtttttt");
		pa.setDescription("wwwwww");
		pa.setUserId(642);
		pa.setEnclosure("attachment");
		pa.setExtension("");
		pa.setToMySelf("self0");
		pa.setWritegroups("14,5,6");
		pa.groupIds = new ArrayList<Integer>(); 
		pa.groupIds.add(1);
		pa.groupIds.add(2);
		pa.execute();
		
	}*/
}
