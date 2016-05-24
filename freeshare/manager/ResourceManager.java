/**
 * 
 */
package com.free4lab.freeshare.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.python.antlr.PythonParser.return_stmt_return;

import com.free4lab.freeshare.action.list.DeleteRelationAction;
import com.free4lab.freeshare.manager.factory.ResourceFactory;
import com.free4lab.freeshare.model.dao.ListRelation;
import com.free4lab.freeshare.model.dao.Resource;

/**
 * @author wenlele
 *when to add the resource(item) into a list or lists,
 *we need to create a relation between item and list.
 *
 */
public class ResourceManager {

	private static final Logger logger = Logger.getLogger(ResourceManager.class);

	private static ResourceFactory resourceFactory = null;
	private static ListRelationManager listRelationManager = null;
	
	private ResourceFactory getResourceFactory(){
		if(resourceFactory == null){
			resourceFactory = ResourceFactory.getInstance();
		}
		return resourceFactory;
	}
	private ListRelationManager getListRelationManager(){
		if(listRelationManager == null){
			listRelationManager = ListRelationManager.getInstance();
		}
		return listRelationManager;
	}
	//根据字段创建资源，并返回创建的对象。
	public Resource createResource(Integer type, String name, String description,
			Integer authorId, String attachment){
		return getResourceFactory().createResource(type,name,description,authorId,attachment);
	}
	//创建资源，并返回创建的对象。
	public Resource createResource(Resource resource){
		return getResourceFactory().createResource(resource);
	}
	//更新资源
	public Resource updateResource(Resource resource){
		return getResourceFactory().updateResource(resource);
	}
	//根据id，删除资源。
	public boolean deleteResource(Integer resourceId){
		return getResourceFactory().deleteResource(resourceId);
	}
	//根据资源id，读取一个资源，并返回。
	public Resource readResource(Integer resourceId){
		return getResourceFactory().getResource(resourceId);
	}
	//根据多个资源id，读取多个资源，并返回。
	public List<Resource> readMutilResource(List<Integer> resourceIds){
		return getResourceFactory().getMutilResource(resourceIds);
	}
	//创建一个资源与列表的关系。
	public boolean createListRelation(Integer resourceId,Integer listId){
		getListRelationManager().createListRelation(resourceId, listId);
		return true;
	}
	//通过资源id和列表id获取它们的关系。
	public ListRelation obtainRelation(Integer resourceId,Integer listId){
		
		return getListRelationManager().obtainListRelation(resourceId, listId);
	}
	//根据资源的id获取资源所在的列表和资源关系。
	public List<ListRelation> obtainRelationByResource(Integer resourceId){
		
		return getListRelationManager().obtainRelationByResource(resourceId);
	}
	//通过列表的id获取该列表和资源关系。
	public List<ListRelation> obtainRelationByList(Integer listId){
		return getListRelationManager().obtainRelationByList(listId);
	}
	//判断资源是否在列表中。
	public boolean isListRelation(Integer resourceId,Integer listId){
		
		if(null != getListRelationManager().obtainListRelation(resourceId, listId)){
			return true;
		}
		else{
			return false;
		}
	}
	//通过资源id和列表id删除资源和列表的关系。
	public boolean deleteRelation(Integer resourceId, Integer listId){
		return getListRelationManager().deleteListRelation(resourceId, listId);
	}
	//通过资源id删除该资源和列表的关系。
	public int deleteResourceRelations(Integer resourceId){	
		return getListRelationManager().deleteResourceRelations(resourceId);
		
	}
	//通过列表id删除资源和列表的关系。
	public int deleteListRelations(Integer listId){	
		return getListRelationManager().deleteListRelations(listId);
		
	}
	public static void main(String[] args){
		Integer type = 0;
		String name = "shuangtest2";
		String description = "description";
		Integer authorId = 78;
		String attachment = "attachment";
		ResourceManager rManager = new ResourceManager();
		rManager.createResource(type,name,description,authorId,attachment);
		//rManager.getresourceFactory().createResource(type,name,description,authorId,attachment);
	}
	
}
