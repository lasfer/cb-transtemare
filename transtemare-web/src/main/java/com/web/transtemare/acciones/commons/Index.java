package com.web.transtemare.acciones.commons;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class Index extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private String destino = "urlCarpetas";

	@Action(value = "/index", results = { @Result(location = "/paginas/index.jsp") })
	public String execute() {
		return SUCCESS;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getDestino() {
		return destino;
	}
}
