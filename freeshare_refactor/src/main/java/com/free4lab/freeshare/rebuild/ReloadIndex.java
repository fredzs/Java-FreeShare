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
import com.free4lab.freeshare.util.TagsUtil;
import com.free4lab.utils.http.SearchClient;

/**
 * 重写数据，更新索引
 * 
 * @author zhaowei
 */

public class ReloadIndex extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static final String resource_URL = "resource?id=";
	private static final String GROUP_URL = "group/resource?groupId=";
	private final static String NEED_INDEX_DESCRIPTION = "description";

	private SearchClient client = new SearchClient(
			URLConst.APIPrefix_FreeSearch);
	Map<String, Map<Integer, List<Integer>>> permissionMap = new HashMap<String, Map<Integer, List<Integer>>>();
	private List<String> l = new ArrayList<String>();
	private List<Resource> rlist;
//	private List<ListRelation> lrlist;
	private List<Group> glist;
	private List<Comment> clist;
	private List<DocVersion> dlist;

	public String execute() {
		// 仅由我调用此方法
		// if(getSessionUID() != 70){
		// return INPUT;
		// }
		System.out.println(client.getAddDocUrl());
		l.add(NEED_INDEX_DESCRIPTION);
		init();
		reloadResourceIndex();
//		reloadRelationIndex();
		reloadCommentIndex();
		reloadGroupIndex();
		reloadDocversionIndex();
		return SUCCESS;
	}
	
	void init(){
		valPermissionMap();
		rlist = new ResourceDAO().findAll();
//		lrlist = new ListRelationDAO().findAll();
		dlist = new DocVersionDAO().findAll();
		clist = new CommentDao().findAll();
		glist = GroupManager.getAllGroups();
	}
	
	void reloadGroupIndex() {
		for (int i = 0; i < glist.size(); i++) {
			Group group = glist.get(i);
			//TODO 一次获取全部
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

	void reloadResourceIndex() {
		Map<Integer, List<Integer>> ipMap = permissionMap.get("ip");

		for (int i = 0; i < rlist.size(); i++) {
			Resource resource = rlist.get(i);
			String url = new String(resource_URL + resource.getId());
			String title = resource.getName();
			Integer type = resource.getType();
			Integer uid = resource.getAuthorId();
			BasicContent bContent = new BasicContent(resource.getAuthorId(),
					delhtml(resource.getDescription()), type,
					BehaviorConst.TYPE_CREATE);
			Content content = returnContent(type, resource);

			List<Integer> groupIds = ipMap.get(resource.getId());
			if (groupIds == null) {
				groupIds = new ArrayList<Integer>();
			}

			try {
				String contentString = ContentFaced.getContent(bContent,
						content);
				List<String> tags = TagsUtil.getTagValue(type,
						BehaviorConst.TYPE_CREATE, uid, groupIds);
				client.addDoc(url, title, contentString, tags, l, resource
						.getCreateTime().toString(), resource.getRecentEditTime()
						.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	void reloadDocversionIndex() {

		for (DocVersion dv : dlist) {
			List<String> versionTags = new ArrayList<String>();
			versionTags.add(TagValuesConst.pNEWVERSION + dv.getItemId());
			
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

		List<ResourcePermission> ipList = new ResourcePermissionDAO().findAll();
		for (ResourcePermission rp : ipList) {
			Integer resourceId = rp.getResource().getId();
			if (!ipMap.containsKey(resourceId)) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(rp.getGroupId());
				ipMap.put(resourceId, list);
			} else {
				ipMap.get(resourceId).add(rp.getGroupId());
			}
		}
		permissionMap.put("ip", ipMap);
	}

	private List<String> getCmtTags(Integer iid, Integer uid, Integer type) {
		List<String> tags = new ArrayList<String>();
		tags.add(TagValuesConst.FMT_COMMENT);
		tags.add(TagValuesConst.pAUTHOR + uid);
		tags.add("resource?id=" + iid);
		return tags;
	}

	private Content returnContent(Integer type, Object object) {
		Content content = new Content();
		content= resourceContent(type, object);
		return content;
	}
	//TODO 针对视频地址出现异常解析,处理不够好
	private Content resourceContent(Integer type, Object object) {
		Content content = new Content();
		Resource resource = (Resource) object;
		String imgUrl = getImgSrc(resource.getDescription());
		if (type.equals(ResourceTypeConst.TYPE_DOC)) {
			// 对于文档类型，attachment存放下载地址，如果下载地址为空，此资源无效，则直接删除
			if (resource.getAttachment() == null || resource.getAttachment() == "") {
				new ResourceDAO().deleteByPrimaryKey(resource.getId());
			} else
				content = new StandardContent(imgUrl, resource.getAttachment());
		} else if (type.equals(ResourceTypeConst.TYPE_URL)) {
			content = new StandardContent(imgUrl, resource.getAttachment());
		} else if (type.equals(ResourceTypeConst.TYPE_VIDEO_URL)) {
			String swfUrl = "";
			String videoImgUrl = "";
			String enclosure = resource.getAttachment();
			swfUrl = new VideoManager().getFlash(enclosure);
			videoImgUrl = new VideoManager().getImgsrc(enclosure);
			content = new VideoContent(imgUrl, enclosure, swfUrl, videoImgUrl);
		} else
			content = new StandardContent(imgUrl, ""); // 文章、话题、列表
		return content;
	}

	public static void main(String[] args) {
		long stime = System.currentTimeMillis();
		new ReloadIndex().execute();
		long etime = System.currentTimeMillis();
		System.out.println("rebuild index " + (etime - stime));
	}
}
