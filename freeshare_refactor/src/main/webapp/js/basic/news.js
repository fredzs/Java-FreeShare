/*
 对应的jsp文件是：
basic/news.jsp
*/
/*
 * 常量定义
 */
var MAXPAGE = 20;
var PAGESIZE = 20;

var isOutTopic = true;

/*
 * 过滤条件filter的5个属性
 */
var firstTime = 0;
var lastTime = 0;
var resourceTypeList = "all";
var groupList = '';
var groupNameStr = '[所有群组]';
var page = 1;
var filter = {};
var tmp; //企业版添加，满足切换需求


/*
* 企业版添加 by wangxy
*/
function group(){
	tmp = groupList;
	$.ajax(
		{
			url : 'group/getenterpriseid?type=2',
			type : 'post',
			success : function(data)
			{
				groupList = data.groupIds;
				$('#re_group').addClass("hidden");
				$('#notice').removeClass("hidden");
				$('#notice').html("<div id=\"re_group_title\" class=\"strong midsize\" style=\"padding-left: 20px; background-image: url(http://localhost:8080/freeshare/images/blgroup.png); background-position: 0% 50%; background-repeat: no-repeat no-repeat;\">企业公告</div><div class=\"dottedline\"></div><div>There's a meeting this afternoon.</div>");
				$('#company').html("<a id=\"change\" href=\"javascript:void(0)\" class=\"sgraybutton\" onclick = \"personal()\">切换至个人版</a> <a id=\"manage\" href=\"group/useradmin?type=2\" class=\"sgraybutton \" target=\"_blank\">管理</a>	");
				setFilter(0, 0);
				newsFilter(filter, "reload");
				$("#newscontainer").html("<a class=\"button rightfloat\" href=\"group/create_enterprise.jsp\" target=\"_blank\">新建企业</a>");
			}
		}
	);
}

/*
* 企业版添加 by wangxy
*/
function personal(){
	$('#company').html("<a id=\"change\" href=\"javascript:void(0)\" class=\"sgraybutton\" onclick = \"group()\">切换至企业版</a>");
	$('#notice').addClass("hidden");
	groupList = tmp;
	setFilter(0, 0);
	newsFilter(filter, "reload");
	$('#re_group').removeClass("hidden");
}
/*
 * 设置过滤规则:filter 
 */
function setFilter(fTime, lTime){
	filter.firstTime = fTime;
	filter.lastTime = lTime;
	filter.page = page;
	filter.resourceTypeList = resourceTypeList;
	var userId = $("#hiddenuid").val();
	var groupIds = nGetCookie(userId+'groupsfilter');
	if(groupList == '' && groupIds != null && groupIds != 'null'){
		groupIds = GB2312UnicodeConverter.ToGB2312(groupIds);
		groupNameStr = GB2312UnicodeConverter.ToGB2312(nGetCookie(userId+'groupsname'));
		groupList = groupIds;
	}
	filter.groupIds = groupList;
	$("#groupfilterintro").html(groupNameStr);
}

/*
 * 显示群组动态的主函数 
 */
