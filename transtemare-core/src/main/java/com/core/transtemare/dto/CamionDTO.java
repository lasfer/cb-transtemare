package com.core.transtemare.dto;

import java.util.Date;

public class CamionDTO {

	private String id;
	private String matricula;
	private String capacidad;
	private String marca;
	private String anio;
	private String tipo;
	private String numeroPoliza;
	private Date vencimientoPoliza;

	public CamionDTO(int idCamion, String anio2, String capacidad2,
			String marca2, String matricula2, String tipo, String numeroPoliza,
			Date vencimientoPoliza) {
		setId(String.valueOf(idCamion));
		setMatricula(matricula2);
		setMarca(marca2);
		setCapacidad(String.valueOf(capacidad2));
		setAnio(String.valueOf(anio2));
		setTipo(tipo);
		setNumeroPoliza(numeroPoliza);
		setVencimientoPoliza(vencimientoPoliza);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	/**
	 * @return the numeroPoliza
	 */
	public String getNumeroPoliza() {
		return numeroPoliza;
	}

	/**
	 * @param numeroPoliza
	 *            the numeroPoliza to set
	 */
	public void setNumeroPoliza(String numeroPoliza) {
		this.numeroPoliza = numeroPoliza;
	}

	/**
	 * @return the vencimientoPoliza
	 */
	public Date getVencimientoPoliza() {
		return vencimientoPoliza;
	}

	/**
	 * @param vencimientoPoliza
	 *            the vencimientoPoliza to set
	 */
	public void setVencimientoPoliza(Date vencimientoPoliza) {
		this.vencimientoPoliza = vencimientoPoliza;
	}

}
