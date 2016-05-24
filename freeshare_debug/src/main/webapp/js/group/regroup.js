/*
 对应的jsp文件是：
basic/recotonewuser.jsp
error/has_no_groups.jsp
*/
	var reGroupIds = nGetCookie('reGroupIds');
	if(reGroupIds != null && reGroupIds != 'null'){
		reGroupIds = GB2312UnicodeConverter.ToGB2312(reGroupIds);
	}else{
		reGroupIds = '';
	}
	$.getJSON('reco/regroups',{reGroupIds:reGroupIds},function(data) {
		if(data.groupList.length > 0){
			 $('#re_group').append("<div id=\"re_group_title\" class=\"strong midsize\">群组推荐</div>");
			 $('#re_group_title').css({'padding-left':'20px','background':'url(images/blgroup.png) left center no-repeat'});
			 $('#re_group').append("<div class=\"dottedline\"></div>");
		 }
		var newReGroupIds = '';
		$.each(data.groupList,function(i,value) {
			var desc = value.groupInfo.desc;
			if (desc.length > 23) {
				desc = desc.substring(0,22) + "...";
			}
			var name = value.groupInfo.name;
			if(name.length > 12){
				name = value.groupInfo.name.substring(0,11) + "..."
			}
			$('#re_group').append(
					"<div class= \"topmargin_10\"><table>" +
					"<tr>" +
					"<td width=\"60\" rowspan=\"2\" class=\"topveralign\">" +
					"<img class=\"topmargin_5\" src=\"http://freedisk.free4lab.com/download?uuid=" + value.groupInfo.avatar + "\"" + 
					" onerror='javascript:this.src=\"images/user_male.png\"' width=\"50\" height=\"50\" border=\"0\" />" +
					"</td>" + 
					"<td class=\"middleveralign\">" +
					"<a class = \"greyletter\" title=\"" +
					value.groupInfo.name +
					"\" href=\"group/resource?groupId=" + value.groupId + "\">" + name + "</a>" +
					"</td>" +
					"</tr>" + 
					"<tr><td colspan=\"2\" class = \"lightgreyletter\">" + desc +"</td></tr></table>" + 
					"</div>"
			);
			newReGroupIds += value.groupId+','
		})
		newReGroupIds = newReGroupIds.substring(0, newReGroupIds.length-2);
		nSetCookie('reGroupIds', newReGroupIds, 'd3');
	});
