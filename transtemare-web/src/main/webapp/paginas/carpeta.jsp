<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<script>
	$('#ruta').blur(function() {
		var ruta = document.getElementById('carpeta.rutasLargo');
		ruta.value = $(this).val();
	});

	$(document).keydown(
			function(e) {
				var nodeName = e.target.nodeName.toLowerCase();
				if (e.which === 8) {
					if ((nodeName === 'input' && e.target.type === 'text')
							|| nodeName === 'textarea') {
						// do nothing
					} else {
						e.preventDefault();
					}
				}
			});
</script>
<h2>Edicion de carpeta</h2>


<s:url id="jsonCamion" action="jsonListaCamiones" />
<s:url id="jsonRemolques" action="jsonListaRemolques" />
<s:url id="jsonTransportadora" action="jsonListaTransportadoras" />
<s:url id="jsonChofer" action="jsonListaChoferes" />
<s:url id="jsonClientes" action="jsonListaClientes" />
<s:url id="tipoContenedor" action="jsonTipoContenedores" />
<s:url id="terminales" action="jsonListaTerminales" />
<s:url id="agenciaMaritima" action="jsonListaAgenciasMaritimas" />
<s:url id="despachante" action="jsonListaDespachantes" />
<s:url id="ciudades" action="jsonListaCiudades" />
<s:url id="aduanas" action="jsonListaAduanas" />
<s:url id="rutas" action="jsonListaRutas" />
<s:url id="paises" action="jsonListaPaises" />
<s:url id="bultos" action="jsonListaBultos" />
<s:url id="monedas" action="jsonListaMonedas" />
<s:url id="tamanosLetras" action="jsonListaTamaniosLetra" />
<s:url id="empresas" action="jsonListaEmpresas" />
<s:url id="mics" action="jsonMics">
	<s:param value="%{c.idCarpeta}" name="id" />
</s:url>
<s:url includeParams="none" id="urlResumen" action="resumenCarpeta">
	<s:param value="%{c.idCarpeta}" name="id" />
</s:url>
<s:url id="historico" action="pasarCarpetaHistorico">
	<s:param name="id" value="%{c.idCarpeta}" />
</s:url>
<s:url id="enviarCarpeta" action="CrearCarpeta" />
<s:url id="micdtaResumen" action="jsonMics">
	<s:param value="%{c.idCarpeta}" name="id" />
</s:url>

<div id="dialogos">
	<sj:submit href="%{enviarCarpeta}" targets="formResult" formIds="form"
		value="Guardar Carpeta" loadingText="GUARDANDO CARPETA....."
		button="true" buttonIcon="ui-icon-newwin" />
	<sj:dialog id="camionesdialog" autoOpen="false" width="950"
		height="600">
		<jsp:include page="/paginas/camiones.jsp" />
	</sj:dialog>
	<sj:a openDialog="camionesdialog" button="true">Camiones</sj:a>
	<sj:dialog id="choferesdialog" autoOpen="false" width="950"
		height="600">
		<jsp:include page="/paginas/choferes.jsp" />
	</sj:dialog>
	<sj:a openDialog="choferesdialog" button="true">Choferes</sj:a>
	<sj:dialog id="empresasdialog" autoOpen="false" width="950"
		height="600">
		<jsp:include page="/paginas/empresas.jsp" />
	</sj:dialog>
	<sj:a openDialog="empresasdialog" button="true">Empresas</sj:a>


	<s:if test="%{c.esCRT}">
		<a id="crtt"
			href="${pageContext.request.contextPath}/CRT.action?id=<s:property  value='%{c.idCarpeta}' />"
			class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
			<span class="ui-button-text">CRT</span>
		</a>
	</s:if>

	<a id="MICDTAA"
		href="${pageContext.request.contextPath}/MICDTA.action?id=<s:property  value='%{c.idCarpeta}' />"
		class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
		<span class="ui-button-text">MICDTA</span>
	</a>
	
	<a id="MICDTACamSust"
		href="${pageContext.request.contextPath}/MICDTA.action?id=<s:property  value='%{c.idCarpeta}' />&camionSust=true"
		class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
		<span class="ui-button-text">Camion Sust.</span>
	</a>

	<s:if test="%{c.esCRT}">

		<a id="CARATULAA"
			href="${pageContext.request.contextPath}/CARATULA.action?id=<s:property  value='%{c.idCarpeta}' />"
			class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
			<span class="ui-button-text">CARATULA</span>
		</a>
		<sj:a targets="formResult" button="true" href="%{historico}">A HISTORICO</sj:a>
	</s:if>
