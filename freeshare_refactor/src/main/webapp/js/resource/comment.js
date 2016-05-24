/*
 对应的jsp文件是：
basic/browse.jsp
basic/collections.jsp
basic/news.jsp
*/
function subCmt(id, type) {	
	var cmt = trim($('#cmtcont_'+id+'_'+type).val());
	
	if(cmt.length > 150){
		fillTipBox("error","您输入的评论字数已超过150个，请重新输入");
		return false;
	}
	
	if (cmt != null && cmt != "") {
		var reg = /(http:\/\/|https:\/\/)((\w|=|\?|\.|\/|&|-)+)/g
			//替换textarea中的回车，给超链接自动加上a标签
			cmtCont = cmt.replace(/\n/g,"<br/>").replace(reg, "<a href='$1$2' target='_blank'>$1$2</a>");
		
		$('#subCm_'+id+'_'+type).val('...').toggleClass("button graybutton");
		$('#subCm_'+id+'_'+type).attr('disabled', "true");
		$.ajax({
			url : 'resource/commentwithat',
			type : 'post',
			data : {
				uid : $('#uid_'+id+'_'+type).val(),
				id : $('#id_'+id+'_'+type).val(),
				aid : $('#aid_'+id+'_'+type).val(),
				pid : $('#pid_'+id+'_'+type).val(),
				type : type,
				name : $('#name_'+id+'_'+type).val(),
				cmtCont : cmtCont,
				uname : $('#uname_'+id+'_'+type).val()
				},
				success : function(data) {
					var d = new Date();
					var year = d.getFullYear();
					var month = d.getMonth() + 1;
					var day = d.getDate();
					var hour = d.getHours();
					var min = d.getMinutes();
					var sec = d.getSeconds();
					var time = (year + "-" + month + "-" + day + " " + hour
							+ ":" + min + ":" + sec);
					var div = "<div><div class=\"dottedline\"></div>";
					var leftTable = "<table>";
					var tr = "<tr><td rowspan=\"2\" width=\"60\" class=\"centeralign topveralign\">"
							+ "<img src=\"http://freedisk.free4lab.com/download?uuid=" + $('#user_avatar_'+id+'_'+type).val() + "\" "
							+ "onerror=\"javascript:this.src='images/user_male.png'\""
							+ "border=\"0\" width=\"40\" height=\"40\"/></td>"
							+ "<td class=\"leftalign\"><span id=\"cmtname\">" + $('#uname_'+id+'_'+type).val() + " </span>" 
							+ time + "<span class=\"rightfloat\">"
							+ "<a class=\"blueletter\" href=\"javascript:void(0)\" onclick=\"delCmt('"+data.url+"',this)\">删除</a>"
							+ "</span><br/>" + "<span>" + cmtCont + "</span>"
							+ "</td></tr>";
					var rigthTable = "</table></div>";
					var table = div + leftTable + tr + rigthTable;
					$('#cmtarea_'+id+'_'+type).append(table);
					$('#cmtcont_'+id+'_'+type).attr('value', '');
					$('#subCm_'+id+'_'+type).val('评论').toggleClass("button graybutton");
					$('#subCm_'+id+'_'+type).removeAttr("disabled");
					//评论字数提示置空
					$('#cmtcont_'+id+'_'+type+'_showtxt').html("");
					
					var  tipContent = "<table><tr><td><img src='images/success.png'/></td><td>&nbsp;评论发布成功,获得0.5经验值&nbsp;</td></tr></table>";
					 
					$("#tipboxWrapper").html("<div  id=\"tipbox\"  class=\"mytipboxposition\"></div>");
					$("#tipbox").tipbox  ({
					         content:tipContent,
					         autoclose:true,
					         hasclose:false
					});
				}
			});
	} 
}
function replyCmt(param, id,type) {
	$('#cmtcont_'+id+'_'+type).focus();
	var value = '回复 @' + $('#' + param).text().trim() + "("+ param +") ：";
	$('#cmtcont_'+id+'_'+type).attr('value', value);
	$('#pid').val(param);
	
	//使光标移到textarea的最后位置，不同浏览器识别方法不同
	/*var obj = document.getElementById('#cmtcont_'+id+'_'+type);
	var len = obj.value.length;
    if (document.selection) {
        var sel = obj.createTextRange();
        sel.moveStart('character',len);
        sel.collapse();
        sel.select();
    } else if (typeof obj.selectionStart == 'number' && typeof obj.selectionEnd == 'number') {
        obj.selectionStart = obj.selectionEnd = len;
    }*/
}
function delCmt(param,obj) {
	if(confirm('确认删除?')){
		$.ajax({
			url : 'resource/delcomment',
			type : 'post',
			data : {
				cmtUrl : param,
			},
			success : function() {
				$(obj).parent().parent().parent().parent().parent().parent().remove();
				var  tipContent = "<table><tr><td><img src='images/success.png'/></td><td>&nbsp;删除成功&nbsp;</td></tr></table>";
				 
				$("#tipboxWrapper").html("<div  id=\"tipbox\"  class=\"mytipboxposition\"></div>");
				$("#tipbox").tipbox  ({
				         content:tipContent,
				         autoclose:true,
				         hasclose:false
				});
			}
		});
	}
}
function upUser() {
	$.ajax({
		url : 'resource/upuser',
		type : 'post',
		data : {
			uid : $('#pid').val(),
		},
		success : function() {
			alert("支持成功！");
		}
	});
}

