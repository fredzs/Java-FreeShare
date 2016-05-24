<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>添加好友</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
		<table class="">
			<tr>
		     	<td>好友账号<span class="redletter">*</span></td>
		     	<td><input id="friendInputId" type="text" value="" class="editline" placeholder="好友注册webrtc的email地址" onkeydown="enterToSend(event)"/>
		    	<input id="searchFriend" type="button" class="sgraybutton" onclick="searchFriendFn('friendInputId')" value="搜索"></td>
		    </tr>
		    <tr>
		     	<td>选择群组<span class="redletter"></span></td>
		     	<td><select id="groupListId" class="selectboxsmall">
		     		
		     	</select>		     		
		     	<a class="leftmargin_5 blueletter" href="javascript:void(0)" onclick="addGroupFn()">新建群组</a></td>
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
		    <tr><td colspan="2">
		    	<div id="noSearchResult" class="lheight grey1border greybg">
		    		<div id="noSearchResultTip" class="centeralign middleveralign lightgreyletter ">请输入好友账号并点击搜索</div>
		    	</div>
		    	<div id="showSearchResult" class="hidden">
		    		<table class="datatable" id="searchResultTable">		    			
		    		</table>
		    	</div>
		    </td></tr>
		</table>
	
		<div>
			<div class="leftfloat">
				<span id="addFriendTip" class="redletter"></span>
			</div>			
			<div class="rightfloat">
				 <input type="text" id="friendId"/>
				 <input type="button" class="button" value="申请添加" onclick="checkBeforeAddFriendFn()"/>
		   	 	 <a href="javascript:void(0)" onclick="cancelFaceboxFn()" >取消</a>
			</div>
			<div class="clear"></div>		   
	   	</div>
	   	<script>
	   		$(document).ready(function(){
	   			com.freemessage.getGroupList();	   			
	   		});
	   		function enterToSend(event){
	   			var currKey = 0,e = event || window.event; //为了兼容，ie用event，而标准用e
	   			currKey = e.keyCode||e.which||e.charCode;

	   			if(currKey == 13 && !e.shiftKey){ //13为enter键，同时要求没有按下shift键
	   			 	e.returnValue=false;
	   			 	$("#searchFriend").click();
	   			 	return false;
	   			}
	   		}
	   	</script>
</body>
</html>
