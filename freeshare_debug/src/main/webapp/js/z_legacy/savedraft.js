function savedraft(){
    	// 以下是提交到后端的部分
    	var id = $("#draft_id").val();
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
    	
    	$.post("upload/savedraft",
    			{id:id,
    			name:name,
    			description:docdesc,
    			type:type,
    			extension:extension,
    			enclosure:enclosure,
    			toMySelf:toMySelf,
    			writegroups:writegroups
    			}, 
    			function(data){
    				if(data.status != "success" || data.id == null || data.id == -1){
    					alert("保存草稿失败!");
    				}else{	    
    					$('#draft_id').val(data.id);
    					fillTipBox('success',"保存草稿成功。");
    				}
    			}
    	)
}
