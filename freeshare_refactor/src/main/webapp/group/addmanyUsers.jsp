<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>选择群组动态</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div class="divline">
	企业内所有成员：
	</div>
	
	<div class="greybg grey1border padding10 topmargin_5">
		<div id="groupslist">
			<s:iterator id="doc" value="userlist" status="st">
				<div class="half padding5">
					<input type="checkbox" checked="true" id="<s:property value="#doc.uid" />" value="<s:property value="#doc.uid" />" name="groupfilter" />
					<label for="<s:property value="#doc.id" />"><s:property value="#doc.email" />(<s:property value="#doc.screen_name" />)</label>
				</div>
			</s:iterator>
		</div>
		<div class="clear"></div>
	</div>
	<div class="padding5 leftalign">

		<div class="rightfloat">
			<input type="button" class="button"  onclick="submit()" value="确定">
		</div>
		<div class="clear"></div>
		<input id="groupId" value="<s:property value="groupId"/>" type="hidden">
	</div>

 <script>
		function submit(){
			/* var check = $('#groupslist').find("input[type='checkbox']").attr('checked', "checked");
			var groupArray = check.split(','); */
			var a = document.getElementsByName("groupfilter");
			var userIds = "";
			for(var i = 0 ;i<a.length;i++)
			{
			//	alert(a[i].value);
				 if(a[i].checked){	
					userIds = userIds + a[i].value + ",";
				 }
			}
			userIds = userIds.substr(0,userIds.length-1);
			alert(userIds);
			$.ajax({
		 		type:"post", 
		 		url:"group/addmanyusers", 
		 		data:{
		 			userIds:userIds,
		 			groupId:$('#groupId').val(),
		 		},
		 		success:function(data){
/* 		 			
		 			location.reload(); */
					alert($('#groupId').val());	 
					jQuery(document).trigger('close.facebox');
		 		}
		 	});		    
			
			
		}
	</script>
</body>
</html>