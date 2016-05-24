package com.free4lab.freeshare.action.list;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.TagValuesConst;
import com.free4lab.freeshare.URLConst;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.manager.search.NewIndexManager;
import com.free4lab.freeshare.manager.search.SearchManager;
import com.free4lab.freeshare.model.data.SrhResult;
import com.free4lab.freeshare.model.data.Doc4Srh;
import com.free4lab.utils.http.SearchClient;
import com.free4lab.utils.perflog.FatalLogger;

/**
 * 处理对列表中的资源调整次序
 * 
 * @author zhaowei
 * 
 */
public class ChangeOrderAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ch_url;
	private String chto_url;
	private Integer lid;
	private Integer order;
	private static final Logger logger = Logger
			.getLogger(ChangeOrderAction.class);

	public String execute() {
		Runnable r = new ChangeOrder(ch_url, chto_url, lid, order);
		Thread t = new Thread(r);
		t.start();
		return SUCCESS;
	}

	public String getCh_url() {
		return ch_url;
	}

	public void setCh_url(String ch_url) {
		this.ch_url = ch_url;
	}

	public String getChto_url() {
		return chto_url;
	}

	public void setChto_url(String chto_url) {
		this.chto_url = chto_url;
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	// 异步调整两个资源中的IndexTagValues.pITEM_IN_LIST+lid这个tag的得分，以调整资源在列表中的次序
	class ChangeOrder implements Runnable {
		private String chUrl;
		private String chToUrl;
		private Integer listId;
		private Integer order_;

		public ChangeOrder(String chUrl, String chToUrl, Integer listId,
				Integer order) {
			this.chUrl = chUrl;
			this.chToUrl = chToUrl;
			this.listId = listId;
			this.order_ = order;
		}

		public void run() {
			SearchClient client = new SearchClient(URLConst.APIPrefix_FreeSearch);
			String tag = TagValuesConst.pITEM_IN_LIST + listId;
			logger.info("the chUrl is " + chUrl + ", and the chToUrl is "
					+ chToUrl + "\nthe _order is " + order_ + " the tag is "
					+ tag);
			long preTagValue = 0;
			long nextTagValue = 0;
			// 如果调整到第一个位置
			if (order_ == 1) {
				try {
					logger.info("order_ == 1 : abc");
					Date d = new Date();
					long longtime = d.getTime();
					logger.info("the value is " + -longtime);
					client.setTagValue(chUrl, tag, -longtime);
				} catch (Exception e) {
					FatalLogger.log("FREESHARE", "changeorder",
							"change_url :" + chUrl + ",change_order:" + order);
					e.printStackTrace();
				}
			} else {
				//如果调整到最后一个位置,而且是当前页
				if(order == 8){
					logger.info("order == 8 : abc");
					try {
						Date d = new Date();
						long longtime = d.getTime();
						client.setTagValue(chUrl, tag, longtime);
					} catch (Exception e) {
						FatalLogger.log("FREESHARE", "changeorder",
								"change_url :" + chUrl + ",change_order:"
										+ order);
						e.printStackTrace();
					}
				} else {
					logger.info("orde else : abc");
					int page = order % 8 == 0 ? order/8 : order/8 + 1; 
					SrhResult srhResult = SearchManager.searchListItems(page, 8,
							tag);
					List<Doc4Srh> docs = srhResult.getDocs();
					int i = 0;
					int size = docs.size();
					while (i < size - 1
							&& !docs.get(i).getUrl().equals(chToUrl)) {
						i++;
					}
					String preUrl = docs.get(i - 2).getUrl();
					preTagValue = new NewIndexManager().getTagValue(preUrl, tag);
					nextTagValue =  new NewIndexManager().getTagValue(chToUrl, tag);
					// 如果两个value想等则全部重排
					if (preTagValue == nextTagValue) {
						//TODO 重排全部
						logger.info("orde else value equal : abc");
					} else {
						logger.info("orde else value equal else: abc");
						long value = (preTagValue + nextTagValue) / 2;
						try {
							logger.info("the churl is " + chUrl + "the reset value is " + value);
							client.setTagValue(chUrl, tag, value);
						} catch (Exception e) {
							FatalLogger.log("FREESHARE",
									"changeorder", "change_url :" + chUrl
											+ ",change_order:" + order);
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
