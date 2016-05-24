<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建话题--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>

<body>
	<div id="container">
	    <s:include value="/template/_pub_banner.jsp"/>
		<s:include value="/template/_banner.jsp?index=new"></s:include>
		<div id="inner" class="content">
			<div class="bottommargin_10 topmargin_10">
				<a href="upload/uploadtopic" class="graybutton leftbutton ${'selected'}">话题</a>
				<a href="upload/uploaditem" class="graybutton middlebutton">资源</a>
				<a href="upload/uploadlist" class="graybutton rightbutton">列表</a>
			</div>
			<div class="dottedbox">
				<form id="articleForm" action="upload/publisharticle" method="post"
					enctype="multipart/form-data">
					<table class="formtable"><tr>
						<td >
							话题内容<br /> 
							<span class="lightgreyletter">（少于140字）</span>
						</td>
						<td >
							<textarea id="topicDesc" name="topicDesc" class="fullarea blue1border"></textarea>
							<span id="topicDescTip"></span>
						</td>
					</tr>
						<s:include value="_sendtomyself.jsp" >
							<s:param name="type">0</s:param>
						</s:include>
					<tr>
						<td></td>
						<td><span id="groupTip" class="redletter"></span></td>
					</tr><tr>
						<td></td>
						<td>
<!-- 							<a class = "button" href="javascript:void(0)" onclick="checkBeforeSendFn('topicDesc','toMySelf','selectedIdList')" -->
<!-- 								id="submit_articleForm">发布</a> -->
							<input type="button" value="发布" class="button bottommargin_5 topmargin_5" onclick="submitTopic('topicDesc','toMySelf','selectedIdList',this)"/>
							<a href="javascript:cancle()" class="blueletter">取消</a>
							&nbsp;&nbsp;<span id="warnTipId" class="redletter"></span>	
						</td>
					</tr></table>
				</form>
			</div>
		</div>
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/upload/upload.js"></script>
</body>
</html>