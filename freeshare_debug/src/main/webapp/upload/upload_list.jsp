<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建列表--Free分享</title>
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
				<a href="upload/uploaditem" class="graybutton middlebutton ">资源</a>
				<a href="upload/uploadlist" class="graybutton rightbutton ${'selected'}">列表</a>
			</div>
          	<div class="dottedbox">
                <form id="listForm" action="upload/publish" method="post" enctype="multipart/form-data">
                    <table class="formtable"><tr>
                        <td>
                        	名称<br />
                          	<span class="lightgreyletter">（少于40字）</span>
                        </td>
                        <td>
                         	<input id="name" type="text" value="" name="name" class="fullinput" /> 
                          	<span id="text3" class="redletter"></span>
                        </td>
                    </tr><tr>
                        <td>
                        	描述<br />
                          	<span class="lightgreyletter">（少于300字）</span>
                        </td>
                        <td colspan="2">  
                            <script type="text/plain" id="description"></script>
                            <span id="listText" class="redletter"></span>
                        </td>
                    </tr><tr>
                    	<td><br/></td>
                    </tr>
						<s:include value="_sendtomyself.jsp" >
							<s:param name="type">0</s:param>
						</s:include>
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
		                </td>
		                <td class="padding5 topveralign">
		                	<s:include value="_tags.jsp" />
		                </td>
		                <td colspan="2"></td>
		            </tr>
		            <tr>
                       	<td></td>
                        <td>
                          	<!-- a class = "button"  id="submit_list">发布</a -->
                          	<!-- input type="button" class="button" id="submit_list"  value="发布"/> -->
                          	<input type="button" class="button" id="submit_list"   onclick="javascript:submitList();" value="发布"/>
                          	<a href="javascript:cancle()" class="blueletter">取消</a>
                        </td>
                    </tr></table>
                </form>
            </div> 
  		</div><!--#inner-->
  	 	<s:include value="/template/_footer.jsp" />
 	</div><!--#container--> 
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/upload/upload.js"></script>
	<script src="js/resource/labels.js"></script>
	<script src="uueditor/editor_config.js"></script>
	<script src="uueditor/editor_all.js"></script>
	<script> 
	     var editor = new baidu.editor.ui.Editor({ textarea:'description'});                       
	     editor.render("description");	  
	     
	     var settings = {
				getTagsUrl : 'labels',
				newTags : 'newtags'
		 };
		 $('#addresourcetags').addTags(settings);
	</script>
</body>
</html>