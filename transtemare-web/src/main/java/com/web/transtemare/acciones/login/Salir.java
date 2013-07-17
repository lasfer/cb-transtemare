package com.web.transtemare.acciones.login;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Salir extends ActionSupport{

	
	private static final long serialVersionUID = 1L;
	

	public String execute(){
		@SuppressWarnings("rawtypes")
		Map attributes = ActionContext.getContext().getSession();
		String loged = (String) attributes.get("loged");
		String usuario = (String) attributes.get("usuario");
		if(loged != null){
			attributes.remove("loged");
		}
		if(usuario != null){
			attributes.remove("usuario");
		}
		return SUCCESS;
	}
}
