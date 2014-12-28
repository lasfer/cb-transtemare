<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
	var destGarantiasCarga='CarpetasACargarGarantiaReport';
	var destGarantiasInfo='GarantiaCarpetasReport';
    var dest=destGarantiasCarga;
    
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
				'${pageContext.request.contextPath}/'+dest+'?ajax=true&'
						+ parameterString, function(data) {
					$("#reporte").html(data);
				}, "text");
	}
	function onInvokeExportAction(id) {
		var parameterString = $.jmesa.createParameterStringForLimit(id);
		location.href = '${pageContext.request.contextPath}/'+dest+'?'
				+ parameterString;
	}
	
	function changeDest(destParam) {
		dest=destParam;
		$('form').get(0).setAttribute('action', '${pageContext.request.contextPath}/'+dest);
		if(dest != destGarantiasCarga){
			$('#boton_guardar').hide();
		}else{
			$('#boton_guardar').show();
		}
	}
</script>

   <s:url id="ajaxCargaGarantia" value="CarpetasACargarGarantiaReport?ajax=true"/>    
	<sj:a id="ajaxlink" 
		href="%{ajaxCargaGarantia}" 
		targets="reporte" 
		indicator="indicator" 
		button="true" 
		buttonIcon="ui-icon-refresh"
		onclick="changeDest(destGarantiasCarga);"
	>
	  	Carga garantías
	</sj:a>
	
	<s:url id="ajaxReporteCarpetas" value="GarantiaCarpetasReport?ajax=true"/>    
	<sj:a id="ajaxlink2" 
		href="%{ajaxReporteCarpetas}" 
		targets="reporte" 
		indicator="indicator" 
		button="true" 
		buttonIcon="ui-icon-refresh"
		onclick="changeDest(destGarantiasInfo);"
	>
	  	Devoluciones
	</sj:a>

<form name="form"
	action="none">
	<div id="reporte">${tabla}</div>
</form>
<div id="boton_guardar">
	<br>
	<p align="center">
		<sj:a id="ajaxlink3" indicator="indicator" button="true"
			buttonIcon="ui-icon-refresh"
			onclick="javascript:jQuery.jmesa.setSaveToWorksheet('tag');onInvokeAction('tag','save_worksheet');">
		  	Guardar Todo
		</sj:a>
	</p>
</div>

