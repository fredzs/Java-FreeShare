package com.free4lab.freeshare.action.comment;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.handler.Handler;
import com.free4lab.freeshare.handler.HandlerFactory;
import com.free4lab.freeshare.model.adapter.ObjectAdapter;
import com.free4lab.freeshare.model.data.Doc4Srh;
import com.free4lab.utils.userinfo.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class CommentObtainAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(CommentObtainAction.class);
	private Integer id;
	private Integer resourceType; // 标识是资源还是列表，用以在打开资源单页的时候标识
	private Integer page;
	private ObjectAdapter info;
	private List<Doc4Srh> docs;
	private Map<Integer, UserInfo> m;
	//TODO 查找为何需要info对象，资源单页已经获取info这里又要获取一次，重复操作了？？？
	public String execute() {
		logger.info("the type is " + resourceType);
		CommentParse fc = new CommentParse();
		docs = new ArrayList<Doc4Srh>();
		docs = fc.findComment(id, resourceType, page);
		info = new ObjectAdapter(resourceType);
		Handler handler = HandlerFactory.getHandler(info);
		info = handler.getObject(id);

		m = fc.findUserAvatar(docs, getSessionToken());

		return SUCCESS;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}


	public ObjectAdapter getInfo() {
		return info;
	}

	public void setInfo(ObjectAdapter info) {
		this.info = info;
	}

	public List<Doc4Srh> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc4Srh> docs) {
		this.docs = docs;
	}

	public Map<Integer, UserInfo> getM() {
		return m;
	}

	public void setM(Map<Integer, UserInfo> m) {
		this.m = m;
	}
}
