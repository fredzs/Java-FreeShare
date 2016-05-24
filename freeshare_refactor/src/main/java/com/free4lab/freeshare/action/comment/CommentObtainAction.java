package com.free4lab.freeshare.action.comment;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.handler.HandlerBootstrap;
/*import com.free4lab.freeshare.handler.HandlerFactory;*/
import com.free4lab.freeshare.model.dao.Comment;
import com.free4lab.freeshare.model.dao.CommentDao;
import com.free4lab.freeshare.model.data.Doc4Srh;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;
import com.free4lab.utils.account.UserInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

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
	private ResourceWrapper info;
	private List<Comment> listcomment;
	private Map<Integer, UserInfo> m;
	//TODO 查找为何需要info对象，资源单页已经获取info这里又要获取一次，重复操作了？？？
	/*以前从search搜索评论
	 * public String execute() {
		logger.info("the type is " + resourceType);
		CommentParse fc = new CommentParse();
		docs = new ArrayList<Doc4Srh>();
		docs = fc.findComment(id, resourceType, page);
		//TODO:
		info = new ResourceWrapper(resourceType);
		HandlerBootstrap handler = HandlerFactory.getHandler(info);
		info = handler.getObject(id);

		m = fc.findUserAvatar(docs, getSessionToken());

		return SUCCESS;
	}*/
	
	// 从数据库获取评论
	public String execute() {
		logger.info("the type is " + resourceType);
		CommentDao comment = new CommentDao();		
		listcomment = comment.findCommentByProperty("resourceId",id,page-1,50);
		for (int i = 0; i <listcomment.size(); i++) {
			// 解析评论
			String[] fulldes = listcomment.get(i).getDescription().split(",");
			String[] cmtdes = fulldes[3].split(":");
			String cmt = cmtdes[1].replace("\\", "");
			cmt = cmt.substring(1, cmt.length()-1);
			listcomment.get(i).setDescription(cmt);
		}
		//TODO:
	/*	info = new ResourceWrapper(resourceType);
		HandlerBootstrap handler = HandlerFactory.getHandler(info);
		info = handler.getObject(id);*/
		CommentParse fc = new CommentParse();
		m = fc.findUserAvatar(listcomment, getSessionToken());

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


	public ResourceWrapper getInfo() {
		return info;
	}

	public void setInfo(ResourceWrapper info) {
		this.info = info;
	}

	public Map<Integer, UserInfo> getM() {
		return m;
	}

	public void setM(Map<Integer, UserInfo> m) {
		this.m = m;
	}

	public List<Comment> getListcomment() {
		return listcomment;
	}

	public void setListcomment(List<Comment> listcomment) {
		this.listcomment = listcomment;
	}
}
