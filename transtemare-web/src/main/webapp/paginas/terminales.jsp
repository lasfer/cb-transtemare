<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>



<div align="center">

	<s:url id="urlListarTerminales" action="jsonTerminales" />
	<s:url id="editURL" action="ABMTerminales" />



	<sjg:grid id="gridTerminales"
		width="400" 
		autoencode="false" 
		caption="Terminales" 
		dataType="json" 
		href="%{urlListarTerminales}" pager="true"
		navigator="true"
		navigatorSearch="false"
		navigatorDelete="true"
		gridModel="gridModel"
		rowList="15"
		rowNum="15"
		editurl="%{editURL}">

		<sjg:gridColumn name="id" search="false" sortable="false"  index="id" title="ID" editable="false" width="30" key="true" required="false"   />
		<sjg:gridColumn name="nombre" search="false" sortable="false" index="nombre" title="Nombre" editable="true"  />

	</sjg:grid>



</div>


<div id="ie_clearing">&#160;</div>
