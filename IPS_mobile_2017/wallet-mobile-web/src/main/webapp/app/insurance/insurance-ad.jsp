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
    <script src="js/h5public/html5shiv.min.js" type="text/javascript"></script>
    <script src="js/h5public/respond.min.js" type="text/javascript"></script>
    <![endif]-->
</head>
<body>
    <!--<article class="nav text-center navbar-form public-header">-->
        <!--<a class="col-xs-2" href="#">-->
            <!--<img src="img/back-arrow.png" width="12" height="21">-->
        <!--</a>-->
        <!--<p class="col-xs-8 title">保险</p>-->
    <!--</article>ios去除-->
    <style>
        .modal-dialog {padding-top:10%;}
        .modal-content {background:url(<c:url value='/static/h5/img/bx-pop-lq.png'/>) no-repeat;background-size:100%;height:376px;width:245px;margin:0 auto;border:0;box-shadow:none;}
        .modal-header {border:0;}
    </style>
    <article class="container">
        <section class="row posin-re">
              <img src="<c:url value='/static/h5/img/renshoubaoxin-bg.png'/>" width="100%">
            <img id="yun01" class="posin-ab" src="<c:url value='/static/h5/img/yun01.png'/>" width="9.903%">
            <img id="yun02" class="posin-ab" src="<c:url value='/static/h5/img/yun02.png'/>" width="7.132%">
            <img id="yun03" class="posin-ab" src="<c:url value='/static/h5/img/yun03.png'/>" width="6.4323%">
            <section class="row pad-b-ltrt-30 xinyong">
               
                <button type="button" class="btn btn-info public-btn-d poptype" id="buyBtn" onclick="buyInsurance()">立即领取</button>
                
                <a id="success_modal" href="javascript:;" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo"></a>
            </section>
            <div class="modal fade" id="exampleModal" tabindex="-2" role="dialog" aria-labelledby="exampleModalLabel">
                <div class="modal-dialog" role="document"  style="width: 100%;height: 100%">
                    <div class="modal-content">
                        <div class="modal-header">
                            <a href="<c:url value='/app/insurance/insuranceDetail.security'/>?userId=${userId}&uuid=${uuid}" class="close"  style="width:40px;height:30px;margin-top:-10px;">
                                <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                            </a>
                        </div>
                        <div class="container" style="margin-top:68px; padding:0 28px;">
                            <p class="ft-size-16 ft-col-black mag-bot-0">恭喜您！</p>
                            <p class="ft-size-14 ft-col-black">账户安全险已放入您的钱包最高保额<b class="ft-size-16" style="color:#FFBD30">100万元</b>!</p>
                            <p class="ft-size-14" style="color:#9B9B9B;">有效期一年</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </article>
    
<script>
   $(document).ready(function() {
       setTimeout("sssss()", 10);
   });
   function sssss() {
       $("#yun01").animate({left: '+=5%'}, 4000);
       $("#yun01").animate({left: '-=5%'}, 4000);
       $("#yun02").animate({left: '+=5%'}, 4000);
       $("#yun02").animate({left: '-=5%'}, 4000);
       $("#yun03").animate({left: '-=5%'}, 3500);
       $("#yun03").animate({left: '+=5%'}, 3500);
       sssss();
   };
</script>

    
    

<script type="text/javascript">

$(function(){
	//检测是否已经成功领取保险
	
	
});
//领取保险
function buyInsurance(){
	 $.ajax({
	        type: "POST",
	        url: "<c:url value='/app/insurance/buyPICCInsurance.security'/>?userId=${userId}&uuid=${uuid}",
	        cache: false,
	        beforeSend:function(){
	        	$("#buyBtn").attr({ disabled: "disabled" });
	        },
	        success: function (result) {
	            if (result.footer.status == '610') {
	               alert('登录超时,请重新登录');
	               return false;
	            } 
				if (result.footer.status == '620') {
	          	   alert(result.footer.message);
	               window.location.href='<c:url value='/app/insurance/insuranceIndex.security'/>?userId=${userId}&uuid=${uuid}';
	               return false;
	            }
				if (result.footer.status == '200') {
					$("#success_modal").click();
					//领取成功
					setTimeout(function(){
						window.location.href='<c:url value='/app/insurance/insuranceDetail.security'/>?userId=${userId}&uuid=${uuid}';
					}, 5000);
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
	
}
</script>
    
    
    <style>
        #yun01 {left:14%;top:35.48%;}
        #yun02 {left:37.68%;top:34.10%;}
        #yun03 {left:83.33%;top:33.33%;}
        .xinyong {position:absolute;width:100%;bottom:15.36%; margin:0 auto;}
        .xinyong button {background:#91d142;border:0;}
    </style>
</body>
</html>