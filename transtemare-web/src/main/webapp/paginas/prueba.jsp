 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <div id="tone">
      
      </div>


      <div id="tseven">
				<div  style="width: 95%;"  >
				
				<div class="result ui-widget-content ui-corner-all" >
				<table cellpadding="1">
				    	<tr>
				    		<td>Fecha de alta:</td>
				    		<td><sj:textfield disabled="true"  value="%{c.fechaAlta}" name="carpeta.fechaAlta"   id="carpeta.fechaAlta" cssStyle="width:65px"  /> &nbsp;&nbsp;&nbsp; </td>
				    		<td>Fecha de Modificacion:</td>
				    		<td><sj:textfield disabled="true"  value="%{c.fechaModificacion}" name="carpeta.fechaModificacion"  cssStyle="width:65px" />&nbsp;&nbsp;&nbsp;</td>
				    		<td>Numero de contenedor:</td>
				    		<td><sj:textfield name="carpeta.numeroContenedorParte1" id="carpeta.numeroContenedorParte1"  value="%{carpeta1}" maxlength="4" size="4" /> - <sj:textfield  name="carpeta.numeroContenedorParte2" id="carpeta.numeroContenedorParte2"   value="%{carpeta2}" size="6" maxlength="6" /> - <sj:textfield name="carpeta.numeroContenedorParte3" id="carpeta.numeroContenedorParte3"   value="%{carpeta3}" maxlength="1" size="1" />
				    		<b>(<s:property value="%{c.idCarpeta}"/>)</b><s:hidden  name="idCarpeta" value="%{c.idCarpeta}" />
				    		</td>
				    	</tr>
				 </table>
				</div>
				 <p></p>   	
				<div class="result ui-widget-content ui-corner-all"  > 
				<table cellpadding="1">
				    	<tr>
					    	<td>Transportadora:</td>
					    	<td><sj:textfield disabled="true" name="transportadora" id="transportadora"  value="%{c.trans}" size="30" />&nbsp;&nbsp;&nbsp;</td>
					    	<td style="color: red">Cliente:</td>
					    	<td><sj:autocompleter id="cliente"  value="%{c.cliente.toString()}" name="cliente" href="%{jsonClientes}" delay="50" loadMinimumCount="2" cssStyle="width: 350px "  /></td>
				    	</tr>
				    	<tr>
					    	<td>Carpeta en Paraguay:</td>
					    	<td><sj:textfield  id="carpeta.referenciaDestino" name="carpeta.referenciaDestino"  value="%{c.referenciaDestino}" size="30" /></td>
					    	<td></td>
					    	<td></td>
					    </tr>
					    <tr>
					    	<td>Nro CRT:</td>
					    	<td><sj:textfield  label="CRT" name="nroCRT" value="%{c.trans.prefijo}%{c.nroDocumento}" readonly="true" size="30" disabled="true"/></td>
					    </tr>
					    <tr>
					    	<td>Comentarios:</td>
					    	<td>
					    		<sj:textarea label="Comentarios" name="carpeta.comentarios" value="%{c.comentarios}"  cols="28" rows="5"  />
					    	</td>
					    
					    </tr>
					   
				</table>
				
				
				
</div>
 				








