<!--
使用该文件的地方：
error/has_no_groups.jsp
group/groups.jsp
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
<title>创建群组--Free分享</title>
<s:include value="/template/_head.jsp" />
<link href="css/input.css" rel="stylesheet"/>
<link rel="stylesheet" href="http://front.free4lab.com/css/public.css" />
</head>
<body>
	<div id="container">
		
		<div class="banner" style="position: fixed; top: 25px; right: 0px; left: 0px; z-index: 999;">
    		<a href=""><img id="logo" src="images/1-logo.png" border="0"></a>
    		<span class="nav">
        		<a href="group/useradmin"><img src="images/banner_user.png" border="0" class="cur"></a>
        		<a href="basic/resourceAdmin.jsp"><img src="images/banner_recourse.png" border="0" class=""></a>
        		<a href="basic/shareAdmin.jsp"><img src="images/banner_share.png" border="0" class=""></a>
        		<a href="basic/logAdmin.jsp"><img src="images/banner_day.png" border="0" class=""></a>
    		</span>
		</div>
		
		<div id="inner" class="content">
			<div>
				<h1>创建企业</h1>
			</div>
			<div class="dottedbox">

				<form id="create" name="newgroup" action="group/createenterprise">
					<table>
						<tr>
							<td>企业名称<span class="redletter">*</span> <br />
							<span class="lightgreyletter">少于20个字</span>
							</td>
							<td><input type="text" id="name" name="name" class="squareinputbig blue1border" />
								<span id="nameText" class="redletter"></span></td>
						</tr>
						<tr>
							<td>企业公告<span class="redletter">*</span> <br />
							<span class="lightgreyletter">少于250个字</span>
							</td>
							<td>
							<textarea id="desc" cols="30" rows="5"  class="editboxbig blue1border"
									name="desc"></textarea> 
							<span id="descText" class="redletter"></span></td>
						</tr>
						<tr>
							<td>
								<input type="hidden" name="avatar" id="hidden2"/>
								<input type="hidden" name="hiddenUuid" id="hidden"/>
								<input type="hidden" id="permission"  name="groupPermission" value="" ></input>
							</td>
							<td><br/> 
							<input class="graybutton" type="button" value="完成" 
								onclick="check()"></input>
								</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/group/group.js"></script>
	<script src="js/group/checkinput.js"></script>
	<script>
	function closeFacebox()
	{
		//window.close();		
	}
	</script>

	
</body>
</html>