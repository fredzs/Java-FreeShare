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
	     	<td>好友账号</td>
	     	<td id="editFriendId"></td>
	    </tr><tr>
	     	<td>选择群组<span class="redletter"></span></td>
	     	<td><select id="groupListId" class="selectboxsmall">
	     	</select>
	     	<a class="leftmargin_5 blueletter" href="javascript:void(0)" onclick="addGroupFn()">新建群组</a>
	     	</td>
	    </tr><tr>
		     	<td></td><td>
		     		<div id="addGroupDiv" class="hidden">
               			<span class="padding5">
	               			<span class="blackletter strong">
	               				请输入群组名称&nbsp;&nbsp;<span class="redletter" id="groupNameTip"></span>
	               			</span>
	               			<br/>
	               			<input type="text" name="groupName" id="groupName" class="editline"/>
	               			<a class="greybutton" href="javascript:void(0)" onclick="checkBeforeAddGroupFn()">确定</a>
	               			<a class="blueletter " href="javascript:void(0)" onclick="cancelAddGroupFn()">取消</a>
               			</span>
               		</div>
               		<br/>
		     	</td>
		    </tr>
	</table>

	<div class="rightalign">
	    <input type="button" class="button" value="确定" onclick="editFriendInfo()"/>
	    <a href="javascript:void(0)" onclick="cancelFaceboxFn()" >取消</a>
   	</div>
	<script>
		//页面加载时初始化一些数据
		$(document).ready(function(){			
			com.freemessage.getGroupList('edit');
		})		
	</script>   	
</body>
</html>
