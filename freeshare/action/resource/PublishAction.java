package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

import com.free4lab.freeshare.BehaviorConst;
import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.Constant;
import com.free4lab.freeshare.action.recommend.RecommendUtil;
import com.free4lab.freeshare.handler.HandlerFactory;
import com.free4lab.freeshare.handler.Handler;
import com.free4lab.freeshare.manager.FreeUserScoreManager;
import com.free4lab.freeshare.manager.TagsManager;
import com.free4lab.freeshare.manager.factory.PermissionFactory;
import com.free4lab.freeshare.manager.factory.ScoreManagerFactory;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.model.adapter.ObjectAdapter;
import com.free4lab.freeshare.model.dao.DocVersionDAO;
import com.free4lab.freeshare.model.data.BasicContent;

/**
 * 发布普通资源、列表还有编辑普通资源的处理类 <br/>
 * 编辑列表的处理过程待重构到此类
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
	private List<Integer> groupIds;
	private NewIndexManager nim = NewIndexManager.getInstance();

	public String execute() {
		if (check() == false) {
			return INPUT;
		}
		userId = getSessionUID();
		ObjectAdapter adapter = new ObjectAdapter(type, name, description,
				userId, enclosure, extension);

		handle(adapter);
		adapter.setGroupIds(groupIds);
		labelsOperate(adapter);
		nim.addIndex(adapter);

		versionOperate();
		RecommendUtil.postLog(getSessionUID(), id, Constant.BEHAVIOR_TYPE_EDIT, adapter);
		FreeUserScoreManager.updateFus(userId, 0, 1);
		status = "success";
		logger.debug("创建成功。");
		return SUCCESS;
	}

	// 根据id是否为null，判断是发布新资源还是编辑资源，然后调用不同方法处理
	private void handle(ObjectAdapter adapter) {
		Handler handler = HandlerFactory.getHandler(adapter);
		if (id != null) {
			adapter.setId(id);
			handler.update(adapter);
			groupIds = updatePermission();
		} else {
			handler.save(adapter);
			setId(adapter.getId());
			groupIds = savePermission();
			ScoreManagerFactory.getScoreManager(type).addPublish(id, userId);
		}
	}

	private List<Integer> savePermission() {
		List<Integer> groupIds = new ArrayList<Integer>();
		if (!toMySelf.equals("self1")) {
			groupIds = PermissionFactory.getPermissionManager(type)
					.savePermission(id, "private", writegroups, "");
		}
		if (groupIds != null) {
			return groupIds;
		}
		return new ArrayList<Integer>();
	}
	public void modifyGroup(){
		logger.info("writegroups:"+writegroups);
		toMySelf = "self0";
		updatePermission();
	}
	private List<Integer> updatePermission() {
		List<Integer> idList = new ArrayList<Integer>();// 更新后的所属群组
		// 不是自己可见，且存在已选群组
		if (!toMySelf.equals("self1") && writegroups != null) {
			String[] writeIdArray = writegroups.split(",");
			for (String gid : writeIdArray) {
				idList.add(Integer.parseInt(gid));
			}
		}

		updatePermission(idList);
		if (!toMySelf.equals("self1") && writegroups != null) {
			PermissionFactory.getItemPermissionManager().savePermission(id,
					"private", writegroups, "");
		}
		return idList;
	}

	private void updatePermission(List<Integer> idList) {
		List<String> tagValues = new ArrayList<String>();
		List<Integer> list = PermissionFactory.getItemPermissionManager()
				.findWriteTeams(id);
		if (!(idList.containsAll(list) && idList.size() == list.size())) {
			// 删除原有的所有的归属群组的tag和本地保存的资源、群组对应关系
			for (Integer groupId : list) {
				tagValues.add(TagValuesConst.pGROUP + groupId);
				tagValues.add(TagValuesConst.pGROUP_ITEM + groupId);

				PermissionFactory.getItemPermissionManager().deletePermission(
						id, groupId);
			}
			nim.removeTags(new String("item?id=" + id), tagValues);

		}
	}

	void versionOperate() {
		if (type.equals(ResourceTypeConst.TYPE_DOC)) {
			new DocVersionDAO().create(userId, enclosure, description, id);

			List<String> versionTags = new ArrayList<String>();
			versionTags.add(TagValuesConst.pITEM_VERSION + id);

			BasicContent versionContent = new BasicContent(getSessionUID(),
					delhtml(description), ResourceTypeConst.TYPE_VERSION,
					BehaviorConst.TYPE_CREATE);
			nim.addIndex(enclosure, name, versionContent.toString(),
					versionTags);
		}
	}

	void labelsOperate(ObjectAdapter adapter) {
		if (labels != null && !labels.equals("")) {
			adapter.setLables(Arrays.asList(labels.split(",")));
			new TagsManager().addLabels(adapter.getAuthorId(), labels,
					newLabels, adapter.getId(), adapter.getType());
		}
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
}