function newsFilter(filter, method){
	if(method == "after"){
		$("#newsloadingbottom").removeClass("hidden");
	}else{
		$("#myOwnTopicContainer").html("");
		$("#newsloading").removeClass("hidden");
	}
	if(method == "reload"){
		$("#newscontainer").html("");
	}
	$.post("ajaxnews", filter, function(data){
		$("#nomorenews").addClass("hidden");
		$("#moreoldnews").addClass("hidden");
		var html = "";
		var html1 = "<table class=\"normalline\"><tr><td rowspan=\"3\" width=\"45px\" class=\"topveralign\">";
		var html2 = "</td><td class=\"midsize padding5\">";
		var html3 = "</td></tr><tr><td class=\"midsize padding5\">";
		var html4 = "</div><div class=\"hidden greybg grey1border padding10\"></div></td></tr><tr><td class=\"rightalign padding5\">";
		var html5 = "</td></tr></table><div class=\"dottedline\"></div>";
		if(data.srt == null || data.srt == 'null' || data.srt.docs.length <= 0){
			if(method != 'after'){
				$("#newsloading").addClass("hidden");
				$("#newscontainer").html("<div class='blueletter bigsize centeralign'>对不起，没有相应的结果，请重新选择过滤条件</br>若您还未加入任何群组，请选择加入一个群组！</div>");
				
			}else{
				$("#newsloadingbottom").addClass("hidden");
				$("#newscontainer").append("<div class='blueletter bigsize centeralign'>没有更多的结果了</div>");
			}
			return;
		}
		var news = data.srt.docs;
		firstTime = news[0].time;
		lastTime = news[0].time;
		if(news == null){
			$("#nomorenews").removeClass("hidden");
		}else{
			for(i = 0; i < news.length; i++){
				if(firstTime < news[i].time){
					firstTime = news[i].time;
				}
				if(lastTime > news[i].time){
					lastTime = news[i].time;
				}
				html += html1;
				var userId = $("#hiddenuid").val();
				var comment = "<a class=\"blueletter leftmargin_5 comment\" href=\"javascript:void(0)\" rid=\""+news[i].resourceId+"\" type=\""+news[i].type+"\">评论</a>";
				var commentDetail = "<div class=\"arrowbox hidden\">"+
									"<div class=\"arrow\" ><p class=\"arrowborder\">◆</p><p class=\"arrowbg\">◆</p></div>"+
									"<p class=\"cmtcontent\"><img src=\"images/loading.gif\" border=\"0\"/>正在加载评论，请稍候...</p></div>";
				var reType;
				if(news[i].type==-1){
					reType = "list";
				}else{
					reType = "item";
				}
				
				var edit;
			    if(news[i].type!=0){
			    	if(news[i].type!=-1)
			    		edit = "<a class=\"blueletter leftmargin_5\" target=\"_blank\" href=\"resource/preupdateresource?id="+news[i].resourceId+"\">编辑</a>";
			    	else
			    		edit = "<a class=\"blueletter leftmargin_5\" target=\"_blank\" href=\"resource/preupdatelist?id="+news[i].resourceId+"\">编辑</a>";	
			    }
			    if(news[i].type == 0){
			    	view = "<a class=\"blueletter leftmargin_5\" href=\""+news[i].url+"\" target=\"_blank\">查看</a>"
				}
				var collection = "<a class=\"blueletter leftmargin_5\" href=\"javascript:collect("+news[i].resourceId+",'"+reType+"')\">收藏</a>";
				//var addToList = "<a class=\"blueletter leftmargin_5\" href=\"resource/preaddtolist?id="+news[i].resourceId+"&method=itemaddtolist\" rel=\"addtolist\" title=\"选择列表\">加入列表</a>";
				var more = "<a class=\"blueletter leftmargin_5 \" href=\"javascript:void(0)\">更多▼</a>";
							/*"<ul class=\"webwidget_vertical_menu_content\" >"+
							"<li class=\"padding715 dottedline\"><a class=\"greyletter\"  href=\""+news[i].url+"\" target=\"_blank\">新页面打开</a></li>"+
							"<li class=\"padding715 dottedline\"><a class=\"greyletter\" href=\"javascript:updown("+news[i].resourceId+","+news[i].type+",'up')\">好评</a></li>"+
							"<li class=\"padding715\"><a class=\"greyletter\" href=\"javascript:updown("+news[i].resourceId+","+news[i].type+",'down')\">差评</a></li>"+
							"</ul>";*/
				var avatarPart = "";
				var userNamePart = "";
			 	if(news[i].userId != userId){
					avatarPart = "<a href=\"javascript:void(0)\" "+
									"onclick=\"quickCommunicateFn('"+news[i].userId+"')\">"+
									"<img src=\"http://freedisk.free4lab.com/download?uuid="+news[i].avatar+"\""+
									" onerror=\"javascript:this.src='images/user_male.png'\" "+
									"width=\"50\" height=\"50\" border=\"0\" /></a>";
					userNamePart = "<a href=\"javascript:void(0)\" class=\"blueletter\" "+
									"onclick=\"quickCommunicateFn('"+news[i].userId+"')\">"+
									news[i].userName + "</a>";
				}else{
					avatarPart = "<img src=\"http://freedisk.free4lab.com/download?uuid="+news[i].avatar+"\""+
									" onerror=\"javascript:this.src='images/user_male.png'\" "+
									"width=\"50\" height=\"50\" border=\"0\" />";
					userNamePart = news[i].userName;
				}
				html += avatarPart;
				html += html2;
				html += userNamePart;
				html += news[i].operation;
				html += html3;
				html += "<p>" + news[i].description + "</p>";
				html += "<div>";
				if(news[i].imgUrl != '' && news[i].imgUrl != null){
					html += "<img src=\"images/loading.gif\" data-original=\""+news[i].imgUrl+"\" border=\"0\" class=\"lazy rightmargin_10\" " +
							"onload=\"javascript:if(this.height>120) this.height=120; if(this.width>300) this.width=300;\">";
				}
				if(news[i].type == 3){//视频
					html += "<img class=\"pointer\" src=\""+news[i].videoImgUrl+"\" height=\"120\" width=\"160\" onclick=\"showbigvideo(this,'"+news[i].videoUrl+"','"+news[i].swfUrl+"')\"/>"+
							"<img class=\"pointer videoplay\" src=\"images/feedvideoplay.gif\" onclick=\"showbigvideo(this,'"+news[i].videoUrl+"','"+news[i].swfUrl+"')\" />"+
							"<br/><span>视频地址："+
							"<a href=\""+news[i].videoUrl+"\" target=\"_blank\" class=\"blueletter\">"+news[i].videoUrl+"</a></span>";
				}
				if(news[i].type == 2){//链接
					html += "<br /><span>链接地址："+
							"<a class=\"blueletter\" href=\""+news[i].linkUrl+"\" target=\"_blank\">"+news[i].linkUrl+"</a></span>";
				}
				html += html4;
				html += "<div class=\"greyletter leftfloat\">"+news[i].time+"</div>";
				if(news[i].type == 1){//文档
					html += "<a class=\"blueletter leftmargin_5\" href=\"http://freedisk.free4lab.com/download?uuid="+news[i].docUrl+"\">下载</a>";
				}
				
				
				//abeibei
				if(news[i].type!=0){
	     			html += edit;
				}else{
					html += view;
				}
				html += comment;
				html += collection;
				
			/*	if(news[i].type != -1){//列表
					html += addToList;
				}*/
				html += more;
				html += commentDetail;
				html += html5;
			}
			if(method == "after"){
				$("#newsloadingbottom").addClass("hidden");
			}else{
				$("#newsloading").addClass("hidden");
			}
			if(method == "reload"){
				$("#newscontainer").html(html);
			}else if(method == "after"){
				$("#newscontainer").append(html);
			}else if(method == "before"){
				$("#newscontainer").prepend(html);
			}
			$(".webwidget_vertical_menu").webwidget_vertical_menu();
			$(".videoplay").css({"opacity":"0.6", "margin-left":"-97px", "margin-bottom":"43px"});
			$('a[rel*=addtolist]').facebox();
			$("img").lazyload();
			$(".comment").each(function(i){
				$(this).click(function(){
					var arrowbox = $(this).parents("td").find(".arrowbox");
					var id = $(this).attr("rid");
					var type = $(this).attr("type");
					var param = {
							obj: this,
							detail: arrowbox,
							resourceId: id,
							type: type
					}
					showComments(param);
					//showComments(this, arrowbox, id, type);
				})
				$(this).removeClass("comment");
			}) 
			if(news.length < PAGESIZE){
				$("#nomorenews").removeClass("hidden");
			}else{
				$("#moreoldnews").removeClass("hidden");
			}
		}
	})
}

