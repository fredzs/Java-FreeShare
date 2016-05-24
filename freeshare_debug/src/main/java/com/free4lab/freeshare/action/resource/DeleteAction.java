package com.free4lab.freeshare.action.resource;

import java.io.IOException;
import java.util.List;
import org.apache.commons.httpclient.HttpException;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.Constant;
import com.free4lab.freeshare.action.recommend.RecommendUtil;
import com.free4lab.freeshare.handler.Handler;
import com.free4lab.freeshare.handler.HandlerFactory;
import com.free4lab.freeshare.manager.RelationManager;
import com.free4lab.freeshare.manager.permission.IPermissionManager;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.manager.factory.PermissionFactory;
import com.free4lab.freeshare.model.adapter.ObjectAdapter;
import com.free4lab.freeshare.model.dao.UserCollectionDAO;
import com.free4lab.freeshare.model.dao.UserLatestList;
import com.free4lab.freeshare.model.dao.UserScoreDAO;
import com.free4lab.utils.http.DiskClient;
import com.free4lab.freeshare.model.dao.UserLatestListDAO;

public class DeleteAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2358063862038647315L;
	private Integer id;
	private Integer type;
	private String url;
	private String uuid;
	NewIndexManager instance = NewIndexManager.getInstance();
	public String execute() {
		ObjectAdapter adapter = new ObjectAdapter(type);
		delete(adapter);
		//记录用户行为日志
		RecommendUtil.log(getSessionUID(), id, type, Constant.BEHAVIOR_TYPE_DELETE);
		return SUCCESS;
	}

	private void delete(ObjectAdapter adapter) {
		Handler handler = HandlerFactory.getHandler(adapter);
		handler.delete(id);
		if(type == null || type != -1){
			deleteItem();
		}else{
			deleteList();
		}
		
	}
	//TODO 删除历史版本
	private void deleteItem() {
		instance.delIndex("item?id=" + id);
		new UserScoreDAO().delete(id);
		new UserCollectionDAO().delete(getSessionUID(), id, "item");
		(new RelationManager()).delelteItemRelations(id);
		deletePermission();
	}

	private void deleteList() {
		instance.delIndex("list?id=" + id);
		new UserScoreDAO().delete(id);
		new UserCollectionDAO().delete(getSessionUID(), id, "list");
		(new RelationManager()).deleltelistRelations(id);
		UserLatestList userLatestList = new UserLatestListDAO().findLatestListByUid(getSessionUID());
		String listStr = userLatestList.getLatestList();
		listStr = listStr.replace(","+id+"," , ",");
		new UserLatestListDAO().updateLatestList(getSessionUID(), listStr);
		deletePermission();
	}

	private void deletePermission() {
		IPermissionManager pm = PermissionFactory.getPermissionManager(type);
		List<Integer> idList = pm.findWriteTeams(id);
		for(Integer groupId : idList){
			pm.deletePermission(id, groupId);
		}
	}

	public String deleteversion() {
		new NewIndexManager().delIndex(url);
		try {
			DiskClient.delete(url);
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
