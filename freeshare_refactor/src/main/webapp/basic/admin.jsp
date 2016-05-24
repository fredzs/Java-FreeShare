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

	<div class="banner">
    	<a href=""><img id="logo" src="images/1-logo.png" border="0"></a>
    	<span class="nav">
        	<a href="group/useradmin"><img src="images/banner_user.png" border="0" class="cur"></a>
        	<a href="basic/resourceAdmin.jsp"><img src="images/banner_recourse.png" border="0" class=""></a>
        	<a href="basic/shareAdmin.jsp"><img src="images/banner_share.png" border="0" class=""></a>
        	<a href="basic/logAdmin.jsp"><img src="images/banner_day.png" border="0" class=""></a>
    	</span>
	</div>
	
	<div id="inner">
		<%-- <s:property value="name.length()"/> --%>
		<%-- num:<s:property value="num" /> --%>
		<%-- <s:include value="template/_left.jsp?menu=dashboard" /><!-- change your left url here --> --%>
			<div class="left">
				<dl>
					<dt class="selected">
						<a href="group/useradmin"><img src="images/left_user.png" border="0"></a>
					</dt>
					<dt class="">
						<a href="group/groupadmin?type=2"><img src="images/left_group.png" border="0"></a>
					</dt>
				</dl>
			</div>
			<s:if test="num != 0"> 
			<div class="right">
				<!-- edit the main content here -->
				<a id="addmember" href="group/preAddUser" rel="facebox" size="s" class="button rightfloat">添加新用户</a>
				<a id="addmember" href="basic/addOldUser.jsp" rel="facebox" size="s" class="button rightfloat">添加老用户</a>
				<div class="divline ">
					用户管理<span class="redletter">(<s:property value="company"/>公司)</span>
				</div><!--divline-->
				<div class="contentline">
					<table class="datatable">
			      		 <tr>
                       	 	<td width="100px"> 姓名</td >
                        	<td width="100px"> 邮箱</td > 
                        	<td width="120px">归属部门</td>
                        	<td width="120px">所发资源</td>              
                        	<td width="120px"> 操作</td >
                  
                   		</tr>   
			    	</table>
			   		<s:iterator value="userlist" id="c" status ="st">
			   		<table class="datatable">
	        	           <tr>
								<td width="60px"><s:property value="#c.screen_name"/></td>
								<td width="80px"><s:property value="#c.email"/></td>
								<td width="80px">
									<span>
										<a rel="facebox" href="group/belongs?uid=<s:property value = "#c.uid"/>"  >查看</a>
										<a rel="facebox" href="group/addoneuser2many?uid=<s:property value = "#c.uid"/>"  >添加</a>
									</span>
								</td>
								<td width="80px"><a href="javascript:void(0)" onclick="unfold('<s:property value="#c.email"/>')">查看</a></td>
								<td width="80px">
									<span>
										<a href="group/useradmin">修改权限</a>
				   	 					<a href="group/deleteuser?uid=<s:property value = "#c.uid"/>" onclick="javascript:return show_confirm()">删除用户</a>
				   	 				</span>	
								</td>
	        				</tr>
	        				<tr id="<s:property value="#c.email"/>" class="hidden">
	        				<td>我终于出现了</td>
	        				</tr>
	        			</table>
	        			</s:iterator>
			
					</div><!--contentline-->
			 	
					<!-- main content ends -->
				</div>
			</s:if>
			<s:else>
				<div class="right">
					<div class='blueletter bigsize  topmargin_20 leftmargin_60 padding715'>对不起，您暂时不是任何企业的管理员</div>
					<div class="leftmargin_60 padding715"><a class="button " href="group/create_enterprise.jsp" target="_blank">新建企业</a></div>
				</div>
			</s:else> 
	</div>
	
	
	<s:include value="template/_footer.jsp"/><!-- change your footer url here -->
</div>
<s:include value="/basic/_globlejs.jsp" />
<%-- <script src="js/public.js"></script> --%>
<script>
	loadJpMenu("groupurl","");
	function show_confirm()
	 {
	 	if (confirm("您确认删除？"))
	 	{
	   		alert("You pressed OK!，已经删除");
	   		
	   	}
	 	else
	  	{
	 		//alert("You pressed Cancel!，不删除");
	  		 return false;
	 		 
	   	}
	 }	
	function unfold(param){
/* 		$('#param').html("<td width=\"60px\">出现吧！<td>"); */
 		$('#param').removeClass("hidden");
		
	}
</script>
</body>
</html>