</div>





      		
    		
      
      </div>
      
      <div id="ttwo"  >
      
      <sj:accordion id="accordion" autoHeight="true"   >
		
		<div>
    	<sj:accordionItem title="Camion Original"   >
    	<table>
    	<tr>
    		<td style="color: red">Transportadora:</td>
    		<td><sj:autocompleter id="transportadoraCamionOriginal"  value="%{c.transportadoraCamion.toString()}"  name="transportadoraCamionOriginal" href="%{jsonTransportadora}" delay="50" loadMinimumCount="2" cssStyle="width: 350px "  /></td>
    	</tr>
    	<tr>	
    		<td style="color: red">Chofer:</td>
    		<td><sj:autocompleter id="choferOriginal"  value="%{c.choferOriginal.toString()}" name="choferOriginal" href="%{jsonChofer}" delay="50" loadMinimumCount="2" cssStyle="width: 350px "  /></td>
    	</tr>
    	<tr>
    		<td style="color: red">Camion:</td>
    		<td><sj:autocompleter id="camionOriginal"  value="%{c.camionOriginal.toString()}" name="camionOriginal" href="%{jsonCamion}" delay="50" loadMinimumCount="2" cssStyle="width: 350px "  /></td>
    	</tr>
    	<tr>
    		<td>Remolque:</td>
    		<td><sj:textfield id="remolqueOriginal"  value="%{c.remolqueOriginal}" name="remolqueOriginal"  cssStyle="width: 350px "  /></td>
    	</tr>
    	</table>
    	</sj:accordionItem>
    	<sj:accordionItem title="Camion Sustituto"  >
    	<table>
    	<tr>
    		<td style="color: red">Transportadora:</td>
    		<td><sj:autocompleter id="transportadoraCamionSustituto" name="transportadoraCamionSustituto" value="%{c.transportadoraCamionSustituto.toString()}"  href="%{jsonTransportadora}" delay="50" loadMinimumCount="2" cssStyle="width: 350px "  /></td>
    	</tr>
    	<tr>	
    		<td style="color: red">Chofer:</td>
    		<td><sj:autocompleter id="choferSubstituto" name="choferSubstituto"  value="%{c.choferSubstituto.toString()}"  href="%{jsonChofer}" delay="50" loadMinimumCount="2" cssStyle="width: 350px "  /></td>
    	</tr>
    	<tr>
    		<td style="color: red">Camion:</td>
    		<td><sj:autocompleter id="camionSubstituto" name="camionSubstituto" value="%{c.camionSubstituto.toString()}"  href="%{jsonCamion}" delay="50" loadMinimumCount="2" cssStyle="width: 350px "  /></td>
    	</tr>
    	<tr>
    		<td>Remolque:</td>
    		<td><sj:textfield id="remolqueSubstituto" name="remolqueSubstituto" value="%{c.remolqueSubstituto}"   cssStyle="width: 350px "  /></td>
    	</tr>
    	</table>
    	</sj:accordionItem>
    	<sj:accordionItem title="Buque"  >
	    	<table>
		    	<tr>
		    		<td>Nombre Buque:</td>
		    		<td><sj:textfield value="%{c.nombreBuque}" name="carpeta.nombreBuque"  cssStyle="width: 350px "  /></td>
		    	</tr>
		    	<tr>
		    		<td>Fecha llegada:</td>
		    		<td>
		    		<sj:datepicker  name="carpeta.fechaLlegadaBuque"  id="carpeta.fechaLlegadaBuque" displayFormat="dd/mm/yy" showAnim="slideDown"  value="%{c.fechaLlegadaBuque}" />
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>Liberacion:</td>
		    		<td>
		    			<s:checkbox name="carpeta.liberacion"  value="%{c.liberacion}" />
   						<sj:textarea name="carpeta.liberacionDescripcion" value="%{c.liberacionDescripcion}"  cols="25" rows="3"  />
   					</td>
		    	</tr>
	    	</table>
    	
    	
    	
    	
    	
    	
    	</sj:accordionItem>
    	
    	<sj:accordionItem title="Contenedor"  >
				<table>
					<tr>
						<td>Tipo Contenedor :</td>
						<td><sj:select name="carpeta.tipoContenedor" href="%{tipoContenedor}"  value="%{c.tipoContenedor}" list="lista" /></td>
					</tr>
					<tr>
						<td>Quilaje Carga(Kg) :</td>
						<td><sj:textfield name="carpeta.pesoBruto" id="carpeta.pesoBruto"   value="%{c.pesoBruto}" >  </sj:textfield> </td>
					</tr>
				</table>
			</sj:accordionItem>
			
			<sj:accordionItem title="Agencia Maritima/Despachante/Dia solicitado por cliente"  >
				<table>
					<tr>
						<td style="color: red">Agencia Maritima</td>
						<td><sj:autocompleter name="agenciaMaritima"  id="agenciaMaritima" href="%{agenciaMaritima}" value="%{c.agenciaMaritima.toString()}" delay="50" loadMinimumCount="2" cssStyle="width: 350px "  /></td>
					</tr>
					<tr>
						<td>Dia Solicitado por cliente</td>
						<td><sj:datepicker  name="carpeta.fechaSalidaSolicitudCliente"  id="carpeta.fechaSalidaSolicitudCliente" displayFormat="dd/mm/yy" showAnim="slideDown"  value="%{c.fechaSalidaSolicitudCliente}" /></td>
					</tr>
					<tr>
						<td style="color: red">Despachante</td>
						<td><sj:autocompleter name="despachante" id="despachante" href="%{despachante}" value="%{c.despachante.toString()}" delay="50" loadMinimumCount="2" cssStyle="width: 350px " /></td>
					</tr>
				</table>
			</sj:accordionItem>
			
			
			
			<sj:accordionItem title="Ciudad de destino final"  >
				<table>
					<tr>
						<td style="color: red">Ciudad</td>
						<td><sj:autocompleter name="ciudadDeDestino" id="ciudadDeDestino" href="%{ciudades}" value="%{c.ciudadPaisDestino.toString()}" delay="50" loadMinimumCount="2" cssStyle="width: 350px "  /></td>
					</tr>
					<tr>
						<td style="color: red">Aduana</td>
						<td><sj:autocompleter name="aduanaDestino" id="aduanaDestino" href="%{aduanas}" value="%{c.aduanaDestino.toString()}" delay="50" loadMinimumCount="2" cssStyle="width: 350px " /></td>
					</tr>
				</table>
			</sj:accordionItem>
    	</div>
