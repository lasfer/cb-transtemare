<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jmesa.css"></link>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.jmesa.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jmesa.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.migrate.js"></script>

<script type="text/javascript">	
    <%--       
 	      Si no es ajax
    function onInvokeAction(id) {
                 $.jmesa.setExportToLimit(id, '');
                 $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
    }--%>
	function onInvokeAction(id) {
		setExportToLimit(id, '');
		var parameterString = createParameterStringForLimit(id);
		$.get(
				'${pageContext.request.contextPath}/CarpetasACargarGarantiaReport?ajax=true&'
						+ parameterString, function(data) {
					$("#presidents").html(data);
				}, "text");
	}
	function onInvokeExportAction(id) {
		var parameterString = $.jmesa.createParameterStringForLimit(id);
		location.href = '${pageContext.request.contextPath}/CarpetasACargarGarantiaReport?'
				+ parameterString;
	}
</script>
<form name="presidentsForm"
	action="${pageContext.request.contextPath}/CarpetasACargarGarantiaReport">
	<div id="presidents">${tabla}</div>
</form>
