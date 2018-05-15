<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>开户</title>

<script type="text/javascript">
function load()
{
	
	document.getElementById("login_form_x").submit();
}
</script>
</head>
<body onload="load()">

<form  id="login_form_x" action="${urls}" method="post" >
        	    <input type="hidden"  name="operationType" value="${operationType}">
				<input type="hidden"  name="merchantID" value="${merchantID}">
				<input type="hidden"  name="sign" value="${sign}">
				<input type="hidden"  name="request" value="${request}">
    </form>
</body>
</html>