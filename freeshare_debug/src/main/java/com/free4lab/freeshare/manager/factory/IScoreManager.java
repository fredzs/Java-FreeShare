package com.free4lab.freeshare.manager.factory;

/**
 * @author Administrator
 * 对score的相关操作的接口，让不同对象实现
 *
 */
//TODO 重构，更改位置
public interface IScoreManager {
	public abstract boolean addPublish(Integer id, Integer uid);
	public abstract boolean addBrowse(Integer id, Integer uid);
	public abstract boolean addUpDown(Integer id, Integer uid, String type);
	public abstract boolean addReply(Integer id, Integer uid);
}
