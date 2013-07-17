package com.web.transtemare.acciones.transportadora;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class ABMTransportadora extends ActionSupport {

	private Logger logger = Logger.getLogger(ABMTransportadora.class);
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private String domicilio;
	private String rol;
	private String pais;
	private String localidad;
	private String oper = "";
	private String prefijo;
	private String numerador;
	private String imagen;
	private Fachada fac;

	public ABMTransportadora(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/ABMTransportadora", results = { @Result(location = "/paginas/simpleecho.jsp") })
	public String execute() throws Exception {

		try {
			if (oper.equals("add")) {
				fac.altaTransportadora(nombre, domicilio, rol, pais, localidad,
						prefijo, numerador, imagen);
			} else if (oper.equals("del")) {
				fac.borrarTransportadora(Integer.parseInt(id));
			} else if (oper.equals("edit")) {
				fac.modificarTransportadora(Integer.parseInt(id), nombre,
						domicilio, rol, pais, localidad, prefijo, numerador,
						imagen);
			} else if (oper.equals("")) {
				return SUCCESS;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}

		return SUCCESS;
	}

	public void validate() {
	}

	public void validateExecute() {
	}

	public void validateDoExecute() {
	}

	public void defaultMethod() {

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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
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

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getImagen() {
		return imagen;
	}

}
