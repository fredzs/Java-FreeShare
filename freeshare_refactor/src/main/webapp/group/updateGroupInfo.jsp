<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<div>
		<br/>
		<form id="updateGroup" name="newgroup" action="group/updategroupinfo">
			<table class="formtable" >
			<tr>
				<td >
					群组名称<span class="redletter">*</span> <br />
					<span class="lightgreyletter">少于20个字</span>
				</td>
				<td>
					<input type="text" id="name" name="name" class="fullinput" value="${name}" />
					<span id="nameText" class="redletter"></span>
				</td>
			</tr>
			<tr>
				<td class="topveralign">
					群组描述<span class="redletter">*</span> <br />
					<span class="lightgreyletter">少于250个字</span>
				</td>
				<td>
					<textarea id="desc" cols="30" rows="5" class="fullarea blue1border"
						name="desc" ></textarea>
					<!-- input type="text" id="desc" name="desc" value="${desc}" />	 -->	
					<span id="descText" class="redletter"></span>
				</td>
			</tr>
			<tr>
							<td><br />群组类型<span class="redletter">*</span></td>
							<td><br />
							 <input type="radio" id="private" name="groupPermission" value="private" 
							 />私有（仅组内人员可见，无法被搜索，需要群组管理员审核方可加入）
						
			</tr>
			<tr>
							<td></td>
							<td>
							
							<input type="radio"  id="normal" name="groupPermission"
								value="normal" />普通（注册用户可见，可以被搜索，需要群组管理员审核方可加入）
							
			</tr>
			<tr>
							<td></td>
							<td>
							<input type="radio" id="public" name="groupPermission"
								value="public" />公开（所有用户可见，可以被搜索，无需群组管理员审核即可加入）</td>
			</tr>
			
			<tr>
				<td></td>
				<td><input type="text" id = "groupId" name="groupId" value="${groupId}" class="hidden"></input>
					<input type="text" id="groupdesc" value="${desc}" class="hidden"></input>
					<input type="text" id="permission"  value="${permission}" class="hidden"></input>
					<br/> 
					<input class="graybutton" type="button" value="提交" 
					onclick="updateGroup()"></input>
				</td>
			</tr>
			</table>
		</form>
		<br />
	</div>
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/group/group.js"></script>
	<script src="js/group/checkinput.js"></script>
	<script>
	$(function(){
		document.getElementById("desc").innerHTML = document.getElementById("groupdesc").value;
		var tmp = document.getElementById("permission");
		
		if(tmp != null){
			
			if(tmp.value.match("private") != null){
				$("#private").attr("checked",'checked');
			} 
			else if(tmp.value.match("public") != null){
				$("#public").attr("checked",'checked');
			} 
			else if(tmp.value.match("normal") != null){
				$("#normal").attr("checked",'checked');
			} 
		}
	});
	</script>
	

