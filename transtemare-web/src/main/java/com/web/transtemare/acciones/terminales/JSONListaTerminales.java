package com.web.transtemare.acciones.terminales;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Terminal;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class JSONListaTerminales extends ActionSupport {

	private Logger logger = Logger.getLogger(JSONListaTerminales.class);
	private static final long serialVersionUID = 1L;
	private List<Terminal> lista;
	private Fachada fac;

	public JSONListaTerminales(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonListaTerminales", results = { @Result(type = "json", name = "success") })
	public String execute() {
		logger.debug("execute");
		lista = new ArrayList<Terminal>();
		fac.obtenerTodasLasTerminales(lista);
		return SUCCESS;
	}

	public List<Terminal> getLista() {
		return lista;
	}

	public void setLista(List<Terminal> lista) {
		this.lista = lista;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	public Fachada getFac() {
		return fac;
	}

}
