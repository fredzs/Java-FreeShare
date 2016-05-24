<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源已删除--Free分享</title>
<s:include value="/template/_head.jsp" />  
</head>

<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp" />
  		<s:include value="/template/_banner.jsp"></s:include>
  		<div id="inner" class="content">
     		<br />
     		<br />
     		<br />
       		<h1>此资源不存在或已经被删除了！ </h1>
         	<p>返回应用，请点击 <a class="greyletter"  id="url" href="">这里</a> </p>
         	<br />
         	<br />
         	<br />
  		</div>
    	<s:include value="/template/_footer.jsp" />
  		<!--#inner-->
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
</body>
</html>