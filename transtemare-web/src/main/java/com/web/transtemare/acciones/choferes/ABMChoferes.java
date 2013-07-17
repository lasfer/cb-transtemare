package com.web.transtemare.acciones.choferes;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.Responsable;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class ABMChoferes extends ActionSupport {

	private Logger logger = Logger.getLogger(ABMChoferes.class);
	private static final long serialVersionUID = 1L;
	private String oper = "";
	private String id;
	private String nombre;
	private String apellido;
	private String documento;
	private String pais;
	private String ciudad;
	private String telefono;
	private Fachada fac;

	public ABMChoferes(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/ABMChoferes", results = { @Result(location = "/paginas/simpleecho.jsp") })
	public String execute() throws Exception {

		logger.debug("Entro execute");

		try {
			if (oper.equals("add")) {
				fac.altaChofer(new Responsable(0, documento, nombre, apellido,
						new Localidad(0, null, ciudad, false), telefono));
			} else if (oper.equals("del")) {
				fac.borrarChofer(Integer.valueOf(id));
			} else if (oper.equals("edit")) {
				fac.modificarChofer(new Responsable(Integer.valueOf(id),
						documento, nombre, apellido, new Localidad(0, null,
								ciudad, false), telefono));
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

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
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
