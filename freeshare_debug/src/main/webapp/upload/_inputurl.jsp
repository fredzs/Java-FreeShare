<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title>可读组</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<form action="upload/url" method="post" >
		<input type="text" name="url" id="searchText" value="" class="squareinput" onkeyup="searchFn()" />
   
    </form>
    <s:include value="/basic/_globlejs.jsp" />
    
</body>
</html>