<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建话题--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>

<body>
	<div id="container">
	    <s:include value="/template/_pub_banner.jsp"/>
		<s:include value="/template/_banner.jsp?index=new"></s:include>
		<div id="inner" class="content">
			<div class="bottommargin_10 topmargin_10">
				<a href="upload/uploadtopic" class="graybutton leftbutton ${'selected'}">话题</a>
				<a href="upload/uploaditem" class="graybutton middlebutton">资源</a>
				<a href="upload/uploadlist" class="graybutton rightbutton">列表</a>
			</div>
			<div class="dottedbox">
				<form id="articleForm" action="upload/publisharticle" method="post"
					enctype="multipart/form-data">
					<table class="formtable"><tr>
						<td >
							话题内容<br /> 
							<span class="lightgreyletter">（少于140字）</span>
						</td>
						<td >
							<textarea id="topicDesc" name="topicDesc" class="fullarea blue1border" onkeyup="topicCheckFn(this)"></textarea>
							<span id="topicDescTip"></span>
						</td>
					</tr>
						<s:include value="_sendtomyself.jsp" >
							<s:param name="type">0</s:param>
						</s:include>
					<tr>
						<td></td>
						<td><span id="groupTip" class="redletter"></span></td>
					</tr><tr>
						<td></td>
						<td>
<!-- 							<a class = "button" href="javascript:void(0)" onclick="checkBeforeSendFn('topicDesc','toMySelf','selectedIdList')" -->
<!-- 								id="submit_articleForm">发布</a> -->
							<input type="button" value="发布" class="button bottommargin_5 topmargin_5" onclick="checkBeforeSendFn('topicDesc','toMySelf','selectedIdList',this)"/>
							<a href="javascript:cancle()" class="blueletter">取消</a>
							&nbsp;&nbsp;<span id="warnTipId" class="redletter"></span>	
						</td>
					</tr></table>
				</form>
			</div>
		</div>
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/upload/upload.js"></script>
	<script>
		/*
		func:对于“发布话题”的输入框的字数验证
		author:qiepei
		*/
		function topicCheckFn(obj){
			var topicValue = $(obj).val();
			var length = topicValue.length;
			if(length <= 140){
				var tip = "您已输入了"+ length +"字，还可输入"+ (140-length) +"字";
				$("#topicDescTip").attr("class","lightgreyletter");
				$("#topicDescTip").html(tip);
				return true;
			}else{
				var tip = "您已输入了"+ length +"字，超出了140的最大字数限制";
				$("#topicDescTip").attr("class","redletter ");
				$("#topicDescTip").html(tip);
				return false;
			}			
		}
		
		function checkBeforeSendFn(topicId,toMySelfId,selectedIdListId,obj){
            //验证话题是否为空
            var topicValue = $( "#"+topicId).val();
            if(topicValue == "" || !topicValue){
                 var tip = "发布的话题不能为空" ;
                $( "#topicDescTip").attr( "class", "redletter");
                $( "#topicDescTip").html(tip);
                 return false;
           }
           
            var toMySelf = $("input[name="+toMySelfId+"]:checked" ).val();
            if(toMySelf == 'self1'){
                 var type = 0;
                 var description = $( "#topicDesc").val();
                 if(description.length > 26){
                       var name = description.substr(0,25);
                      name += "...";
                } else{
                       var name = description;
                }
                
                $(obj).val('...').toggleClass( "button graybutton");
                $(obj).attr('disabled',"true");                     
                showLoading();
                
                $.post( 'upload/publishtopic',{ 'type':type, 'toMySelf':toMySelf, 'name':name, 'description':description}, function(data){
                       if(data == undefined){
                      alert( "话题发布失败" );
                } else{
                     location.href= "item?tips="+encodeURIComponent( "话题发布成功" )+"&tipstatus=success&id=" +data.id;                          
                }
                      $(obj).val('发布').toggleClass("button graybutton");
                      $(obj).removeAttr("disabled");
                });
           } else{
                 var selectedIdList = $("#"+selectedIdListId).val();
                 if(selectedIdList == "" || !selectedIdList){
                       var tip = "必须至少选择一个群组" ;
                      $( "#groupTip").html(tip);
                       return false;
                } else{
                       var toMySelf = "self0";
                       var writegroups = selectedIdList;
                       var type = 0;
                       var description = $("#topicDesc").val();
                       if(description.length > 26){
                            var name = description.substr(0,25);
                           name += "...";
                      } else{
                            var name = description;
                      }
                      
                      $(obj).val('...').toggleClass( "button graybutton");
                   	  $(obj).attr( 'disabled', "true");
                   	  showLoading();
                      
                     $.post( 'upload/publishtopic',{ 'type':type, 'toMySelf':toMySelf, 'name':name, 'description':description, 'writegroups':writegroups}, function(data){
                            if(data == undefined){
                           alert( "话题发布失败" );
                      } else{                               
                           location.href= "item?tips="+encodeURIComponent( "话题发布成功" )+"&tipstatus=success&id=" +data.id;                          
                      }
                      $(obj).val('发布').toggleClass("button graybutton");
                      $(obj).removeAttr("disabled");
                      });
                }
           }
      }

		userAutoTips({id:'topicDesc'});
	</script>
</body>
</html>