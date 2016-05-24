package com.free4lab.freeshare.model.data;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * 解析一条索引。一个资源或者列表在search是一条索引，向search请求返回是json格式字符串
 * 现在把字符串解析成struts识别的java对象。以在前后端对各个字段进行交互
 * 
 * 一条索引的结构：
 * {
	        "content":{
	        	 "description":"= FreeSearch 开放Api设计 = ... ",
	        	 "type":"1",
	        	 "optype":"1",
	        	 "srcUrl":"http://freeshare.free4lab.com",
	        	 "imgUrl":""
	        	 },
	        "time":"2011-07-24",
	        "title":"Free Search 开放Api设计",
	        "url":"item?id=1234",    //free分享可以识别的url
	        "fakeUrl":"xxxx",    //用以跳转搜索记录用户行为，为随机数乱码url
	        "urlMd5":-4827329742059431534},
	        "score":5.0
 * }
 * @author Administrator
 * 
 */
public class Doc4Srh {

	private Integer userId;
	private String description;
	private String imgUrl;
	private String docUrl;
	private String linkUrl;
	private String videoUrl;
	private String swfUrl;
	private String videoImgUrl;
	private long urlMd5;
	private Integer type;
	private Integer optype;
	private String time;

	private List<String> listIds;

	private List<String> listNames;
	private String avatar;
	private String operation;
	private String resourceId;
	private String userName;
	private String title;
	private String url;
	private String ptime;
	private String etime;

	private final JSONObject content;

	public Doc4Srh(JSONObject jsonObj) throws JSONException {
		content = new JSONObject(jsonObj.getString("content"));
		type = content.getInt("type");
		optype = content.getInt("optype");
		try {
			valueParams(type, optype);
		} catch (Exception e) {
			System.err.println("xxxUrl解析失败。资源类型为：" + type);
			System.err.println(jsonObj.toString());
		}
		//TODO 	如果想获取free分享自己能识别的url，请另外再声明一个变量
		url = jsonObj.getString("fakeUrl");
		//搜索返回的是个字符串null，所以只能如此判断
		if(url.equals("null")){
			url = jsonObj.getString("url");
		}
		title = jsonObj.getString("title");
		time = jsonObj.getString("time");
		setResourceId(url.substring(8, url.length()));
	}

	public void valueCommonParams() throws JSONException {
		setUserId(content.getInt("userId"));
		setType(content.getInt("type"));
		setOptype(content.getInt("optype"));

		String desc = content.optString("description", "...");
		setDescription(desc);

	}

	/**
	 * 根据资源类型，解析特定内容
	 * 
	 * @param type
	 * @param optype
	 * @throws Exception
	 */
	private void valueParams(Integer type) throws JSONException,
			org.apache.struts2.json.JSONException {
		valueCommonParams();
		setImgUrl(content.getString("imgUrl"));
		// @ResourceTypeConst
		switch (type) {
		case 1:
			setDocUrl(content.getString("srcUrl"));
			break;
		case 2:
			setLinkUrl(content.getString("srcUrl"));
			break;
		case 3:
			setVideoUrl(content.getString("srcUrl"));
			setSwfUrl(content.getString("swfUrl"));
			setVideoImgUrl(content.getString("videoImgUrl"));
			break;
		}
	}

	/**
	 * 根据操作类型，解析特定内容
	 * 
	 * @param type
	 * @param optype
	 * @throws Exception
	 */
	private void valueParams(Integer type, Integer optype) throws Exception {
		// @ItemBehaviorConst
		switch (optype) {
		case 3:
			setListIds(getListByIdString(content.getString("ids")));
			valueParams(type);
			break;
		case 4:
			setListIds(getListByIdString(content.getString("ids")));
			valueParams(type);
			break;
		default:
			valueParams(type);
			break;
		}
	}

	private List<String> getListByIdString(String idString) {
		String[] idArray = idString.split(",");
		return Arrays.asList(idArray);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPtime() {
		return ptime;
	}

	public void setPtime(String ptime) {
		this.ptime = ptime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getUrlMd5() {
		return urlMd5;
	}

	public void setUrlMd5(long urlMd5) {
		this.urlMd5 = urlMd5;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOptype() {
		return optype;
	}

	public void setOptype(Integer optype) {
		this.optype = optype;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public List<String> getListIds() {
		return listIds;
	}

	public void setListIds(List<String> listIds) {
		this.listIds = listIds;
	}

	public List<String> getListNames() {
		return listNames;
	}

	public void setListNames(List<String> listNames) {
		this.listNames = listNames;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getSwfUrl() {
		return swfUrl;
	}

	public void setSwfUrl(String swfUrl) {
		this.swfUrl = swfUrl;
	}

	public String getVideoImgUrl() {
		return videoImgUrl;
	}

	public void setVideoImgUrl(String videoImgUrl) {
		this.videoImgUrl = videoImgUrl;
	}
}
