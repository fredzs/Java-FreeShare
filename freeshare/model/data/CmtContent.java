package com.free4lab.freeshare.model.data;

import org.json.JSONException;

/**
 * 用来封装除了公有变量之外，评论独有的变量
 * 
 * @author Administrator
 * 
 */
public class CmtContent extends Content {
	private Integer id;
	private Integer pid;
	private String uname;

	public Integer getId() {
		return id;
	}

	public void setIid(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public CmtContent() {
	}

	public CmtContent(Integer id, Integer pid, String uname) {
		try {
			this.put("iid", id).put("pid", pid).put("userName", uname).put("imgUrl", "");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
