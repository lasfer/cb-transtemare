<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<s:div   id="opcionesCarpeta">
   			<h3>DATOS DE LA MERCADERIA/CONTENEDOR</h3>
			<ul style="align: right;" >
   			<li><s:textfield name="numPrecintos" label="Numero de precintos"  value="%{c.numeroPrecinto}" theme="xhtml" /></li>
   			<li><sx:autocompleter keyValue="%{c.origenMercaderia.idPais}" keyName="paisOrigenMercaderias" label="Pais de Origen" labelposition="left" name="paisOriMerc" id="paisOriMerc" list="paises" autoComplete="true" showDownArrow="true" required="true" forceValidOption="true" value="%{c.origenMercaderia.descripcion}"/></li>
			<li>Moneda :  <s:select name="moneda"	list="#{'1':'US$','2':'US','3':'EUR'}" 	label="Moneda" value="%{c.moneda}" /></li>
			<li>Valor FOT:<s:textfield name="valorFOT" label="Valor FOT"  value="%{c.valorFOT}" /></li>
			<li>Flete en US$ : <s:textfield name="costoFlete" label="Flete en US$" required="true" value="%{c.costoFlete}" /></li>
			<li>Seguro en US$ :  <s:textfield name="seguro" label="Seguro en US$"  value="%{c.seguro}" /></li>
			<li>Volumen en m.cu :<s:textfield name="volumen" label="Volumen en m.cu" value="%{c.volumenMC}" /></li>
			<li>Cantidad de Bultos :<s:select name="cantBultos" label="Cantidad de Bultos" list="cantidadDeBultos"  value="%{c.cantidadBultos}"/></li>
			<li>Tipo de Bultos :<s:select name="tipoBultos" label="Tipo de Bultos" list="tipoDeBultos" value="%{c.tipoBulto.codBulto}" /></li>
<!--			<li>Peso Bruto :<s:textfield name="pesoBrut" label="Peso Bruto"   value="%{c.pesoBruto}" /></li>-->
			<li>Gastos a pagar :<s:textfield name="gasApagar" label="Gastos a pagar"  value="%{c.gastosPagar}" /></li>
			<li>Monto Remitente : <s:textfield name="montoRemitente" label="Monto Remitente"  value="%{c.montoRemitente}" /></li>
			<li>Moneda Remitente :<s:select name="monedaRemitente"	list="#{'1':'US$','2':'US'}" 	label="Moneda Remitente"  value="%{c.monedaRemitente}"/></li>
			<li>Monto Destinatario : <s:textfield name="montoDestinatario" label="Monto Destinatario" value="%{c.montoDestinatario}" /></li>
			<li>Moneda Destinatario :<s:select name="monedaDestinatario"	list="#{'1':'US$','2':'US','3':'EUR'}" 	label="Moneda Destinatario" value="%{c.monedaDestinatario}" /></li>
			<li>Monto del flete externo :<s:textfield name="montoFleteExterno" label="Monto del flete externo"  value="%{c.montoFleteExterno}" /></li>
			<li>Monto de rembolso contra entrega :<s:textfield name="montoRetorno" label="Monto de rembolso contra entrega"  value="%{c.montoRembolso}" /></li>
			<li>Declaraci&oacute;n del valor de las mercancias :<s:textfield name="decValor" label="Declaraci&oacute;n del valor de las mercancias" value="%{c.valorMercancias}" /></li>
			<li>Otros :<s:textfield name="otros" label="Otros"   value="%{c.otros}" /></li>
			</ul>
			
			
			<h4>Marca y numero de los bultos :</h4>
			<ul type="square" >
			<li><s:textarea name="marNumBultos" cols="70" rows="10" label="Marca y numero de los bultos"  onkeyup="return ismaxlength(this)" value="%{c.marcaYnumerobultos}" /></li>
			</ul>
			
			
			<ul>
			<li>Tama&ntilde;o de Letra :<s:select name="carpeta.tamanioLetraMarcaYNumero"	list="#{'6':'Muy Peque&ntilde;o','7':'Peque&ntilde;o','8':'Mediano','9':'Normal'}" 	label="Tama&ntilde;o de Letra"  value="%{c.tamanioLetraMarcaYNumero}"/></li>
			</ul>
			
			<h4>Documentos Anexos :</h4>
			<ul>
			<li><s:textarea  name="docAnex"  cols="40" rows="10"  label="Documentos Anexos"  onkeyup="return ismaxlength(this)" value="%{c.documentosAnexos}" /> </li>
			</ul>
			
			
			<h4>Instrucc. sobre formalidades de aduana :</h4>
			<ul>
			<li><s:textarea  name="instrAduana"  cols="40" rows="10"  label="Instrucc. sobre formalidades de aduana" onkeyup="return ismaxlength(this)" value="%{c.formalidadesAduana}" /></li>
			</ul>
			
			
				<s:submit name="enviar"  value="Guardar Carpeta"></s:submit>

				<s:url var="url" action="pasarHistorico.action">
            		<s:param name="idCarpeta" value="%{c.idCarpeta}"/>
        		</s:url>
        		<s:a href="%{url}">Cerrar Carpeta</s:a> 				
</s:div>