package com.free4lab.freeshare.action.comment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.action.comment.CommentAction;
import com.free4lab.freeshare.manager.CheckAtManager;
import com.free4lab.freeshare.util.MessageUtil;

public class CommentWithAtAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(CommentWithAtAction.class);
	private Integer uid;// 评论者的id
	private Integer id;// 被评论资源的id
	private Integer pid;// 被评论的评论者的id
	private Integer aid;// 资源所有者的id
	private String name;// 被评论的资源名称
	private String uname;// 评论者名，不存值；显示评论时在写入，以防用户名更改
	private String cmtCont;// 评论内容
	private int type;// 被评论的资源的类型(单个资源/列表)

	private String url;//新评论的url
	
	public String execute() throws Exception{
		//检测是否有@
		List<String> toUserNameToIds = (new CheckAtManager()).checkAt(cmtCont);
		//存储收消息人的id
		List<Integer> toUserIds = new ArrayList<Integer>();
		//存在合法的@
		if( toUserNameToIds != null && toUserNameToIds.size()>0 )
		{				
			for(int i=0; i<toUserNameToIds.size(); i++){
				String[] nameToIds = toUserNameToIds.get(i).split("-");
				String nameToIdItem = nameToIds[0]+"("+nameToIds[1]+")";
				// 添加@效果
				String namewithat = "<a class=\"blueletter\" onclick=\"quickCommunicateFn(\'"+nameToIds[1]+"\')\">@"+nameToIds[0]+"</a>";
				//将cmtCont中的name（id）替换掉
				cmtCont = cmtCont.replace("@"+nameToIdItem, namewithat);
				toUserIds.add(Integer.parseInt(nameToIds[1]) );
			}
		}	
		CommentAction myCommentAction = new CommentAction();
		myCommentAction.setUid(uid);
		myCommentAction.setId(id);
		myCommentAction.setPid(pid);
		myCommentAction.setAid(aid);
		myCommentAction.setName(name);
		myCommentAction.setUname(uname);
		myCommentAction.setCmtCont(cmtCont);
		myCommentAction.setType(type);
		
		String returnStatus = myCommentAction.execute();
		url = myCommentAction.getCmtUrl();	//取得评论的url
		
		if( ("success").equals(returnStatus) ){
			String redirectUrl = "http://freeshare.free4lab.com/resource?id="+myCommentAction.getId();
			String myName = getSessionUserName();
			//发@提醒
			logger.info("toUserIds="+toUserIds);
			if( toUserIds.size()>0 ){
				String title = myName+" 在 "+name+" 中 @了我";
				MessageUtil.sendMessage(title, redirectUrl,toUserIds);
			}			
			return SUCCESS;
		}else{
			return "error";
		}
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

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getCmtCont() {
		return cmtCont;
	}

	public void setCmtCont(String cmtCont) {
		this.cmtCont = cmtCont;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
