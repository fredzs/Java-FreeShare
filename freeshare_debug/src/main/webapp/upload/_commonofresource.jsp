<!--
使用该文件的地方：
upload/old_upload_resource.jsp
upload/upload_resource.jsp 新建资源
view/update_item.jsp 编辑资源
-->
<!DOCTYPE html>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<tr><td><br/>
	<input type="hidden" name="docUuid" id="docUuid" value=""/>
	<input type="hidden" name="extension" id="extension" />
	<input type="hidden" value="" id="recordAddedRes" />
	<input type="hidden" value="" id="draft_id" />
</td></tr> 
    <td class="rightalign topveralign padding5" width="100px" >
   		名称	               		
    </td>
    <td class="padding5">
        <input id="name" type="text" value="" name="name" class="fullinput" /> 
    </td>
</tr><tr>
	<td class="lightgreyletter rightalign">
		<span>
			（少于40字）
		</span>
	</td>
	<td>
		<span class="redletter" id="text1"></span>
	</td>
</tr>
<tr><td class="rightalign topveralign padding5" width="100px">模版</td>
         <td >
    	 <div >
    	 
    	 <s:iterator id = "fm" value = "view_fmList"> 
    	 <div class="leftfloat">
    	  <table><tr>
           <td> 
            <a href="javascript:loadfw('<s:property value = "#fm.id"/>')" ><img src="<s:property value = "#fm.image_path"/>"/></a>
            </td> 
            <tr><td width="40" class="centeralign"><s:property value = "#fm.name"/></td>
         </tr></table>
          </div>
    	 </s:iterator>
    	
         <div class="leftfloat">
    	  <table><tr>
           <td> 
            <a href="upload/uploadformwork" title="模版列表" class="leftfloat leftmargin_5" rel="facebox"><img src="images/更多.png" /></a>
         </td> 
            <tr><td width="40" class="centeralign">更多…</td>
         </tr></table>
          </div>
         
         </div>
           </td></tr>
<tr>

<tr>
    <td class="rightalign topveralign padding5" >
    	资源内容<br />
    </td>
    <td class="padding5" colspan="2">                   
       	 <script type="text/plain" id="docdesc" name="docdesc"></script>      
         <span id="docText" class="redletter"></span>
      </td>
</tr><tr>
	<td class="rightalign padding5">
		<span id="uploadtip"></span>
	</td>
    <td class="blackletter">
    	<table id="files_1"></table> 
    	<span class="lightgreyletter" id="fileText"></span>
         </td>
</tr><tr>
    <td class="rightalign padding10" >
         	添加
    </td>
    <td>
        <table><tr>
       		<td>
			<a href="javascript:void(0)" onclick="show()" class="greybutton leftbutton ">链接</a>
        </td>
         <td class="leftalign">
        	<form id="docForm" action="upload/uploadfile" method="post" enctype="multipart/form-data">
            	<span id="drop_zone_1" class="yellowbox padding10">
            	  	<input id="file_1" type="file" name="doc" class="txtfile"/>
            	    <a href="javascript:void(0)" class="blueletter strong pointer">点此上传</a>&nbsp;本地文档,或将文档拖拽至此处
            	</span>			                      	
        	    </form>                	    
       </td>
       </tr></table>
    </td>
</tr><tr>
	<td></td>
	<td>
   		<div id="urladdress" class="hidden">
   			<span >
    			<span class="blackletter strong">
    				请输入要添加的链接（系统将自动为来自优酷、酷6和56的视频链接添加播放功能）
    			</span>
    			<br/>
    			<input type="text" name="url" id="linkId" class="squareinput"/>
    			<a class="greybutton" onclick="javascript:submiturl();">确定</a>
    			<a class="blueletter " href="javascript:void(0)" onclick="javascript:hide();">取消</a>
    			<br/>
    			<span id="linkText" class="redletter"></span>
   			</span>
   		</div>
  	</td>
</tr>
<tr><td></td>
	<td><span class="lightgreyletter" >（只能上传一个附件或链接）</span>
	</td></tr>
<s:include value="_sendtomyself.jsp" >
	<s:param name="type">1</s:param>
</s:include>
<tr>
	<td></td>
             <td><span class="redletter" id="text4"></span></td>
</tr>
<script>
			function loadfw(param){
				 $.ajax({
				 		type:"post", 
				 		url:"upload/formwork", 
				 		data:{
				 			id:param,
				 		},
				 		success:function(data){
				 			$("#name").val( data.name );
				 			editor.setContent(data.content);
				 			$(document).trigger('close.facebox');
				        }
				 });
			}
</script>