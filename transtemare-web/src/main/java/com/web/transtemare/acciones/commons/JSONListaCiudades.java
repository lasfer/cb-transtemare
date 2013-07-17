package com.web.transtemare.acciones.commons;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Localidad;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class JSONListaCiudades extends ActionSupport {

	private Logger logger = Logger.getLogger(JSONListaCiudades.class);
	private static final long serialVersionUID = 1L;
	private Fachada fac;
	private ArrayList<String> lista;
	private String term;
	
	

	public JSONListaCiudades(Fachada fac) {
		this.fac = fac;
	}

	@Action(value = "/jsonListaCiudades", results = { @Result(type = "json", name = "success", params = {
			"root", "lista" }) })
	public String execute() {

		ArrayList<Localidad> localidades = new ArrayList<Localidad>();
		lista = new ArrayList<String>();
		try {
			fac.darTodasLasLocalidades(localidades);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ERROR;
		}

		if (term != null && term.length() > 1) {
			for (int i = 0; i < localidades.size(); i++) {
				if (StringUtils.contains(localidades.get(i).getDescripcion()
						.toLowerCase(), term.toLowerCase())) {
					lista.add(localidades.get(i).toString());
				}
			
			}
		}
		return SUCCESS;
	}

	public ArrayList<String> getLista() {
		return lista;
	}

	public void setLista(ArrayList<String> lista) {
		this.lista = lista;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}
	
	
	

}
