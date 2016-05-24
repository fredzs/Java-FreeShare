/*
 对应的jsp文件是：
basic/items.jsp
group/group_resource.jsp
*/
var resourceFilterObj = {
		param : {},
		
		initialize : function(options){
			this.param = {};
			this.param = $.extend(this.param, options);
			this.clickAndToggle();
			this.selectAll();
			this.confirmFilter();
			this.cancelFilter();
		},
		
		setSelectList : function(selectList){
			this.param.selectList = selectList;
		},
		
		setPage : function(page){
			this.param.page = page;
		},
		
		//点击按钮展开资源复选框区域，并根据cookie或js变量中的内容加载选择的内容
		clickAndToggle : function(){
			//参数说明，点击id为clickId的按钮会触发toggleId区域的toggle动作
			//selectList为js中记录选择结果的变量，在页面刷新后更新
			//name为复选框的统一name，用来操作复选框
			//cookieId为cookie中保存选择结果的id
			//selectAllId为全选按钮的id
			var param = this.param;
			var clickId = param.clickId,
				toggleId = param.toggleId,
				name = param.name,
				cookieId = param.cookieId,
				cookieName = param.cookieName,
				filterIntroId = param.filterIntroId,
				rememId = param.rememId,
				selectAllId = param.selectAllId;
			$('#'+clickId).click(function(){
				$(this).siblings('.selected').removeClass('selected');
				$(this).addClass('selected');
				if( $('#'+toggleId).hasClass('hidden') ){
					$('#'+toggleId).removeClass('hidden');
					selectList = resourceFilterObj.param.selectList;
					if(nGetCookie(cookieId) != null && nGetCookie(cookieId) != 'null' && nGetCookie(cookieId) != ''){
						selectList = GB2312UnicodeConverter.ToGB2312(nGetCookie(cookieId));
						selectName = GB2312UnicodeConverter.ToGB2312(nGetCookie(cookieName));
						$('#'+filterIntroId).html(selectName);
						$('#'+rememId).attr('checked',true);
					}
					if(selectList == 'all'){
						$('input[name=\''+name+'\']').each(function(){
							this.checked = true;
						})
						$('#'+selectAllId).attr('checked', true);
					}else{
						$('#'+selectAllId).attr('checked', false);
						var selectArray = [];
						selectArray = selectList.split(',');
						$('input[name=\''+name+'\']').each(function(){
							this.checked = false;
							for( var k in selectArray){
								if($(this).attr('id') == selectArray[k]){
									this.checked = true;
								}
							}
						})
						if(selectArray.length == $('input[name=\''+name+'\']').length){
							$('#'+selectAllId).attr('checked', true);
						}
					}
				}else{
					$('#'+toggleId).addClass('hidden');
				}
			});
		},
		
		//全选/全不选动作绑定
		selectAll : function(){
			//selectAllId是全选框的id，name是它控制的复选框的name
			var param = this.param;
			var selectAllId = param.selectAllId,
				name = param.name;
			$('#'+selectAllId).click(function(){
				if($(this).attr('checked') == 'checked'){
					$("input[name='"+name+"']").each(function(){
						$(this).attr('checked', 'checked');
					})
				}else{
					$("input[name='"+name+"']").each(function(){
						$(this).attr('checked', false);
					})
				}
			})
			$("input[name='"+name+"']").click(function(){
				if($(this).attr('checked') == 'checked'){
					var checkedNum = 0;
					$("input[name='"+name+"']").each(function(){
						if($(this).attr('checked') == 'checked'){
							checkedNum ++;
						}
					})
					var sum = $("input[name='"+name+"']").length;
					if(checkedNum == sum){
						$('#'+selectAllId).attr('checked', 'checked');
					}
				}else{
					$('#'+selectAllId).attr('checked', false);
				}
			})
		},
		
		//提交选择结果的方法
		confirmFilter : function(){
			//submitId为确认按钮的id
			//name为复选框的统一name
			//toggleId为收起的div的id，为null则没有这个步骤，例如群组筛选
			//cookieId为cookie中保存选择的记录id的key
			//cookieName为cookie中保存选择的记录name的key
			//rememId为“记住此次选择”的复选框id
			//type为文字表述的类型name（如“资源”）
			//submitFunction为提交数据的function
			var param = this.param;
			var submitId = param.submitId,
				name = param.name,
				toggleId = param.toggleId,
				cookieId = param.cookieId,
				cookieName = param.cookieName,
				rememId = param.rememId,
				type = param.type,
				submitFunction = param.submitFunction,
				filterIntroId = param.filterIntroId,
				visionType = param.visionType,
				styleFunction = param.styleFunction,
				ajaxLoad = this.ajaxLoad;
			$('#'+submitId).click(function(){
				var result = {
						idList : '',
						nameList : ''
				};
				$("input[name='"+name+"']").each(function(){
					if($(this).attr('checked') == 'checked'){
						var id = $(this).attr('id');
						var value = $(this).val();
						result.idList += id + ',';
						result.nameList += '[' + value + ']';
					}
				})
				if(result.idList == ''){
					alert('请至少选择一种资源类型！');
				}else{
					result.idList = result.idList.substring(0, result.idList.length - 1);
					resourceFilterObj.param.selectList = result.idList;
					var selectLength = result.idList.split(',').length;
					if(result.nameList.length > 25){
						result.nameList = result.nameList.substring(0, 20) + '...等' + selectLength + '个' + type;
					}
					if($("input[name='"+name+"']").length == selectLength){
						result.idList = 'all';
						result.nameList = '[所有'+type+']';
					}
					if($('#'+rememId).attr('checked') == 'checked'){
						nSetCookie(cookieId, result.idList, 'd30');
						nSetCookie(cookieName, result.nameList, 'd30');
					}else{
						nSetCookie(cookieId, null, 'd30');
						nSetCookie(cookieName, null, 'd30');
					}
					if(toggleId != null){
						$('#'+toggleId).addClass('hidden');
					}
					$('#'+filterIntroId).html(result.nameList);
					if(visionType == 'list'){
						resourceFilterObj.param.page = 1;
						ajaxLoad();
					}else if(visionType == 'stream'){
						styleFunction(result);
					}
				}
			})
				
		},
		//点击取消选择
		cancelFilter : function(){
			//cancelId为"取消"按钮的id
			//toggleId为复选区域的id
			var param = this.param;
			var cancelId = param.cancelId,
				toggleId = param.toggleId;
			$('#'+cancelId).click(function(){
				$('#'+toggleId).addClass('hidden');
			})
		},
		//ajax加载资源
		ajaxLoad : function(){
			var param = this.param;
			var url = param.url,
				paramName = param.paramName,
				divPage = param.divPage,
				listStyle = resourceFilterObj.listStyle,
				showDivPage = resourceFilterObj.showDivPage,
				page = param.page;
			var num = $("#num_id").val();
			$.post(url, {
					'resourceTypeList' : resourceFilterObj.param.selectList,
					'page' : page,
					'num' : num
				}, function(data) {
					if (divPage) {
							resourceFilterObj.param.totalNum = data.num;
							document.getElementById('num_id').value =  data.num;
							
						showDivPage();
					}
					listStyle(data);
				})
		},
		//资源加载为list形式
		listStyle : function(data){
			var param = this.param;
			var resourceContainer = param.resourceContainer;
			var page = resourceFilterObj.param.page;
			var htmlSum = '';
			if(data.resource.length > 0){
				var htmlEach = [];
				for(var i =0; i < data.resource.length; i++){
					var htmlItem = [], j = 0;
					htmlItem[j++] = '<div>';
					htmlItem[j++] = '<table class="formtable"><tr><td rowspan="2">';
					if(data.resource[i].type == -1){
						htmlItem[j++] = '<img src="images/list.png" border="0" />';
					}else{
						htmlItem[j++] = '<img src="images/resource.png" border="0" />';
					}
					htmlItem[j++] = '</td><td class="leftalign">';
					htmlItem[j++] = '<a class="blackletter" target="_blank"  href="resource?id=' + data.resource[i].id + '">';
					htmlItem[j++] = data.resource[i].name + '</a><br />';
					
					data.resource[i].description = data.resource[i].description.replace(/<[^>]+>/g,"");
					
					if(data.resource[i].description.length > 80){
						data.resource[i].description = data.resource[i].description.substring(0, 80)+'...';
					}
					
					htmlItem[j++] = data.resource[i].description + '</td><td class="topveralign" width="150px">';
					htmlItem[j++] = data.resource[i].publicTime + '</td></tr></table></div>';
					htmlEach[i] = htmlItem.join('');
				}
				htmlSum = htmlEach.join('');
			}else{
				htmlSum = '没有符合选择条件的资源';
			}
			if(resourceContainer == '' || resourceContainer == null || resourceContainer == undefined){
				$('#inner').append(htmlSum);
			}else{
				$('#'+resourceContainer).html('<div class="list"></div>');
				$('#'+resourceContainer+' .list').html(htmlSum);
			}
			getStyle();
		},
		//分页部分的显示
		showDivPage : function(){
			var pageSize = 10,
				pageSum = Math.ceil(resourceFilterObj.param.totalNum/pageSize),
				showPage = 6,
				currentPage = this.param.page,
				resourceContainer = this.param.resourceContainer;
			var pageHtml = [],
				i = 0;
			pageHtml[i++] = '<span class="divpage"><span>共 ' + pageSum + ' 页 </span>';
			pageHtml[i++] = '<a class="text" href="javascript:resourceFilterObj.changePage(1)">首页</a>';
			currentPage = resourceFilterObj.param.page;
			if(currentPage > 1){
				var lastPage = Number(Number(currentPage)-1);
				pageHtml[i++] = '<a class="pagenum" href="javascript:resourceFilterObj.changePage('+lastPage+')"><<</a>';
			}
			if(currentPage < showPage){
				for(var j = 1; j <= showPage && j <= pageSum; j++){
					if(j == currentPage){
						pageHtml[i++] = '<span class="currpage">'+j+'</span>';
					}else{
						pageHtml[i++] = '<a class="pagenum" href="javascript:resourceFilterObj.changePage('+j+')">'+j+'</a>';
					}
				}
			}else if(currentPage > pageSum - showPage){
				for(var j = pageSum-showPage; j <= pageSum ; j++){
					if(j == currentPage){
						pageHtml[i++] = '<span class="currpage">'+j+'</span>';
					}else if(j > 0){
						pageHtml[i++] = '<a class="pagenum" href="javascript:resourceFilterObj.changePage('+j+')">'+j+'</a>';
					}
				}
			}else{
				for(var j = currentPage-showPage/2; j <= currentPage+showPage/2; j++){
					if(j == currentPage){
						pageHtml[i++] = '<span class="currpage">'+j+'</span>';
					}else{
						pageHtml[i++] = '<a class="pagenum" href="javascript:resourceFilterObj.changePage('+j+')">'+j+'</a>';
					}
				}
			}
			if(currentPage < pageSum){
				var nextPage = Number(Number(currentPage)+1);
				pageHtml[i++] = '<a class="pagenum" href="javascript:resourceFilterObj.changePage('+nextPage+')">>></a>';
			}
			pageHtml[i++] = '<a class="text" href="javascript:resourceFilterObj.changePage('+pageSum+')">尾页</a>';
			var html = pageHtml.join('');
			$('#divpagedown').remove();
			$('#divpageup').remove();
			$('#'+resourceContainer).after('<div class="rightalign" id="divpagedown">'+html+'</div>');
			$('#'+resourceContainer).before('<div class="rightalign" id="divpageup">'+html+'</div>');
		},
		changePage : function(page){
			resourceFilterObj.param.page = page;
			resourceFilterObj.ajaxLoad();
		}
}


