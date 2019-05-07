package com.core.transtemare.entidades;

import com.core.transtemare.commons.Utils;

public class Localidad{

	private int idLocalidad;
	private Pais pais;
	private String descripcion;
	private boolean aduana;
	private String codigoAduana;

	
	public Localidad(int idLocalidad, Pais pais, String descripcion,
			boolean aduana) {
		super();
		this.idLocalidad = idLocalidad;
		this.pais = pais;
		this.descripcion = descripcion;
		this.aduana = aduana;
	}
	public Localidad(int idLocalidad, Pais pais, String descripcion,
			boolean aduana, String codigoAduana) {
		super();
		this.idLocalidad = idLocalidad;
		this.pais = pais;
		this.descripcion = descripcion;
		this.aduana = aduana;
		this.codigoAduana=codigoAduana;
	}

	public Localidad() {

	}

	public Localidad(int idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public int getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(int idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the esAduana
	 */
	public boolean isAduana() {
		return aduana;
	}

	/**
	 * @param esAduana the esAduana to set
	 */
	public void setAduana(boolean aduana) {
		this.aduana = aduana;
	}

	/**
	 * @return the codigoAduana
	 */
	public String getCodigoAduana() {
		return codigoAduana;
	}

	/**
	 * @param codigoAduana the codigoAduana to set
	 */
	public void setCodigoAduana(String codigoAduana) {
		this.codigoAduana = codigoAduana;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getIdLocalidad() > 0) {
			sb.append(getIdLocalidad());
			sb.append(Utils.ID);
			sb.append(getDescripcion());
			if (getPais() != null
					&& !pais.getDescripcion()
							.equalsIgnoreCase(getDescripcion())) {
				sb.append("-" + getPais().getDescripcion());
			}
			return sb.toString();
		}
		return "";
	}

}