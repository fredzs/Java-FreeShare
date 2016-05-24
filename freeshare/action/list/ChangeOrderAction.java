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
	private Integer item_number;
	private Integer page;
	private Integer lastpage;
	private static final Logger logger = Logger
			.getLogger(ChangeOrderAction.class);

	public String execute() {
		Runnable r = new ChangeOrder(ch_url, chto_url, lid, order,item_number, page, lastpage);
		r.run();
		return SUCCESS;
	}
	
	// 异步调整两个资源中的IndexTagValues.pITEM_IN_LIST+lid这个tag的得分，以调整资源在列表中的次序
	class ChangeOrder implements Runnable {
		private String chUrl;
		private String chToUrl;
		private Integer listId;
		private Integer order_;
		private Integer item_number_;
		private Integer page_;
		private Integer lastpage_;
		
		public ChangeOrder(String chUrl, String chToUrl, Integer listId,
				Integer order,Integer item_number,Integer page,Integer lastpage) {
			this.chUrl = chUrl;
			this.chToUrl = chToUrl;
			this.listId = listId;
			this.order_ = order;
			this.item_number_ = item_number;
			this.page_  = page;
			this.lastpage_  = lastpage;
		}

		public void run() {
			SearchClient client = new SearchClient(URLConst.APIPrefix_FreeSearch);
			String tag = TagValuesConst.pITEM_IN_LIST + listId;
		
			if (item_number_ >= order_) {//资源从后往前调整
				if(order_ == 1 && page_ == 1){//调整到第一页第一项
					try {
						Date d = new Date();
						long longtime = d.getTime();
						client.setTagValue(chUrl, tag, -longtime);
					}catch (Exception e) {
						FatalLogger.log("FREESHARE", "changeorder",
								"change_url :" + chUrl + ",change_order:" + order_);
						e.printStackTrace();
					}
				}
				else{
					SrhResult srhResult = SearchManager.searchListItems(page_, 10,tag);
					List<Doc4Srh> docs = srhResult.getDocs();
					String preUrl = "";
					String nextUrl = docs.get(order_-1).getUrl();
					
					if(order_ == 1){//调整到当前页第一项（非首页）
						SrhResult pre_srhResult = SearchManager.searchListItems(page_-1, 10,tag);
						List<Doc4Srh> pre_docs = pre_srhResult.getDocs();
						
					    preUrl = pre_docs.get(9).getUrl();
						
					}else{//调整到当前页非第一项
						preUrl = docs.get(order_-2).getUrl();
					}
						
					long preTagValue = NewIndexManager.getInstance().getTagValue(preUrl, tag);
					long nextTagValue =  NewIndexManager.getInstance().getTagValue(nextUrl, tag);
					
					long value = preTagValue + (nextTagValue-nextTagValue)/10;
					try {
						client.setTagValue(chUrl, tag, value);
					} catch (Exception e) {
						FatalLogger.log("FREESHARE",
								"changeorder", "change_url :" + chUrl
										+ ",change_order:" + order);
						e.printStackTrace();
					}
				}
			}
			else{//资源从前往后调整
				SrhResult srhResult = SearchManager.searchListItems(page_, 10,tag);
				List<Doc4Srh> docs = srhResult.getDocs();
				
				if(order_ >= docs.size() && page_ == lastpage_){//末尾页-调整到最后和范围超出的情况
					try {
						Date d = new Date();
						long longtime = d.getTime();
						client.setTagValue(chUrl, tag, longtime);
					}catch (Exception e) {
						FatalLogger.log("FREESHARE", "changeorder",
								"change_url :" + chUrl + ",change_order:" + order_);
						e.printStackTrace();
					}
				}
				else{
					String preUrl = "";
					String nextUrl = "";
					
					if(order_ >= docs.size() ){ //调整为当前页的最后一项
						SrhResult next_srhResult = SearchManager.searchListItems(page_+1, 10,tag);
						List<Doc4Srh> next_docs = next_srhResult.getDocs();
						
						preUrl = docs.get(9).getUrl();
					    nextUrl = next_docs.get(0).getUrl();
					
					}
					else{ //调整到当前页的非末尾项
						preUrl = docs.get(order_-1).getUrl();
						nextUrl = docs.get(order_).getUrl();
					}
					
					long preTagValue = NewIndexManager.getInstance().getTagValue(preUrl, tag);
					long nextTagValue =  NewIndexManager.getInstance().getTagValue(nextUrl, tag);
					
					long value = preTagValue + (nextTagValue-nextTagValue)/10;
					try {
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
	
	public Integer getItem_number() {
		return item_number;
	}

	public void setItem_number(Integer item_number) {
		this.item_number = item_number;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLastpage() {
		return lastpage;
	}

	public void setLastpage(Integer lastpage) {
		this.lastpage = lastpage;
	}
}

