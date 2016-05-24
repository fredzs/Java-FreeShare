package com.free4lab.freeshare.action.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.recommend.Constant;
import com.free4lab.freeshare.action.recommend.RecommendUtil;
import com.free4lab.freeshare.handler.Handler;
import com.free4lab.freeshare.handler.HandlerFactory;
import com.free4lab.freeshare.manager.VideoManager;
import com.free4lab.freeshare.manager.factory.ScoreManagerFactory;
import com.free4lab.freeshare.manager.search.SearchManager;
import com.free4lab.freeshare.model.adapter.ObjectAdapter;
import com.free4lab.freeshare.model.dao.UserScore;
import com.free4lab.freeshare.model.dao.UserScoreDAO;
import com.free4lab.freeshare.model.data.Doc4Srh;
import com.free4lab.freeshare.model.data.SrhResult;
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
	private ObjectAdapter info; // 资源信息
	private String authorName;
	private Integer authorId;
	private String videoUrl;
	private String description;
	private SrhResult srhResult;
	private List<Doc4Srh> docs;
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
		info = findInfo(resourceType);
		if(info == null){
			return INPUT;
		}
		evaluate(info);
		evalUserInfo();

		
		RecommendUtil.log(getSessionUID(), id, info.getType(),
				Constant.BEHAVIOR_TYPE_BROWSE);
		ScoreManagerFactory.getScoreManager(resourceType).addBrowse(id,
				getSessionUID());
		return SUCCESS;
	}

	private ObjectAdapter findInfo(Integer resourceType) {
		ObjectAdapter objectAdapter;
		// 只要访问类型不是列表，则就是资源，随便以非列表类型构造adapter
		if (resourceType == -1) {
			objectAdapter = new ObjectAdapter(ResourceTypeConst.TYPE_LIST);
		} else {
			objectAdapter = new ObjectAdapter(0);
		}
		Handler handler = HandlerFactory.getHandler(objectAdapter);
		objectAdapter = handler.getObject(id);
		return objectAdapter;
	}

	// TODO 修改加载评论、历史版本、列表内资源为异步加载
	private void evaluate(ObjectAdapter info) {
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
			lookNum = (int) (look * 10 - 500);
			avgNum = 50 + (int) (look - 50) / 10 + upNum - downNum;
		}
	}

	private void findByType(Integer type) {
		if (type == null) {
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

	private void findHistory() {
		if (null == getPage()) {
			setPage(1);
		}
		String tags = TagValuesConst.pITEM_VERSION + id;
		setSrhResult(SearchManager.searchItems(NULL_QUERY, page, SIZE, tags));
		if (null == getSrhResult()) {
			logger.warn("result is null");
			return;
		}
		setLastPage(srhResult.getTotalNum() / SIZE + 1);
	}

	private void findItemInList() {
		String tags = TagValuesConst.pITEM_IN_LIST + id;
		page = (page == null) ? 1 : page;
		setSrhResult(SearchManager.searchListItems(page, SIZE, tags));
		if (null != getSrhResult()) {
			for (Doc4Srh d : srhResult.getDocs()) {
				set.add(d.getUserId());
			}
			List<Integer> uidList = new ArrayList<Integer>(set);
			List<UserInfo> uiList = UserInfoUtil.getUserInfoByUid(
					getSessionToken(), uidList);
			map = new HashMap<Integer, UserInfo>();
			for (UserInfo ui : uiList) {
				map.put(Integer.parseInt(ui.getUserId()), ui);
			}

			// 计算资源列表的总页数
			if (srhResult.getTotalNum() % SIZE == 0) {
				setLastPage(srhResult.getTotalNum() / SIZE);
			} else
				setLastPage(srhResult.getTotalNum() / SIZE + 1);
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ObjectAdapter getInfo() {
		return info;
	}

	public void setInfo(ObjectAdapter info) {
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

	public List<Doc4Srh> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc4Srh> docs) {
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
}