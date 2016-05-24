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
<title>编辑资源--Free分享</title>
<s:include value="/template/_head.jsp" />
<link href="uueditor/themes/default/css/ueditor.css" rel="stylesheet" />

</head>
<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp" />
		<div id="inner" class="content">
	    	<h1>修改资源信息</h1>
	    	<!-- 隐藏的，从action处得到的信息 -->
	    	<input type="hidden" id="itemId" value="<s:property value="id"/>" />
			<input type="hidden" id="preEnclosure" value="<s:property value="enclosure"/>"/>
			<input type="hidden" id="preAttachment" value="<s:property value="attachment"/>"/>
			<input type="hidden" id="preExtension" value="<s:property value="extension"/>"/>
            <input type="hidden" id="preType" value="<s:property value='type'/>"/>
            <input type="hidden" id="preName" value="<s:property value='name'/>"/>
            <input type="hidden" id="toMySelfWrapperId" value="<s:property value="configMySelf"/>"/>
            <!--#description container-->
			<div id="content-cc" class="hidden">
				<s:property value="description" escape="false" />
			</div>
			<!-- 以下为正常显示的列表 -->
	        <div class="dottedbox">
	            <table>
	            <!-- 引入和“发布资源”公用的列表部分 -->
	            <s:include value="/upload/_commonofresource.jsp" />
				<tr class = "hidden">
	                <td class="rightalign topveralign padding5">
	                	添加标签<br />
	                	<span class="lightgreyletter">
							（用空格分隔）
						</span>
	                </td>
	                <td class="padding5 topveralign">
	                	<s:include value="/upload/_tags.jsp" />
	                </td>
	                <td colspan="2"></td>
	            </tr> 
	            <tr><td></td>
	                <td>
	                	<!--  input type="button" class="button" value="保存"
								id="submit_updateitem" /--> 
						<input type="button" class="button" value="保存"
								id="submit_updateitem" /> 
						<a href="javascript:history.go(-1)">取消</a>
	                </td>
	                <td colspan="2"></td> 
	            </tr></table>
	        </div>
	  	</div>
		<!--#inner-->
		<s:include value="/template/_footer.jsp" />
	</div>
	
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/upload/upload.js"></script>
	<script src="js/resource/labels.js"></script> 
	<script src="js/resource/add_attachments.js"></script>
	<script src="uueditor/editor_config.js"></script>
	<script src="uueditor/editor_all.js"></script>
	<script>
		var settings = {
				getTagsUrl : 'tags',
				newTags : 'newtags'
		};
		$('#addresourcetags').addTags(settings);
		var editor = new baidu.editor.ui.Editor({
			textarea : 'docdesc'
		});	
		editor.ready(function(){
        	//需要ready后执行，否则可能报错
        	var content = document.getElementById("content-cc").innerHTML;
        	editor.setContent(content);
        })
		/* var setcontent = {
			ready : function() {
				var content = document.getElementById("content-cc").innerHTML;
				editor.setContent(content);
			}
		} */
		$(document).ready(function(){			
			//初始化标题
			$("#name").val( $("#preName").val() );
			//初始化百度编辑器及其内容
			editor.render("docdesc");
			//setcontent.ready();
			//初始化链接或者附件
			var myType = $("#preType").val();
			if(myType == '2' || myType == '3'){
				$("#linkId").val( $("#preEnclosure").val() );
				submiturl();
			}else if( myType == '1' ){
            	$("#docUuid").val( $("#preEnclosure").val() );
        		$("#extension").val( $("#preExtension").val() );
        		$("#uploadtip").html("已添加");
        		var delLink = "<span class=\"blueletter\">&nbsp;&nbsp;&nbsp;[&nbsp;<a class=\"blueletter\" href=\"javascript:void(0)\" onclick=\"deleteAddedResource()\">删除</a>&nbsp;]</span>";
                $("#recordAddedRes").val('<tr><td>' + $("#preAttachment").val() + delLink+'<\/td><\/tr>');
        		$("#files_1").html("<tr><td>"+$("#preAttachment").val() +delLink + "</td></tr>");
			}			
		});
	</script> 
</body>
</html>