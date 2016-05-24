<!--
使用该文件的地方：
basic/news.jsp
-->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base
	href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>快捷联系</title>
</head>
<body>
<div>
	<div class="leftfloat leftmargin_10 ">
	   <img id="friend_avatar"
			 onerror="javascript:this.src='images/user_male.png'" width="60" height="60" border="0"/>
	
	</div>
	<table cellpadding="3px" style="width:240px">
		<tr>
			<td  class="leftalign greyletter strong leftmargin_10"><span id="friend_name" class=""></span></td>
			<td><span></span></td>
		</tr>
		<tr>
			<td class="leftalign greyletter strong leftmargin_10"><span>个人简介：</span></td>
			<td><span  id="current_level" class=""></span></td>
		</tr>
	</table>
	
	<div class="clear"></div>
	
	<div id="onfriend" class="hidden" style="margin-top:30px margin-left:5px" class="centeralign">
	<a id="on_qq"><img class="leftfloat leftmargin_10" width="35px" height="35px" src="<%=com.free4lab.freeshare.URLConst.APIPrefix_FreeMessage%>images/friendlist/qq.png"/></a>
	<a id="on_email"><img class="leftfloat leftmargin_10" width="35px" height="35px" src="<%=com.free4lab.freeshare.URLConst.APIPrefix_FreeMessage%>images/friendlist/email.png"/></a>
	<a id="on_history" target="_blank"><img  class="leftfloat leftmargin_10" width="35px" height="35px" src="<%=com.free4lab.freeshare.URLConst.APIPrefix_FreeMessage%>images/friendlist/history.png"/></a>
	<img id="on_webrtc" class="leftfloat leftmargin_10" width="35px" height="35px" src="<%=com.free4lab.freeshare.URLConst.APIPrefix_FreeMessage%>images/friendlist/webrtc.png"/>
	</div>
	
	<div id="isfriend" class="hidden" style="margin-top:30px margin-left:5px" class="centeralign">
	<a id="is_qq"><img class="leftfloat leftmargin_10" width="35px" height="35px" src="<%=com.free4lab.freeshare.URLConst.APIPrefix_FreeMessage%>images/friendlist/qq.png"/></a>
	<a id="is_email"><img class="leftfloat leftmargin_10" width="35px" height="35px" src="<%=com.free4lab.freeshare.URLConst.APIPrefix_FreeMessage%>images/friendlist/email.png"/></a>
    <a id="is_history" target="_blank"><img class="leftfloat leftmargin_10" width="35px" height="35px" src="<%=com.free4lab.freeshare.URLConst.APIPrefix_FreeMessage%>images/friendlist/history.png"/></a> 
	</div>

    <div id="notfriend"  style="margin-top:30px margin-left:5px" class="centeralign">
	<a id="not_qq"><img class="leftfloat leftmargin_10" width="35px" height="35px" src="<%=com.free4lab.freeshare.URLConst.APIPrefix_FreeMessage%>images/friendlist/qq.png"/></a>
	<a id="not_email"><img class="leftfloat leftmargin_10" width="35px" height="35px" src="<%=com.free4lab.freeshare.URLConst.APIPrefix_FreeMessage%>images/friendlist/email.png"/></a>
	</div> 
	
	
	<div class="clear"></div>
	
	<div class="rightalign" >
		<input id="fri_button" type="button" value="加为好友" onclick="addFriend()"/>
	</div>
	<div class="clear"></div>
</div>
</body>
<script type="text/javascript">
var FREEMESSAGE_URL = "http://webrtcmessage.free4lab.com/";
//var FREEMESSAGE_URL = "http://localhost:9090/freemessage/";
var quick_friendId = $('#quickcommunicatehref').val();
var quick_groupId;
$(document).ready( function() {
 	$.getJSON(FREEMESSAGE_URL +"friend/getfriendinfo?callback=?",{'friendId':quick_friendId},function(result){
 		if(result){
			var userInfo = result.userInfo[0];
			var imgUrl = com.freemessage.FREEMESSAGE_AVATAR_URL+userInfo.avatar;
			var userName = userInfo.userName;
			var isFriend = result.isFriend;
		    quick_groupId = result.groupId;
			
			$("#friend_avatar").attr('src',imgUrl);
			$("#friend_name").append(userName);
			
			$("#not_email").attr('href',"mailto:"+ userInfo.email+"");
			$("#not_qq").attr('href',"tencent://message/?uin="+userInfo.qq+"");
			
			if(isFriend == "true"){
				if($("#realfriend").length != 0){
					$("#onfriend").removeClass('hidden');
					
					$("#on_email").attr('href',"mailto:"+ userInfo.email+"");
					$("#on_qq").attr('href',"tencent://message/?uin="+userInfo.qq+"");
					$("#on_history").attr('href',""+com.freemessage.FREEMESSAGE_URL+"history/pairshistory?friendId="+quick_friendId+"");
					$("#on_webrtc").attr('onclick',"startFriendDialog('"+quick_friendId+"','"+imgUrl+"','"+userName+"')");
				}
				else{
					$("#isfriend").removeClass('hidden');	
					$("#is_email").attr('href',"mailto:"+ userInfo.email+"");
					$("#is_qq").attr('href',"tencent://message/?uin="+userInfo.qq+"");
				    $("#is_history").attr('href',""+com.freemessage.FREEMESSAGE_URL+"history/pairshistory?friendId="+quick_friendId+"");
				 }
				$("#notfriend").addClass('hidden');
				
				$("#fri_button").attr('value',"删除好友");
				$("#fri_button").attr('onclick',"delFriend()");
			}
		}else{
			alert("没有找到该用户的个人信息");
		}
	}); 
}) 
</script>
</html>