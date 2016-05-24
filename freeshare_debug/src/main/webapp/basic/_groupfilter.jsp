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
	从所有群组中选择：
	</div>
	
	<div class="greybg grey1border padding10 topmargin_5">
		<div class="padding5">
			<input type="checkbox" id="selallgroups" onclick="selectAllGroups(this)">
			<label for="selallgroups">全选</label>
		</div>
		<div id="groupslist">
			<s:iterator id="doc" value="lists" status="st">
				<div class="half padding5">
					<input type="checkbox" checked="true" id="<s:property value="#doc.id" />" value="<s:property value="#doc.name" />" name="groupfilter" />
					<label for="<s:property value="#doc.id" />"><s:property value="#doc.name" /></label>
				</div>
			</s:iterator>
		</div>
		<div class="clear"></div>
	</div>
	<div class="padding5 leftalign">
		<input type="checkbox" checked="false" id="remembergroup" value="remembergroup" />
		<label for="remembergroup">下次访问时记住以上选择</label>
		<div class="rightfloat">
			<input type="button" class="button" onclick="filtByGroup()" value="确定">
			<a href="javascript:closeFacebox()" >取消</a>
		</div>
		<div class="clear"></div>
	</div>
	<script>
		var userId = $("#hiddenuid").val();
		if(groupList != ''){
			$('#groupslist').find("input[type='checkbox']").attr('checked', false);
			var groupArray = groupList.split(',');
			for(var i = 0; i < groupArray.length; i++){
				$('#'+groupArray[i]).attr('checked', 'checked');
			}
			if(groupArray.length == $('#groupslist').find("input[type='checkbox']").length){
				$('#selallgroups').attr('checked', 'checked');
			}
		}else if(nGetCookie(userId+'groupsfilter') != null && nGetCookie(userId+'groupsfilter') != 'null'){
			$('#groupslist').find("input[type='checkbox']").attr('checked', false);
			var groupArray = nGetCookie(userId+'groupsfilter').split(',');
			for(var i = 0; i < groupArray.length; i++){
				$('#'+groupArray[i]).attr('checked', 'checked');
			}
			if(groupArray.length == $('#groupslist').find("input[type='checkbox']").length){
				$('#selallgroups').attr('checked', 'checked');
			}
		}else{
			$('#selallgroups').attr('checked', 'checked');
		}
		$('#groupslist').find("input[type='checkbox']").click(function(){
			if($(this).attr("checked") != 'checked'){
				$("#selallgroups").attr("checked", false);
			}else {
				var selectNum = 0;
				$('#groupslist').find("input[type='checkbox']").each(function(){
					if($(this).attr('checked') == 'checked'){
						selectNum ++;
					}
				})
				if(selectNum == $('#groupslist').find("input[type='checkbox']").length){
					$("#selallgroups").attr("checked", "checked");
				}
			}
		})
	</script>
</body>
</html>