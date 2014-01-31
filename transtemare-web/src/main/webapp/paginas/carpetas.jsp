<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="sjt" uri="/struts-jquery-tree-tags"%>
<%--<%@ taglib prefix="jo" uri="/struts-json-tags" %>--%>

<s:url id="urlCarpetas" action="jsonCarpetas" />
<s:url id="urlSubCarpetas" action="jsonCarpetasSubLista" />
<s:url includeParams="none" id="CrearCarpetaPaso1"
	action="CrearCarpetaPaso1" />
<s:url id="urlResumenCortoCarpeta" action="resumenCortoCarpeta">
	<s:param name="id" value="%{c.idCarpeta}" />
</s:url>

<div id="col1" style="width: 80%">
	<div id="col1_content" class="clearfix">
		<sjg:grid autoencode="false" id="gridedittable" caption="Carpetas"
			dataType="json" href="%{urlCarpetas}" pager="true" width="800"
			navigator="true" gridModel="gridModel" rowList="15,30,60" rowNum="30"
			navigatorDelete="false" navigatorAdd="false" navigatorEdit="false"
			onSelectRowTopics="rowselectCarpetasEditar,rowselectCarpetasCrt,rowselectCarpetasCaratula,rowselectCarpetasMicDta,rowselectCarpetasPreview"
			navigatorSearchOptions="{sopt:['eq']}">
			<sjg:grid autoencode="false" id="editarSubCarpeta"
				subGridUrl="%{urlSubCarpetas}" gridModel="gridModel"
				onSelectRowTopics="rowselectCarpetasEditar">
				<sjg:gridColumn name="numeroCarpeta" index="numeroCarpeta"
					search="true" title="Nro. Carpeta" key="true" required="false" />
				<sjg:gridColumn name="numeroContenedorFormateado"
					index="numeroContenedor" search="false" id="numeroContenedor"
					title="Contenedor" />
			</sjg:grid>
			<sjg:gridColumn name="numeroCarpeta" index="numeroCarpeta"
				search="true" searchoptions="{sopt:['eq']}" title="Numero"
				key="true" required="false" width="65" />
			<sjg:gridColumn name="numeroDocumento" index="numeroDocumento"
				search="true" searchoptions="{sopt:['eq'],searchhidden : true}" title="Documento"
				required="false" width="0" hidden="true" />
			<sjg:gridColumn name="referenciaDestino" index="referenciaDestino"
				search="false" title="Nro. Paraguay" key="true" required="false"
				width="130" />
			<sjg:gridColumn name="numeroContenedorFormateado"
				index="numeroContenedor" search="true" id="numeroContenedor"
				title="Contenedor" width="130"  searchoptions="{sopt:['eq']}"/>
			<sjg:gridColumn name="transportadora" index="transportadora"
				search="false" id="transportadora" title="Transportadora"
				width="130" />
			<sjg:gridColumn name="terminal" index="terminal" search="false"
				id="terminal" title="Terminal" width="75" />
			<sjg:gridColumn name="agenciaMaritima" index="agenciaMaritima"
				search="false" id="agenciaMaritima" title="Agencia" width="130" />
			<sjg:gridColumn name="fechaLlegadaBuque" align="center"
				search="false" index="fechaLlegadaBuque" id="fechaLlegadaBuque"
				title="Llegada" formatter="date" width="75"
				formatoptions="{newformat : 'd-m-Y', srcformat : 'Y-m-d H:i:s'}" />
		</sjg:grid>

	</div>
</div>

<div id="col3">
	<div id="col3_content">
		<ul>
			<li><sj:a id="Carpetas1" href="%{CrearCarpetaPaso1}"
					targets="main">CREAR CARPETA</sj:a></li>
			<li><sj:div id="gridEditarCarpetas" /></li>
			
			<li><sj:div id="gridCrt" /></li>
			<li><sj:div id="gridMicDta" /></li>
			<li><sj:div id="gridCaratula" /></li>
		</ul>
		<div id='previewID' class='result ui-widget-content ui-corner-all'>
		</div>
	</div>
</div>

<!-- <div id="col3"> -->
<!-- 	<div id="col3_content"> -->
<!-- 		<div id='tree' class='result ui-widget-content ui-corner-all'> -->

<!-- 		<h2>Static Tree</h2> -->
<!--     <p class="text"> -->
<!--         A simple static Tree Component. -->
<!--     </p> -->
<%--     	<sjt:tree id="treeStatic" jstreetheme="default" openAllOnLoad="true"> --%>
<%--     		<sjt:treeItem title="Struts2"> --%>
<%-- 	    		<sjt:treeItem title="General"> --%>
<%-- 		    		<sjt:treeItem title="Struts2" href="http://struts.apache.org/2.x/index.html"/> --%>
<%-- 		    		<sjt:treeItem title="Struts2 @ Facebook" href="http://www.facebook.com/pages/Struts2-Users/103890046351798"/> --%>
<%--     			</sjt:treeItem> --%>
<%-- 	    		<sjt:treeItem title="Plugins"> --%>
<%-- 		    		<sjt:treeItem title="Struts2 Plugins" href="https://cwiki.apache.org/S2PLUGINS/home.html"/> --%>
<%-- 		    		<sjt:treeItem title="Struts2 jQuery Plugin" href="http://code.google.com/p/struts2-jquery/"/> --%>
<%-- 		    		<sjt:treeItem title="Struts2 Full Hibernate Plugin" href="http://code.google.com/p/full-hibernate-plugin-for-struts2/"/> --%>
<%--     			</sjt:treeItem> --%>
<%-- 	    		<sjt:treeItem title="Blogs"> --%>
<%-- 		    		<sjt:treeItem title="Struts2 jQuery News" href="http://www.jgeppert.com/category/java/struts2-jquery/"/> --%>
<%--     			</sjt:treeItem> --%>
<%-- 	    		<sjt:treeItem title="AJAX Links"> --%>
<%-- 					<s:url id="ajax1" value="/ajax1.action"/> --%>
<%-- 		    		<sjt:treeItem title="Ajax 1" href="%{ajax1}" targets="result"/> --%>
<%-- 					<s:url id="ajax2" value="/ajax2.action"/> --%>
<%-- 		    		<sjt:treeItem title="Ajax 2" href="%{ajax2}" targets="result" effect="highlight" effectDuration="2500"/> --%>
<%-- 					<s:url id="ajax3" value="/ajax3.action"/> --%>
<%-- 		    		<sjt:treeItem title="Ajax 3" href="%{ajax3}" targets="result" onBeforeTopics="beforeLink" onCompleteTopics="completeLink"/> --%>
<%-- 					<s:url id="ajax4" value="/ajax4.action"/> --%>
<%-- 		    		<sjt:treeItem title="Ajax 4" href="%{ajax4}" targets="result" effect="bounce" effectDuration="1000"/> --%>
<%--     			</sjt:treeItem> --%>
<%--     		</sjt:treeItem> --%>
<%--     	</sjt:tree> --%>

<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->
<!-- IE Column Clearing -->
<div id="ie_clearing">&#160;</div>



