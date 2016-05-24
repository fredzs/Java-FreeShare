package com.free4lab.freeshare.rebuild;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.free4lab.freeshare.BehaviorConst;
import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.URLConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.GroupManager;
import com.free4lab.freeshare.manager.VideoManager;
import com.free4lab.freeshare.model.dao.*;
import com.free4lab.freeshare.model.data.BasicContent;
import com.free4lab.freeshare.model.data.Content;
import com.free4lab.freeshare.model.data.ContentFaced;
import com.free4lab.freeshare.model.data.Group;
import com.free4lab.freeshare.model.data.StandardContent;
import com.free4lab.freeshare.model.data.VideoContent;
import com.free4lab.freeshare.util.IndexUtil;
import com.free4lab.utils.http.SearchClient;

/**
 * 重写数据，更新索引
 * 
 * @author zhaowei
 */

public class ReloadResourceIndex extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static final String LIST_URL = "list?id=";
	private static final String ITEM_URL = "item?id=";
	private static final String GROUP_URL = "group/resource?groupId=";
	private final static String NEED_INDEX_DESCRIPTION = "description";

	private SearchClient client = new SearchClient(
			URLConst.APIPrefix_FreeSearch);
	// 资源、列表权限所对应的map
	Map<String, Map<Integer, List<Integer>>> permissionMap = new HashMap<String, Map<Integer, List<Integer>>>();
	private List<String> l = new ArrayList<String>();
	private List<Item> ilist;
	private List<Lists> llist;
	private List<Group> glist;
	private List<Comment> clist;
	private List<DocVersion> dlist;

	public String execute() {
		// 仅由我调用此方法
		// if(getSessionUID() != 70){
		// return INPUT;
		// }
		l.add(NEED_INDEX_DESCRIPTION);
		init();
		reloadItemIndex();
		reloadListIndex();
		reloadRelationIndex();
		reloadCommentIndex();
		reloadGroupIndex();
		reloadDocversionIndex();
		return SUCCESS;
	}
	void init(){
		valPermissionMap();
		ilist = new ItemDAO().findAll();
		llist = new ListsDAO().findAll();
		dlist = new DocVersionDAO().findAll();
		clist = new CommentDao().findAll();
		glist = GroupManager.getAllGroups();
	}
	
	void reloadGroupIndex() {
		for (int i = 0; i < glist.size(); i++) {
			Group group = glist.get(i);
			List<GroupPermission> gplist = new GroupPermissionDAO()
					.findByProperty2("groupId", group.getGroupId(), "type", 2);
			try {

				String url = GROUP_URL + group.getGroupId();
				Integer uid = gplist.get(0).getUid();
				String title = group.getGroupInfo().get("name");

				BasicContent bContent = new BasicContent(uid, group
						.getGroupInfo().get("desc"),
						ResourceTypeConst.TYPE_GROUP, BehaviorConst.TYPE_CREATE);
				List<String> tags = new ArrayList<String>();
				tags.add(TagValuesConst.FMT_GROUP);
				tags.add(TagValuesConst.pAUTHOR + uid);

				client.addDoc(url, title, bContent.toString(), tags, l, null,
						null);
			} catch (Exception e) {
				// ignore
			}
		}
	}

	void reloadItemIndex() {
		Map<Integer, List<Integer>> ipMap = permissionMap.get("ip");

		for (int i = 0; i < ilist.size(); i++) {
			Item item = ilist.get(i);
			String url = new String(ITEM_URL + item.getId());
			String title = item.getName();
			Integer type = item.getType();
			Integer uid = item.getAuthorId();
			BasicContent bContent = new BasicContent(item.getAuthorId(),
					delhtml(item.getDescription()), type,
					BehaviorConst.TYPE_CREATE);
			Content content = returnContent(type, item);

			List<Integer> groupIds = ipMap.get(item.getId());
			if (groupIds == null) {
				groupIds = new ArrayList<Integer>();
			}

			try {
				String contentString = ContentFaced.getContent(bContent,
						content);
				List<String> tags = IndexUtil.getTagValue(type,
						BehaviorConst.TYPE_CREATE, uid, groupIds);
				client.addDoc(url, title, contentString, tags, l, item
						.getTime().toString(), item.getRecentEditTime()
						.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	void reloadListIndex() {
		Map<Integer, List<Integer>> lpMap = permissionMap.get("lp");
		for (int i = 0; i < llist.size(); i++) {
			Lists list = llist.get(i);
			String url = new String(LIST_URL + list.getId());
			String title = list.getName();
			Integer uid = list.getAuthorId();

			BasicContent bContent = new BasicContent(list.getAuthorId(),
					delhtml(list.getDescription()),
					ResourceTypeConst.TYPE_LIST, BehaviorConst.TYPE_CREATE);
			Content content = returnContent(ResourceTypeConst.TYPE_LIST, list);

			List<Integer> groupIds = lpMap.get(list.getId());
			if (groupIds == null) {
				groupIds = new ArrayList<Integer>();
			}
			try {
				String contentString = ContentFaced.getContent(bContent,
						content);
				List<String> tags = IndexUtil.getTagValue(
						ResourceTypeConst.TYPE_LIST, BehaviorConst.TYPE_CREATE,
						uid, groupIds);
				client.addDoc(url, title, contentString, tags, l, list
						.getTime().toString(), list.getRecentEditTime()
						.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	void reloadDocversionIndex() {

		for (DocVersion dv : dlist) {
			List<String> versionTags = new ArrayList<String>();
			versionTags.add(TagValuesConst.pITEM_VERSION + dv.getItemId());
			
			BasicContent versionContent = new BasicContent(dv.getUserId(),
					delhtml(dv.getDescription()),
					ResourceTypeConst.TYPE_VERSION, BehaviorConst.TYPE_CREATE);
			try {
				client.addDoc(dv.getUuid(), "", versionContent.toString(),
						versionTags, l, dv.getEditTime().toString(), dv
								.getEditTime().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//TODO 把所有数据一次获取
	void reloadRelationIndex() {
		for (int i = 0; i < llist.size(); i++) {
			Lists list = llist.get(i);
			List<Relation> rlist = new ArrayList<Relation>();
			rlist = new RelationDAO().findByList(list.getId());
			for (Relation r : rlist) {
				Integer itemId = r.getItemId();
				List<String> tags = new ArrayList<String>();
				tags.add(TagValuesConst.pITEM_IN_LIST + r.getListId());
				try {
					client.addTags(ITEM_URL + itemId, tags);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	void reloadCommentIndex() {
		for (Comment c : clist) {
			try {
				client.addDoc(
						c.getCmtUrl(),
						"",
						c.getDescription(),
						getCmtTags(c.getResourceId(), c.getUserId(),
								c.getResourceType()), l,
						c.getTime().toString(), c.getTime().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	void valPermissionMap() {
		// 资源id与归属的群组
		Map<Integer, List<Integer>> ipMap = new HashMap<Integer, List<Integer>>();
		// 列表id与归属的群组
		Map<Integer, List<Integer>> lpMap = new HashMap<Integer, List<Integer>>();

		List<ItemPermission> ipList = new ItemPermissionDAO().findAll();
		for (ItemPermission ip : ipList) {
			Integer itemId = ip.getItemId();
			if (!ipMap.containsKey(itemId)) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(ip.getTeamId());
				ipMap.put(itemId, list);
			} else {
				ipMap.get(itemId).add(ip.getTeamId());
			}
		}

		List<ListPermission> lpList = new ListPermissionDAO().findAll();
		for (ListPermission lp : lpList) {
			Integer listId = lp.getListId();
			if (!lpMap.containsKey(listId)) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(lp.getTeamId());
				lpMap.put(listId, list);
			} else {
				lpMap.get(listId).add(lp.getTeamId());
			}
		}
		System.out.println(lpList.size());
		permissionMap.put("ip", ipMap);
		permissionMap.put("lp", lpMap);
	}

	private List<String> getCmtTags(Integer iid, Integer uid, Integer type) {
		List<String> tags = new ArrayList<String>();
		tags.add(TagValuesConst.FMT_COMMENT);
		tags.add(TagValuesConst.pAUTHOR + uid);
		if (type == 0) {
			tags.add("item?id=" + iid);
		} else
			tags.add("list?id=" + iid);

		return tags;
	}

	private Content returnContent(Integer type, Object object) {
		Content content = new Content();
		if (type != -1) {
			content= itemContent(type, object);
		} else {
			Lists list = (Lists) object;
			String imgUrl = getImgSrc(list.getDescription());
			content = new StandardContent(imgUrl, "");
		}
		return content;
	}
	//TODO 针对视频地址出现异常解析,处理不够好
	private Content itemContent(Integer type, Object object) {
		Content content = new Content();
		Item item = (Item) object;
		String imgUrl = getImgSrc(item.getDescription());
		if (type.equals(ResourceTypeConst.TYPE_DOC)) {
			// 对于文档类型，content存放下载地址，如果下载地址为空，此资源无效，则直接删除
			if (item.getContent() == null || item.getContent() == "") {
				new ItemDAO().deleteByPrimaryKey(item.getId());
			} else
				content = new StandardContent(imgUrl, item.getContent());
		} else if (type.equals(ResourceTypeConst.TYPE_URL)) {
			content = new StandardContent(imgUrl, item.getContent());
		} else if (type.equals(ResourceTypeConst.TYPE_VIDEO_URL)) {
			String swfUrl = "";
			String videoImgUrl = "";
			String enclosure = item.getContent();
			swfUrl = new VideoManager().getFlash(enclosure);
			videoImgUrl = new VideoManager().getImgsrc(enclosure);
			content = new VideoContent(imgUrl, enclosure, swfUrl, videoImgUrl);
		} else
			content = new StandardContent(imgUrl, ""); // 文章、话题
		return content;
	}

	public static void main(String[] args) {
		long stime = System.currentTimeMillis();
		new ReloadResourceIndex().execute();
		long etime = System.currentTimeMillis();
		System.out.println("rebuild index " + (etime - stime));
	}
}
