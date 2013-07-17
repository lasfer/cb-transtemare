<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<s:url id="micdtaResumen" action="jsonMics"  ><s:param value="%{carpeta.idCarpetaPadre}" name="id" /> </s:url>
      
<style>
      .column { 
	width: 50%;
	float: left;
	padding-bottom: 100px;
	}
	
	.column div { 
	margin:5px;
	}
	
	.column div div .ui-icon { 
	float: right;
	}
      
	</style>      
      
      
      <sj:div id="column1" cssClass="column" sortable="true" sortableConnectWith=".column" sortablePlaceholder="ui-state-highlight" sortableForcePlaceholderSize="true" sortableHandle="div.ui-widget-header" sortableCursor="crosshair" sortableOnUpdateTopics="onupdate">

    <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
        <div class="ui-widget-header ui-corner-all"><span class="title">Carpeta</span><span class="ui-icon ui-icon-plusthick"></span></div>
        <div class="portlet-content">
        <table>
	        <tr><td>Fecha Alta:<s:property  default="" value ="%{carpeta.fechaAlta}"/></td></tr>
		    <tr><td>Fecha Modifiacion:<s:property value ="%{carpeta.fechaModificacion}"/></td></tr>
		    <tr><td>Numero CRT : <s:property  value ="%{carpeta.trans.prefijo}"/><s:property value="%{carpeta.nroDocumento}" /> </td></tr>
	    </table>  		
		</div>
    </div>
    
    <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
        <div class="ui-widget-header ui-corner-all"><span class="title">Contenedor</span><span class="ui-icon ui-icon-plusthick"></span></div>
        <div class="portlet-content">
			 <table>
			<tr><td>Numero Contenedor : <s:property  value ="%{carpeta.nroContenedor}"/></td></tr>
	        <tr><td>Tipo contenedor: <s:property  value ="%{carpeta.tipoContenedor}"/></td></tr>
		    <tr><td>Peso Bruto:<s:property value="%{carpeta.pesoBruto}"/></td></tr>
		   
	    </table> 
		</div>
    </div>
    
     <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
        <div class="ui-widget-header ui-corner-all"><span class="title">Tranportadora</span><span class="ui-icon ui-icon-plusthick"></span></div>
        <div class="portlet-content">
       		<table>
				<tr><td>Transportadora : <s:property  value ="%{carpeta.trans.nombreTransportadora}"/></td></tr>
			</table>
        </div>
    </div>
    
     <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
        <div class="ui-widget-header ui-corner-all"><span class="title">MICDTA Relacionados</span><span class="ui-icon ui-icon-plusthick"></span></div>
        <div class="portlet-content">
        <sj:div href="%{micdtaResumen}"  />
        </div>
    </div>

</sj:div>



<sj:div id="column2" cssClass="column" sortable="true" sortableConnectWith=".column" sortablePlaceholder="ui-state-highlight" sortableForcePlaceholderSize="true" sortableHandle="div.ui-widget-header" sortableCursor="crosshair" sortableOnUpdateTopics="onupdate">

    <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
        <div class="ui-widget-header ui-corner-all"><span class="title">Camion Original</span><span class="ui-icon ui-icon-plusthick"></span></div>
        <div class="portlet-content">
	        <table>
	    		<tr><td>Transportadora: <s:property  value="%{carpeta.transportadoraCamion.nombreTransportadora}" /></td></tr>
	    		<tr><td>Chofer:<s:property  value="%{carpeta.choferOriginal.nombre}"/> <s:property value="%{carpeta.choferOriginal.apellido}" /> <s:property value="%{carpeta.choferOriginal.documento}" /> </td></tr>
	    		<tr><td>Camion:<s:property  value="%{carpeta.camionOriginal.matricula}" /> <s:property value="%{carpeta.camionOriginal.capacidad}" /> <s:property value="%{carpeta.camionOriginal.marca}" /> <s:property value="%{carpeta.camionOriginal.anio}" /> </td></tr>
	    		<tr><td>Remolque:<s:property  value="%{carpeta.remolqueOriginal}" /></td></tr>
	    	</table>
        </div>
    </div>
    
     <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
        <div class="ui-widget-header ui-corner-all"><span class="title">Camion Sustituto</span><span class="ui-icon ui-icon-plusthick"></span></div>
        <div class="portlet-content">
        	<table>
		    	<tr><td>Transportadora:<s:property  value="%{carpeta.transportadoraCamionSustituto.nombreTransportadora}" /></td></tr>
    			<tr><td>Chofer:<s:property  value="%{carpeta.choferSubstituto.nombre}"/><s:property value="%{carpeta.choferSubstituto.apellido}" /> <s:property value="%{carpeta.choferSubstituto.documento}" /> </td></tr>
    			<tr><td>Camion:<s:property  value="%{carpeta.camionSubstituto.matricula}" /> <s:property value="%{carpeta.camionSubstituto.capacidad}" /> <s:property value="%{carpeta.camionSubstituto.marca}" /> <s:property value="%{carpeta.camionSubstituto.anio}" /> </td></tr>
    			<tr><td>Remolque:<s:property  value="%{carpeta.remolqueSubstituto}" /></td></tr>
    		</table>
        </div>
    </div>
     <div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
        <div class="ui-widget-header ui-corner-all"><span class="title">Buque</span><span class="ui-icon ui-icon-plusthick"></span></div>
        <div class="portlet-content">
       		<table>
		    	<tr><td>Nombre Buque: <s:property value="%{carpeta.nombreBuque}" /></td></tr>
		    	<tr><td>Fecha llegada: <s:property value="%{carpeta.fechaLlegadaBuque}" /></td></tr>
		    	<tr>
		    		<td>Liberacion:
		    			<s:if test="%{carpeta.liberacion}">
							SI
						</s:if>
						<s:else>
							NO
						</s:else>
		    			<p></p>
		    			<s:property value="%{carpeta.liberacionDescripcion}" /></td></tr>
	    	</table>
        </div>
    </div>
    
    
    

</sj:div>


      	
      
  
      
     
   

