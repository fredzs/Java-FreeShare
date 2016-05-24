<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>选择列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div class="member">
		<div class="bottommargin_5">
	    	<input type="text" id="searchText" style="width:178px;" class="squareinput middleveralign"
	    		onkeydown="if(event.keyCode==13){
					searchList('resource/addtolistsearch',{resourceId:<s:property value="id"/>,searchText:$('#searchText').val().trim()},'back');
					return false;}"/>
	    	<a class="sgraybutton" id="searchButton" 
	    		onclick="searchList('resource/addtolistsearch',{resourceId:<s:property value="id"/>,searchText:$('#searchText').val().trim()},'back')">群组列表搜索</a>
		</div>
     
		<div class="fromdiv">
			<div id="buttontag" class="bottommargin_5">
				<button class="graybutton leftbutton selected" id="latestlist" onclick="showLatestList()">最近的列表</button>
				<button class="graybutton middlebutton" id="mylist" onclick="showMyList('resource/addtomylist',{itemId:<s:property value="id"/>})">我的列表</button>
			</div>
			
			<div id="selectListDiv">
			 	<table id="selectTable" class="parseline">
			    <!-- 最近使用的列表 -->
				<s:iterator id="l" value="lists" status="st">
				<tr><td>
					<input id="<s:property value="#l.id"/>" type="checkbox" name="<s:property value="#l.id"/>:<s:property value="#l.name"/>" 
						value="<s:property value="#l.name"/>" onclick="addItemFn(this)"></input>
					<label for="<s:property value="#l.id"/>"><s:property value="#l.name" /></label>
				</td></tr>
				</s:iterator>
				</table>
				<span id="nohistory" class="redletter"></span>
	      	</div>
		</div>
	
		<div class="midfunction"></div>
		
		<div class="todiv">
			<div class="bottommargin_5">
				<button class="graybutton">已选列表：<span id="selectedNumId">0</span>个</button>
			</div>
			<input type="hidden" value="," id="selectedList" name="selectedList" />
		   	<div id="resultDiv">
		       <table id="resultTableId" class="parseline" name="resultTable"></table>
		   	</div>
		   	<br/>
			<div class="rightalign">
			   	<input type="button" class="button" id="submit" onclick="submitAddtoList(<s:property value="method"/>)" value="确定"/>
				<a href="javascript:void(0)" onclick="cancelAddtoList()" >取消</a>
			</div>
	  	</div>
    
		<div class="clear"></div>
	</div>
<script>

$(document).ready(function(){
	getlists();
	initall("您还没有任何历史记录，请到“我的列表”中选择，或在上方进行搜索！", "group","selectedvalue2");
});

/* 
$("#selectListDiv").click(function(){
	getlists();
	initall("您还没有任何历史记录，请到“我的列表”中选择，或在上方进行搜索！", "group","selectedvalue2");
}); 
 */
 
function getlists(){
	 var selectedvalue = "";
	 var selectedIdList = "";
	$.getJSON('resource/getbllists', 
			{id : <s:property value="id"/>},
			function(data) {
			//	alert("data.listsList.length2:"+data.listsList.length);
				if(data.listsList.length > 0){		 
					$.each( data.listsList,function(i, value) {
						selectedvalue += value.id+":"+value.name+",";
						selectedIdList += value.id+",";
					});
			//		alert("selectedvalue2:"+selectedvalue);
					 $('#resultDiv').append("<input id=\"selectedvalue2\" class=\"hidden\" type=\"text\" value=\""+selectedvalue+"\"></input>");
					 $('#resultDiv').append("<input id=\"selectedIdList\" class=\"hidden\" type=\"text\" value=\""+selectedIdList+"\"></input>");
				}
			})
			
	}
 
