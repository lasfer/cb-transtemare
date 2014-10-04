package com.core.transtemare.entidades;

import com.core.transtemare.commons.Utils;

public class Empresa {
	public static final byte EMPRESA = 0;
	public static final byte CLIENTE = 1;
	public static final byte DESPACHANTE = 2;
	public static final byte AGENCIA_MARITIMA = 3;

	private int idEmpresa;
	private byte tipo;
	private String nombre;
	private String rolContribuyente;
	private String direccion;
	private Localidad localidad;
	private String nombreCorto;
	private String codigo;

	public Empresa() {
	}

	public Empresa(int idEmpresa, byte tipo, String nombre,
			String rolContribuyente, String direccion, Localidad localidad,
			String nombreCorto, String codigo) {
		super();
		this.idEmpresa = idEmpresa;
		this.tipo = tipo;
		this.nombre = nombre;
		this.rolContribuyente = rolContribuyente;
		this.direccion = direccion;
		this.localidad = localidad;
		this.nombreCorto = nombreCorto;
		this.codigo = codigo;
	}

	public Empresa(Integer id) {
		idEmpresa = id;
	}

	public byte getTipo() {
		return tipo;
	}

	public void setTipo(byte tipo) {
		this.tipo = tipo;
	}

	public Empresa(int idEmpresa) {
		super();
		this.idEmpresa = idEmpresa;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getRolContribuyente() {
		return rolContribuyente;
	}

	public void setRolContribuyente(String rolContribuyente) {
		this.rolContribuyente = rolContribuyente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getIdEmpresa() > 0) {
			sb.append(getIdEmpresa());
			sb.append(Utils.ID);
			if (nombre != null && nombre.length() > 0
					&& rolContribuyente != null
					&& rolContribuyente.length() > 0
					&& (tipo == 0 || tipo == 1 || tipo == 2 || tipo == 3)) {
				sb.append(" Nom: ");
				sb.append(getNombre());
				sb.append(" Rol: ");
				sb.append(getRolContribuyente());
				sb.append(" Tipo: ");
				if (getTipo() == 0) {
					sb.append("EMP.");
				} else if (getTipo() == 1) {
					sb.append("CLI.");
				} else if (getTipo() == 2) {
					sb.append("DESP.");
				} else if (getTipo() == 3) {
					sb.append("AG.MAR.");
				}
				if(this.getDireccion()!= null){
					sb.append(" DIR. ");
					sb.append(this.getDireccion());
				}
				return sb.toString();
			} else if (nombre != null && nombre.length() > 0
					&& rolContribuyente != null
					&& rolContribuyente.length() > 0) {
				sb.append(" Nom: ");
				sb.append(getNombre());
				sb.append(" Rol: ");
				sb.append(getRolContribuyente());
				return sb.toString();
			} else if (nombre != null && nombre.length() > 0) {
				sb.append(" Nom: ");
				sb.append(getNombre());
				return sb.toString();
			}
		}

		return sb.toString();
	}

}
