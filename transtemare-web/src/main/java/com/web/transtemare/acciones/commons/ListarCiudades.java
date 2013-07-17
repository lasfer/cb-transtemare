package com.web.transtemare.acciones.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.excepciones.FachadaException;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class ListarCiudades extends ActionSupport {

	private Logger logger = Logger.getLogger(ListarCiudades.class);
	private static final long serialVersionUID = 1L;
	private List<String> localidad;
	private Fachada fac;
	
	
	
	
	public ListarCiudades(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/lc", results={
			@Result(location="/paginas/ciudades.jsp",name="success")})
	public String execute(){
		localidad = new ArrayList<String>();
		ArrayList<Localidad>loc = new ArrayList<Localidad>();
		try {
			
			fac.darTodasLasLocalidades(loc);
			for (Localidad l : loc) {
				localidad.add(l.getDescripcion());
			}
		} catch (FachadaException e) {
			logger.error(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public List<String> getLocalidad() {
		return localidad;
	}

	public void setLocalidad(List<String> localidad) {
		this.localidad = localidad;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}
	
	
	
	
	

	
}
