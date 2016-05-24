<!--
使用该文件的地方：
upload/_commonofresource.jsp
upload/upload_list.jsp
upload/upload_topic.jsp
view/old_update_item.jsp
view/update_list.jsp
-->
<!DOCTYPE html>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
 <% 
 	System.out.println(request.getParameter("type"));
    Integer type =Integer.parseInt(request.getParameter("type"));
    ActionContext context = ActionContext.getContext();
    context.put("type", type);
%>
<s:if test="groupId != null">
	<tr id="showSelectedResult" class="">
		<s:if test ="#type == 1" ><td class="rightalign padding10">已选择群组</td></s:if>
		<s:else><td class="rightalign">已选择群组</td></s:else>
		<td class="middleveralign">
			<span id="select" class="blackletter" name="writeGroupNames"><s:property value="writeGroupNames"/></span>	    	
		</td>
	</tr>
</s:if>
<s:else>
	<tr id="showSelectedResult" class="hidden">
		<s:if test ="#type == 1" ><td class="rightalign padding10">已选择群组</td></s:if>
		<s:else><td class="rightalign">已选择群组</td></s:else>
		<td class="middleveralign">
			<span id="select" class="blackletter" name="writeGroupNames"><s:property value="writeGroupNames"/></span>	    	
		</td>
	</tr>
</s:else>
<%-- <tr><s:property value="writeGroupNames"/></tr> --%>
<tr>
	<s:if test ="#type == 1" ><td class="rightalign padding10">权限</td></s:if>
	<s:else><td class="rightalign">权限</td></s:else>
	<td>
		<div id="shareToGroupDiv" class="leftfloat darkgreybg">
			<input type="radio" value="self0" name="toMySelf" id="selectMySelf0" checked="checked" onclick="selectMySelfFn('toMySelf')"/>
		    <label class="greyletter" for="selectMySelf0">群组可见</label>
		    <p id="shareToGroupId" class="lightgreyletter" >&nbsp;<a href="resource/preaddtogroup" rel="facebox" title="归属群组" class="blueletter">选择</a>可以查看和编辑此资源的群组&nbsp;&nbsp;</p>
			<input id="selectedIdList" type="text" class="hidden" name="selectedgroups"  value="<s:property value="selectedgroups"/>"></input>
	    	<input id="selectedvalue" type="text" class="hidden" name="selectedvalue"  value="<s:property value="selectedvalue"/>"></input>
		</div>
		<div id="shareToMyselfDiv" class="leftfloat">
			<input type="radio" name="toMySelf" value="self1" id="selectMySelf1" onclick="selectMySelfFn('toMySelf')"/>
			<label class="greyletter" for="selectMySelf1">仅自己可见</label>
			<p id="shareToMyselfId" class="hidden">&nbsp;此资源将对其他用户不可见&nbsp;&nbsp;</p>
		</div>
		<div class="clear"></div>
		
	</td>
</tr>

<script>
/* $(document).ready(function(){
		
		alert("hah");
	}
	); */
	
</script>
<!-- <tr><td><br/></td></tr> -->