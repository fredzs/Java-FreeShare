/*
 对应的jsp文件是：
group/_mygroup.jsp
group/apply_in.jsp
group/group_lists.jsp
group/group_resource.jsp
group/groups.jsp
group/invitation.jsp
group/invitemember.jsp
group/mygroup.jsp
*/
/*$(document).ready(function(){
	if(location.href.indexOf("?") != -1){
		var myquery = location.href.split("?")[1];
		var queryArray = myquery.split("&");
		var queryMap = new Object();
		for(var i=0; i < queryArray.length; i++){
			var aquery = queryArray[i].split("=");
			queryMap[aquery[0]] = aquery[1];
		}
		if( "tipstatus" in queryMap){
			var status = queryMap["tipstatus"];//0-success;1-warning;2-error
			var content = decodeURIComponent(queryMap["content"]);
			var pngName="";
			switch(status){
				case '0':
					pngName = "success.png";
					break;
				case '1':
					pngName = "warning.png";
					break;
				case '2':
					pngName = "error.png";
					break;
			}
			var  tipContent = "<table><tr><td><img src='images/"+pngName+"'/></td><td>&nbsp;"+ content +"&nbsp;</td></tr></table>";
			 
			$("#tipboxWrapper").html("<div  id=\"tipbox\"  class=\"mytipboxposition\"></div>");
			$("#tipbox").tipbox  ({
			         content:tipContent,
			         autoclose:true,
			         hasclose:false
			});
		}
	}				
});*/


function selectall() {
	$("input[type='checkbox']").attr("checked", 'true');
	
}
function selectnone() {
	$("input[type='checkbox']").removeAttr("checked");
}

