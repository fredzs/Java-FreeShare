/*
 对应的jsp文件是：
group/apply_in.jsp
group/invitemember.jsp
*/
/*
 * to check whether the email box input is legal.
 * */

$(document).ready(function(){
		 $("#mailGroupId").val($("#groupId").val());
		 $("#emailBox #email").keyup(function(){
		        if($("#emailBox #email").val().length==49){
		            $("#text").text("最多输入40个字符!");
		        }
		        else if($("#emailBox #email").val().length==0){
		               $("#text").text("邮箱不得为空!"); 
		        }
		        else{
		            $("#text").text("");
		        }
		    });
		    
		 $("#emailBox #email").blur(function(){
		        if($("#emailBox #email").val().length==0){
		               $("#text").text("邮箱不得为空!"); 
		        }
		        else{
		            $("#text").text("");
		        }
		  });
	

	    $("#emailForm").click(function(){
	        if(($("#emailBox #email").val().length)>0&&$("#emailBox #email").val().match("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+")){
	        	
	        	return true;
	        }
	        else{
	             if(($("#emailBox #email").val().length==0))
	                $("#emailBox #text").text("邮箱不得为空!");
	             
	            if(!$("#emailBox #email").val().match("\\w+([\\.\\w_-]+)*@\\w+([\\.\\w_-]+)*"))
	            	 $("#emailBox #text").text("邮箱格式不正确!");
	             return false;
	        }
	    });
	    $("#applyForm #apply").click(function(){
	    	
	    	if($("#applyForm #desc").val().length>0 &&$("applyForm #desc").val().length<100){
	    		document.forms["#applyForm"].submit();
	    		$(document).trigger('close.facebox');
	    		return true;
	    	}
	    	else{
	    		if($("#applyForm #desc").val().length== 0){
	    			 $("#applyForm #text").text("申请原因不得为空!");
	    		}
	    		else{
	    			 $("#applyForm #text").text("申请原因不得超过50个字!");
	    		}
	    		return false;
	    	}
	    	
	    });
	  
	});
/*
 * to write email content to the selectbox of its father page.
 * */
	function submitEmail(){
		var str= $("#emailBox #email").val();
		
		str = str.replace(/[\uff00-\uffff]/g,",");  //替换全角
		var emails = new Array();

		emails = str.split(",");
		
		var addList=document.getElementById("resultTableId").innerHTML;
		//验证Mail的正则表达式,^[a-zA-Z0-9_-]:开头必须为字母,下划线,数字
		var regm = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
		for(var j=0;j < emails.length;j++){
			if(emails[j].match(regm)){
				addList += "<tr><td>"+emails[j]+"</td><td><a class='blueletter' name='emails[i]' onclick='delItemUserFn(this)'>删除</a></td></tr>";
			}
			else{
				alert("email:"+emails[j]+"不合法");
			}
	     }
		
		var index = emails.length;
		var selectedNum = document.getElementById("selectedNumId").innerHTML - '0';
		document.getElementById("selectedNumId").innerHTML = selectedNum + index; 
		
		$("#resultTableId").html(addList);
		var mytable = document.getElementById("selectTable"); 
		var rows = mytable.getElementsByTagName("input");
		
		for(var i=0; i<rows.length; i++) { 
		
		}
		var tmp= $("#selectedList").val();
		$("#selectedList").val(tmp+str+",");
		
		selectList +=$("#selectedList").val();
		$(document).trigger('close.facebox');

	} 
  