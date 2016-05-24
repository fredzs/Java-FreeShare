<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建资源--Free分享</title>
<s:include value="/template/_head.jsp" />  
<link href="uueditor/themes/default/css/ueditor.css" rel="stylesheet"/>
</head>

<body>
	<div id="container">
	  	<s:include value="/template/_pub_banner.jsp"/>
	  	<s:include value="/template/_banner.jsp?index=new"></s:include>
	  	<div id="inner" class="content">
	    	<div class="bottommargin_10 topmargin_10">
				<a href="upload/uploadtopic" class="graybutton leftbutton">话题</a>
				<a href="upload/uploaditem" class="graybutton middlebutton ${'selected'}">资源</a>
				<a href="upload/uploadlist" class="graybutton rightbutton">列表</a>
			</div>
			
			<input type="hidden" name="type" id="preType" value="4"/>  
			
	        <div class="dottedbox">
	            <table  id="docTable">
		            <!-- 引入和“发布资源”公用的列表部分 -->
		            <s:include value="_commonofresource.jsp" />
		        <tr class = "hidden">
	                <td class="rightalign topveralign padding5">
	                	添加标签<br />
	                	<span class="lightgreyletter">
							（用空格分隔）
						</span>
	                </td>
	                <td class="padding5 topveralign">
	                	<s:include value="_tags.jsp" />
	                </td>
	                <td colspan="2"></td>
	            </tr>
	            <tr>
	                <td></td>
	                <td>
	                	<!--  a class="button" id="submit_doc" onclick="javascript:submitDocment();">发布</a-->
	                	<!-- input type="button" class="button" id="submit_doc"  value="发布"/-->
	                	<input type="button" class="button" id="submit_doc" onclick="javascript:submitDoc();" value="发布"/>
	                	<!-- TODO 增加提示 “正在保存”。
	                	<a class="button" id="save_draft" href = "javascript:savedraft();">保存草稿</a>
	                	 防止继续点击保存按钮-->
	                	<a href="javascript:cancle()" class="blueletter">取消</a>
	                </td>
	                <td colspan="2"></td>
	            </tr></table>
	        </div>
	  	</div><!--#inner-->
	  	<s:include value="/template/_footer.jsp" />
	</div><!--#container--> 
	<s:include value="/basic/_globlejs.jsp" />
	<script src="uueditor/editor_config.js"></script>
	<script src="uueditor/editor_all.js"></script>
	<script src="js/upload/upload.js"></script>
	<script src="js/resource/labels.js"></script> 
	
	<script> 
		var editor = new baidu.editor.ui.Editor({ textarea:'docdesc'});  
	    editor.render("docdesc");
	    
	    var settings = {
				getTagsUrl : 'labels',
				newTags : 'newtags'
		};
		$('#addresourcetags').addTags(settings);
	</script>
	<script src="js/resource/add_attachments.js"></script>
</body>
</html>