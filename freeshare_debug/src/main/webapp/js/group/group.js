/*
 对应的jsp文件是：
group/_mygroup.jsp
group/_readgroup.jsp
group/_searchgroup.jsp
group/_writegroup.jsp
group/creategroup.jsp
group/invitemember.jsp
group/mygroup.jsp
group/updateGroupInfo.jsp
*/
/*create a group to check whether the input is legal */
function preinvite(){
	 if(($("#create #name").val().length==0)){
         $("#nameText").text("名称不得为空！");
         return false;
	 }
	 if($("#create #name").val().length>=21){
         $("#nameText").text("最多输入20个字符！");
         return false;
     }
     if(($("#create #desc").val().length==0)){
         $("#descText").text("描述不得为空！");
         return false;
      }
     if($("#create #desc").val().length>=251){
         $("#descText").text("最多输入250个字符！");
         return false;
      }
    var checkValue ="";
    $("#create").find("input[type='radio']").each(function(){				
		if(this.checked == true){
			checkValue = this.value;
		}
					
     });
     
     showLoading();
	$("#permission").val(checkValue);
	$("#create").attr("action","members/createinvite");
	
	document.forms["create"].submit();
}

function updateGroup(){
	//清空提示
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
//返回群组页面。
function backToGroup(){
	var currentURL = window.location.toString();  
	var store = [];
	 store = currentURL.split("/");
	 currentURL = "";
	 for(var i = 0; i < store.length -2;i++){
		 currentURL += store[i]+"/";
	 }
	currentURL += "groups";
	window.location = currentURL;
	
}


