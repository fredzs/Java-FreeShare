function showSuccess(result){
		//$("#hiddenuuid2").val(result);
		//$("#hiddenuuid").val("");
		$("#hidden").val("");
		$("#avatarinfo").html("头像保存成功");
		setTimeout(function(){$("#avatarinfo").html("");},3000);
}
$(function(){
       
		var saveOptions = {
				cache : false,
				//beforeSubmit: checkUuid,
				//success : showSuccess
		};
		$("#avatarsaveform").submit(function(){
			$(this).ajaxSubmit(saveOptions);  
		    return false;
		});
		var initFileUpload = function (suffix) {
        $('#avatarform').fileUploadUI({
        	autoUpload: false,
            maxChunkSize: 20*1024*1024,
            namespace: 'file_upload_' + suffix,
            fileInputFilter: '#photo_' + suffix,
            dropZone: $('#drop_zone_' + suffix),
            uploadTable: $('#avatarinfo'),
            downloadTable: $('#avatarinfo'),
            acceptFileTypes: /(png)|(jpe?g)|(gif)$/i,
            beforeSend: function (event, files, index, xhr, handler, callBack) {
        	    //test the type of file
        	    var regexp = /(png)|(jpe?g)|(gif)$/i;
        	    if (!regexp.test(files[index].name)) {
                    handler.uploadRow.find('.file_upload_progress').html('头像必须为png、jpg或gif类型!');
                    setTimeout(function () {
                        handler.removeNode(handler.uploadRow);
                    }, 3000);
                    return;
                }
        	  //test the size of file
                if (files[index].size > 30*1024) {
                    handler.uploadRow.find('.file_upload_progress').html('文件必须小于30KB!');
                    setTimeout(function () {
                        handler.removeNode(handler.uploadRow);
                    }, 3000);
                    return;
                }
        	    
                callBack();
            },
            buildUploadRow: function (files, index) {
            	
            	$("#avatarinfo").html("");
                return $('<tr>' +
                        '<td class="file_upload_progress"><div><\/div><\/td>' +
                        '<td class="file_upload_cancel">' +
                        '<button class="ui-state-default ui-corner-all" title="Cancel">' +
                        '<span class="ui-icon ui-icon-cancel">取消<\/span>' +
                        '<\/button><\/td><\/tr>');
            },
            buildDownloadRow: function (file) {
            	if(file.uuid == null || file.uuid == ""){
            		//alert("文件上传失败，请重传！");
            		var  tipContent = "<table><tr><td><img src='images/error.png'/></td><td>&nbsp;文件上传失败，请重传！&nbsp;</td></tr></table>";
					 
					$("#tipboxWrapper").html("<div  id=\"tipbox\"  class=\"mytipboxposition\"></div>");
					$("#tipbox").tipbox  ({
					         content:tipContent,
					         autoclose:true,
					         hasclose:false
					});
            	}else{
	            	$("#showavatar").attr("src", "http://freedisk.free4lab.com/download?uuid="+file.uuid);
	        		//$("#hiddenuuid").val(file.uuid);
	        		$("#hidden").val(file.uuid);
	        		$("#hidden2").val(file.uuid);
	        		$("#avatarinfo").css({"color":"red"});
	                //return $('<tr><td>头像预览中，未保存<\/td><\/tr>');
	                return ;
	                //一般情况下，我们可以刷新页面，表示文件已经上传完毕
            	}
            }
        });
    };
    initFileUpload(1);
});
	