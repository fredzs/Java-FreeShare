//转移到group_administration中的administration.js中	
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
					groupName : groupName,
				},
				success : function() {
					jQuery(document).trigger('close.facebox');
					alert("申请成功！");
					hideLoading();
				}
			});
		}
	}