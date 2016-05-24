<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base
	href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>权限不够--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>
<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp"></s:include>
		<div id="inner" class="content">
			<br /> <br /> <br />
<!-- 			<h1> -->
<!-- 				<img src="images/notification_warning.png" -->
<!-- 					onerror="javascript:this.src='images/notification_warning.png'" -->
<!-- 					width="50px" height="50px" border="0" />  -->
<!-- 				&nbsp&nbsp亲，你权限不够哦！ -->
<!-- 			</h1> -->
			<div class="listtitle" bg="images/notification_warning.png">
				<h1>&nbsp亲，你权限不够哦！</h1>
			</div>
			<br />
			<div>
<%-- 				<span class="strong midsize">该资源归属群组</span> <br /> --%>
				<span class="strong midsize">该资源归属如下群组，您需要加入群组才可以访问此资源。</span> 
				<br /><br />
				<div id="bl_groups"></div>
			</div> 
		</div>
		<!--#inner-->
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/right_column/belonggroups2.js"></script>
	<script>
		$(document).ready(function() {
			if ('<s:property value = "idlist"/>' == "") {
				getblgroups('<s:property value = "iditem"/>','');
			} else {
				getblgroups('<s:property value = "idlist"/>','-1');
			}
		})
	</script>
</body>
</html>