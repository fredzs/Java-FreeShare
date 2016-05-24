package com.free4lab.freeshare;
/**
 * 记录freeshare在freesearch的索引tag值，其值待抽离出.properties文件
 * 在搜索的时候，添加相应的搜索tag
 * @author 
 *
 */
public class TagValuesConst {

	/**
	 * == 搜索tag一览 ==
	 * 列表/资源/群组/评论 类型 ： SHARE:FMT:LIST / SHARE:FMT:ITEM
	 * 组所属 ： SHARE:GROUP:groupId
	 * 创建者 ： SHARE:AUTHOR:userId
	 * 当一个资源属于一个列表时 ： SHARE:LIST:ITEM:123
	 * 资源版本tag ： SHARE:ITEM:VERSION:234
	 * 资源类型 ：由带有p前缀的tag来明确标识哪一种资源
	 */
	public static final String FMT_LIST = "SHARE:FMT:LIST";
	public static final String FMT_ITEM = "SHARE:FMT:ITEM";
	public static final String FMT_GROUP = "SHARE:FMT:GROUP";
	public static final String FMT_COMMENT = "SHARE:FMT:COMMENT";
	
	public static final String pGROUP = "SHARE:GROUP:";
	public static final String pAUTHOR = "SHARE:AUTHOR:";
	public static final String pGROUP_ITEM = "SHARE:ITEM:GROUP:";
	public static final String pGROUP_LIST = "SHARE:LIST:GROUP:";
	
	public static final String pITEM_DOC = "SHARE:ITEM:DOC";
	public static final String pITEM_URL = "SHARE:ITEM:URL";
	public static final String pITEM_VIDEO = "SHARE:ITEM:VIDEO";
	public static final String pITEM_TOPIC = "SHARE:ITEM:TOPIC";
	public static final String pITEM_TEXT = "SHARE:ITEM:TEXT";
	public static final String pITEM_VERSION = "SHARE:ITEM:VERSION:";
	public static final String pITEM_PHOTO = "SHARE:ITEM:PHOTO";
	public static final String pITEM_ALBUM = "SHARE:ITEM:ALBUM";
	public static final String pITEM_IN_LIST ="SHARE:LIST:ITEM:";
	
	
	//如下为标识操作类型的tag，当进行某种操作的时候，对该索引添加上该tag
	//用以在筛选特定的操作动态的时候使用
	public static final String pCREATE ="SHARE:CREATE";
	public static final String pEDIT ="SHARE:EDIT";
	public static final String pNEWVERSION ="SHARE:NEWVERSION";
	public static final String pINLIST ="SHARE:INLIST";
	public static final String pOUTLIST ="SHARE:OUTLIST";
}
