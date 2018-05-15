
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
        <meta name="apple-mobile-web-app-capable" content="yes"/>
        <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
        <meta name="format-detection" content="telephone=no,email=no,address=no"/>
        <title>责任条款</title>
      
</head>
<body>

    <embed src="<c:url value='/app/businessInsurance/queryLiabilityClauseDetail/${fileId}'/>" width="100%;" height="100%;">
    
</body>
</html>
