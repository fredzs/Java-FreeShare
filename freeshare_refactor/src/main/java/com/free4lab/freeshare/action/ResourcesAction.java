package com.free4lab.freeshare.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.manager.search.SearchManager;
import com.free4lab.freeshare.model.data.SrhResult;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

/**
 * 获取我的全部资源，并根据用户选择资源类型进行相应筛选
 * @author zhaowei
 * 
 */
public class ResourcesAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4487948594123775045L;
	private static final Logger logger = Logger
			.getLogger(ResourcesAction.class);
	private Integer page;
	private Integer num;
	private List<ResourceWrapper> resource = new LinkedList<ResourceWrapper>();
	private String resourceTypeList;
	private static final Integer SIZE = 10;

	public String execute() {
		logger.info(resourceTypeList);
		
		page = (page == null) ? 1 : page;
		
		logger.info(getSessionUID());
		logger.info("the num is " + num);
		
		if(num == null){
			num = new ResourceManager().getResourceWrapperNum(getTypeList(), getSessionUID());
			logger.info(num);
		}
			
	    resource = new ResourceManager().readResourceWrapper(page,getTypeList(), getSessionUID());
		
		return SUCCESS;
		
	}
	
	public static void main(String[] args){
		ResourcesAction test = new ResourcesAction();
		test.getTypeList();
	}
	
	private List<Integer> getTypeList(){
		List<Integer> typeList = new ArrayList<Integer>();
		String tmp = resourceTypeList;
		while(tmp.length() != 0){
			String s = tmp.substring(tmp.lastIndexOf(',')+1, tmp.length());
			Integer test = Integer.parseInt(s);
			typeList.add(test);
			if(tmp.lastIndexOf(',')>0)
				tmp = tmp.substring(0,tmp.lastIndexOf(','));
			else
				tmp = "";
		}	
		logger.info(typeList);
		return typeList;
	}
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getResourceTypeList() {
		return resourceTypeList;
	}

	public void setResourceTypeList(String resourceTypeList) {
		this.resourceTypeList = resourceTypeList;
	}


	public List<ResourceWrapper> getResource() {
		return resource;
	}

	public void setResource(List<ResourceWrapper> resource) {
		this.resource = resource;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
}
