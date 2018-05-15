<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>H5支付</title>
<link href="../styles/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
table {
	margin: 0 auto;
}
</style>
</head>
<body>
<form name="form1" METHOD="POST" ACTION="http://localhost:90/wallet-mobile/api/user/center/uploadHeadCulpture/v1.0.security" ENCTYPE="multipart/form-data">    
    <input name="userId" type= "text" value="US201707110000002661"><br>
    <input type="file" name="file">    
    <input type="submit" value="提交2">     
  </form>  
</body>
</html>