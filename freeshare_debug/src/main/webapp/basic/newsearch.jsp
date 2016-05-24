<!DOCTYPE html>
<%@ page import="com.free4lab.freeshare.*"%>
<%@ page import="java.net.URLEncoder, java.util.*, com.opensymphony.xwork2.*"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>搜索结果--Free分享</title>
<s:include value="/template/_head.jsp" />  
</head>

<body>
<div id="container">
	<s:include value="/template/_pub_banner.jsp"/>
	<s:include value="/template/_banner.jsp?index=search"></s:include>
	<div id="inner" class="content">
		<div class="sidebar">
			<div id="re_group">
			</div>
		</div>
		<div class="main leftmargin_0">
			<form action="search" method="post">
				<input class="squareinput greyletter" name="query"
				 id="bigquery" value="<s:property value="query"/>" placeholder="查找群组、列表和资源" />
				 <input type="hidden" name="groupIds" value="<s:property value='groupIds'/>" id="groupidspage" />
				 <input type="hidden" name="resourceTypeList" value="<s:property value='resourceTypeList'/>" id="searchtypepage"/>
				 <input type="submit" class="sgraybutton" value="搜索" />

				 <div id="searchpage" class = "hidden">
					 <div class="grey1border padding715 radius">
					 	<table id="searchmytypespage"><tr>
						 	<td width="60px" class="topveralign darkblueletter">搜索类型:</td>
						 	<td>
							 	<div class="bottommargin_5">
									<input type="checkbox" id="selectalltypespage" value="all" />
									<label for="selectalltypespage">全选</label>
								</div>
								<div class="searchmytypespage">
									<input type="checkbox" id="SHARE:FMT:LIST:PAGE" value="SHARE:FMT:LIST"/> 
									<label class="rightmargin_20" for="SHARE:FMT:LIST:PAGE">列表</label>
									<input type="checkbox" id="SHARE:ITEM:TEXT:PAGE" value="SHARE:ITEM:TEXT" /> 
									<label class="rightmargin_20" for="SHARE:ITEM:TEXT:PAGE">文章</label>
									<input type="checkbox" id="SHARE:ITEM:URL:PAGE" value="SHARE:ITEM:URL" /> 
									<label class="rightmargin_20" for="SHARE:ITEM:URL:PAGE">链接</label>
									<input type="checkbox" id="SHARE:ITEM:VIDEO:PAGE" value="SHARE:ITEM:VIDEO" /> 
									<label class="rightmargin_20" for="SHARE:ITEM:VIDEO:PAGE">视频</label>
									<input type="checkbox" id="SHARE:ITEM:DOC:PAGE" value="SHARE:ITEM:DOC" /> 
									<label class="rightmargin_20" for="SHARE:ITEM:DOC:PAGE">文档</label>
									<input type="checkbox" id="SHARE:ITEM:TOPIC:PAGE" value="SHARE:ITEM:TOPIC" /> 
									<label class="rightmargin_20" for="SHARE:ITEM:TOPIC:PAGE">话题</label>
									<input type="checkbox" id="SHARE:FMT:GROUP:PAGE" value="SHARE:FMT:GROUP" /> 
									<label class="rightmargin_20" for="SHARE:FMT:GROUP:PAGE">群组</label>
								</div>
							</td>
						</tr></table>
					 	<table class="topmargin_10" id="searchmygroupspage"><tr>
						 	<td width="60px" class="topveralign darkblueletter">搜索范围:</td>
						 	<td id="searchmygroupstd"></td>
						</tr></table>
						<table class="topmargin_10"><tr>
						 	<td width="60px" ></td>
						 	<td class="leftalign"><input type="button" class="greybutton" value="收起" id="searchpagecancel" /></td>
						</tr></table>
					 </div>
				 </div>
			</form>
			<div class="bottommargin_10" style="margin-top: 10px;">
				<s:if test = "query == ''">
					搜索结果共<s:property value='srhResult.totalNum'/>个
				</s:if>
				<s:else>
					搜索结果[包含'<s:property value="query"/>'的结果]共<s:property value='srhResult.totalNum'/>个
				</s:else>
			</div>
		<div class="list">							
	        <s:iterator id="doc" value="srhResult.docs" status="st">
	          <div>
	            <table class="formtable">
	              <tr>
	                <td rowspan="2">
	                	<s:if test="#doc.type == -1">
							<img src="images/list.png" border="0" />
						</s:if>
						<s:elseif test="#doc.type == 6">
							<img src="images/group_head.png" border="0" />
						</s:elseif>	
						<s:else>
							<img src="images/resource.png" border="0" />
						</s:else>            
	                </td>
	                <td>
	                	<a href="<%= com.free4lab.freeshare.URLConst.APIPrefix_FreeSearch %>/page?fakeUrl=<s:property value="#doc.url"/>&hostname=<%= request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>" target="_blank">
	                	<s:property value="#doc.title" escape = "false"/>
	                	</a>
	                	<br/>
	                	<span> <s:property value="#doc.description" escape="false"/> </span> 
	                </td>
	              </tr>
	            </table>
	          </div>  
	        </s:iterator>        
	       </div>
	   		<s:if test="srhResult.totalNum != 0">
		        <div class="divpage">   
		        	<s:include value="/_page/_pager.jsp">
		           		<s:param name="url"><%= URLEncoder.encode("search?type2="+ActionContext.getContext().getValueStack().findValue("type2")+"&query="+ActionContext.getContext().getValueStack().findValue("query")+"&groupIds="+ActionContext.getContext().getValueStack().findValue("groupIds")+"&resourceTypeList="+ActionContext.getContext().getValueStack().findValue("resourceTypeList"), "UTF-8") %>&</s:param>
		           		<s:param name="currPage" value="page"/>
		           		<s:param name="endPage" value="lastPage"/>
		         	</s:include>         
		       	</div><!-- divpage -->
		    </s:if>
		</div>
		<div class="clear"></div>
  	</div><!--#inner-->
  <s:include value="/template/_footer.jsp" />
