<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发布资源--Free分享</title>
<s:include value="/template/_head.jsp" />  
<link href="ueditor/themes/default/ueditor.css" rel="stylesheet"/>
</head>

<body>
	<div id="container">
	  	<s:include value="/template/_pub_banner.jsp"/>
	  	<s:include value="/template/_banner.jsp"></s:include>
	  	<div id="inner" class="content">
	    	<div class="bottommargin_10 topmargin_10">
				<a href="upload/uploadtopic" class="graybutton leftbutton">新话题</a>
				<a href="upload/uploaditem" class="graybutton middlebutton ${'selected'}">新资源</a>
				<a href="upload/uploadlist" class="graybutton rightbutton">新列表</a>
			</div>
	        <div class="dottedbox">
	            <table  id="docTable"> 
	            
	            <!-- 引入和“发布资源”公用的列表部分 -->
		        <s:include value="_commonofresource.jsp" />
	            
	               <tr><td></td>
	                <td>
	                	<a class="button" id="submit_doc">发布</a>
	                	<a href="" class="blueletter">取消</a>
	                </td>
	                <td colspan="2"></td>
	            </tr></table>
	        </div>
	  	</div><!--#inner-->
	  	<s:include value="/template/_footer.jsp" />
	</div><!--#container--> 
	<s:include value="/basic/_globlejs.jsp" />
	<script src="ueditor/editor_config.js"></script>
	<script src="ueditor/editor_all.js"></script>
	<script src="js/upload/upload.js"></script>
    <script> 
		var editor = new baidu.editor.ui.Editor({ textarea:'docdesc'});  
	    editor.render("docdesc");
	</script> 
	<script src="js/resource/add_attachments.js"></script>
</body>
</html>