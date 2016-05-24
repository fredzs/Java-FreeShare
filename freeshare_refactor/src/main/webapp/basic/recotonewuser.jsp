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
<title>用户指南--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>

<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp" />
		<div id="inner" class="content">
			<div class="sidebar">
				<span class="strong midsize">群组推荐</span> <br />
				<div class="dottedline"></div>
				<table id="re_group">
				</table>
			</div>
			<div class="main leftmargin_0">
				<div class="bottommargin_10 strong midsize">请选择兴趣标签</div>
				<table id = "tag_">
					
				</table>
				<input type="button" class="button" value="确定" id="submitTags"/>
			</div>
			<div class="clear"></div>
		</div>
		<!--#inner-->
		<s:include value="/template/_footer.jsp" />
	</div>
	<s:include value="/basic/_globlejs.jsp" />
	<script	src="js/group/regroup.js"></script>
	<script>
	var selectedTags = '';
	$(document).ready( function(){
		
		$.ajax({
			type:"post",
			url:"reco/newuser",
			data: {},
			success : function(data){
				console.log(data.tagMap);
				$.each(data.tagMap, function(i, value){
				
					var id = value.id;
					var name = value.name;
					$('#tag_').append(
							"<div id=\""+id+"\" class = \"recotags ddgreybg leftfloat padding5 rightmargin_20 bottommargin_20 radius\">" +
							"<img class=\"radius\" src=\"http://freetag.free4lab.com/images/" + name + ".jpg" + "\"" + 
							"width=\"80\" height=\"80\" border=\"0\" />" +
							"<a class = \"centeralign block blackletter nodecoration\" title=\"" +
							name +
							"\" href=\"javascript:void(0)\">" +
							"<stong>" + name + "</strong>"+ "</a></div>"
					);
				})
				$('.recotags').each(function(){
					$(this).click(function(){
						$(this).toggleClass('orangebg', 'ddgreybg');
						if( $(this).hasClass('orangebg') ){
							if( selectedTags.length > 0 && selectedTags[selectedTags.length-1] != ' ' ){
								selectedTags += ' ';
							}
							selectedTags += $(this).attr('id')+' ';
						}else{
							selectedTags = selectedTags.replace($(this).attr('id')+' ' ,'')
						}
					})
				})
			}
		})
		$('#submitTags').click(function(){
			if( selectedTags[selectedTags.length-1] == ',' ){
				selectedTags = selectedTags.substr(0, selectedTags.length-1);
			}
			$.ajax({
				type:"post",
				url:"reco/newusertags",
				data: {tags : selectedTags},
				success : function(data){
					location.href = "reco/recommend?recoType=" + 120;
				}
			})
		})
		
	})
	</script>
</body>
</html>
