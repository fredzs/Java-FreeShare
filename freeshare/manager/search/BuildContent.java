package com.free4lab.freeshare.manager.search;

import org.json.JSONException;

import com.free4lab.freeshare.BehaviorConst;
import com.free4lab.freeshare.ResourceTypeConst;

import com.free4lab.freeshare.action.recommend.Build;
import com.free4lab.freeshare.manager.VideoManager;
import com.free4lab.freeshare.model.adapter.ObjectAdapter;
import com.free4lab.freeshare.model.dao.Item;
import com.free4lab.freeshare.model.data.BasicContent;
import com.free4lab.freeshare.model.data.Content;
import com.free4lab.freeshare.model.data.ContentFaced;
import com.free4lab.freeshare.model.data.StandardContent;
import com.free4lab.freeshare.model.data.VideoContent;
import com.free4lab.freeshare.util.Tool;

public class BuildContent extends Build{

	public String buildContent(ObjectAdapter adapter)
			throws JSONException {
		String description = Tool.delhtml(adapter.getDescription());
		BasicContent bContent;
		//根据发布时间是否和编辑时间相同，判断是编辑还是发布资源，并相应打上tag
		if(adapter.getPublicTime().equals(adapter.getEditTime())){
			bContent = new BasicContent(adapter.getAuthorId(),
					description, adapter.getType(), BehaviorConst.TYPE_CREATE);
		}else{
			bContent = new BasicContent(adapter.getAuthorId(),
					description, adapter.getType(), BehaviorConst.TYPE_EDIT);
		}
		
		String content = ContentFaced.getContent(bContent,
				returnContent(adapter));
		return content;
	}
	//根据资源类型，建立不同资源特有的content
	public static Content returnContent(ObjectAdapter adapter) {

		Content content = new Content();
		String imgUrl = Tool.getImgSrc(adapter.getDescription());
		Integer type = adapter.getType();
		// TODO 重构
		// 根据type判断究竟是何种资源类型，再加以构建索引
		if (type != -1) {
			Item ITEM = adapter.getItem();
			String enclosure = ITEM.getContent();
			if (type.equals(ResourceTypeConst.TYPE_DOC)) {
				if (enclosure == null || enclosure == "") {
					return null;
				}
				content = new StandardContent(imgUrl, enclosure);
			} else if (type.equals(ResourceTypeConst.TYPE_URL)) {
				if (!enclosure.startsWith("http://")
						&& !enclosure.startsWith("https://")) {
					enclosure = "http://" + enclosure;
				}
				content = new StandardContent(imgUrl, enclosure);
			} else if (type.equals(ResourceTypeConst.TYPE_VIDEO_URL)) {
				try {
					String swfUrl = new VideoManager().getFlash(enclosure);
					String videoImgUrl = new VideoManager()
							.getImgsrc(enclosure);
					content = new VideoContent(imgUrl, enclosure, swfUrl,
							videoImgUrl);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else {
				content = new StandardContent(imgUrl, "");
			}
		} else {
			content = new StandardContent(imgUrl, "");
		}
		return content;
	}
}
