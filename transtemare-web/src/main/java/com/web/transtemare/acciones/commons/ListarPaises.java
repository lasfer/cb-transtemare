package com.web.transtemare.acciones.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Pais;
import com.core.transtemare.excepciones.FachadaException;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class ListarPaises extends ActionSupport{
	
	private Logger logger = Logger.getLogger(ListarPaises.class);
	private static final long serialVersionUID = 1L;
	private List<String> paises;
	private Fachada fac;
	
	
	
	public ListarPaises(Fachada fac) {
		super();
		this.fac = fac;
	}


	@Action(value = "/lp", results={
			@Result(location="/paginas/paises.jsp",name="success")})
	public String execute(){
		paises = new ArrayList<String>();
		ArrayList<Pais>p = new ArrayList<Pais>();
		try {
			
			fac.darTodosLosPaises(p);
			for (Pais pais : p) {
				paises.add(pais.getDescripcion());
			}
		} catch (FachadaException e) {
			logger.error(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	
	public List<String> getPaises() {
		return paises;
	}

	public void setPaises(List<String> paises) {
		this.paises = paises;
	}


	public Fachada getFac() {
		return fac;
	}


	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	
	
	

}
