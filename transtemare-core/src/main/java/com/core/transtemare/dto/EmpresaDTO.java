package com.core.transtemare.dto;

public class EmpresaDTO {

	private String id;
	private String nombre;
	private String domicilio;
	private String rol;
	private String tipo;
	private String pais;
	private String ciudad;
	private String nombreCorto;

	public EmpresaDTO(int idEmpresa, String nombre2, String rolContribuyente,
			byte tipo2, String direccion, String pais, String ciudad,
			String nombreCorto) {
		setId(String.valueOf(idEmpresa));
		setNombre(nombre2);
		setRol(rolContribuyente);
		setDomicilio(direccion);
		this.nombreCorto = nombreCorto;
		switch (tipo2) {
		case 0:
			setTipo("Empresa");
			break;
		case 1:
			setTipo("Cliente");
			break;
		case 2:
			setTipo("Despachante");
			break;
		case 3:
			setTipo("Agencia Maritima");
			break;

		default:
			break;
		}
		setPais(pais);
		setCiudad(ciudad);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the nombreCorto
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}

	/**
	 * @param nombreCorto
	 *            the nombreCorto to set
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

}
