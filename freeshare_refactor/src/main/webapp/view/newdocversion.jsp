<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + 
request.getContextPath() %>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发布新版--Free分享</title>
<s:include value="/template/_head.jsp" />  
<link href="uueditor/themes/default/css/ueditor.css" rel="stylesheet"/>
</head>
<body>
 	<div id="container">
 		<s:include value="/template/_pub_banner.jsp" />
  		<s:include value="/template/_banner.jsp"/>
  		<div id="inner" class="content">
		 	<h1>发布新版 </h1>
            	<div class="dottedbox">
                   <input  name="id" id="_id" type ="hidden" value="<s:property value="id"/>" />
                   <table class="formtable" id="newDocTable"> 
                   <tr>
                   <td >名称</td>
                   <td><span class="lightgreyletter" id = "_name"><s:property value="name"/></span></td>
                   </tr>
                   <tr><td><br/></td></tr>
                   <tr>
						<td class="rightalign padding5">
							<span id="uploadtip"></span>
						</td>
					    <td class="blackletter">
					    	<table id="files_1"></table> 
					    	<span class="lightgreyletter" id="fileText"></span>
					         </td>
				   </tr>    
                   <tr>
					    <td class="rightalign padding10" >添加</td>
					    <td class="middleveralign">
					        <table><tr>					       		
					         <td class="leftalign" colspan="2">
					        	<form id="docForm" action="upload/uploadfile" method="post" enctype="multipart/form-data">
					            	<span id="drop_zone_1" class="yellowbox padding10">
					            	  	<input id="file_1" type="file" name="doc" class="txtfile"/>
					            	    <a href="javascript:void(0)" class="blueletter strong pointer">点此上传</a>&nbsp;本地文档,或将文档拖拽至此处
					            	</span>			                      	
					        	    </form>                	    
					       </td></tr></table>
					       <%-- <span class="lightgreyletter" id="fileText">&nbsp;文件最大为20M</span> --%>
							<input type="hidden" name="docUuid" id="docUuid" />
							<input type="hidden" name="extension" id="extension" />
							<input type="hidden" name="type" id="preType" value="4"/>
					    </td>
					</tr>                 
                   <tr><td><br/></td></tr>  
                   <tr>
                       <td>
                       	更新说明<br />
                         	<span class="lightgreyletter">（少于100字）</span>
                       </td>
                       <td colspan="2">
                         	<%-- <script type="text/plain" id="description"></script> --%>
                         	<textarea class="fullarea blue1border" id="newVersionDoc"></textarea>
                         	<span id="newVersionText"  class="redletter"></span>
                       </td>
                   </tr>
                   <tr><td><br/></td></tr>  
                   <tr>
                       <td></td>
                       <td><input type="button" class ="button" onclick="javascript:submitnewdoc()" id="submit_newDocForm" value="发布新版"></input>
                       <a href="javascript:history.go(-1)">取消</a>  </td>
                       <td colspan="2"></td>
                   </tr>
               </table>
           </div><!-- dottedbox --> 
    	</div><!--#inner-->
    	<s:include value="../template/_footer.jsp" />
	</div><!--#container--> 
	<s:include value="/basic/_globlejs.jsp" />
	<script src='js/upload/upload.js'></script>
	<script src='js/resource/add_attachments.js'></script>
	<script src="uueditor/editor_config.js"></script>
	<script src="uueditor/editor_all.js"></script>
	<script> 
		
	   /* var editor = new baidu.editor.ui.Editor({ textarea:'description'});  
	   editor.render("description"); */
	</script>
</body>
</html>