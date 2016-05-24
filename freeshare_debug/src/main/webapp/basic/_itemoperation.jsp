<!--
使用该文件的地方：
basic/browse.jsp
-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="bottommargin_5">
	<!-- TODO 当permission为null的时候，检验失败！！！！-->
	<s:if test="1==permission"><!-- 权限为1表示当前访问者为资源作者，可以修改和删除 -->
		<!-- 下载最新版、发布新版和编辑 -->
		<s:if test="info.type == 1"><!-- 资源类型为1，表示该资源带有附件 -->
			<a class="button"
				href="http://freedisk.free4lab.com/download?uuid=<s:property value="info.content"/>"
				target="_blank">下载最新版</a>
			<a class="graybutton middlebutton"
				href="resource/newdocversion?id=<s:property value="id" />">发布新版</a>
			<a class="graybutton middlebutton"
				href="resource/preupdateitem?id=<s:property value="id" />">
				编辑</a>
		</s:if>
		<s:else>
		<s:if test="info.type != 0"><!-- 资源类型不是话题 -->
			<a class="graybutton leftbutton"
				href="resource/preupdateitem?id=<s:property value="id" />">
				编辑</a>
		</s:if>
		</s:else>
		<!-- 删除  -->
		<a class="graybutton middlebutton "
			href="javascript:del('')"
			onclick="return confirm('确认删除?');">删除</a>
	</s:if>
	<s:if test="2 == permission"><!-- 权限为2表示当前访问者非资源作者，可以修改，不可以删除 -->
	<!-- 下载最新版、发布新版和编辑 -->
		<s:if test="info.type == 1"><!-- 资源类型为1，表示该资源带有附件 -->
			<a class="button"
				href="http://freedisk.free4lab.com/download?uuid=<s:property value="info.content"/>"
				target="_blank"> 下载最新版</a>
			<a class="graybutton middlebutton"
				href="resource/newdocversion?id=<s:property value="id" />">发布新版</a>
			<a class="graybutton middlebutton"
				href="resource/preupdateitem?id=<s:property value="id" />">
				编辑</a>
		</s:if>
		<s:else>
		 <s:if test="info.type != 0"><!-- 资源类型不是话题 -->
			<a class="graybutton leftbutton"
				href="resource/preupdateitem?id=<s:property value="id" />">
				编辑</a>
		</s:if>
		</s:else>
	</s:if>
	<!-- end permission 1,2 -->
	<!-- 加入列表、收藏、分享到 -->
	<!-- TODO 把“收藏”的resourceType 改成Integer型 -->
	<s:if test="info.type != null"><!-- 资源类型不是列表 -->
		<a class="graybutton middlebutton"
			href="resource/preaddtolist?id=<s:property value="id"/>&method=itemaddtolist"
			rel="facebox" title="选择列表">加入列表</a>
		<a class="graybutton middlebutton" href="javascript:void(0)"
			onclick="collect('<s:property value="id" />','item')">收藏</a>
	</s:if>
		
	<a class="shareto_button graybutton middlebutton"
		href="http://shareto.com.cn/share.html">分享到</a> 
</div>