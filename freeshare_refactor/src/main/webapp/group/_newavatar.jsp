<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="sidebar dottedbox greybg padding5">
	<div class="greyletter parseline">头像预览</div>
	<div class="padding715 centeralign leftfloat">
		<img class="avatarpreview" height="40" width="40" border="0"
			src='<s:property value="avatar"/>' onerror="javascript:this.src='images/grouphead.png'" />
		<br />
		40x40
	</div>
	<div class="padding715 centeralign rightfloat">
		<img class="avatarpreview" height="100" width="100" border="0"
			src='<s:property value="avatar"/>' onerror="javascript:this.src='images/grouphead.png'" />
		<br />
		100x100
	</div>
</div>

<div id="selfavatar" class="main leftmargin_0 padding5">
	<input class="greybutton leftbutton pointer selected" type="button" value="自定义头像" />
	<input class="greybutton rightbutton pointer" type="button" onclick="showsys()" value="系统头像" /><br />
	<div class="padding10 centeralign">
		<form id="avatarform" action="group/previewavatar" method="post" enctype="multipart/form-data">
			<span>
			<input id="photo_1" type="file" name="photo" size="1" class="txtfile topmargin_20 pointer"></input>
			<input type="hidden" name="hiddenUuid" id="hiddenuuid"/>
			<input type="hidden" name="groupId" id="groupId" value = '<s:property value="groupId"/>'/>
			<input class="button topmargin_20 pointer"  id="drop_zone_1" type="button" value="上传本地图片" />
			</span>
			<br />
			从本地选择一张图片上传，文件大小小于30kb<br />文件类型为png、jpg、jpeg、gif
			<br />
			<table id="avatarinfo">
				<tr><td><br /></td></tr>
			</table>
		</form>
	</div>
	<div class="rightalign topmargin_20">
		<input type="button" class="button" onclick="saveavatar()" value="确认" />
		<input type="button" class="greybutton" onclick="cancel()" value="取消" />
	</div>
</div>
<div id="sysavatar" class="main leftmargin_0 padding5 hidden">
	<input class="greybutton leftbutton pointer" type="button" onclick="showself()" value="自定义头像" />
	<input class="greybutton rightbutton pointer selected" type="button" value="系统头像" /><br />
	<div class="padding10">
		<s:iterator id="ava" value="avatars" status="st">
			<div class="leftfloat dottedbox padding10 bothmargin">
				<img src="http://freedisk.free4lab.com/download?uuid=<s:property value="#ava" />"  
					width="50" height="50" 
					title="<s:property value="#ava" />"
					onclick="selectThis('<s:property value="#ava" />')"/>
			</div>
		</s:iterator>
		<div class="clear"></div>
	</div>
	<div class="rightalign topmargin_20">
		<input type="button" class="button" onclick="saveavatar()" value="确认" />
		<input type="button" class="greybutton" onclick="cancel()" value="取消" />
	</div>
</div>
<div id="sysavatar" class="main leftmargin_0 padding5 hidden">
	<input class="greybutton leftbutton pointer" type="button" onclick="showself()" value="自定义头像" />
	<input class="greybutton rightbutton pointer selected" type="button" value="系统头像" /><br />
	<div class="padding10 xlheight">
		<s:iterator id="ava" value="avatars" status="st">
			<div class="leftfloat dottedbox padding10 bothmargin topmargin_5 bottommargin_5">
				<img src="http://freedisk.free4lab.com/download?uuid=<s:property value="#ava" />" 
					width="50" height="50" 
					title="<s:property value="#ava" />"
					onclick="selectThis('<s:property value="#ava" />')"/>
			</div>
		</s:iterator>
		<div class="clear"></div>
	</div>
	<div class="rightalign topmargin_20">
		<input type="button" class="button" onclick="saveavatar()" value="确认" />
		<input type="button" class="greybutton" onclick="cancel()" value="取消" />
	</div>
</div>

<div class="clear"></div>
<script>
	var initFileUpload = function (suffix) {
		//alert("init!");
	    $('#avatarform').fileUploadUI({
	    	autoUpload: false,
	        maxChunkSize: 20*1024*1024,
	        namespace: 'file_upload_' + suffix,
	        fileInputFilter: '#photo_' + suffix,
	        dropZone: $('#drop_zone_' + suffix),
	        uploadTable: $('#avatarinfo'),
	        downloadTable: $('#avatarinfo'),
	        acceptFileTypes: /(png)|(jpe?g)|(gif)$/i,
	        buildUploadRow: function (files, index) {
	        	$("#avatarinfo").html("");
	        	return $('<table><tr>' +
	                    '<td><font color=\"red\">' + files[index].name + '</font><\/td>' +
	                    '<td class="file_upload_progress"><div><\/div><\/td></table>');
	        },
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
	    	    //alert("before callback!");
	            callBack();
	            //alert("after callback!");
	        },
	        
	        buildDownloadRow: function (file) {
	        	//alert("buildDownloadRow!");
	        	if(file.uuid == null || file.uuid == ""){
	        		$("#avatarinfo").css({"color":"red"});
	                return $('<tr><td>文件上传失败，请重传！<\/td><\/tr>');
	        	}else{
	            	$(".avatarpreview").attr("src", "http://freedisk.free4lab.com/download?uuid="+file.uuid);
	            	$("#showavatar").attr("src", "http://freedisk.free4lab.com/download?uuid="+file.uuid);
	        		$("#hiddenuuid").val(file.uuid);
	        		
	        	}
	        }
	    });
	};
	initFileUpload(1);
	
	function selectThis(uuid){
		$.post("group/deleteavatar", {oldAvatar:$("#hiddenuuid").val()}, function(){
			//$("#showavatar").attr("src", "http://freedisk.free4lab.com/download?uuid="+uuid);
    		$("#hiddenuuid").val(uuid);
			$(".avatarpreview").attr("src", "http://freedisk.free4lab.com/download?uuid="+uuid);
		});
	}
	function showself(){
		$("#sysavatar").addClass("hidden");
		$("#selfavatar").removeClass("hidden");
	}
	function showsys(){
		$("#sysavatar").removeClass("hidden");
		$("#selfavatar").addClass("hidden");
	}
	function saveavatar(){
		var hiddenuuid = $("#hiddenuuid").val();
		if(hiddenuuid == ''){
			$("#avatarinfo").html("<font color=\"red\">你没有上传新的头像文件</font>");
			setTimeout(function(){$("#avatarinfo").html("");},3000);
			return false;
		}else{
			$("#hidden").val(hiddenuuid);
    		$("#hidden2").val(hiddenuuid);
			$("#showavatar").attr("src", "http://freedisk.free4lab.com/download?uuid="+hiddenuuid);
			$.facebox.close();
			
			
		}
	}
	function cancel(){
		if($("#hiddenuuid").val() != ''){ 
			if(confirm("确认放弃此次头像修改？")){
				$.post("user/deleteavatar", {oldAvatar:$("#hiddenuuid").val()}, function(){
					$.facebox.close();
				});
			}
		}else{
			$.facebox.close();
		}
	}
</script>
