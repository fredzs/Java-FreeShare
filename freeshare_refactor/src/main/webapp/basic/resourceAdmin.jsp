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
        	<a href="basic/resourceAdmin.jsp"><img src="images/banner_recourse.png" border="0" class="cur"></a>
        	<a href="basic/shareAdmin.jsp"><img src="images/banner_share.png" border="0" class=""></a>
        	<a href="basic/logAdmin.jsp"><img src="images/banner_day.png" border="0" class=""></a>
    	</span>
	</div>
	
	<div id="inner" style="padding-top: 65px;">
		<%-- <s:include value="template/_left.jsp?menu=dashboard" /><!-- change your left url here --> --%>
		<div class="left">
			<!-- <dl>
				<dt class="selected">
					<a href="group/useradmin"><img src="images/left_user.png" border="0"></a>
				</dt>
				<dt class="">
					<a href="group/groupadmin?type=2"><img src="images/left_group.png" border="0"></a>
				</dt>
			</dl> -->
		</div>
		
		<div class="right">
			<!-- edit the main content here -->
			<a id="addmember" href="#" class="button rightfloat">发布新资源</a>
			<div class="divline">
			资源管理
			</div><!--divline-->
			<div class="contentline">
				<table class="datatable">
			       <tr>
                        <td width="100px"> 资源名字</td >
                        <td width="100px"> 发布者</td > 
                        <td width="120px">发布时间</td>
                        <td width="120px">最近修改时间</td>              
                        <td width="120px"> 操作</td >
                  
                   </tr>   
			    </table>
			   <s:iterator value="userlist" id="c" status ="st">
			   		<table class="datatable">
	        	           <tr>
								<td width="60px"><s:property value="#c.realName"/></td>
								<td width="80px"><s:property value="#c.email"/></td>
								<td width="80px"><a rel="facebox" href="group/belongs?uid=<s:property value = "#c.uid"/>"  >查看</a></td>
								<td width="80px"><a href="#">查看</a></td>
								<td width="80px">
									<span>
										<a href="#">修改</a>
				   	 					<a href="#">删除用户</a>
				   	 				</span>	
							</td>
	        				</tr>
	        			</table>
	        	</s:iterator>
			
			</div><!--contentline-->
			 	
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
