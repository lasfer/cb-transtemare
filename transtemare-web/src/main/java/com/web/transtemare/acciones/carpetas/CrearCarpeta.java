package com.web.transtemare.acciones.carpetas;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.commons.Globales;
import com.core.transtemare.commons.Utils;
import com.core.transtemare.entidades.Aduana;
import com.core.transtemare.entidades.Bulto;
import com.core.transtemare.entidades.Camion;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.Pais;
import com.core.transtemare.entidades.Responsable;
import com.core.transtemare.entidades.Ruta;
import com.core.transtemare.entidades.Terminal;
import com.core.transtemare.entidades.Transportadora;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@ParentPackage(value = "default")
public class CrearCarpeta extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CrearCarpeta.class);

	private String idCarpeta;
	// private Date fechaDeEmision;//Ver si la fecha de emision vale la pena
	// modificarlo porque se setea cuando se crea la carpeta
	private String ciudadDeOrigen;
	private String aduanaOrigen;
	private String ciudadDeDestino;
	private String aduanaDestino;
	// Camion Original
	private String camionOriginal;
	private String remolqueOriginal;
	private String choferOriginal;
	private String transportadoraCamionOriginal;
	// Camion sustituto
	private String camionSubstituto;
	private String remolqueSubstituto;
	private String choferSubstituto;
	private String transportadoraCamionSustituto;// Esta hay que crearla

	private String empresaRemitente;
	private String empresaDestinataria;
	private String empresaConsignataria;
	private String notificarA;

	private String ciudadDeEmision;
	private String ciudadPortador;
	private String ciudadEntrega;
	private String paisOrigenMercaderias;
	private Fachada fac;
	private String transportadora;
	private String cliente;
	private String despachante;
	private String agenciaMaritima;
	private String ruta;
	private String echo;
	private String tipoBulto;
	private Terminal terminal;

	private Carpeta carpeta = new Carpeta();
	{
		carpeta.setCliente(new Empresa());
		carpeta.setDespachante(new Empresa());
		carpeta.setAgenciaMaritima(new Empresa());
		carpeta.setTransportadoraCamion(new Transportadora());
		carpeta.setRuta(new Ruta());
		carpeta.setTerminal(new Terminal());
	}

	public CrearCarpeta(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/CrearCarpeta", results = {
			@Result(location = "/paginas/simpleecho.jsp", name = "success"),
			@Result(location = "/paginas/simpleecho.jsp", name = "input"),
			@Result(location = "/paginas/simpleecho.jsp", name = "error") })
	public String execute() {
		boolean check = false;
		try {

			carpeta.setTipoBulto(new Bulto(Utils.obtenerId(tipoBulto, Utils.ID)));
			carpeta.setNroContenedor(Utils.construirNroContenedor(
					carpeta.getNumeroContenedorParte1(),
					carpeta.getNumeroContenedorParte2(),
					carpeta.getNumeroContenedorParte3()));
			carpeta.setCliente(new Empresa(Utils.obtenerId(cliente, Utils.ID)));
			carpeta.setDespachante(new Empresa(Utils.obtenerId(despachante,
					Utils.ID)));
			carpeta.setAgenciaMaritima(new Empresa(Utils.obtenerId(
					agenciaMaritima, Utils.ID)));
			carpeta.setTransportadoraCamion(new Transportadora(Utils.obtenerId(
					transportadoraCamionOriginal, Utils.ID)));
			carpeta.setRuta(new Ruta(Utils.obtenerId(ruta, Utils.ID)));

			// logger.info("Modificando carpeta: " + idCarpeta +
			// " - Parseando campos");
			Date fechaModificacion = new Date();
			carpeta.setIdCarpeta(Integer.valueOf(idCarpeta));
			carpeta.setFechaModificacion(fechaModificacion);
			// carpeta.setFechaEmision(fechaDeEmision);

			// Camion Original
			carpeta.setCamionOriginal(new Camion(Utils.obtenerId(
					camionOriginal, Utils.ID)));
			carpeta.setRemolqueOriginal(new Camion(Utils.obtenerId(
					getRemolqueOriginal(), Utils.ID)));
			carpeta.setChoferOriginal(new Responsable(Utils.obtenerId(
					choferOriginal, Utils.ID)));

			// Camion Sustituto
			carpeta.setCamionSubstituto(new Camion(Utils.obtenerId(
					camionSubstituto, Utils.ID)));
			carpeta.setRemolqueSubstituto(new Camion(Utils.obtenerId(
					getRemolqueSubstituto(), Utils.ID)));
			carpeta.setChoferSubstituto(new Responsable(Utils.obtenerId(
					choferSubstituto, Utils.ID)));
			carpeta.setTransportadoraCamionSustituto(new Transportadora(Utils
					.obtenerId(transportadoraCamionSustituto, Utils.ID)));

			// Pais partida//aduana Origen//ciudad destino//aduana detino//
			carpeta.setCiudadPaisPartida(new Localidad(Utils.obtenerId(
					ciudadDeOrigen, Utils.ID)));// primero
			carpeta.setAduanaOrigen(new Aduana(Utils.obtenerId(aduanaOrigen,
					Utils.ID)));
			carpeta.setCiudadPaisDestino(new Localidad(Utils.obtenerId(
					ciudadDeDestino, Utils.ID)));
			carpeta.setAduanaDestino(new Aduana(Utils.obtenerId(aduanaDestino,
					Utils.ID)));

			// remitente//destinatario//consignatario//notificar a
			carpeta.setRemitente(new Empresa(Utils.obtenerId(empresaRemitente,
					Utils.ID)));
			carpeta.setDestinatario(new Empresa(Utils.obtenerId(
					empresaDestinataria, Utils.ID)));
			carpeta.setConsignatario(new Empresa(Utils.obtenerId(
					empresaConsignataria, Utils.ID)));
			carpeta.setNotificar(new Empresa(Utils.obtenerId(notificarA,
					Utils.ID)));

			// pais ciudad emision//pais ciudad portador cargo//pais ciudad
			// entrega//origen mercaderia
			carpeta.setPaisCiudadEmision(new Localidad(Utils.obtenerId(
					ciudadDeEmision, Utils.ID)));// Segundo
			carpeta.setPaisCiudadPaisEntrega(new Localidad(Utils.obtenerId(
					ciudadEntrega, Utils.ID)));
			carpeta.setOrigenMercaderia(new Pais(Utils.obtenerId(
					paisOrigenMercaderias, Utils.ID)));
			// carpeta.setTrans(trans);
			// carpeta.setTransportadoraCamion(transportadoraCamion)
			check = fac.modificarCarpeta(carpeta);
			logger.info("Modificando carpeta: " + idCarpeta
					+ " - Carpeta Almacenada");

		} catch (NumberFormatException e) {
			setEcho("La carpeta no se guardo correctamente , hubo un error");
		} catch (Exception e) {
			setEcho("La carpeta no se guardo correctamente , hubo un error");
			logger.error(
					"Error al modificar carpeta: " + idCarpeta + " - "
							+ e.getMessage(), e);
			logger.error(Globales.ERROR_AL_CREAR_CARPETA);
			logger.error(e.getMessage());
		}

		if (check) {
			addActionMessage(Globales.SE_CREO_CARPETA);
			setEcho("La carpeta se guardo correctamente");
			return SUCCESS;
		}
		return SUCCESS;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "El numero de carpeta es un campo requerido")
	public String getIdCarpeta() {
		return idCarpeta;
	}

	public void setIdCarpeta(String idCarpeta) {
		this.idCarpeta = idCarpeta;
	}

	public String getCiudadDeOrigen() {
		return ciudadDeOrigen;
	}

	public void setCiudadDeOrigen(String ciudadDeOrigen) {
		this.ciudadDeOrigen = ciudadDeOrigen;
	}

	public String getAduanaOrigen() {
		return aduanaOrigen;
	}

	public void setAduanaOrigen(String aduanaOrigen) {
		this.aduanaOrigen = aduanaOrigen;
	}

	public String getCiudadDeDestino() {
		return ciudadDeDestino;
	}

	public void setCiudadDeDestino(String ciudadDeDestino) {
		this.ciudadDeDestino = ciudadDeDestino;
	}

	public String getAduanaDestino() {
		return aduanaDestino;
	}

	public void setAduanaDestino(String aduanaDestino) {
		this.aduanaDestino = aduanaDestino;
	}

	public String getCamionOriginal() {
		return camionOriginal;
	}

	public void setCamionOriginal(String camionOriginal) {
		this.camionOriginal = camionOriginal;
	}

	public String getRemolqueOriginal() {
		return remolqueOriginal;
	}

	public void setRemolqueOriginal(String remolqueOriginal) {
		this.remolqueOriginal = remolqueOriginal;
	}

	public String getChoferOriginal() {
		return choferOriginal;
	}

	public void setChoferOriginal(String choferOriginal) {
		this.choferOriginal = choferOriginal;
	}

	public String getTransportadoraCamionOriginal() {
		return transportadoraCamionOriginal;
	}

	public void setTransportadoraCamionOriginal(
			String transportadoraCamionOriginal) {
		this.transportadoraCamionOriginal = transportadoraCamionOriginal;
	}

	public String getCamionSubstituto() {
		return camionSubstituto;
	}

	public void setCamionSubstituto(String camionSubstituto) {
		this.camionSubstituto = camionSubstituto;
	}

	public String getRemolqueSubstituto() {
		return remolqueSubstituto;
	}

	public void setRemolqueSubstituto(String remolqueSubstituto) {
		this.remolqueSubstituto = remolqueSubstituto;
	}

	public String getChoferSubstituto() {
		return choferSubstituto;
	}

	public void setChoferSubstituto(String choferSubstituto) {
		this.choferSubstituto = choferSubstituto;
	}

	public String getTransportadoraCamionSustituto() {
		return transportadoraCamionSustituto;
	}

	public void setTransportadoraCamionSustituto(
			String transportadoraCamionSustituto) {
		this.transportadoraCamionSustituto = transportadoraCamionSustituto;
	}

	public String getEmpresaRemitente() {
		return empresaRemitente;
	}

	public void setEmpresaRemitente(String empresaRemitente) {
		this.empresaRemitente = empresaRemitente;
	}

	public String getEmpresaDestinataria() {
		return empresaDestinataria;
	}

	public void setEmpresaDestinataria(String empresaDestinataria) {
		this.empresaDestinataria = empresaDestinataria;
	}

	public String getEmpresaConsignataria() {
		return empresaConsignataria;
	}

	public void setEmpresaConsignataria(String empresaConsignataria) {
		this.empresaConsignataria = empresaConsignataria;
	}

	public String getNotificarA() {
		return notificarA;
	}

	public void setNotificarA(String notificarA) {
		this.notificarA = notificarA;
	}

	public String getCiudadDeEmision() {
		return ciudadDeEmision;
	}

	public void setCiudadDeEmision(String ciudadDeEmision) {
		this.ciudadDeEmision = ciudadDeEmision;
	}

	public String getCiudadPortador() {
		return ciudadPortador;
	}

	public void setCiudadPortador(String ciudadPortador) {
		this.ciudadPortador = ciudadPortador;
	}

	public String getCiudadEntrega() {
		return ciudadEntrega;
	}

	public void setCiudadEntrega(String ciudadEntrega) {
		this.ciudadEntrega = ciudadEntrega;
	}

	public String getPaisOrigenMercaderias() {
		return paisOrigenMercaderias;
	}

	public void setPaisOrigenMercaderias(String paisOrigenMercaderias) {
		this.paisOrigenMercaderias = paisOrigenMercaderias;
	}

	public String getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(String transportadora) {
		this.transportadora = transportadora;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getDespachante() {
		return despachante;
	}

	public void setDespachante(String despachante) {
		this.despachante = despachante;
	}

	public String getAgenciaMaritima() {
		return agenciaMaritima;
	}

	public void setAgenciaMaritima(String agenciaMaritima) {
		this.agenciaMaritima = agenciaMaritima;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public void setEcho(String echo) {
		this.echo = echo;
	}

	public String getEcho() {
		return echo;
	}

	public void setTipoBulto(String tipoBulto) {
		this.tipoBulto = tipoBulto;
	}

	public String getTipoBulto() {
		return tipoBulto;
	}

	public Carpeta getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(Carpeta carpeta) {
		this.carpeta = carpeta;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}
	
	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

}
