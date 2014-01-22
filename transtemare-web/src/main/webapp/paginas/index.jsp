<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>Transtemare</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="description" content="Sistema de gestion" />
	<link href="styles/layout.css" rel="stylesheet" type="text/css" />
	<!--[if lte IE 7]>
	<link href="styles/patch_layout.css" rel="stylesheet" type="text/css" />
	<![endif]-->

	<!-- This files are needed for AJAX Validation of XHTML Forms -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/struts/utils.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/struts/xhtml/validation.js"></script>
	<sj:head debug="true" locale="es" jquerytheme="showcase"
		customBasepath="themes" loadFromGoogle="true"
		defaultIndicator="indicator" />
</head>
<body>

	<div class="page_margins">
		<div class="page">
			<div id="header" class="ui-widget-header">
				<div id="themebox"></div>
				<div id="headline">
					<h1 class="ui-state-default"
						style="background: none; border: none;">Transtemare</h1>
					<h4 class="ui-state-default"
						style="background: none; border: none;">Sistema de gestion</h4>
					<s:url id="usuariosActivos" action="usuariosActivos" />
					<sj:div id="div2" href="%{usuariosActivos}"
						reloadTopics="reloaddiv2" onCompleteTopics="completediv2"
						timeout="5000" indicator="indicator" effect="pulsate"
						effectDuration="250">
					</sj:div>
					<img id="indicator" src="images/ajax-loader.gif" alt="Cargando..."
						style="display: none" />
				</div>
			</div>
			<div id="nav">
				<div class="hlist ui-widget-header">
					<ul>
						<li class="ui-widget-header"><s:url includeParams="none"
								id="urlTransportadoras" action="urlTransportadoras" />
							<sj:a id="Transportadoras" href="%{urlTransportadoras}"
								targets="main">Transportadoras</sj:a></li>
						<li class="ui-widget-header"><s:url includeParams="none"
								id="urlAlarmasCarpetas" action="urlAlarmasCarpetas" />
							<sj:a id="alarmasCarpetas" href="%{urlAlarmasCarpetas}"
								targets="main">Alarmas</sj:a></li>
						<li class="ui-widget-header"><s:url includeParams="none"
								id="urlCamiones" action="urlCamiones" />
							<sj:a id="Camiones" href="%{urlCamiones}" targets="main">Camiones</sj:a></li>
						<li class="ui-widget-header"><s:url includeParams="none"
								id="urlChoferes" action="urlChoferes" />
							<sj:a id="Choferes" href="%{urlChoferes}" targets="main">Choferes</sj:a></li>
						<li class="ui-widget-header"><s:url includeParams="none"
								id="urlEmpresas" action="urlEmpresas" />
							<sj:a id="Emresas" href="%{urlEmpresas}" targets="main">Empresas</sj:a></li>
							
						<li class="ui-widget-header"><s:url includeParams="none" id="urlTerminales" action="urlTerminales" />
							<sj:a id="Terminales" href="%{urlTerminales}" targets="main">Terminales</sj:a>
						</li>
							
						<li class="ui-widget-header"><s:url includeParams="none"
								id="urlCarpetas" action="urlCarpetas" />
							<sj:a id="Carpetas" href="%{urlCarpetas}" targets="main">Carpetas</sj:a></li>
						<li class="ui-widget-header"><s:url includeParams="none"
								id="urlCarpetasHistorico" action="urlCarpetasHistorico" />
							<sj:a id="CarpetasHistorico" href="%{urlCarpetasHistorico}"
								targets="main">Historico</sj:a></li>
								<li class="ui-widget-header"><s:url includeParams="none"
								id="urlLocalidades" action="urlLocalidades" />
							<sj:a id="Localidades" href="%{urlLocalidades}" targets="main">Localidades</sj:a></li>
						<li class="ui-widget-header"><s:url includeParams="none"
								id="urlRep" action="CarpetasACargarGarantiaReport" />
							<sj:a id="Rep" href="%{urlRep}" targets="main">Reportes</sj:a></li>
						<li class="ui-widget-header"><s:url includeParams="none"
								id="salir" action="logout" />
							<sj:a id="Salir" href="%{salir}" targets="main">SALIR</sj:a></li>
					</ul>
				</div>
			</div>
			<sj:div id="main" href="%{destino}">
				<img id="indicator" src="images/indicator.gif" alt="Cargando..." />
			</sj:div>
			<!-- begin: #footer -->
			<div id="footer">
				<!--        Written by  <a href="http://www.jgeppert.com" title="Java Developer Blog">Johannes Geppert</a><br/>-->
				<!--        Hosted by  <a href="http://www.weinfreund.de" title="Wein vom Weingut, Weinforum, Wein Community">weinfreund.de</a><br/>-->
				<!--        Layout based on <a href="http://www.yaml.de/" title="OpenSource CSS Layout">YAML</a>-->
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//Make it possible to link to an specific site in the Showcase
		//e.g. and link to index.action?ajaxhistory=true#main=_sj_action_accordionlink
		//opens now the accordion examples
		jQuery(document).ready(function() {
			if (jQuery.struts2_jquery.ajaxhistory) {
				var topic = $.bbq.getState('main');
				if (topic !== undefined && topic != '') {
					jQuery.publish(topic);
				}
			}
		});
		var ctx = "${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/commons.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.numberformatter-1.2.3.min.js"></script>
</body>
</html>
