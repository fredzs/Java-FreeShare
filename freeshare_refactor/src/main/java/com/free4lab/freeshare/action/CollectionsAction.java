package com.free4lab.freeshare.action;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import com.free4lab.freeshare.manager.ResourceManager;
import com.free4lab.freeshare.model.dao.UserCollection;
import com.free4lab.freeshare.model.dao.UserCollectionDAO;
import com.free4lab.freeshare.model.wrapper.ResourceWrapper;

/**
 * 
 * @author zhaowei 添加收藏/查看我的收藏/删除某个收藏
 * 
 */
public class CollectionsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(CollectionsAction.class);
	private Integer id;
	private String type;
	private Integer page;
	private Integer lastPage;
	private Integer collectionSize;
	private List<ResourceWrapper> myCtionList;

	private final static Integer SIZE = 10;// 搜索结果一页呈现多少个结果

	// 添加一个收藏
	public String execute() {
		if (type == null) {
			setType("item");
		}
		if (new UserCollectionDAO().isExist(getSessionUID(), id, type)) {
			new UserCollectionDAO().create(getSessionUID(), id, type);
		}
		return SUCCESS;
	}

	// 获取我的收藏
	public String collections() {
		logger.info("查看我的收藏,资源类型为：" + type);
		if (page == null) {
			page = 1;
		}
		if (type == null || type == "") {
			setType("item");
		}
		List<UserCollection> userCollections = new UserCollectionDAO()
				.findByUid(getSessionUID(), type);
		myCtionList = new LinkedList<ResourceWrapper>();

		if (userCollections != null && userCollections.size() != 0) {
			Integer PAGE = getPage() - 1;
			Integer size = PAGE * SIZE;
			// TODO 修改批量加载，而不是每次都查询一次
			for (int i = size; i < size + 10 && i < userCollections.size(); i++) {
				UserCollection uc = userCollections.get(i);
				try {
					if (type.equals("item")) {
						ResourceWrapper item = new ResourceManager()
								.readResourceWrapper(uc.getIid());
						if (item != null) {
							String description = delhtml(item.getDescription());
							if (description.length() > 50) {
								description = description.substring(0, 49)
										+ "...";
							}
							item.setDescription(description);
							myCtionList.add(item);
						}
					} else {
						ResourceWrapper list = new ResourceManager()
								.readResourceWrapper(uc.getIid());
						if (list != null) {
							String description = delhtml(list.getDescription());
							if (description.length() > 50) {
								description = description.substring(0, 49)
										+ "...";
							}
							list.setDescription(description);
							myCtionList.add(list);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		if (null == myCtionList) {
			logger.info("您的收藏为空");
			setLastPage(1);
		} else {
			setCollectionSize(userCollections.size());
			if (userCollections.size() % SIZE == 0) {
				setLastPage(userCollections.size() / SIZE);
			} else
				setLastPage(userCollections.size() / SIZE + 1);
		}
		return SUCCESS;
	}

	// 删除一个收藏
	public String deleteColl() {
		logger.info("删除我的收藏,资源类型为：" + type);
		if (type == null || type == "") {
			setType("item");
		}
		new UserCollectionDAO().delete(getSessionUID(), id, type);
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getCollectionSize() {
		return collectionSize;
	}

	public void setCollectionSize(Integer collectionSize) {
		this.collectionSize = collectionSize;
	}

	public List<ResourceWrapper> getMyCtionList() {
		return myCtionList;
	}

	public void setMyCtionList(List<ResourceWrapper> myCtionList) {
		this.myCtionList = myCtionList;
	}

}
