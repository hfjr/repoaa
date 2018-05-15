<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no">
    <title>融桥宝</title>
    <link type="text/css" rel="stylesheet" href="<c:url value='/static/h5/css/vjmian.css'/>"/>
    <script type="text/javascript" src="<c:url value='/static/h5/js/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/h5/js/vjmjs.js'/>"></script>
</head>
<body>


<!-- <header> -->
<!--     <h1>消息详情</h1> -->
<!--     <a class="a_back" href="#"> -->
<%--         <img src="<c:url value='/static/h5/img/leftjt.png'/>"> --%>
<!--     </a> -->
<!-- </header> -->

<article class="PublicArticle" style="padding-top:30px;">
    <section style="margin:0 0 20px 20px;padding-bottom:16px;padding-left:10px;" class="BorderB3f2">
        <img class="FloatLeft" src="<c:url value='/static/h5/img/xiaoxiic.png'/>" style="margin:5px 9px 0 0;">
        <div class="FloatLeft">
            <p style="font-size:1.4em;color:#4a4a4a;">  ${content.type}</p>
            <p style="font-size:1.2em;color:#9B9B9B;">  ${content.createDate} </p>
        </div>
        <div class="cbdiv"></div>
    </section>
    <p class="xieyip" style="color:#4A4A4A;">
      ${content.content}
    </p>
</article>
</body>
<!-- 百度统计代码 wallet-mobile -->
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  //hm.src = "//hm.baidu.com/hm.js\?b0334162ce58a358a7ea396016a5ddea";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
</html>
