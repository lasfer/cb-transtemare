package com.web.transtemare.acciones.transportadora;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class URLTransportadoras extends ActionSupport{
	
	
	private static final long serialVersionUID = 1L;

	@Action(value = "/urlTransportadoras", results={
			@Result(location="/paginas/transportadoras.jsp")})
	public String execute(){
		return SUCCESS;
	}

}
