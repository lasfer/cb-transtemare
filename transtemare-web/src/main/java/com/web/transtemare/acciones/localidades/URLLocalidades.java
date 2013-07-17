package com.web.transtemare.acciones.localidades;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

public class URLLocalidades extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Action(value = "/urlLocalidades", results = { @Result(location = "/paginas/localidades.jsp", name = "success") })
	public String execute() {
		return SUCCESS;
	}

}
