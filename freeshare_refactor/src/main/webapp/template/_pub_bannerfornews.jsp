<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="pub_banner content" sys="sharefornews" user="<s:property value='#session.email'/>" userid="<s:property value='#session.userId'/>"  acctoken="<s:property value='#session.accessToken.substring(8, 24)'/>" 
index= "<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"%>" handleurl = "<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"%>login"></div>
