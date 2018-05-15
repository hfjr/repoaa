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
	<br />
	<form name="h5pay" method="post" action="<c:url value='/page/fuiou/pay/gateway/v1.0'/>">
		<table width="70%" cellspacing="0" border="0" cellspacing="1">
			<tr>
				<td class="info_title">H5手机支付(2.0)</td>
			</tr>
			<tr>
				<td width="100%">
					<table width="100%" border="0" cellspacing="1">
						<tr>
							<td width="200" class="bg_gray" align="right">客户编号：&nbsp;&nbsp;</td>
							<td class="bg_yellow2" align="left">&nbsp;&nbsp;<input
								type="text" name="userId" size='30' value='88888888' /></td>
						</tr>
						<tr>
							<td width="200" class="bg_gray" align="right">交易金额(分)：&nbsp;&nbsp;</td>
							<td class="bg_yellow2" align="left">&nbsp;&nbsp;<input
								type="text" name="amt" size='30' maxlength='100' value='1' /></td>
						</tr>
						<tr>
							<td width="200" class="bg_gray" align="right">银行卡号：&nbsp;&nbsp;</td>
							<td class="bg_yellow2" align="left">&nbsp;&nbsp;<input
								type="text" name="bankCard" size='30' maxlength='100'
								value='6216910205717926' /></td>
						</tr>
						<tr>
							<td width="200" class="bg_gray" align="right">姓名：&nbsp;&nbsp;</td>
							<td class="bg_yellow2" align="left">&nbsp;&nbsp;<input
								type="text" name="name" size='30' maxlength='100'
								value='孙悟空' /></td>
						</tr>
						<tr>
							<td width="200" class="bg_gray" align="right">证件类型：&nbsp;&nbsp;</td>
							<td class="bg_yellow2" align="left">&nbsp;&nbsp;<select
								name="idType">
									<option selected value="0">身份证</option>
									<option value="1">护照</option>
									<option value="2">军官证</option>
									<option value="3">士兵证</option>
									<option value="4">回乡证</option>
									<option value="6">户口本</option>
									<option value="7">其他</option>
							</select></td>
						</tr>
						<tr>
							<td width="200" class="bg_gray" align="right">证件号：&nbsp;&nbsp;</td>
							<td class="bg_yellow2" align="left">&nbsp;&nbsp;<input
								type="text" name="idNo" size='30' maxlength='100'
								value='420116199001011234' /></td>
						</tr>
						<tr>
							<td width="200" class="bg_gray" align="right">类型：&nbsp;&nbsp;</td>
							<td class="bg_yellow2" align="left">&nbsp;&nbsp;<select
								name="type">
									<option selected value="10">理财版</option>
									<option value="11">电商版</option>
							</select></td>
						</tr>
						<tr>
							<td height="50">&nbsp;</td>
							<td><input type="submit" name="Submit" value="确 定" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="reset" name="Submit2" value="重 置" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>