/*
 * 查看全部群组动态 
 */
$("#allnews").click(function(){
	var select = $(this).siblings(".selected");
	if(!$("#selfdefinefilter").hasClass("hidden")){
		$("#selfdefinefilter").addClass("hidden");
	}
	$(select).removeClass("selected");
	$(this).addClass("selected");
	$("#resourcefilterintro").html("[ 所有资源类型 ]");
	resourceTypeList = "all";
	page = 1;
	setFilter(0,0);
	newsFilter(filter, "reload");
	resourceFilterObj.setSelectList(resourceTypeList);
});

/*
 * 查看话题的动态 
 */
$("#easytopic").click(function(){
	var select = $(this).siblings(".selected");
	if(!$("#selfdefinefilter").hasClass("hidden")){
		$("#selfdefinefilter").addClass("hidden");
	}
	$(select).removeClass("selected");
	$(this).addClass("selected");
	$("#resourcefilterintro").html("[ 话题资源 ]");
	resourceTypeList = "SHARE:RESOURCE:TOPIC";
	page = 1;
	setFilter(0,0);
	newsFilter(filter, "reload");
	resourceFilterObj.setSelectList(resourceTypeList);
});

/*
 * 查看文档的动态 
 */
$("#easydoc").click(function(){
	var select = $(this).siblings(".selected");
	if(!$("#selfdefinefilter").hasClass("hidden")){
		$("#selfdefinefilter").addClass("hidden");
	}
	$(select).removeClass("selected");
	$(this).addClass("selected");
	$("#resourcefilterintro").html("[ 文档资源 ]");
	resourceTypeList = "SHARE:RESOURCE:DOC";
	page = 1;
	setFilter(0,0);
	newsFilter(filter, "reload");
	resourceFilterObj.setSelectList(resourceTypeList);
});

