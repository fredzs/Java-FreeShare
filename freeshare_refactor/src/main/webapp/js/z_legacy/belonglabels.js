function getbltags(id, resourceType){
	if(resourceType == null || resourceType == ""){
		resourceType = -1;
	}
	$.getJSON('resource/getbllabels',
			 { id : id, resourceType:resourceType},
			 function(data) {
				 console.log(data.tagMap);
				 var count = 0;
				 $('#bl_tags').append('<span class="darkblueletter strong midsize padding5">标签:</span>');
				 $.each(data.toList,function(i,value){
					 count ++;
					 var id = value.id
					 var name = value.name;
					 $('#bl_tags').append("<span class= \"padding10\">"
											+ "<a  class = \"belongtags blueletter\" href=\"javascript:void(0)"
											+ "\" title=\"搜索含有此标签的资源\">"
											+ name
											+ "</a></span>");
				 });
				 if(count == 0){
					 $('#bl_tags').html('<div class="darkblueletter strong midsize">此资源尚无标签</div>');
				 }else{
					 $('.belongtags').click(function(){
						 location.href = 'search?query=&resourceTypeList=LABEL:'+encodeURI($(this).html());
					 })
				 }
				 
				 
	})
}
