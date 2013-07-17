package com.web.transtemare.acciones.commons;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class UsuariosActivos extends ActionSupport {

	private static final long serialVersionUID = 1864582964997301117L;

	@Action(value = "/usuariosActivos", results = { @Result(location = "/paginas/usuariosActivos.jsp", name = "success") })
	public String execute() throws Exception {
		return SUCCESS;
	}
}
