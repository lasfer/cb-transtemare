<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<s:url id="jsonTransportadora" action="jsonListaTransportadoras" />
<div class="result ui-widget-content ui-corner-all" align="center">
	<s:form name="obtenerNumeros" action="NuevaCarpeta" validate="true"
		cssClass="yform" theme="xhtml">
		<sj:autocompleter required="true" id="transportadora"
			label="Transportadora" name="transportadora"
			href="%{jsonTransportadora}" delay="50" loadMinimumCount="2"
			cssStyle="width: 350px " />
		<s:textfield label="Cantidad de MIC/DTA" name="cantidadMIC"
			value="%{cantidadMIC}" required="true" />
		<s:submit name="enviar" value="Crear Carpeta" />
	</s:form>
</div>
