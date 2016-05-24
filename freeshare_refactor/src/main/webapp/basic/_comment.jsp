<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="cmt">
<s:if test = "#session.userId != null">
	<form method="post" action="resource/commentwithat">
		<table>
			<tr>
				<td>
					<div class="fulleditbox"> 
						<textarea id="cmtconttemp" name="cmtCont" placeholder="发表评论，试试@你的好友吧（不超过150个字符）"></textarea>
				    </div>						
				</td>
				<td width="65px" class="bottomveralign rightalign">
					<input type="button" id="subCm_<s:property value="id"/>_<s:property value="resourceType"/>" class="button rightfloat" value="评论" onclick="subCmt(<s:property value="id"/>,<s:property value="resourceType"/>)" />
				</td>
			</tr>
			<tr><td class="leftalign"><span id="show_txt" ></span></td><td></td></tr>
		</table>
		
		
		<input type="hidden" id="uname_<s:property value="id"/>_<s:property value="resourceType"/>" name="username" value="<s:property value="#session.userName"/>"></input>
		<input type="hidden" id="id_<s:property value="id"/>_<s:property value="resourceType"/>" name="id" value="<s:property value="id"/>"></input>
		<input type="hidden" id="uid_<s:property value="id"/>_<s:property value="resourceType"/>" name="uid" value="<s:property value="#session.userId"/>"></input>
		<input type="hidden" id="aid_<s:property value="id"/>_<s:property value="resourceType"/>" name="aid" value="<s:property value="info.authorId"/>" />
		<input type="hidden" id="pid_<s:property value="id"/>_<s:property value="resourceType"/>" name="pid" value="" />
		<input type="hidden" id="user_avatar_<s:property value="id"/>_<s:property value="resourceType"/>" value="<s:property value = "#session.avatar"/>"></input>
		<input type="hidden" id="name_<s:property value="id"/>_<s:property value="resourceType"/>" name="name" value="<s:property value="info.name"/>"></input>
	</form>
</s:if>
	<div id="cmtarea_<s:property value="id"/>_<s:property value="resourceType"/>">
		<s:iterator value="listcomment" id="doc" status="st">
		<div>
			<div class="dottedline"></div>
			<table>
				<tr>
				<td rowspan="2" width="60" class="centeralign topveralign">
					<s:property value="#doc.size"/>
					<s:if test="#session.userId!=#doc.userId">
					<a href="javascript:void(0)" class="grayletter" onclick="quickCommunicateFn('<s:property value="#doc.userId"/>')">
						<img src="http://freedisk.free4lab.com/download?uuid=<s:property value="m[#doc.userId].profile_image_url"/>" border="0" width="40" height="40" onerror="javascript:this.src='images/user_male.png'" />
					</a>
					</s:if>
					<s:else>
						<img src="http://freedisk.free4lab.com/download?uuid=<s:property value="m[#doc.userId].profile_image_url"/>" border="0" width="40" height="40" onerror="javascript:this.src='images/user_male.png'" />
					</s:else>
				</td>
					
				<td class="leftalign">
					<s:if test="#session.userId!=#doc.userId">
					<a id="<s:property value="#doc.userId"/>" href="javascript:void(0)" class="grayletter" onclick="quickCommunicateFn('<s:property value="#doc.userId"/>')">
							<s:property value="m[#doc.userId].screen_name" />
					</a>
					</s:if>
					<s:else>
						<s:property value="m[#doc.userId].screen_name" />
					</s:else>
					
					<s:property value="#doc.time.toString().substring(0,19)" />
					
					<span class="rightfloat">
						<s:if test="#session.userId==#doc.userId">
							<a href="javascript:void(0)" class="blueletter" onclick="delCmt('<s:property value="#doc.cmtUrl"/>',this)">删除</a>
						</s:if>
						<s:else>
							<a href="javascript:void(0)" class="blueletter" onclick="replyCmt('<s:property value="#doc.userId"/>','<s:property value ="#doc.resourceId"/>','0')">回复</a> 
						</s:else>
					</span> <br />
					
					<span id="hq"><s:property value="#doc.description" escape="false" /></span>
				</td>
				</tr>
			</table>
		</div>
		</s:iterator>
	</div>
</div>
<script>
/* $(document).ready()
{
	var size = <s:property value="listcomment.size"/>;
	alert($("hq").val());
} */
</script>