<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String webAppPath = request.getContextPath()+"/";
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>XXX商城</title>
<link href="styles/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
table{ 
	margin:0 auto; 
} 
</style>
</head>
<body>
<!--top -->
<br />
<form name="tempForm" method="post">
<table width="70%" cellspacing="0" border="0" cellspacing="1" >
	  <tr> 
			<td class="info_title" >操作</td>
	  </tr>
	  
	  <tr>
	   <td width="100%"> 
		<table width="100%" border="0" cellspacing="1">

			<tr>
				<td width="200" class="bg_gray" align="right" >1、&nbsp;&nbsp;</td>
				<td align="left">&nbsp;&nbsp;<a href="<%= webAppPath %>orderNew.jsp">订单支付（1.0.1）</a></td>
			</tr>
			<tr>
				<td width="200" class="bg_gray" align="right" >2、&nbsp;&nbsp;</td>
				<td align="left">&nbsp;&nbsp;<a href="<%= webAppPath %>query.jsp">订单查询</a></td>
			</tr>
			<tr>
				<td width="200" class="bg_gray" align="right" >3、&nbsp;&nbsp;</td>
				<td align="left">&nbsp;&nbsp;<a href="<%= webAppPath %>refund.jsp">订单退款</a></td>
			</tr>

		</table>
	   </td>
	  </tr>
	</table>
</form>
</body>
</html>