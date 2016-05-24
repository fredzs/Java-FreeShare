<!--
使用该文件的地方：
basic/browse.jsp
-->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="permission <= 2">
	<s:if test="resourceType == -1">
		 <s:if test="itemNum>0"> 
			<table id="item_table" class="datatable">
				<thead>
					<tr>
						<td width="20px"></td>
						<td width="200px">资源名称</td>
						<td width="260px">资源描述</td>
						<td width="40px">发布者</td>
						<td width="220px">操作</td>
					</tr>
				</thead>

				<s:iterator id="doc" value="item" status="st">
					<tr id="<s:property value="#st.index + 1"/>_tr">
						<td width="5px"><s:property value="#st.index +1+(page-1)*10" /></td>
						<td><a target="_blank" class="blueletter" href="resource?id=<s:property value="#doc.id"/>"> 
							<s:property value="#doc.name" /></a>
						</td>
						<td><span class="blackletter" id="resource?id=<s:property value="#doc.id"/>" >
							<s:if test = "#doc.description.length() > 50">
								<s:property value="#doc.description.substring(0,50)" escape="false"/>
							</s:if>
							<s:else>
								<s:property value="#doc.description" escape="false"/>
							</s:else> 
						</span></td>
						<td><s:property value="map[#doc.authorId].screen_name" /></td>
						<td id="<s:property value="#st.index + 1"/>">
							<div id="<s:property value="#doc.id"/>">
								<a class="blueletter" href="resource/delrelation?id=<s:property value="resourceId"/>&resourceId=<s:property value="#doc.id"/>&type=-1"
									 onclick="return confirm('确认移出列表?');">移出列表</a>&nbsp;|&nbsp; 
								<a class="blueletter" href="javascript:void(0)" id="ch_href"
									onclick="sort_order('<s:property value="#doc.id"/>','<s:property value="#st.index + 1"/>','<s:property value="page"/>','<s:property value="lastPage"/>');">调整次序</a>
							</div>
						</td>
					</tr>
				</s:iterator>
			</table>
			<div class="divpage">
				<s:include value="/_page/_pager.jsp">
					<s:param name="url">
				   resource?id=<s:property value="id" />&
				</s:param>
					<s:param name="currPage" value="page" />
					<s:param name="endPage" value="lastPage" />
				</s:include>
			</div>
		 </s:if> 
	</s:if>
</s:if>

<script src="js/resource/sort_order.js"></script> 

