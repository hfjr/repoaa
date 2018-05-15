<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">  
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
	<link type="text/css" rel="stylesheet" href="<c:url value='/static/h5/css/css.css'/>"/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/static/h5/css/news-detail.css'/>"/>
	<title>融桥宝</title>
</head>
<body>


<div class="system">
		<p class="same ">${content.title}</p>
		<p class="same size">
		<c:if test="${empty content.releaseTime}">${content.createDate}</c:if>
		<c:if test="${not empty content.releaseTime}">${content.releaseTime}</c:if>
		</p>
	</div>
	<div class="back"></div>
	<div class="padding">
		<div class="content">${content.content}</div>
	</div>
</body>

</html>
