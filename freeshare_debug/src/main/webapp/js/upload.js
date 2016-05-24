$(document).ready(function(){
	//设置“仅自己可见”为否by qiepei
	if( $("#toMySelfWrapperId").val() != undefined){ //编辑资源的页面
		var toMySelf = $("#toMySelfWrapperId").val();
		if(toMySelf == "self1"){//仅自己可见
			$("#selectMySelf1").attr("checked","checked");			
		}else{
			$("#selectMySelf0").attr("checked","checked");
		}
		selectMySelfFn("toMySelf");
	}else{
		$("#selectMySelf0").attr("checked","checked");
		//selectMySelfFn("toMySelf");
	}
	
	// 发布资源，资源名称检查
    $("#docTable #name").keyup(function(){
        if($("#docTable #name").val().length>=40){
            $("#text1").text("最多输入40个字符！");
            fillTipBox('error',"最多输入40个字符！");
        }else if($("#docTable #name").val().length==0){
               $("#text1").text("资源名称ddddd不得为空！"); 
               fillTipBox('error',"资源名ddddd称不得为空！");
        }else{
            $("#text1").text("dfsda");
        }
    });
    
    $("#docTable #name").blur(function(){
    	if($("#docTable #name").val().length>=40){
            $("#text1").text("最多输入40个字符！");
            fillTipBox('error',"最多输入40个字符！");
        }else if($("#docTable #name").val().length==0){
               $("#text1").text("资源名称555不得为空！"); 
               fillTipBox('error',"资源名称不得为空！");
        }else{
            $("#text1").text("的范德萨");
        }
    });
    
    /*
     * 发布资源的提交函数
     * 
     * /
   
/*$("#submit_doc").click(function(){

	
    	$("#submit_doc").val('...').toggleClass("button graybutton");
    	$("#submit_doc").attr('disabled', "true");
    	//提交前的判断是否合法的函数
    	var status = checkBeforeUpload();
    	if(!status){
    		//this.disabled = false;
    		$("#submit_doc").attr('disabled', "false");
    		$("#submit_doc").val('发布').toggleClass("graybutton button");
    		return false;
    	}
    	//以下是提交到后端的部分
    	var name = trim($("#name").val());
    	var docUuid = $("#docUuid").val();
    	var docdesc = trim( editor.getContent() );
    	var writegroups = $("#selectedIdList").val();
    	var extension = $("#extension").val();
    	var type = $("#preType").val();
    	var url = $("#linkId").val();
    	var toMySelf = $("input[name=toMySelf]:checked").val();
    	var enclosure = "";
    	if( type == '1'){
    		enclosure = docUuid;
    	}else if(type == '2' || type == '3' ){
    		enclosure = url;
    	}	
    	var labels = $("#addresourcetags").val().split(' ').join(',');
    	var newLabels = $("#newtags").html().split(' ').join(',');
    	
    	if(labels.substring(labels.length-1) == ','){
    		labels = labels.substring(0, labels.length-2);
    	}
    	if(newLabels.substring(newLabels.length-1) == ','){
    		newLabels = newLabels.substring(0, newLabels.length-2);
    	}
    	
    	$.post("upload/publish",
    			{name:name,
    			description:docdesc,
    			type:type,
    			extension:extension,
    			enclosure:enclosure,
    			toMySelf:toMySelf,
    			labels:labels,
    			newLabels:newLabels,
    			writegroups:writegroups
    			}, 
    			function(data){
    				if(data.status != "success"){
    					fillTipBox('error',"文档上传失败");
    				}else{	        					
    					location.href="item?tips="+encodeURIComponent("发布资源成功")+"&tipstatus=success&id="+data.id;
    				}
    			}
    	);
    
	
  });

*/
    
    
    /*
     * 编辑资源的提交函数
     * */
    $("#submit_updateitem").click(function(){
    	//提交前的判断是否合法的函数
    	var status = checkBeforeUpload();
    	if(!status){
    		return false;
    	}
    	//以下是提交到后端的部分
    	var id = $("#itemId").val();
    	var name = trim($("#name").val());
    	var docUuid = $("#docUuid").val();
    	var docdesc = trim(editor.getContent() );
    	var writegroups = $("#selectedIdList").val();
    	var extension = $("#extension").val();
    	var type = $("#preType").val();
    	var url = $("#linkId").val();
    	var toMySelf = $("input[name=toMySelf]:checked").val();
    	var enclosure = "";
    	if( type == '1'){
    		enclosure = docUuid;
    	}else if(type == '2' || type == '3' ){
    		enclosure = url;
    	}	
    	var labels = $("#addresourcetags").val().split(' ').join(',');
    	var newLabels = $("#newtags").html().split(' ').join(',');
    	
    	if(labels.substring(labels.length-1) == ','){
    		labels = labels.substring(0, labels.length-2);
    	}
    	if(newLabels.substring(newLabels.length-1) == ','){
    		newLabels = newLabels.substring(0, newLabels.length-2);
    	}
    	
    	$.post("upload/publish",
    			{id:id,
    			name:name,
    			description:docdesc,
    			type:type,
    			extension:extension,
    			enclosure:enclosure,
    			toMySelf:toMySelf,
    			labels:labels,
    			newLabels:newLabels,
    			writegroups:writegroups
    			}, 
    			function(data){
    				if(data.status != "success"){
    					fillTipBox('error',"文档上传失败");
    				}else{	        					
    					location.href="item?tipstatus=success&id="+data.id;
    				}
    			}
    	);
    });
    
    // the controler of the listForm
    // 发布列表，列表名称检查
    $("#listForm #name").keyup(function(){
        if($("#listForm #name").val().length >= 40){
            $("#text3").text("最多输入40个字符！");
            fillTipBox('error',"最多输入40个字符！");
        }
        else if($("#listForm #name").val().length==0){
            $("#text3").text("列表名称不得为空！"); 
            fillTipBox('error',"列表名称不得为空！");
        }
        else{
            $("#text3").text("");
        }
    });
    
    $("#listForm #name").blur(function(){
    	if($("#listForm #name").val().length >= 40){
            $("#text3").text("最多输入40个字符！");
            fillTipBox('error',"最多输入40个字符！");
        }
        else if($("#listForm #name").val().length==0){
            $("#text3").text("列表名称不得为空！"); 
            fillTipBox('error',"列表名称不得为空！");
        }
        else{
            $("#text3").text("");
        }
    });
   /*
    $("#listForm #submit_list").click(function(){
    	
    	$("#submit_list").val('...').toggleClass("button graybutton");
    	$("#submit_list").attr('disabled', "true");
    	
    	var groups = $("#selectedIdList").val();
    	var me = $("input[name=toMySelf]:checked").val();
    	if(groups == "" && me == "self0"){
    		$("#text4").text("请选择所属群组或者选择资源仅自己可见！"); 
    		fillTipBox('error',"请选择所属群组或者选择资源仅自己可见！");
    		$("#submit_list").attr('disabled', "false");
    		$("#submit_list").val('发布').toggleClass("graybutton button");
    		return false;
    	}
        if(($("#listForm #name").val().length)>0&&editor.getContent().length-13>0){
        	if(editor.hasContents()){  
        		editor.sync();
        	}
	    	
	    	var name = $("#name").val();
	    	var description = editor.getContent();
	    	var	writegroups = $("#selectedIdList").val();	
	    	var onlyme = $("input[name=toMySelf]:checked").val();
	    	
	    	var labels = $("#addresourcetags").val().split(' ').join(',');
	    	var newLabels = $("#newtags").html().split(' ').join(',');
	    	
	    	if(labels.substring(labels.length-1) == ','){
	    		labels = labels.substring(0, labels.length-2);
	    	}
	    	if(newLabels.substring(newLabels.length-1) == ','){
	    		newLabels = newLabels.substring(0, newLabels.length-2);
	    	}
	    	showLoading();
	    	$.post("upload/publish",
	    			{name:name,
	    			 type:-1,
	    			 description:description,
	    			 writegroups:writegroups,
	    			 labels:labels,
	     			 newLabels:newLabels,
	    			 toMySelf:onlyme
	    			}, function(data){
	    				if(data.status != "success"){
	    					fillTipBox('error',"列表发布失败！");
	    				}else{
	    					location.href=encodeURI("list?tips=发布列表成功&id="+data.id);
	    				}
	    			}
	    	);
        	return true;
        }
        else {
        	if (($("#listForm #name").val().length==0)){
        		$("#text3").text("列表名称不得为空！");
        		fillTipBox('error',"列表名称不得为空！");
        	}        		
        	
        	if (($("#listForm #name").val().length>40)){
        		$("#text3").text("列表名称最多输入40个字符！");
        		fillTipBox('error',"列表名称最多输入40个字符！");
        	}
        		
        	if(editor.getContent().length-13<=0){
        		$("#listText").text("列表描述不能小于6个字符!");
        		fillTipBox('error',"列表描述不能小于6个字符!");
        	}
        		
        	if($("#selectedIdList").val().length<=0 ){
        		$("#groupText").text("请选择归属组");
        		fillTipBox('error',"请选择归属组！");
            }
        	$("#submit_list").attr('disabled', "false");
    		$("#submit_list").val('发布').toggleClass("graybutton button");
        	return false;
        }
    });*/
    
    $("#updateitemForm #name").keyup(function(){
        if($("#updateitemForm #name").val().length >40){        	
            $("#updateitemForm #text").text("最多输入40个字符！");
            fillTipBox('error',"最多输入40个字符！");
        }
        else if($("#updateitemForm #name").val().length==0){        	
            $("#updateitemForm #text").text("资源名称不得为空！"); 
            fillTipBox('error',"资源名称不得为空！");
        }else{
            $("#updateitemForm #text").text("");
        }
    });
    
    $("#updateitemForm #name").blur(function(){
        if($("#updateitemForm #name").val().length==0){
            $("#updateitemForm #text").text("资源名称不得为空！"); 
            fillTipBox('error',"资源名称不得为空！");
        }else{
            $("#updateitemForm #text").text("");
        }
    });

    $("#updatelistForm #name").keyup(function(){
        if($("#updatelistForm #name").val().length > 40){
            $("#updatelistForm #text").text("最多输入40个字符！");
            fillTipBox('error',"最多输入40个字符！");
        }else if($("#updatelistForm #name").val().length==0){
            $("#updatelistForm #text").text("资源名称不得为空！"); 
            fillTipBox('error',"资源名称不得为空！");
        }else{
            $("#updatelistForm #text").text("");
        }
    });
    
    $("#updatelistForm #name").blur(function(){
        if($("#updatelistForm #name").val().length==0){
            $("#updatelistForm #text").text("资源名称不得为空！"); 
            fillTipBox('error',"资源名称不得为空！");
        }
        if($("#updatelistForm #name").val().length > 40){
            $("#updatelistForm #text").text("资源名称最多输入40个字符！"); 
            fillTipBox('error',"资源名称最多输入40个字符！");
        }else{
            $("#updatelistForm #text").text("");
        }
    });
    $("#updatelistForm #description").keyup(function(){
        if($("#updatelistForm #description").val().length > 40){
            $("#updatelistForm #descriptionText").text("最多输入40个字符！");
            fillTipBox('error',"最多输入40个字符！");
        }else if($("#updatelistForm #description").val().length==0){
            $("#updatelistForm #descriptionText").text("新版本描述不得为空！"); 
            fillTipBox('error',"新版本描述不得为空！");
        }else{
            $("#updatelistForm #descriptionText").text("");
        }
    });
    $("#updatelistForm #description").blur(function(){
        if($("#updatelistForm #description").val().length==0){
            $("#updatelistForm #descriptionText").text("资源名称不得为空！"); 
            fillTipBox('error',"资源名称不得为空！");
        }else if($("#updatelistForm #description").val().length >40){
            $("#updatelistForm #descriptionText").text("资源名称最多输入40个字符！"); 
            fillTipBox('error',"资源名称最多输入40个字符！");
        }else{
            $("#updatelistForm #descriptionText").text("");
        }
    });
    
});

