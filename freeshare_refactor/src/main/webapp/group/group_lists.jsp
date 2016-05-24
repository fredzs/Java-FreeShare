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
<title>群组列表--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>
<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp?index=group"></s:include>
		<div id="inner" class="content">
			<s:include value="_mygroup.jsp?menu=group_lists"></s:include>
			<s:if test="srhResult.totalNum > 0 && lastPage != 1">
				<div class="divpage rightalign">
					<s:include value="/_page/_pager.jsp">
						<s:param name="url">group/lists?groupId=<s:property value="groupId" />&</s:param>
						<s:param name="currPage" value="page"></s:param>
						<s:param name="endPage" value="lastPage"></s:param>
					</s:include>
				</div>
			</s:if>
			<div class="list">
				<s:iterator id="doc" value="srhResult.docs" status="st">
					<div>
						<table class="formtable">
							<tr>
								<td><img src="images/list.png" /></td>
								<td><a class="blackletter"
									href="<s:property value="#doc.url"/>"><s:property
											value="#doc.title" /></a><br /> <span
									id="<s:property value="#doc.time" />"></span> <script>
										var str = '<s:property value="#doc.description" escape="false"/>';
										var descelem = document
												.getElementById("<s:property value="#doc.time" />");

										var reTag = /\&[a-zA-Z]{1,10};/g;
										var reTag1 = /<(?:.|\s)*?>/g;
										str = str.replace(reTag, "");
										str = str.replace(reTag1, "");

										if (str.length > 50) {
											str = str.substring(0, 50)
													+ "...";
										}
										descelem.innerHTML = str;
									</script></td>
								<td class="topveralign">
									<span class="issuedate">发布于
								    <s:property value="#doc.time" /></span>
								</td>
							</tr>
						</table>
					</div>
				</s:iterator>
			</div>

			<s:if test="srhResult.totalNum > 0 && lastPage != 1">
				<div class="divpage rightalign">
					<s:include value="/_page/_pager.jsp">
						<s:param name="url">group/lists?groupId=<s:property value="groupId" />&</s:param>
						<s:param name="currPage" value="page"></s:param>
						<s:param name="endPage" value="lastPage"></s:param>
					</s:include>
				</div>
			</s:if>
		</div>
		<!--#inner-->
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/group_administration/administration.js"></script>
	<script>
		document.getElementById("group_lists").innerHTML = "本组列表 ("
				+ <s:property value='srhResult.totalNum' /> + "个)";
	</script>
</body>
</html>