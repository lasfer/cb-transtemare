package com.web.transtemare.acciones.choferes;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class URLChoferes extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Action(value = "/urlChoferes", results={
			@Result(location="/paginas/choferes.jsp",name="success")})
	public String execute(){
		return SUCCESS;
	}
	
	
}