function submitDoc(){
	//alert("doc");
	$("#submit_doc").val('...').toggleClass("button graybutton");
	$("#submit_doc").attr('disabled', "true");
	//提交前的判断是否合法的函数
	var status = checkBeforeUpload();
	if(!status){
		//this.disabled = false;
		//$("#submit_doc").attr('disabled', "false");
		$("#submit_doc").removeAttr("disabled");
		$("#submit_doc").val('发布').toggleClass("graybutton button");
		return false;
	}

	
	//以下是提交到后端的部分
	var name = trim($("#name").val());
	var docUuid = $("#docUuid").val();
	var docdesc = trim( editor.getContent() );
	var writegroups = $("#selectedIdList").val();
	var extension = $("#extension").val();
	var type = $("#preType").val();
	var url = $("#linkId").val();
	var toMySelf = $("input[name=toMySelf]:checked").val();
	var enclosure = "";
	if( type == '1'){
		enclosure = docUuid;
	}else if(type == '2' || type == '3' ){
		enclosure = url;
	}	
	var labels = $("#addresourcetags").val().split(' ').join(',');
	var newLabels = $("#newtags").html().split(' ').join(',');
	
	if(labels.substring(labels.length-1) == ','){
		labels = labels.substring(0, labels.length-2);
	}
	if(newLabels.substring(newLabels.length-1) == ','){
		newLabels = newLabels.substring(0, newLabels.length-2);
	}
	showLoading();
	$.post("upload/publish",
			{name:name,
			description:docdesc,
			type:type,
			extension:extension,
			enclosure:enclosure,
			toMySelf:toMySelf,
			labels:labels,
			newLabels:newLabels,
			writegroups:writegroups
			}, 
			function(data){
				if(data.status != "success"){
					fillTipBox('error',"文档上传失败");
				}else{	        					
					location.href="item?tipstatus=success&id="+data.id;
				}
			}
	);
	//$("#submit_doc").attr('disabled', "false");
	$("#submit_doc").removeAttr("disabled");
	$("#submit_doc").val('发布').toggleClass("graybutton button");
	return true;
}

