<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
  <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>群组成员--Free分享</title>
  <s:include value="/template/_head.jsp" />  
</head>

<body>
<div id="container">
  <s:include value="/template/_pub_banner.jsp"/>
  <s:include value="/template/_banner.jsp?index=group"></s:include>
  <div id="inner" class="content">
   	
   
      <s:include value="_mygroup.jsp?menu=group_members"></s:include>
      
      <div class="list">
      
	       	<div>
				 <table >
				   <tr>
	        	     
	        	    <td ><s:iterator value="creator" id="c" status ="st">
	        	           <div class = "enableitem">
										<a href="javascript:void(0)" class="nodecoration"
											onclick="quickCommunicateFn('<s:property value="#c.userId" />')">
												<img src="http://freedisk.free4lab.com/download?uuid=<s:property value="#c.avatar"/>"
											onerror="javascript:this.src='images/user_male.png'"
											width="50" height="50" border="0"  />
										</a>
										<br/>
											<s:if test="#c.userName != null">
							          		<a href="javascript:void(0)" class="grayletter"
																onclick="quickCommunicateFn('<s:property value="#c.userId"/>')">
							          	
							          	   <s:property value="#c.userName"/>
							          	   </a>
							         </s:if>
						         		<br/>
						         		<span class="myemail"><s:property value="#c.email"/></span>
	        				</div>
	        				</s:iterator>
	        				<s:iterator value="adminlist" id="al" status ="st">
	        				 	<div class = "enableitem">
		        				 	<a href="javascript:void(0)" class="nodecoration"
											onclick="quickCommunicateFn('<s:property value="#al.userId" />')">
												<img src="http://freedisk.free4lab.com/download?uuid=<s:property value="#al.avatar"/>"
											onerror="javascript:this.src='images/user_male.png'"
											width="50" height="50" border="0"  />
									</a>
									<br/>
									<s:if test="#al.userName != null">
							          	<a href="javascript:void(0)" class="grayletter"
																onclick="quickCommunicateFn('<s:property value="#al.userId"/>')">
							          	
							          	   <s:property value="#al.userName"/>
							          	   </a>
							         </s:if>
					         		<br/>
					         		<span class="myemail"><s:property value="#al.email"/></span>
				         		</div>
							</s:iterator>
							<s:iterator value="userlist" id="ul" status ="st">
									<div class="enableitem">
											<a href="javascript:void(0)"  class="nodecoration"
														onclick="quickCommunicateFn('<s:property value="#ul.userId"/>')">
					          	<img src="http://freedisk.free4lab.com/download?uuid=<s:property value="#ul.avatar"/>"
														onerror="javascript:this.src='images/user_male.png'"
														width="50" height="50" border="0"  />
											</a>
									<br/>
									<s:if test="#ul.userName != null">
							          	<a href="javascript:void(0)" class="grayletter"
																onclick="quickCommunicateFn('<s:property value="#ul.userId"/>')">
							          	
							          	   <s:property value="#ul.userName"/>
							          	   </a>
							         </s:if>
							         <br/>
							         <span  id ="useremail" class="myemail"><s:property value="#ul.email"/></span>
									</div>
							</s:iterator>
	        		 </td>
	        		
         			</tr>
         		</table>
		       </div>
	      
     </div>
  </div><!--#inner-->
   <s:include value="/template/_footer.jsp" />
</div><!--#container-->
<s:include value="/basic/_globlejs.jsp" />

<script>

document.getElementById("group_members").innerHTML="本组成员 ("+ <s:property value='memberNum' /> + "个)";
</script>
</body>
</html>