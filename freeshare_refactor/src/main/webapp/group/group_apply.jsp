<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
  <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>群组资源--Free分享</title>
  <s:include value="/template/_head.jsp" />  
</head>
<body>
	<div id="container">
	  	<s:include value="/template/_pub_banner.jsp"/>
	  	<s:include value="/template/_banner.jsp?index=group"></s:include>
	  	<div id="inner"  class="content">
	     	<s:include value="_mygroup.jsp?menu=group_resource"></s:include>
	  <%--   	<s:include value="/basic/_resourceTypeFilter.jsp" /> --%>
	  <!--   	<div id="groupresources"></div> -->
	  	</div><!--#inner-->
	 	<s:include value="/template/_footer.jsp" />
	</div><!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/group_administration/administration.js"></script>
	<script src="js/basic/filter.js"></script>
	<script>
    $(document).ready( function() {
        $("#rememberresource").attr("checked", true); //默认复选框选中
    })
    </script>
	<script>
		var url = 'group/ajaxresource'+location.href.split('resource')[1];
		var param = {
				clickId : 'group_resource',
				toggleId : 'selfdefinefilter',
				selectList : 'all',
				name : 'resourcefilter',
				cookieId : <s:property value = "#session.userId"/>+'resourceFilterGroup',
				cookieName : <s:property value = "#session.userId"/>+'resourceFilterGroupName',
				selectAllId : 'allresources',
				paramName : 'resourceTypeList',
				submitId : 'selfdefinenews',
				cancelId : 'selfdefinecancel',
				rememId : 'rememberresource',
				type : '资源',
				filterIntroId : 'resourcefilterintro',
				visionType : 'list',
				resourceContainer : 'groupresources',
				page : 1,
				divPage : true,
				url : url
		};
		$('#groupfilterintro').html('');
		resourceFilterObj.initialize(param);
		$('#'+param.clickId).attr('href','javascript:void(0)');
		$('#'+param.clickId).click();
		$('#'+param.submitId).click();
	</script>
</body>
</html>
