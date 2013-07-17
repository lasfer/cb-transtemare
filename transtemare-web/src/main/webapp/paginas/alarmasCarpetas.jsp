<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>


<div align="center"> 

<s:url id="remoteurl" action="jsonAlarmas"/> 
    <sjg:grid 
    	autoencode="false"
    	id="gridmultitable" 
    	caption="Alarmas de carpetas" 
    	dataType="json" 
    	navigatorSearch="false"
    	href="%{remoteurl}" 
    	pager="true" 
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorEdit="false"
    	navigatorView="false"
    	navigatorDelete="false"
    	navigatorAdd="false"
    	gridModel="gridModel"
    	rowList="15"
    	onSelectRowTopics="rowselectAlarmas"
    	viewrecords="true"  width="430"  >
    	
    	<sjg:gridColumn width="80" name="numeroCarpeta" id="numeroCarpeta" index="numeroCarpeta" title="Carpeta"  sortable="true"  search="true"/>
    	<sjg:gridColumn width="80" name="fecAlta" index="fecAlta" title="Alta" sortable="false" formatter="date" formatoptions="{newformat : 'd-m-Y', srcformat : 'Y-m-d H:i:s'}" />
    	<sjg:gridColumn width="80" name="fecMod" index="fecMod" title="Modificaci&oacute;n" sortable="false" formatter="date" formatoptions="{newformat : 'd-m-Y', srcformat : 'Y-m-d H:i:s'}" />
    	<sjg:gridColumn width="80" name="fecVto" index="fecVto" title="Vencimiento" sortable="false"  formatter="date" formatoptions="{newformat : 'd-m-Y', srcformat : 'Y-m-d H:i:s'}" />
    	<sjg:gridColumn width="90" name="nroContenedor" index="nroContenedor" title="Contenedor" sortable="false" hidden="false"/>
      </sjg:grid>


<p>
<sj:div id="gridinfo" cssStyle='ui-widget-content ui-corner-all' effect="pulsate" effectDuration="1000" />


 </div>
  
 
<div id="ie_clearing"> &#160; </div>

