<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="org.apache.commons.beanutils.BeanUtils"%>

<%@page import="com.fuiou.pay.client.txn.NewOrderData"%>

<%@page import="com.fuiou.pay.client.util.MD5"%>

<%@page import="com.fuiou.pay.client.util.StringUtils"%>

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

	<%

	String xml ="";
	
try{

	request.setCharacterEncoding("UTF-8");

	String mchnt_cd = StringUtils.nvl(request.getParameter("mchnt_cd"));

	String order_id = StringUtils.nvl(request.getParameter("order_id"));

	String order_amt = StringUtils.nvl(request.getParameter("order_amt"));

	String order_pay_type = StringUtils.nvl(request.getParameter("order_pay_type"));

	String page_notify_url = StringUtils.nvl(request.getParameter("page_notify_url"));

	String back_notify_url = StringUtils.nvl(request.getParameter("back_notify_url"));

	String order_valid_time = StringUtils.nvl(request.getParameter("order_valid_time"));

	String iss_ins_cd = StringUtils.nvl(request.getParameter("iss_ins_cd"));

	String goods_name = StringUtils.nvl(request.getParameter("goods_name"));

	String goods_display_url = StringUtils.nvl(request.getParameter("goods_display_url"));

	String mchnt_key = StringUtils.nvl(request.getParameter("mchnt_key")); //32位的商户密钥

	String rem = StringUtils.nvl(request.getParameter("rem"));

	String ver = "1.0.1";


	String signDataStr = mchnt_cd + "|" + order_id+ "|" +order_amt+ "|" +order_pay_type+ "|" +
 
                    page_notify_url+ "|" +back_notify_url+ "|" +order_valid_time+ "|" +

                     iss_ins_cd+ "|" +goods_name+ "|" +goods_display_url+ "|" 
 +rem+ "|" +ver+ "|" + mchnt_key;
                     

String md5 = MD5.MD5Encode(signDataStr);

System.out.println("signDataStr==="+signDataStr);

System.out.println("md5==="+md5);

%>

<body onload="javascript:submitForm();">

<!--支付网关测试环境地址：http://www-1.fuiou.com:8888/wg1_run/smpGate.do-->

<!--支付网关生产环境地址：https://pay.fuiou.com/smpGate.do-->
 
<!-- <form name="pay" method="post" action="http://www-1.fuiou.com:8888/wg1_run/smpGate.do" id = "form"> -->
<form name="pay" method="post" action="https://pay.fuiou.com/smpGate.do" id = "form">

<input type="hidden" value = '<%=md5%>' name="md5"/>

<input type="hidden" value = '<%=mchnt_cd%>' name="mchnt_cd"/>

<input type="hidden" value = '<%=order_id%>' name="order_id"/>

<input type="hidden" value = '<%=order_amt%>' name="order_amt"/>

<input type="hidden" value = '<%=order_pay_type%>' name="order_pay_type"/>

<input type="hidden" value = '<%=page_notify_url%>' name="page_notify_url"/>

<input type="hidden" value = '<%=back_notify_url%>' name="back_notify_url"/>

<input type="hidden" value = '<%=order_valid_time%>' name="order_valid_time"/>

<input type="hidden" value = '<%=iss_ins_cd%>' name="iss_ins_cd"/>

<input type="hidden" value = '<%=goods_name%>' name="goods_name"/>

<input type="hidden" value = '<%=goods_display_url%>' name="goods_display_url"/>

<input type="hidden" value = '<%=rem%>' name="rem"/>

<input type="hidden" value = '<%=ver%>' name="ver"/>

</form>
</body>

<%}catch(Exception e){

	e.printStackTrace();
	out.print(e.getMessage());
} %>

<body>
</body>

</html>
