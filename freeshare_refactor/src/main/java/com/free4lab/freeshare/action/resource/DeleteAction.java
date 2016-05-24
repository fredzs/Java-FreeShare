package com.free4lab.freeshare.action.resource;

import java.io.IOException;
import java.util.List;
import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.Constant;
import com.free4lab.freeshare.action.recommend.RecommendUtil;
import com.free4lab.freeshare.handler.HandlerBootstrap;
import com.free4lab.freeshare.manager.ListRelationManager;
import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.model.dao.DocVersionDAO;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.model.dao.UserCollectionDAO;
import com.free4lab.freeshare.model.dao.UserLatestList;
import com.free4lab.freeshare.model.dao.UserScoreDAO;
import com.free4lab.utils.http.DiskClient;
import com.free4lab.freeshare.model.dao.UserLatestListDAO;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

public class DeleteAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2358063862038647315L;
	private static final Logger logger = Logger.getLogger(DeleteAction.class);
	private Integer id;
	private Integer type;
	private String url;
	private String uuid;
	NewIndexManager instance = NewIndexManager.getInstance();
	public String execute() {
		new ResourceManager().deleteResource(id);
		logger.info("type"+type+"id"+id);
		if(type == -1){
			logger.info("deleteList"+id);
			deleteList();
		}else{
			logger.info("deleteItem"+id);
			deleteItem();
			
		}
		
		//记录用户行为日志
		RecommendUtil.log(getSessionUID(), id, type, Constant.BEHAVIOR_TYPE_DELETE);
		return SUCCESS;
	}

	//TODO 删除历史版本
	private void deleteItem() {
		instance.delIndex(instance.indexUrl(id));
		new UserScoreDAO().delete(id);
		new UserCollectionDAO().delete(getSessionUID(), id, "item");
		new ListRelationManager().deleteResourceRelations(id);
	}

	private void deleteList() {
		instance.delIndex("resource?id=" + id);
		new UserScoreDAO().delete(id);
		new UserCollectionDAO().delete(getSessionUID(), id, "list");
		new ListRelationManager().deleteListRelations(id);
		UserLatestList userLatestList = new UserLatestListDAO().findLatestListByUid(getSessionUID());
		String listStr = userLatestList.getLatestList();
		listStr = listStr.replace(","+id+"," , ",");
		new UserLatestListDAO().updateLatestList(getSessionUID(), listStr);
	}


	public String deleteversion() {
		new NewIndexManager().delIndex(url);
		try {
			DiskClient.delete(url);
			new DocVersionDAO().deleteByUUid(url);
			
			Resource resource = new ResourceManager().readResource(id);
			Integer idd= new DocVersionDAO().updateUuidAfterDelete(id);
			resource.setAttachment(new DocVersionDAO().findById(idd).getUuid());
			new ResourceManager().updateResource(resource);
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
