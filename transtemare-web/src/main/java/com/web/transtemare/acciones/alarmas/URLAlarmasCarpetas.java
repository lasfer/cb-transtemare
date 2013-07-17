package com.web.transtemare.acciones.alarmas;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class URLAlarmasCarpetas extends ActionSupport{

	
	private static final long serialVersionUID = 1L;

	@Action(value = "/urlAlarmasCarpetas", results={
			@Result(location="/paginas/alarmasCarpetas.jsp",name="success")})
	public String execute(){
		return SUCCESS;
	}
}
