<!--
使用该文件的地方：
basic/_maininfo.jsp
-->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="oldversion" style="dispaly: none">
	<table class="datatable">
		<s:if test="docsNum>0">
			<tr>
				<td width="120">更新时间</td>
				<td>修订说明</td>
				<td width="140">操作</td>
			</tr>
		</s:if>
		<s:iterator id="doc" value="docs" status="st">
		
			<s:if test="#st.first">
			<tr>
				<td class="topveralign"><s:property value="#doc.editTime.toString().substring(0,19)" /></td>
				<td class="topveralign" id = "td<s:property value = "#st.index"/>">
					<span id="<s:property value="#doc.id"/>">
						最初版本
					</span></td>					
				<%-- <td id="desc" style="display: none;">
					<s:if test = "#doc.description.length > 50">
					<s:property value="#doc.description.substring(0,50)" escape="false" />
					</s:if>
					<s:else>
					<s:property value="#doc.description" escape="false" />
					</s:else><br /></td> --%>
				<td class="topveralign"><a class="blueletter"
					href="http://freedisk.free4lab.com/download?id=<s:property value="id"/>&uuid=<s:property value="#doc.uuid"/>">下载</a>
				</td>
			</tr></s:if>
			<s:else>
			<tr >
				<!-- 更新时间 -->
				<td class="topveralign"><s:property value="#doc.editTime.toString().substring(0,19)" /></td>
				<!-- 修订说明 -->
				<td class="topveralign" id = "td<s:property value = "#st.index"/>">
					<span id="<s:property value="#doc.id"/>">
						<s:if test = "#doc.description.length() > 50">
							<s:property value="#doc.description.substring(0,50)" escape="false" />
						</s:if>
						<s:else>
							<s:property value="#doc.description" escape="false" />
						</s:else></span></td>						
				<%-- <td id="desc" style="display: none;">
					<s:if test = "#doc.description.length > 50">
					<s:property value="#doc.description.substring(0,50)" escape="false" />
					</s:if>
					<s:else>
					<s:property value="#doc.description" escape="false" />
					</s:else><br />
				 </td>	 --%>		 
				<!-- 操作 -->
				<td class="topveralign" id="editTr<s:property value = "#st.index"/>"><a class="blueletter"
					href="http://freedisk.free4lab.com/download?id=<s:property value="id"/>&uuid=<s:property value="#doc.uuid"/>">下载</a>&nbsp;|&nbsp;
					<a class="blueletter" href="resource/deleteversion?id=<s:property value="#doc.itemId"/>&url=<s:property value="#doc.uuid"/>"
					onclick="return confirm('确认删除?');">删除</a>&nbsp;|&nbsp;
					&nbsp;<a id = "a<s:property value = "#st.index"/>" class="blueletter" href = "javascript:void(0)" 
						onclick = "javascript:edit_desc('tableTr<s:property value = "#st.index"/>','td<s:property value = "#st.index"/>','<s:property value="#doc.description"/>','<s:property value="#doc.uuid"/>')">编辑描述</a>
				</td>
			</tr>
			<tr id="tableTr<s:property value = "#st.index"/>" class="hidden"><td></td><td></td><td></td>
			</tr>
			</s:else>
		</s:iterator>
	</table>
</div>