package com.free4lab.freeshare.action.resource;

import com.free4lab.freeshare.action.BaseAction;
import com.free4lab.freeshare.model.dao.Formwork;
import com.free4lab.freeshare.model.dao.FormworkDAO;

public class UseFormworkAction extends BaseAction {
	
		private static final long serialVersionUID = 1L;
		private Integer id;
		private Integer type;
		private String name;
		private String content;

		public String execute() {
			/*ObjectAdapter adapter = new ObjectAdapter(10);
			Handler handler = HandlerFactory.getHandler(adapter);
			logger.info( ((String[])ActionContext.getContext().getParameters().get("id"))[0]);
			adapter = handler.getObject(id);
			if (null == adapter) {
				logger.info("找不到相关的资源");
				return INPUT;
			}*/
			Formwork myFormwork = (new FormworkDAO()).findById(id);
			// 资源类型
			setType(myFormwork.getType());
		
			// 资源id
			setId(id);
			
			// 资源名称
			setName(myFormwork.getName());
			
			// 资源描述
			setContent(myFormwork.getContent());
			return SUCCESS;
		}
		
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
}
