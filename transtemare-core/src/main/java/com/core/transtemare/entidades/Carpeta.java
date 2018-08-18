package com.core.transtemare.entidades;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.core.transtemare.enums.EnumTipoGarantia;
import com.core.transtemare.enums.TipoContenedor;

public class Carpeta {

	private Integer idCarpeta;
	private Integer cantidadMic;
	private Integer nroDocumento;
	private Date fechaAlta;
	private Date fechaModificacion;
	// private Date fechaEmision;
	private Boolean transitoAduanero;
	private Boolean esCRT;
	private Integer numeroDeMic;

	private Localidad ciudadPaisPartida;
	private Localidad ciudadPaisDestino;
	private Localidad paisCiudadEmision;
	private Localidad paisCiudadPaisEntrega;

	private Pais origenMercaderia;

	private Transportadora trans;

	// Camion Original
	private Responsable choferOriginal;
	private Camion camionOriginal;
	private Camion remolqueOriginal;
	private Transportadora transportadoraCamion;

	// Camion Sustituto
	private Camion remolqueSubstituto;
	private Camion camionSubstituto;
	private Responsable choferSubstituto;
	private Transportadora transportadoraCamionSustituto;

	// BUQUE
	private String nombreBuque;
	private Date fechaLlegadaBuque;

	// ADUANA
	private Aduana aduanaOrigen;
	private Aduana aduanaDestino;

	// EMPRESAS
	private Empresa remitente;
	private Empresa consignatario;
	private Empresa destinatario;
	private Empresa notificar;
	private Empresa cliente;

	// CONTENEDOR
	private String numeroPrecinto;
	private String nroContenedor;
	private String numeroContenedorParte1;
	private String numeroContenedorParte2;
	private String numeroContenedorParte3;

	private String moneda;
	private String valorFOT;
	private String valorFOTAux;
	private String valorMercancias;
	private String costoFlete;
	private String seguro;
	private String volumenMC;
	private String cantidadBultos;
	private Bulto tipoBulto;
	private String pesoBruto;
	private String gastosPagar;
	private String montoRemitente;
	private String monedaRemitente;
	private String montoDestinatario;
	private String monedaDestinatario;
	private String montoFleteExterno;
	private String montoRembolso;
	private String otros;
	private String marcaYnumerobultos;
	private String documentosAnexos;
	private String formalidadesAduana;
	private Ruta ruta;

	// nro de carpeta en paraguay
	private String referenciaDestino;
	private Date fechaSalida;
	private Date fechaVencimiento;

	private Boolean facturaOriginal;
	private Boolean packingOriginal;
	private Boolean blOriginal;
	private Date fechaRecibidoFactura;
	private Date fechaRecibidoPacking;
	private Date fechaRecibidoBL;
	private Boolean liberacion;
	private String liberacionDescripcion;
	private Boolean pasadoAParaguay;
	private Empresa despachante;
	private Empresa agenciaMaritima;
	private String comentarios;
	private TipoContenedor tipoContenedor;
	private Date fechaSalidaSolicitudCliente;
	private String nombreFirmaDestinatario;

	// Tamanio configurable de la letra del campo Marca y Numero de bultos
	private Byte tamanioLetraMarcaYNumero;
	// Sale en el MICDTA en RUTAS ejemplo "3 DIAS"
	private String validoHasta;
	// Descripcion corta de las rutas Por ejemplo "1 y 3"
	private String rutasCorto;
	private String rutasLargo;
	private int idCarpetaPadre;
	private Terminal terminal;
	private Boolean contenedorDevuelto;
	private Boolean cargarInformacionGarantia;
	private EnumTipoGarantia tipoGarantia;
	private BigDecimal importeGarantia;
	private Date fechaCargaGarantia;
	private String bancoGarantia;
	private String nroChequeGarantia;
	private Boolean garantiaDevuelta;
	private String nroDUA;
	private String nroTransmision;
	private String firmante;

	public EnumTipoGarantia getTipoGarantia() {
		return tipoGarantia;
	}

	public void setTipoGarantia(EnumTipoGarantia tipoGarantia) {
		this.tipoGarantia = tipoGarantia;
	}

	public Boolean getContenedorDevuelto() {
		return contenedorDevuelto;
	}

	public void setContenedorDevuelto(Boolean contenedorDevuelto) {
		this.contenedorDevuelto = contenedorDevuelto;
	}

