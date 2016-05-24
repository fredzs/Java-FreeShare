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
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp?index=group"></s:include>
		<div id="inner" class="content">
			<div>
				<h1>创建群组</h1>
			</div>
			<div class="dottedbox">
			<!--  
				<form id="avatarform" action="group/previewavatar" method="post" enctype="multipart/form-data">
					-->
					<table>
						<tr>
						    <td width="100px">群组头像</td>
							<td width="200px" height="100px" class="centeralign bottomveralign">
								<!--  <img id="showavatar" src='images/grouphead.png' onload="javascript:if(this.width>150&&this.width>this.height)this.width=150;else if(this.height>100&&this.height>this.width)this.height=100;" />-->
								<img id="showavatar" src='images/grouphead.png' width="100" height="100" />
							</td>
							<td class="leftalign" width="90px">
							<!--  
								<input id="photo_1" type="file" name="photo" size="1" class="txtfile"></input>
								-->
								
							</td>
							<td><a class="greybutton" href="group/uploadavatar" rel="facebox" title="上传头像"> 上传头像</a></td>
						</tr>
					</table>
				<!--  </form>-->
			<!--  	<table id="avatarinfo" class="xsheight">
				<tr><td><br /><br /></td></tr>-->
			</table>
				<form id="create" name="newgroup" action="group/creategroup">
					<table>
						<tr>
							<td>群组名称<span class="redletter">*</span> <br />
							<span class="lightgreyletter">少于20个字</span>
							</td>
							<td><input type="text" id="name" name="name" class="squareinputbig blue1border" />
								<span id="nameText" class="redletter"></span></td>
						</tr>
						<tr>
							<td>群组描述<span class="redletter">*</span> <br />
							<span class="lightgreyletter">少于250个字</span>
							</td>
							<td>
							<textarea id="desc" cols="30" rows="5"  class="editboxbig blue1border"
									name="desc"></textarea> 
							<span id="descText" class="redletter"></span></td>
						</tr>
						<tr>
							<td class="topveralign"><br />群组类型<span class="redletter">*</span></td>
							<td class="topveralign"><br />
							 <input type="radio" id="private"  value="private" name="permission"
								 /> 私有（仅组内人员可见，无法被搜索）<br/>
							<input type="radio" id="normal" name="permission"
								value="normal" checked="checked"/>普通（所有用户可见，可被搜索，需要群组管理员审核方可加入）<br/>
							<input type="radio" id="public" name="permission"
								value="public" />公开（所有用户可见，可被搜索，无需群组管理员审核即可加入）</td>
						</tr>
						<tr>
							<td>
								<input type="hidden" name="avatar" id="hidden2"/>
								<input type="hidden" name="hiddenUuid" id="hidden"/>
								<input type="hidden" id="permission"  name="groupPermission" value="" ></input>
							</td>
							<td><br/> 
							<input class="button" id="addmem" type="button"
								value="完成并邀请" onclick="preinvite()"></input>
							<input class="graybutton" type="button" value="完成" 
								onclick="check()"></input>
								</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/group/group.js"></script>
	<script src="js/group/checkinput.js"></script>

	
</body>
</html>