package com.web.transtemare.acciones.commons;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;

import com.core.transtemare.commons.Fachada;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class JSONListaTamaniosLetra extends ActionSupport{

	private Logger logger = Logger.getLogger(JSONListaTamaniosLetra.class);
	private static final long serialVersionUID = 1L;
	private HashMap<String,String>lista;
	private Fachada fac;
	
	

	 public JSONListaTamaniosLetra(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonListaTamaniosLetra", results = {
				@Result(type = "json",name = "success")})
	 public String execute()  {
		 logger.debug("execute");
		 lista = new HashMap<String, String>();
		 lista.put("6","Muy Pequeño");
		 lista.put("7","Pequeño");
		 lista.put("8","Mediano");
		 lista.put("9","Normal");
		 
		 return SUCCESS;
		}
	 
	 @JSON
	public HashMap<String, String> getLista() {
		return lista;
	}

	public void setLista(HashMap<String, String> lista) {
		this.lista = lista;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	public Fachada getFac() {
		return fac;
	}

	 
	 
	 
	
}
