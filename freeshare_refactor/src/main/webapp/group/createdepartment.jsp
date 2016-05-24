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
		<div id="tipboxWrapper"></div>
		<div id="inner" class="content">
			<div>
				<h1>创建部门</h1>
			</div>
			<div class="dottedbox">

			</table>
				<form id="create" name="newgroup" action="group/creategroup2">
					<table>
						<tr>
							<td>部门名称<span class="redletter">*</span> <br />
							<span class="lightgreyletter">少于20个字</span>
							</td>
							<td><input type="text" id="name" name="name" class="squareinputbig blue1border" />
								<span id="nameText" class="redletter"></span></td>
						</tr>
						<tr>
							<td>部门描述<span class="redletter">*</span> <br />
							<span class="lightgreyletter">少于250个字</span>
							</td>
							<td>
							<textarea id="desc" cols="30" rows="5"  class="editboxbig blue1border"
									name="desc"></textarea> 
							<span id="descText" class="redletter"></span></td>
						</tr>
						<tr>
							<td class="topveralign"><br />部门类型<span class="redletter">*</span></td>
							<td class="topveralign"><br />
							 <input type="radio" id="private"  value="" name="permission" checked="checked"
								 /> 部门（仅部门内人员查看，其他用户无法搜索） <br/>
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