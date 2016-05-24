<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模版列表--Free分享</title>
<link href="ueditor/themes/default/ueditor.css" rel="stylesheet"/>
</head>
<body>
 	<div class="xxlheight grey1border">
         <s:iterator id = "fm" value = "fmList"> 
    	 <div class="leftfloat">
    	  <table><tr>
           <td> 
            <a href="javascript:loadfw('<s:property value = "#fm.id"/>')" ><img src="<s:property value = "#fm.image_path"/>"/></a>
            </td> 
            <tr><td width="40" class="centeralign"><s:property value = "#fm.name"/></td>
         </tr></table>
          </div>
    	 </s:iterator>
    </div><br />
</body>
</html>