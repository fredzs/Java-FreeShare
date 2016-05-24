package com.free4lab.freeshare.manager;

import java.util.List;

import com.free4lab.freeshare.model.dao.*;

/**
 * 针对单个用户的得分记录的操作
 * 
 * @author Administrator
 * 
 */
public class FreeUserScoreManager {
	/**
	 * 更新用户的评分记录
	 * @param uid 用户的id
	 * @param type 要更新的类型
	 *  -1:红心，0：发表，1：回复，2：浏览
	 * @param score 增加的分数
	*/
	public static void updateFus(Integer uid, Integer type, Integer score) {
		FreeUserScore fus = new FreeUserScoreDAO().findFusByUid(uid);
		if (fus != null) {
			new FreeUserScoreDAO().updateFus(uid, type, score);
		} else {
			new FreeUserScoreDAO().create(uid, 0, 0, 0, 0, 0, 0, 0);
		}
	}
	public static Integer getScoreByUid(Integer uid){
		Integer score = 0;
		FreeUserScore fus = new FreeUserScoreDAO().findFusByUid(uid);
		score += fus.getBrowse()/10;
		score += fus.getPublish();
		score += fus.getRedHeart() * 2;
		return score;
	}

	public static void deleteFusByUid(Integer uid) {
		new FreeUserScoreDAO().delete(uid);
	}

	public static List<FreeUserScore> findAll() {
		return new FreeUserScoreDAO().findAll();
	}
}
