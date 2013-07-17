package com.web.transtemare.acciones.carpetas;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.commons.Globales;
import com.core.transtemare.commons.Utils;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.NumerosCarpetaDocumento;
import com.core.transtemare.entidades.Transportadora;
import com.opensymphony.xwork2.ActionSupport;

public class NuevaCarpeta extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(NuevaCarpeta.class);
	private Integer cantidadMIC;
	private NumerosCarpetaDocumento numeros;
	private String transportadora;
	private Fachada fac = null;
	private Carpeta c;
	private List<String> mics = null;
	private String echo;

	public NuevaCarpeta(Fachada fac) {
		super();
		this.fac = fac;
	}

	public String execute() {

		try {
			mics = new ArrayList<String>();
			Transportadora trans = fac.obtenerTransportadoraPorId(Utils
					.obtenerId(transportadora, Utils.ID));
			if (cantidadMIC < 1) {
				cantidadMIC = 1;
			}
			numeros = fac.obtenerNumerosCarpetaDocumento(trans, cantidadMIC);
			logger.info("Creando carpeta: " + numeros.getNroCarpeta()
					+ " - Carpeta Almacenada");
			c = fac.obtenerCarpeta(numeros.getNroCarpeta());

			int nroDocumento = c.getNroDocumento();
			for (int i = 0; i < c.getCantidadMic(); i++, nroDocumento++) {
				mics.add(c.getTrans().getPrefijo() + nroDocumento);
			}

		} catch (Exception e) {
			logger.error(Globales.ERROR_AL_CREAR_CARPETA);
			logger.error(e.getMessage(), e);
			logger.error("Error al crear carpeta: " + numeros.getNroCarpeta()
					+ " - " + e.getMessage());

		}

		return SUCCESS;
	}

	public Integer getCantidadMIC() {
		return cantidadMIC;
	}

	public void setCantidadMIC(Integer cantidadMIC) {
		this.cantidadMIC = cantidadMIC;
	}

	public NumerosCarpetaDocumento getNumeros() {
		return numeros;
	}

	public void setNumeros(NumerosCarpetaDocumento numeros) {
		this.numeros = numeros;
	}

	public String getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(String transportadora) {
		this.transportadora = transportadora;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	public Carpeta getC() {
		return c;
	}

	public void setC(Carpeta c) {
		this.c = c;
	}

	public List<String> getMics() {
		return mics;
	}

	public void setMics(List<String> mics) {
		this.mics = mics;
	}

	public String getEcho() {
		return echo;
	}

	public void setEcho(String echo) {
		this.echo = echo;
	}

}