var groupManager = {
		groupId:$("#groupId").val(),
		url:null,
		//operationList:null,
		init:function(){
			this.groupId = $("#groupId").val();
		},
		_getCheckboxList:function(){
			var uid = "";
			$("input[type='checkbox']").each(function() {
				if ($(this).attr("checked")) {
					//下面的这句是为了防止全选框的。
					if($(this).attr("name")!=undefined){
						uid += $(this).attr("value") + ",";
					}
				}
			});
			return uid;
		},
		//为管理员或者是创建者的特殊的处理。TODO：看看能不能进行合并。
		_getCheckboxList2:function(){
			var uid = "";
			$("input[type='checkbox']").each(function() {
				if ($(this).attr("checked")) {
					//下面的这句是为了防止全选框的。
					if($(this).attr("name")!=undefined){
						uid += $(this).attr("value") +":"+$(this).attr("name")+ ",";
					}
				}
			});
			return uid;
		},
		//成功的时候的提示。
		successTip:function(){
			var query = "tipstatus=0&content="+encodeURIComponent("操作成功！");
			if(location.href.indexOf("?") != -1){
				var newUrl = location.href+"&"+query;
			}else{
				var newUrl = location.href+"?"+query;
			}
			//alert(newUrl);
			location.replace(newUrl);
		},
		//失败的时候提示。
		errorTip:function(){
			var  tipContent = "<table><tr><td><img src='images/error.png'/></td><td>&nbsp;对不起，操作失败，请刷新重试！&nbsp;</td></tr></table>";
			 
			$("#tipboxWrapper").html("<div  id=\"tipbox\"  class=\"mytipboxposition\"></div>");
			$("#tipbox").tipbox  ({
			         content:tipContent,
			         autoclose:true,
			         hasclose:false
			});
		},
		//type == "agree"用户同意邀请，或是公开的组，用户点击同意加入。
		//type == "ignore"用户忽略邀请。
		agreeOrIngore:function(uid,groupId,type){
			if(type == "agree"){
				url = 'members/agree';
			}
			else if(type == "ignore"){
				url = 'members/ignore';
			}
			else{
				alert("type 类型不能为空！");
			}
			if (groupId != "") {
				$.ajax({
					type : 'POST',
					url : url,
					data : {
						uid:uid,
						groupId : groupId
						
					},
					success:function(){
			 			groupManager.successTip();
			 		},
			 		error : function() {
			 			groupManager.errorTip();
					}
				});
			}
		},
		
		//从群组中移除用户。
		deleteMem:function(){
			this.init();
			userId= this._getCheckboxList();
			if(userId =="")
			{
				alert("请至少选中一个用户");
			}
			else
			{
				
				if(confirm("确认要删除吗？")){
					$.ajax({
						type : 'POST',
						url : 'members/deletemembers',
						data : {
							uid : userId,
							groupId : this.groupId
						},
						success:function(){
							groupManager.successTip();
						},
						error : function() {
							groupManager.errorTip();
						}
					});
				}
			}
			
		},
		//同意或者是忽略申请的用户。
		agreeOrIgnoreApply:function(type){
			this.init();
			if(type == "agree"){
				url = 'members/applyadd';
				userId = this._getCheckboxList2();
			}
			else if(type == "ignore"){
				url = 'members/adminIgnore';
				userId = this._getCheckboxList();
			}else{
				alert("type 类型不能为空！");
				return;
			}
			if(userId == ''){
				alert("请选择用户！")
				return;
			}
			$.ajax({
				type : 'POST',
				url : url,
				data : {
					uid : userId,
					groupId : this.groupId
				},
				success : function() {
					showLoading();
					location.reload();
				},
				error : function() {
					groupManager.errorTip();
				}
			});
			
		},
		//type == "agree",根据用户的id，设置该用户为群组管理员。
		//type == "cancle"根据用户的id，取消该用户的管理员身份。
		setOrRemoveAdmin:function(userId,type){
			this.init();
			//alert(url);
			if(type == "agree"){
				confirminfo = "确认设为管理员吗？";
				url = 'members/setadmin';
			}
			else if(type == "cancle"){
				confirminfo = "确认取消管理员吗？";
				url = 'members/removeadmin';
			}
			else{
					alert("type 类型不能为空！");
			}
			if(confirm(confirminfo)){
				userId = userId+",";
				$.ajax({
					type : 'POST',
					url : url,
					data : {
						uid : userId,
						groupId : this.groupId
					},
					success:function(){
			 			groupManager.successTip();
			 		},
			 		error : function() {
			 			groupManager.errorTip();
					}
		         });
					
			}
			
		},
		
		//被邀请的成员页面中的删除被邀请成员信息。
		deleteInviteMemInfo:function(){
			this.init();
			userId= this._getCheckboxList2();
			if (userId != "") 
			{
				$.ajax({
			 		type:"post", 
			 		url:"members/deleteinvitemem", 
			 		data:{
			 			groupId:this.groupId,
			 			email:this._getCheckboxList2()
			 		},
			 		success:function(){
			 			groupManager.successTip();
			 		},
			 		error : function() {
			 			groupManager.errorTip();
					}
		         });
			} 
			else 
			{
				alert("请选择至少一个用户，请重试！");
			}
		},
		//被邀请的成员页面中的再次发出邀请。
		sendInviteAgain:function(){
			this.init();
			userId= this._getCheckboxList2();
			if (userId != "") 
			{
				$.ajax({
			 		type:"post", 
			 		url:"members/sendinviteagain", 
			 		data:{
			 			groupId:this.groupId,
			 			selectedList:this._getCheckboxList2()
			 		},
			 		success : function(){
			 			groupManager.successTip();
			 		},
			 		error : function() {
			 			groupManager.errorTip();
					}	
		         });
			} 
			else 
			{
				alert("请选择至少一个用户，请重试！");
			}
		},
		//TODO：改成通用的邀请成员。
		groupOperator:function(){
			this.init();
			$.ajax({
		 		type:"post", 
		 		url:this.url, 
		 		data:{
		 			groupId:this.groupId,
		 			operationList:this._getCheckboxList()
		 		},
		 		success:function(){
		 			groupManager.successTip();		     		
		 		},
		 		error : function() {
		 			groupManager.errorTip();
				}
	         });
		}
}
//申请加入群组
function submitApply(){
	if($("#desc").val().length == 0){
		$("#text").html("申请理由不能为空");
	}
	else if($("#desc").val().length >= 100){
		$("#text").html("申请理由不能超过50个字");
	}
	else{
		$(document).trigger('close.facebox');
		showLoading();
		var applyReason = $("#desc").val();
		var groupId = $("#groupId").val();
		var groupName = $("#groupName").val();
		$.ajax({
	
			type : 'POST',
			url : 'members/apply',
			data : {
				applyReason : applyReason,
				groupId : groupId,
				groupName : groupName
			},
			success : function() {
				jQuery(document).trigger('close.facebox');
				alert("申请成功！");
				hideLoading();
			}
		});
	}
}

//成员退出群组
function quit(uid, groupId) {

	if(confirm('确认退出?') == true){
		$.ajax({
			type : 'POST',
			url : 'members/quit',
			data : {
				uid : uid,
				groupId : groupId
			},
			success : function() {
				//alert("已经退出该群组！");
				gotonext("groups?tipstatus=4");
			}
		});
	}
}
