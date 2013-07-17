package com.core.transtemare.dto;

public class ChoferDTO {

	private String id;
	private String nombre;
	private String apellido;
	private String documento;
	private String pais;
	private String ciudad;
	private String telefono;

	public ChoferDTO(int idResponsable, String nombre2, String apellido2,
			String documento2, String pais, String ciudad, String telefono) {
		setId(String.valueOf(idResponsable));
		setNombre(nombre2);
		setApellido(apellido2);
		setDocumento(documento2);
		setPais(pais);
		setCiudad(ciudad);
		setTelefono(telefono);

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

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
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

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getApellido() {
		return apellido;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
