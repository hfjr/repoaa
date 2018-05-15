<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<c:if test="${chinaPaymentDTO.successful == true }">
		正在跳转银联支付页面...
		<form name="payment" action="http://payment.chinapay.com/pay/TransGet" method="POST" target="_aa">
			<input type=hidden name="MerId" value="${chinaPaymentDTO.merId }" />
			<input type=hidden name="OrdId" value="${chinaPaymentDTO.ordId }" />
			<input type=hidden name="TransAmt" value="${chinaPaymentDTO.transAmt }" /> 
			<input type=hidden name="CuryId" value="${chinaPaymentDTO.curyId }" /> 
			<input type=hidden name="TransDate" value="${chinaPaymentDTO.transDate }" />
			<input type=hidden name="TransType" value="${chinaPaymentDTO.transType }" /> 
			<input type=hidden name="Version" value="${chinaPaymentDTO.version }"/> 
<!-- 			<input type=hidden name="Version" value="20040916"/>  -->
<!-- 			<input type=hidden name="Version" value="20070129"/>  -->
			<input type=hidden name="BgRetUrl" value="${chinaPaymentDTO.bgRetUrl }" /> 
			<input type=hidden name="PageRetUrl" value="${chinaPaymentDTO.pageRetUrl }" />
			<input type=hidden name="GateId" value="${chinaPaymentDTO.gateId }" />
			<input type=hidden name="Priv1" value="${chinaPaymentDTO.priv1 }" />
			<input type=hidden name="ChkValue" value="${chinaPaymentDTO.chkValue }" /> 
		</form>
		<script type="text/javascript">
			setTimeout(function(){
				document.payment.submit();
				
			}, 3000);
		</script>
	</c:if>
	<c:if test="${chinaPaymentDTO.successful == true }">
		系统异常，请关闭此窗口重新提交!		
	</c:if>
</body>
</html>