</div>

<div>

	<s:form id="form" theme="simple" cssClass="yform">


		<sj:div id="formResult" />






		<sj:tabbedpanel id="localtabs" animate="true">
			<sj:tab id="tab1" href="%{urlResumen}" label="Resumen" />
			<sj:tab id="tab2" target="ttwo" label="Seccion1" />
			<sj:tab id="tab3" target="tthree" label="Seccion2" />
			<sj:tab id="tab3" target="tfour" label="Garantia" />
			<!--      <sj:tab id="tab4" target="tfour" label="Seccion 3"/>-->
			<!--      <sj:tab id="tab5" target="tfive" label="Seccion 4"/>-->
			<!--      <sj:tab id="tab6" target="tsix" label="Seccion5"/>-->
			<!--      <sj:tab id="tab7" target="tseven" label="Seccion6"/>-->


			<div id="ttwo">


				<table cellpadding="1">
					<tr>
						<td title="Transportadora asignada a la carpeta">Transportadora:</td>
						<td><b><s:property default="" value="%{c.trans}" /> </b>
							&nbsp;&nbsp;&nbsp;</td>
						<td style="color: red" title="Cliente">Cliente:</td>
						<td><sj:autocompleter id="cliente"
								value="%{c.cliente.toString()}" name="cliente"
								href="%{jsonClientes}" delay="50" loadMinimumCount="2"
								cssStyle="width: 350px " /></td>
					</tr>
				</table>


				<table cellpadding="1">
					<tr>
						<td title="Fecha de alta">F. alta:</td>
						<td><b><s:property default="" value="%{c.fechaAlta}" /> </b>
							&nbsp;&nbsp;&nbsp;</td>
						<td title="Fecha de modificacion">F. Modificacion:</td>
						<td><b><s:property default=""
									value="%{c.fechaModificacion}" /> </b> &nbsp;&nbsp;&nbsp;</td>
						<td title="Numero de contenedor">N. de contenedor:</td>
						<td><sj:textfield name="carpeta.numeroContenedorParte1"
								id="carpeta.numeroContenedorParte1" value="%{carpeta1}"
								maxlength="4" size="4" /> - <sj:textfield
								name="carpeta.numeroContenedorParte2"
								id="carpeta.numeroContenedorParte2" value="%{carpeta2}" size="6"
								maxlength="6" /> - <sj:textfield
								name="carpeta.numeroContenedorParte3"
								id="carpeta.numeroContenedorParte3" value="%{carpeta3}"
								maxlength="1" size="1" /> <b>(<s:property
									value="%{c.idCarpeta}" />)
						</b>
						<s:hidden name="idCarpeta" value="%{c.idCarpeta}" /> <!--				    		<input type="button" onclick="validarNumeroContenedor()" value="Validar"  >-->
							&nbsp;&nbsp;</td>
						<td title="Transito aduanero">Tran. Aduanero:</td>
						<td><sj:radio name="carpeta.transitoAduanero" 
								list="#{'true':'Si','false':'No'}" value="%{c.transitoAduanero}"  />
						</td>
					</tr>

				</table>

				<table cellpadding="1">
					<tr>
						<td title="Terminal">Terminal:</td>
						<td>

