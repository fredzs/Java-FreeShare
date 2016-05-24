<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>自邮之翼--Free分享</title>
<s:include value="/template/_head.jsp" />  
</head>
<body>
	<div id="container">
	    <s:include value="/template/_pub_banner.jsp"/>
		<s:include value="/template/_banner.jsp"></s:include>
		<div id="inner" class="content">
			<div class="sidebar">
				<span class="strong midsize">群组推荐</span> <br />
				<div class="dottedline"></div>
				<table id="re_group">
				</table>
			</div>
	      	<h1>该群组已经不存在！</h1>
	      	<p>返回主页面，请点击<a class="greyletter"  href="">这里</a></p>
	      	<br />
	      	<br />
	      	<br />
		</div>
		<!--#inner-->
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
</body>
</html>
