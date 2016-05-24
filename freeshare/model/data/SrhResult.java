package com.free4lab.freeshare.model.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.free4lab.freeshare.model.data.Doc4Srh;

public class SrhResult {

	/**
	 * 搜索返回的JSON结构
	 * { 		
	 * "beginNum":1,
	 * "endNum":2,
	 * "keyword":"free",
	 * "parameter":{"q":["free"],"size":["2"],"type":["json"]},
	 * "result":
	 *   [
	 *     {
	 *       "doc":{"content":"= FreeSearch 开放Api设计 = ... ",
	 *       "time":"2011-07-24",
	 *       "title":"Free Search 开放Api设计",
	 *       "url":"http:\/\/59.64.179.210:8484\/FreeSearch\/Api",
	 *       "urlMd5":-4827329742059431534},
	 *       "score":5.0
	 *     },
	 *     {
	 *       "doc":{"content":"[b][font=黑体][size=4]Free Account[\/size][\/font][\/b]\r\n...",
	 *       "time":"2011-07-24",	
	 *       "title":"Free Account 简易使用介绍",
	 *       "url":"http:\/\/59.64.179.52\/discuz\/viewthread.php?tid=323",
	 *       "urlMd5":-1174783999968517032},
	 *       "score":4.0
	 *     }
	 *   ],
	 * "searchTime":34,
	 * "totalNum":10
	 * }
	 */
	private Integer beginNum;
	private Integer endNum;
	private String keyword;
	private List<Doc4Srh> docs;
	private Integer totalNum;
	private Integer searchTime;
	
	//返回结果的参数常量
	private static final String BEGIN_NUMBER = "beginNum";
	private static final String END_NUMBER = "endNum";
	private static final String KEYWORD = "keyword";
	private static final String RESULT = "result";
	private static final String DOC = "doc";
	private static final String TOTAL_NUM = "totalNum";
	private static final String SRH_TIME = "searchTime";
	public SrhResult(){}
	public SrhResult(JSONObject jsonObj) throws Exception{		
		this.setBeginNum(jsonObj.getInt(BEGIN_NUMBER));
		this.setEndNum(jsonObj.getInt(END_NUMBER));
		this.setSearchTime(jsonObj.getInt(SRH_TIME));
		this.setTotalNum(jsonObj.getInt(TOTAL_NUM));
		this.setKeyword(jsonObj.getString(KEYWORD));
		
		docs = new ArrayList<Doc4Srh>();
		JSONArray resultList = jsonObj.getJSONArray(RESULT);
		//TODO 解析改成批量解析，而不是批量调用
		for (int i = 0; i < resultList.length(); i++){
			JSONObject o = resultList.getJSONObject(i);
			try{
				Doc4Srh aDoc = new Doc4Srh(o.getJSONObject(DOC));
				
				if (aDoc != null){
					docs.add(aDoc);
				}
			}catch(Exception E){
				E.printStackTrace();
			}
		}
	}
	
	
	public Integer getBeginNum() {
		return beginNum;
	}
	public void setBeginNum(Integer beginNum) {
		this.beginNum = beginNum;
	}
	public Integer getEndNum() {
		return endNum;
	}
	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<Doc4Srh> getDocs() {
		return docs;
	}
	public void setDocs(List<Doc4Srh> docs) {
		this.docs = docs;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(Integer searchTime) {
		this.searchTime = searchTime;
	}
}
