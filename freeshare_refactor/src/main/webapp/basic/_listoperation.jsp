<!--
使用该文件的地方：
basic/browse.jsp
-->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="bottommargin_5" id="botton_margin">
	<a class="graybutton leftbutton"
		href="resource/preupdatelist?id=<s:property value="id" />&type=-1">编辑</a>
	<s:if test="permission == 1">
		<a class="graybutton middlebutton"
			href="javascript:del('-1')"
			onclick="return confirm('确认删除?');">删除 </a>
	</s:if>
	<a class="shareto_button graybutton middlebutton"
		href="http://shareto.com.cn/share.html">分享到</a>
	<s:else>
		<a class="graybutton middlebutton" href="javascript:void(0)"
			onclick="collect('<s:property value="id" />','list')">收藏</a>
	</s:else>
</div>