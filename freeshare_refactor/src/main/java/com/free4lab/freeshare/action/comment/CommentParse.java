package com.free4lab.freeshare.action.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.manager.search.SearchManager;
import com.free4lab.freeshare.model.dao.Comment;
import com.free4lab.freeshare.model.data.Doc4Srh;
import com.free4lab.freeshare.model.data.SrhResult;
import com.free4lab.utils.account.UserInfo;
import com.free4lab.utils.account.UserInfoUtil;

/**
 * 
 * 处理查找评论，根据评论得到用户头像
 * 
 * @author zhaowei
 * 
 */
public class CommentParse {
	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CommentParse.class);
	// 该资源的所有评论
/*	public List<Doc4Srh> findComment(Integer id, Integer type, Integer page) {
		logger.info("开始查找评论,查找资源id为" + id + "， 查找类型为：" + type);
		String tags = "";
		if (type == -1) {
			tags = "list?id=" + id;
		} else{
			tags = "item?id=" + id;
		}
		tags += " AND " + TagValuesConst.FMT_COMMENT;
		if (null == page)
			page = 1;
		SrhResult srhResult = SearchManager.searchCmt(page, 20, tags);
		if (null == srhResult) {
			logger.info("result is null");
			return new ArrayList<Doc4Srh>();
		}
		return srhResult.getDocs();
	}*/

	public Map<Integer, UserInfo> findUserAvatar(List<Comment> docs,
			String token) {
		// TODO 当用户未登录的时候，评论的显示
		Map<Integer, UserInfo> m = new HashMap<Integer, UserInfo>();
		if (token != null) {
			List<Integer> userIdList = new ArrayList<Integer>();
			for (int i = 0; i < docs.size(); i++) {
				userIdList.add(docs.get(i).getUserId());
			}
			List<UserInfo> uiList = UserInfoUtil.getUserInfoByUid(token,
					userIdList);
			for(UserInfo ui : uiList){
				Integer uid = Integer.parseInt(ui.getUserId());
				m.put(uid, ui);
			}
		}
		return m;
	}
}
