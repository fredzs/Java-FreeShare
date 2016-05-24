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
<title>群组动态--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>

<body>
	<div id="container">
		<div id="tipboxWrapper"></div>
		<s:include value="/template/_pub_bannerfornews.jsp" />
		<s:include value="/template/_bannerfornews.jsp?index=index" />
		<div id="inner" class="content dottedline">
			<div class="sidebar">
				
				<div id="company">	
				 	<a id="change" href="javascript:void(0)" title="管理" class="sgraybutton" onclick = "group()">切换至企业版</a>
				 <!-- 	<a id="manage" href="group/useradmin?type=2" class="sgraybutton hidden" target="_blank" title="管理" >管理</a>	 -->
				</div>
				
				<div id="re_group">
				</div>
				<div id="notice">
				</div>
				<%-- <span class="strong midsize">群组推荐</span> <br />
				<div class="dottedline"></div>
				<div id="re_group">
				</div> --%>
			</div>
			<div class="main leftmargin_0">
				<div class="leftalign greybox topmargin_10 bottommargin_10">
					<div class="leftfloat leftmargin_5 topmargin_5">
						<a href="basic/myscore.jsp" rel="facebox" size="s" title="我的经验值"><img src="http://freedisk.free4lab.com/download?uuid=<s:property value = "#session.avatar"/>" 
							 onerror="javascript:this.src='images/user_male.png'" width="50" height="50" border="0"/></a>
					</div>
					<div class="midsize leftmargin_10 leftfloat topmargin_5">
						<table><tr>
							<td width="65px" class="centeralign">
								<a class="blackletter nodecoration" href="basic/myscore.jsp" rel="facebox" class="nodecoration" size="s" title="我的经验值"><s:property value = "#session.userName"/></a>
								<input type="hidden" id="hiddenuid" value="<s:property value = "#session.userId"/>" />
								<div class="centeralign topmargin_5">
								<a href="basic/myscore.jsp" rel="facebox" size="s" id="user_score" title="我的经验值"></a>
								<input id="my_score" class="hidden" type="text" value=""/>
								<input id="my_level" class="hidden" type="text" value=""/>
								<input id="my_nscore" class="hidden" type="text" value=""/>
								</div>
							</td>
						</tr></table>
					</div>
					<div class="topicbox topmargin_10 leftmargin_10 bottommargin_10 rightmargin_5 leftfloat" id="topicbox">
						<textarea class="normalsize greyletter" id="topic" placeholder="发表新话题，试试@你的好友吧"></textarea>
						<div class="leftalign"><span id="topicDescTip"></span></div>
						<div id="sendtopic" class="rightalign greybg hidden">
							<div class="leftfloat leftalign">
								<div id="shareToGroupDiv" class="leftfloat darkgreybg">
									<input type="radio" value="self0" name="toMySelf" id="selectMySelf0" checked="checked" onclick="selectMySelfFn('toMySelf')"/>
								    <label class="greyletter" for="selectMySelf0">群组可见</label>
								    <p id="shareToGroupId" class="lightgreyletter" >
								    	&nbsp;
								    	<a href="resource/preaddtogroup" rel="facebox" title="归属群组" class="blueletter">
								    		选择
								    	</a>
								    	可以查看和编辑此资源的群组&nbsp;&nbsp;
								    </p>
									<input id="selectedIdList" type="text" name="selectedgroups" class= "hidden" />
							    	<input id="selectedvalue" type="text" name="selectedvalue" class= "hidden" />
							    	<input type="text" id="myOwnNameId" value="<s:property value = '#session.userName'/>" class="hidden"/>
									<input type="text" id="myOwnAvatarId" value="<s:property value = '#session.avatar'/>" class="hidden"/>
								</div>
								<div id="shareToMyselfDiv" class="leftfloat">
									<input type="radio" name="toMySelf" value="self1" id="selectMySelf1" onclick="selectMySelfFn('toMySelf')"/>
									<label class="greyletter" for="selectMySelf1">仅自己可见</label>
									<p id="shareToMyselfId" class="hidden">&nbsp;此资源将对其他用户不可见&nbsp;&nbsp;</p>
								</div>
								<div class="clear"></div>
								
							</div>
							<div class="rightfloat">								
								<input type="button" value="发布" class="button bottommargin_5 topmargin_5" onclick="checkBeforeSendFn('topic','toMySelfId','selectedIdList',this)"/>
							</div>
							<div class="clear leftalign">
								<div id="showSelectedResult" class="hidden">
									&nbsp;&nbsp;已选择：<span id="select"></span>	    	
								</div>
							</div>
						</div>
					</div>
					<div id="publishitem" class="leftfloat leftmargin_5 topmargin_10">
						<a class="nodecoration" href="upload/uploaditem" target="_blank">
							<img src="images/new_item.png" />
						</a>
					</div>
					<div id="publishlist" class="leftfloat leftmargin_5 topmargin_10">
						<a class="nodecoration" href="upload/uploadlist" target="_blank">
							<img src="images/new_list.png" />
						</a>
					</div>
					<div class="leftclear"></div>
				</div>
				<div>
					<a class="graybutton" href="getmygroups" rel="facebox" title="选择群组">选择群组</a>
					<a class="graybutton leftbutton" id="allnews" href="javascript:void(0)">全部</a> 
					<a class="graybutton middlebutton" id="easytopic" href="javascript:void(0)">话题</a>
					<a class="graybutton middlebutton" id="easydoc" href="javascript:void(0)">文档</a>
					<a class="graybutton middlebutton" id="easylist" href="javascript:void(0)">列表</a>
					<a class="graybutton middlebutton" id="easyvideo" href="javascript:void(0)">视频</a>
					<a class="graybutton middlebutton" id="easytext" href="javascript:void(0)">文章</a>
					<a class="graybutton middlebutton" id="easyurl" href="javascript:void(0)">链接</a>
					<a class="graybutton rightbutton" id="selfdefine" href="javascript:void(0)">自定义▼</a>
				</div>
				<s:include value="/basic/_resourceTypeFilter.jsp" />
				<!--div class="bottommargin_10 centeralign padding715 yellowbox">
					<a class="yellowletter" href="javascript:moreNewNews()">有1条新动态，点击查看</a>
				</div-->
				<div id="newsloading" class="hidden centeralign">
					<img src="images/loading.gif" border="0"/>正在加载中，请稍候...
				</div>
				<div id="myOwnTopicContainer"></div>
				<div id="newscontainer"></div>
				<div id="newsloadingbottom" class="hidden centeralign">
					<img src="images/loading.gif" border="0"/>正在加载中，请稍候...
				</div>
				<div id="nomorenews" class="hidden centeralign topmargin_10 midsize blueletter">
					没有更多新动态了
				</div>
				<div class="centeralign topmargin_10" id="moreoldnews">
					<a href="javascript:moreOldNews()" class="midsize blueletter">
						加载更多群组动态...
					</a>
				</div>
			</div>
			<div class="clear"></div>				
		</div>
		<s:include value="/template/_footer.jsp" />
	</div>
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/resource/comment.js"></script>
	<script src="js/basic/filter.js"></script>
	<script	src="js/basic/news.js"></script>
	<script src="js/basic/recommend.js"></script>
	<script type="text/javascript">
		//loadFriendList();
		userAutoTips({id:'topic'});
	</script>
	<script>
    $(document).ready( function() {
        recommendGroup();
        $("#rememberresource").attr("checked", true); //默认复选框选中
    })
    </script>
    <script src="<%=com.free4lab.freeshare.URLConst.APIPrefix_FreeMessage%>js/api/freemessage_api-1.0.js"></script>
</body>