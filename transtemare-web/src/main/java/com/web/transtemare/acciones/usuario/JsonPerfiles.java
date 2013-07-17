package com.web.transtemare.acciones.usuario;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.opensymphony.xwork2.ActionSupport;


@ParentPackage(value="default")
public class JsonPerfiles extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Fachada fac;
	private ArrayList<String> lista;
	private String term;
	
	

	public JsonPerfiles(Fachada fac) {
		this.fac = fac;
	}

	@Action(value = "/jsonListaPerfiles", results = { @Result(type = "json", name = "success", params = {
			"root", "lista" }) })
	public String execute() {
//
//		ArrayList<Profile> localidades = new ArrayList<Localidad>();
//		lista = new ArrayList<String>();
//		try {
//			fac.darTodasLasLocalidades(localidades);
//		} catch (FachadaException e) {
//
//		}
//
//		if (term != null && term.length() > 1) {
//			for (int i = 0; i < localidades.size(); i++) {
//				if (StringUtils.contains(localidades.get(i).getDescripcion()
//						.toLowerCase(), term.toLowerCase())) {
//					lista.add(localidades.get(i).toString());
//				}
//			
//			}
//		}
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
