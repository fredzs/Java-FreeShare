/*
 对应的jsp文件是：
error/permission_denied.jsp
*/
//得到资源或者列表的belonggroups
function getblgroups(id, resourceType){
	$.getJSON('resource/getblgroups2',
			 { id : id, resourceType:resourceType},
			 function(data) {
				 if(data.groupList.length > 0){
					 //$('#bl_groups').append("<div id=\"bl_group_title\" class=\"strong midsize\">所属群组</div>");
					 $('#bl_group_title').css({'padding-left':'20px','background':'url(images/blgroup.png) left center no-repeat'});
					 //$('#bl_groups').addClass("dottedline bottommargin_10");
					 //$('#bl_groups').append("<div class=\"list\">");
				     var bl_groupsContent = "<div class=\"list\">";
				     $.each(data.groupList,function(i,value){
						var g_avatar = "images/group_head.png";
						if(value.groupInfo.hasOwnProperty("avatar")){
							g_avatar = value.groupInfo.avatar;
						}
						//alert("g_avatar:"+g_avatar);
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
						bl_groupsContent += "<div><table class=\"formtable\"><tr><td rowspan=\"2\" width=\"70\"><img src='http://freedisk.free4lab.com/download?uuid="
							                + g_avatar
							                + "' onerror=\"javascript:this.src=\"images/user_male.png\"\" width='50' height='50' border='0'/></td>"
											+ "<td class=\"leftalign\"><a  class = \"greyletter\" href=\"group/resource?groupId="
											+ value.groupId
											+ "\">"
											+ g_name
											+ "</a>"
											+ "<br/><span class=\"lightgreyletter\">"
											+ g_desc
											+ "</span></td>";
						
						if(value.extend == "public"){
							bl_groupsContent += "<td class=\"rightfloat\"><a id=\"apply\"  href=\"group/resource?groupId="
												+ value.groupId
												+"&from=register\" >加入该组</a></td></tr></table></div>";
						  }
						  else{
							  
							  bl_groupsContent += "<td class=\"rightfloat\"><a id=\"apply\"  title =\"申请加入\" rel=\"facebox\" size=\"s\" href=\"members/preapply?groupId="
									+ value.groupId
									+"\" >申请加入</a></td></tr></table></div>";
						  }
			
				     });
				     bl_groupsContent += "</div>";

				     $('#bl_groups').html(bl_groupsContent);
				     $("a[rel=facebox]").facebox();
				     getStyle();
				     //$('#bl_groups').append("</table></div>");
				     //*/
				}
	});
}

