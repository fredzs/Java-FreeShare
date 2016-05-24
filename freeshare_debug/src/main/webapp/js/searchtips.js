var searchTips = (function($){
	
	var _thisObj;
	var _selectWord = -1;
	
	$.fn.searchTips = function(){
		_thisObj = this;
		$(this).val('');
		
		$("#searchtypes input[type=radio]").click(function(){_setSubmitTypes(this)});
		$("#searchgroups input[type=radio]").click(function(){_setSubmitGroups(this)});
		//取消按钮
		$("#searchtipscancel").click(function(){
			$('#searchtips').addClass('hidden');
			$(_thisObj).val('');
		});
		//搜索按钮，检查是否各项都填写了
		$("#searchtipsbtn").click(function(){
			if($("input[name=searchtype]").val() == ''){
				$("#searcherror").html("未输入搜索词!");
				return false;
			}
			if($("input[name=resourceTypeList]").val() == ''){
				$("#searcherror").html("未选择搜索类型!");
				return false;
			}
			if($("input[name=groupIds]").val() == ''){
				$("#searcherror").html("未选择搜索范围!");
				return false;
			}
			$('#searchtips').addClass('hidden');
		});
		
		function inputHandler(e){
			if($(this).val() != ''){
				$("#hiddensquery").val($(this).val());
				_setLocation(_thisObj);
				$(window).resize(function(){
					_setLocation(_thisObj);
				});
				if($('#searchtips').hasClass('hidden')){
					$('#searchtips').removeClass('hidden');
					$("#searcherror").html("");
				}
				var e = e || window.event;
				var code = e.keyCode;
				_getSearchWords( code );
			}else{
				$('#searchtips').addClass('hidden');
			}
		}
		return this.keyup(inputHandler);
	}
	
	//将要提交的资源类型数据写入对应的input
	var _setSubmitTypes = function(obj){
		$("#searchtype").val("all" );
		if($(obj).attr("id") == "searchalltypes"){
			$("#searchmytypes").addClass("hidden");
			$("#searchtype").val('all');
		}else{
			$("#searchmytypes").removeClass("hidden");
			$("#searchmytypes input[type=checkbox]").attr("checked", "checked");
		}
		
		var selectAllObj = $("#selectalltypes");
		var otherObjs = $(".searchmytypes input[type=checkbox]");
		var resultInput = $("#searchtype");
		selectAll(selectAllObj, otherObjs, resultInput);
	}
	
	//将要提交的群组范围数据写入对应的input
	var _setSubmitGroups = function(obj){
		$("#searchgroups").val("");
		if($(obj).attr("id") == "searchallgroup"){
			$("#searchgroups").val("all");
			if(!$("#searchmygroups").hasClass("hidden")){
				$("#searchmygroups").addClass("hidden");
			}
		}else{
			if($("#searchmygroups").hasClass("hidden")){
				$("#searchmygroups").removeClass("hidden");
				
				if($("#searchmygroups input[type=checkbox]").length == 0){
					var myGroupUrl = "getmygroupsajax";
					$.post(myGroupUrl, {}, function(data){
						if(data.lists.length == 0){
							$("#searchmygroups").html("您还没有加入任何群组");
							return false;
						}else{
							$("#searchmygroups").html("");
							$("#searchmygroups").append("<div class=\"bottommargin_5\"><input checked=\"checked\"" +
									" type=\"checkbox\" id=\"selectallgroups\" value=\"all\" />" +
									"<label for=\"selectallgroups\">全选</label></div>" +
									"<div class=\"leftfloat rightmargin_10 searchmygroups\" id=\"searchmygroups1\"></div>" +
									"<div class=\"leftfloat searchmygroups\" id=\"searchmygroups2\"></div>");
							$.each(data.lists, function(i, value){
								var name = value.name;
								if(name.length > 9){
									name = name.substring(0,8)+'...';
								}
								var container;
								if( i % 2 == 0){
									container = $("#searchmygroups1");
								}else{
									container = $("#searchmygroups2");
								}
								$(container).append("<div><input type=\"checkbox\" id=\"searchgroup" + value.id + 
										"\" checked=\"checked\" title=\"" + value.name + "\" value=\"" + value.id + "\" /> <label title=\"" + value.name + "\" for=\"searchgroup" + 
										value.id + "\">" + name + "</label></div>");
							});
							$("#searchmygroups").append("<div class=\"clear\"></div>");
							var selectAllObj = $("#selectallgroups");
							var otherObjs = $(".searchmygroups input[type=checkbox]");
							var resultInput = $("#searchgroupIds");
							selectAll(selectAllObj, otherObjs, resultInput);
						}
					});
				}
			}
		}
	}
	
	//全选/全不选按钮的设置
	var selectAll = function(selectAllObj, otherObjs, resultInput){
		$(selectAllObj).click(function(){
			if( $(this).attr("checked") == "checked" ){
				$(otherObjs).attr("checked", "checked");
				$(resultInput).val("all");
			}else{
				$(otherObjs).attr("checked", false);
				$(resultInput).val("");
			}
		});
		$(otherObjs).click(function(){
			$(resultInput).val("");
			var selectedNum = 0;
			otherObjs.each(function(){
				if( $(this).attr("checked") == "checked" && this != selectAllObj){
					selectedNum ++;
					if($(resultInput).val() == ""){
						$(resultInput).val($(this).val());
					}else{
						$(resultInput).val($(resultInput).val() + ',' + $(this).val());
					}
				}
				if(selectedNum == otherObjs.length){
					$(selectAllObj).attr("checked", "checked");
					$(resultInput).val('all');
				}else{
					$(selectAllObj).attr("checked", false);
				}
			})
		});
	}	
	
	//设置搜索框的位置，如果页面较宽则和输入框左对齐，否则和输入框右对齐
	var _setLocation = function(obj){
		var left = $(obj).offset().left;
		if($("body").width() - left > $('#searchtips').width()){
			var offsetLeft = left - $('#inner').offset().left;
			$('#searchtips').css({'left' : offsetLeft});
		}else{
			var offsetLeft = left - ($('#searchtips').width() - $(obj).width()) - $('#inner').offset().left;
			$('#searchtips').css({'left' : offsetLeft});
		}
	}
	
	//根据用户输入获取搜索提示词
	var _getSearchWords = function( code ){
		if(code == 38 || code == 40){
			var num = $("#searchwords>div").length;
			$("#searchwords>div").removeClass("ddgreybg");
			if(code == 40){
				_selectWord = (_selectWord + 1) % num;
				$("#searchwords"+_selectWord).addClass("ddgreybg");
				$("#squery").val($("#searchwords"+_selectWord).html());
				$("#hiddensquery").val($("#searchwords"+_selectWord).html());
			}else if(code == 38){
				if(_selectWord == -1){//直接按了up键
					_selectWord = num - 1;
				}else{
					_selectWord = (_selectWord + num - 1) % num;
				}
				$("#searchwords"+_selectWord).addClass("ddgreybg");
				$("#squery").val($("#searchwords"+_selectWord).html());
				$("#hiddensquery").val($("#searchwords"+_selectWord).html());
			}
		}else{
			var inputWord = $(_thisObj).val();
			var getKeyWordUrl = 'searchTips';
			$.post(getKeyWordUrl, {'tips':inputWord}, function(data){
				$("#searchwords").html("");
				$.each(data.searchTips, function(i, value){
					$("#searchwords").append("<div class=\"searchwords padding5\" id=\"searchwords"+i+"\">"+value+"</div>")
				});
				$(".searchwords").click(function(){
					$("#searchwords>div").removeClass("ddgreybg");
					$(this).addClass("ddgreybg");
					$("#squery").val($(this).html());
					$("#hiddensquery").val($(this).html());
				});
				$(".searchwords").hover(function(){
					$(this).addClass("darkgreybg");
				},function(){
					$(this).removeClass("darkgreybg");
				});
			})
		}
	}
	return {
		selectAll : selectAll
	};
})(jQuery);
$('#squery').searchTips();