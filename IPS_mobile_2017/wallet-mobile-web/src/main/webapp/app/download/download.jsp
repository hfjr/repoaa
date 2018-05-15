<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no" />    
<meta name="description" content="" />
<meta name="author" content="" />
<link rel="icon" href="<c:url value='/static/image/ico.ico'/>" mce_href="<c:url value='/static/image/ico.ico'/>" type="image/x-icon" />
<link rel="shortcut icon" href="<c:url value='/static/image/ico.ico'/>" mce_href="<c:url value='/static/image/ico.ico'/>" type="image/x-icon" />
<title>融桥宝下载</title>

<style>
	
</style>
</head>
<body>

<div class="" align="center" >
	<strong style="font-size: 12px;display: none;" id="weixinTip" name="weixinTip">温馨提示：在微信中，若超过3秒未反应，您可以点击微信右上角，“用浏览器打开” - 即可下载融桥宝</strong>
</div>
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
<script>
	// 判断是否是微信
	function isWeiXin() {
		var ua = window.navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i) == 'micromessenger') {
			return true;
		} else {
			return false;
		}
	}
	var browser = {
		versions : function() {
			var u = navigator.userAgent, app = navigator.appVersion;
			return { //移动终端浏览器版本信息
				trident : u.indexOf('Trident') > -1, //IE内核
				presto : u.indexOf('Presto') > -1, //opera内核
				webKit : u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
				gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
				mobile : !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
				ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
				android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
				iPhone : u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
				iPad : u.indexOf('iPad') > -1, //是否iPad
				webApp : u.indexOf('Safari') == -1
			//是否web应该程序，没有头部与底部
			};
		}(),
		language : (navigator.browserLanguage || navigator.language)
				.toLowerCase()
	};
	
	var flag = false;
	setTimeout(function(){
		if(flag){
			return;
		}
		startDownload();
	}, 100);
	
	if(isWeiXin()){
		document.getElementById("weixinTip").style.display='block';
	}
	function startDownload(){
		flag = true;
		
		if (browser.versions.iPhone || browser.versions.iPad) {
			window.location.href = "https://itunes.apple.com/us/app/gong-zi-qian-bao/id1024437486?l=zh&ls=1&mt=8";
		}else {
			window.location.href = "http://app.gongziqianbao.com/wallet-mobile/resources/android/wallet_app.apk";
		}
// 		if(isWeiXin()){
// 			if (browser.versions.iPhone || browser.versions.iPad) {
// 	            //window.location.href = "http://mp.weixin.qq.com/mp/redirect?url=https%3A%2F%2Fitunes.apple.com%2Fus%2Fapp%2Fgong-zi-qian-bao%2Fid1024437486%3Fl%3Dzh%26ls%3D1%26mt%3D8";
// 	            //window.location.href = "http://fir.im/ewbn";
// 	        } else {
// 	        	//window.location.href = "<c:url value='/resources/android/wallet_app.apk'/>";
// 	        	window.location.href = "http://app.vjwealth.com/wallet-app/resources/android/wallet_app.apk";
// 	        }	
// 		}else {
// 			if (browser.versions.iPhone || browser.versions.iPad) {
// 				window.location.href = "https://itunes.apple.com/us/app/gong-zi-qian-bao/id1024437486?l=zh&ls=1&mt=8";
// 			} else {
// 				//window.location.href = "<c:url value='/resources/android/wallet_app.apk'/>";
// 				window.location.href = "http://app.vjwealth.com/wallet-app/resources/android/wallet_app.apk";
// 			}
// 		}
	}
</script>
</body>
</html>