/*
 * 查看列表的动态 
 */
$("#easylist").click(function(){
	var select = $(this).siblings(".selected");
	if(!$("#selfdefinefilter").hasClass("hidden")){
		$("#selfdefinefilter").addClass("hidden");
	}
	$(select).removeClass("selected");
	$(this).addClass("selected");
	$("#resourcefilterintro").html("[ 列表资源 ]");
	resourceTypeList = "SHARE:RESOURCE:LIST";
	page = 1;
	setFilter(0,0);
	newsFilter(filter, "reload");
	resourceFilterObj.setSelectList(resourceTypeList);
});

/*
 * 查看视频的动态 
 */
$("#easyvideo").click(function(){
	var select = $(this).siblings(".selected");
	if(!$("#selfdefinefilter").hasClass("hidden")){
		$("#selfdefinefilter").addClass("hidden");
	}
	$(select).removeClass("selected");
	$(this).addClass("selected");
	$("#resourcefilterintro").html("[ 视频资源 ]");
	resourceTypeList = "SHARE:RESOURCE:VIDEO";
	page = 1;
	setFilter(0,0);
	newsFilter(filter, "reload");
	resourceFilterObj.setSelectList(resourceTypeList);
});

/*
 * 查看文章的动态 
 */
$("#easytext").click(function(){
	var select = $(this).siblings(".selected");
	if(!$("#selfdefinefilter").hasClass("hidden")){
		$("#selfdefinefilter").addClass("hidden");
	}
	$(select).removeClass("selected");
	$(this).addClass("selected");
	$("#resourcefilterintro").html("[ 文章资源 ]");
	resourceTypeList = "SHARE:RESOURCE:TEXT";
	page = 1;
	setFilter(0,0);
	newsFilter(filter, "reload");
	resourceFilterObj.setSelectList(resourceTypeList);
});

/*
 * 查看链接的动态 
 */
$("#easyurl").click(function(){
	var select = $(this).siblings(".selected");
	if(!$("#selfdefinefilter").hasClass("hidden")){
		$("#selfdefinefilter").addClass("hidden");
	}
	$(select).removeClass("selected");
	$(this).addClass("selected");
	$("#resourcefilterintro").html("[ 链接资源 ]");
	resourceTypeList = "SHARE:RESOURCE:URL";
	page = 1;
	setFilter(0,0);
	newsFilter(filter, "reload");
	resourceFilterObj.setSelectList(resourceTypeList);
});

/*
 * 根据资源类型过滤动态的类方法调用
 */
