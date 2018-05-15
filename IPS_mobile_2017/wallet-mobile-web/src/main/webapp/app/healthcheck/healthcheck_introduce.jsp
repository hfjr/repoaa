<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
     <meta name="format-detection" content="telephone=no">
    <title>套餐介绍</title>
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
    <article class="container">
        <section class="row bg-cor-white">
            <img src="<c:url value='/static/h5/img/insurance-details.png'/>" width="100%">
            <div class="container mag-top-40 mag-bot-40 pad-b-ltrt-30 insurance-details-lidisc">
                <span class="col-xs-1 mag-top-2"><img src="<c:url value='/static/h5/img/lidisc.png'/>" width="2" height="2"></span>
                <b class="col-xs-11 ft-size-15 ft-col-nblue">体检须知</b>
                <span class="col-xs-1 mag-top-2"><img src="<c:url value='/static/h5/img/lidisc.png'/>" width="2" height="2"></span>
                <b class="col-xs-11 ft-size-15 ft-col-nblue">体检须知</b>
                <span class="col-xs-1 mag-top-2"><img src="<c:url value='/static/h5/img/lidisc.png'/>" width="2" height="2"></span>
                <b class="col-xs-11 ft-size-15 ft-col-nblue">体检须知</b>
            </div>
        </section>
    </article>
</body>
</html>