package com.core.transtemare.dto;

import java.util.Date;

import com.core.transtemare.commons.Utils;
import com.core.transtemare.entidades.Carpeta;

public class CarpetaDTO {

	private String numeroCarpeta;
	private String referenciaDestino;
	private String numeroContenedor;
	private Integer numeroDocumento;
	private String transportadora;
	private String terminal;
	private String agenciaMaritima;
	private Date fechaLlegadaBuque;

	private Date fechaModificacion;
	private Date fechaCreacion;

	public CarpetaDTO(Carpeta carpeta) {
		super();
		this.numeroCarpeta = String.valueOf(carpeta.getIdCarpeta());
		this.referenciaDestino = carpeta.getReferenciaDestino();
		this.numeroContenedor = carpeta.getNroContenedor();
		this.numeroDocumento = carpeta.getNroDocumento();
		this.transportadora = carpeta.getTrans().getNombreTransportadora();
		this.terminal = carpeta.getTerminal() != null ? carpeta.getTerminal().getNombre() : null;
		this.agenciaMaritima = carpeta.getAgenciaMaritima().getNombre();
		this.fechaLlegadaBuque = carpeta.getFechaLlegadaBuque();
		this.fechaModificacion = carpeta.getFechaModificacion();
		this.fechaCreacion = carpeta.getFechaAlta();
	}

	public String getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(String transportadora) {
		this.transportadora = transportadora;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Date getFechaLlegadaBuque() {
		return fechaLlegadaBuque;
	}

	public void setFechaLlegadaBuque(Date fechaLlegadaBuque) {
		this.fechaLlegadaBuque = fechaLlegadaBuque;
	}

	public String getNumeroCarpeta() {
		return numeroCarpeta;
	}

	public void setNumeroCarpeta(String numeroCarpeta) {
		this.numeroCarpeta = numeroCarpeta;
	}

	public String getNumeroContenedor() {
		return numeroContenedor;
	}

	public String getNumeroContenedorFormateado() {
		if (numeroContenedor == null || numeroContenedor.isEmpty()) {
			return null;
		}
		String[] nroContenedor = Utils.desarmarNroContenedor(numeroContenedor);
		return new StringBuilder(nroContenedor[0]).append('-')
				.append(nroContenedor[1]).append('-').append(nroContenedor[2])
				.toString();
	}

	public void setNumeroContenedor(String numeroContenedor) {
		this.numeroContenedor = numeroContenedor;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the referenciaDestino
	 */
	public String getReferenciaDestino() {
		return referenciaDestino;
	}

	/**
	 * @param referenciaDestino
	 *            the referenciaDestino to set
	 */
	public void setReferenciaDestino(String referenciaDestino) {
		this.referenciaDestino = referenciaDestino;
	}

	/**
	 * @return the agenciaMaritima
	 */
	public String getAgenciaMaritima() {
		return agenciaMaritima;
	}

	/**
	 * @param agenciaMaritima
	 *            the agenciaMaritima to set
	 */
	public void setAgenciaMaritima(String agenciaMaritima) {
		this.agenciaMaritima = agenciaMaritima;
	}

	/**
	 * @return the numeroDocumento
	 */
	public Integer getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(Integer numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}