/*
 * 对资源或列表的好评、差评操作
 * resourceId：资源或列表的id
 * type：-1为列表，其他为资源
 * updown："up"为顶，"down"为踩
 */
function supportresource(resourceId,type,updown) {
	var operation = "";
	var url = "resource/";
	
	operation = type + "_" + updown;
	
	if (nGetCookie(resourceId + operation) == null) {
		$.ajax({
			url : "resource/supportresource",
			type : 'post',
			data : {
				id : resourceId,
				resourceType: type,
				upDown : updown
			},
			success : function() {
				nSetCookie(resourceId + operation, "already", 'd1');
				//如果在资源页面，那么更新顶和踩的数量
				if($('#down_num')){
					if(updown == "up"){
						var num = $('#up_num').text() * 1;
						num = num + 1;
						$('#up_num').text(num);
					} else {
						var num = $('#down_num').text() * 1;
						num = num + 1;
						$('#down_num').text(num);
					}
				}
				var alertmsg = "";
				if(updown == "up"){
					alertmsg = "已顶过";
				} else{
					alertmsg = "已踩过";
				}
				//alert(alertmsg);
				var  tipContent = "<table><tr><td><img src='images/success.png'/></td><td>&nbsp;"+ alertmsg +"&nbsp;</td></tr></table>";
				 
				$("#tipboxWrapper").html("<div  id=\"tipbox\"  class=\"mytipboxposition\"></div>");
				$("#tipbox").tipbox  ({
				         content:tipContent,
				         autoclose:true,
				         hasclose:false
				});
			}
		});
	} else {
		var alertmsg = "";
		if(updown == "up"){
			alertmsg = "今天已顶过，明天再来";
		} else{
			alertmsg = "今天已踩过，明天再来";
		}
		//alert(alertmsg);
		var  tipContent = "<table><tr><td><img src='images/warning.png'/></td><td>&nbsp;"+ alertmsg +"&nbsp;</td></tr></table>";
		 
		$("#tipboxWrapper").html("<div  id=\"tipbox\"  class=\"mytipboxposition\"></div>");
		$("#tipbox").tipbox  ({
		         content:tipContent,
		         autoclose:true,
		         hasclose:false
		});
	}
}
/** **************收藏的处理****************** */
function collect(param1, param2) {
	$.ajax({
		url : 'resource/collect',
		type : 'post',
		data : {
			type : param2,
			id : param1,
		},
		success : function() {
			//alert("已收藏。");
			var  tipContent = "<table><tr><td><img src='images/success.png'/></td><td>&nbsp;收藏成功&nbsp;</td></tr></table>";
			 
			$("#tipboxWrapper").html("<div  id=\"tipbox\"  class=\"mytipboxposition\"></div>");
			$("#tipbox").tipbox  ({
			         content:tipContent,
			         autoclose:true,
			         hasclose:false
			});
		}
	});
}
/***************编辑的处理****************/
function edit(param1,param2){
	alert("param1:"+param1+" "+param2);
	
}

function removecoll(param, type) {
	$.ajax({
		url : 'resource/delcollect',
		type : 'post',
		data : {
			type :type,
			id : param,
		},
		success : function() {
			location.reload();
		}
	});
}

function showCount(obj){
	var messageValue = trim($(obj).val());
	var length = messageValue.length;
	var txt_id = obj.id +'_showtxt';
	
	if(length <= 150){
		$('#'+txt_id).attr("class","greyletter");
		
		var tip = "您已输入了"+ length +"字，还可输入"+ (150-length) +"字";
		$('#'+txt_id).html(tip);
		return true;
	}else{
		$('#'+txt_id).attr("class","redletter");
		var tip = "您已输入了"+ length +"字，超出了150的最大字数限制";			
		$('#'+txt_id).html(tip);
		return false;
	}
}

function showComments(param){
	if(param.detail != undefined && !$(param.detail).hasClass("hidden")){
		$(param.detail).addClass("hidden");
		$(param.obj).text("评论");
		$(param.detail).find(".cmtcontent").html("<img src=\"images/loading.gif\" border=\"0\"/>正在加载评论，请稍候...");
	}else{
		if(param.detail != undefined){
			$(param.detail).removeClass("hidden");
			$(param.obj).text("收起评论");
			
			var left = $(param.obj).offset().left + 10 - $("#inner").offset().left;
			$(param.detail).find(".arrow").css({left:left});
		}
		if(param.type != -1){
			param.type = 0;
		}
		url = "comments";
		$.post(url,{id:param.resourceId,page:'1',resourceType:param.type},function(data){
			if(param.detail != undefined){
				$(param.detail).find(".cmtcontent").html(data);
			}else{
				$(".cmtcontent").html(data);
			}
			$("#cmtconttemp").attr('id','cmtcont_'+param.resourceId+'_'+param.type);
			$("#show_txt").attr('id','cmtcont_'+param.resourceId+'_'+param.type+'_showtxt');
			
			userAutoTips({id:'cmtcont_'+param.resourceId+'_'+param.type});
			$('#cmtcont_'+param.resourceId+'_'+param.type).tah({
				moreSpace : 15, // 输入框底部预留的空白, 默认15, 单位像素
				maxHeight : 600, // 指定Textarea的最大高度, 默认600, 单位像素
				animateDur : 100 // 调整高度时的动画过渡时间, 默认200, 单位毫秒
			});
			$('#cmtcont_'+param.resourceId+'_'+param.type).keyup(function(){
				showCount(this);
			});	
		})
	}
}