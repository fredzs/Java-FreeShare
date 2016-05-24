/*
 对应的jsp文件是：
basic/browse.jsp
*/
//得到资源或者列表的belonggroups
function getblgroups(id, resourceType){
	$.getJSON('resource/getblgroups',
			 { id : id, resourceType:resourceType},
			 function(data) {
				 if(data.groupList.length > 0){
					
					 var selectedvalue = ",";
					 var selectedIdList = "";

					 $.each(data.groupList,function(i,value){
							var g_desc = value.groupInfo.desc;
							if (g_desc.length > 35) {
								g_desc = g_desc.substring(0,34)
										+ "...";
							}
							var g_name = value.groupInfo.name;
							if (g_name.length > 20) {
								g_name.substring(0,20)
										+ "...";
							}
							$('#bl_groups').append("<div class= \"leftmargin_10 topmargin_5 bottommargin_5\">"
													+ "<a  class = \"greyletter\" href=\"group/resource?groupId="
													+ value.groupId
													+ "\">"
													+ g_name
													+ "</a>"
													+ "<br/><span class=\"lightgreyletter\">"
													+ g_desc
													+ "</span></div>");
							
							selectedvalue += value.groupId+":"+g_name+",";
							selectedIdList += value.groupId+",";
					 });
					
					 var resourceId = $("#resourceId").val();
				
					 $('#bl_groups').prepend("<div id=\"bl_group_title\" class=\"strong midsize\">所属群组<a href=\"resource/preaddtogroup?id="+data.id+"&selectedvalue="+selectedvalue+"&submittype="+resourceId+"\" rel=\"editGroup\" title=\"选择群组\" class=\"strong normalsize\">  修改</a></div>");
					 $('#bl_groups').append("<input id=\"selectedvalue\" class=\"hidden\" type=\"text\" value=\""+selectedvalue+"\"></input>");
					 $('#bl_groups').append("<input id=\"selectedIdList\" class=\"hidden\" type=\"text\" value=\""+selectedIdList+"\"></input>");
					 $('a[rel*=editGroup]').facebox();
					 $('#bl_group_title').css({'padding-left':'20px','background':'url(images/blgroup.png) left center no-repeat'});
					 $('#bl_groups').addClass("dottedline bottommargin_10");
				 }
				
				
	
	})
}

