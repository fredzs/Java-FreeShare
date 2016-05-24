<!--
使用该文件的地方：
group/group_lists.jsp
group/group_members.jsp
group/group_resource.jsp
-->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="topmargin_20">
    <div class="leftfloat">
    	<img src="<s:property value="groupAvatar"/>"
		onerror="javascript:this.src='images/grouphead.png'"
		width="100" height="100" border="0"  />
	</div>
	<div class="leftmargin_20 bottommargin_5 bottomveralign leftfloat">
		<!-- br/> -->
		<span class="blueletter">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="backToGroup();">群组页</a>》<s:property value="name"/></span>
		<h1>&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="name"/></h1>
		<span id="groupInfo">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="groupInfo"/></span>
	</div>
	<s:if test="type == 2||type == 1">
		<a class="button topmargin_20 rightfloat" href="group/getgroup?groupId=<s:property value= "groupId"/>&kind=0">管理群组</a>
	</s:if>
	<div class="clear"></div>
</div>


<div class="bottommargin_5">
 	<a id="group_resource" href="group/resource?groupId=<s:property value= "groupId"/>" class='graybutton leftbutton ${ param.menu=="group_resource"?"selected":"" }' >资源过滤▼</a>
 	<%-- <a id="group_lists" href="group/lists?groupId=<s:property value= "groupId"/>" class='graybutton middlebutton ${ param.menu=="group_lists"?"selected":"" }' >本组列表</a> --%>
 	<a id="group_members" href="group/members?groupId=<s:property value= "groupId"/>" class='graybutton rightbutton ${ param.menu=="group_members"?"selected":"" }' >本组成员</a>
	<s:if test="type == 4"><!-- 非组成员  -->
		<a class="button rightfloat" href="javascript:groupManager.agreeOrIngore('<s:property value="#session.email"/>','<s:property value ="groupId"/>','agree')">同意加入</a>
	</s:if>
	<s:elseif test="type == 0">
		<a id="apply" class="button rightfloat" href="members/preapply?groupId=<s:property value ='groupId'/>" 
		  title ="申请加入" rel="facebox" size="s">申请加入</a>
	</s:elseif>
	<s:elseif test="type == 3"><!-- 普通成员 -->
		<a class="graybutton rightfloat" 
		   href="javascript:quit(<s:property value="#session.userId"/>,<s:property value ="groupId"/>)">退出群组</a>
	</s:elseif>
	<s:elseif test="type == 1"><!-- 管理员 -->
		
		<a class="graybutton rightfloat" onclick="return confirm('确认退出?');"
 			 href="javascript:quit(<s:property value="#session.userId"/>,<s:property value ="groupId"/>)">退出群组</a>
	</s:elseif>

</div>
<!-- script src="js/quit.js"> 

</script>-->
<script src="js/group_administration/administration.js"></script>
<script src="js/group/group.js"></script>

