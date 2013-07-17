package com.web.transtemare.acciones.carpetas;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class ResumenCorto  extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Fachada fac;
	private Integer idCarpeta;
//	private static final Logger logger = Logger.getLogger(ResumenCorto.class);

	
	
	
	public ResumenCorto(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/resumenCortoCarpeta", results = { @Result(location = "/paginas/resumen_corto.jsp") })
	public String execute(){
		
		setIdCarpeta(id);
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}


	public void setIdCarpeta(Integer idCarpeta) {
		this.idCarpeta = idCarpeta;
	}

	public Integer getIdCarpeta() {
		return idCarpeta;
	}
	
	
	
	
}
