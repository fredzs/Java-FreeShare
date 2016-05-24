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
<link href="ueditor/themes/default/ueditor.css" rel="stylesheet" />

</head>
<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp" />
		<div id="inner" class="content">
			<h1>修改资源信息</h1>
			<div class="dottedbox">
				<form id="updateitemForm" action="resource/updateitem" method="post"
					enctype="multipart/form-data">
					<input name="itemId" class="hidden"
						value="<s:property value="itemId"/>" />
					<table class="formtable">
						<tr>
							<td>名称<br /> <span class="lightgreyletter">（少于20字）</span>
							</td>
							<td><input id="name" type="text" class="fullinput"
								name="name" value="<s:property value="name" />" /> <span
								id="text" class="redletter"></span></td>
						</tr>
						<s:if test="type==2||type==3">
							<tr>
								<td>链接地址<br />
								</td>
								<td><input id="url" type="text" class="fullinput"
									name="link" value="<s:property value="link" />" /> <span
									id="text" class="redletter"></span></td>
							</tr>
							<tr>
								<td><br /></td>
							</tr>
						</s:if>
						<tr>
							<s:if test="type==4">
								<td>文章内容<br /> <span class="lightgreyletter"></span>
							</s:if>
							<s:else>
								<td>描述<br /> <span class="lightgreyletter">（少于300字）</span>
							</s:else>
							</td>
							<td colspan="2"><script type="text/plain" id="updescription"
									name="description"></script> <span id="descriptionText"
								class="redletter"></span></td>
						</tr>
						<tr>
							<td><br /></td>
						</tr>
						<!--tr>
							<td><a id="write" class="blueletter"
								href="upload/getwritegroups" title="归属群组" rel="facebox">归属群组</a></td>
							<td><input id="select" class="fullinput"
								value="<s:property value ="writeGroupNames"/>"></input> <input
								id="test" type="text" class="hidden" name="writeGroupIds"
								value="<s:property value ="writeGroupIds"/>"></input></td>
						</tr-->
						<s:include value="../upload/_sendtomyself.jsp" >
							<s:param name="type">1</s:param>
						</s:include>
						<input type="text" id="toMySelfWrapperId" class="hidden" value="<s:property value="configMySelf"/>"/>
						<tr class="hidden">
							<td><a id="read" href=""></a></td>
						</tr>
						<tr>
							<td></td>
			                <td><span class="redletter" id="text4"></span></td>
			            </tr>
						<tr>
							<td></td>
							<td><input type="button" id="save" class="button" value="保存"
								onclick="submitupdateitem()" /> <a
								href="javascript:history.go(-1)">取消</a></td>
							<td></td>
							<td></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!--#inner-->
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<div id="content-cc" class="hidden">
		<s:property value="description" escape="false" />
	</div>
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/upload/upload.js"></script>
	<script src="ueditor/editor_config.js"></script>
	<script src="ueditor/editor_all.js"></script>
	<script>
		var editor = new baidu.editor.ui.Editor({
			textarea : 'description'
		});
		editor.render("updescription");
	</script>

	<script>
		var setcontent = {
			ready : function() {
				var content = document.getElementById("content-cc").innerHTML;
				editor.setContent(content);
			}
		}
		$(document).ready(setcontent.ready);
	</script>
</body>
</html>