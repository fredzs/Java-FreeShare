<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>收到视频通话请求</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<div class='centeralign'>
	是否接受会话请求？
</div>
<div class='rightalign'>
	<input type='button' class="button" value='接受' id='receivevideo' />
	<input type='button' class="greybutton"  id='refusevideo' value='拒绝' />
</div>
<script>
    var ringbell;
     $(document).ready( function() {
    	ringbell = window.setInterval("playAudioFn()",1000);
    })
</script> 
<script>
function GetRequest() {
   var url = location.search; //获取url中"?"符后的字串
   var theRequest = new Object();
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
      }
   }
   return theRequest;
}
var Request = new Object();
Request = GetRequest();
var divId = $('#hiddenDivId').val();
console.log('divId='+divId);
$('#receivevideo').click(function(){
	window.clearInterval(ringbell);
	var localUserId = $('.pub_banner').attr("userId");
	var remoteMedia = 'video' + divId, localMedia = 'video' + localUserId + '_' + divId;
	com.webrtc.setMediaDisplayLabel(localMedia, remoteMedia);
	$(document).trigger('close.facebox');
	com.webrtc.accept();
})
$('#refusevideo').click(function(){
	window.clearInterval(ringbell);
	com.webrtc.refuse();
	$('.video').removeClass('hidden').attr('src',com.freemessage.FREEMESSAGE_URL+'images/video.png');
	$('.audio').removeClass('hidden').attr('src',com.freemessage.FREEMESSAGE_URL+'images/voice.png');
	$('.hang').addClass('hidden');
	$(document).trigger('close.facebox');
})
</script>
</body>
</html>
