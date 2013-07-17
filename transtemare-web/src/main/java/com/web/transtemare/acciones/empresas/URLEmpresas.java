package com.web.transtemare.acciones.empresas;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class URLEmpresas extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	@Action(value = "/urlEmpresas", results={
			@Result(location="/paginas/empresas.jsp",name="success")})
	public String execute(){
		return SUCCESS;
	}

}
