/*
 对应的jsp文件是：
basic/browse.jsp
*/
//得到资源表的belonglists
function getbllists(param){
	$.getJSON('resource/getbllists', 
			{ id : param },
			function(data) {
				if(data.listsList.length > 0){
					 //$('#bl_list').append("<div id=\"bl_list_title\" class=\"strong midsize\">所属列表</div>");
					
					 //$('#bl_list').append("<a href=\"\">修改</a>");
					 //$('#bl_list').append("</div>");
					
					 var selectedvalue = "";
					 var selectedIdList = "";
				
					$.each( data.listsList,function(i, value) {
						var l_desc = value.description;
						var reTag = /\&[a-zA-Z]{1,10};/g;
						var reTag1 = /<(?:.|\s)*?>/g;
						l_desc = l_desc.replace(reTag, "");
						l_desc = l_desc.replace(reTag1, "");
						if (l_desc.length > 35) {
							l_desc = l_desc.substring(0, 35) + "...";
						}
						var l_name = value.name;
						if (l_name.length > 20) {
							l_name = l_name.substring(0, 20) + "...";
						}
						$('#bl_list').append("<div class= \"leftmargin_10 topmargin_5 bottommargin_5\">"
									+ "<a  class = \"greyletter\" href=\"list?id="
									+ value.id
									+ "\">"
									+ l_name
									+ "</a>"
									+ "<br/><span class=\"lightgreyletter\">"
									+ l_desc
									+ "</span></div>");
						selectedvalue += value.id+":"+value.name+",";
						selectedIdList += value.id+",";
					});
					 $('#bl_list').prepend("<div id=\"bl_list_title\" class=\"strong midsize\">所属列表  <a href=\"resource/preaddtolist?id="+data.id+"&selectedvalue="+selectedvalue+"&method=itemaddtolist\" rel=\"addtolist\" title=\"选择列表\" class=\"strong normalsize\">  修改</a></div>");
					 $('#bl_list').append("<input id=\"selectedvalue2\" class=\"hidden\" type=\"text\" value=\""+selectedvalue+"\"></input>");
					 $('#bl_list').append("<input id=\"selectedIdList\" class=\"hidden\" type=\"text\" value=\""+selectedIdList+"\"></input>");
					 $('a[rel*=addtolist]').facebox();
					 $('#bl_list_title').css({'padding-left':'20px','background':'url(images/bllist.png) left center no-repeat'});
					 $('#bl_list').addClass("dottedline bottommargin_10");
				}
			})
}