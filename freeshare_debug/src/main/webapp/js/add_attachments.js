$(function () {
    var initFileUpload = function (suffix) {
        $('#docForm').fileUploadUI({
        	autoUpload: false,
            maxChunkSize: 20*1024*1024,
            namespace: 'file_upload_' + suffix,
            fileInputFilter: '#file_' + suffix,
            dropZone: $('#drop_zone_' + suffix),
            uploadTable: $('#files_' + suffix),
            downloadTable: $('#files_' + suffix),
            acceptFileTypes: /(png)|(jpe?g)|(gif)$/i,
            beforeSend: function (event, files, index, xhr, handler, callBack) {
            	
            	//判断是否已经上传了链接和视频
            	if($("#preType").val() != '4'){		            		
            		if(confirm("上传文件将替换您已经添加的链接或文件，是否继续？")){
            			deleteAddedResource();		            			
            		}else{		       
            			this.cancelUpload(event, files, index, xhr, handler);
            			$("#files_1").html( $("#recordAddedRes").val() );
            			return ;
            		}		            		
            	}
        		//test the size of file
                if (files[index].size > 20*1024*1024) {
                	alert("larger file size");
                    handler.uploadRow.find('.file_upload_progress').html('文件必须小于20MB!');
                    setTimeout(function () {
                        handler.removeNode(handler.uploadRow);
                    }, 100000);
                    return;
                }
                callBack();
                $("#submit_doc_no").removeClass("hidden");		            	
            },
            buildUploadRow: function (files, index) {		         
            	$("#urladdress").attr("class","greybg grey1border padding5");
            	$("#files_1").html("");
            	//如果输入链接的窗口开着，就把他关起来
            	hide();
            	
                return $('<tr><td class="file_upload_progress" width="130px"><div><\/div><\/td>' +
                        '<td class="file_upload_cancel" width="70px">' +
                        '<button class="greybutton" title="Cancel">' +
                        '<span class="ui-icon ui-icon-cancel">取消<\/span>' +
                        '<\/button><\/td><\/tr>');
            },
            buildDownloadRow: function (file) {
            	if(file.docUuid == null || file.docUuid == ""){
            		fillTipBox('error',"文件上传失败，请重传");
            	}else{		            		
            		var fileName = file.docFileName;
	            	$("#docUuid").val(file.docUuid);
	            	var pos = fileName.lastIndexOf(".");
	        		var fileType = fileName.substring(pos, fileName.length);
	        		$("#extension").val(fileType);
	        		$("#uploadtip").html("已添加");
	        		$("#preType").val("1");
	        		var delLink = "<span class=\"blueletter\">&nbsp;&nbsp;&nbsp;[&nbsp;<a class=\"blueletter\" href=\"javascript:void(0)\" onclick=\"deleteAddedResource()\">删除</a>&nbsp;]</span>";
	                $("#recordAddedRes").val('<tr><td>' + file.docFileName + delLink+'<\/td><\/tr>');
	        		return $('<tr><td>' + file.docFileName + delLink+'<\/td><\/tr>');
            	}
            }
        });
    };
    initFileUpload(1);
    
    window.onbeforeunload = onbeforeunload_handler; 
    function onbeforeunload_handler(){ 
    	if($("#docUuid").val() != ""){
    		 var warning="文件已上传，退出会导致文件丢失";           
    	     return warning;
    	}
    }
}); 
//添加链接的函数
function addlink(){
	var recordUrl = $("#linkId").val();
	var whiteList = ["youku.com","ku6.com","56.com"];
	var isVideo = false;
	for(var i=0; i<whiteList.length; i++){
		if(recordUrl.indexOf(whiteList[i]) != -1){
			$("#preType").val("3");
			isVideo = true;
			break;
		}
	}
	if(!isVideo){
		$("#preType").val("2");
	}			
	hide();			
}
//输入链接的伸展层的显示
function show(){
	$("#urladdress").attr("class","grey1border padding5 greybg");
}
//输入链接的伸展层的隐藏
function hide(){
	$("#urladdress").attr("class","hidden");
}
//输入链接的伸展层的“提交”按钮触发的函数
function submiturl(){
	//先判断是否已经上传了文档附件
	if($("#docUuid") && $("#docUuid").val().length == 36){
		if(confirm("保存链接将替换您已经添加的文件，是否继续？")){
			delUploadedFile();					
		}else{
			hide();
			return ;
		}				
	}			
	$("#uploadtip").html("已添加");
	addlink();
	var videoTip="";
	if( $("#preType").val()=='3' ){
		videoTip="<span class=\"greyletter \">&nbsp;&nbsp;（系统已自动将其识别为视频）</span>";
	}
	var delLink = "<span class=\"blueletter\">&nbsp;&nbsp;&nbsp;[&nbsp;<a class=\"blueletter\" href=\"javascript:void(0)\" onclick=\"deleteAddedResource()\">删除</a>&nbsp;]</span>";
	$("#files_1").html("<tr><td>"+$("#linkId").val()+delLink + videoTip + "</td></tr>");		
	$("#recordAddedRes").val("<tr><td>"+$("#linkId").val()+delLink + videoTip + "</td></tr>");
}
//删除“已添加”后边的链接或者文件
function deleteAddedResource(){
	var type = $("#preType").val();
	//删除视频和链接
	if(type == '2' || type == '3'){
		$("#linkId").val("");
	}else if(type == '1'){
		//删除文件
		delUploadedFile();				
	}
	//"无附件的文档"资源类型为4
	$("#preType").val("4");				
	$("#uploadtip").html("");
	$("#docUuid").val("");
	$("#files_1").html("");
	$("#recordAddedRes").val("");
}
//删除已上传的文件
function delUploadedFile(){
	var uuid = $('#docUuid').val();
	if(uuid.length == 36){
		//表示已经上传成功一个文件，接下来要删除它
		$.post('upload/delUploadedfile',{docUuid:uuid},function(data){
			$("#docUuid").val("");
			fillTipBox('success','删除文件成功');  
		})
	}
}