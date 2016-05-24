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
	企业内所有部门：
	</div>
	
	<div class="greybg grey1border padding10 topmargin_5">
		<div id="groupslist">
			<s:iterator id="doc" value="groupList" status="st">
				<div class="half padding5">
					<input type="checkbox" checked="true" id="<s:property value="#doc.groupId" />" value="<s:property value="#doc.groupId" />" name="groupfilter" />
					<label for="<s:property value="#doc.groupId" />"><s:property value="#doc.groupInfo.name" /></label>
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
		<input id="userId" value="<s:property value="uid"/>" type="hidden">
	</div>

 <script>
		function submit(){
			/* var check = $('#groupslist').find("input[type='checkbox']").attr('checked', "checked");
			var groupArray = check.split(','); */
			var a = document.getElementsByName("groupfilter");
			var groupIds = "";
			for(var i = 0 ;i<a.length;i++)
			{
			//	alert(a[i].value);
				 if(a[i].checked){	
					groupIds = groupIds + a[i].value + ",";
				 }
			}
			groupIds = groupIds.substr(0,groupIds.length-1);
			alert(groupIds);
			$.ajax({
		 		type:"post", 
		 		url:"group/addtomanygroups", 
		 		data:{
		 			groupIds2:groupIds,
		 			uid:$('#userId').val(),
		 		},
		 		success:function(data){
		 			
		 			/*location.reload(); */
					alert($('#userId').val());	 
					jQuery(document).trigger('close.facebox');
		 		}
		 	});		    
			
			
		}
	</script>
</body>
</html>