<!--
使用该文件的地方：
basic/_iteminlist.jsp
basic/collections.jsp
basic/newsearch.jsp
group/group_lists.jsp
-->
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags" %>

<%
//由于 s:include的参数并未存入ActionContext的request.parameters里，所以增加以下内容将相关参数传入 ActionContext

    String url = java.net.URLDecoder.decode((String)request.getParameter("url"), "UTF-8");
    // String url = (String)request.getParameter("url");
    Integer currPage = Integer.parseInt(request.getParameter("currPage"));
    Integer lastPage =Integer.parseInt(request.getParameter("endPage"));
    ActionContext context = ActionContext.getContext();

    context.put("url", url);
    context.put("currPage", currPage);
    context.put("lastPage", lastPage);

    if (lastPage.compareTo(6) <= 0)
    	context.put("showPage", lastPage);
    else
    	context.put("showPage", new Integer(6));  
%>             
            <span class="divpage">             
             <span>共 <s:property value="#lastPage"/> 页</span>
             <a class="text" href="<s:property value="#url"/>page=1">首页</a>             
             <a class="pagenum" href="<s:property value="#url"/>page=<s:property value="#currPage > 1 ? #currPage - 1 : 1"/>">&lt;&lt;</a> 
             <s:if test="#currPage < #showPage">
              <s:iterator status="s" begin="1" end="#showPage">
               <s:if test="#s.count == #currPage">
               <span class='currpage'><s:property value="#s.count"/></span>
               </s:if>            
               <s:else><a class="pagenum" href="<s:property value="#url"/>page=<s:property value="#s.count"/>"><s:property value="#s.count"/></a></s:else> 
              </s:iterator>
             </s:if>
             <s:elseif test="#currPage > (#lastPage - #showPage)">
              <s:iterator status="s" begin="(#lastPage - #showPage + 1)" end="#lastPage">
              <s:if test="(#s.count + #lastPage - #showPage) == #currPage">
              <span class='currpage'><s:property value="#currPage"/></span>
              </s:if>
              <s:else><a class="pagenum" href="<s:property value="#url"/>page=<s:property value="#s.count + #lastPage - #showPage"/>"><s:property value="#s.count + #lastPage - #showPage"/></a></s:else>
              </s:iterator>
             </s:elseif>
             <s:else>
              <s:iterator status="s" begin="#currPage - (#showPage/2)" end="#currPage + (#showPage/2)">
               <s:if test="#currPage - (#showPage / 2) + #s.index == #currPage">
               <span class='currpage'><s:property value="#currPage"/></span>
               </s:if>
               <s:else><a class="pagenum" href="<s:property value="#url"/>page=<s:property value="#currPage-(#showPage/2)+#s.index"/>"><s:property value="#currPage-(#showPage/2)+#s.index"/></a></s:else>
              </s:iterator>
             </s:else> 
             <a class="pagenum" href="<s:property value="#url"/>page=<s:property value="#currPage < #lastPage ? #currPage + 1 : #lastPage"/>">&gt;&gt;</a>
             <a class="text" href="<s:property value="#url"/>page=<s:property value="#lastPage"/>">尾页</a>
            </span><!-- divpage --> 
