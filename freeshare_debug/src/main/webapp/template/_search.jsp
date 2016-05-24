<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form action="search" id="search" method="post" target="_blank">
	<input type="text" class="squareinputmid greyletter rightmargin_10" 
					id="squery" placeholder="查找群组、列表和资源" />
	<!-- 折中的办法，暂时不用hidden的input了，以后还得改回来 -->
	<input type="hidden" id="hiddensquery" name="query" />
	<div id="searchtips" class="searchtips grey1border padding5 hidden" style="z-index:1000;">
		<div id="searchwords" class="dottedline"></div>
		<div class="padding5 bottommargin_5">
			<div class="bottommargin_5 darkblueletter leftfloat">搜索类型</div>
			<div id="searchtypes" class="leftfloat hidden">
				<input type="radio" id="searchselecttype" value="all" checked="checked" name="searchtype" /> 
				<label for="searchselecttype">自定义</label>
			</div>
			<div class="clear"></div>
			<div id="searchmytypes" class="greybox padding5 radius">
				<div class="bottommargin_5">
					<input checked="checked" type="checkbox" id="selectalltypes" value="all" />
					<label for="selectalltypes">全选</label>
				</div>
				<div class="searchmytypes">
					<input type="checkbox" id="SHARE:FMT:LIST:1" value="SHARE:FMT:LIST" checked="checked"/> 
					<label class="rightmargin_20" for="SHARE:FMT:LIST:1">列表</label>
					<input type="checkbox" id="SHARE:ITEM:TEXT:1" value="SHARE:ITEM:TEXT" checked="checked" /> 
					<label class="rightmargin_20" for="SHARE:ITEM:TEXT:1">文章</label>
					<input type="checkbox" id="SHARE:ITEM:URL:1" value="SHARE:ITEM:URL" checked="checked" /> 
					<label class="rightmargin_20" for="SHARE:ITEM:URL:1">链接</label>
					<input type="checkbox" id="SHARE:ITEM:VIDEO:1" value="SHARE:ITEM:VIDEO" checked="checked" /> 
					<label class="rightmargin_20" for="SHARE:ITEM:VIDEO:1">视频</label>
					<input type="checkbox" id="SHARE:ITEM:DOC:1" value="SHARE:ITEM:DOC" checked="checked" /> 
					<label class="rightmargin_20" for="SHARE:ITEM:DOC:1">文档</label>
					<input type="checkbox" id="SHARE:ITEM:TOPIC:1" value="SHARE:ITEM:TOPIC" checked="checked" /> 
					<label class="rightmargin_20" for="SHARE:ITEM:TOPIC:1">话题</label>
					<input type="checkbox" id="SHARE:FMT:GROUP:1" value="SHARE:FMT:GROUP" checked="checked" /> 
					<label class="rightmargin_20" for="SHARE:FMT:GROUP:1">群组</label>
				</div>
			</div>
			<input type="hidden" name="resourceTypeList" id="searchtype" value="all" />
		</div>
		<div class="dottedline"></div>
		<div class="padding5">
			<div class="bottommargin_5 darkblueletter leftfloat">搜索范围</div>
			<div id="searchgroups" class="leftfloat">
				<input type="radio" id="searchallgroup" value="all" checked="checked" name="searchrange" /> 
				<label for="searchallgroup">所有群组</label>
				<input type="radio" id="searchselectgroup" value="all" name="searchrange" /> 
				<label for="searchselectgroup">自定义</label>
			</div>
			<div class="clear"></div>
			<input type="hidden" name="groupIds" id="searchgroupIds" value="all" />
			<div class="greybox padding5 radius hidden" id="searchmygroups">
				<img src="images/loading.gif" border="0"/>加载中，请稍候...
			</div>
			
		</div>
		<div class="dottedline"></div>
		<div id="searcherror" class="redletter padding5"></div>
		<div class="leftalign">
			<input type="submit" class="button" id="searchtipsbtn" value="搜索" />
			<input type="button" class="greybutton" id="searchtipscancel" value="取消" />
		</div>
	</div>
</form>