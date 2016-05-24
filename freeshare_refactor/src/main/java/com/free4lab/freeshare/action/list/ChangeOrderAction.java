package com.free4lab.freeshare.action.list;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.URLConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.ListRelationManager;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.manager.search.SearchManager;
import com.free4lab.freeshare.model.data.SrhResult;
import com.free4lab.freeshare.model.data.Doc4Srh;
import com.free4lab.utils.http.SearchClient;
import com.free4lab.utils.perflog.FatalLogger;

/**
 * 处理对列表中的资源调整次序,采用资源位置互换的方式
 * 
 * @author zhaowei
 * 
 */
public class ChangeOrderAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(ChangeOrderAction.class);
	private Integer listId ;
	private Integer resource_id ;
	private Integer target_order ;
	
	public String execute() {
		ListRelationManager manager =  new ListRelationManager();
		manager.changeOrderInList(resource_id,target_order,listId);
		return SUCCESS;
	}
	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public Integer getResource_id() {
		return resource_id;
	}

	public void setResource_id(Integer resource_id) {
		this.resource_id = resource_id;
	}

	public Integer getTarget_order() {
		return target_order;
	}

	public void setTarget_order(Integer target_order) {
		this.target_order = target_order;
	}

	/*public static void main(String[] arg){
		ListRelationManager manager = ListRelationManager.getInstance();
		manager.changeOrderInList(resource_id,target_order,listId);
	}*/

}

