package com.free4lab.freeshare.action.account;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.FreeUserScoreManager;

/**
 * 处理和用户经验值相关
 * 获取自己最终得分
 * 减少得分
 * @author zhaowei
 *
 */
public class UserScoreAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer score;
	public String execute(){
		score = FreeUserScoreManager.getScoreByUid(getSessionUID());
		return SUCCESS;
	}
	
	public String reduceScore(){
		
		return SUCCESS;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
