package com.web.transtemare.acciones.carpetas;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class CrearCarpetaPaso1 extends ActionSupport {
	
	
	
	private static final long serialVersionUID = 1L;

	@Action(value = "/CrearCarpetaPaso1", results = { @Result(location = "/paginas/CrearCarpetaPaso1.jsp") })
	public String execute(){
		return SUCCESS;
	}
	
	

}
