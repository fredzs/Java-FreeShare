package com.free4lab.freeshare;

import java.io.IOException;
import java.util.Properties;

/**
 * share上的所有类型统一使用type来标识，其作用：
 * 1：根据type来为一条索引打上不同的标签
 * 2：用以在获取search返回数据之后用以判断是何种类型
 * 
 * TYPE_LIST = -1
 * TYPE_TOPIC = 0
 * TYPE_DOC = 1
 * TYPE_URL = 2
 * TYPE_VIDEO_URL = 3
 * TYPE_TEXT = 4
 * 
 * TYPE_VERSION = 5
 * 
 * TYPE_GROUP = 6
 * TYPE_COMMENT = 7 
 * 
 * TYPE_ALBUM = 8
 * TYPE_PHOTO = 9
 * TYPE_FORMWORK = 10
 * @author Administrator
 *
 */
public class ResourceTypeConst{
	public static final Integer TYPE_LIST;//列表
	public static final Integer TYPE_TOPIC;//话题
	public static final Integer TYPE_DOC;//添加文本附件的文档
	public static final Integer TYPE_URL;//链接
	public static final Integer TYPE_VIDEO_URL;//视频链接
	public static final Integer TYPE_TEXT; //无附件的文章
	public static final Integer TYPE_ALBUM; //相册
	public static final Integer TYPE_PHOTO; //相片
	public static final Integer TYPE_VERSION; //版本
	public static final Integer TYPE_GROUP; //群组
	public static final Integer TYPE_COMMENT; //评论
	public static final Integer TYPE_FORMWORK;//模版
	static{
		try {
			Properties p = new Properties();
			p.load(ResourceTypeConst.class.getClassLoader().getResourceAsStream("resourcetype.properties"));
			TYPE_LIST = new Integer(Integer.parseInt(p.getProperty("TYPE_LIST")));
			TYPE_TOPIC = new Integer(Integer.parseInt(p.getProperty("TYPE_TOPIC")));
			TYPE_DOC = new Integer(Integer.parseInt(p.getProperty("TYPE_DOC")));
			TYPE_URL = new Integer(Integer.parseInt(p.getProperty("TYPE_URL")));
			TYPE_VIDEO_URL = new Integer(Integer.parseInt(p.getProperty("TYPE_VIDEO_URL")));
			TYPE_TEXT = new Integer(Integer.parseInt(p.getProperty("TYPE_TEXT")));
			TYPE_ALBUM =  new Integer(Integer.parseInt(p.getProperty("TYPE_ALBUM")));
			TYPE_PHOTO = new Integer(Integer.parseInt(p.getProperty("TYPE_ALBUM")));
			TYPE_VERSION = new Integer(Integer.parseInt(p.getProperty("TYPE_VERSION")));
			TYPE_GROUP  = new Integer(Integer.parseInt(p.getProperty("TYPE_GROUP")));
			TYPE_COMMENT = new Integer(Integer.parseInt(p.getProperty("TYPE_COMMENT")));
			TYPE_FORMWORK = new Integer(Integer.parseInt(p.getProperty("TYPE_FORMWORK")));
		} catch (IOException e) {
			 throw new RuntimeException("Failed to init app configuration", e);
		}
	}
}
