<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>



<div align="center">

	<s:url id="urlListaEmpresas_2" action="jsonEmpresas" />

	<s:url id="editUrlEmpresas_2" action="ABMEmpresas" />
	<s:url id="urlPaisesEmpresas_2" action="lp" />
	<s:url id="urlCiudadesEmpresas_2" action="lc" />


	<sjg:grid id="gridedittable_2" autoencode="false" caption="Empresas"
		dataType="json" href="%{urlListaEmpresas_2}" pager="true"
		navigator="true"
		navigatorAddOptions="{height:250,width:450,reloadAfterSubmit:true}"
		navigatorEditOptions="{height:250,width:450,reloadAfterSubmit:true}"
		navigatorDelete="true"
		navigatorDeleteOptions="{height:150,width:450,reloadAfterSubmit:true}"
		gridModel="gridModel" rowList="15" rowNum="15"
		editurl="%{editUrlEmpresas_2}" editinline="false">

		<sjg:gridColumn name="id" search="false" index="id" title="ID"
			editable="false" width="30" key="true" required="false" />
		<sjg:gridColumn name="nombre" searchoptions="{sopt:['cn']}"
			index="nombre" id="nombre" title="Nombre" editable="true"
			edittype="text" editrules="{required: true}"
			editoptions="{size: 35, maxlength: 150}" />
		<sjg:gridColumn name="domicilio" search="false" index="domicilio"
			id="domicilio" title="Domicilio" editable="true" edittype="text"
			editrules="{required: true}" editoptions="{size: 35, maxlength: 250}" />
		<sjg:gridColumn name="rol" search="false" index="rol" id="rol"
			title="Rol del cont." editable="true" edittype="text"
			editrules="{required: true}" editoptions="{size: 35, maxlength: 100}" />
		<sjg:gridColumn name="tipo" search="false" index="tipo" title="Tipo"
			editable="true" hidden="true" editrules="{edithidden:true}"
			edittype="select"
			editoptions="{value:'Empresa:Empresa;Cliente:Cliente;Despachante:Despachante;Agencia Maritima:Agencia Maritima'}" />
		<sjg:gridColumn name="tipo" search="false" index="tipo" title="Tipo" />
		<sjg:gridColumn name="nombreCorto" search="false" width="75"
			disabled="true" index="nombreCorto" hidden="true" id="nombreCorto"
			title="Nombre Corto" editable="true" edittype="text"
			editrules="{edithidden:true,required: false}"
			editoptions="{size: 6, maxlength: 6}" />
		<sjg:gridColumn name="pais" search="false" index="pais" title="Pais"
			editable="false" edittype="select"
			editoptions="{ dataUrl:'%{urlPaisesEmpresas_2}' }" />
		<sjg:gridColumn name="ciudad" search="false" index="ciudad"
			title="Ciudad" editable="true" edittype="select"
			editoptions="{dataUrl:'%{urlCiudadesEmpresas_2}'}" />

	</sjg:grid>

	<!--    <sj:submit id="grid_edit_addbutton" value="Nueva Empresa" onClickTopics="rowadd" button="true"  />-->


</div>


<div id="ie_clearing">&#160;</div>
