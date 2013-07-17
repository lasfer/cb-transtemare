<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>


<table border="1" width="100%"  cellspacing="0" cellpadding="0" id="opcionesCarpeta" bgcolor="#C7C4F3" >

<tr>

   			<td width="50%">
   			<table align="left" >
   			<sx:datetimepicker  label="Fecha de Creacion" name="fechaCreacionCarpeta" displayFormat="dd/MM/yyyy" disabled="true" value="%{c.fechaAlta}" />
   			</table>
			</td>
			
			
   			<td>
   			<table align="right" >
   			<sx:datetimepicker label="Fecha de modificacion" name="fechaModificacionCarpeta" displayFormat="dd/MM/yyyy"  disabled="true" value="%{c.fechaModificacion}" />
   			</table>
			</td>

</tr>

<tr>

   			<td>
   			
   			<table align="left" >   			
   			Nro Contenedor:<s:textfield label="Nro Contenedor" name="carpeta.nroContenedor" value="%{c.nroContenedor}"  maxlength="13" size="14"/>
   			</table>
   			<b>(<s:property value="%{c.idCarpeta}"/>)</b><s:hidden  name="idCarpeta" value="%{c.idCarpeta}" />
			</td>
			
			
   			<td>
	   			<table  align="right" >
   			Transportadora:<s:textfield label="Transportadora" name="transportadora" value="%{c.trans.nombreTransportadora}" readonly="true"  disabled="true"/>
   			  			
   			</table> 
			</td>

</tr>
<tr>


   			<td>
   			<table  align="left" >
   			Ref Destino(Carpeta en Paraguay): <s:textfield label="Ref Destino(Carpeta en Paraguay)" name="carpeta.referenciaDestino" value="%{c.referenciaDestino}"  />
   			</table>
			</td>
			
			
   			<td>
   			<table>
   			<sx:autocompleter keyValue="%{c.cliente.idEmpresa}" 
 		                   keyName="carpeta.cliente.idEmpresa" 
 		                   value="%{c.cliente.nombre}" 
 		                   label="Cliente" 
 		                   labelposition="left" 
 		                   name="carpeta.cliente.nombre" 
 		                   id="cliente" 
 		                   list="clientes" 
 		                   autoComplete="true" 
 		                   showDownArrow="true" 
 		                   required="true" 
 		                   forceValidOption="true" />  
 		          </table> 			
			</td>

</tr>
<tr>


   			<td>
   			<table  align="left" >
   			Nombre Buque :<s:textfield label="Nombre Buque" name="carpeta.nombreBuque" value="%{c.nombreBuque}"  />
   			</table>
			</td>
			
			
   			<td>
   			<table align="right" >
   			<sx:datetimepicker name="carpeta.fechaLlegadaBuque" label="Fecha Llegada Buque" displayFormat="dd/MM/yyyy" value="%{c.fechaLlegadaBuque}" />
   			</table>
			</td>

</tr>

<tr>


   			<td>
   			<table>
   			
   			Liberacion :<s:checkbox label="Liberacion" labelposition="left" name="carpeta.liberacion"  value="%{c.liberacion}" />
   			<s:textarea name="carpeta.liberacionDescripcion" value="%{c.liberacionDescripcion}"  cols="25" rows="3"  />
   			</table>
			</td>
			
			
   			<td>
				<table>
   				Tipo Contenedor :<s:select name="carpeta.tipoContenedor" label="Tipo Contenedor"   list="#{'0':'SUELTA','20':'20','40':'40'}" value="%{c.tipoContenedor}" />
   				Quilaje Carga(Kg) :<s:textfield name="pesoBrut" label="Quilaje Carga(Kg)"   value="%{c.pesoBruto}"/>
   			</table> 
   			
   			  
			</td>

</tr>

<tr>
			<td>
   			<table>  	
   			
   			Nro CRT :<s:textfield label="Nro CRT" name="nroCRT" value="%{c.trans.prefijo}%{c.nroDocumento}" readonly="true"  disabled="true"/>
			</table> 
			</td>
			
			
   			<td>
   			MIC/DTA Relacionados:<BR>  			
   			<s:iterator value="%{mics}" var="mic">
   				<s:property value="%{#mic}"/>
   			</s:iterator>
   	
   			
   		
			</td>
