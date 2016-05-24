<!--
使用该文件的地方：
basic/browse.jsp
-->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<s:if test="info.type != 4">
		<div>
			<s:property value="info.description" escape="false" />
		</div>
	</s:if>
	<s:else>
		<p />
	</s:else>
	
	<!-- end s:if test="info.type != 4" -->
	
	<s:if test="info.type == 2 ">
		<div class="midsize">
			<span>链接地址:</span> 
			<a
				href="<s:property value="info.content" escape="false" />"
				target="_blank"> <s:property value="info.content" escape="false" />
			</a>
		</div>
		<br />
	</s:if>
	
	<!-- end s:if test="info.type == 2" -->

	<s:if test="info.type == 4">
		<s:property value="info.description" escape="false" />
	</s:if>
	
	<!-- end s:if test="info.type == 4"  -->
				
	<s:if test="info.type == 3">
		<div>
			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" height="498" width="695">
				<param name="movie" value="<s:property value="videoUrl"/>" />
				<param name="quality" value="high" />
				<param name="bgcolor" value="#FFFFFF" />
				<param name="auto" value="true">
				<param name="allowNetworking" value="all" />
				<param name="allowScriptAccess" value="sameDomain" />
				<param name="allowFullScreen" value="true" />
				<param name="wmode" value="transparent" />
				<embed type="application/x-shockwave-flash" height="498" width="695"
					src="<s:property value="videoUrl"/>" allowfullscreen="true"
					wmode="transparent" allownetworking="all" allowscriptaccess="sameDomain"></embed>
			</object>
		</div>

		<div>
			<span>来自: 
				<a class="greyletter"
					href="<s:property value="info.content" escape="false" />"
					target="_blank"> <s:property value="info.content" escape="false" />
				</a>
			</span>
		</div>
	</s:if>
				
	<!-- end s:if test="info.type == 3" -->
	
	<s:if test="info.type == 1">
		<br/>
		<s:include value="/basic/_version.jsp" />
	</s:if>