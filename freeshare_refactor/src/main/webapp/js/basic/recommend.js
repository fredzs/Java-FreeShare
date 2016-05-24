/*
 对应的jsp文件是：
basic/browse.jsp
basic/news.jsp
basic/newsearch.jsp
*/
function feedback(resourceId, resourceType){
	$.post('reco/feedback', {resourceId:resourceId,resourceType:resourceType}, function(){
		fillTipBox('success','操作成功，此资源将不会再出现在您的推荐列表中');
		$("#reco"+resourceId).remove();
	});
}
//猜你喜欢推荐
var recItemFunc = function(data) {
    var	results = jQuery.parseJSON(data.result).Results
	if(results.length > 0){
		 $('#reco_you').append("<div id=\"reco_you_title\" class=\"strong midsize\">猜你喜欢</div>");
		 $('#reco_you_title').css({'padding-left':'20px','background':'url(images/youlike.png) left center no-repeat'});
	 }
	$.each(results, function(i,value){
		console.log(value.Object)
		var url = value.Object.Url;
		var name = value.Object.Name;
		var resourceId = value.Object.IntegerID;
		var resourceType = value.Object.Type;
		var description = value.Object.Description;
		// 替换掉url的item为resource
		url = url.replace("item","resource");
		
		if (name.length > 20) {
			name.substring(0,20)
					+ "...";
		}
		if (description.length > 35) {
			description = description.substring(0,35)
					+ "...";
		}
		$('#reco_you').append("<div class= \"leftmargin_10 dottedline topmargin_5 padding5 bottommargin_5 greybg\" "
				+ "id=\"reco" + resourceId + "\" >"
				+ "<table><tr><td><a class = \"blueletter\" href=\""
				+ url
				+ "\">"
				+ name
				+ "</a></td><td class=\"topveralign rightalign\" width=\"20px\">"
				+ "<a title=\"不再推荐此资源\" class=\"nodecoration feedbackhref radius\" href=\"javascript:feedback("
				+ resourceId + "," + resourceType
				+ ")\" > </a>"
				+ "</td></tr></table>"
				+ "<br/><span class=\"grayletter\">"
				+ description
				+ "</span></div>");
	})
	$(".feedbackhref").css({"background" : "url(http://front.free4lab.com/css/images/icons.png) #eaeaea 0 -64px no-repeat",
							"display" : "inline-block",
							"width" : "15px",
							"height" : "16px"});
}
//相似资源推荐
var recItemFunc1 = function(data) {

    var	results = jQuery.parseJSON(data.result).Results
	if(results.length > 0){
		 $('#reco_you1').append("<div id=\"reco_you_title1\" class=\"strong midsize\">相似资源</div>");

		 $('#reco_you_title1').css({'padding-left':'20px','background':'url(images/youlike.png) left center no-repeat'});
	 }
	$.each(results, function(i,value){
		console.log(value.Object);
		var url = value.Object.Url;
		var name = value.Object.Name;
		var resourceId = value.Object.IntegerID;
		var resourceType = value.Object.Type;
		var description = value.Object.Description;
		// 替换掉url的item为resource
		url = url.replace("item","resource");
		
		if (name.length > 20) {
			name.substring(0,20)
					+ "...";
		}
		if (description.length > 35) {
			description = description.substring(0,35)
					+ "...";
		}
		
		$('#reco_you1').append("<div class= \"leftmargin_10 dottedline topmargin_5 padding5 bottommargin_5 greybg\" "
				+ "id=\"reco" + resourceId + "\" >"
				+ "<table><tr><td><a class = \"blueletter\" href=\""
				+ url
				+ "\">"
				+ name
				+ "</a></td><td class=\"topveralign rightalign\" width=\"20px\">"
				+ "<a title=\"不再推荐此资源\" class=\"nodecoration feedbackhref radius\" href=\"javascript:feedback("
				+ resourceId + "," + resourceType
				+ ")\" > </a>"
				+ "</td></tr></table>"
				+ "<br/><span class=\"grayletter\">"
				+ description
				+ "</span></div>");
	})
	$(".feedbackhref").css({"background" : "url(http://front.free4lab.com/css/images/icons.png) #eaeaea 0 -64px no-repeat",
		"display" : "inline-block",
		"width" : "15px",
		"height" : "16px"});
}

var recGroupFunc = function(data) {

     var	results = jQuery.parseJSON(data.result).Results
	//var	results = jQuery.parseJSON(data.result)
	if(results.length > 0){
		 $('#re_group').append("<div id=\"re_group_title\" class=\"strong midsize\">群组推荐</div>");
	     $('#re_group_title').css({'padding-left':'20px','background':'url(images/blgroup.png) left center no-repeat'});
	     $('#re_group').append("<div class=\"dottedline\"></div>");
	}
    var uid = $("#hiddenuid").val();
	$.each(	results, function(i,value){
		var url = value.Object.Url;
		var name = value.Object.Name;
		var desc = value.Object.Description;
		var groupId = url.substring(url.indexOf("=")+1);
		if (name.length > 12) {
			name.substring(0,11)+ "...";
		}
		if (desc.length > 23) {
			desc = desc.substring(0,22)
					+ "...";
		}
		var imageUrl = value.Object.ImageUrl;
		if(!imageUrl) imageUrl = "images/user_male.png";
		$('#re_group').append(
					"<div class= \"topmargin_10\"><table>" +
					"<tr>" +
					"<td width=\"60\" rowspan=\"2\" class=\"topveralign\">" +
					"<img class=\"topmargin_5\" src=\"" + imageUrl + "\"" + 
					" onerror='javascript:this.src=\"images/user_male.png\"' width=\"50\" height=\"50\" border=\"0\" />" +
					"</td>" + 
					"<td class=\"middleveralign\">" +
					"<a class = \"blueletter\" title=\"" +
					value.Object.Name +
					"\" href=\"" + url+ "\" target=\"_blank\" >" + name + "</a>" +
					"<a href=\"members/preapply?groupId="+groupId+"\" rel=\"facebox\" title=\"申请加入\">"+
					"<img src=\"images/engage.png\" onerror='javascript:this.src=\"images/user_male.png\"' width=\"15\" height=\"15\"/>"+
					"</a>"+
					"</td>" +
					"</tr>" + 
					"<tr><td colspan=\"2\" class = \"greyletter\">" + desc +"</td></tr></table>" + 
					"</div>"
			);
	})
}

//recotype 要推荐的类型
//resourceType 当前页面资源类型 128 为UNKOWN_TYPE
//resourceId 当前页面资源id 0为默认值
function recommend(recoType){
	resourceType = arguments[1] ? arguments[1] : 128;
	resourceId = arguments[2] ? arguments[2] : 0;
	recByUser = arguments[3] != undefined ? arguments[3] : 1;
	func =  recItemFunc;
	if(recByUser == 0 ){ func = recItemFunc1; }
	$.getJSON('reco/recommend'
	        , { recoType : recoType, resourceType : resourceType,resourceId : resourceId , recByUser : recByUser }
			, func
	);
}

function recommendGroup(){
	$.getJSON('reco/recommend', { recoType : 2 ,recoNum:7},recGroupFunc);
}
