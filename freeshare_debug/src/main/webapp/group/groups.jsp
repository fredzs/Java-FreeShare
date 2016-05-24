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
<title>我的群组--Free分享</title>
<s:include value="/template/_head.jsp" />
</head>

<body>
	<div id="container">
		<div id="tipboxWrapper"></div>
		<s:include value="/template/_pub_banner.jsp" />
		<s:include value="/template/_banner.jsp?index=group"></s:include>
		<div id="inner" class="content">
				<div class="bottommargin_10 topmargin_10">
					
					<a id="allgroup" href="groups"
						class="graybutton leftbutton ${param.type==null?'selected':'' }">
						所有群组<s:if test="type==null"> (<s:property
								value="groupList.size" />个)</s:if>
					</a> <a id="getowngroup" href="groups?type=2"
						class="graybutton middlebutton ${param.type==2?'selected':'' }">
						我创建的群组<s:if test="type==2"> (<s:property
								value="groupList.size" />个)</s:if>
					</a> <a id="getmanagegroup" href="groups?type=1"
						class="graybutton middlebutton ${param.type==1?'selected':'' }">
						我管理的群组<s:if test="type==1"> (<s:property
								value="groupList.size" />个)</s:if>
					</a>
					<a id="getmanagegroup" href="groups?type=3"
						class="graybutton rightbutton ${param.type==3?'selected':'' }">
						邀请我的群组<s:if test="inviteNum > 0"><span class="redletter">(<s:property value="inviteNum" />个)</span></s:if>
					</a>
					<a id="creategroup" href="group/creategroup.jsp" class="button rightfloat">创建群组</a>
					<!-- a id="searchgroup" href="" class="graybutton">搜索群组
					</a> -->
				</div>
				<div class="list">
					<s:if test="type!=3">
					<s:iterator value="groupList" id="group" status="st">
						<div>
							<table class="formtable">
								<tr>
									<td><img src="http://freedisk.free4lab.com/download?uuid=<s:property value="#group.groupInfo.avatar" />" onerror="javascript:this.src='images/group_head.png'"
									width="50px" height="50px" border="0" /></td><td>
									<a class="blackletter"
									  href="group/resource?groupId=<s:property value="#group.groupId" />"><s:property value="#group.groupInfo.name" /></a>
									  <s:if test="type==2">
									  	<a class="blueletter leftmargin_5 rightfloat" href="javascript:void(0)"  onclick="deletegroup('<s:property value="#group.uuid" />');" >删除</a>
									  </s:if>
									  <!--a class="blueletter leftmargin_5 rightfloat" href='upload/uploaditem?groupId=<s:property value="#group.groupId" />' onclick="" >向此群组发布资源</a-->
									  <a class="blueletter leftmargin_5 webwidget_vertical_menu rightfloat" href="javascript:void(0)">向此群组发布▼</a>
                                      
                                      <ul class="webwidget_vertical_menu_content" >
										  <li class="padding715 dottedline">
										 
										  <a class="greyletter" href='upload/uploaditem?groupId=<s:property value="#group.groupId" />' target="_blank" >发布资源</a>
										  </li>
										  <li class="padding715 dottedline">
										  <a class="greyletter" href='upload/uploadtopic?groupId=<s:property value="#group.groupId"/>' target="_blank" >发布话题</a>
										  </li>
										  <li class="padding715">
										  <a class="greyletter" href='upload/uploadlist?groupId=<s:property value="#group.groupId"/>'sa target="_blank">发布列表</a>
										  </li>
									  </ul>
                                      <s:if test='#group.authToken =="admin"||type >= 1'>
                                      	<a class="blueletter leftmargin_5 rightfloat" href="group/getgroup?groupId=<s:property value="#group.groupId" />&kind=0"  onclick="" >管理</a>
                                      	<a class="blueletter leftmargin_5 rightfloat" href="members/invitemembers?groupId=<s:property value="#group.groupId" />"  onclick="" >邀请成员</a>
                                       </s:if>
                                      <s:if test='#group.extend =="public"'>
                                        <span id='<s:property value="#group.groupId" />'class = "rightfloat">
                                      	<a class="blueletter leftmargin_5 rightfloat" href="javascript:void(0)"  onclick="obtainShareAddress('<s:property value="#group.groupId" />')" >获取群组分享链接</a>
                                      	</span>
                                      </s:if>
									 <br /> 
									  <span><s:property value="#group.groupInfo.desc" /></span></td>
								</tr>
							</table>
						</div>
					</s:iterator>
					</s:if>
					<s:else>
					    
						<s:if test="inviteNum == 0">
						    你现在没有邀请信息。
						</s:if>
						
						<s:elseif test="inviteNum > 0">
			   	 	    <div>
								<div  class="list">    
									<s:iterator value="groupList" id="group" status="st">
									<div id="<s:property value ="#st.index"/>">
										<table class="formtable">
											<tr>
												<td rowspan="2"><img src="http://freedisk.free4lab.com/download?uuid=<s:property value="#group.groupInfo.avatar" />" onerror="javascript:this.src='images/group_head.png'"
											width="40px" height="42px" border="0" />
												</td>
												<td class="leftalign">
												<a class="greyletter"  href ="group/resource?groupId=<s:property value="#group.groupId"/>"><s:property value="#group.groupInfo.name" /></a>
												    <br/>期待你的加入</td>
												<td>
												<span>
												    <a href="javascript:groupManager.agreeOrIngore('<s:property value="#session.userId"/>','<s:property value='#group.groupId'/>','agree')">同意</a>
				   	 								<a href="javascript:groupManager.agreeOrIngore('<s:property value="#session.userId"/>','<s:property value='#group.groupId'/>','ignore')">忽略</a>
				   	 								</span>	
												</td>
											</tr>
										</table>
									</div>
									</s:iterator>
								</div>
							
						</div>
					</s:elseif>
				</s:else>
			</div>
		</div>
		<!--#inner-->
		<s:include value="/template/_footer.jsp" />
	</div>
	<!--#container-->
	<s:include value="/basic/_globlejs.jsp" />
	<script src="js/group_administration/administration.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			if(location.href.indexOf("?") != -1){
				var myquery = location.href.split("?")[1];
				var queryArray = myquery.split("&");
				var queryMap = new Object();
				for(var i=0; i < queryArray.length; i++){
					var aquery = queryArray[i].split("=");
					queryMap[aquery[0]] = aquery[1];
				}
				if( "tipstatus" in queryMap){
					var status = queryMap["tipstatus"];//0-success;1-warning;2-error;3-创建群组
					var content = decodeURIComponent(queryMap["content"]) || "";
					var pngName="";
					switch(status){
						case '0':
							pngName = "success.png";
							break;
						case '1':
							pngName = "warning.png";
							break;
						case '2':
							pngName = "error.png";
							break;
						case '3':
							{
								pngName = "success.png";
								content = "创建群组成功";
							}							
							break;
						case '4':
							{
								pngName = "success.png";
								content = "退出群组成功";
							}							
							break;
					}
					var  tipContent = "<table><tr><td><img src='images/"+pngName+"'/></td><td>&nbsp;"+ content +"&nbsp;</td></tr></table>";
					 
					$("#tipboxWrapper").html("<div  id=\"tipbox\"  class=\"mytipboxposition\"></div>");
					$("#tipbox").tipbox  ({
					         content:tipContent,
					         autoclose:true,
					         hasclose:false
					});
				}
			}	
			
			$(".webwidget_vertical_menu").webwidget_vertical_menu();
		});
		 function deletegroup(uuid){
			//alert("delete function");
			//alert("uuid:"+uuid);
			if(confirm("您确实要删除这个群组？")){
				$.ajax({
					url : 'group/deletegroup',
					type : 'post',
					data : {
						uuid : uuid
					},
					success : function() {
						//alert("删除群组成功！");
						var query = "tipstatus=0&content="+encodeURIComponent("删除群组成功！");
						if(location.href.indexOf("?") != -1){
							var newUrl = location.href+"&"+query;
						}else{
							var newUrl = location.href+"?"+query;
						}
						location.replace(newUrl);
					}
				});
			}
		} 
		
		 function obtainShareAddress(groupId){
				//alert("right");
				
				//alert("groupId"+groupId);
				var address = "群组分享地址为：http://freeshare.free4lab.com/group/resource?groupId="+groupId+"&from=register"; 
				//var tmp = "#share"+groupId;
				//alert("tmp:"+tmp);
				$("#"+groupId).html("");
				$("#"+groupId).html("                         "+address);
				//$("#"+groupId).html("<h4  class=\"lightgreyletter\">"+address+"</h4>");
			}
	</script>
</body>
</html>
