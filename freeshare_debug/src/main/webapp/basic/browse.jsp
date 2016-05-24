<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<base href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:property value="info.name" /> - <s:property
		value="authorName" />--Free分享</title>
<s:include value="/template/_head.jsp" />

</head>
<body>
	<div id="container">
		<s:if test="0 != banner">
			<s:include value="/template/_pub_banner.jsp" />
			<s:include value="/template/_banner.jsp"></s:include>
		</s:if>	
		<div id="tipboxWrapper"></div>
		<div id="inner" class="content">
			<s:if test="0 != right">
				<div class="sidebar">
					<s:if test="info.type != null"><!-- 资源类型不是列表 -->
						<div id="bl_list">
							<%-- <span class="strong midsize"><img src="images/bllist.png" class="" />所属列表</span> <br />
							<div id="bl_list"  class=dottedline>
							</div> --%>
						</div>
					</s:if>
					<div id="bl_groups">
						<%-- <span class="strong midsize">所属群组</span> <br />
						<div id="bl_groups"  class=dottedline>
						</div> --%>
					</div>
					<div id="reco_you1">
                        <%-- <span class="strong midsize">猜你喜欢</span> <br />
                        <div id="reco_you">
                        </div> --%>
                    </div>
                    
					<div id="reco_you">
						<%-- <span class="strong midsize">猜你喜欢</span> <br />
						<div id="reco_you">
						</div> --%>
					</div>
					
				</div>
				<div class="main leftmargin_0">
			</s:if>
			<s:else>
				<div>
			</s:else>
				<h1>
					<input id="resourceId" type="hidden" value='<s:property value="info.id" />'/>
					<s:property value="info.name" />
				</h1>
				<s:if test="#session.userId!=null">
					<s:if test="0 != evaluate">
						<div class="resourceinfo">
							<s:include value="/basic/_score.jsp" />
						</div>
					</s:if>
				</s:if>
				<s:if test="info.type != null">
				<s:include value="/basic/_itemoperation.jsp" />
				</s:if>
				<s:else>
					<s:include value="/basic/_listoperation.jsp" />
				</s:else>
				<br/>
				<s:include value="/basic/_maininfo.jsp" />
				<br/>
				<s:include value="/basic/_iteminlist.jsp" />
				<br/>	
				<!-- 	
				<div id="bl_tags" class="greybg grey1border padding10 bottommargin_10 radius">
				</div>
				 -->	
			<div class="cmtcontent"></div>
			</div>
			<!--right-->
			<div class="clear"></div>
		</div>
		<!--#inner-->
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/right_column/belonggroups.js"></script>
	<script src="js/right_column/belonglists.js"></script>
	<!--  script src="js/right_column/belonglabels.js"></script-->
	<script src="js/basic/recommend.js"></script>
	<script>
	$(document).ready( function() {
		$("img").css({'max-width':'680px'});
		
		var resourceType = '<s:property value = "resourceType"/>';
		var type=-1;
		//资源类型
		if(resourceType != -1){
			getbllists('<s:property value = "id"/>');
			type='<s:property value = "info.type"/>';
		}
		getblgroups('<s:property value = "id"/>', resourceType);
		//getbltags('<s:property value = "id"/>', '<s:property value = "info.type"/>');
		
		recommend(120,type,<s:property value = "id"/>);
		recommend(120,type,<s:property value = "id"/>,0);
		var param = {
				resourceId: <s:property value="id" />,
				type: type
		}
		showComments(param);
	})
	
	function del(type){
		alert("type:"+type);
		$.ajax({
			type : 'post',
			url : 'resource/delete',
			data : {
				type: type,
				id : '<s:property value="id" />',
			},
			success : function(data) {
				 fillTipBox('success','删除成功，该窗口稍等会自动关闭。');
				 window.setTimeout("window.close()", 2000);
			}
		});
	}
	//待抽离
	function edit_desc(param,param1){
		if($('#edit_val').length > 0){
			alert('请编辑当前资源后再选择编辑其他资源。');
		}else{
			$('#' + param1).attr("onclick","javascript:void(0)");
			var oldval = $('#' + param).children().text();
			var uuid = $('#' + param).children().attr('id');
			$('#' + param).children().remove();
			var instr = '<input id=\"edit_val\" type = \"text\" name = \"description\" value = \"' + oldval + '\"></input>';
			instr = instr + '&nbsp;<a href = \"javascript:subedit(\'' + param + '\',\'' + uuid + '\')\">' + '确定' + '</a>';
			instr = instr + '&nbsp;<a href = \"javascript:canceledit(\'' + param + '\',\'' + oldval + '\',\'' + uuid + '\')\">' + '取消' + '</a>';
			$('#' + param).append(instr);
		}
	}
	function canceledit(param1, param2, uuid){	
		var instr = '<span id = \'' + uuid + '\'>' + param2 + '</span>';
		$('#' + param1).children().remove();
		$('#' + param1).html(instr);
		var aid = 'a' + param1.substring(2, param1.length);
		$('#' + aid).attr("onclick",'javascript:edit_desc(\'' + param1 + '\',\'' + aid + '\')');
	}
	function subedit(param, uuid){
		$.ajax({
			type : 'post', 
			url : 'resource/updateVersionDesc',
			data : {
				name : $('#name_' + '<s:property value="id" />' + '_0').val(),
				description : $('#edit_val').val(),
				docUuid : uuid,
				itemId : '<s:property value="id" />'
			},success : function(data) {
				var instr = '<span id = \'' + uuid + '\'>' + $('#edit_val').val() + '</span>';
				$('#' + param).children().remove();
				$('#' + param).html(instr);
				var aid = 'a' + param.substring(2, param.length);
				$('#' + aid).attr("onclick",'javascript:edit_desc(\'' + param + '\',\'' + aid + '\')');
			}
		
		})
	}
		//检测url中有没有tip需要显示
		if(location.href.indexOf("?") != -1){
			var myquery = location.href.split("?")[1];
			var queryArray = myquery.split("&");
			var queryMap = new Object();
			for(var i=0; i < queryArray.length; i++){
				var aquery = queryArray[i].split("=");
				queryMap[aquery[0]] = aquery[1];
			}
			if( "tipstatus" in queryMap){
				var status = queryMap["tipstatus"];//0-success;1-warning;2-error;3-创建群组
				var content = "发布资源成功";
				fillTipBox(status,content);
			}
		}
	</script>
	<script src="js/resource/comment.js"></script>
</body>
</html>