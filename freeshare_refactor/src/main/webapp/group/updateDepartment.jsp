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
							 <input type="radio" id="private" name="groupPermission" value="<s:property value="permission"/>" checked="checked"
							 /> 部门（仅部门内人员查看，其他用户无法搜索）
						
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
	
	<script	src="http://front.free4lab.com/js/public.js"></script>
	<s:include value="/basic/_globlejs.jsp" />
	<script src="/js/group/group.js"></script>
	<script src="/js/group/checkinput.js"></script>
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
	
	function updateGroup(){
		//清空提示
		alert("I am in updateGroup");
		$("#nameText").text("");
		$("#descText").text("");
		//群组名称和群组描述字数符合要求的情况
		 if(($("#updateGroup #name").val().length)>0&&($("#updateGroup #desc").val().length)>0&&($("#updateGroup #name").val().length)<=20&&($("#updateGroup #desc").val().length)<=250){
		      	var str = $("#updateGroup #name").val();
		      	
		      	var reTag=/\&[a-zA-Z]{1,10};/g;
			    var reTag1 = /<(?:.|\s)*?>/g;
			    str=str.replace(reTag,"");
			    str=str.replace(reTag1,"");
			     $("#updateGroup #name").val(str);
			     var checkValue = "";
			     //var table = $("#updateGroup");
			     $("#updateGroup").find("input[type='radio']").each(function(){				
						if(this.checked == true){
							checkValue = this.value;
						}
									
				});
			     $.ajax({
			 		type:"post", 
			 		url:"group/updategroupinfo", 
			 		data:{
			 			groupId:$("#groupId").val(),
			 			name:$("#name").val(),
			 			desc:$("#desc").val(),
			 			permission:checkValue
			 			
			 		},
			 		success:function(dat){
			 			jQuery(document).trigger('close.facebox');
			 			location.reload();
			     		
			 		}
			 	});		    
			}
			//群组名称和群组描述字数不符合要求的情况
			else{
			  	if (($("#updateGroup #name").val().length <= 0)) {
					$("#nameText").text("名称不得为空！");
				}
				else if ($("#updateGroup #name").val().length >= 21) {
					$("#nameText").text("最多输入20个字符！");
				}
				if (($("#updateGroup #desc").val().length <= 0)) {
					$("#descText").text("描述不得为空！");
				}
				else if ($("#updateGroup #desc").val().length >= 251) {
					$("#descText").text("最多输入250个字符！");
	    		}
	    		return false;
	     	}   
	}
	</script>
	

