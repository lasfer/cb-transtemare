package com.web.transtemare.acciones.carpetas;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.commons.Globales;
import com.core.transtemare.commons.Utils;
import com.core.transtemare.entidades.Carpeta;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class EditarCarpeta extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditarCarpeta.class);
	private Carpeta c;
	private Carpeta carpeta=new Carpeta();
	private Integer id;
	private String carpeta1;
	private String carpeta2;
	private String carpeta3;
	private Fachada fac;

	public EditarCarpeta(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/editarCarpeta", results = { @Result(location = "/paginas/carpeta.jsp", name = "success") })
	public String execute() {
		try {
			c = fac.obtenerCarpeta(id);
			String[] lista = Utils.desarmarNroContenedor(c.getNroContenedor());
			carpeta1 = lista[0];
			carpeta2 = lista[1];
			carpeta3 = lista[2];
			
		} catch (Exception e) {
			logger.error(Globales.ERROR_EDITAR_CARPETA, e);
			return ERROR;
		}
		return SUCCESS;
	}

	public Carpeta getC() {
		return c;
	}

	public void setC(Carpeta c) {
		this.c = c;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCarpeta1() {
		return carpeta1;
	}

	public void setCarpeta1(String carpeta1) {
		this.carpeta1 = carpeta1;
	}

	public String getCarpeta2() {
		return carpeta2;
	}

	public void setCarpeta2(String carpeta2) {
		this.carpeta2 = carpeta2;
	}

	public String getCarpeta3() {
		return carpeta3;
	}

	public void setCarpeta3(String carpeta3) {
		this.carpeta3 = carpeta3;
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
