<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>选择群组</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div class="member">
		<div class="bottommargin_5">
    	<input type="text" id="searchText" style="width:178px;" class="squareinput middleveralign"
    		onkeydown="if(event.keyCode==13){ searchList('resource/addtomygroup','','front'); return false;}"/>
    	<a class="sgraybutton" id="searchButton" 
    		onclick="searchList('resource/addtomygroup','','front')">群组搜索</a>
	</div>
     
	<div class="fromdiv">
		<div id="buttontag" class="bottommargin_5">
			<button class="graybutton leftbutton selected" id="latestlist" onclick="showLatestList()">最近的群组</button>
			<button class="graybutton middlebutton" id="mylist" onclick="showMyList('resource/addtomygroup','')">我的群组</button>
		</div>
			
      	<div id="selectListDiv">
        	<table id="selectTable"  class="parseline">
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
			<button class="graybutton">已选群组：<span id="selectedNumId">0</span>个</button>
		</div>
		<input  value="," type="hidden" id="selectedList" name="selectedList" />
	    <div id="resultDiv">
	        <table id="resultTableId" class="parseline" name="resultTable"></table>
	    </div>
	    <br/>
		<div class="rightalign">
		    <input type="button" class="button" onclick="submitAddtoGroup(${param.submittype})" value="确定"/>
		    <a class="blueletter" href="javascript:void(0)" onclick="cancelAddtoList()" >取消</a>
	   	</div>
  	</div>
   
	<div class="clear"></div>
  </div>
<script>
$(document).ready(function (){
	initall("您还没有任何历史记录，请到“我的群组”中选择，或在上方进行搜索！", "group");
	
});
</script>
<%-- <script src="js/upload/upload.js"></script> --%>
</body>
</html>
