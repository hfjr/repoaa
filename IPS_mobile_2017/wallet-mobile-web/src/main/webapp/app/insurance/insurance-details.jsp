<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
     <meta name="format-detection" content="telephone=no">
    <title>融桥宝</title>
   <link href="<c:url value='/static/h5/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/h5/css/public_main.css'/>" rel="stylesheet">
    <script src="<c:url value='/static/h5/js/jquery.min.js'/>"></script>
    <script src="<c:url value='/static/h5/js/bootstrap.min.js'/>"></script>
  <!--[if lt IE 9]>
    <script src="<c:url value='/static/h5/js/h5public/html5shiv.min.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/static/h5/js/h5public/respond.min.js'/>" type="text/javascript"></script>
    <![endif]-->
</head>
<body>
    <!--<article class="nav text-center navbar-form public-header">-->
        <!--<a class="col-xs-2" href="#">-->
            <!--<img src="img/back-arrow.png" width="12" height="21">-->
        <!--</a>-->
        <!--<p class="col-xs-8 title">详情</p>-->
    <!--</article>ios去除-->
    <article class="container">
        <section class="row bg-cor-white">
            <img src="<c:url value='/static/h5/img/insurance-details.png'/>" width="100%">
            <div class="container mag-top-40 mag-bot-40 pad-b-ltrt-30 insurance-details-lidisc">
                <span class="col-xs-1 mag-top-2"><img src="<c:url value='/static/h5/img/lidisc.png'/>" width="2" height="2"></span>
                <b class="col-xs-11 ft-size-15 ft-col-nblue">保障平台资金因被他人盗用而导致的损失</b>
                <span class="col-xs-1 mag-top-2"><img src="<c:url value='/static/h5/img/lidisc.png'/>" width="2" height="2"></span>
                <b class="col-xs-11 ft-size-15 ft-col-nblue">保障手机丢失导致的资金损失</b>
                <span class="col-xs-1 mag-top-2"><img src="<c:url value='/static/h5/img/lidisc.png'/>" width="2" height="2"></span>
                <b class="col-xs-11 ft-size-15 ft-col-nblue">保障平台账户因被他人盗用而导致的资金损失</b>
            </div>
        </section>
        <section class="row bg-cor-white bdr-bot-1">
            <div class="pad-b-ltrt-30 pad-botop-10 ft-size-16 ft-blod ft-col-nblue bg-jb-blue">· 保障</div>
            <p class="pad-b-ltrt-30 pad-botop-20 ft-size-14 ft-col-nblue letter-spacing-none mag-bot-0">按实际损失金额100%赔付，最高赔付金额为100万元</p>
            <div class="pad-b-ltrt-30 pad-botop-10 ft-size-16 ft-blod ft-col-nblue bg-jb-blue">· 提示</div>
            <p class="pad-b-ltrt-30 pad-botop-20 ft-size-14 ft-col-nblue letter-spacing-none mag-bot-0">本保险期限为一年，投保后立即生效<br>每人限持一份，多投无效</p>
        </section>
    </article>
</body>
</html>