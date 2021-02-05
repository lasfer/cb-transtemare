package com.web.transtemare.acciones.commons;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;

import com.core.transtemare.commons.Fachada;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class JSONListaMonedas extends ActionSupport {

	private Logger logger = Logger.getLogger(JSONListaMonedas.class);
	private static final long serialVersionUID = 1L;
	private ArrayList<String> lista;
	private Fachada fac;
	
	
	
	public JSONListaMonedas(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonListaMonedas", results = { @Result(type = "json", name = "success") })
	public String execute() {
		logger.debug("execute");
		lista = new ArrayList<String>();
		lista.add("US$");
		lista.add("US");
		lista.add("EUR");
		return SUCCESS;
	}

	@JSON
	public ArrayList<String> getLista() {
		return lista;
	}

	public void setLista(ArrayList<String> lista) {
		this.lista = lista;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	public Fachada getFac() {
		return fac;
	}

}