<sj:select
        href="%{terminales}"
        name="carpeta.terminal.id"
        value="%{c.terminal.id}"
        list="lista"
        listKey="id"
        listValue="nombre"
        headerValue="Seleccione una opcion"/>
						

						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td style="color: red" title="Aduana y ciudad de partida">Aduana
							y ciudad de partida:</td>
						<td><sj:autocompleter
								value="%{c.ciudadPaisPartida.toString()}" name="ciudadDeOrigen"
								id="ciudadDeOrigen" href="%{ciudades}"  cssStyle="width: 350px " /></td>
						<td>Carpeta en Paraguay:</td>
						<td><sj:textfield id="carpeta.referenciaDestino"
								name="carpeta.referenciaDestino" value="%{c.referenciaDestino}"
								size="30" /></td>
					</tr>
					<tr>
						<td style="color: red" title="Lugar de Emision">Lugar de
							emision:</td>
						<td><sj:autocompleter name="ciudadDeEmision"
								id="ciudadDeEmision" href="%{ciudades}"
								value="%{c.paisCiudadEmision.toString()}"
								cssStyle="width: 350px " /></td>
						<td>Nro CRT:</td>
						<td><sj:textfield label="CRT" name="nroCRT"
								value="%{c.trans.prefijo}%{c.nroDocumento}" readonly="true"
								size="30" disabled="true" /></td>
					</tr>

					<tr>

						<td width="200" style="color: red"
							title="Lugar de origen de las mercaderias">Lugar Origen
							mercaderias:</td>
						<td><sj:autocompleter name="paisOrigenMercaderias"
								id="paisOrigenMercaderias" href="%{paises}"
								value="%{c.origenMercaderia.toString()}"
								cssStyle="width: 350px " /></td>
						<%-- <td width="200" style="color: red" title="Lugar en que el portador se hace cargo de las mercader&iacute;as" >Lugar destino mercader&iacute;as: </td>
      		<td><sj:autocompleter name="ciudadPortador" id="ciudadPortador"  href="%{ciudades}" value="%{c.paisCiudadPortadorCargo.toString()}" cssStyle="width: 350px "  /></td>
      		--%>
						<td>Nro MICDTA:</td>
						<td><sj:textfield label="MICDTA" name="nroMICDTA"
								value="%{c.trans.prefijo}%{c.numeroDeMic}" readonly="true"
								size="30" disabled="true" /></td>
					</tr>
				</table>


				<div style="width: 100%">
					<sj:accordion id="accordion1" autoHeight="true" collapsible="true"
						active="false">
						<div style="width: 70%">
							<sj:accordionItem title="Ciudad de destino final">
								<table>
									<tr>
										<td style="color: red">Ciudad-Pais</td>
										<td><sj:autocompleter name="ciudadDeDestino"
												id="ciudadDeDestino" href="%{ciudades}"
												value="%{c.ciudadPaisDestino.toString()}" delay="50"
												loadMinimumCount="2" cssStyle="width: 450px " /></td>
									</tr>
									<tr>
										<td style="color: red">Aduana</td>
										<td><sj:autocompleter name="aduanaDestino"
												id="aduanaDestino" href="%{aduanas}"
												value="%{c.aduanaDestino.toString()}" delay="50"
												loadMinimumCount="2" cssStyle="width: 450px " /></td>
									</tr>
								</table>
							</sj:accordionItem>

							<sj:accordionItem title="Camion Original">
								<table>
									<tr>
										<td style="color: red">Transportadora:</td>
										<td><sj:autocompleter id="transportadoraCamionOriginal"
												value="%{c.transportadoraCamion.toString()}"
												name="transportadoraCamionOriginal"
												href="%{jsonTransportadora}" delay="50" loadMinimumCount="2" forceValidOption="false"
												cssStyle="width: 450px " /></td>
									</tr>
									<tr>
										<td style="color: red">Chofer:</td>
										<td><sj:autocompleter id="choferOriginal"
												value="%{c.choferOriginal.toString()}" name="choferOriginal"
												href="%{jsonChofer}" delay="50" loadMinimumCount="2" forceValidOption="false"
												cssStyle="width: 450px " /></td>
									</tr>
									<tr>
										<td style="color: red">Camion:</td>
										<td><sj:autocompleter id="camionOriginal"
												value="%{c.camionOriginal.toString()}" name="camionOriginal"
												href="%{jsonCamion}" delay="50" loadMinimumCount="2" forceValidOption="false"
												cssStyle="width: 450px " /></td>
									</tr>
									<tr>
										<td style="color: red">Remolque:</td>
										<td><sj:autocompleter id="remolqueOriginal"
												value="%{c.remolqueOriginal.toString()}"
												name="remolqueOriginal" href="%{jsonRemolques}" delay="50" forceValidOption="false"
												loadMinimumCount="2" cssStyle="width: 450px " /></td>
									</tr>
								</table>
							</sj:accordionItem>

							<sj:accordionItem title="Camion Sustituto">
								<table>
									<tr>
										<td style="color: red">Transportadora:</td>
										<td><sj:autocompleter id="transportadoraCamionSustituto"
												name="transportadoraCamionSustituto"
												value="%{c.transportadoraCamionSustituto.toString()}"
												href="%{jsonTransportadora}" delay="50" loadMinimumCount="2" forceValidOption="false"
												cssStyle="width: 450px " /></td>
									</tr>
									<tr>
										<td style="color: red">Chofer:</td>
										<td><sj:autocompleter id="choferSubstituto"
												name="choferSubstituto"
												value="%{c.choferSubstituto.toString()}"
												href="%{jsonChofer}" delay="50" loadMinimumCount="2" forceValidOption="false"
												cssStyle="width: 450px " /></td>
									</tr>
									<tr>
										<td style="color: red">Camion:</td>
										<td><sj:autocompleter id="camionSubstituto"
												name="camionSubstituto"
												value="%{c.camionSubstituto.toString()}"
												href="%{jsonCamion}" delay="50" loadMinimumCount="2" forceValidOption="false"
												cssStyle="width: 450px " /></td>
									</tr>
									<tr>
										<td style="color: red">Remolque:</td>
										<td><sj:autocompleter id="remolqueSubstituto"
												name="remolqueSubstituto"
												value="%{c.remolqueSubstituto.toString()}"
												href="%{jsonRemolques}" delay="50" loadMinimumCount="2" forceValidOption="false"
												cssStyle="width: 450px " /></td>
									</tr>
								</table>
							</sj:accordionItem>
						</div>
					</sj:accordion>

					<!--    				<table>-->
					<!--			    		<tr>-->
					<!--				      		<td>Moneda:</td>-->
					<!--				      		<td><sj:select name="carpeta.moneda"	list="lista" href="%{monedas}"	 value="%{c.moneda}" /></td>-->
					<!--			      		</tr>-->
					<!--			      		<tr>-->
					<!--				      		<td>Valor FOT:</td>-->
					<!--				      		<td><sj:textfield name="carpeta.valorFOT"    value="%{c.valorFOT}" /></td>-->
					<!--			      		</tr>-->
					<!--			      		<tr>-->
					<!--				      		<td>Flete en US$ : </td>-->
					<!--				      		<td><sj:textfield name="carpeta.costoFlete"  value="%{c.costoFlete}" /></td>-->
					<!--			      		</tr>-->
					<!--	    			</table>-->



					<sj:accordion id="accordion2" autoHeight="true" animated="slide"
						collapsible="true" active="false">
						<div style="width: 70%">
							<sj:accordionItem title="Otros Gastos">
								<table>
									<tr>
										<td>Gastos a pagar :</td>
										<td><sj:textfield name="carpeta.gastosPagar"
												value="%{c.gastosPagar}" /></td>
									</tr>
									<tr>
										<td>Monto Remitente :</td>
										<td><sj:textfield name="carpeta.montoRemitente"
												value="%{c.montoRemitente}" /></td>
									</tr>
									<tr>
										<td>Moneda Remitente :</td>
										<td><sj:select name="carpeta.monedaRemitente"
												list="lista" href="%{monedas}" value="%{c.monedaRemitente}" /></td>
									</tr>
									<tr>
										<td>Monto Destinatario :</td>
										<td><sj:textfield name="carpeta.montoDestinatario"
												value="%{c.montoDestinatario}" /></td>
									</tr>
									<tr>
										<td>Moneda Destinatario :</td>
										<td><sj:select name="carpeta.monedaDestinatario"
												id="monedaDestinatario" list="lista" href="%{monedas}"
												value="%{c.monedaDestinatario}" /></td>
									</tr>
									<tr>
										<td>Monto del flete externo :</td>
										<td><sj:textfield name="carpeta.montoFleteExterno"
												value="%{c.montoFleteExterno}" /></td>
									</tr>
									<tr>
										<td>Monto de rembolso contra entrega :</td>
										<td><sj:textfield name="carpeta.montoRembolso"
												value="%{c.montoRembolso}" /></td>
									</tr>
									<tr>
										<td>Declaraci&oacute;n del valor de las mercancias :</td>
										<td><sj:textfield name="carpeta.valorMercancias"
												value="%{c.valorMercancias}" /></td>
									</tr>
									<tr>
										<td>Otros :</td>
										<td><sj:textfield name="carpeta.otros" value="%{c.otros}" /></td>
									</tr>
								</table>
							</sj:accordionItem>
						</div>
					</sj:accordion>

					<table>
						<tr>
							<td>Moneda:</td>
							<td><sj:select name="carpeta.moneda" list="lista"
									href="%{monedas}" value="%{c.moneda}" /></td>
						</tr>
						<tr>
							<td>Valor FOT:</td>
							<td><sj:textfield id="valorFot" name="carpeta.valorFOT" value="%{c.valorFOT}" 
							  onChangeTopics="formatFot" /></td>
						</tr>
						<tr>
							<td>Flete en US$ :</td>
							<td><sj:textfield name="carpeta.costoFlete"
									value="%{c.costoFlete}" /></td>
						</tr>
					</table>

					<table>
						<tr>
							<td>Seguro en US$ :</td>
							<td><sj:textfield name="carpeta.seguro" value="%{c.seguro}" /></td>
						</tr>
						<tr>
							<td style="color: red">Tipo de Bultos :</td>
							<td><sj:autocompleter name="tipoBulto" id="tipoBulto"
									href="%{bultos}" value="%{c.tipoBulto.toString()}" /></td>
						</tr>
						<tr>
							<td>Cantidad de Bultos :</td>
							<td><sj:textfield name="carpeta.cantidadBultos"
									value="%{c.cantidadBultos}" /></td>
						</tr>
						<tr>
							<td>Quilaje Carga(Kg) :</td>
							<td><sj:textfield name="carpeta.pesoBruto"
									id="carpeta.pesoBruto" value="%{c.pesoBruto}">
								</sj:textfield></td>
						</tr>
						<tr>
							<td>Volumen en m.cu :</td>
							<td><sj:textfield name="carpeta.volumenMC"
									value="%{c.volumenMC}" /></td>
						</tr>
						<tr>
							<td style="color: red">Remitente</td>
							<td><sj:autocompleter name="empresaRemitente"
									id="empresaRemitente" href="%{empresas}"
									value="%{c.remitente.toString()}" cssStyle="width: 800px " /></td>
						</tr>
						<tr>
							<td style="color: red">Destinatario</td>
							<td><sj:autocompleter name="empresaDestinataria"
									id="empresaDestinataria" href="%{empresas}"
									value="%{c.destinatario.toString()}" cssStyle="width: 800px " /></td>
						</tr>
						<tr>
							<td style="color: red">Consignatario:</td>
							<td><sj:autocompleter name="empresaConsignataria"
									id="empresaConsignataria" href="%{empresas}"
									value="%{c.consignatario.toString()}" cssStyle="width: 800px " /></td>
						</tr>
						<tr>
							<td style="color: red">Notificar a:</td>
							<td><sj:autocompleter name="notificarA" id="notificarA"
									href="%{empresas}" value="%{c.notificar.toString()}"
									cssStyle="width: 800px " /></td>
						</tr>
					</table>

				</div>


				<sj:accordion id="accordion3" autoHeight="true" animated="slide"
					collapsible="true" active="false">
					<sj:accordionItem title="Contenedor">
						<table>
							<tr>
								<td>Tipo Contenedor :</td>
								<td><sj:select name="carpeta.tipoContenedor"
										href="%{tipoContenedor}" value="%{c.tipoContenedor}"
										list="lista" /></td>
							</tr>
							<tr>
								<td>Marca y numero de los bultos :</td>
								<td><sj:textarea name="carpeta.marcaYnumerobultos"
										cols="70" rows="7" value="%{c.marcaYnumerobultos}" /></td>
							</tr>
							<tr>
								<td>Tama&ntilde;o de Letra :</td>
								<td><sj:select name="carpeta.tamanioLetraMarcaYNumero"
										list="lista" href="%{tamanosLetras}"
										value="%{c.tamanioLetraMarcaYNumero}" /></td>
							</tr>
							<tr>
								<td>Documentos Anexos :</td>
								<td><sj:textarea name="carpeta.documentosAnexos" cols="40"
										rows="7" value="%{c.documentosAnexos}" /></td>
							</tr>
							<tr>
								<td>Instrucc. sobre formalidades de aduana :</td>
								<td><sj:textarea name="carpeta.formalidadesAduana"
										cols="40" rows="7" value="%{c.formalidadesAduana}" /></td>
							</tr>
						</table>
					</sj:accordionItem>

				</sj:accordion>
				<table>
					<%-- 
			    <tr>
			    	<td style="color: red">Seleccione Ruta predeterminada</td>
			    	<td><sj:autocompleter  id="ruta"  value="%{c.ruta.toString()}" name="ruta"  href="%{rutas}" delay="50" loadMinimumCount="2" cssStyle="width: 350px "/></td>
			    </tr>--%>
					<tr>
						<td>Rutas:</td>
						<td><sj:textarea name="carpeta.rutasLargo"
								id="carpeta.rutasLargo" label="Rutas Largo"
								value="%{c.rutasLargo}" rows="6" cols="50" /></td>
					</tr>
					<tr>
						<td>Valido Hasta :</td>
						<td><sj:textfield name="carpeta.validoHasta"
								value="%{c.validoHasta}" /></td>
					</tr>
					<tr>
						<td>Rutas Corto :</td>
						<td><sj:textfield name="carpeta.rutasCorto"
								label="Rutas Corto" value="%{c.rutasCorto}" size="40"
								maxlength="40" /></td>
					</tr>
				</table>
			</div>

			<div id="tthree">
				<sj:accordion id="accordion5" autoHeight="true" animated="slide"
					collapsible="true" active="false">
					<div style="width: 70%">
						<sj:accordionItem
							title="Agencia Maritima/Despachante/Dia solicitado por cliente">
							<table>
								<tr>
									<td style="color: red">Agencia Maritima</td>
									<td><sj:autocompleter name="agenciaMaritima"
											id="agenciaMaritima" href="%{agenciaMaritima}"
											value="%{c.agenciaMaritima.toString()}" delay="50"
											loadMinimumCount="2" cssStyle="width: 350px " /></td>
								</tr>
								<tr>
									<td>Dia Solicitado por cliente</td>
									<td><sj:datepicker
											name="carpeta.fechaSalidaSolicitudCliente"
											id="carpeta.fechaSalidaSolicitudCliente"
											displayFormat="dd/mm/yy" showAnim="slideDown"
											value="%{c.fechaSalidaSolicitudCliente}" /></td>
								</tr>
								<tr>
									<td style="color: red">Despachante</td>
									<td><sj:autocompleter name="despachante" id="despachante"
											href="%{despachante}" value="%{c.despachante.toString()}"
											delay="50" loadMinimumCount="2" cssStyle="width: 350px " /></td>
								</tr>
							</table>
						</sj:accordionItem>
						<sj:accordionItem title="Buque">
							<table>
								<tr>
									<td>Nombre Buque:</td>
									<td><sj:textfield value="%{c.nombreBuque}"
											name="carpeta.nombreBuque" cssStyle="width: 350px " /></td>
								</tr>
								<tr>
									<td>Fecha llegada:</td>
									<td><sj:datepicker name="carpeta.fechaLlegadaBuque"
											id="carpeta.fechaLlegadaBuque" displayFormat="dd/mm/yy"
											showAnim="slideDown" value="%{c.fechaLlegadaBuque}" /></td>
								</tr>
								<tr>
									<td>Liberacion:</td>
									<td><s:checkbox name="carpeta.liberacion"
											value="%{c.liberacion}" /> <sj:textarea
											name="carpeta.liberacionDescripcion"
											value="%{c.liberacionDescripcion}" cols="25" rows="3" /></td>
								</tr>
							</table>
						</sj:accordionItem>
					</div>
				</sj:accordion>


				<table cellspacing="4">
					<tr>
						<td>Factura :</td>
						<td><sj:radio name="carpeta.facturaOriginal"
								list="#{'true':'Original','false':'Copia'}"
								value="%{c.facturaOriginal}" /></td>
						<td><sj:datepicker name="carpeta.fechaRecibidoFactura"
								id="carpeta.fechaRecibidoFactura" displayFormat="dd/mm/yy"
								showAnim="slideDown" value="%{c.fechaRecibidoFactura}" /></td>
					</tr>
					<tr>
						<td>Packing :</td>
						<td><sj:radio name="carpeta.packingOriginal"
								list="#{'true':'Original','false':'Copia'}"
								value="%{c.packingOriginal}" /></td>
						<td><sj:datepicker name="carpeta.fechaRecibidoPacking"
								id="carpeta.fechaRecibidoPacking" displayFormat="dd/mm/yy"
								showAnim="slideDown" value="%{c.fechaRecibidoPacking}" /></td>
					</tr>
					<tr>
						<td>BL :</td>
						<td><sj:radio name="carpeta.blOriginal"
								list="#{'true':'Original','false':'Copia'}"
								value="%{c.blOriginal}" /></td>
						<td><sj:datepicker name="carpeta.fechaRecibidoBL"
								id="carpeta.fechaRecibidoBL" displayFormat="dd/mm/yy"
								showAnim="slideDown" value="%{c.fechaRecibidoBL}" /></td>
					</tr>
				</table>
				<table>
					<tr>
						<td>Pasado a Paraguay :</td>
						<td><s:checkbox name="carpeta.pasadoAParaguay"
								value="%{c.pasadoAParaguay}" /></td>
					</tr>
					<tr>

						<td>Fecha de Salida:</td>
						<td><sj:datepicker name="carpeta.fechaSalida"
								id="carpeta.fechaSalida" displayFormat="dd/mm/yy"
								showAnim="slideDown" duration="fast" value="%{c.fechaSalida}" /></td>
					</tr>
					<tr>
						<td>Fecha de Vencimiento</td>
						<td><sj:datepicker name="carpeta.fechaVencimiento"
								id="carpeta.fechaVencimiento" displayFormat="dd/mm/yy"
								showAnim="slideDown" duration="fast"
								value="%{c.fechaVencimiento}" /></td>
					</tr>
				</table>

				<!--	    <sj:accordion id="accordion8" autoHeight="true"  animated="slide" collapsible="true" active="false"  >-->
				<!--	    <sj:accordionItem title="Buque"  >-->
				<!--	    	<table>-->
				<!--		    	<tr>-->
				<!--		    		<td>Nombre Buque:</td>-->
				<!--		    		<td><sj:textfield value="%{c.nombreBuque}" name="carpeta.nombreBuque"  cssStyle="width: 350px "  /></td>-->
				<!--		    	</tr>-->
				<!--		    	<tr>-->
				<!--		    		<td>Fecha llegada:</td>-->
				<!--		    		<td>-->
				<!--		    		<sj:datepicker  name="carpeta.fechaLlegadaBuque"  id="carpeta.fechaLlegadaBuque" displayFormat="dd/mm/yy" showAnim="slideDown"  value="%{c.fechaLlegadaBuque}" />-->
				<!--		    		</td>-->
				<!--		    	</tr>-->
				<!--		    	<tr>-->
				<!--		    		<td>Liberacion:</td>-->
				<!--		    		<td>-->
				<!--		    			<s:checkbox name="carpeta.liberacion"  value="%{c.liberacion}" />-->
				<!--   						<sj:textarea name="carpeta.liberacionDescripcion" value="%{c.liberacionDescripcion}"  cols="25" rows="3"  />-->
				<!--   					</td>-->
				<!--		    	</tr>-->
				<!--	    	</table>-->
				<!--    		</sj:accordionItem>-->
				<!--    		-->
				<!--    	</sj:accordion>-->


			</div>
			
			<div id="tfour">
			   <table cellspacing="4" width="200px">
			   		<tr>
						<td width="130px" title="Cargar Info Garantia">Cargar Información Garantía:</td>
						<td><sj:radio name="carpeta.cargarInformacionGarantia" 
								list="#{'true':'Si','false':'No'}" value="%{c.cargarInformacionGarantia}"  /></td>				
					</tr>
				</table>
				<table cellspacing="4">
					<tr>
						<td>Tipo Garantia :</td>
						<td><sj:textfield label="Tipo Garantia" name="carpeta.tipoGarantia"
								value="%{c.tipoGarantia}" readonly="true"
								size="15"  /></td>
					</tr>
					<tr>
						<td>Importe :</td>
						<td><sj:textfield label="importeGarantia" name="carpeta.importeGarantia"
								value="%{c.importeGarantia}" readonly="true"
								size="15"  /></td>
					</tr>
					<tr>
						<td>Banco :</td>
						<td><sj:textfield label="Banco" name="carpeta.bancoGarantia"
								value="%{c.bancoGarantia}" readonly="true"
								size="15"  /></td>
					</tr>
					<tr>
						<td>Nro Cheque:</td>
						<td><sj:textfield label="Nro Cheque" name="carpeta.nroChequeGarantia"
								value="%{c.nroChequeGarantia}" readonly="true"
								size="15"/></td>
					</tr>
				</table>
				<table cellspacing="4" width="200px">
					<tr>
						<td width="130px" title="Contenedor Devuelto">Contenedor Devuelto:</td>
						<td><sj:radio name="carpeta.contenedorDevuelto" 
								list="#{'true':'Si','false':'No'}" value="%{c.contenedorDevuelto}"   /></td>						
					</tr>
					<tr>
						<td width="130px"  title="Garantia Devuelta">Garantia Devuelta:</td>
						<td><sj:radio name="carpeta.garantiaDevuelta" 
								list="#{'true':'Si','false':'No'}" value="%{c.garantiaDevuelta}"  /></td>						
					</tr>				
				</table>
			</div>
		</sj:tabbedpanel>
	</s:form>
</div>