</tr>
<tr>
 <td>
   		<h4>Datos Del Cami&oacute;n</h4>
   		<table>
   		
   		<s:url id="jsonCamion" action="JSONCamion"  />
				 	 			<sx:autocompleter name="city"  href="%{jsonCamion}" size="24" loadOnTextChange="true" 
				 	 			preload="false" searchType="subString" label="Transportadora Cami&oacute;n"
				 	 			name="c.transportadoraCamion.nombreTransportadora" id="camOri" 
				 	 			forceValidOption="true" autoComplete="true" showDownArrow="true" cssClass="autoCompleter200" />
   		
   				
   				<sx:autocompleter
 					keyValue="%{c.choferOriginal.idResponsable}" 
 		 			keyName="choferOriginal" 
 				 label="Chofer" name="chofOri" id="chofOri" labelposition="left" list="listaChoferes"  autoComplete="true" showDownArrow="true" required="true" forceValidOption="true" value="%{c.choferOriginal}"></sx:autocompleter>
      			<sx:autocompleter keyValue="%{c.camionOriginal.idCamion}" 
 		 					keyName="camionOriginal"       			
      			label="Camion"  name="camOri" id="camOri" list="listaCamion"  autoComplete="true" showDownArrow="true" required="true" forceValidOption="true" value="%{c.camionOriginal}"></sx:autocompleter>
 			    
   
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Remolque original : <s:textfield  name="semiRemolqueOriginal" value="%{c.remolqueOriginal}"  />
 				
        		
		</table>
	</td>
	<td>
		<h4>Ciudad de destino final</h4>
   		<table>
   		
  		<sx:autocompleter keyValue="%{c.ciudadPaisDestino.idLocalidad}" 
  		                  keyName="ciudadDeDestino"  
  		                  value="%{c.ciudadPaisDestino.descripcion}" 
  		                  label="Ciudad" 
  		                  name="ciudadDeDes" 
  		                  id="ciudadDeDes" 
  		                  list="localidades" 

  		                  autoComplete="true" 
  		                  showDownArrow="true" 
  		                  required="true" 
  		                  forceValidOption="true"  />

 		 <sx:autocompleter  
 		 					keyValue="%{c.aduanaDestino.idAduana}" 
 		 					keyName="aduanaDestino" 
 		 label="Aduana" name="aduDes" id="aduDes" list="listaAduanas"  autoComplete="true" showDownArrow="true" required="true" forceValidOption="true" value="%{c.aduanaDestino.Descripcion}"></sx:autocompleter>
		</table>
	</td>	
</tr>
<tr>
			<td>
   			<table align="left">  	
   			
   			Comentarios :<s:textarea label="Comentarios" name="carpeta.comentarios" value="%{c.comentarios}"  cols="28" rows="5" />
			</table> 
			</td>
			<td>
			<table>
	   			<sx:autocompleter keyValue="%{c.agenciaMaritima.idEmpresa}" 
	 		                   keyName="carpeta.agenciaMaritima.idEmpresa" 
	 		                   value="%{c.agenciaMaritima.nombre}" 
	 		                   label="Agencia Mar&iacute;tima" 
	 		                   labelposition="left" 
	 		                   name="carpeta.agenciaMaritima.nombre" 
	 		                   id="agenciaMaritima" 
	 		                   list="agenciasMaritimas" 
	 		                   autoComplete="true" 
	 		                   showDownArrow="true" 
	 		                   required="true" 
	 		                   forceValidOption="true" />  
	 		                   
	 		    <sx:datetimepicker  label="Dia Solicitado Por Cliente" name="carpeta.fechaSalidaSolicitudCliente" displayFormat="dd/MM/yyyy"  value="%{c.fechaSalidaSolicitudCliente}" />
	 		    <sx:autocompleter keyValue="%{c.despachante.idEmpresa}" 
	 		                   keyName="carpeta.despachante.idEmpresa" 
	 		                   value="%{c.despachante.nombre}" 
	 		                   label="Despachante" 
	 		                   labelposition="left" 
	 		                   name="carpeta.despachante.nombre" 
	 		                   id="despachante" 
	 		                   list="despachantes" 
	 		                   autoComplete="true" 
	 		                    
	 		                   required="true" 
	 		                   forceValidOption="true" />  
 		    </table>
			</td>
			

