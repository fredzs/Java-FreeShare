package com.free4lab.freeshare.manager.factory;

import java.util.List;

/**
 * 暂时为使用
 * @deprecated
 * @author Administrator 产生建立索引的各种策略
 * 
 */

public class IndexContext {
	private IndexManagerFactory imf;

	public IndexManagerFactory getIndexManagerFactory(IndexManagerFactory imf) {
		this.imf = imf;
		return imf;
	}

	/**
	 * 增加一条索引内容
	 * 
	 */
	public boolean addTags(String url, List<String> tags){
		boolean tf = imf.addTags(url, tags);
		return tf;
	}
	public boolean delIndex(String url){
		boolean tf = imf.delIndex(url);
		return tf;
	}
	
}
