package com.free4lab.freeshare.manager.factory;

import java.util.List;

/**
 * 定义了对索引的各种操作，实现由具体的实现类定义<br/>
 * 直接实现类：NewIndexManager
 * 
 * @see com.free4lab.freeshare.manager.search.NewIndexManager
 * @author zhaowei
 */
public interface IndexManagerFactory {

	public void addIndex(String url, String title, String content,
			List<String> tags);

	public boolean delIndex(String url);

	public boolean addTags(String url, List<String> tags);

	public boolean removeTags(String url, List<String> tags);

	public long getTagValue(String url, String tag);

	public void setTagValue(String url, String tag, long value);
}
