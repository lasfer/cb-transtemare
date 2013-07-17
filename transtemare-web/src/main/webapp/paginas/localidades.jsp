<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<div id="reloate" align="center">
	<s:url id="urlTableLocalidades" action="jsonTableLocalidades" />
	<s:url id="paises" action="lp" />
	<s:url id="editurl" action="ABMLocalidades" />
	<sjg:grid autoencode="false" id="gridedittable" caption="Localidades"
		dataType="json" href="%{urlTableLocalidades}" pager="true"
		navigator="true" 
		navigatorAddOptions="{height:200,reloadAfterSubmit:true,top:1,left:1,width:300}"
		navigatorEditOptions="{height:280,reloadAfterSubmit:true}"
		navigatorDelete="true" 
		navigatorSearch="false"
		navigatorDeleteOptions="{height:280,reloadAfterSubmit:true}"
		gridModel="gridModel" 
		rowList="15" 
		rowNum="20" 
		editurl="%{editurl}"
		editinline="false">
		<sjg:gridColumn name="idLocalidad" index="idLocalidad" title="ID"
			editable="false" key="true" width="30" required="false" />
		<sjg:gridColumn name="descripcion" index="descripcion"
			id="descripcion" title="Descripcion" editable="true" edittype="text"
			editrules="{required: true}" />
		<sjg:gridColumn name="pais.descripcion" index="pais.descripcion"
			title="Pais" editable="true" edittype="select"
			editoptions="{ dataUrl:'%{paises}' }" width="85" />
		<sjg:gridColumn name="aduana" index="aduana" title="Es Aduana"
			editable="true" edittype="checkbox"
			editoptions="{value:'true:false'}" />
	</sjg:grid>
</div>
<div id="ie_clearing">&#160;</div>