function submitList(){	
	$("#submit_list").val('...').toggleClass("button graybutton");
	$("#submit_list").attr('disabled',"true");	
	//清空提示
	$("#text4").text("");
	$("#text3").text("");
	$("#listText").text("");
	$("#groupText").text("");

	var groups = $("#selectedIdList").val();
	var me = $("input[name=toMySelf]:checked").val();
	if(groups == "" && me == "self0"){
		$("#text4").text("请选择所属群组或者选择资源仅自己可见！"); 
		fillTipBox('error',"请选择所属群组或者选择资源仅自己可见！");
		//$("#submit_list").attr('disabled', "false");
        $("#submit_list").removeAttr("disabled");
		$("#submit_list").val('发布').toggleClass("graybutton button");
		return false;
	}
	
    if(($("#listForm #name").val().length)>0&&editor.getContent().length-13>0){
    	if(editor.hasContents()){  
    		editor.sync();
    	}
    	
    	var name = $("#name").val();
    	var description = editor.getContent();
    	var	writegroups = $("#selectedIdList").val();	
    	var onlyme = $("input[name=toMySelf]:checked").val();
    	var labels = $("#addresourcetags").val().split(' ').join(',');
    	var newLabels = $("#newtags").html().split(' ').join(',');
    	
    	if(labels.substring(labels.length-1) == ','){
    		labels = labels.substring(0, labels.length-2);
    	}
    	if(newLabels.substring(newLabels.length-1) == ','){
    		newLabels = newLabels.substring(0, newLabels.length-2);
    	}
    	showLoading();
    	$.post("upload/publish",
    			{name:name,
    			 type:-1,
    			 description:description,
    			 writegroups:writegroups,
    			 labels:labels,
     			 newLabels:newLabels,
    			 toMySelf:onlyme
    			}, function(data){
    				if(data.status != "success"){
    					fillTipBox('error',"列表发布失败！");
    				}else{
    					location.href=encodeURI("list?tips=发布列表成功&id="+data.id);
    				}
    			}
    	);
    	$("#submit_list").removeAttr("disabled");
		$("#submit_list").val('发布').toggleClass("graybutton button");
    	return true;
    }
    else {
    	if (($("#listForm #name").val().length==0)){
    		$("#text3").text("列表名称不得为空！");
    		fillTipBox('error',"列表名称不得为空！");
    	}        		
    	
    	if (($("#listForm #name").val().length>40)){
    		$("#text3").text("列表名称最多输入40个字符！");
    		fillTipBox('error',"列表名称最多输入40个字符！");
    	}
    		
    	if(editor.getContent().length-13<=0){
    		$("#listText").text("列表描述不能小于6个字符!");
    		fillTipBox('error',"列表描述不能小于6个字符!");
    	}
    		
    	if($("#selectedIdList").val().length<=0 ){
    		$("#groupText").text("请选择归属组");
    		fillTipBox('error',"请选择归属组！");
        }
    	//$("#submit_list").attr('disabled', "false");
        $("#submit_list").removeAttr("disabled");
		$("#submit_list").val('发布').toggleClass("graybutton button");
    	return false;
    }
}

