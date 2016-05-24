<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>选择列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>
<form method="post" action="add">
<p>id:<input type="text" name="id" /> </p>
<p>uid:<input type="text" name="uid" /> </p>
<p>value:<input type="text" name="originalValue" /> </p>
<p>editvalue:<input type="text" name="editValue" /> </p>
<p>type:<input type="text" name="type" /> </p>
<input type="submit" value="Add" />
</form>
</body>
</html>