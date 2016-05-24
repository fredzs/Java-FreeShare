<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="banner">
	<div class="content">
		<a href=""><img id="logo" src="images/logo_inner.png" border="0" /></a>
		<span class="nav">
	        <a href="news" class="nodecoration">
	        	<img src="images/bn_index.png" border="0"  class='${param.index=="index"?"cur":"" }'/>
	        </a>
	        <a href="groups" class="nodecoration">
	        	<img src="images/bn_group.png" border="0"  class='${param.index=="group"?"cur":"" }'/>
	        </a>
	        <span>
	        <a href="javascript:void(0)" class="nodecoration webwidget_vertical_menu"> 
	        	<img src="images/bn_mine.png" border="0"  class='${ param.index=="mine"?"cur":"" }'/>
	        </a>
				<ul class="webwidget_vertical_menu_content tightline">
					<li class="padding5 toppadding_0 bottompadding_0">
						<a href="collections"><img src="images/bn_mine_1.png" border="0" /></a>
					</li>
					<li class="padding5 toppadding_0 bottompadding_0">
						<a href="items"><img src="images/bn_mine_3.png" border="0" /></a>
					</li>
				</ul>
			</span>
			<a href="javascript:void(0)" class="nodecoration">
				<img src="images/bn_find.png" border="0"  class='${ param.index=="find"?"cur":"" }'/>
			</a>
	        <a href="search" class='nodecoration ${ param.index=="search"?"":"hidden" }'>
	       		<img src="images/bn_search.png" border="0"  class='${ param.index=="search"?"cur":"" }'/>
	       	</a>
	    	<span>
	    	<a href="javascript:void(0)" 
	    			class='nodecoration webwidget_vertical_menu ${ param.index!="search"?"":"hidden" }'>
	    		<img src="images/bn_new.png" border="0"  class='${ param.index=="new"?"cur":"" }'/>
	    	</a>
	    		<ul class="webwidget_vertical_menu_content tightline">
					<li class="padding5 toppadding_0 bottompadding_0">
						<a href="upload/uploaditem"><img src="images/bn_new_1.png" border="0" /></a>
					</li>
					<li class="padding5 toppadding_0 bottompadding_0">
						<a href="upload/uploadlist"><img src="images/bn_new_2.png" border="0" /></a>
					</li>
					<li class="padding5 toppadding_0 bottompadding_0">
						<a href="upload/uploadtopic"><img src="images/bn_new_3.png" border="0" /></a>
					</li>
				</ul>
			</span>
			<span>
	    	<a href="javascript:void(0)" 
	    			class='webwidget_vertical_menu ${ param.index=="search"?"":"hidden" }'}'>
	    		<img src="images/bn_new_s.png" border="0"  class='${ param.index=="new"?"cur":"" }'/>
	    	</a>
	    		<ul class="webwidget_vertical_menu_content tightline">
					<li class="padding5 toppadding_0 bottompadding_0">
						<a href="upload/uploaditem"><img src="images/bn_new_1.png" border="0" /></a>
					</li>
					<li class="padding5 toppadding_0 bottompadding_0">
						<a href="upload/uploadlist"><img src="images/bn_new_2.png" border="0" /></a>
					</li>
					<li class="padding5 toppadding_0 bottompadding_0">
						<a href="www.baidu.com"><img src="images/bn_new_3.png" border="0" /></a>
					</li>
				</ul>
			</span>
	    </span>
		<div class="searchbox rightfloat">
			<s:include value="/template/_search.jsp" /> 
		</div>
	</div>
</div>