var param = {
		clickId : 'selfdefine',
		toggleId : 'selfdefinefilter',
		selectList : resourceTypeList,
		name : 'resourcefilter',
		cookieId : $("#hiddenuid").val() + 'resourceFilterNews',
		cookieName : $("#hiddenuid").val() + 'resourceNameNews',
		selectAllId : 'allresources',
		paramName : 'resourceTypeList',
		submitId : 'selfdefinenews',
		cancelId : 'selfdefinecancel',
		rememId : 'rememberresource',
		type : '资源',
		filterIntroId : 'resourcefilterintro',
		visionType : 'stream',
		resourceContainer : 'myresources',
		page : 1,
		styleFunction : function(result){
			page = 1;
			setFilter(0,0);
			filter.resourceTypeList = result.idList;
			resourceTypeList = result.idList;
			newsFilter(filter, "reload");
		}
};


if(nGetCookie(param.cookieId) != null && nGetCookie(param.cookieId) != 'null' && nGetCookie(param.cookieId) != ''){
	resourceFilterObj.initialize(param);
	$('#'+param.clickId).click();
	$('#'+param.submitId).click();
}else{
	$("#allnews").click();
	resourceFilterObj.initialize(param);
}


/*
 * 点击底部的查看更多群组动态 
 */
function moreOldNews(){
	page = page + 1;
	setFilter(0, lastTime);
	newsFilter(filter, "after");
	
	if(page > MAXPAGE){
		$("#nomorenews").removeClass("hidden");
		$("#moreoldnews").addClass("hidden");
	}
}

/*
 * 页面滚动到底部自动加载更多动态
 */
$(window).scroll( function() {  
	if($("#nomorenews").hasClass("hidden")){
		var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());  
		if ($(document).height() <= totalheight) {
			moreOldNews();
		}
	}
}); 

/*
 * 点击顶部的”还有n条未读动态“ 
 */
function moreNewNews(){
	setFilter(firstTime, 0);
	newsFilter(filter, "before");
}


/*
 * 查看视频 
 */
function showbigvideo(obj,videourl,swfurl){
	$(obj).parent().addClass("hidden");
	$(obj).parent().next().removeClass("hidden").addClass("leftfloat");
	$(obj).parent().next().html(
			"<div>" +
			"<a href='javascript:void(0);' onclick='showsmallvideo(this)' class='bothmargin blueletter'>收起</a>" +
			"<a href='"+videourl+"' class='bothmargin blueletter' target='_blank'>新窗口打开</a>" +
			"</div>" +
			"<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'>" +
			"<embed type='application/x-shockwave-flash' height='356' width='500' flashvars='isAutoPlay=true' " +
			"src='"+swfurl+"' allowfullscreen='true' wmode='transparent' allownetworking='all' allowscriptaccess='sameDomain'>" +
			"</embed></object>"
	);
}

/*
 * 收起视频 
 */
function showsmallvideo(obj){
	$(obj).parent().parent().prev().removeClass("hidden");
	$(obj).parent().parent().addClass("hidden").removeClass("leftfloat");
	$(obj).parent().parent().html("");
}

/*
 * 选择群组动态对话框的取消操作
 */
function closeFacebox(){
	$(document).trigger('close.facebox');
}

/*
 * 选择群组动态对话框的确定操作
 */
function filtByGroup(){
	groupList = '';
	var groupSum = $('#groupslist').find("input[type='checkbox']").length;
	groupNameStr = '';
	$('#groupslist').find("input[type='checkbox']").each(function(){
		if($(this).attr('checked') == 'checked'){
			var id = $(this).attr('id');
			var value = $(this).val();
			groupList += id + ',';
			groupNameStr += '[' + value + ']';
		}
	})
	groupList = groupList.substring(0, groupList.length - 1);
	var selectedGroupSum = groupList.split(',').length;
	if(groupNameStr.length > 25){
		groupNameStr = groupNameStr.substring(0, 20) + '...等' + selectedGroupSum + '个群组  ';
	}
	if(groupSum == selectedGroupSum){
		groupNameStr = '[所有群组]';
	}
	var userId = $("#hiddenuid").val();
	if($('#remembergroup').attr('checked') == 'checked'){
		nSetCookie(userId+'groupsfilter', groupList, 'd30');
		nSetCookie(userId+'groupsname', groupNameStr, 'd30');
	}else{
		nSetCookie(userId+'groupsfilter', null, 'd30');
		nSetCookie(userId+'groupsname', null, 'd30');
	}
	$("#groupfilterintro").text(groupNameStr);
	setFilter(0, 0);
	newsFilter(filter, 'reload');
	closeFacebox();
}

