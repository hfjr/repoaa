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
        <!--<p class="col-xs-8 title">保险</p>-->
    <!--</article>ios去除-->
    <article class="container">
        <section class="row posin-re">
            <img src="<c:url value='/static/h5/img/baoxin-lq-topbg.png'/>" width="100%">
            <div class="pad-b-ltrt-20">
                <ul class="container jb-ul pad-b-left-20 pad-right-0" style="padding-right:2px!important;">
                    <li class="bdr-bot-1 pad-botop-15 col-xs-12 pad-right-20">
                        <p class="col-xs-8 mag-bot-0 ft-size-15 ft-col-black">保障开始时间</p>
                        <b class="col-xs-4 ft-size-14 ft-col-nblue text-right mag-top-2" id="startTime"></b>
                    </li>
                    <li class="bdr-bot-1 pad-botop-15 col-xs-12 pad-right-20">
                        <p class="col-xs-8 mag-bot-0 ft-size-15 ft-col-black">保障结束时间</p>
                        <b class="col-xs-4 ft-size-14 ft-col-nblue text-right mag-top-2" id="endTime"></b>
                    </li>
                    <a href="<c:url value='/app/insurance/insurance-details.jsp'/>">
                        <li class="bdr-bot-1 pad-botop-15 col-xs-12 pad-right-20">
                            <p class="col-xs-8 mag-bot-0 ft-size-15 ft-col-black">提供保障</p>
                            <b class="col-xs-4 ft-size-14 ft-col-nblue text-right mag-top-2">更多详情 ></b>
                        </li>
                    </a>
                    <li class="bdr-bot-1 pad-botop-15 col-xs-12 pad-right-20">
                        <p class="col-xs-8 mag-bot-0 ft-size-15 ft-col-black">保障期限</p>
                        <b class="col-xs-4 ft-size-14 ft-col-nblue text-right mag-top-2" id="safeTerm"></b>
                    </li>
                    <li class="bdr-bot-1 pad-botop-15 col-xs-12 pad-right-20">
                        <p class="col-xs-8 mag-bot-0 ft-size-15 ft-col-black">保障金额</p>
                        <b class="col-xs-4 ft-size-14 ft-col-nblue text-right mag-top-2" id="safeMoney"></b>
                    </li>
<!--                     <li class="bdr-bot-1 pad-botop-15 col-xs-12 pad-right-20"> -->
<!--                         <p class="col-xs-8 mag-bot-0 ft-size-15 ft-col-black">可赔次数</p> -->
<!--                         <b class="col-xs-4 ft-size-14 ft-col-nblue text-right mag-top-2" id="compensateTimes"></b> -->
<!--                     </li> -->
                    <li class="bdr-bot-1 pad-botop-15 col-xs-12 pad-right-20">
                        <p class="col-xs-8 mag-bot-0 ft-size-15 ft-col-black">支付金额</p>
                        <b class="col-xs-4 ft-size-14 ft-col-nblue text-right mag-top-2" id="payMoney"></b>
                    </li>
                    <li class="bdr-bot-1 pad-botop-15 col-xs-12 pad-right-20">
                        <p class="col-xs-8 mag-bot-0 ft-size-15 ft-col-black">服务热线</p>
                        <b class="col-xs-4 ft-size-14 ft-col-nblue text-right mag-top-2" id="hotline"></b>
                    </li>
                </ul>
            </div>
        </section>
    </article>
    <style>
        .jb-ul {border:1px solid #4AC0F0;border-radius:5px;background:#fff;}
    </style>
</body>

<script type="text/javascript">

$(function () {
    $.ajax({
        type: "POST",
        url: "<c:url value='/app/insurance/queryPICCInsuranceInfo.security'/>?userId=${userId}&uuid=${uuid}",
        cache: false,
        success: function (result) {
            if (result.footer.status == '610') {
               alert('登录超时,请重新登录');
               return false;
            } 
			if (result.footer.status == '620') {
          	   alert(result.footer.message);
               window.location.href='<c:url value='/app/insurance/insuranceIndex.security'/>';
               return false;
            }
			if (result.footer.status == '200') {
				  $.each(result.body, function(key, value){
					 $("#"+key).html(value); 
				  });
				  return true;
            }
			if (result.footer.status != '200') {
            	alert(result.footer.message);
            	return false;
            }
        },
        error: function () {
        	alert("系统繁忙，请尝试重新登录");
        }
    });
});


</script>
</html>