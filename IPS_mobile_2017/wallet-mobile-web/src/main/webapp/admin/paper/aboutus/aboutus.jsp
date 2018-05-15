
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		
		<link rel="stylesheet" href="css/what.css">
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
		<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>
	<body scroll="no">
		<header>
			<p class="head-intro">
				<span class="whats">什么是融桥宝</span><br />
				<span class="what">WHAT</span>
			</p>
			<div class="head-right">
				<img src="images/head.png">
				<span>?</span>
			</div>
		</header>
		
		<section>
			<div class="wallet-one">
				<div class="wallet-info">
					<div class="one-circle-group">
						<div class="circle"></div>
						<div class="circle"></div>
						<div class="circle"></div>
					</div>
					<p>融桥宝提供工资余额自动增值服务，当工资转入融桥宝账户，即自动对接了上海银行e账户并购买了易方达货币基金</p>
				</div>
			</div>
			<img class="wallet-img-one" src="images/what1.png">
			
			<div class="wallet-two">
				<div class="wallet-two-info">
					<div class="two-circle-group">
						<div class="circle"></div>
						<div class="circle"></div>
						<div class="circle"></div>
					</div>
					<p>融桥宝内的资金既能享受稳定的高收益又可通过实时提现当做现金使用也可用于购买其他理财产品。</p>
				</div>
			</div>
			<img class="wallet-img-two" src="images/what2.png">
			<div class="wallet-three">
				<div class="wallet-three-info">
					<p><span class="three-title">稳健收益</span><br />
					<span>最新七日年化收益率
							<span class="shouyi">
								 <c:if test="${weekReceiveRateString !=null }">
								    	${weekReceiveRateString }%
								   </c:if>
							</span></span>
					</p>
				</div>
			</div>
			<img class="wallet-img-three" src="images/what3.png">
			<div class="wallet-four">
				<div class="wallet-four-info">
					<p><span class="four-title">实时提现</span><br />
					<span><span class="shouyi">7X24小时</span>实时到帐</span>
					</p>
				</div>
			</div>
			<img class="wallet-img-four" src="images/what4.png">
		</section>
		
	</body>
</html>