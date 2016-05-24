<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base
	href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑审核--Free分享</title>
<s:include value="/template/_head.jsp" />
<link href="ueditor/themes/default/ueditor.css" rel="stylesheet" />

</head>
<body>
<div id = "edit_id">
</div>
<s:include value="/basic/_globlejs.jsp" />
	<script type="text/javascript">
	//得到资源或者列表的belonggroups
	$(document).ready( function() {
		$.getJSON('editt',
				{idd:3},
				 function(data) {
					$('#edit_id').append(data.edittion.edittion);
				 }
		)
	})
	</script>
</body>
</html>
