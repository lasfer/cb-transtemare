package com.web.transtemare.acciones.commons;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.enums.TipoContenedor;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class JSONTipoContenedores extends ActionSupport {

	private Logger logger = Logger.getLogger(JSONTipoContenedores.class);
	private static final long serialVersionUID = 1L;
	private ArrayList<String> lista;
	private Fachada fac;

	public JSONTipoContenedores(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonTipoContenedores", results = { @Result(type = "json", name = "success") })
	public String execute() {
		logger.debug("execute");
		lista = new ArrayList<String>();

		for (TipoContenedor tipoContenedor : TipoContenedor.values()) {
			lista.add(tipoContenedor.getValue());
		}

		return SUCCESS;
	}

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
