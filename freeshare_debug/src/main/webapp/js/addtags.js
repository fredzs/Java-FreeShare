function addtags(tags, id, resourceType){
	var tags = "aaa bbb ccc";
	$.getJSON('addtags',
			 { id : id, tags:tags},
			 function(data) {
				$.each(data.tagMap,function(i,value){
					var id = value.id
					var name = value.name;
					
					$('#bl_tags').append("<div class= \"topmargin_10\">"
											+ "<a  class = \"blackletter midsize\" href=\"javascript:void(0)"
											+ id
											+ "\">"
											+ name
											+ "</a>"
											+ "</div>");
				})
	})
}