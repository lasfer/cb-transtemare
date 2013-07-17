package com.core.transtemare.entidades;

import java.util.Date;

import com.core.transtemare.commons.Utils;

public class Camion {
	private int idCamion;
	private String matricula;
	private String capacidad;
	private String marca;
	private String anio;
	private String tipo;
	private String numeroPoliza;
	private Date vencimientoPoliza;

	public Camion() {

	}

	public Camion(int idCamion) {
		super();
		this.idCamion = idCamion;
	}

	public Camion(Integer idCamion, String matricula, String capacidad,
			String marca, String anio, String tipo, String numeroPoliza,
			Date vencimientoPoliza) {
		super();
		setIdCamion(idCamion);
		setMatricula(matricula);
		setCapacidad(capacidad);
		setMarca(marca);
		setAnio(anio);
		setTipo(tipo);
		setNumeroPoliza(numeroPoliza);
		setVencimientoPoliza(vencimientoPoliza);
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

	public int getIdCamion() {
		return idCamion;
	}

	public void setIdCamion(int idCamion) {
		this.idCamion = idCamion;
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

	public String getNumeroPoliza() {
		return numeroPoliza;
	}

	public void setNumeroPoliza(String numeroPoliza) {
		this.numeroPoliza = numeroPoliza;
	}

	public Date getVencimientoPoliza() {
		return vencimientoPoliza;
	}

	public void setVencimientoPoliza(Date vencimientoPoliza) {
		this.vencimientoPoliza = vencimientoPoliza;
	}

	public String toString() {
		StringBuilder sb;
		if (matricula != null && matricula.length() > 0) {
			sb = new StringBuilder();
			sb.append(getIdCamion());
			sb.append(Utils.ID);
			sb.append(" Mat:");
			sb.append(matricula);
			if (marca != null && marca.length() > 0) {
				sb.append(" Marca:").append(marca);
			}
			// if(anio>0){
			sb.append(" Anio:").append(anio);
			// }
			return sb.toString();
		}
		return "";
	}

}