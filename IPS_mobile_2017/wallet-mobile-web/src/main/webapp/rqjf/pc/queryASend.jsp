<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="org.apache.commons.beanutils.BeanUtils"%>
<%@page import="com.fuiou.pay.client.txn.NewOrderData"%>
<%@page import="com.fuiou.pay.client.util.MD5"%>
<%@page import="com.fuiou.pay.client.util.StringUtils"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付结果查询</title>
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
String mchnt_key = StringUtils.nvl(request.getParameter("mchnt_key")); //32位秘钥

String signDataStr = mchnt_cd + "|" + order_id+ "|"
                   + mchnt_key;
System.out.println(signDataStr);
String md5 = MD5.MD5Encode(signDataStr);
%>
<body onload="javascript:submitForm();">
<!--查询(直接返回)测试环境地址：http://www-1.fuiou.com:8888/wg1_run/smpAQueryGate.do-->
<!-- 						  https://pay.fuiou.com/smpAQueryGate.do  -->
<!--查询(直接返回)生产环境地址：https://pay.fuiou.com/smpAQueryGate.do-->
 <form name="pay" method="post" action="https://pay.fuiou.com/smpAQueryGate.do" id = "form">
<input type="hidden" value = '<%=md5%>' name="md5"/>
<input type="hidden" value = '<%=mchnt_cd%>' name="mchnt_cd"/>
<input type="hidden" value = '<%=order_id%>' name="order_id"/>
</form>
</body>
<%}catch(Exception e){
	e.printStackTrace();
	out.print(e.getMessage());
} %>
<body>
</body>
</html>