	public Boolean getCargarInformacionGarantia() {
		return cargarInformacionGarantia;
	}

	public void setCargarInformacionGarantia(Boolean cargarInformacionGarantia) {
		this.cargarInformacionGarantia = cargarInformacionGarantia;
	}

	public Boolean getGarantiaDevuelta() {
		return garantiaDevuelta;
	}

	public Date getFechaCargaGarantia() {
		return fechaCargaGarantia;
	}

	public void setFechaCargaGarantia(Date fechaCargaGarantia) {
		this.fechaCargaGarantia = fechaCargaGarantia;
	}

	public void setGarantiaDevuelta(Boolean garantiaDevuelta) {
		this.garantiaDevuelta = garantiaDevuelta;
	}

	public BigDecimal getImporteGarantia() {
		return importeGarantia;
	}

	public void setImporteGarantia(BigDecimal importeGarantia) {
		this.importeGarantia = importeGarantia;
	}

	public String getBancoGarantia() {
		return bancoGarantia;
	}

	public void setBancoGarantia(String bancoGarantia) {
		this.bancoGarantia = bancoGarantia;
	}

	private HashMap<Integer, Localidad> localidades;
	private HashMap<Integer, Aduana> aduanas;

	public String getValidoHasta() {
		return validoHasta;
	}

	public void setValidoHasta(String validoHasta) {
		this.validoHasta = validoHasta;
	}

	public String getRutasCorto() {
		return rutasCorto;
	}

	public void setRutasCorto(String rutasCorto) {
		this.rutasCorto = rutasCorto;
	}

	public Boolean getFacturaOriginal() {
		return facturaOriginal;
	}

	public void setFacturaOriginal(Boolean facturaOriginal) {
		this.facturaOriginal = facturaOriginal;
	}

	public Boolean getPackingOriginal() {
		return packingOriginal;
	}

	public void setPackingOriginal(Boolean packingOriginal) {
		this.packingOriginal = packingOriginal;
	}

	public Boolean getBlOriginal() {
		return blOriginal;
	}

	public void setBlOriginal(Boolean blOriginal) {
		this.blOriginal = blOriginal;
	}

	public Date getFechaRecibidoFactura() {
		return fechaRecibidoFactura;
	}

	public void setFechaRecibidoFactura(Date fechaRecibidoFactura) {
		this.fechaRecibidoFactura = fechaRecibidoFactura;
	}

	public Date getFechaRecibidoPacking() {
		return fechaRecibidoPacking;
	}

	public Byte getTamanioLetraMarcaYNumero() {
		return tamanioLetraMarcaYNumero;
	}

	public void setTamanioLetraMarcaYNumero(Byte tamanioLetraMarcaYNumero) {
		this.tamanioLetraMarcaYNumero = tamanioLetraMarcaYNumero;
	}

	public void setFechaRecibidoPacking(Date fechaRecibidoPacking) {
		this.fechaRecibidoPacking = fechaRecibidoPacking;
	}

	public Date getFechaRecibidoBL() {
		return fechaRecibidoBL;
	}

	public void setFechaRecibidoBL(Date fechaRecibidoBL) {
		this.fechaRecibidoBL = fechaRecibidoBL;
	}

	public Empresa getCliente() {
		return cliente;
	}

	public void setCliente(Empresa cliente) {
		this.cliente = cliente;
	}

	public Boolean getLiberacion() {
		return liberacion;
	}

	public void setLiberacion(Boolean liberacion) {
		this.liberacion = liberacion;
	}

	public String getLiberacionDescripcion() {
		return liberacionDescripcion;
	}

	public void setLiberacionDescripcion(String liberacionDescripcion) {
		this.liberacionDescripcion = liberacionDescripcion;
	}

	public Boolean getPasadoAParaguay() {
		return pasadoAParaguay;
	}

	public void setPasadoAParaguay(Boolean pasadoAParaguay) {
		this.pasadoAParaguay = pasadoAParaguay;
	}

	public Empresa getDespachante() {
		return despachante;
	}

	public void setDespachante(Empresa despachante) {
		this.despachante = despachante;
	}

	public Empresa getAgenciaMaritima() {
		return agenciaMaritima;
	}

