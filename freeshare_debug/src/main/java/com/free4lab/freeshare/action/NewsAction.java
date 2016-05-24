package com.free4lab.freeshare.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

import com.free4lab.freeshare.SessionConstants;
import com.free4lab.freeshare.handler.ListHandler;
import com.free4lab.freeshare.manager.search.SearchManager;
import com.free4lab.freeshare.model.dao.Lists;
import com.free4lab.freeshare.model.data.SrhResult;
import com.free4lab.freeshare.model.data.Doc4Srh;
import com.free4lab.freeshare.util.Tool;
import com.free4lab.utils.userinfo.UserInfo;
import com.free4lab.utils.userinfo.UserInfoUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 获取群组动态 通过tag，向搜索请求，获取数据。并对数据进行相应的解析
 * 
 * @author zhaowei
 * 
 */
public class NewsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4676939858481469666L;
	private static final Logger logger = Logger.getLogger(NewsAction.class);
	private Integer page;
	private String operation;
	private String groupIds;
	private String resourceTypeList;
	private String firstTime;
	private String lastTime;

	private SrhResult srt;
	private Map<Integer, UserInfo> m;// 用户id和该id的用户信息
	private Map<String, String> lm;// 列表id与列表名字对应的map
	private final static Integer SIZE = 20;

	public String valAvatar() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<UserInfo> user = UserInfoUtil.getUserInfoByUid(getSessionToken(),
				null);
		if (user.size() > 0
				&& !user.get(0).getAvatar().equals(getSessionAvatar())) {
			session.put(SessionConstants.Avatar, user.get(0).getAvatar());
			session.put(SessionConstants.UserName, user.get(0).getUserName());
		}
		return SUCCESS;
	}

	public String execute() {
		long newsstarttime = System.currentTimeMillis();
		String tags = Tool.returnTags(getSessionUID(), groupIds,
				resourceTypeList);
		page = (page == null) ? 1 : page;
		srt = SearchManager.searchItems("", page, SIZE, tags);
		long searchEndtime = System.currentTimeMillis();
		logger.info("the search time is " + (searchEndtime - newsstarttime));
		if (srt != null) {
			List<Doc4Srh> dl = srt.getDocs();
			if (dl != null && dl.size() > 0) {
				long valMtime = System.currentTimeMillis();
				valM(dl);
				long valMendtime = System.currentTimeMillis();
				logger.info("the valM time is " + (valMendtime - valMtime));
				valLM(dl);
				long valLMendtime = System.currentTimeMillis();
				logger.info("the valLM time is " + (valLMendtime - valMendtime));
				valD(dl);
				long valDendtime = System.currentTimeMillis();
				logger.info("the valD time is " + (valDendtime - valLMendtime));
			}
		}
		long newsendtime = System.currentTimeMillis();
		logger.info("the news time is " + (newsendtime - newsstarttime));
		return SUCCESS;
	}

	private void valD(List<Doc4Srh> dl) {
		for (Doc4Srh d : dl) {
			d.setUserName(m.get(d.getUserId()).getUserName());
			d.setAvatar(m.get(d.getUserId()).getAvatar());
			operation = reOperation(d.getTitle(), d.getUrl(), d.getOptype(),
					d.getType(), d.getListIds());
			d.setOperation(operation);
		}
	}

	// 获取用户信息
	private void valM(List<Doc4Srh> dl) {
		m = new HashMap<Integer, UserInfo>();

		Set<Integer> set = new HashSet<Integer>();
		for (Doc4Srh d : dl) {
			set.add(d.getUserId());
		}
		List<Integer> uidList = new ArrayList<Integer>(set);
		List<UserInfo> uiList = UserInfoUtil.getUserInfoByUid(
				getSessionToken(), uidList);
		for (UserInfo ui : uiList) {
			m.put(Integer.parseInt(ui.getUserId()), ui);
		}
	}

	// 获取列表信息
	private void valLM(List<Doc4Srh> dl) {
		lm = new HashMap<String, String>();
		Integer size = dl.size();
		for (int i = 0; i < size; i++) {
			Doc4Srh doc = dl.get(i);
			List<String> listIds = doc.getListIds();
			if (listIds != null && listIds.size() > 0) {
				for (String id : listIds) {
					try {
						Lists lists = new ListHandler().getLists(Integer
								.parseInt(id));
						if (lists != null) {
							lm.put(id, lists.getName());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	// TODO 修改string为stringbuffered
	// 首先根据optype判断操作类型
	// 然后根据type在判断操作的资源为何种资源
	public String reOperation(String name, String url, Integer optype,
			Integer type, List<String> listIds) {
		String operation = "";
		if (optype != null) {
			switch (optype) {
			case 0: {
				switch (type) {
				case -1:
					operation = " 发布了列表 " + "<a href = \"" + url
							+ "\" target=\"_blank\" class=\"blueletter\">"
							+ name + "</a>";
					break;
				case 0:
					operation = " 发布了话题 " + "<a href = \"" + url
							+ "\" target=\"_blank\" class=\"blueletter\">"
							+ name + "</a>";
					break;
				case 1:
					operation = " 发布了文档 " + "<a href = \"" + url
							+ "\" target=\"_blank\" class=\"blueletter\">"
							+ name + "</a>";
					break;
				case 2:
					operation = " 发布了链接 " + "<a href = \"" + url
							+ "\" target=\"_blank\" class=\"blueletter\">"
							+ name + "</a>";
					break;
				case 3:
					operation = " 发布了视频 " + "<a href = \"" + url
							+ "\" target=\"_blank\" class=\"blueletter\">"
							+ name + "</a>";
					break;
				case 4:
					operation = " 发布了文章 " + "<a href = \"" + url
							+ "\" target=\"_blank\" class=\"blueletter\">"
							+ name + "</a>";
					break;
				}
			}
				break;
			case 1: {
				switch (type) {
				case -1:
					operation = " 编辑了列表 " + "<a href = \"" + url
							+ "\" target=\"_blank\" class=\"blueletter\">"
							+ name + "</a>";
					break;
				default:
					operation = " 编辑了资源 " + "<a href = \"" + url
							+ "\" target=\"_blank\" class=\"blueletter\">"
							+ name + "</a>";
					break;
				}
			}
				break;
			case 2: {
				if (listIds != null) {
					operation = " 把资源 " + "<a href = \"" + url
							+ "\" target=\"_blank\" class=\"blueletter\">" + name
							+ "</a>" + " 移除了列表 ";
					for (String listid : listIds) {
						String listUrl = "list?id=" + listid;
						operation += "<a href = \"" + listUrl
								+ "\" target=\"_blank\" class=\"blueletter\">"
								+ lm.get(listid) + "</a> ";
					}

				} else {
					operation = " 把资源 " + "<a href = \"" + url
							+ "\" target=\"_blank\" class=\"blueletter\">" + name
							+ "</a>" + " 移除了列表 ";
				}
			}
				break;
			case 3: {
				for (String listid : listIds) {
					String listUrl = "list?id=" + listid;
					Lists l = new ListHandler().getLists(Integer
							.parseInt(listid));

					if (l != null) {
						operation = " 把资源 " + "<a href = \"" + url
								+ "\" target=\"_blank\" class=\"blueletter\">"
								+ name + "</a>" + " 加入了列表 " + "<a href = \""
								+ listUrl
								+ "\" target=\"_blank\" class=\"blueletter\">"
								+ lm.get(listid) + "</a> ";
					} else {
						operation = " 修改了资源 " + "<a href = \"" + url
								+ "\" target=\"_blank\" class=\"blueletter\">"
								+ name + "</a> ";
					}
				}
			}
				break;

			case 4: {
				operation = " 把资源 " + "<a href = \"" + url
						+ "\" target=\"_blank\" class=\"blueletter\">" + name
						+ "</a>" + " 移除了列表 ";
				for (String listid : listIds) {
					String listUrl = "list?id=" + listid;
					operation += "<a href = \"" + listUrl
							+ "\" target=\"_blank\" class=\"blueletter\">"
							+ lm.get(listid) + "</a> ";
				}
			}
				break;
			case 5:
				operation = " 更新了资源 " + "<a href = \"" + url
						+ "\" target=\"_blank\" class=\"blueletter\">" + name
						+ "</a>" + " 的版本";
			}
		}
		return operation;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setM(Map<Integer, UserInfo> m) {
		this.m = m;
	}

	public Map<Integer, UserInfo> getM() {
		return m;
	}

	public Map<String, String> getLm() {
		return lm;
	}

	public void setLm(Map<String, String> lm) {
		this.lm = lm;
	}

	public SrhResult getSrt() {
		return srt;
	}

	public void setSrt(SrhResult srt) {
		this.srt = srt;
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

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
}
