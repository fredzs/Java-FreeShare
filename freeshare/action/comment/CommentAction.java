package com.free4lab.freeshare.action.comment;

import com.free4lab.freeshare.BehaviorConst;
import com.free4lab.freeshare.ResourceTypeConst;
import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.SendMail;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.model.dao.CommentDao;
import com.free4lab.freeshare.model.data.BasicContent;
import com.free4lab.freeshare.model.data.CmtContent;
import com.free4lab.freeshare.model.data.ContentFaced;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;
import com.free4lab.utils.perflog.FatalLogger;

import com.free4lab.freeshare.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONException;

public class CommentAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CommentAction.class);
	private Integer uid;// userId 评论者的id
	private Integer id;// 被评论资源的id
	private Integer pid;// parentId 被评论的评论者的
	private Integer aid;// authorId 资源所有者id
	private String name;// 被评论的资源名称
	private String uname;// 评论者名，不存值；显示评论时在写入，以防用户名更改
	private String cmtCont;// 评论内容
	private Integer type;// 被评论的资源的类型(资源/列表)

	private String cmtUrl;// 要删除的评论的url

	public String execute() {
		logger.info("the resource id is " + id + ", the type is " + type);
		if (id != null) {
			String uuid = UUID.randomUUID().toString();
			Integer uid = getSessionUID();
			BasicContent bContent = new BasicContent(uid, cmtCont, ResourceTypeConst.TYPE_COMMENT, BehaviorConst.TYPE_CREATE);
			CmtContent cct = new CmtContent(id, pid, uname);
			String content = "";
			try {
				content = ContentFaced.getContent(bContent, cct);
				List<String> tags = getCmtTags(id, uid, type);
				cmtUrl =tags.get(2) + "&cmtUrl?uuid=" + uuid;
				
				new NewIndexManager().addIndex(cmtUrl, name, content, tags);
				new CommentDao().create(cmtUrl, id, type, getSessionUID(), content);
				// 开启一个线程发送邮件
				CmtRunnable r = new CmtRunnable(getSessionToken(), aid, pid, uid, type);
				Thread t = new Thread(r);
				t.start();
				
				
			} catch (JSONException e) {
				FatalLogger.log("FREESHARE", "comment", bContent + "组装两个content错误。");
				e.printStackTrace();
			}
			
		}
		return SUCCESS;
	}
	/**
	 * 删除评论，前端ajax调用
	 * @return
	 */
	public String delCmt() {
		new NewIndexManager().delIndex(cmtUrl);
		new CommentDao().deleteByCmtUrl(cmtUrl);
		return SUCCESS;
	}
	
	private List<String> getCmtTags(Integer iid, Integer uid, Integer type){
		List<String> tags = new ArrayList<String>();
		tags.add(TagValuesConst.FMT_COMMENT);
		tags.add(TagValuesConst.pAUTHOR + uid);
		if(type == 0){
			tags.add("item?id=" + iid);
		}else
			tags.add("list?id=" + iid);
		
		logger.info("the type is " + type + ", the iid is " + iid);
		return tags;
	}
	
	class CmtRunnable implements Runnable {
		private String accessToken;
		private Integer authorId;
		private Integer parentId;
		private Integer loginId;
		private Integer type;
		private String content;

		public CmtRunnable(String accessToken, Integer aid, Integer pid, Integer uid, Integer type) {
			this.accessToken = accessToken;
			this.authorId = aid;
			this.parentId = pid;
			this.loginId = uid;
			this.type =  type;
		}

		public void run() {
			logger.info("the authorId is " + authorId + ",loginId is" + loginId
					+ ",parentId is " + parentId + "uname is " + uname + "the type is " + type);			
			String toEmail = "";
			String toName = "";
			// 被评论的用户不是当前登录用户才发送邮件
			if (!authorId.equals(loginId) && parentId == null) {
				List<Integer> idList = new ArrayList<Integer>();
				idList.add(authorId);
				UserInfo ui = UserInfoUtil.getUserInfoByUid(accessToken, idList).get(0);
				toEmail = ui.getEmail();
				toName = ui.getUserName();
				String redirectUrl = "";
				List<Integer> toUserId = new ArrayList<Integer>();
				if (type == 0) {
					content = toName + "，您好！<br/><br/>" + "您发布的资源\"" + name + "\"被 " + uname + " 评论。您可以点击"
							+ "<a href = \"http://freeshare.free4lab.com/item?id=" + id + "\">"
							+ "http://freeshare.free4lab.com/item?id=" + id + "</a>" + "进行相关处理。<br/><br/><br/><br/>"
							+ "-------------------------------<br/><br/>" + "Free分享：面向群组的在线知识管理应用。<br/>"
							+ "<a href = \"http://freeshare.free4lab.com\">"+"http://freeshare.free4lab.com" + "</a>";
					try {
						List<String> mailList = new ArrayList<String>();
						mailList.add(toEmail);
						SendMail.sendMail(mailList, "您发布的资源\"" + name + "\"被评论。", content);
						//发送消息提醒
						redirectUrl = "http://freeshare.free4lab.com/item?id="+id;						
						toUserId.add(aid);
						MessageUtil.sendMessage(uname+"评论了您的资源:"+name, redirectUrl,toUserId);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					content = toName + "，您好！<br/><br/>" + "您发布的列表\"" + name + "\"被 " + uname + " 评论。您可以点击"
							+ "<a href = \"http://freeshare.free4lab.com/list?id="+ id + "\">"
							+ "http://freeshare.free4lab.com/list?id=" + id + "</a>" + "进行相关处理。<br/><br/><br/><br/>"
							+ "-------------------------------<br/><br/>" + "Free分享：面向群组的在线知识管理应用。<br/>"
							+ "<a href = \"http://freeshare.free4lab.com\">"+"http://freeshare.free4lab.com" + "</a>";
					try {
						List<String> mailList = new ArrayList<String>();
						mailList.add(toEmail);
						SendMail.sendMail(mailList, "您发布的列表\"" + name + "\"被评论。", content);
						//发送消息提醒
						redirectUrl = "http://freeshare.free4lab.com/list?id="+id;
						toUserId.add(aid);
						MessageUtil.sendMessage(uname+"评论了您的列表:"+name, redirectUrl,toUserId);						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
			// 当非空且不是对自己的回复进行回复的时候，进行邮件提醒
			if (parentId != null && !parentId.equals(loginId)) {
				List<Integer> idList = new ArrayList<Integer>();
				idList.add(parentId);
				UserInfo ui = UserInfoUtil.getUserInfoByUid(getSessionToken(), idList).get(0);
				toEmail = ui.getEmail();
				toName = ui.getUserName();
				logger.info(toEmail + "回复评论！");
				content = toName + "，您好！<br/><br/>" + "您对\"" + name + "\"的评论被 " + uname + " 回复。您可以点击"
						+ "<a href = \"http://freeshare.free4lab.com/item?id=" + id + "\">" 
						+ "http://freeshare.free4lab.com/item?id=" + id + "</a>" + "进行相关处理。<br/><br/><br/><br/>"
						+ "-------------------------------<br/><br/>" + "Free分享：面向群组的在线知识管理应用。<br/>"
						+ "<a href = \"http://freeshare.free4lab.com\">"+"http://freeshare.free4lab.com" + "</a>";
				try {
					List<String> mailList = new ArrayList<String>();
					mailList.add(toEmail);
					SendMail.sendMail(mailList, "您对\"" + name + "\"的评论被回复。", content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCmtCont() {
		return cmtCont;
	}

	public void setCmtCont(String cmtCont) {
		this.cmtCont = cmtCont;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCmtUrl() {
		return cmtUrl;
	}

	public void setCmtUrl(String cmtUrl) {
		this.cmtUrl = cmtUrl;
	}
}
