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
					用户邮箱<span class="redletter">*</span> <br />
					<span class="lightgreyletter">少于15个字</span>
				</td>
				<td>
					<input type="text" id="email" name="email" class="fullinput" value="${name}" />
					<span id="nameText" class="redletter"></span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="text" id = "groupId" name="groupId" value="${groupId}" class="hidden"></input>
					<input type="text" id="groupdesc" value="${desc}" class="hidden"></input>
					<input type="text" id="permission"  value="${permission}" class="hidden"></input>
					<br/> 
					<input class="graybutton" type="button" value="添加" onclick=submitAddUser()></input>
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
	<script src="/js/group/md5.js"></script>
	<script>

	function submitAddUser()
	{
		// var passwordMd5 = hex_md5($("#pwd").val()); 
		 var passwordMd5 = $("#pwd").val();
		 alert(passwordMd5);
		 $.ajax({
		 		type:"post", 
		 		url:"group/addnewcompanyuser", 
		 		data:{
		 			email:$("#email").val(),
		 			pwdMD5:passwordMd5,
		 		},
		 		success:function(data){
		 			jQuery(document).trigger('close.facebox');
		 			location.reload();		     		
		 		}
		 	});		    
	}
	/* function hex_md5(s){
	    return binl2hex(core_md5(str2binl(s), s.length * chrsz));
	}  */
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
	