function submitnewdoc(){
	window.onbeforeunload = function(){};
	//文档描述的验证
    if(trim( editor.getContent() ).length-13<=0){
    	$("#newVersionText").text("更新描述不可为空");
    	fillTipBox('error',"更新描述不可为空");
   	 	return false;
    }
	if(editor.hasContents()){ 
	    editor.sync();
	}
	//文件是否已完成了上传,如果已上传，就会写进docUuid里。如果docUuid是空的，linkId又没有内容，而且“已添加”后边的区域内容不为空，
	//就有可能是正在上传，还没有结束
	if($("#docUuid").val() == ""){
		if($("#files_1").html() == ""){
			$("#fileText").html("没有上传文件");
		}else{
			$("#fileText").html("文件上传未结束，请稍候发布");
		}
		return false;
	}
	
	showLoading();
	var id = $("#_id").val();
	var name =  $("#_name").text();
	var enclosure = $("#docUuid").val();
 	var description = trim( editor.getContent() );
 	var extension = $("#extension").val();
 	//TODO 获取上传文档的后缀名，传值给后端action中extension处理
 	$.post("resource/donewdocversion",
			{id:id,
 			 name:name,
 		     description:description,
 			 enclosure:enclosure, 
 			 extension:extension,
			}, function(data){
				if(data == undefined){
					fillTipBox('error',"发布失败。");
				}else{
					location.href="item?id="+data.id;
				}
			});
}
function submitupdateitem(){
	var toMySelf = $("input[name=toMySelf]:checked").val();
	var selectedIdList = $("#selectedIdList").val();
	if(toMySelf == 'self1' || (selectedIdList && selectedIdList != '')){
		if($("#updateitemForm #name").val().length>0&&$("#updateitemForm #name").val().length<=40&&editor.getContent().length-13>0){
			editor.sync();
			document.forms["updateitemForm"].submit();
			showLoading();
	    	return true;	
		}else{
			if($("#updateitemForm #name").val().length==0){
				$("#updateitemForm #text").text("请输入文章名称!");
				fillTipBox('error',"请输入文章名称!");
				return false;
			}
			if($("#updateitemForm #name").val().length >40){
				$("#updateitemForm #text").text("文章名称最多输入40个字符!");
				fillTipBox('error',"文章名称最多输入40个字符!");
				return false;
			}
			if(editor.getContent().length-13<=0){
				$("#updateitemForm #descriptionText").text("文章内容不能小于6个字符");
				fillTipBox('error',"文章内容不能小于6个字符!");
				return false;
			}
			return false;
		}
	}else{
		$("#text4").html("请选择所属群组或者选择资源仅自己可见！");
		fillTipBox('error',"请选择所属群组或者选择资源仅自己可见！");
		return false;
	}
}
function submitupdatelist(){
	var toMySelf = $("input[name=toMySelf]:checked").val();
	var selectedIdList = $("#selectedIdList").val();

	var labels = $("#addresourcetags").val().split(' ').join(',');
	var newLabels = $("#newtags").html().split(' ').join(',');
	
	if(labels.substring(labels.length-1) == ','){
		labels = labels.substring(0, labels.length-2);
	}
	if(newLabels.substring(newLabels.length-1) == ','){
		newLabels = newLabels.substring(0, newLabels.length-2);
	}
	
	$("input[name=labels]").val(labels);
	$("input[name=newLabels]").val(newLabels);
	if(toMySelf == 'self1' || (selectedIdList && selectedIdList != '')){
		if($("#updatelistForm #name").val().length>0&&$("#updatelistForm #name").val().length<=40&&editor.getContent().length-13>0){
			editor.sync();
			document.forms["updatelistForm"].submit();
			showLoading();
	    	return true;
		}
		else{
			if($("#updatelistForm #name").val().length==0){
				$("#updatelistForm #text").text("请输入文章名称!");
				fillTipBox('error',"请输入文章名称!");
				return false;
			}
			if($("#updatelistForm #name").val().length > 40){
				$("#updatelistForm #text").text("文章名称最多输入40个字符！");
				fillTipBox('error',"文章名称最多输入40个字符！");
				return false;
			}
			if(editor.getContent().length-13<=0){			
				$("#updatelistForm #descriptionText").text("列表内容不可为空");
				fillTipBox('error',"列表内容不可为空!");
				return false;
			}
			return false;
		}
	}else{
		$("#text4").html("请选择所属群组或者选择资源仅自己可见！");
		fillTipBox('error',"请选择所属群组或者选择资源仅自己可见！");
		return false;
	}	
}
//“归属群组”浮层的“提交”按钮对应的处理函数，by qiepei
function submitAddtoGroup(){
	
	
	var str= $("#selectedList").val();
	if(str!=","){
		
		$("#selectedvalue").val(str);
		var selectedList = str.split(",");
		var selectedGroupName="";
		str = "";
		for(var i=0;i<selectedList.length;i++){
			if(selectedList[i] != ""){
				var group = selectedList[i].split(":");
				str += group[0]+",";
				selectedGroupName += group[1]+",";
			}					
		}
		$("#selectedIdList").val(str.substring(0,str.length-1));
		
		$.post('resource/addtogroups',{'selectedGroup':str},function(){					
			 $("#select").html(selectedGroupName.substring(0,selectedGroupName.length-1));
			 $("#selectedgroups").val(str);
			 
			 $("#showSelectedResult").attr("class","");
			 $(document).trigger('close.facebox');
		});				
	}			
}
function preModifyGroup(){
	
	var str= $("#selectedList").val();
	if(str!=","){
		
		$("#selectedvalue").val(str);
		var selectedList = str.split(",");
		var selectedGroupName="";
		str = "";
		for(var i=0;i<selectedList.length;i++){
			if(selectedList[i] != ""){
				var group = selectedList[i].split(":");
				str += group[0]+",";
				selectedGroupName += group[1]+",";
			}					
		}
		$("#selectedIdList").val(str.substring(0,str.length-1));
		
		$.post('resource/addtogroups',{'selectedGroup':str},function(){					
			 $("#select").html(selectedGroupName.substring(0,selectedGroupName.length-1));
			 $("#selectedgroups").val(str);
			
			 $("#showSelectedResult").attr("class","");
			 $(document).trigger('close.facebox');
		});				
	}			
}
function modifyGroup(resourceId){
	var writegroups = $("#selectedList").val();
	
	var str= $("#selectedList").val();
	if(str!=","){
		
		$("#selectedvalue").val(str);
		var selectedList = str.split(",");
		var selectedGroupName="";
		str = "";
		for(var i=0;i<selectedList.length;i++){
			if(selectedList[i] != ""){
				var group = selectedList[i].split(":");
				str += group[0]+",";
				selectedGroupName += group[1]+",";
			}					
		}
		$("#selectedIdList").val(str.substring(0,str.length-1));
		
		$.post('resource/modifyblgroups',{id:resourceId,'writegroups':str},function(){					
			
			
			 $(document).trigger('close.facebox');
			 location.reload();
		});			
	}
}
//“归属群组”浮层的“提交”按钮对应的处理函数，by qiepei
function submitAddtoGroup(submittype){
	if(submittype != undefined){
		modifyGroup(submittype);
	}
	else{
		preModifyGroup();
	}
}


