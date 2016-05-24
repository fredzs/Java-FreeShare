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
<div class="contentline">
				<table class="datatable">
			       <tr>
                        <td width="100px"> 姓名</td >
                        <td width="100px"> 邮箱</td > 
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
										<a href="#">修改权限</a>
										<a href="group/deleteuser2?uid=<s:property value="#c.uid"/>&groupId=<s:property value="groupId"/>" onclick="javascript:return show_confirm()">移除</a>
				   	 				</span>	
							</td>
	        				</tr>
	        			</table>
	        	</s:iterator>
			
			</div><!--contentline-->

</body>

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
</script>