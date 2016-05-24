<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑列表--Free分享</title>
<s:include value="/template/_head.jsp" />
<link href="uueditor/themes/default/css/ueditor.css" rel="stylesheet" />
</head>

<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp" />
		<div id="inner" class="content">
			<h1>修改列表信息</h1>
			<div class=dottedbox>
				<form id="updatelistForm" action="resource/updatelist"
					method="post">
					<input type="hidden" name="id"
						value="<s:property value="id"/>" />
					<table class="formtable">
						<tr>
							<td >名称<br /> <span class="lightgreyletter">（少于20字）</span></td>
							<td  colspan="2"><input id="name" type="text"
								 class="fullinput" name="name"
								 value="<s:property value="name" />" /> 
								 <span id="text" class="redletter"></span></td>
						</tr>
						<tr>
							<td >描述<br /> <span class="lightgreyletter">（少于300字）</span></td>
							<td  colspan="2">
							<script type="text/plain" id="description" name="description"></script> 
							<span id="descriptionText" class="redletter"></span></td>
						</tr>
						<tr><td ><br/></td></tr>
						<s:include value="../upload/_sendtomyself.jsp" >
							<s:param name="type">1</s:param>
						</s:include>
						<input type="text" id="toMySelfWrapperId" class="hidden" value="<s:property value="configMySelf"/>"/>
						<!--tr>
							<td >
							<a id="write" class="blueletter" href="upload/getwritegroups" title="归属群组" rel="facebox">归属群组</a></td>
							<td ><input id="select" class="fullinput" value="<s:property value ="writeGroupNames"/>"></input> 
								<input id="test" type="text" class="hidden" name="writeGroupIds"
								value="<s:property value ="writeGroupIds"/>"></input></td>
						</tr-->
						<tr class="hidden">
							<td><a id="read" href=""></a></td>
						</tr>
						<tr>
							<td></td>
			                <td><span class="redletter" id="text4"></span></td>
			            </tr>
			            <tr class = "hidden">
			                <td class="rightalign topveralign padding5">
			                	添加标签<br />
			                	<span class="lightgreyletter">
									（用空格分隔）
								</span>
								<input type="hidden" name="labels" />
								<input type="hidden" name="newLabels" />
			                </td>
			                <td class="padding5 topveralign">
			                	<s:include value="/upload/_tags.jsp" />
			                </td>
			                <td colspan="2"></td>
			            </tr>  
						<tr>
							<td ></td>
							<td >
								<input type="button" id="save2" class="button" value="保 存" onclick="submitupdatelist()" />
								<a href="javascript:history.go(-1)">取消</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<div id="content-cc" class="hidden">
		<s:property value="description" escape="false" />
	</div>
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/upload/upload.js"></script>
	<script src="js/resource/labels.js"></script>
	<!--script src="js/labels.js"></script> 转移到z_legacy中 -->
	<script src="uueditor/editor_config.js"></script>
	<script src="uueditor/editor_all.js"></script>
	<script>
		var settings = {
				getTagsUrl : 'tags',
				newTags : 'newtags'
		};
		$('#addresourcetags').addTags(settings);
		var editor = new baidu.editor.ui.Editor({
			textarea : 'description'
		});
		editor.render("description");
	</script>
	<script>
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
		//$(document).ready(setcontent.ready);
	</script>
</body>
</html>