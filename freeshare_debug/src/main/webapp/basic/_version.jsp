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
		<s:if test="srhResult.docs.size>0">
			<tr>
				<td width="120">更新时间</td>
				<td>修订说明</td>
				<td width="140">操作</td>
			</tr>
		</s:if>
		<s:iterator id="doc" value="srhResult.docs" status="st">
			
			<tr>
				<td class="topveralign"><s:property value="#doc.time" /></td>
				<td class="topveralign" id = "td<s:property value = "#st.index"/>">
					<span id="<s:property value="#doc.url"/>">
						<s:if test = "#doc.description.length() > 50">
							<s:property value="#doc.description.substring(0,50)" escape="false" />
						</s:if>
						<s:else>
							<s:property value="#doc.description" escape="false" />
						</s:else></span></td>
				<td id="desc" style="display: none;">
					<s:if test = "#doc.description.length > 50">
					<s:property value="#doc.description.substring(0,50)" escape="false" />
					</s:if>
					<s:else>
					<s:property value="#doc.description" escape="false" />
					</s:else><br />
				 </td>

				<td class="topveralign"><a
					href="http://freedisk.free4lab.com/download?id=<s:property value="id"/>&uuid=<s:property value="#doc.url"/>">下载</a>&nbsp;|&nbsp;
					<a href="resource/deleteversion?itemId=<s:property value="id"/>&url=<s:property value="#doc.url"/>"
					onclick="return confirm('确认删除?');">删除</a>&nbsp;|&nbsp;
					&nbsp;<a id = "a<s:property value = "#st.index"/>" href = "javascript:void(0)" 
						onclick = "javascript:edit_desc('td<s:property value = "#st.index"/>','a<s:property value = "#st.index"/>')">编辑描述</a>
					</td>
			</tr>
		</s:iterator>
	</table>
</div>