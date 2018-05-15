<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<html>

<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<title>提交到富友交易系统</title>

</head>

<script type="text/javascript">

	function submitForm(){
		
		document.getElementById("form").submit();

	}

</script>


<body onload="javascript:submitForm();">

<!--支付网关测试环境地址：http://www-1.fuiou.com:8888/wg1_run/smpGate.do-->

<!--支付网关生产环境地址：https://pay.fuiou.com/smpGate.do-->
 
<!-- <form name="pay" method="post" action="http://www-1.fuiou.com:8888/wg1_run/smpGate.do" id = "form"> -->
<form name="pay" method="post" action="https://pay.fuiou.com/smpGate.do" id = "form">

<input type="hidden" value = '${md5}' name="md5"/>

<input type="hidden" value = '${mchnt_cd}' name="mchnt_cd"/>

<input type="hidden" value = '${order_id}' name="order_id"/>

<input type="hidden" value = '${order_amt}' name="order_amt"/>

<input type="hidden" value = '${order_pay_type}' name="order_pay_type"/>

<input type="hidden" value = '${page_notify_url}' name="page_notify_url"/>

<input type="hidden" value = '${back_notify_url}' name="back_notify_url"/>

<input type="hidden" value = '${order_valid_time}' name="order_valid_time"/>

<input type="hidden" value = '${iss_ins_cd}' name="iss_ins_cd"/>

<input type="hidden" value = '${goods_name}' name="goods_name"/>

<input type="hidden" value = '${goods_display_url}' name="goods_display_url"/>

<input type="hidden" value = '${rem}' name="rem"/>

<input type="hidden" value = '${ver}' name="ver"/>

</form>
</body>


<body>
</body>

</html>
