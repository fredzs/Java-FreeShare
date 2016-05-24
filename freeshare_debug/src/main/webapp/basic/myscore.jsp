<!--
使用该文件的地方：
basic/news.jsp
-->
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
<title>我的经验值</title>
</head>
<body>
<div>
	<div class="leftfloat leftmargin_10 topmargin_5">
						<img src="http://freedisk.free4lab.com/download?uuid=<s:property value = "#session.avatar"/>" 
							 onerror="javascript:this.src='images/user_male.png'" width="100" height="100" border="0"/>
	</div>
	<table cellpadding="3px" style="width:240px">
		<tr>
			<td  class="rightalign greyletter strong"><span>当前经验值：</span></td>
			<td><span  id="current_score" class=""></span></td>
		</tr>
		<tr>
			<td class="rightalign greyletter strong"><span>当前等级：</span></td>
			<td><span  id="current_level" class=""></span></td>
		</tr>
		<tr>
			<td class="rightalign greyletter strong">下一等级还需经验值：</td>
			<td><span  id="next_score" class=""></span></td>
		</tr>
		
	</table>
	
	<div style="margin-top:40px">
	<span class="strong greyletter">经验值算法：</span>
		<span class="greyletter">
	    	发表资源+1,
			浏览资源+0.1,
			发表的资源得到一个好评+2
	    </span>
	</div>
</div>
	<script type="text/javascript">
	$(document).ready( function() {			
				$("#current_score").append($("#my_score").val());				
				$("#current_level").append("<img src='images/userLevel/level"+$("#my_level").val()+".png' title=\"等级"+$("#my_level").val()+"\"/>");						
				$("#next_score").append($("#my_nscore").val());
	})
	</script>
</body>
</html>