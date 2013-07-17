<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%--<%@ taglib prefix="jo" uri="/struts-json-tags" %>--%>

<s:url id="urlCarpetasHistorico" action="jsonCarpetasHistorico"  />

<div id="col1" style="width: 50%" >
  <div id="col1_content" class="clearfix">
 
   <sjg:grid autoencode="false" id="gridedittable"  caption="Historico de Carpetas" dataType="json" href="%{urlCarpetasHistorico}" pager="true" 
         navigator="true" gridModel="gridModel" rowList="15" rowNum="15"   navigatorDelete="false" navigatorAdd="false"
          navigatorEdit="false" onSelectRowTopics="rowselectCarpetasCaratulaHistorico,rowselectCarpetasMicDtaHistorico,rowselectCarpetasCrtHistorico" >
    	
	    	<sjg:gridColumn  name="idCarpeta" index="idCarpeta"  title="Carpeta"  width="60" key="true" required="false"  />
	    	<sjg:gridColumn name="numeroContenedor"  index="numeroContenedor" id="numeroContenedor" title="Contenedor"  width="90"  />
	    	<sjg:gridColumn name="fechaModificacion" align="center" index="fechaModificacion" id="fechaModificacion" title="Modificado"  width="80"   formatter="date" formatoptions="{newformat : 'd-m-Y', srcformat : 'Y-m-d H:i:s'}"  />
	    	<sjg:gridColumn name="fechaCreacion" align="center" index="fechaCreacion" id="fechaCreacion" title="Creacion" formatter="date"  width="80" formatoptions="{newformat : 'd-m-Y', srcformat : 'Y-m-d H:i:s'}" />
	    	<sjg:gridColumn name="fechaPasadoHistorico" align="center" index="fechaCreacion" id="fechaCreacion" title="Historico" formatter="date"  width="80" formatoptions="{newformat : 'd-m-Y', srcformat : 'Y-m-d H:i:s'}" />
   		 </sjg:grid>
    
  </div>
</div>

<div id="col3">
  <div id="col3_content" >
   <ul>
	  <li><sj:div id="gridCaratulaHistorico"/></li>
   	  <li><sj:div id="gridCrtHistorico"/></li>
   	  <li><sj:div id="gridMicDtaHistorico"/></li>
     
      
    </ul>
  </div>
  <!-- IE Column Clearing -->
  <div id="ie_clearing"> &#160; </div>
</div>



