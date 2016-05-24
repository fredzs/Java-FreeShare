package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.manager.search.SearchManager;
import com.free4lab.freeshare.model.data.Doc4Srh;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.model.data.ListBrief;
import com.free4lab.freeshare.model.data.SrhResult;

/**
 * 加入列表：搜索我所在群组的列表
 * @author lzc
 *
 */
public class AddtoListSearchAction extends BaseAction {

	private static final long serialVersionUID = -7096302954488249474L;
	private static final Logger logger = Logger.getLogger(AddtoListSearchAction.class);
	private List<ListBrief> lists;		//列表信息
	private Integer test;
	public Integer getTest() {
		return test;
	}

	public void setTest(Integer test) {
		this.test = test;
	}
	private Integer itemId;				//资源ID
	private String searchText;			//搜索的字符串

	private final static Integer PAGE = 1;	//固定以1页呈现
	private final static Integer MAX_SIZE = Integer.MAX_VALUE;//分页大小为整型最大值
	
	public String execute(){
		
		setLists(new ArrayList<ListBrief>());//初始化lists
		test = 1;
	/*通过search，得到用户所属群组的，名称包含searchTex的全部列表*/
		SrhResult srhResult = null;
		//获取用户所在的群组信息
		List<Group> groups = GroupUserManager.getMyGroups(getSessionUID());
		if(groups==null ){
			return SUCCESS;
		}

		String tags = "";//搜索相关的标签
		for(Group g : groups){
			tags += " " + TagValuesConst.pGROUP_LIST + g.getGroupId();
		}
		//获取搜索到的列表信息
		srhResult = SearchManager.searchItems(searchText, PAGE, MAX_SIZE, tags);
		if(srhResult==null){
			return SUCCESS;
		}

	/*遍历search搜索结果，将列表信息返回*/
		setLists(new ArrayList<ListBrief>());//初始化lists
		Integer loc = new Integer(0);
		Integer listId = new Integer(0);
		for (Doc4Srh doc : srhResult.getDocs()){
			loc = doc.getUrl().indexOf("=");//其实默认为8
			listId = Integer.parseInt(doc.getUrl().substring(loc + 1));
			lists.add(new ListBrief(listId,	delhtml(doc.getTitle())));
		}
		return SUCCESS;		
	}
	
	public List<ListBrief> getLists() {
		return lists;
	}
	public void setLists(List<ListBrief> lists) {
		this.lists = lists;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
