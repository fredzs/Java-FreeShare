package com.free4lab.freeshare.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.manager.GroupUserManager;
import com.free4lab.freeshare.model.data.Group;

/**
 * 提供一些基本方法
 * 
 * @author Administrator
 * 
 */
public class Tool {

	public static String delhtml(String desc) {
		if (desc.length() > 0) {
			String str = desc.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
					"<[^>]*>", "");
			str = str.replaceAll("[(/>)<]", "");
			return str;
		} else
			return "";
	}

	public static String getImgSrc(String htmlStr) {
		String img = "";
		Pattern p_image;
		Matcher m_image;

		String regEx_img = "<img.*src=(.*?)[^>]*?>"; // 图片链接地址
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); // 匹配src
			while (m.find()) {
				img = m.group(1);
				break;
			}
		}
		if (img == null) {
			img = "";
		}
		return img;
	}

	/**
	 * 生成所需要的tag
	 * 
	 * @param userId
	 * @param groupIds
	 * @param rType
	 * @return String
	 */
	public static String returnTags(Integer userId, String groupIds,
			String rType) {
		String tags = "";
		if (groupIds == null || groupIds.equals("all") || groupIds.equals("")) {
			List<Group> groups = GroupUserManager.getMyGroups(userId);
			for (Group group : groups) {
				tags += " " + TagValuesConst.pGROUP + group.getGroupId();
			}
		} else {
			String[] groupIdArray = groupIds.split(",");
			for (String id : groupIdArray) {
				tags += " " + TagValuesConst.pGROUP + id;
			}
		}
		
		if (rType == null || rType.equals("all")) {
			return tags;
		}else{
			tags = "( " + tags + " )";
			if(rType.contains("SHARE:FMT:GROUP")){
				rType = rType.replace("SHARE:FMT:GROUP", "");
				tags += " AND (" + rType.replaceAll(",", " ")+")";
				tags = "( " + tags + " )"; 
				tags = "( SHARE:FMT:GROUP )" + tags;
			}else{
				tags += " AND (" + rType.replaceAll(",", " ") +")";
			}
			return tags;
		}
		//
		//当搜索类型里面含有群组类型的时候，特殊处理
		
		
	}
}
