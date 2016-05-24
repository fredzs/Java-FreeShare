<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>邀请成员--Free分享</title>
<s:include value="/template/_head.jsp" />  
<link href="textextplugin/textext.core.css" rel="stylesheet"/>
<!--  link href="textextplugin/textext.plugin.arrow.css" rel="stylesheet" type="text/css"/-->
<link href="textextplugin/textext.plugin.focus.css" rel="stylesheet"/>
<link href="textextplugin/textext.plugin.autocomplete.css" rel="stylesheet"/>
<link href="textextplugin/textext.plugin.prompt.css" rel="stylesheet"/>
<link href="textextplugin/textext.plugin.tags.css" rel="stylesheet"/>
<link href="css/invite.css" rel="stylesheet"/>

</head>

<body>
 <div id="container">
  <s:include value="/template/_pub_banner.jsp"/>
  <s:include value="/template/_banner.jsp?index=group"></s:include>
	  <div id="inner" class="content">
	        <div>
	   		<h1> 邀请成员加入“<s:property value="groupName"/>”组
	   		<s:if test='groupPermission =="public"'><span id="publicIn"><a class="button rightfloat" onclick="obtainShareAddress();">自由访问群组地址</a></span></s:if></h1>
			<span id="publicInAddress"></span>
			</div>
			<div class="tabcontainer">
					<ul>
						<li>好友</li><li onclick="futureMembers.displayAllGroupsMem();">其他群组</li><li>Email</li>
					</ul>
					<div class="tabdiv">
						 <div style="height:280px;overflow: auto;">
							 <table>
							     <tr>
							        <td width="180px"> <input type="text" id="searchFriend" value="搜索好友"  name="searchText"   onkeyup="searchMyFn(this.value,'friend');" onclick="clearSearchMyFn();"  />
							         </td>
						             <td id="friendAll"><input type="checkbox" id ="friendMember" onclick='futureMembers.selectAll(this);'>全选
						             </td>
				                  </tr>
				                  <tr><td><div id="groupList"></div></td>
				                  <td><table id="myfriends" ></table>
				                  <table id="friendSearchTable" ></table>
				                  </td></tr>
				              </table>
			              </div>
			             
					</div>
					          
				   <div class="tabdiv" >
					     <div id = "memberdiv" style="height:280px; overflow: auto;" >
					      		<table>
								     <tr>
								        <td width="180px" class="topveralign"> <input type="text" id="searchText" value="搜索群组成员"  name="searchText"   onkeyup="searchMyFn(this.value,'member');" onclick="clearSearchMyFn();"  />
								         <table id="groupTable" ></table>
								         
								         </td>
							             <td  class="topveralign"><span id="groupAll"><input type="checkbox" id ="groupMember" onclick="futureMembers.selectAll(this);">全选</span>
							              <table id="selectMyTable" ></table>
								          <table id="searchTable" ></table>
							             
							             </td>
							        
					              </table>
							 
						 </div>
					</div><!-- end  class="tabdiv" -->
					
					<div class="tabdiv">
						<div style="height:280px;">
							Email:<br/>
							<textarea name="emailSend" id="emailSend" class="blue1border" style="width:850px;height:100px;" onkeyup="convertToDBC(this);"></textarea>
							<br/>
							<span  class="greyletter">填写多个邮箱请用，隔开</span>
							
							<a class="button"  href="javascript: addUserEmail();">添加到被邀请人</a>
						</div>
					</div>
			</div><!-- end class="tabcontainer"-->
		     		 
		     
		    <br/>
		    <div id="emailtable">
		   		<table >
						<tr>
							<td width="60px" class="topveralign"> 被邀请人</td>
							<td>
		   		 
		   		 			 <textarea id="emails" name="emails" class="blue1border" onmousedown="set();" onmouseup="set();" onmouseover="set();" onkeydown="set();"></textarea>
					   		  </td></tr>
					   	<!-- tr><td></td><td><span  class="greyletter">填写多个邮箱请用，隔开</span></td></tr> -->
					   	<tr><td></td><td><span id="emailtext" class="redletter"></span></td></tr>
		   		  </table>
		   		    <br/>
		   		    <a href="javascript:show();" class="blueletter">如果想要修改邀请信的内容，请点击此处。</a>
					<div id = "invite2" ><a id="commonInvite" class="button" href="javascript: submitInvitation()">发出邀请</a></div>
					
					<div id = "invite" style="display: none;">
						<form id="inviteForm" action="members/sendinvite" method="post" enctype="multipart/form-data">
							<table >
								<tr>
									<td width="120px" class="topveralign blueletter">主题</td><td><input type="text" id="title" name="title"  style="width:895px;" class="blue1border"  /></td>
								</tr>
								<tr>
									<td ></td></tr>
								
								<tr>
									<td width="120px" class="topveralign blueletter">内容</td>
									<td><div id="updateContent" ><textarea id="reason" name="reason" style="width:895px;height:120px;" class="blue1border"  ></textarea></div>
										
										
									</td>
								</tr>
								
								
								<tr>
									<td></td>
									<td>
										<a id="invitation" class="button" href="javascript: submitInvitation()">发出邀请</a>
									</td>
								</tr>
							</table>
						
							 <input type ="hidden" value="" id="selectedList" class="fullinput" name="selectedList"  />
							 <input type ="hidden" value="" id="selecteIds" class="fullinput" name="selectIds"  />
							 <input type ="hidden" id ="groupId" name="groupId" value ="<s:property value ="groupId"/>"/>
				   		  	 <input type ="hidden" id ="groupName" name="groupName" value ="<s:property value ="groupName"/>"/>
				   		  	 <input type ="hidden" id ="myName" name="myName" value ="<s:property value ="myName"/>"/>
						</form>
					</div><!--  end  id = "invite"--> 
				 </div><!-- end id="emailtable" -->
		    	</div><!-- end id="inner" -->
		   

  <s:include value="/template/_footer.jsp" />  
</div><!--#container-->
<s:include value="/basic/_globlejs.jsp" />
<script src="js/group_administration/administration.js"></script>
<script src="js/group_administration/email.js"></script>
<script src="js/group/group.js"></script>
<script src="js/group_administration/invite.js"></script>

<script src="textextplugin/textext.core.js"></script>
<script src="textextplugin/textext.plugin.ajax.js"></script>
<!--script type="text/javascript" src="textextplugin/textext.plugin.arrow.js"></script-->
<script src="textextplugin/textext.plugin.focus.js"></script>
<script src="textextplugin/textext.plugin.autocomplete.js"></script>
<script src="textextplugin/textext.plugin.prompt.js"></script>
<script src="textextplugin/textext.plugin.tags.js"></script>
<script>

$('#emails').textext({
	//alert("text2");
    plugins : 'tags prompt focus autocomplete ajax',
    //tagsItems : ['ok'],
    prompt : '填写被邀请者...',
    //alert("jinru");
 	ajax : {
        url : '/freeshare/textextplugin/data.json',
        dataType : 'json',
        cacheResults : false
    } 
}); 

$(document).ready(function(){
	futureMembers.displayFriends();
	setInviteLetter();
});

</script>
</body>
</html>
