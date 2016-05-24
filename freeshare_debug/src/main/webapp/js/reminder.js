//页面加载完毕后生成tip，用来提示新上线的功能。
$(document).ready(function(){
	var position = {
			left: "200px",
			top: "200px",
	};
	var title = "新功能上线喽！";
	var content = "“猜你喜欢”功能强力上线";
	var formore = "查看详情";
	var url = baseAddr+"introduction.jsp";
	var userid = $(".pub_banner").attr("userid");
	var currunt_cookie = userid+"new_function2";//此处"new_function"为当前上线的功能
	var new_cookie = userid+"new_function";//此处"new_function"为新上线的功能
	//注：如果不想要foremore和url，直接把formore置成""就好了
    var mytip = {
			title:title,
			content:content,
			formore:formore,
			url:url,//新功能介绍页面
			hasclose:true	//默认带小叉叉
	};
    
    if (( nGetCookie(new_cookie) == "false" ) || ( nGetCookie(new_cookie) == null ))
	{
	    $(".sidebar").after("<div id=\"browerbox\"></div>");
		$("#browerbox").tipbox(mytip);
		$("#browerbox").css({"left":position.left, "top":position.top});
		nSetCookie(new_cookie,"true","d30");
		nSetCookie(currunt_cookie,"true","s120");
		currunt_cookie = new_cookie;
	}
    //nSetCookie(currunt_cookie,"false","d30");
});