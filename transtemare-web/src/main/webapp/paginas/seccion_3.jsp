<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>


<s:div id="opcionesCarpeta">

			<h4>TRANSITO ADUANERO</h4>
			<ul>
			<li>Transito Aduanero :<s:radio label="Transito Aduanero" name="carpeta.transitoAduanero"  list="#{'true':'Si','false':'No'}" value="%{c.transitoAduanero}" /></li>
			</ul>

			<h4>Aduana y ciudad de partida</h4>
			<ul>
			  <li><sx:autocompleter keyValue="%{c.ciudadPaisPartida.idLocalidad}" keyName="ciudadDeOrigen" value="%{c.ciudadPaisPartida.descripcion}" label="Ciudad" labelposition="left"  name="ciudadDeOri" id="ciudadDeOri" list="localidades"  autoComplete="true" showDownArrow="true" required="true"  forceValidOption="true" /></li>
			</ul>
			<ul>
			  <li> <sx:autocompleter keyValue="%{c.aduanaOrigen.idAduana}" keyName="aduanaOrigen"	label="Aduana" labelposition="left"  name="adu" id="adu" list="listaAduanas"  autoComplete="true" showDownArrow="true" required="true" forceValidOption="true" value="%{c.aduanaOrigen.Descripcion}"/></li>
			</ul>

			

   			<h4>Datos de las empresas</h4>
   			<ul>
   			<li><sx:autocompleter keyValue="%{c.remitente.idEmpresa}" keyName="empresaRemitente" label="Remitente." labelposition="left" name="empRemi" id="empRemi" list="empresas" autoComplete="true" showDownArrow="true" required="true" forceValidOption="true" value="%{c.remitente.nombre}"/></li>
   			<li><sx:autocompleter keyValue="%{c.destinatario.idEmpresa}" keyName="empresaDestinataria" label="Destinatario" labelposition="left" name="empDes" id="empDes" list="empresas" autoComplete="true" showDownArrow="true" required="true"  forceValidOption="true" value="%{c.destinatario.nombre}"/></li>
			<li><sx:autocompleter keyValue="%{c.consignatario.idEmpresa}" keyName="empresaConsignataria" label="Consignatario" labelposition="left" name="empCon" id="empCon" list="empresas" autoComplete="true" showDownArrow="true" required="true" forceValidOption="true" value="%{c.consignatario.nombre}"/></li>
			<li><sx:autocompleter keyValue="%{c.notificar.idEmpresa}" keyName="notificarA" 	label="Notificar a" labelposition="left" name="noti" id="noti" list="empresas" autoComplete="true" showDownArrow="true" required="true" forceValidOption="true" value="%{c.notificar.nombre}"/></li>
   			</ul>
   			
   			<h4>Lugar de emision</h4>
	  		<ul>
	  		<li><sx:autocompleter keyValue="%{c.paisCiudadEmision.idLocalidad}" keyName="ciudadDeEmision" label="Ciudad" name="ciudadDeEmi" id="ciudadDeEmi" list="localidades" autoComplete="true" showDownArrow="true" required="true"  forceValidOption="true" value="%{c.paisCiudadEmision.descripcion}"/></li>
			</ul>
			
			<h4>Lugar en que el portador se hace cargo de las mercader&iacute;as</h4>
   			<ul>
	  		<LI><sx:autocompleter keyValue="%{c.paisCiudadPortadorCargo.idLocalidad}" keyName="ciudadPortador" label="Ciudad" name="ciudadPort" id="ciudadPort" list="localidades" autoComplete="true" showDownArrow="true" required="true" forceValidOption="true" value="%{c.paisCiudadPortadorCargo.descripcion}"/></LI>
	  		</ul>
			  						
			<h4>Lugar de entrega</h4>
	  		<ul>
	  		<li><sx:autocompleter  keyValue="%{c.paisCiudadPaisEntrega.idLocalidad}" keyName="ciudadDeEntrega" label="Ciudad" name="ciudadDeEnt" id="ciudadDeEnt" list="localidades" autoComplete="true" showDownArrow="true" required="true" forceValidOption="true" value="%{c.paisCiudadPaisEntrega.descripcion}" /></li>
			</ul>

			<table align="right">	      	
				<s:submit name="enviar"  value="Guardar Carpeta"></s:submit>	
			</table>	 		
			
			<table align="right">	      	
					<s:url var="url" action="pasarHistorico.action">
	            		<s:param name="idCarpeta" value="%{c.idCarpeta}"/>
	        		</s:url>
	        		<s:a href="%{url}">Cerrar Carpeta</s:a>				
		
			</table>

</s:div>

