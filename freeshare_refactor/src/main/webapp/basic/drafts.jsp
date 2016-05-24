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
<title>我的草稿--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>

<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp?index=mine"></s:include>
		<div id="inner" class="content">
			<div class="list">							
        <s:iterator id="doc" value="doList" status="st">
          <div>
            <table class="formtable">
              <tr>
                <td rowspan="2">
                </td>
                <td>
                	<a href="draft?id=<s:property value="#doc.id"/>" >
                	<s:property value="#doc.name" escape = "false"/>
                	</a>
                	<s:property value="#doc.time"/>
                	<br/>
                	<span> <s:property value="#doc.description" escape="false"/> </span> 
                </td>
              </tr>
            </table>
          </div>  
        </s:iterator>        
       </div>
		</div>
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/basic/filter.js"></script>
</body>
</html>