function selectAllGroups(obj){
	if($(obj).attr('checked') == 'checked'){
		$('#groupslist').find("input[type='checkbox']").each(function(){
			$(this).attr('checked', 'checked');
		})
	}else{
		$('#groupslist').find("input[type='checkbox']").each(function(){
			$(this).attr('checked', false);
		})
	}
}
/*
 * 话题字数检查 
 */
function topicCheckFn(obj){
	var topicValue = $(obj).val();
	var length = topicValue.length;
	if(length <= 140){
		var tip = "您已输入了"+ length +"字，还可输入"+ (140-length) +"字";
		$("#topicDescTip").attr("class","lightgreyletter");
		$("#topicDescTip").html(tip);
		return true;
	}else{
		var tip = "您已输入了"+ length +"字，超出了140的最大字数限制";
		$("#topicDescTip").attr("class","redletter ");
		$("#topicDescTip").html(tip);
		return false;
	}			
}
/*
 *  每次页面刷新后默认打开全部动态
 */
$("#topicbox").css({"height":"31px", "width":"320px"});

/*
 * 话题输入框处的隐藏、展开效果 
 */
$("#container").bind('click',function(){
    if(isOutTopic){
    	$("#sendtopic").addClass("hidden");
    	$("#topicbox").css({"height":"31px", "width":"320px"});
    	$("#topic").css({"height":"20px"});
    	$("#publishlist").removeClass("topmargin_5").addClass("topmargin_10");
    }
    isOutTopic = true;
});
$("#topicbox").bind('click',function(){
	isOutTopic = false;
	$("#sendtopic").removeClass("hidden");
	$("#topicbox").css({"height":"auto", "width":"425px"});
});
$('#topic').focus(function(){
	$("#sendtopic").removeClass("hidden");
	$("#topicbox").css({"height":"auto", "width":"425px"});
	$("#publishlist").removeClass("topmargin_10").addClass("topmargin_5");
})
$('#topic').tah({
    moreSpace:20,   // 输入框底部预留的空白, 默认15, 单位像素
    maxHeight:600,  // 指定Textarea的最大高度, 默认600, 单位像素
    animateDur:100  // 调整高度时的动画过渡时间, 默认200, 单位毫秒
});
$('#topic').keyup(function(){		
	topicCheckFn(this);
});
/*
 * 获取用户经验值 
 */
$(document).ready(function() {
	$.ajax({
		url : 'score',
		type : 'post',
		success : function(data) {
			$('#user_score').html('');
			var level=Math.floor(Math.sqrt(data.score/50))+1;
			if(data.score == 0){
				level == 1;
			}
			var nextscore=50*level*level-data.score;					
			$("#user_score").append("<img src='images/userLevel/level"+level+".png' title='等级"+level+"'/>");
			$("#my_score").val(data.score.toString());
			$("#my_level").val(level.toString());
			$("#my_nscore").val(nextscore.toString());
			var base;
			var score = Number(data.score);
			for( base = 1000; base >= 1; base = Math.round(base/10) ){
				var digital = Math.floor(score/base);
				$('#user_score').append('<img src="images/number/'+digital+'.png" />');
				score = Number(score-digital*base);
			}
			
		}
	})
})



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
			 var showResult = selectedGroupName.substring(0,selectedGroupName.length-1);
			 if(showResult.length > 41){
				 showResult = showResult.substr(0,30);
				 showResult += "...";
			 }
			 $("#select").html(showResult);
			 //$("#selectedgroups").val(str);
			// $("#selectedgroups").blur();
			 $("#showSelectedResult").attr("class","greybg");
			 $(document).trigger('close.facebox');
		});				
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

