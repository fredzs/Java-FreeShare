<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title>可读组</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<form action="" method="post" >
	<div class="fromdiv">
	    <div class="centeralign">
	    	<input type="text" id="searchText" value="" class="squareinput" onkeyup="searchFun('r')" />
        </div>
       
	    <div class="leftalign xparseline">
          	<span>选择：</span>
          	<span><a href="javascript:void(0)" id="selectAllId" onclick="selectAllFn()">全部</a></span>&nbsp;
          	<span><a href="javascript:void(0)" id="selectNoneId" onclick="selectNoneFn()">无</a></span>
        </div>
        <div id="selectListDiv"> 
        	<table id="searchTable" class="parseline"></table>
        	
            <table id="selectTable" class="parseline">
             <tr><td><label>------------------选择可读组-----------------</label></td></tr>
                  <s:iterator value="grouplist" id="map" status="st">               
                    <tr>
                        <td><input type="checkbox" name='<s:property value="key"/>:<s:property value="value" />' value='<s:property value="value" />'/>
                        	<s:property value="value" />
                        </td>
                    </tr>
                </s:iterator>
            </table>
        </div>
	</div>
	<div class="midfunction">
		<input type="button" value="添加 >>" class="button" onclick="addUserFn()" />
	</div>
	
	<div class="todiv">
		<div class="leftalign xparseline">已选收件人：<span id="selectedNumId">0</span>个</div>
        <input type="text" value="" id="selectedList" name="selectedList" class="hidden" />
        <div id="resultDiv">
            <table id="resultTableId" class="parseline" name="resultTable">
            </table>
        </div>
    </div>
    
	<div class="clear"></div>
	<div class="rightalign">
	    <input type="button" value="确定" class="graybutton" id="submitgroups" onclick="submitReadGroups();"/>
		<!-- input type="submit" value="确定" class="graybutton"/> -->
        <input type="button" value="取消" class="graybutton" id="cancelNewAdmin" onclick="cancelNewAdminFn()"/>
    </div>
    </form>
    <s:include value="/basic/_globlejs.jsp" />
      <script src="js/group/group.js"></script>
</body>
</html>