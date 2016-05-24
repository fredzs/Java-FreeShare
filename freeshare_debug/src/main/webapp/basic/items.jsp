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
<title>我创建的--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>

<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp?index=mine"></s:include>
		<div id="inner" class="content">
			<a class="graybutton middlebutton topmargin_10" id="selfdefine" href="javascript:void(0)">根据资源类型过滤▼</a>
			<s:include value="/basic/_resourceTypeFilter.jsp" />
			<div id="myresources"></div>
		</div>
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/basic/filter.js"></script>
	<script>
		var param = {
				clickId : 'selfdefine',
				toggleId : 'selfdefinefilter',
				selectList : 'all',
				name : 'resourcefilter',
				cookieId : <s:property value = "#session.userId"/>+'resourceFilterMine',
				cookieName : <s:property value = "#session.userId"/>+'resourceNameMine',
				selectAllId : 'allresources',
				paramName : 'resourceTypeList',
				submitId : 'selfdefinenews',
				cancelId : 'selfdefinecancel',
				rememId : 'rememberresource',
				type : '资源',
				filterIntroId : 'resourcefilterintro',
				visionType : 'list',
				resourceContainer : 'myresources',
				page : 1,
				divPage : true,
				url : 'ajaxmine'
		};
		$('#groupfilterintro').html('');
		resourceFilterObj.initialize(param);
		$('#'+param.clickId).click();
		$('#'+param.submitId).click();
	</script>
</body>
</html>