function checkBeforeSendFn(topicId,toMySelfId,selectedIdListId,obj){
	//验证话题是否为空
	var topicValue = $("#"+topicId).val();
	if(topicValue == "" || !topicValue){
		var tip = "发布的话题不能为空";
		$("#topicDescTip").attr("class","redletter ");
		$("#topicDescTip").html(tip);
		return false;
	}
	//验证字数是否在140以内
	var countStatus = topicCheckFn("#topic");
	if( !countStatus ){
		return false;
	}
	//var toMySelfCb = $("#"+toMySelfId).attr("checked");
	var toMySelfCb = $("input[name=toMySelf]:checked").val();
	if(toMySelfCb == 'self1'){
		//alert("发送给了我自己");
		var toMySelf = "self1";
		var type = 0;
		var description = $("#topic").val();				
		if(description.length > 26){
			var name = description.substr(0,25);
			name += "...";
		}else{
			var name = description;
		}	
		
		$(obj).val('...').toggleClass("button graybutton");
		$(obj).attr('disabled', "true");
		
		$.post('upload/publishtopic',{'type':type,'toMySelf':toMySelf,'name':name,'description':description},function(data){
			if(data == undefined){
				fillTipBox("error","话题发布失败");
			}else{
				updateTopicFn(name,description,data.id);
				//发布按钮置为可用				
				$(obj).val('发布').toggleClass("button graybutton");
				$(obj).removeAttr("disabled");
				//话题框收起，并置空
				if(isOutTopic){
			    	$("#sendtopic").addClass("hidden");
			    }
			    isOutTopic = true;
			    $("#topic").val("");
			    $("#topicDescTip").html("");
			    $("#topic").height(20);
			    //清空选择群组的历史记录
			    $("#selectMySelf0").attr("checked","checked");
			    $("#shareToMyselfId").attr("class","hidden");
				$("#shareToGroupId").attr("class","lightgreyletter");	
				$("#shareToGroupDiv").attr("class","leftfloat greybg");
				$("#shareToMyselfDiv").attr("class","leftfloat");	
			    
			    $("#"+selectedIdListId).val("");
			    $("#selectedvalue").val("");				    
			    $("#showSelectedResult").attr("class","hidden");
			    $("#select").html("");	
			    
				fillTipBox("success","话题发布成功");
			}
		});
	}else{
		var selectedIdList = $("#"+selectedIdListId).val();
		if(selectedIdList == "" || !selectedIdList){
			var tip = "必须至少选择一个群组";
			$("#topicDescTip").attr("class","redletter ");
			$("#topicDescTip").html(tip);
			return false;
		}else{
			var toMySelf = "self0";
			var writegroups = selectedIdList;
			var type = 0;
			var description = $("#topic").val();
			if(description.length > 26){
				var name = description.substr(0,25);
				name += "...";
			}else{
				var name = description;
			}			
			
			$(obj).val('...').toggleClass("button graybutton");
			$(obj).attr('disabled', "true");
			$.post('upload/publishtopic',{'type':type,'toMySelf':toMySelf,'name':name,'description':description,'writegroups':writegroups},function(data){
				if(data == undefined){
					fillTipBox("error","话题发布失败");
				}else{
					updateTopicFn(name,description,data.id);
					//发布按钮置为可用
					$(obj).val('发布').toggleClass("button graybutton");
					$(obj).removeAttr("disabled");
					//话题框收起，并置空
					if(isOutTopic){
				    	$("#sendtopic").addClass("hidden");
				    }
				    isOutTopic = true;
				    $("#topic").val("");
				    $("#topicDescTip").html("");
				    $("#topic").height(20);
				  //清空选择群组的历史记录
				    $("#"+selectedIdListId).val("");
				    $("#selectedvalue").val("");				    
				    $("#showSelectedResult").attr("class","hidden");
				    $("#select").html("");					
					fillTipBox("success","话题发布成功");
				}
			});
		}
	}
}

