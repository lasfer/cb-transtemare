package com.core.transtemare.entidades;


public class Ruta {

	private int idRuta;
	private String descripcion;
	private String descripcionCorta;
	
	
	public Ruta(){
	}
	
	public Ruta(int id) {
		idRuta = id;
	}

	public int getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}

	public String getDescripcion() {
		
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(getIdRuta() > 0){
		sb.append(getIdRuta());
		sb.append("-- ");
		sb.append(getDescripcionCorta());
		return sb.toString();
		}
		return "";
	}

}