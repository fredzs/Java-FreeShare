package com.free4lab.freeshare.action.resource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.CheckAtManager;
import com.free4lab.freeshare.util.MessageUtil;

public class UploadTopicResourceAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7734723834466945444L;
	private final Logger logger = Logger.getLogger(UploadTopicResourceAction.class);
	/*
	 * 1、资源类型：文章 其中包含所有的资源类型的基本的属性。 标识资源类型 —— type:
	 * TYPE_DOC、TYPE_VIDEO_URL、TYPE_URL、TYPE_TXT
	 * type是前端页面通过添加附件的过程中进行的赋值，默认是无附件的TYPE_TXT的形式的资源。
	 * 通过前端页面的标签页的转化进行的是不同类型的资源的发布识别。
	 */
	private String toMySelf;// 记录是否是自己保存的资源。如果是self1的话，就是自己保存
	private String name;
	private String description;
	private Integer type;

	/* 下面是资源选择添加到的可读组、读写组 */
	private String writegroups;
	/* 资源的id */
	private Integer id;

	public String execute() throws Exception{
		
		//检测是否有@
		List<String> toUserNameToIds = (new CheckAtManager()).checkAt(description);
		//存储收消息人的id
		List<Integer> toUserIds = new ArrayList<Integer>();
		//存在合法的@
		if( toUserNameToIds != null && toUserNameToIds.size()>0 )
		{				
			for(int i=0; i<toUserNameToIds.size(); i++){
				String[] nameToIds = toUserNameToIds.get(i).split("-");
				String nameToIdItem = nameToIds[0]+"("+nameToIds[1]+")";
				// 添加@效果
				String namewithat = "<a class=\"blueletter\">@"+nameToIds[0]+"</a>";
				//将description中的name（id）替换掉
				description = description.replace("@"+nameToIdItem, namewithat);
				name = name.replace(nameToIdItem, nameToIds[0]);
				toUserIds.add(Integer.parseInt(nameToIds[1]) );
			}
		}
		PublishAction myResourceAction = (new PublishAction());
		myResourceAction.setToMySelf(toMySelf);
		myResourceAction.setName(name);
		myResourceAction.setDescription(description);
		myResourceAction.setType(type);
		myResourceAction.setWritegroups(writegroups);
		logger.info("toMySelf="+toMySelf+",name="+name+",type="+type+",description="+description+",writegroups="+writegroups);
		String returnStatus = myResourceAction.execute();
		logger.info("returnStatus="+returnStatus);
		if( ("success").equals(returnStatus) ){
			//TODO 郄培修改一下这个地方，我不知道为何使用这个。。。
			//String redirectUrl = myResourceAction.getRedirectUrl();
			//获取资源id，用于独立页面发布完成后的页面跳转
			id = myResourceAction.getId();
			logger.info("http://freeshare.free4lab.com/resource_id="+id);
			String redirectUrl = "http://freeshare.free4lab.com/resource?id="+id;
			String myName = getSessionUserName();
			//发@提醒
			logger.info("toUserIds="+toUserIds);
			if(toUserIds.size() > 0){
				String title = myName+" 在 "+name+" 中 @了我";
				MessageUtil.sendMessage(title, redirectUrl,toUserIds);
			}			
			return SUCCESS;
		}else{
			return "error";
		}
	}
	public String getToMySelf() {
		return toMySelf;
	}
	public void setToMySelf(String toMySelf) {
		this.toMySelf = toMySelf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getWritegroups() {
		return writegroups;
	}
	public void setWritegroups(String writegroups) {
		this.writegroups = writegroups;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
/*	public static void main(String[] arg) throws Exception {
		UploadTopicResourceAction uptopic = new UploadTopicResourceAction();
		uptopic.setName("topic");
		uptopic.setDescription("topic");
		uptopic.setToMySelf("self0");
		uptopic.setType(0);
		uptopic.setWritegroups("602,601");
		uptopic.execute();

	}*/

}
