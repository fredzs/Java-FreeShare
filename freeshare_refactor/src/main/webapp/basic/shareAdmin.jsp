<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<base
	href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业管理员管理页面</title>
<link rel="stylesheet" href="http://front.free4lab.com/css/public.css" />
</head>
<body>
<div id="container">

	<div class="banner" style="position: fixed; top: 25px; right: 0px; left: 0px; z-index: 999;">
    	<a href=""><img id="logo" src="images/1-logo.png" border="0"></a>
    	<span class="nav">
        	<a href="group/useradmin"><img src="images/banner_user.png" border="0" class=""></a>
        	<a href="basic/resourceAdmin.jsp"><img src="images/banner_recourse.png" border="0" class=""></a>
        	<a href="basic/shareAdmin.jsp"><img src="images/banner_share.png" border="0" class="cur"></a>
        	<a href="basic/logAdmin.jsp"><img src="images/banner_day.png" border="0" class=""></a>
    	</span>
	</div>
	
	<div id="inner" style="padding-top: 65px;">
		<%-- <s:include value="template/_left.jsp?menu=dashboard" /><!-- change your left url here --> --%>
		<div class="left">
			<dl>
				<dt class="selected">
					<a href="basic/shareAdmin.jsp"><img src="images/left_Address.png" border="0"></a>
				</dt>
				<dt class="">
					<a href="basic/shareAdmin.jsp"><img src="images/left_notice.png" border="0"></a>
				</dt>
			</dl> 
		</div>
		
		<div class="right">
			<!-- edit the main content here -->
			<a id="addmember" href="#" class="button rightfloat">修改通讯录</a>
			<div class="divline">
			企业通讯录
			</div><!--divline-->
			
			<!-- main content ends -->
		</div>
	</div>
	
	<s:include value="template/_footer.jsp"/><!-- change your footer url here -->
</div>
<s:include value="/basic/_globlejs.jsp" />
<script src="js/public.js"></script>
<script>loadJpMenu("groupurl","");</script>
</body>
</html>
