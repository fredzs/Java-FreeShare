package com.free4lab.freeshare.manager;

import com.free4lab.freeshare.manager.factory.IScoreManager;
import com.free4lab.freeshare.model.dao.UserScoreDAO;

public class ListScoreManager implements IScoreManager {
	private UserScoreDAO usd = new UserScoreDAO();

	public boolean addPublish(Integer id, Integer uid) {
		if (usd.create(uid, id, "list", 50.0f) == null)
			return false;
		else
			return true;
	}

	/**
	 * 通过kind和type值，相应更新评分记录
	 * 
	 * @param uid
	 *            用户
	 * @param id
	 *            资源
	 * @param kind
	 *            1：顶，2：踩
	 */
	public boolean addUpDown(Integer id, Integer uid, String kind) {
		if (kind.equals("up")) {
			usd.update(uid, id, "list", 1);
		} else
			usd.update(uid, id, "list", 2);
		return true;
	}

	public boolean addBrowse(Integer id, Integer uid) {
		usd.update(uid, id, "list", 3);
		return true;
	}

	public boolean addReply(Integer id, Integer uid) {
		usd.update(uid, id, "list", 4);
		return true;
	}

}
