<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>邀请消息--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>

<body>
	<div id="container">
		<div id="tipboxWrapper"></div>
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp?index=group"></s:include>
		<div id="inner">
			
			<div class="right">
			 <h1>我的消息</h1>
				<s:if test="groups.size==0">
				    你现在没有邀请信息。
				</s:if>
				
				<s:elseif test="groups.size>0">
	   	 	    <div>
						<div id = "mem" class="list">    
							<s:iterator value="groups" id="group" status="st">
							<div id="<s:property value ="#st.index"/>">
								<table class="formtable">
									<tr>
										<td rowspan="2"><img src="http://freedisk.free4lab.com/download?uuid=<s:property value="#group.groupInfo.avatar" />" onerror="javascript:this.src='images/group_head.png'"
									width="40px" height="42px" border="0" />
										</td>
										<td class="leftalign">
										<a class="greyletter"  href ="group/items?groupId=<s:property value="#group.groupId"/>"><s:property value="#group.groupInfo.name" /></a>
										    <br/>期待你的加入</td>
										<td>
										<span>
										    <a href="javascript:groupManager.agreeOrIngore('<s:property value="#session.userId"/>','<s:property value='#group.groupId'/>','agree')">同意</a>
		   	 								<a href="javascript:groupManager.agreeOrIngore('<s:property value="#session.userId"/>','<s:property value='#group.groupId'/>','ignore')">忽略</a>
		   	 								</span>	
										</td>
									</tr>
								</table>
							</div>
							</s:iterator>
						</div>
					
				</div>
				</s:elseif>
			</div>
		</div>
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/group_administration/administration.js"></script>
</body>
</html>