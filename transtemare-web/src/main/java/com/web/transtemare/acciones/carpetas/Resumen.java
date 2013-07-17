package com.web.transtemare.acciones.carpetas;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Carpeta;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class Resumen extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private String id;
	private Fachada fac;
	private Carpeta carpeta;
	private static final Logger logger = Logger.getLogger(Resumen.class);

	public Resumen(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/resumenCarpeta", results = { @Result(location = "/paginas/resumen.jsp") })
	public String execute() {

		try {
			carpeta = fac.obtenerCarpeta(Integer.valueOf(id));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}

		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	public Carpeta getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(Carpeta carpeta) {
		this.carpeta = carpeta;
	}

}
