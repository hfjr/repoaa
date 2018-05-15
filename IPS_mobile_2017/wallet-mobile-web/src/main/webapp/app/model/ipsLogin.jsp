<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>平台下用户登录</title>
</head>

<script type="text/javascript">

function load()
{
	
	document.getElementById("login_form_x").submit();
}
</script> 

<body  onload="load()">

<form  id="login_form_x" action="${url}" method="post" >

        		
        	    <input type="hidden"  name="userName" value="${userName}">
				<input type="hidden"  name="merchantId" value="${merchantId}">

</form>
</body>
</html>