</div><!--#container-->  

<s:include value="/basic/_globlejs.jsp" />
<script src="js/basic/recommend.js"></script>
<script	src="js/basic/searchtips.js"></script>
<script>
    $(document).ready( function() {
        recommendGroup();
    })
	$("#searchpagecancel").click(function(){
		$("#searchpage").addClass("hidden");
	})

	function searchMenu_new(){
	var isOut4 = true;
	$("#bigquery").focus(function(){
		isOut4 = false;
		if($("#searchpage").hasClass("hidden")){
			$("#searchpage").removeClass("hidden");
			var groupIds = "<s:property value='groupIds'/>".split(',');
			var resourceTypeList = "<s:property value='resourceTypeList'/>".split(',');
			if(resourceTypeList[0] == 'all'){
				$("#searchmytypespage input[type=checkbox]").attr('checked', 'checked');
			}else{
				$.each($(".searchmytypespage input[type=checkbox]"), function(i, value){
					if( $.inArray($(this).val(),resourceTypeList) >= 0 ){
						$(this).attr('checked', 'checked');
					}
				})
			}
			var selectAllObj = $("#selectalltypespage");
			var otherObjs = $(".searchmytypespage input[type=checkbox]");
			var resultInput = $("#searchtypepage");
			searchTips.selectAll(selectAllObj, otherObjs, resultInput);
			var myGroupUrl = "getmygroupsajax";
			$.post(myGroupUrl, {}, function(data){
				
				if(data.lists.length == 0){
					$("#searchmygroupstd").html("您还没有加入任何群组");
					return false;
				}else{
					$("#searchmygroupstd").html("");
					$("#searchmygroupstd").append("<div class=\"bottommargin_5\"><input" +
							" type=\"checkbox\" id=\"selectallgroupspage\" value=\"all\" />" +
							"<label for=\"selectallgroupspage\">全选</label></div>" +
							"<div class=\"leftfloat rightmargin_10 searchmygroupspage\" id=\"searchmygroupspage1\"></div>" +
							"<div class=\"leftfloat rightmargin_10 searchmygroupspage\" id=\"searchmygroupspage2\"></div>" +
							"<div class=\"leftfloat rightmargin_10 searchmygroupspage\" id=\"searchmygroupspage3\"></div>" +
							"<div class=\"leftfloat rightmargin_10 searchmygroupspage\" id=\"searchmygroupspage4\"></div>");
					$.each(data.lists, function(i, value){
						var name = value.name;
						if(name.length > 9){
							name = name.substring(0,8)+'...';
						}
						var container;
						if( i % 4 == 0){
							container = $("#searchmygroupspage1");
						}else if( i % 4 == 1){
							container = $("#searchmygroupspage2");
						}else if( i % 4 == 2){
							container = $("#searchmygroupspage3");
						}else if( i % 4 == 3){
							container = $("#searchmygroupspage4");
						}
						$(container).append("<div><input type=\"checkbox\" id=\"searchgroup" + value.id + 
								"\" title=\"" + value.name + "\" value=\"" + value.id + "\" /> <label title=\"" + value.name + "\" for=\"searchgroup" + 
								value.id + "\">" + name + "</label></div>");
					});
					if(groupIds[0] == 'all'){
						$("#searchmygroupspage input[type=checkbox]").attr('checked', 'checked');
					}else{
						$.each($(".searchmygroupspage input[type=checkbox]"), function(i, value){
							if( $.inArray($(this).val(),groupIds) >= 0 ){
								$(this).attr('checked', 'checked');
							}
						})
					}
					var selectAllObj = $("#selectallgroupspage");
					var otherObjs = $(".searchmygroupspage input[type=checkbox]");
					var resultInput = $("#groupidspage");
					searchTips.selectAll(selectAllObj, otherObjs, resultInput);
				}
			});
		}
	});
	
		$("#searchpage").click(function(){
			isOut4 = false;
		});
	    $("#container").click(function(){
	        if(isOut4){
	        	$("#searchpage").addClass('hidden');
	        }
	        isOut4 = true;
	    });
	}
	$(function(){
		searchMenu_new();
	});
</script>
</body>
</html>