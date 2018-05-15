<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no,email=no,address=no"/>
    <link href="<c:url value='/static/v-client/v-client/static/css/base.css'/>" rel="stylesheet">
    <title>车险分期借款合同</title>
	<style>
		body{margin:0;padding:18px;font-size:14px;color:#666;line-height:24px;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;}.viewport p{text-indent:2em;padding:3px 0;text-align:justify;}.h1{font-size:18px;font-weight:bold;text-align:center;padding:10px 0;color:#000;}p.h2{font-size:16px;font-weight:bold;color:#333;text-indent:0;}p.h3{font-size:14px;font-weight:bold;color:#222;text-indent:0;}.underline{border-bottom:1px solid #666;font-weight:bold;padding:0 15px;}.picc-logo{text-align:center;padding:15px 0;}.picc-logo img{border:none;width:114px;height:11px;}
	</style>
</head>
<body>
<article class="viewport">
${content}
</article>
<div class="picc-logo"><img src="<c:url value='/static/contract/img/picc-logo.png'/>"></div>
</body>
</html>