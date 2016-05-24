<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base
	href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户指南--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>

<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp" />
		<div id="inner" class="content">
			<div class="sidebar">
				<span class="strong midsize">群组推荐</span> <br />
				<div class="dottedline"></div>
				<table id="re_group">
				</table>
			</div>
			<div class="main leftmargin_0">
				亲，欢迎你第一次来到free分享。free分享是一款面向群组的资源共享应用。<br/><br/>
				我们支持文档、视频等多种媒体类型资源。<br/><br/>
				你也可以建立列表，更好的组织、管理你的资源。<br/><br/>
				你可以使用我们的即时聊天系统和定向@好友，随时和好友交流。<br/><br/>
				你可以加入群组获取资源，点击右侧为你推荐群组申请加入。<br/><br/>
      			你可以创建群组，邀请你的好友，一起分享资源。 <a href = "group/creategroup.jsp">点此创建你第一个群组</a><br/><br/>
				
			</div>
			<div class="clear"></div>
		</div>
		<!--#inner-->
		<s:include value="/template/_footer.jsp" />
	</div>
	<s:include value="/basic/_globlejs.jsp" />
	<script	src="js/group/regroup.js"></script>
</body>
</html>
