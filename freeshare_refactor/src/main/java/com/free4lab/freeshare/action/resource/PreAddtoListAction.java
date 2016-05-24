package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
//import com.free4lab.freeshare.handler.ListHandler;
import com.free4lab.freeshare.manager.search.SearchManager;
//import com.free4lab.freeshare.model.dao.Lists;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.model.dao.ResourceDAO;
import com.free4lab.freeshare.model.dao.UserLatestList;
import com.free4lab.freeshare.model.dao.UserLatestListDAO;
import com.free4lab.freeshare.model.data.Doc4Srh;
import com.free4lab.freeshare.model.data.ListBrief;
import com.free4lab.freeshare.model.data.SrhResult;

/**
 * 找到最近使用的列表
 * @author lzc
 *
 */
public class PreAddtoListAction extends BaseAction {

	private static final long serialVersionUID = -7096302954488249474L;
	private static Logger logger = Logger.getLogger(PreAddtoListAction.class);
	private List<ListBrief> lists;		//列表信息
	private Integer id;				//资源Id
	private String method;					//确定提交时，要调用的js方法
	private String selectedvalue;
	private final static Integer PAGE = 1;	//固定以1页呈现
	private final static Integer MAX_SIZE = Integer.MAX_VALUE; //分页大小为整型最大值
	
	/**
	 * 查询“最近使用的列表”
	 */
	public String execute() {
		// 初始化
		setLists(new ArrayList<ListBrief>());
		Integer uid = this.getSessionUID();
		// 从数据库读取用户的最近操作记录 ->userLatestList
		UserLatestList userLatestList = new UserLatestListDAO()
				.findLatestListByUid(uid);
		if (userLatestList == null) {
			return SUCCESS;
		}
		// 从数据库读取用户的最近列表 ->latestList
		String listStr = userLatestList.getLatestList();
		String[] listArray = listStr.split(",");
		List<Integer> latestList = new ArrayList<Integer>();
		for (int i = 0; i < listArray.length; i++) {
			if (listArray[i] != "" && !listArray[i].equals("")) {
				latestList.add(Integer.parseInt(listArray[i]));
			}
		}
		logger.info("latestList:" + latestList);
		//int updatelist = 0;//是否需要更新最近列表记录，主要是看原来记录中的列表是否已被删除
		// 如果最近的列表存在，则把列表id+name -> lists
		for (Integer listId : latestList) {
			Resource resource = new ResourceDAO().getResource(listId);
			if(resource != null)
			{
				lists.add(new ListBrief(listId, resource.getName()));
				logger.info("listId:" + listId + ",listName:" + resource.getName());
			}
			
		}
		return SUCCESS;
	}
	
	/**
	 * 查询我的列表
	 */
	public String myList() {
		// 搜索列表的标签
		String tags = TagValuesConst.pAUTHOR + getSessionUID();
		tags += " AND " + TagValuesConst.pRESOURCE_LIST;
		// 获取列表信息
		SrhResult srhResult = SearchManager.searchItems(NULL_QUERY, PAGE,
				MAX_SIZE, tags);
		if (srhResult == null) {
			return SUCCESS;
		}
		
		setLists(new ArrayList<ListBrief>());// 初始化lists
		Integer loc = new Integer(0);
		Integer listId = new Integer(0);

		for (Doc4Srh doc : srhResult.getDocs()) {
			loc = doc.getUrl().indexOf("=");// 其实默认为8
			listId = Integer.parseInt(doc.getUrl().substring(loc + 1));
			lists.add(new ListBrief(listId, doc.getTitle()));
			logger.info("listId:" + listId + ",listName:" + doc.getTitle());
		}
		return SUCCESS;
	}
	
	
	public List<ListBrief> getLists() {
		return lists;
	}

	public void setLists(List<ListBrief> lists) {
		this.lists = lists;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSelectedvalue() {
		return selectedvalue;
	}

	public void setSelectedvalue(String selectedvalue) {
		this.selectedvalue = selectedvalue;
	}
	
/*	public static void main(String[] arg)
	{
		//测试execute() 更改uid=122
		PreAddtoListAction paddtolist = new PreAddtoListAction();
		paddtolist.execute();
		
		//测试myList() 更改uid=127
		PreAddtoListAction paddtolist = new PreAddtoListAction();
		paddtolist.myList();
		
		
	}*/
}
