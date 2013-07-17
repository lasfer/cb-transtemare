<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<div id="reloate" align="center">
	<s:url id="urlTableCamiones" action="jsonTableCamiones" />
	<s:url id="editurl" action="ABMCamiones" />
	<sjg:grid autoencode="false" id="gridedittable" caption="Camiones"
		dataType="json" href="%{urlTableCamiones}" pager="true"
		navigator="true"
		navigatorAddOptions="{height:320,reloadAfterSubmit:true,top:1,left:1,width:350}"
		navigatorEditOptions="{height:320, width:350, reloadAfterSubmit:true}"
		navigatorDelete="true"
		navigatorDeleteOptions="{height:280,reloadAfterSubmit:true}"
		gridModel="gridModel" rowList="15" rowNum="15" editurl="%{editurl}"
		editinline="false">
		<sjg:gridColumn name="id" index="id" title="ID" search="false"
			editable="false" width="30" key="true" required="false" />
		<sjg:gridColumn name="matricula" index="matricula" id="matricula"
			title="Matricula" search="true" searchoptions="{sopt:['cn']}"
			editable="true" edittype="text" editrules="{required: true}" />
		<sjg:gridColumn name="capacidad" index="capacidad" id="capacidad"
			title="Capacidad" search="false" editable="true" edittype="text"
			editrules="{required: true}" />
		<sjg:gridColumn name="marca" index="marca" id="marca" title="Marca"
			search="false" editable="true" edittype="text"
			editrules="{required: true}" editoptions="{size: 25, maxlength: 50}" />
		<sjg:gridColumn name="anio" index="anio" id="anio" title="Anio"
			search="false" editable="true" edittype="text"
			editrules="{number: true,required: true,minValue : 1,maxValue : 9999}" />
		<sjg:gridColumn name="tipo" index="tipo" title="Tipo" search="false"
			editable="true" hidden="true" editrules="{edithidden:true}"
			edittype="select"
			editoptions="{value:'Camion:Camion;Remolque:Remolque'}" />
		<sjg:gridColumn name="tipo" index="tipo" title="Tipo" search="false" />
		<sjg:gridColumn name="numeroPoliza" index="numeroPoliza"
			id="numeroPoliza" title="Nro. Poliza" search="false" editable="true"
			edittype="text" hidden="true" editrules="{edithidden:true,required: false}" />
		<sjg:gridColumn name="vencimientoPoliza" index="vencimientoPoliza"
			id="vencimientoPoliza" title="Venc. Poliza" search="false"
			editable="true" edittype="text" formatter="date"
			formatoptions="{newformat : 'd/m/Y'}"  hidden="true" editrules="{edithidden:true,required: false}"  />
	</sjg:grid>
</div>
<div id="ie_clearing">&#160;</div>








