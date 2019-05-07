package com.core.transtemare.entidades;

import java.io.Serializable;

import com.core.transtemare.commons.Utils;

public class Pais implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7006914030333032688L;
	private int idPais;
	private String descripcion;
	private String codigo;

	public Pais() {

	}

	public Pais(int idPais) {
		this.idPais = idPais;
	}

	public Pais(int pais, String desc) {
		this.idPais = pais;
		this.descripcion = desc;
	}

	public int getIdPais() {
		return idPais;
	}

	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getIdPais() > 0) {
			sb.append(getIdPais());
			sb.append(Utils.ID);
			sb.append(getDescripcion());
		}
		return sb.toString();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}