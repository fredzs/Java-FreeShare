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
 * 在这里除了继承父类的之外，解析其他的所有项
 * @author Administrator
 *
 */

public class VideoContent extends Content {

	private String imgUrl;
	private String srcUrl;
	private String swfUrl;
	private String videoImgUrl;

	public VideoContent() {}
	public VideoContent(JSONObject jsonObj) throws JSONException{
		JSONObject content = new JSONObject(jsonObj.getString("content"));
		setImgUrl(content.getString("imgUrl"));
		setSrcUrl(content.getString("srcUrl"));
		setSwfUrl(content.getString("swfUrl"));
		setVideoImgUrl(content.getString("videoImgUrl"));
	}
	/**
	 * 初始化变量
	 * @param imgUrl
	 * @param srcUrl
	 * @param swfUrl
	 * @param videoImgUrl
	 * @author Administrator
	 *
	 */
	public VideoContent(String imgUrl, String srcUrl, String swfUrl,
			String videoImgUrl) {
		try {
			this.put(ContentConst.IMGURL, imgUrl)
			.put(ContentConst.SRCURL, srcUrl)
			.put(ContentConst.SWFURL, swfUrl)
			.put(ContentConst.VIDEOIMGURL, videoImgUrl);
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

	public static void main(String[] args){
		VideoContent fcontent = new VideoContent("1", "123", "1", "2");
		System.out.println(fcontent.toString());
	}
}
