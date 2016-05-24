<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的群组--Free分享</title>
<s:include value="/template/_head.jsp" />  
</head>

<body>
 <div id="container">
 	<div id="tipboxWrapper"></div>
  <s:include value="/template/_pub_banner.jsp"/>
  <s:include value="/template/_banner.jsp?index=group"></s:include>
  <div id="inner" class="content">
  	<div class="topmargin_20">
		 <div class="leftfloat">
		 	<img id="showavatar" src="<s:property value="groupAvatar"/>"
				onerror="javascript:this.src='images/grouphead.png'"
				width="100" height="100" border="0"  /> 
			<br/>&nbsp;&nbsp;&nbsp;&nbsp;
			<a id="groupavatarid" class="blackletter"  onclick="javascript:setavatar();" rel="facebox" href="" title="修改头像">修改头像</a>
		</div>
		<div class="leftmargin_20 bottommargin_5 bottomveralign leftfloat"><span class="blueletter">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="backgroup" onclick="backToGroup();">群组页</a>》<s:property value="name"/></span>
		<br/><h1>&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="name"/></h1>
		<input id="groupId"  name="groupId" class= "hidden" value="<s:property value= "groupId"/>"></input>
		<span id="groupInfo">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="groupInfo"/></span>
		<span id="update" class="leftmargin_10"><a class="blackletter" rel="facebox" title="修改群组信息" href="group/preupdateinfo?groupId=${groupId}&name=${name}&desc=${groupInfo}">修改</a></span>
		</div>
		<!--s:if test="type == 2||type == 1">
		<a class="button topmargin_20 rightfloat" href="group/getgroup?groupId=<s:property value= "groupId"/>&kind=0">管理群组</a>
	    
	    <div class="clear"></div> -->
		<a class="graybutton topmargin_20 rightfloat" href="group/resource?groupId=<s:property value= "groupId"/>">本组资源</a>
		<div class="clear"></div>
		
		
	</div>
	<br/>
	<br/>
	<div class="bottommargin_5">
		<input type="checkbox" class="leftmargin_20 rightmargin_5" onclick="if(this.checked==true) { selectall(); } else { selectnone(); }" />
    	<s:if test="kind==0"><!-- 已经是群组成员 -->
        	<a class="graybutton leftbutton" href="javascript:groupManager.deleteMem();">移出群组</a>
        	<s:if test="type==2"><!-- 创建者 -->					
			</s:if>
        </s:if>
        <s:elseif test="kind==1"><!-- 申请加入的群组成员 -->
        	<a class="graybutton leftbutton" href="javascript:groupManager.agreeOrIgnoreApply('agree')">同意</a>
        	<a class="graybutton rightbutton" href="javascript:groupManager.agreeOrIgnoreApply('ignore')">忽略</a>
        </s:elseif>
        <s:elseif test="kind==2"><!-- 邀请加入的群组成员 -->
        <a class="graybutton leftbutton" href="javascript:void(0)"onclick="groupManager.sendInviteAgain();">再次邀请</a>
        	<a class="graybutton rightbutton" href="javascript:void(0)" onclick="groupManager.deleteInviteMemInfo();">删除</a>
        </s:elseif>
		<a id="allmember" href="group/getgroup?groupId=<s:property value= "groupId"/>&kind=0" class="graybutton leftbutton leftmargin_5 ${ param.kind==0?'selected':'' }">
			所有成员<s:if test="kind==0"> (<s:property value='memberNum' />个)</s:if>
		</a>
	    <a id="applymember" href="group/getgroup?groupId=<s:property value= "groupId"/>&kind=1" class="graybutton middlebutton ${ param.kind==1?'selected':'' }">
	    	已申请<s:if test="kind==1"> (<s:property value='memberNum' />个)</s:if>
	    </a>
	    <a id="invitemember" href="group/getgroup?groupId=<s:property value= "groupId"/>&kind=2" class="graybutton rightbutton rightmargin_5 ${ param.kind==2?'selected':'' }">
	    	已邀请<s:if test="kind==2"> (<s:property value='memberNum' />个)</s:if>
	    </a>
	     
	    <a class="button bothmargin_5" href="members/invitemembers?groupId=<s:property value= "groupId"/>">邀请成员</a>
	   
	    <s:if test="type==2"><!-- 创建者 -->
	    </s:if>
   	  </div>
      
	  	<div id = "mem" class="list">  
	  		<s:iterator value="creator" id="c" status ="st">
			      <div>
						<table >
							<tr>
							<td class="middleveralign" width="20px">
				       			<s:if test="kind <2&&#st.index != 0&&type==2">
				        			<input type="checkbox" value="<s:property value="#c.userId"/>" name="<s:property value ="#c.userId"/>"/>
				        		</s:if>
				       	    <s:else>
				       	    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				       	    </s:else>
				     	    </td>
			        	     
				     	    <td width="60px">
				     	  		<img src="http://freedisk.free4lab.com/download?uuid=<s:property value='#c.avatar'/>"
										onerror="javascript:this.src='images/user_male.png'"
										width="50" height="50" border="0"  />
				     		 	</td>
				     		 	<td >
				       			<s:property value="#c.userName"/>
				       			<s:if test="#st.index != 0&&type==2">
				       			
				       				<span class="rightfloat"><a class="blackletter" href="javascript:groupManager.setOrRemoveAdmin(<s:property value="#c.userId"/>,'cancle')">取消管理员</a></span>
				       			</s:if>
				       			<br/>
				       			<s:property value="#c.email"/>
				     			</td>
				        </tr>
				        </table>
				    </div>
		    </s:iterator>      
	  	<s:iterator value="adminlist" id="al" status ="st">
	      <div>
				<table >
					<tr>
					<td class="middleveralign" width="20px">
       			<s:if test="kind <2&&type==2">
        			<input type="checkbox" value="<s:property value="#al.userId"/>" name="<s:property value ="#al.email"/>"/>
        		</s:if>
       	    <s:else>
       	    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       	    </s:else>
     	    </td>
	        	     
     	    <td width="60px">
     	  		<img src="http://freedisk.free4lab.com/download?uuid=<s:property value='#al.avatar'/>"
						onerror="javascript:this.src='images/user_male.png'"
						width="50" height="50" border="0"  />
     		 	</td>
     		 	<td >
       			<s:property value="#al.userName"/>
       			<s:if test="type==2">
       				<span class="rightfloat"><a class="blackletter" href="javascript:groupManager.setOrRemoveAdmin(<s:property value="#al.userId"/>,'cancle')">取消管理员</a></span>
       				
       			</s:if>
       			<br/>
       			<s:property value="#al.email"/>
     			</td>
        </tr>
        </table>
		    </div>
	    </s:iterator>
	    <s:if test="kind==1">
	      <s:iterator value="userlist" id="ul" status ="st">
	      <div>
				<table >
					<tr>
					<td class="middleveralign" width="20px">
		       			<s:if test="kind <2">
		        			<input type="checkbox" value="<s:property value="#ul.userId"/>" name="<s:property value ="#ul.email"/>"/>
		        		</s:if>
		        	</td>
			        		
		       		<td width="60px">
		       			<img src="http://freedisk.free4lab.com/download?uuid=<s:property value='#ul.avatar'/>"
										onerror="javascript:this.src='images/user_male.png'"
										width="50" height="50" border="0"  />
		       	  </td>
			        <td >
		         		<s:property value="#ul.userName"/>
		         		<span class="grayletter"><s:property value="#ul.email"/></span>
		         		<br/>申请理由：<s:property value="applyReason[#ul.email]"/>
		         	</td>
		         </tr>
		        </table>
        </div>
	      </s:iterator>
	    </s:if>
	    
	    <s:else>
      	<s:iterator value="userlist" id="ul" status ="st">
	      <div>
				<table >
					<tr>
					<td class="middleveralign" width="15px">
					
      			<!-- s:if test="kind <2"-->
       				<input type="checkbox" value="<s:property value="#ul.userId"/>" name="<s:property value ="#ul.email"/>"/>
       			<!--  /s:if-->
       		</td>
       		<td width="60px">
       			<img src="http://freedisk.free4lab.com/download?uuid=<s:property value='#ul.avatar'/>"
								onerror="javascript:this.src='images/user_male.png'"
								width="50" height="50" border="0"  />
       	 	</td>
	        <td >
         		<s:property value="#ul.userName"/> 
         		<s:if test="type==2">
         			<span class="rightfloat "><a class="blackletter" href="javascript:groupManager.setOrRemoveAdmin(<s:property value="#ul.userId"/>,'agree');">设为管理员</a></span>         			
         		</s:if>

         		<br/><s:property value="#ul.email"/>
         	</td>
         </tr>
         </table>
         </div>
	      </s:iterator>
	      <s:iterator value="inviteOutsiders" id="io" status ="st">
		      <div>
					<table >
						<tr>
							<td class="middleveralign" width="15px">
		      					<input type="checkbox" value="'<s:property value="#io"/>'" name="'<s:property value="#io"/>'"/>
		       				</td>
				       		<td width="60px">
				       			<img src="images/user_male.png"
												width="50" height="50" border="0"  />
				       	 	</td>
					        <td >
				         		<br/><s:property value="#io"/>
				         	</td>
				         </tr>
			         </table>
	         </div>
	      </s:iterator>
	     </s:else>
      </div>
    
  </div><!--#inner-->
  <s:include value="/template/_footer.jsp" />
</div><!--#container-->

<s:include value="/basic/_globlejs.jsp" />
<script src="js/group_administration/administration.js"></script>
<script src="js/group/group.js"></script>
<script>
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
});
$(function(){
	selectnone();
});

function setavatar(){
	var groupId = <s:property value="groupId"/>;
	var url="group/changeavatar";
	if($("#showavatar").attr("src") != undefined && $("#showavatar").attr("src") != "images/grouphead.png"){
		var tmp1 = $("#showavatar").attr("src");
		var tmp = tmp1.substring(tmp1.lastIndexOf("=")+1);
		url += "?avatar=";
		url += tmp;
		url += "&groupId=";
		url += groupId;
	}
	else{
		url += "?groupId=";
		url += groupId;
	}
	$("#groupavatarid").attr("href",url);
}

</script>
</body>
</html>