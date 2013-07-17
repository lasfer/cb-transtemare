package com.core.transtemare.entidades;

public class Responsable {

	public Responsable(int idResponsable) {
		super();
		this.idResponsable = idResponsable;
	}

	private int idResponsable;
	private String documento;
	private String nombre;
	private String apellido;
	private Localidad localidad;
	private String telefono;

	public Responsable() {

	}

	public Responsable(int idResponsable, String documento, String nombre,
			String apellido, Localidad localidad, String telefono) {
		super();
		this.idResponsable = idResponsable;
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.localidad = localidad;
		this.telefono = telefono;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(int idResponsable) {
		this.idResponsable = idResponsable;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Localidad getLocalidad() {
		return localidad;
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

	public String toString() {
		StringBuilder sb;
		if (documento != null && documento.length() > 0) {
			sb = new StringBuilder();
			sb.append(getIdResponsable());
			sb.append("-- ");
			sb.append("Doc:");
			sb.append(documento);
			if (nombre != null && nombre.length() > 0 && apellido != null
					&& apellido.length() > 0) {
				sb.append(" Nom: ");
				sb.append(nombre);
				sb.append(" ");
				sb.append(apellido);
			}
			return sb.toString();
		}
		return "";
	}
}