//“仅自己可见”切换时的函数操作，by qiepie
function selectMySelfFn(name){
	var selectedValue = $("input[name="+name+"]:checked").val();
	if(selectedValue == 'self1'){
		$("#shareToMyselfId").attr("class","lightgreyletter");
		$("#shareToGroupId").attr("class","hidden");
		$("#showSelectedResult").attr("class","hidden");
		$("#shareToMyselfDiv").attr("class","leftfloat darkgreybg");
		$("#shareToGroupDiv").attr("class","leftfloat");
	}else{
		$("#shareToMyselfId").attr("class","hidden");
		$("#shareToGroupId").attr("class","lightgreyletter");
		if($("#selectedIdList").val() != ""){
			$("#showSelectedResult").attr("class","");
		}		
		$("#shareToGroupDiv").attr("class","leftfloat darkgreybg");
		$("#shareToMyselfDiv").attr("class","leftfloat");
	}
}
function checkBeforeUpload(){
	/*
	 * 13 is the fixed length, the length of the"<p></br></p>" After
	 * subtracting the fixed length,the rest is the content's length
	 */
	//首先设置页面跳转时的函数，避免出现“确定离开”的提示
	window.onbeforeunload=function(){}
	/*
	 * 以下依次对于提交的各项做前端验证
	 * */    	
	//首先把各项提示都清空
	$("#text1").text("");
	$("#docText").text("");
	$("#text4").text(""); 
	$("#fileText").html("");
	
	//资源名称的验证
	if((trim($("#name").val()).length==0)){
   	 	$("#text1").text("资源名称不得为空！");
   	 	fillTipBox('error',"资源名称不得为空333！");
   	 	alert("123");
   	 	//window.location.hash = "#name";
   	 	return false;
    }
    if((trim($("#name").val()).length >= 40)){
   	 	$("#text1").text("资源名称最多输入40个字符！");
   	 	fillTipBox('error',"资源名称最多输入40个字符！");
   	 	return false;
    }
    //文档描述的验证
    if(trim( editor.getContent() ).length<=5){
   	 	$("#docText").text("文档描述不能小于66个字符");
   	 	fillTipBox('error',"文档描述不能小于6个字符！");
   	 	return false;
    }
    //权限的验证
	var groups = trim($("#selectedIdList").val());
	var me = $("input[name=toMySelf]:checked").val();    	
	if((groups == "" || !groups) && me == "self0"){
		$("#text4").text("请选择所属群组或者选择资源仅自己可见！"); 
		fillTipBox('error',"请选择所属群组或者选择资源仅自己可见！");
		return false;
	}
	if(editor.hasContents()){ 
	    editor.sync();
	}
	//文件是否已完成了上传,如果已上传，就会写进docUuid里。如果docUuid是空的，linkId又没有内容，而且“已添加”后边的区域内容不为空，
	//就有可能是正在上传，还没有结束
	if($("#docUuid").val() == "" && $("#files_1").html().length>0 && $("#linkId").val() == ""){
		$("#fileText").html("文件上传未结束，请稍候发布");
		fillTipBox('error',"文件上传未结束，请稍候发布！");
		return false;
	}
	return true;
}