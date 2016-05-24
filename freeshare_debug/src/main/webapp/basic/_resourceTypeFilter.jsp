<!--
使用该文件的地方：
basic/items.jsp
basic/news.jsp
group/group_resource.jsp
-->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<div class="padding715 grey1border topmargin_5 hidden" id="selfdefinefilter">
			<table>
				<tr>
					<td class="leftalign" width="70px">
						资源类型：
					</td>
					<td class="leftalign padding5">
						<input type="checkbox" id="allresources">
						<label for="allresources">全选</label>
						<input type="checkbox" value="列表资源" name="resourcefilter" id="SHARE:FMT:LIST">
						<label for="SHARE:FMT:LIST">列表</label>
						<input type="checkbox" value="话题资源" name="resourcefilter" id="SHARE:ITEM:TOPIC">
						<label for="topic">话题</label>
						<input type="checkbox" value="文档资源" name="resourcefilter" id="SHARE:ITEM:DOC">
						<label for="doc">文档</label>
						<input type="checkbox" value="链接资源" name="resourcefilter" id="SHARE:ITEM:URL">
						<label for="url">链接</label>
						<input type="checkbox" value="视频资源" name="resourcefilter" id="SHARE:ITEM:VIDEO">
						<label for="video">视频</label>
						<input type="checkbox" value="文章资源" name="resourcefilter" id="SHARE:ITEM:TEXT">
						<label for="article">文章</label>
					</td>
				</tr>
				<tr>
					<td></td>
					<td class="leftalign padding5">
						<div class="redletter" id="filtermsg"></div>
						<input type="checkbox" id="rememberresource" value="rememberresource" />
						<label for="rememberresource">下次访问时记住以上选择</label>
						<input type="button" value="确定" class="button" id="selfdefinenews" />
						<a href="javascript:void(0)" class="bluebutton" id="selfdefinecancel" >取消</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="greyletter topmargin_5 bottommargin_5" id="filterintro">
			显示 <span id="groupfilterintro">[ 所有群组 ]</span>    <span id="resourcefilterintro">[ 所有资源类型 ]</span> 的动态
		</div>
	</body>
</html>