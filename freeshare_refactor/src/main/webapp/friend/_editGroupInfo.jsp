<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>编辑好友信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<table class="formtable">
		<tr>
	     	<td>群组名称<span class="redletter">*</span></td>
	     	<td><input type="text" id="editGroupName" class="editline"/></td>
	    </tr><tr>
	     	<td></td><td><span class="redletter" id="groupNameTip"></span></td>
	    </tr>
	</table>

	<div class="rightalign">
	    <input type="button" class="button" value="确定" onclick="editGroupInfoFn()"/>
	    <a href="javascript:void(0)" onclick="cancelFaceboxFn()" >取消</a>
   	</div>
	<script>
		$(document).ready(function(){
			//获得要编辑的群组
			var editGroupId=readFromMark();
			var groupInfo = getGroupInfo(editGroupId);
			$("#editGroupName").val(groupInfo.group_name);
		})		
	</script>
</body>
</html>
