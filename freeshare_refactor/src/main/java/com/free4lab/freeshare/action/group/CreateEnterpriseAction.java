package com.free4lab.freeshare.action.group;

import org.apache.log4j.Logger;
import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.model.dao.CompanyDAO;
import com.free4lab.freeshare.util.CompanyUser;


public class CreateEnterpriseAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateEnterpriseAction.class);
	private String name;
	private String desc;
	


	public String execute() {

		if (createEnterprise()) {
			return SUCCESS;
		} else
			return INPUT;
	}

	
	public boolean createEnterprise() {
		logger.info("name:"+name);
		Integer userId = new BaseAction().getSessionUID();
		//name = "tecent";
		new CompanyDAO().create(1, name, getSessionUID(), desc);
		new CompanyUser().updateCompanyOfUser(userId, name);
		return true;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}




	
}
