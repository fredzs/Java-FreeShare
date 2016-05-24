/*
 对应的jsp文件是：
upload/upload_list.jsp
upload/upload_resource.jsp
view/update_resource.jsp
view/update_list.jsp
*/
(function($){
	var _myTags = [];
	var _inputTags = [];
	var _newTags = [];
	var _inputDom = {};
	
	$.fn.addTags = function(settings){
		_inputDom = this;
		$(_inputDom).focus(function(){
			if($('#mytagscontainer').length == 0){
				_getMyTags(settings.getTagsUrl, _inputDom);

			}
			if($('#mytagscontainer').hasClass('hidden')){
				$('#mytagscontainer').removeClass('hidden');
			}
		});
		function inputHandler(){
			_inputTags = $(_inputDom).val().split(' ');
			$('.mytags').addClass('darkgreybg blackletter').removeClass('orangebg whiteletter');
			_newTags = [];
			$.each(_inputTags, function(i, value){
				var index = $.inArray(value, _myTags);
				if( index >= 0 ){
					if($('div[title="'+_myTags[index]+'"]').hasClass('darkgreybg')){
						$('div[title="'+_myTags[index]+'"]').removeClass('darkgreybg blackletter').addClass('orangebg whiteletter');
					}
				}else{
					_newTags.push(value);
					$('#'+settings.newTags).html(_newTags.join(' '));
				}
			});
		}
		return this.keyup(inputHandler);
	};
	
	var _myTagsClickHandler = function(obj){
		var selectTag = $(obj).attr('title');
		_inputTags = $(_inputDom).val().split(' ');
		var index = $.inArray(selectTag, _inputTags);
		if( index >= 0 ){
			$(_inputDom).val($(_inputDom).val().replace(selectTag+' ', '').replace(selectTag, ''));
			$(obj).addClass('darkgreybg blackletter').removeClass('orangebg whiteletter');
		}else{
			var length = $(_inputDom).val().length;
			if(length == 0 || $(_inputDom).val().substring(length-1) == ' '){
				$(_inputDom).val($(_inputDom).val()+selectTag+' ');
			}else{
				$(_inputDom).val($(_inputDom).val()+' '+selectTag+' ');
			}
			$(obj).removeClass('darkgreybg blackletter').addClass('orangebg whiteletter');
		}
		_inputTags = $(_inputDom).val().split(' ');
		_inputDom.focus();
	}
	
	var _getMyTags = function(getTagsUrl, obj){
		$.post(getTagsUrl, {}, function(data){
			if(data.toList.length > 0){
				$.each(data.toList, function(i, value){
					_myTags.push(value.name);
				});
			}
			var myTagArray = [];
			myTagArray[0] = '<div id="mytagscontainer" class="topmargin_10 bottommargin_10 dottedbox padding5">'+
							'<div class="padding5 greyletter">我的常用标签:（点击可进行添加）</div>';
			$.each(_myTags, function(i, value){
				if( i < 20 ){
					if( $.inArray(value, _inputTags) == -1 ){
						myTagArray[i+1] = '<div class="mytags blackletter darkgreybg padding5 leftmargin_5 leftfloat rightmargin_10 bottommargin_5 pointer" title="'+value+'">'+value+'</div>';
					}else{
						myTagArray[i+1] = '<div class="mytags orangebg whiteletter padding5 leftmargin_5 leftfloat rightmargin_10 bottommargin_5 pointer" title="'+value+'">'+value+'</div>';
					}
				}
			});
			myTagArray[myTagArray.length] = '<div class="clear"></div></div>';
			var myTagHtml = myTagArray.join('');
			$(obj).after(myTagHtml);
			$('.mytags').click(function(){_myTagsClickHandler(this);});
			$('.mytags').attr('onselectstart', 'return false').attr('onselect', 'document.selection.empty()');
		})
	};
	
})(jQuery);