//完成“加入列表”的具体逻辑操作
function submitAddtoList(func){
	//alert(func);
	var selectedList = $("#selectedList").val();
	if(selectedList!=","){
		$("#submit").attr("disabled","true");
		var selectedArray = new Array();
		var selectedId = "";//最终选中的列表的Id组成的字符串，以逗号分隔，最后一个字符为逗号
		selectedArray = selectedList.split(",");
		for(var i=0; i<selectedArray.length; i++){
			var name = selectedArray[i];
			if(name!=""){
				selectedId += name.split(":")[0]+",";
			}
		}	
		$.post("resource/addtolists",{selectedList:selectedId,id:<s:property value="id"/>},function(){		
			//func();
			//location.reload();
			 if(func())
				 newsaddtolist();//提示加入列表成功
			 else
				 newsaddtolist2();//异步刷新失败
		});
		$(document).trigger('close.facebox');
	}
}

//在资源页面，完成加入列表后，异步刷新所属列表
//by wangxy
function itemaddtolist(){
	 var selectedvalue = "";
	 var selectedIdList = "";
	$.getJSON('resource/getbllists', 
			{id : <s:property value="id"/>},
			function(data) {
				if(data.listsList.length > 0){
					 $('#bl_list').html("");				 
					$.each( data.listsList,function(i, value) {
						var l_desc = value.description;
						var reTag = /\&[a-zA-Z]{1,10};/g;
						var reTag1 = /<(?:.|\s)*?>/g;
						l_desc = l_desc.replace(reTag, "");
						l_desc = l_desc.replace(reTag1, "");
						if (l_desc.length > 35) {
							l_desc = l_desc.substring(0, 35) + "...";
						}
						var l_name = value.name;
						if (l_name.length > 20) {
							l_name = l_name.substring(0, 20) + "...";
						}
						$('#bl_list').append("<div class= \"leftmargin_10 topmargin_5 bottommargin_5\">"
									+ "<a  class = \"blueletter\" href=\"resource?id="
									+ value.id
									+ "\">"
									+ l_name
									+ "</a>"
									+ "<br/><span class=\"greyletter\">"
									+ l_desc
									+ "</span></div>");
						selectedvalue += value.id+":"+value.name+",";
						selectedIdList += value.id+",";
					});
					 $('#bl_list').prepend("<div id=\"bl_list_title\" class=\"strong midsize\">所属列表  <a href=\"resource/preaddtolist?id="+data.id+"&selectedvalue="+selectedvalue+"&method=itemaddtolist\" rel=\"addtolist\" title=\"选择列表\" class=\"blueletter strong normalsize\">  修改</a></div>");
					// $('#bl_list').append("<input id=\"selectedvalue2\" class=\"hidden\" type=\"text\" value=\""+selectedvalue+"\"></input>");
					// $('#bl_list').append("<input id=\"selectedIdList\" class=\"hidden\" type=\"text\" value=\""+selectedIdList+"\"></input>");
					 $('a[rel*=addtolist]').facebox();
					 $('#bl_list_title').css({'padding-left':'20px','background':'url(images/bllist.png) left center no-repeat'});
					 $('#bl_list').addClass("dottedline bottommargin_10");
				}
			})
		
		return true;
}

function newsaddtolist(){
	var  tipContent = "<table><tr><td><img src='images/success.png'/></td><td>&nbsp;修改列表成功&nbsp;</td></tr></table>";
	$("#tipboxWrapper").html("<div  id=\"tipbox\"  class=\"mytipboxposition\"></div>");
	$("#tipbox").tipbox  ({
	         content:tipContent,
	         autoclose:true,
	         hasclose:false
	});
}  
//加入列表失败，信息提示
function newsaddtolist2(){
	var  tipContent = "<table><tr><td><img src='images/success.png'/></td><td>&nbsp;所属列表未刷新，请刷新当前网页查看刷&nbsp;</td></tr></table>";
	$("#tipboxWrapper").html("<div  id=\"tipbox\"  class=\"mytipboxposition\"></div>");
	$("#tipbox").tipbox  ({
	         content:tipContent,
	         autoclose:true,
	         hasclose:false
	});
}  

</script>
</body>
</html>
