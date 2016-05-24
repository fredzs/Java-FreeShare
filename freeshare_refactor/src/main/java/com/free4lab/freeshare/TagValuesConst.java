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
	 * 列表/资源/群组/评论 类型 ： SHARE:FMT:LIST / SHARE:FMT:RESOURCE
	 * 组所属 ： SHARE:GROUP:groupId
	 * 创建者 ： SHARE:AUTHOR:userId
	 * 当一个资源属于一个列表时 ： SHARE:LIST:RESOURCE:123
	 * 资源版本tag ： SHARE:RESOURCE:VERSION:234
	 * 资源类型 ：由带有p前缀的tag来明确标识哪一种资源
	 */
	public static final String FMT_RESOURCE = "SHARE:FMT:RESOURCE";
	public static final String FMT_GROUP = "SHARE:FMT:GROUP";
	public static final String FMT_COMMENT = "SHARE:FMT:COMMENT";
	
	public static final String pGROUP = "SHARE:GROUP:";
	public static final String pAUTHOR = "SHARE:AUTHOR:";
//	public static final String pGROUP_RESOURCE = "SHARE:RESOURCE:GROUP:";
	
	public static final String pRESOURCE_DOC = "SHARE:RESOURCE:DOC";
	public static final String pRESOURCE_URL = "SHARE:RESOURCE:URL";
	public static final String pRESOURCE_VIDEO = "SHARE:RESOURCE:VIDEO";
	public static final String pRESOURCE_TOPIC = "SHARE:RESOURCE:TOPIC";
	public static final String pRESOURCE_TEXT = "SHARE:RESOURCE:TEXT";
	public static final String pRESOURCE_VERSION = "SHARE:RESOURCE:VERSION:";
	public static final String pRESOURCE_LIST ="SHARE:RESOURCE:LIST";
	
	
	//如下为标识操作类型的tag，当进行某种操作的时候，对该索引添加上该tag
	//用以在筛选特定的操作动态的时候使用
	public static final String pCREATE ="SHARE:CREATE";
	public static final String pEDIT ="SHARE:EDIT";
	public static final String pNEWVERSION ="SHARE:NEWVERSION";
	public static final String pINLIST ="SHARE:INLIST";
	public static final String pOUTLIST ="SHARE:OUTLIST";
}
