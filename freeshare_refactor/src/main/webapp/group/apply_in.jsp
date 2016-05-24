<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>申请加入群组--Free分享</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div>
		<br />
		<form id="applyForm" action="members/apply" method="post"
			enctype="multipart/form-data">
			<table class="formtable">
				<tr><td>
					申请理由 ：
					<br/><span class="greyletter">不多于50个字</span>
					</td>
					<td>
					<textarea id="desc" cols="30" rows="5" class="editbox"
										name="applyReason"></textarea> 
					<br/><span id="text" class="redletter" ></span>
					
					
					</td>
			</tr>
			<tr>
				<td></td>
				<td >
					<a class = "button" id="apply" onclick="javascript:submitApply()" >提交</a>
				</td>
				
			</tr>
			<tr><td><input id="groupId" name="groupId" value="<s:property value='groupId'/>" class="hidden" />
			</td><td>
			<input id="groupName" name="groupName" value="<s:property value='groupName'/>" class="hidden" ></input>
			</td></tr>
			</table>
			
		</form>
	</div>
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/group_administration/administration.js"></script>
	<script src="js/group_administration/email.js"></script>
	
	
</body>
</html>