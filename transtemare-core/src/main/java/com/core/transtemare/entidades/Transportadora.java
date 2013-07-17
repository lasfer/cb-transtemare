package com.core.transtemare.entidades;




public class Transportadora {

	private Integer idTransportadora;
	private String nombreTransportadora;
	private Localidad localidad;
	private String rolDelContribuyente = null;
	private String domicilio = null;
	private String prefijo;
	private Integer numerador;
	private String nombreArchivo;
	
	public Transportadora(Integer id) {
		idTransportadora = id ;
	}

	public Transportadora(){

	}

	public Integer getIdTransportadora() {
		return idTransportadora;
	}

	public void setIdTransportadora(Integer idTransportadora) {
		this.idTransportadora = idTransportadora;
	}

	public String getNombreTransportadora() {
		return nombreTransportadora;
	}

	public void setNombreTransportadora(String nombreTransportadora) {
		this.nombreTransportadora = nombreTransportadora;
	}


	public String getRolDelContribuyente() {
		return rolDelContribuyente;
	}

	public void setRolDelContribuyente(String rolDelContribuyente) {
		this.rolDelContribuyente = rolDelContribuyente;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	
	
	
	public Integer getNumerador() {
		return numerador;
	}

	public void setNumerador(Integer numerador) {
		this.numerador = numerador;
	}
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(getIdTransportadora() > 0){
		sb.append(getIdTransportadora());
		sb.append("-- ");
		if(nombreTransportadora != null && nombreTransportadora.length() >0){
			sb.append("Nom: ");
			sb.append(nombreTransportadora);
			return sb.toString();
		}
		}
		return sb.toString();
	}
	
	
	

}