	public void setAgenciaMaritima(Empresa agenciaMaritima) {
		this.agenciaMaritima = agenciaMaritima;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public TipoContenedor getTipoContenedor() {
		return tipoContenedor;
	}

	public void setTipoContenedor(TipoContenedor tipoContenedor) {
		this.tipoContenedor = tipoContenedor;
	}

	public String getNombreBuque() {
		return nombreBuque;
	}

	public void setNombreBuque(String nombreBuque) {
		this.nombreBuque = nombreBuque;
	}

	public Date getFechaLlegadaBuque() {
		return fechaLlegadaBuque;
	}

	public void setFechaLlegadaBuque(Date fechaLlegadaBuque) {
		this.fechaLlegadaBuque = fechaLlegadaBuque;
	}

	public String getNroContenedor() {
		return nroContenedor;
	}

	public void setNroContenedor(String nroContenedor) {
		this.nroContenedor = nroContenedor;
	}

	public String getReferenciaDestino() {
		return referenciaDestino;
	}

	public void setReferenciaDestino(String referenciaDestino) {
		this.referenciaDestino = referenciaDestino;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Carpeta() {
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	// public Date getFechaEmision() {
	// return fechaEmision;
	// }
	//
	// public void setFechaEmision(Date fechaEmision) {
	// this.fechaEmision = fechaEmision;
	// }

	public Boolean getTransitoAduanero() {
		return transitoAduanero;
	}

	public void setTransitoAduanero(Boolean transitoAduanero) {
		this.transitoAduanero = transitoAduanero;
	}

	public Localidad getCiudadPaisPartida() {
		return ciudadPaisPartida;
	}

	public void setCiudadPaisPartida(Localidad ciudadPaisPartida) {
		this.ciudadPaisPartida = ciudadPaisPartida;
	}

	public Localidad getCiudadPaisDestino() {
		return ciudadPaisDestino;
	}

	public void setCiudadPaisDestino(Localidad ciudadPaisDestino) {
		this.ciudadPaisDestino = ciudadPaisDestino;
	}

	public Localidad getPaisCiudadEmision() {
		return paisCiudadEmision;
	}

	public void setPaisCiudadEmision(Localidad paisCiudadEmision) {
		this.paisCiudadEmision = paisCiudadEmision;
	}

	public Pais getOrigenMercaderia() {
		return origenMercaderia;
	}

	public void setOrigenMercaderia(Pais origenMercaderia) {
		this.origenMercaderia = origenMercaderia;
	}

	public Aduana getAduanaOrigen() {
		return aduanaOrigen;
	}

	public void setAduanaOrigen(Aduana aduanaOrigen) {
		this.aduanaOrigen = aduanaOrigen;
	}

	public Aduana getAduanaDestino() {
		return aduanaDestino;
	}

	public void setAduanaDestino(Aduana aduanaDestino) {
		this.aduanaDestino = aduanaDestino;
	}

	public Camion getCamionOriginal() {
		return camionOriginal;
	}

	public void setCamionOriginal(Camion camionOriginal) {
		this.camionOriginal = camionOriginal;
	}

	public Responsable getChoferOriginal() {
		return choferOriginal;
	}

	public void setChoferOriginal(Responsable choferOriginal) {
		this.choferOriginal = choferOriginal;
	}

	public Camion getCamionSubstituto() {
		return camionSubstituto;
	}

	public void setCamionSubstituto(Camion camionSubstituto) {
		this.camionSubstituto = camionSubstituto;
	}

	public Responsable getChoferSubstituto() {
		return choferSubstituto;
	}

	public void setChoferSubstituto(Responsable choferSubstituto) {
		this.choferSubstituto = choferSubstituto;
	}

	public Empresa getRemitente() {
		return remitente;
	}

	public void setRemitente(Empresa remitente) {
		this.remitente = remitente;
	}

	public Empresa getConsignatario() {
		return consignatario;
	}

	public void setConsignatario(Empresa consignatario) {
		this.consignatario = consignatario;
	}

	public Empresa getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Empresa destinatario) {
		this.destinatario = destinatario;
	}

	public Empresa getNotificar() {
		return notificar;
	}

	public void setNotificar(Empresa notificar) {
		this.notificar = notificar;
	}

	public String getNumeroPrecinto() {
		return numeroPrecinto;
	}

	public void setNumeroPrecinto(String numeroPrecinto) {
		this.numeroPrecinto = numeroPrecinto;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getValorFOT() {
		return valorFOT;
	}

	public void setValorFOT(String valorFOT) {
		this.valorFOT = valorFOT;
	}

	public String getValorMercancias() {
		return valorMercancias;
	}

	public void setValorMercancias(String valorMercancias) {
		this.valorMercancias = valorMercancias;
	}

	public String getCostoFlete() {
		return costoFlete;
	}

	public void setCostoFlete(String costoFlete) {
		this.costoFlete = costoFlete;
	}

	public String getSeguro() {
		return seguro;
	}

	public void setSeguro(String seguro) {
		this.seguro = seguro;
	}

	public String getVolumenMC() {
		return volumenMC;
	}

	public void setVolumenMC(String volumenMC) {
		this.volumenMC = volumenMC;
	}

	public String getCantidadBultos() {
		return cantidadBultos;
	}

	public void setCantidadBultos(String cantidadBultos) {
		this.cantidadBultos = cantidadBultos;
	}

	public Bulto getTipoBulto() {
		return tipoBulto;
	}

	public void setTipoBulto(Bulto tipoBulto) {
		this.tipoBulto = tipoBulto;
	}

	public String getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(String pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public String getGastosPagar() {
		return gastosPagar;
	}

	public void setGastosPagar(String gastosPagar) {
		this.gastosPagar = gastosPagar;
	}

	public String getMontoRemitente() {
		return montoRemitente;
	}

	public void setMontoRemitente(String montoRemitente) {
		this.montoRemitente = montoRemitente;
	}

	public String getMonedaRemitente() {
		return monedaRemitente;
	}

	public void setMonedaRemitente(String monedaRemitente) {
		this.monedaRemitente = monedaRemitente;
	}

	public String getMontoDestinatario() {
		return montoDestinatario;
	}

	public void setMontoDestinatario(String montoDestinatario) {
		this.montoDestinatario = montoDestinatario;
	}

	public String getMonedaDestinatario() {
		return monedaDestinatario;
	}

	public void setMonedaDestinatario(String monedaDestinatario) {
		this.monedaDestinatario = monedaDestinatario;
	}

	public String getMontoFleteExterno() {
		return montoFleteExterno;
	}

	public void setMontoFleteExterno(String montoFleteExterno) {
		this.montoFleteExterno = montoFleteExterno;
	}

	public String getMontoRembolso() {
		return montoRembolso;
	}

	public void setMontoRembolso(String montoRembolso) {
		this.montoRembolso = montoRembolso;
	}

	public String getOtros() {
		return otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public String getMarcaYnumerobultos() {
		return marcaYnumerobultos;
	}

	public void setMarcaYnumerobultos(String marcaYnumerobultos) {
		this.marcaYnumerobultos = marcaYnumerobultos;
	}

	public String getDocumentosAnexos() {
		return documentosAnexos;
	}

	public void setDocumentosAnexos(String documentosAnexos) {
		this.documentosAnexos = documentosAnexos;
	}

	public String getFormalidadesAduana() {
		return formalidadesAduana;
	}

	public void setFormalidadesAduana(String formalidadesAduana) {
		this.formalidadesAduana = formalidadesAduana;
	}

	public Transportadora getTrans() {
		return trans;
	}

	public void setTrans(Transportadora trans) {
		this.trans = trans;
	}

	public Localidad getPaisCiudadPaisEntrega() {
		return paisCiudadPaisEntrega;
	}

	public void setPaisCiudadPaisEntrega(Localidad paisCiudadPaisEntrega) {
		this.paisCiudadPaisEntrega = paisCiudadPaisEntrega;
	}

	public Camion getRemolqueOriginal() {
		return remolqueOriginal;
	}

	public void setRemolqueOriginal(Camion remolqueOriginal) {
		this.remolqueOriginal = remolqueOriginal;
	}

	public Camion getRemolqueSubstituto() {
		return remolqueSubstituto;
	}

	public void setRemolqueSubstituto(Camion remolqueSubstituto) {
		this.remolqueSubstituto = remolqueSubstituto;
	}

	public Integer getIdCarpeta() {
		return idCarpeta;
	}

	public void setIdCarpeta(Integer idCarpeta) {
		this.idCarpeta = idCarpeta;
	}

	public Integer getCantidadMic() {
		return cantidadMic;
	}

	public void setCantidadMic(Integer cantidadMic) {
		this.cantidadMic = cantidadMic;
	}

	public Integer getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(Integer nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Transportadora getTransportadoraCamion() {
		return transportadoraCamion;
	}

	public void setTransportadoraCamion(Transportadora transportadoraCamion) {
		this.transportadoraCamion = transportadoraCamion;
	}

	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public void setNumeroContenedorParte1(String numeroContenedorParte1) {
		this.numeroContenedorParte1 = numeroContenedorParte1;
	}

	public String getNumeroContenedorParte1() {
		return numeroContenedorParte1;
	}

	public void setNumeroContenedorParte2(String numeroContenedorParte2) {
		this.numeroContenedorParte2 = numeroContenedorParte2;
	}

	public String getNumeroContenedorParte2() {
		return numeroContenedorParte2;
	}

	public void setNumeroContenedorParte3(String numeroContenedorParte3) {
		this.numeroContenedorParte3 = numeroContenedorParte3;
	}

	public String getNumeroContenedorParte3() {
		return numeroContenedorParte3;
	}

	public void setTransportadoraCamionSustituto(
			Transportadora transportadoraCamionSustituto) {
		this.transportadoraCamionSustituto = transportadoraCamionSustituto;
	}

	public Transportadora getTransportadoraCamionSustituto() {
		return transportadoraCamionSustituto;
	}

	public Date getFechaSalidaSolicitudCliente() {
		return fechaSalidaSolicitudCliente;
	}

	public void setFechaSalidaSolicitudCliente(Date fechaSalidaSolicitudCliente) {
		this.fechaSalidaSolicitudCliente = fechaSalidaSolicitudCliente;
	}

	public void setEsCRT(Boolean esCRT) {
		this.esCRT = esCRT;
	}

	public Boolean getEsCRT() {
		return esCRT;
	}

	public void setNumeroDeMic(Integer numeroDeMic) {
		this.numeroDeMic = numeroDeMic;
	}

	public Integer getNumeroDeMic() {
		return numeroDeMic;
	}

	public void setNombreFirmaDestinatario(String nombreFirmaDestinatario) {
		this.nombreFirmaDestinatario = nombreFirmaDestinatario;
	}

	public String getNombreFirmaDestinatario() {
		return nombreFirmaDestinatario;
	}

	public String getRutasLargo() {
		return rutasLargo;
	}

	public void setRutasLargo(String rutasLargo) {
		this.rutasLargo = rutasLargo;
	}

	/**
	 * @return the localidades
	 */
	public HashMap<Integer, Localidad> getLocalidades() {
		return localidades;
	}

	/**
	 * @param localidades
	 *            the localidades to set
	 */
	public void setLocalidades(HashMap<Integer, Localidad> localidades) {
		this.localidades = localidades;
	}

	public HashMap<Integer, Aduana> getAduanas() {
		return aduanas;
	}

	public void setAduanas(HashMap<Integer, Aduana> aduanas) {
		this.aduanas = aduanas;
	}

	/**
	 * @return the idCarpetaPadre
	 */
	public int getIdCarpetaPadre() {
		return idCarpetaPadre;
	}

	/**
	 * @param idCarpetaPadre
	 *            the idCarpetaPadre to set
	 */
	public void setIdCarpetaPadre(int idCarpetaPadre) {
		this.idCarpetaPadre = idCarpetaPadre;
	}

	/**
	 * @return the terminal
	 */
	public Terminal getTerminal() {
		return terminal;
	}

	/**
	 * @param terminal
	 *            the terminal to set
	 */
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public String getValorFOTAux() {
		return valorFOTAux;
	}

	public void setValorFOTAux(String valorFOTAux) {
		this.valorFOTAux = valorFOTAux;
	}

	public String getNroChequeGarantia() {
		return nroChequeGarantia;
	}

	public void setNroChequeGarantia(String nroChequeGarantia) {
		this.nroChequeGarantia = nroChequeGarantia;
	}

	/**
	 * @return the nroDUA
	 */
	public String getNroDUA() {
		return nroDUA;
	}

	/**
	 * @param nroDUA the nroDUA to set
	 */
	public void setNroDUA(String nroDUA) {
		this.nroDUA = nroDUA;
	}

	/**
	 * @return the nroTransmision
	 */
	public String getNroTransmision() {
		return nroTransmision;
	}

	/**
	 * @param nroTransmision the nroTransmision to set
	 */
	public void setNroTransmision(String nroTransmision) {
		this.nroTransmision = nroTransmision;
	}

	/**
	 * @return the firmante
	 */
	public String getFirmante() {
		return firmante;
	}

	/**
	 * @param firmante the firmante to set
	 */
	public void setFirmante(String firmante) {
		this.firmante = firmante;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}