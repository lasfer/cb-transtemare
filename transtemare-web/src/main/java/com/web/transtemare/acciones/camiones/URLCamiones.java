package com.web.transtemare.acciones.camiones;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

public class URLCamiones extends ActionSupport{

	
	private static final long serialVersionUID = 1L;

	@Action(value = "/urlCamiones", results={
			@Result(location="/paginas/camiones.jsp",name="success")})
	public String execute(){
		return SUCCESS;
	}
	
}
