package com.web.transtemare.acciones.terminales;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class URLTerminales extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	@Action(value = "/urlTerminales", results={
			@Result(location="/paginas/terminales.jsp",name="success")})
	public String execute(){
		return SUCCESS;
	}

}
