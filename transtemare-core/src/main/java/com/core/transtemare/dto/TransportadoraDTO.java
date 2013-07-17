package com.core.transtemare.dto;


public class TransportadoraDTO {

		private Integer id;
		private String nombre;
		private String localidad;
		private String pais;
		private String rol;
		private String domicilio;
		private String prefijo;
		private String numerador;
		private String imagen;
    	
		
		
		public TransportadoraDTO(Integer idTransportadora,String nombreTransportadora, String domicilio2,
				String prefijo2, String rolDelContribuyente,String descripcion, String descripcion2,Integer numerador,String imagen) {
			setId(idTransportadora);
			setNombre(nombreTransportadora);
			setLocalidad(descripcion2);
			setPais(descripcion);
			setRol(rolDelContribuyente);
			setPrefijo(prefijo2);
			setDomicilio(domicilio2);
			setNumerador(String.valueOf(numerador));
			setImagen(imagen);

		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getLocalidad() {
			return localidad;
		}
		public void setLocalidad(String localidad) {
			this.localidad = localidad;
		}
		public String getPais() {
			return pais;
		}
		public void setPais(String pais) {
			this.pais = pais;
		}
		public String getRol() {
			return rol;
		}
		public void setRol(String rol) {
			this.rol = rol;
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
		public void setNumerador(String numerador) {
			this.numerador = numerador;
		}
		public String getNumerador() {
			return numerador;
		}
		public void setImagen(String imagen) {
			this.imagen = imagen;
		}
		public String getImagen() {
			return imagen;
		}
	
		
	
}
