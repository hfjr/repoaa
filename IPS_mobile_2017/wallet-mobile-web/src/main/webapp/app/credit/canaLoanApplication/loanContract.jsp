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
    <title>借款合同</title>
    <link rel="stylesheet" href="<c:url value='/static/contract/css/contract.css'/>">
    <style>
        .PublicArticle{width:auto;padding:8px 20px 14px 20px;text-align:justify}
        .xieyip b{font-size:1em;}
        .xieyip,.xieyibt,table{margin:8px 0;}
        table{display:block;width:100%;margin-bottom:0;border-collapse:collapse;}
        table td{}
    </style>
</head>
<body>
${content}
</body>
</html>

