<!--
使用该文件的地方：
upload/upload_list.jsp
upload/upload_resource.jsp
view/update_resource.jsp
view/update_list.jsp
-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<input type="text" id="addresourcetags" class="squareinput greyletter" placeholder="添加资源标签" value="<s:property value="labels"/>"/>
<div id="newtags" class="hidden"></div>
