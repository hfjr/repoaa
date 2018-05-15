<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>详情</title>
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <meta content="no-cache,must-revalidate" http-equiv="Cache-Control">
    <meta content="no-cache" http-equiv="pragma">
    <meta content="0" http-equiv="expires">
    <meta content="telephone=no, address=no" name="format-detection">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">


    <link href="<c:url value='/app/help/v3.3/css/reset.css'/>" rel="stylesheet">
    <link href="<c:url value='/app/help/v3.3/css/style.css'/>" rel="stylesheet">
    <script src="<c:url value='/app/help/v3.3/js/jquery-2.1.4.min.js'/>"></script>

    <style>
        .helpCenterDetail{padding: 0 0;}
        .wrap{padding:0 5%; box-sizing:border-box; }
        .helpCenterDetail .main{padding:0 0;}
    </style>

</head>

<body class="helpCenterDetail">
<div class="wrap">
    <div class="main">
        <%--<img class="title-img" src="<c:url value='/app/help/v3.3/img/icon/${detail.typeIconUrl}'/>">--%>
        <img class="title-img" src="<c:url value='/app/help/v3.3/img/icon/${detail.typeIconUrl}'/>">
        <h2 class="title">${detail.typeSubName}</h2>
    </div>
    <div class="word-wrap">
        <p>${detail.typeSubDetail}</p>
    </div>
</div>
</body>
</html>