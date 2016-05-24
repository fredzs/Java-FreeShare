<!--
使用该文件的地方：
basic/browse.jsp
-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<span class="strong rightmargin_10">
	<s:if test = "info.authorId != #session.userId">
		<a class="greyletter" href="javascript:void(0)"
			onclick="quickCommunicateFn('<s:property value="authorId" />')">
			<s:property value="map[info.authorId].userName" />
		</a>
	</s:if>
	<s:else>
		<s:property value="map[info.authorId].userName" />
	</s:else>
</span> 
<span class="rightmargin_20"> 
		<s:date name="info.publicTime" format="yyyy-MM-dd HH:mm:SS" /></span>
	<s:if test="avgNum<9">
		<span class="emstar"></span>
		<span class="emstar"></span>
		<span class="emstar"></span>
		<span class="emstar"></span>
		<span class="emstar"></span>
	</s:if>
	<s:elseif test="avgNum<19">
		<span class="fulstar"></span>
		<span class="emstar"></span>
		<span class="emstar"></span>
		<span class="emstar"></span>
		<span class="emstar"></span>
	</s:elseif>
	<s:elseif test="avgNum<49">
		<span class="fulstar"></span>
		<span class="fulstar"></span>
		<span class="emstar"></span>
		<span class="emstar"></span>
		<span class="emstar"></span>
	</s:elseif>
	<s:elseif test="avgNum<59">
		<span class="fulstar"></span>
		<span class="fulstar"></span>
		<span class="fulstar"></span>
		<span class="emstar"></span>
		<span class="emstar"></span>
	</s:elseif>
	<s:elseif test="avgNum<89">
		<span class="fulstar"></span>
		<span class="fulstar"></span>
		<span class="fulstar"></span>
		<span class="fulstar"></span>
		<span class="emstar"></span>
	</s:elseif>
	<s:else>
		<span class="fulstar"></span>
		<span class="fulstar"></span>
		<span class="fulstar"></span>
		<span class="fulstar"></span>
		<span class="fulstar"></span>
	</s:else>
	<span class="leftmargin_20 strong">浏览 &nbsp;&nbsp; 
		<s:property value="lookNum" /></span>
	<span class="evaluate">
		<a href="javascript:supportresource(<s:property value="id" />,'<s:property value="resourceType" />','up')" 
	  		id="up_num" class="upbutton leftbutton" title="顶">
		<s:property value="upNum" /></a>
		
		<a href="javascript:supportresource(<s:property value="id" />,'<s:property value="resourceType" />','down')" 
	   		id="down_num" class="dobutton rightbutton" title="踩">
		<s:property value="downNum" /></a>
</span>