package com.free4lab.freeshare.model.data;

import java.util.ArrayList;
import java.util.List;

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
 * 在这里只提取任何资源都会存在的公共部分 { "type":[资源类型], "optype":[操作类型],
 * "description":[资源的描述信息], "userId":[作者ID], }
 * 
 * @author Administrator
 * 
 */
public class BasicContent extends JSONObject {
	//发送到search的内容
	private Integer userId;
	private String description;
	private Integer type;
	private Integer optype;
	
	public BasicContent() {}
	
	public BasicContent(JSONObject jsonObj) throws JSONException {
		JSONObject content = new JSONObject(jsonObj.getString("content"));
		
		setUserId(content.getInt("userId"));
		setType(content.getInt("type"));
		setOptype(content.getInt("optype"));
		
		String desc = content.optString("description", "...");
		if (desc.length() > 100)
			desc = desc.substring(0, 100) + "...";
		setDescription(desc);
	}
	
	//组合发送到search的内容
	public BasicContent(Integer userId, String description, Integer type,
			Integer optype) {
		this.setUserId(userId);
		this.setDescription(description);
		this.setType(type);
		this.setOptype(optype);
		try {
			this.put(ContentConst.USER_ID, userId).put(ContentConst.TYPE, type).put(ContentConst.OPTYPE, optype)
					.put(ContentConst.DESCRIPTION, description);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getOptype() {
		return optype;
	}

	public void setOptype(Integer optype) {
		this.optype = optype;
	}

	

	public static void main(String[] args) {
		List<Object> l = new ArrayList<Object>();
		l.add("123");
		l.add(1);
		for (Object o : l) {
			if (o instanceof Integer) {
				System.out.println(o);
			}
		}
		BasicContent content = new BasicContent(1, "123", 1, 2);
		System.out.println(content.toString());
	}
}
