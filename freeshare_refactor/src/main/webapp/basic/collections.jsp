<!--
使用该文件的地方：
template/_banner.jsp
-->
<!DOCTYPE html>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.opensymphony.xwork2.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@page import="com.free4lab.freeshare.model.data.SrhResult"%>
<%@page import="org.json.JSONObject"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base
	href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我收藏的--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>

<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp?index=mine" />
		<div id="inner" class="content">
			<div class="bottommargin_10 topmargin_10">
				<a  href="collections" class="graybutton leftbutton ${action.type=='item'?'selected':'' }">
				我收藏的资源<s:if test="type=='item'"> 
				(<span id='item'><s:property value="collectionSize"/></span>个)
				</s:if></a>
				<a href="collections?type=list" class="graybutton rightbutton ${action.type=='list'?'selected':'' }">
				我收藏的列表 <s:if test="type=='list'"> 
				(<span id='list'><s:property value="collectionSize"/></span>个)
				</s:if></a>
			</div>
			<s:if test="type=='item'">
			<div class="list">
			<s:iterator value="myCtionList" id="mc" status="st">
				<div><table class="formtable">
					<tr>
						<td><img src="images/resource.png" /></td>
						<td><a href="resource?id=<s:property value ="#mc.id"/>"
							class="blackletter" target="_blank"> <s:property value="#mc.name" /></a><br />
							<span><s:property value="#mc.description" escape="false"/></span></td>
						<td class="topveralign"><a href="javascript:void(0)"
							onclick="removecoll('<s:property value="#mc.id"/>','item')"
							class="blueletter">移除</a></td>
					</tr>
				</table></div>
			</s:iterator>
			</div>
			</s:if>
			
			<s:else>
			<div class="list">
			<s:iterator value="myCtionList" id="mc" status="st">
				<div><table class="formtable">
					<tr>
						<td><img src="images/list.png" /></td>
						<td><a href="resource?id=<s:property value ="#mc.id"/>"
							class="blackletter" target="_blank"> <s:property value="#mc.name" /></a><br />
							<span><s:property value="#mc.description" escape="false"/></span></td>
						<td class="topveralign"><a href="javascript:void(0)"
							onclick="removecoll('<s:property value="#mc.id"/>','list')"
							class="blueletter">移除</a></td>
					</tr>
				</table></div>
			</s:iterator>
			</div>
			</s:else>
			
			<s:if test="myCtionList.size != 0">
	        <div class="divpage">   
	        <!-- TODO page为1的时候url中丢失了页码！！ -->
	        	<s:include value="/_page/_pager.jsp">	   
	           		<s:param name="url"><%= URLEncoder.encode("collections?type=" + ActionContext.getContext().getValueStack().findValue("type")) +"&" %></s:param>           		           		
	           		<s:param name="currPage" value="page"/>
	           		<s:param name="endPage" value="lastPage"/>
	         	</s:include>         
	       	</div><!-- divpage -->
	        </s:if>
			
		</div>
		<s:include value="/template/_footer.jsp" />
	</div>
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/resource/comment.js"></script>
</body>

</html>
