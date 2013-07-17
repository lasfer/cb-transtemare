package com.web.transtemare.acciones.commons;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Pais;
import com.core.transtemare.excepciones.FachadaException;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class JSONListaPaises extends ActionSupport {

	private Logger logger = Logger.getLogger(JSONListaPaises.class);
	private static final long serialVersionUID = 1L;
	private Fachada fac;
	private ArrayList<String> lista;
	private String term;

	
	
	public JSONListaPaises(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonListaPaises", results = { @Result(type = "json", name = "success", params = {
			"root", "lista" }) })
	public String execute() {

		ArrayList<Pais> paises = new ArrayList<Pais>();
		lista = new ArrayList<String>();
		try {
			fac.darTodosLosPaises(paises);
		} catch (FachadaException e) {
			logger.error(e.getMessage());
			return ERROR;
		}

		if (term != null && term.length() > 1) {
			for (int i = 0; i < paises.size(); i++) {
				if (StringUtils.contains(paises.get(i).getDescripcion()
						.toLowerCase(), term.toLowerCase())) {
					lista.add(paises.get(i).toString());
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
