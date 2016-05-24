<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<base href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:property value="info.name" /> - <s:property
		value="authorName" />--Free分享</title>
<s:include value="/template/_head.jsp" />

</head>
<body>
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/basic/addtags.js"></script>
	<script>
	$(document).ready( function() {
		addtags(0, '', '');
	})
	</script>
</body>
</html>