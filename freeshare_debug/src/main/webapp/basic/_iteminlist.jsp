<!--
使用该文件的地方：
basic/browse.jsp
-->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="permission <= 2">
	<s:if test="resourceType == -1">
		<s:if test="srhResult.totalNum>0">
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

				<s:iterator id="doc" value="srhResult.docs" status="st">
					<tr id="<s:property value="#st.index + 1"/>_tr">
						<td width="5px"><s:property value="#st.index +1" /></td>
						<td><a href="<s:property value="#doc.url"/>"> 
							<s:property value="#doc.title" /></a>
						</td>
						<td><span id="<s:property value="#doc.url"/>">
							<s:if test = "#doc.description.length() > 50">
								<s:property value="#doc.description.substring(0,50)" escape="false"/>
								</s:if>
							<s:else>
								<s:property value="#doc.description" escape="false"/>
							</s:else>
						</span></td>
						<td><s:property value="map[#doc.userId].userName" /></td>
						<td id="<s:property value="#st.index + 1"/>">
							<div id="<s:property value="#doc.url.substring(8, #doc.url.length())"/>">
								<a href="resource/delrelation?id=<s:property value="id"/>&itemId=<s:property value="#doc.url.substring(8, #doc.url.length())"/>&type=-1"
									onclick="return confirm('确认移出列表?');">移出列表</a>&nbsp;|&nbsp; 
								<a href="javascript:void(0)" id="ch_href"
									onclick="sort_order('<s:property value="#doc.url.substring(8,#doc.url.length())"/>');">调整次序</a>
							</div>
						</td>
					</tr>
				</s:iterator>
			</table>
			<div class="divpage">
				<s:include value="/_page/_pager.jsp">
					<s:param name="url">
				   list?id=<s:property value="id" />&
				</s:param>
					<s:param name="currPage" value="page" />
					<s:param name="endPage" value="lastPage" />
				</s:include>
			</div>
		</s:if>
	</s:if>
</s:if>