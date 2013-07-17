<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<div align="center">
	<s:url id="urlListaChoferes_1" action="jsonChoferes" />
	<s:url id="editurlChoferes_1" action="ABMChoferes" />
	<s:url id="urlPaisesChoferes_1" action="lp" />
	<s:url id="urlCiudadesChoferes_1" action="lc" />
	<sjg:grid autoencode="false" id="gridedittable_1" caption="Choferes"
		dataType="json" href="%{urlListaChoferes_1}" pager="true"
		navigator="true"
		navigatorAddOptions="{height:280,reloadAfterSubmit:true,top:1,left:1,width:350}"
		navigatorEditOptions="{height:280,reloadAfterSubmit:true, width:350}"
		navigatorDelete="true"
		navigatorDeleteOptions="{height:280,reloadAfterSubmit:true}"
		gridModel="gridModel" rowList="15" rowNum="15"
		editurl="%{editurlChoferes_1}" editinline="false">
		<sjg:gridColumn name="id" index="id" title="ID" search="false"
			editable="false" width="30" key="true" required="false" />
		<sjg:gridColumn name="nombre" index="nombre" search="true"
			searchoptions="{sopt:['cn']}" id="nombre" title="Nombre"
			editable="true" edittype="text" editrules="{required: true}" />
		<sjg:gridColumn name="apellido" index="apellido" search="true"
			searchoptions="{sopt:['cn']}" id="apellido" title="Apellido"
			editable="true" edittype="text" editrules="{required: true}" />
		<sjg:gridColumn name="documento" index="documento" search="false"
			id="documento" title="Documento" editable="true" edittype="text"
			editrules="{required: true}" />
		<sjg:gridColumn name="telefono" index="telefono" search="false"
			id="telefono" title="Telefono" editable="true" edittype="text"
			editrules="{required: false}" />
		<sjg:gridColumn name="pais" index="pais" search="false" title="Pais"
			editable="false" edittype="select"
			editoptions="{ dataUrl:'%{urlPaisesChoferes_1}' }" />
		<sjg:gridColumn name="ciudad" index="ciudad" search="false"
			title="Ciudad" editable="true" edittype="select"
			editoptions="{ dataUrl:'%{urlCiudadesChoferes_1}' }" />
	</sjg:grid>
	<sj:submit id="grid_edit_addbutton" value="Crear Chofer"
		onClickTopics="rowadd" button="true" />
</div>
<div id="ie_clearing">&#160;</div>

