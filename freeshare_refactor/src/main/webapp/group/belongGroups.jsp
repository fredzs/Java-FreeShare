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
						<a class="greyletter" target="_blank"><s:property value="#group.groupInfo.name" /></a>
					</td>
					<td><s:property value="#group.groupInfo.desc"/>
					</td>
					<td>
						<span>
				   	 		<%-- <a class="blueletter leftmargin_5 rightfloat" href="javascript:void(0)"  onclick="deletegroup('<s:property value="#group.uuid" />');" >删除</a> --%>
				   	 		<a href="group/deleteuser2?uid=<s:property value="uid"/>&groupId=<s:property value="#group.groupId"/>" onclick="javascript:return show_confirm()">移除</a>
				   	 	</span>	
					</td>
				</tr>			
			</s:iterator>
		</table>
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