</sj:accordion>
      </div>
      
      
      <div id="tthree">
      <table>
      		
      		 <tr>
	    	<td>Pasado a Paraguay :</td>
	    	<td><s:checkbox name="carpeta.pasadoAParaguay"  value="%{c.pasadoAParaguay}" /></td>
	    </tr>
	    <tr>
	    
	    	<td>Fecha de Salida:</td>
	    	<td><sj:datepicker   name="carpeta.fechaSalida" id="carpeta.fechaSalida"   displayFormat="dd/mm/yy" showAnim="slideDown"  value="%{c.fechaSalida}" /></td>
	    </tr>
	    <tr>
	    	<td>Fecha de Vencimiento</td>
	    	<td><sj:datepicker  name="carpeta.fechaVencimiento"  id="carpeta.fechaVencimiento" displayFormat="dd/mm/yy" showAnim="slideDown"   value="%{c.fechaVencimiento}" /></td>
	    </tr>
	    </table>
	    <table cellspacing="4" >
	    <tr  >
	    	<td>Factura :</td>
	    	<td ><sj:radio   name="carpeta.facturaOriginal"  list="#{'true':'Original','false':'Copia'}" value="%{c.facturaOriginal}" /></td>
	    	<td><sj:datepicker  name="carpeta.fechaRecibidoFactura"  id="carpeta.fechaRecibidoFactura" displayFormat="dd/mm/yy" showAnim="slideDown"  value="%{c.fechaRecibidoFactura}" /></td>
	    </tr>
	    <tr>
	    	<td>Packing :</td>
	    	<td  ><sj:radio  name="carpeta.packingOriginal"  list="#{'true':'Original','false':'Copia'}" value="%{c.packingOriginal}" /></td>
	    	<td><sj:datepicker  name="carpeta.fechaRecibidoPacking" id="carpeta.fechaRecibidoPacking"   displayFormat="dd/mm/yy" showAnim="slideDown"  value="%{c.fechaRecibidoPacking}" /></td>
	    </tr>
	    <tr>
	    	<td>BL :</td>
	    	<td ><sj:radio   name="carpeta.blOriginal"  list="#{'true':'Original','false':'Copia'}" value="%{c.blOriginal}" /></td>
	    	<td><sj:datepicker  name="carpeta.fechaRecibidoBL"  id="carpeta.fechaRecibidoBL" displayFormat="dd/mm/yy" showAnim="slideDown"  value="%{c.fechaRecibidoBL}" /></td>
	    </tr>
	    </table>
		

	    <table>
	    <tr>
	    	<td style="color: red">Rutas</td>
	    	<td><sj:autocompleter  id="ruta"  value="%{c.ruta.toString()}" name="ruta"  href="%{rutas}" delay="50" loadMinimumCount="2" cssStyle="width: 350px "  /></td>
	    </tr>
	     <tr>
	    	<td>Valido Hasta :</td>
	    	<td><sj:textfield name="carpeta.validoHasta"   value="%{c.validoHasta}" /></td>
	    </tr>
	     <tr>
	    	<td>Rutas Corto :</td>
	    	<td><sj:textfield name="carpeta.rutasCorto" label="Rutas Corto"   value="%{c.rutasCorto}" /></td>
	    </tr>
      </table>
      
      </div>
      
      
   			
			
			
			
			
			
			
      <div id="tfour">
      	<table>
      		<tr>
	      		<td>Numero de precintos:</td>
	      		<td><sj:textfield name="carpeta.numeroPrecinto"    value="%{c.numeroPrecinto}"  /></td>
      		</tr>
      		<tr>
	      		<td style="color: red">Pais de Origen:</td>
	      		<td><sj:autocompleter name="paisOrigenMercaderias" id="paisOrigenMercaderias" href="%{paises}"  value="%{c.origenMercaderia.toString()}"/></td>
      		</tr>
      		<tr>
	      		<td>Moneda:</td>
	      		<td><sj:select name="carpeta.moneda"	list="lista" href="%{monedas}"	 value="%{c.moneda}" /></td>
      		</tr>
      		<tr>
	      		<td>Valor FOT:</td>
	      		<td><sj:textfield name="carpeta.valorFOT"    value="%{c.valorFOT}" /></td>
      		</tr>
      		<tr>
	      		<td>Flete en US$ : </td>
	      		<td><sj:textfield name="carpeta.costoFlete"  value="%{c.costoFlete}" /></td>
      		</tr>
      		<tr>
	      		<td>Seguro en US$ :  </td>
	      		<td><sj:textfield name="carpeta.seguro"    value="%{c.seguro}" /></td>
      		</tr>
      		<tr>
	      		<td>Volumen en m.cu :</td>
	      		<td><sj:textfield name="carpeta.volumenMC"  value="%{c.volumenMC}" /></td>
      		</tr>
      		<tr>
	      		<td>Cantidad de Bultos :</td>
	      		<td><sj:textfield  name="carpeta.cantidadBultos"   value="%{c.cantidadBultos}"/></td>
      		</tr>
      		<tr>
	      		<td style="color: red">Tipo de Bultos :</td>
	      		<td><sj:autocompleter name="tipoBulto"   id="tipoBulto"   href="%{bultos}"  value="%{c.tipoBulto.toString()}" /></td>
      		</tr>
      		<tr>
	      		<td>Gastos a pagar :</td>
	      		<td><sj:textfield name="carpeta.gastosPagar"  value="%{c.gastosPagar}" /></td>
      		</tr>
      		<tr>
	      		<td>Monto Remitente : </td>
	      		<td><sj:textfield name="carpeta.montoRemitente"  value="%{c.montoRemitente}" /></td>
      		</tr>
      		<tr>
	      		<td>Moneda Remitente :</td>
	      		<td><sj:select name="carpeta.monedaRemitente" 	list="lista" href="%{monedas}"  value="%{c.monedaRemitente}"/></td>
      		</tr>
      		<tr>
	      		<td>Monto Destinatario : </td>
	      		<td><sj:textfield name="carpeta.montoDestinatario"  value="%{c.montoDestinatario}" /></td>
      		</tr>
      		<tr>
	      		<td>Moneda Destinatario :</td>
	      		<td><sj:select name="carpeta.monedaDestinatario" id="monedaDestinatario" list="lista" href="%{monedas}"	 value="%{c.monedaDestinatario}" /></td>
      		</tr>
      		<tr>
	      		<td>Monto del flete externo :</td>
	      		<td><sj:textfield name="carpeta.montoFleteExterno"    value="%{c.montoFleteExterno}" /></td>
      		</tr>
      		<tr>
	      		<td>Monto de rembolso contra entrega :</td>
	      		<td><sj:textfield name="carpeta.montoRembolso"    value="%{c.montoRembolso}" /></td>
      		</tr>
      		<tr>
      			<td>Declaraci&oacute;n del valor de las mercancias :</td>
      			<td><sj:textfield name="carpeta.valorMercancias"  value="%{c.valorMercancias}" /></td>
      		</tr>
      		<tr>
      			<td>Otros :</td>
      			<td><sj:textfield name="carpeta.otros"    value="%{c.otros}" /></td>
      		</tr>
      		
      		
      	
      	</table>
			
			


	  </div>
	  
	  
	  <div id="tfive">
	  	<table>
	  		<tr>
	  			<td>Marca y numero de los bultos :</td>
	  			<td><sj:textarea name="carpeta.marcaYnumerobultos"  cols="70" rows="7"  value="%{c.marcaYnumerobultos}" /></td>
	  		</tr>
	  		<tr>
	  			<td>Tama&ntilde;o de Letra :</td>
	  			<td><sj:select name="carpeta.tamanioLetraMarcaYNumero"  list="lista" href="%{tamanosLetras}"   value="%{c.tamanioLetraMarcaYNumero}"/></td>
	  		</tr>	
	  		<tr>	
	  			<td>Documentos Anexos :</td>
	  			<td><sj:textarea  name="carpeta.documentosAnexos"  cols="40" rows="7"    value="%{c.documentosAnexos}" /></td>
	  		</tr>
	  		<tr>
	  			<td>Instrucc. sobre formalidades de aduana :</td>
	  			<td><sj:textarea  name="carpeta.formalidadesAduana"  cols="40" rows="7"   value="%{c.formalidadesAduana}" /></td>
	  		</tr>
	  	
	  	</table>
	  </div>
	  
      <div id="tsix" >
      
      <table>
      	<tr>
      		<td>Transito Aduanero :</td>
      		<td width="10" ><sj:radio  name="carpeta.transitoAduanero"  list="#{'true':'Si','false':'No'}" value="%{c.transitoAduanero}" /></td>
      	</tr>
      	<tr>
      		<td>Aduana y ciudad de partida</td>
      	</tr>
      	<tr>
      		<td style="color: red" >Ciudad de origen</td>
      		<td><sj:autocompleter value="%{c.ciudadPaisPartida.toString()}" name="ciudadDeOrigen" id="ciudadDeOrigen"    href="%{ciudades}" /></td>
      	
      	</tr>
      	<tr>
      		<td style="color: red">Aduana de origen</td>
			<td><sj:autocompleter name="aduanaOrigen" id="aduanaOrigen" href="%{aduanas}" value="%{c.aduanaOrigen.toString()}" delay="50" loadMinimumCount="2" cssStyle="width: 350px " /></td>
      	</tr>
		<tr>
			<td>Datos de la empresas</td>
			<td></td>
		</tr>
      	<tr>
      		<td style="color: red">Remitente</td>
      		<td><sj:autocompleter name="empresaRemitente" id="empresaRemitente" href="%{empresas}" value="%{c.remitente.toString()}" cssStyle="width: 350px " /></td>
      	</tr>
      	<tr>
      		<td style="color: red">Destinatario</td>
      		<td><sj:autocompleter name="empresaDestinataria" id="empresaDestinataria"  href="%{empresas}" value="%{c.destinatario.toString()}"  cssStyle="width: 350px " /></td>
      	<tr>
      		<td style="color: red">Consignatario:</td>
      		<td><sj:autocompleter name="empresaConsignataria" id="empresaConsignataria" href="%{empresas}"  value="%{c.consignatario.toString()}" cssStyle="width: 350px "  /></td>
      	</tr>
      	<tr>
      		<td style="color: red">Notificar a:</td>
      		<td><sj:autocompleter name="notificarA" id="notificarA" href="%{empresas}" value="%{c.notificar.toString()}" cssStyle="width: 350px "  /></td>
      	</tr>
      	<tr>
      		<td style="color: red">Lugar de emision</td>
      		<td><sj:autocompleter name="ciudadDeEmision" id="ciudadDeEmision"  href="%{ciudades}"  value="%{c.paisCiudadEmision.toString()}" cssStyle="width: 350px "  /></td>
      	</tr>
      	<tr>
      		<td width="200" style="color: red" >Lugar en que el portador se hace cargo de las mercader&iacute;as</td>
      		<td><sj:autocompleter name="ciudadPortador" id="ciudadPortador"  href="%{ciudades}" value="%{c.paisCiudadPortadorCargo.toString()}" cssStyle="width: 350px "  /></td>
      	</tr>
      	<tr>
      		<td style="color: red">Lugar de entrega</td>
      		<td><sj:autocompleter  name="ciudadEntrega" id="ciudadEntrega" href="%{ciudades}" value="%{c.paisCiudadPaisEntrega.toString()}" cssStyle="width: 350px "  /></td>
      	</tr>
      	<tr>
      		<td></td>
      		<td></td>
      	</tr>
      
      </table>
      
      	
      
      </div>
      
      
      
<sj:textfield disabled="true"  value="%{c.fechaAlta}" name="carpeta.fechaAlta"   id="carpeta.fechaAlta" cssStyle="width:65px"  />