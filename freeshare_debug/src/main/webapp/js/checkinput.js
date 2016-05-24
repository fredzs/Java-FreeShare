/*
 * 从group.js抽离处理，增加通用性
*/
$(document).ready(function(){
	
	//创建群组，群组名称检查
	$("#create #name").keyup(function(){
        if($("#create #name").val().length>=21){
            $("#nameText").text(" 最多输入20个字符！");
        }
        else if($("#create #name").val().length==0){
               $("#nameText").text(" 名称不得为空！"); 
        }
        else{
            $("#nameText").text("");
        }
    });
	 $("#create #name").blur(function(){
	 	if($("#create #name").val().length>=21){
            $("#nameText").text(" 最多输入20个字符！");
        }
        else if($("#create #name").val().length==0){
               $("#nameText").text(" 名称不得为空！"); 
        }
        else{
            $("#nameText").text("");
        }
	    });
	 //创建群组，群组描述检查  
	 $("#create #desc").keyup(function(){
	        if($("#create #desc").val().length>=251){
	            $("#descText").text(" 最多输入250个字符！");
	        }
	        else if($("#create #desc").val().length==0){
	               $("#descText").text(" 描述不得为空！"); 
	        }
	        else{
	            $("#descText").text("");
	        }
	  }); 
	  $("#create #desc").blur(function(){
	  		if($("#create #desc").val().length>=251){
	            $("#descText").text(" 最多输入250个字符！");
	        }
	        else if($("#create #desc").val().length==0){
	               $("#descText").text(" 描述不得为空！"); 
	        }
	        else{
	            $("#descText").text("");
	        }
	 });
	 
	 //修改群组，群组名称检查
	 $("#updateGroup #name").keyup(function(){
        if($("#updateGroup #name").val().length>=21){
            $("#nameText").text(" 最多输入20个字符！");
        }
        else if($("#updateGroup #name").val().length==0){
               $("#nameText").text(" 名称不得为空！"); 
        }
        else{
            $("#nameText").text("");
        }
    	});
	 $("#updateGroup #name").blur(function(){
	 	if($("#updateGroup #name").val().length>=21){
            $("#nameText").text(" 最多输入20个字符！");
        }
        else if($("#updateGroup #name").val().length==0){
               $("#nameText").text(" 名称不得为空！"); 
        }
        else{
            $("#nameText").text("");
        }
	    });
	 
	 //修改群组，群组描述检查  
	 $("#updateGroup #desc").keyup(function(){
	        if($("#updateGroup #desc").val().length>=251){
	            $("#descText").text(" 最多输入250个字符！");
	        }
	        else if($("#updateGroup #desc").val().length==0){
	               $("#descText").text(" 描述不得为空！"); 
	        }
	        else{
	            $("#descText").text("");
	        }
	  }); 
	  $("#updateGroup #desc").blur(function(){
	  		if($("#updateGroup #desc").val().length>=251){
	            $("#descText").text(" 最多输入250个字符！");
	        }
	        else if($("#updateGroup #desc").val().length==0){
	               $("#descText").text(" 描述不得为空！"); 
	        }
	        else{
	            $("#descText").text("");
	        }
	 });
	 
	 /* $("#create #addmem").click(function(){
		  if(($("#create #name").val().length)>0&&($("#create #desc").val().length)>0){
			 if($("#create #name").val().length<=20&&$("#create #desc").val().length<=50){
		           
		     
		      	var str = $("#create #name").val();
		      	var reTag=/\&[a-zA-Z]{1,10};/g;
			    var reTag1 = /<(?:.|\s)*?>/g;
			    str=str.replace(reTag,"");
			    str=str.replace(reTag1,"");
			     $("#create #name").val(str);
			     return true;
			 }
			
		  }
		  else{
		           if(($("#create #name").val().length==0))
		              $("#text1").text("群组名不得为空！");
		           if(($("#create #desc").val().length==0))
			          $("#text2").text("群组描述不得为空！");
		           return false;
		  }
	  });*/
	
});
/*
 * check whether the input is legal
 */
function check(){
	  /*if(($("#create #name").val().length)>0&&($("#create #desc").val().length)>0){
		  if($("#create #name").val().length<=20&&$("#create #desc").val().length<=250){
	      	var str = $("#create #name").val();
	      	
	      	var reTag=/\&[a-zA-Z]{1,10};/g;
		    var reTag1 = /<(?:.|\s)*?>/g;
		    str=str.replace(reTag,"");
		    str=str.replace(reTag1,"");
		     $("#create #name").val(str);
		     var checkValue ="";
		     $("#create").find("input[type='radio']").each(function(){				
		 		if(this.checked == true){
		 			checkValue = this.value;
		 		}
		 					
		      });
		 	$("#permission").val(checkValue);
		     document.forms["create"].submit();
		  }
      }*/
		if(($("#create #name").val().length)>0&&($("#create #desc").val().length)>0&&($("#create #name").val().length)<=20&&($("#create #desc").val().length)<=250){
	      	var str = $("#create #name").val();	      	
	      	var reTag=/\&[a-zA-Z]{1,10};/g;
		    var reTag1 = /<(?:.|\s)*?>/g;
		    str=str.replace(reTag,"");
		    str=str.replace(reTag1,"");
		     $("#create #name").val(str);
		     var checkValue ="";
		     $("#create").find("input[type='radio']").each(function(){				
		 		if(this.checked == true){
		 			checkValue = this.value;
		 		}
		 					
		      });
		 	$("#permission").val(checkValue);
		     document.forms["create"].submit();
		  }
      	else{			
			if (($("#create #name").val().length <= 0)) {
				$("#nameText").text("名称不得为空！");
			}
			else if ($("#create #name").val().length >= 21) {
				$("#nameText").text("最多输入20个字符！");
			}
			if (($("#create #desc").val().length <= 0)) {
				$("#descText").text("描述不得为空！");
			}
			else if ($("#create #desc").val().length >= 251) {
				$("#descText").text("最多输入250个字符！");
    		}
    		return false;
      }
		
}