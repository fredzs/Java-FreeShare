package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.Constant;
import com.free4lab.freeshare.action.recommend.RecommendUtil;
import com.free4lab.freeshare.manager.ListRelationManager;
import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.manager.VideoManager;
import com.free4lab.freeshare.manager.factory.ScoreManagerFactory;
import com.free4lab.freeshare.manager.search.SearchManager;
import com.free4lab.freeshare.model.dao.DocVersion;
import com.free4lab.freeshare.model.dao.DocVersionDAO;
import com.free4lab.freeshare.model.dao.ListRelation;
import com.free4lab.freeshare.model.dao.Resource;
import com.free4lab.freeshare.model.dao.UserScore;
import com.free4lab.freeshare.model.dao.UserScoreDAO;
import com.free4lab.freeshare.model.data.Doc4Srh;
import com.free4lab.freeshare.model.data.SrhResult;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;

/**
 * 呃，谁能想一个好听的名字
 * 
 * @author zhaowei
 * 
 */
public class BrowseAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1490760110292510051L;
	private static final Logger logger = Logger.getLogger(BrowseAction.class);
	private Integer id; // ID
	private Integer resourceId;
	private ResourceWrapper info; // 资源信息
	private String authorName;
	private Integer authorId;
	private String videoUrl;
	private String description;
	private SrhResult srhResult;
	private List<DocVersion> docs;
	private Integer docsNum;
	private List<Resource> item; 
	private Integer itemNum;
	private String upDown;
	private Integer upNum;
	private Integer downNum;
	private Integer lookNum;
	private Integer avgNum;

	private Integer permission;// 用以判断前端显示那些按钮的不同权限

	private Map<Integer, UserInfo> map; // 用户id与头像的map

	private Integer resourceType;// 标识是资源还是列表

	private Integer banner;
	private Integer left;
	private Integer right;
	private Integer evaluate;
	private Integer function;
	private Integer comment;
	private Integer page;
	private Integer lastPage;

	private Set<Integer> set; // 用户的id组成的set
	private static final Integer SIZE = 10; // 分页大小

	public String execute() {
		resourceId=id;
		info = new ResourceManager().readResourceWrapper(id);
		logger.info(id);
		resourceType = info.getType();
		if(info == null){
			return INPUT;
		}
		
		evaluate(info);
		evalUserInfo();
		evalPermission(info);
		RecommendUtil.log(getSessionUID(), id, info.getType(),
				Constant.BEHAVIOR_TYPE_BROWSE);
		//TODO 修改一下，不分资源与列表，直接保存资源类型
		ScoreManagerFactory.getScoreManager(resourceType).addBrowse(id,
				getSessionUID());
		return SUCCESS;
	}


	// TODO 修改加载评论、历史版本、列表内资源为异步加载
	private void evaluate(ResourceWrapper info) {
		evaluateScore(id, resourceType);
		map = new HashMap<Integer, UserInfo>();
		set = new HashSet<Integer>();
		set.add(getSessionUID());
		set.add(info.getAuthorId());
		findByType(info.getType());
	}

	private void evaluateScore(Integer id, Integer resourceType) {
		String rType = "";
		if (resourceType == -1) {
			rType = "list";
		} else {
			rType = "item";
		}
		List<UserScore> l = (new UserScoreDAO()).findUserScoreById(id, rType);
		if (l != null) {
			upNum = 0;
			downNum = 0;
			float look = 0f;
			avgNum = 0;
			for (UserScore u : l) {
				upNum = u.getUp() + upNum;
				downNum = u.getDown() + downNum;
				look = u.getScore() + look;
			}
			lookNum = (int) (look * 10);
			avgNum = 50 + (int) (look - 50) / 10 + upNum - downNum;
		}
	}

	private void findByType(Integer type) {
		if (type == -1 ) {
			logger.info("获取列表中的资源。");
			findItemInList();
			return;
		}
		switch (type) {
		case 1:
			findHistory();
			break;
		case 3:
			try {
				videoUrl = new VideoManager().getFlash(info.getContent());
			} catch (Exception e) {
				logger.error("解析url获取视频地址出错！！！", e);
			}
			break;
		}
	}
	//TODO 从本地数据库查找
	private void findHistory() {
		if (null == getPage()) {
			setPage(1);
		}
		docs = new ResourceManager().getDocVersions(id);

		if(null != docs){
			for(DocVersion d : docs){
				String description = delhtml(d.getDescription());
				d.setDescription(description);
			}
			docsNum = docs.size();
			setLastPage(docsNum / SIZE + 1);
		}
		
		
		
	}
	//TODO 从本地数据库查找
	private void findItemInList() {
		List<ListRelation> itemInList =  new ListRelationManager().obtainRelationByList(id);
		
		if (null != itemInList) {
			page = (page == null) ? 1 : page;
			if (itemInList.size() % SIZE == 0) {
				setLastPage(itemInList.size() / SIZE);
			} else{
				setLastPage(itemInList.size() / SIZE + 1);
			}
			logger.info(page);
			logger.info(lastPage);
			logger.info(lastPage);
			item = new ArrayList<Resource>();
			ResourceManager Mresource = new ResourceManager();
			List<Integer> resourceIds = new ArrayList<Integer>();
			String description = "";
			if( ! page.equals(lastPage) && lastPage > page){
				for(int i=0;i<SIZE;i++){
					resourceIds.add(itemInList.get((page-1)*10+i).getResourceId());
				}
				List<Resource> resource = Mresource.readMutilResource(resourceIds);
				item.clear();
				for(Resource d : resource){
					set.add(d.getAuthorId());
					description = delhtml(d.getDescription());
					d.setDescription(description);
					item.add(d);
				}
				itemNum = item.size();
				List<Integer> uidList = new ArrayList<Integer>(set);
				List<UserInfo> uiList = UserInfoUtil.getUserInfoByUid(
						getSessionToken(), uidList);
				map = new HashMap<Integer, UserInfo>();
				for (UserInfo ui : uiList) {
					map.put(Integer.parseInt(ui.getUserId()), ui);
				}
							
			}else if(lastPage < page ){
				for (ListRelation d : itemInList) {
					resourceIds.add(d.getResourceId());
				}
				List<Resource> resource = Mresource.readMutilResource(resourceIds);
				item.clear();
				for(Resource d : resource){
					set.add(d.getAuthorId());
					description = delhtml(d.getDescription());
					d.setDescription(description);
					item.add(d);
				}
				itemNum = item.size();
				List<Integer> uidList = new ArrayList<Integer>(set);
				List<UserInfo> uiList = UserInfoUtil.getUserInfoByUid(
						getSessionToken(), uidList);
				map = new HashMap<Integer, UserInfo>();
				for (UserInfo ui : uiList) {
					map.put(Integer.parseInt(ui.getUserId()), ui);
				}
			}else if( page.equals(lastPage)){
				for(int i=(lastPage-1)*SIZE;i<itemInList.size();i++){
					resourceIds.add(itemInList.get(i).getResourceId());
				}
				List<Resource> resource = Mresource.readMutilResource(resourceIds);
				item.clear();
				for(Resource d : resource){
					set.add(d.getAuthorId());
					description = delhtml(d.getDescription());
					d.setDescription(description);
					item.add(d);
				}
				itemNum = item.size();
				List<Integer> uidList = new ArrayList<Integer>(set);
				List<UserInfo> uiList = UserInfoUtil.getUserInfoByUid(
						getSessionToken(), uidList);
				map = new HashMap<Integer, UserInfo>();
				for (UserInfo ui : uiList) {
					map.put(Integer.parseInt(ui.getUserId()), ui);
				}
			}
			
			
		}
		
	}

	private void evalUserInfo() {
		List<Integer> uidList = new ArrayList<Integer>(set);
		List<UserInfo> uiList = UserInfoUtil.getUserInfoByUid(
				getSessionToken(), uidList);
		for (UserInfo ui : uiList) {
			map.put(Integer.parseInt(ui.getUserId()), ui);
		}
	}

	public String updateScore() {
		Integer uid = getSessionUID();
		logger.info(" " + id + " " + uid + " " + upDown);
		ScoreManagerFactory.getItemScoreManager().addUpDown(id, uid, upDown);
		return SUCCESS;
	}

	public void updateScore(Integer resourceType) {
		ScoreManagerFactory.getScoreManager(resourceType).addBrowse(id,
				getSessionUID());
	}
	
	public void evalPermission(ResourceWrapper info){
		
		if(info.getAuthorId().equals(getSessionUID())){
			permission = 1;
		}else{
			permission = 2;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ResourceWrapper getInfo() {
		return info;
	}

	public void setInfo(ResourceWrapper info) {
		this.info = info;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	public SrhResult getSrhResult() {
		return srhResult;
	}

	public void setSrhResult(SrhResult srhResult) {
		this.srhResult = srhResult;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DocVersion> getDocs() {
		return docs;
	}

	public void setDocs(List<DocVersion> docs) {
		this.docs = docs;
	}

	public String getUpDown() {
		return upDown;
	}

	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}

	public Integer getUpNum() {
		return upNum;
	}

	public void setUpNum(Integer upNum) {
		this.upNum = upNum;
	}

	public Integer getDownNum() {
		return downNum;
	}

	public void setDownNum(Integer downNum) {
		this.downNum = downNum;
	}

	public Integer getLookNum() {
		return lookNum;
	}

	public void setLookNum(Integer lookNum) {
		this.lookNum = lookNum;
	}

	public Integer getAvgNum() {
		return avgNum;
	}

	public void setAvgNum(Integer avgNum) {
		this.avgNum = avgNum;
	}

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	public Map<Integer, UserInfo> getMap() {
		return map;
	}

	public void setM(Map<Integer, UserInfo> map) {
		this.map = map;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public void setMap(Map<Integer, UserInfo> map) {
		this.map = map;
	}

	public Integer getBanner() {
		return banner;
	}

	public void setBanner(Integer banner) {
		this.banner = banner;
	}

	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getRight() {
		return right;
	}

	public void setRight(Integer right) {
		this.right = right;
	}

	public Integer getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(Integer evaluate) {
		this.evaluate = evaluate;
	}

	public Integer getFunction() {
		return function;
	}

	public void setFunction(Integer function) {
		this.function = function;
	}

	public Integer getComment() {
		return comment;
	}

	public void setComment(Integer comment) {
		this.comment = comment;
	}


	public List<Resource> getItem() {
		return item;
	}


	public void setItem(List<Resource> item) {
		this.item = item;
	}


	public Integer getItemNum() {
		return itemNum;
	}


	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}


	public Integer getResourceId() {
		return resourceId;
	}


	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}


	public Integer getDocsNum() {
		return docsNum;
	}


	public void setDocsNum(Integer docsNum) {
		this.docsNum = docsNum;
	}
}