//挤入一条数据
function updateTopicFn(topicName,topicDesc,topicId){
	//初始化所需数据
	var myurl = "resource?id="+topicId,
	myavatar = $("#myOwnAvatarId").val(),
	myname = $("#myOwnNameId").val(),
	myoperation = " 发布了话题 ";
	var mytime = getNowFormatDate();
	
	$("#newsloading").removeClass("hidden");
	var html = "";
	var html1 = "<table class=\"normalline\"><tr><td rowspan=\"3\" width=\"45px\" class=\"topveralign\">";
	var html2 = "</td><td class=\"midsize padding5\">";
	var html3 = "</td></tr><tr><td class=\"midsize padding5\">";
	var html4 = "</td></tr><tr><td class=\"rightalign padding5\">";
	var html5 = "</td></tr></table><div class=\"dottedline\"></div>";
				
	
	
	html += html1;
	var view = "<a class=\"blueletter leftmargin_5\" href=\""+myurl+"\" target=\"_blank\">查看</a>";
	var comment = "<a class=\"blueletter leftmargin_5 comment\" href=\"javascript:void(0)\" rid=\""+topicId+"\" type=\"0\">评论</a>";
	var commentDetail = "<div class=\"arrowbox hidden\">"+
	"<div class=\"arrow\" ><p class=\"arrowborder\">◆</p><p class=\"arrowbg\">◆</p></div>"+
	"<p class=\"cmtcontent\"><img src=\"images/loading.gif\" border=\"0\"/>正在加载评论，请稍候...</p></div>";
	var collection = "<a class=\"blueletter leftmargin_5\" href=\"javascript:collect("+topicId+",'item')\">收藏</a>";;
	var more = "<a class=\"blueletter leftmargin_5 webwidget_vertical_menu\" href=\"javascript:void(0)\">更多▼</a>"+
				"<ul class=\"webwidget_vertical_menu_content\">"+
				"<li class=\"padding715\"><a class=\"greyletter\"  href=\""+myurl+"\" target=\"_blank\">新页面打开</a></li>"+
				"<li class=\"padding715\"><a class=\"greyletter\" href=\"javascript:updown("+topicId+",0,'up')\">好评</a></li>"+
				"<li class=\"padding715\"><a class=\"greyletter\" href=\"javascript:updown("+topicId+",0,'down')\">差评</a></li>"+
				"</ul>";
	var avatarPart = "";
	var userNamePart = "";
	avatarPart = "<img src=\"http://freedisk.free4lab.com/download?uuid="+myavatar+"\""+
						" onerror=\"javascript:this.src='images/user_male.png'\" "+
						"width=\"50\" height=\"50\" border=\"0\" />";
	userNamePart = myname;
	
	html += avatarPart;
	html += html2;
	html += userNamePart;
	html += myoperation;
	html += html3;
	html += "<p>" + topicDesc + "</p>";
	
	html += html4;
	html += "<div class=\"greyletter leftfloat\">"+mytime+"</div>";

	html += view;
	html += comment;
	html += collection;
	html += more;
	html += commentDetail;
	html += html5;

	$("#newsloading").addClass("hidden");
	$("#myOwnTopicContainer").prepend(html);
	$(".webwidget_vertical_menu").webwidget_vertical_menu();
	$('a[rel*=addtolist]').facebox();
	$(".comment").each(function(i){
		$(this).click(function(){
			var arrowbox = $(this).parents("td").find(".arrowbox");
			var id = $(this).attr("rid");
			var type = $(this).attr("type");
			var param = {
					obj: this,
					detail: arrowbox,
					resourceId: id,
					type: type
			}
			showComments(param);
		})
		$(this).removeClass("comment");
	}) 
}

//格式化当前时间
function getNowFormatDate() {
	function formate(str){
		if (str >= 1 && str <= 9) {
			str = "0" + str;
	    }
		return str;
	}
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;    

    var currentdate = date.getFullYear() + seperator1 + formate(month) + seperator1 + formate(date.getDate())
            + " " + formate(date.getHours()) + seperator2 + formate(date.getMinutes())
            + seperator2 + formate(date.getSeconds());
    return currentdate;
}

//wangxy
function del(type,param){
	$.ajax({
		type : 'post',
		url : 'resource/delete',
		data : {
			type: type,
			id : param,
		},
		success : function(data) {
			 fillTipBox('success','删除成功，该窗口稍等会重新加载。');
			// window.setTimeout("window.close()", 2000);
			 document.location.reload();
		}
	});
}

