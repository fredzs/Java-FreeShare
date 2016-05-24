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
        	<a href="group/useradmin"><img src="images/banner_user.png" border="0" class="cur"></a>
        	<a href="basic/resourceAdmin.jsp"><img src="images/banner_recourse.png" border="0" class=""></a>
        	<a href="basic/shareAdmin.jsp"><img src="images/banner_share.png" border="0" class=""></a>
        	<a href="basic/logAdmin.jsp"><img src="images/banner_day.png" border="0" class=""></a>
    	</span>
	</div>
	
	<div id="inner" style="padding-top: 65px;">
		<%-- <s:include value="template/_left.jsp?menu=dashboard" /><!-- change your left url here --> --%>
		<div class="left">
			<dl>
				<dt id="leftuser">
					<a href="group/useradmin"><img src="images/left_user.png" border="0"></a>
				</dt>
				<dt id="leftgroup" class="selected" >
					<a href="group/groupadmin?type=2"><img src="images/left_group.png" border="0"></a>
				</dt>
			</dl>
		</div>
		
		<div class="right">
			<!-- edit the main content here -->
			<a id="addmember" href="group/createdepartment.jsp" rel="facebox" class="button rightfloat">新建部门</a>
			<div class="divline">
			部门管理<span class="redletter">(<s:property value="company"/>公司)</span>
			</div><!--divline-->
			<div class="contentline">
				<table class="datatable">
					<tr bgcolor=" #f2f2f2" >
                        <td> 部门</td >
                        <td></td>
                        <td>描述</td>
                        <td> 操作</td >
                   	</tr> 
                   	<s:iterator value="groupList" id="group" status="st">
						<tr>
							<td class="formlabel" style="width: 50px; padding: 0px 5px;"><a target="_blank" href ="group/resource?groupId=<s:property value="#group.groupId"/>"><img src="http://freedisk.free4lab.com/download?uuid=<s:property value="#group.groupInfo.avatar" />" onerror="javascript:this.src='images/group_head.png'"
											width="40px" height="42px" border="0" ></a>
							</td>
							<td>
								<a class="greyletter" rel="facebox"  href ="group/departmentmembers?groupId=<s:property value="#group.groupId"/>"><s:property value="#group.groupInfo.name" /></a>
							</td>
							<td>
								<s:property value="#group.groupInfo.desc"/>
							</td>
							<td>
								<span>
								
									<a rel="facebox" title="修改群组信息"  href="group/preupdateinfo2?groupId=${groupId}&name=${name}&desc=${groupInfo}">修改信息</a>
				   	 				<%-- <a target="_blank" rel="facebox" href="members/invitemembers?groupId=<s:property value="#group.groupId"/>">添加成员</a> --%>
				   	 				<a target="_blank" rel="facebox" href="group/adduser?groupId=${groupId}">添加成员</a>
				   	 				<%-- <a class="blueletter leftmargin_5 rightfloat" href="javascript:void(0)"  onclick="deletegroup('<s:property value="#group.uuid" />');" >删除</a> --%>
				   	 				<a href="group/deletegroup2?uuid=<s:property value="#group.uuid" />" onclick="javascript:return show_confirm()">删除</a>
				   	 			</span>	
							</td>
						</tr>			
					</s:iterator>
				</table>
			</div><!--contentline-->
			<!-- main content ends -->
		</div>
	</div>
	
	<s:include value="template/_footer.jsp"/><!-- change your footer url here -->
</div>
<s:include value="/basic/_globlejs.jsp" />
<script>loadJpMenu("groupurl","");</script>
<%-- <script>
	function a(param1,param2)
	{
		//alert(param1);
		$("#"+param1).addClass("selected");
		$("#"+param2).removeClass("selected");

	}
</script>  --%>

<script>
 function deletegroup(uuid){
			//alert("delete function");
			//alert("uuid:"+uuid);
			if(confirm("您确实要删除这个群组？")){
				$.ajax({
					url : 'group/deletegroup',
					type : 'post',
					data : {
						uuid : uuid
					},
					success : function() {
						//alert("删除群组成功！");
						var query = "tipstatus=0&content="+encodeURIComponent("删除群组成功！");
						if(location.href.indexOf("?") != -1){
							var newUrl = location.href+"&"+query;
						}else{
							var newUrl = location.href+"?"+query;
						}
						location.replace(newUrl);
					}
				});
			}
		} 
 
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
</script>

</body>
</html>