</tr>

<tr>


   			<td width="50%">
   			<table align="left" >
	   			<sx:datetimepicker  label="Fecha de Salida" name="carpeta.fechaSalida" displayFormat="dd/MM/yyyy"  value="%{c.fechaSalida}" />
	   			<sx:datetimepicker label="Fecha de Vencimiento" name="carpeta.fechaVencimiento" displayFormat="dd/MM/yyyy"   value="%{c.fechaVencimiento}" />
	   			Pasado a Paraguay :<s:checkbox label="Pasado a Paraguay" labelposition="left" name="carpeta.pasadoAParaguay"  value="%{c.pasadoAParaguay}" />
   			</table>
   			
   			
   			
			</td>
			
			
   			<td>
   				<table>
   				<tr>
   				<td>
   				<table>
	   				Factura :<s:radio label="Factura" name="carpeta.facturaOriginal"  list="#{'true':'Original','false':'Copia'}" value="%{c.facturaOriginal}" />
	   			</table>
	   			</td>
	   			<td>	   		

   					<sx:datetimepicker  name="carpeta.fechaRecibidoFactura" displayFormat="dd/MM/yyyy"  value="%{c.fechaRecibidoFactura}" />

   				</td>
   				</tr>
   				<tr>
   				<td>
   				<table>
	   				Packing :<s:radio label="Packing" name="carpeta.packingOriginal"  list="#{'true':'Original','false':'Copia'}" value="%{c.packingOriginal}" />
	   			</table>
	   			</td>
	   			<td>
   					<sx:datetimepicker  name="carpeta.fechaRecibidoPacking" displayFormat="dd/MM/yyyy"  value="%{c.fechaRecibidoPacking}" />

   				</td>
   				</tr>
   				<tr>
   				<td>
   				<table>
	   				BL :<s:radio label="BL" name="carpeta.blOriginal"  list="#{'true':'Original','false':'Copia'}" value="%{c.blOriginal}" />
	   			</table>
	   			</td>
	   			<td>

   					<sx:datetimepicker  name="carpeta.fechaRecibidoBL" displayFormat="dd/MM/yyyy"  value="%{c.fechaRecibidoBL}" />

   				<td>
   				</tr>
   				</table>
			</td>

</tr>

<tr>


   			<td>
   			<h4>RUTAS</h4>
   			
   			<table>
   			<sx:autocompleter  
	  		keyValue="%{c.ruta.idRuta}" 
 		                   keyName="carpeta.ruta.idRuta" 
	  		label="Rutas" name="rutaAutocompl" id="rutaAutocompl" list="listaRutas" autoComplete="true" showDownArrow="true" forceValidOption="true" value="%{c.ruta.descripcionCorta}" ></sx:autocompleter>
 			
 			</table>
 			<table>
 			  Valido Hasta :<s:textfield name="carpeta.validoHasta" label="Valido Hasta"   value="%{c.validoHasta}" />
 			  <br>
				Rutas Corto :<s:textfield name="carpeta.rutasCorto" label="Rutas Corto"   value="%{c.rutasCorto}" />
   			</table>
			</td>
			
			
   			<td>
   			
			</td>

</tr>

<tr>
	<td colspan="2">
		<table align="right">	      	
				<s:submit name="enviar"  value="Guardar Carpeta"></s:submit>	
		</table>
		<table align="right">
				<s:url var="url" action="pasarHistorico.action">
            		<s:param name="idCarpeta" value="%{c.idCarpeta}"/>
        		</s:url>
        		 <s:a href="%{url}">Cerrar Carpeta</s:a> 			
		</table>	 		
	</td>
</tr>
</table>
