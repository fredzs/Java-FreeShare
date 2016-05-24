package com.free4lab.freeshare.model.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.freeshare.ContentConst;

/**
 * 公共的索引内容的提取 一个资源发送到search中的主要字段如下： "content":"{ "type":[资源类型],
 * "optype":[操作类型], "description":[资源的描述信息], "userId":[作者ID],
 * "imgUrl":[当有描述中有图片的时候，图片地址；没有为""] "srcUrl"：[资源自身的地址。文档：下载uuid；视频:视频所在网页地址]
 * "swfUrl":[视频的播放流地址] "videoImgUrl"：[视频的截图地址]
 * "ids":[可能使用到的id，比如加入列表存储列表id，没有操作的时候设为负1] "names":[占位字段] },
 * 
 * 在这里解析除了继承父类之外的
 * imgUrl 与srcUrl字段
 * @author Administrator
 *
 */

public class StandardContent extends Content{
	private String imgUrl;
	private String srcUrl;
	
	public StandardContent(JSONObject jsonObj) throws JSONException {
//		JSONObject content = new JSONObject(jsonObj.getString("content"));
		this.imgUrl = jsonObj.getString("imgUrl");
		this.srcUrl = jsonObj.getString("srcUrl");
	}
	
	public StandardContent(String imgUrl, String srcUrl){
		try {
			this.put(ContentConst.IMGURL, imgUrl)
			.put(ContentConst.SRCURL, srcUrl);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getSrcUrl() {
		return srcUrl;
	}
	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}
	
	public static void main(String[] args){
		StandardContent scontent = new StandardContent("1", "<font color=\"#CC0000\">");
		System.out.println(scontent.toString());
		try {
			StandardContent json = new StandardContent(scontent);
			System.out.println(json.getSrcUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
