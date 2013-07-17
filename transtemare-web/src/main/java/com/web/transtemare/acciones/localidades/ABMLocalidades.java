package com.web.transtemare.acciones.localidades;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.Pais;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class ABMLocalidades extends ActionSupport {
	private Logger logger = Logger.getLogger(ABMLocalidades.class);
	private String oper = "";
	private String id;
	private Pais pais;
	private String descripcion;
	private Boolean aduana;

	private Fachada fachada;

	public ABMLocalidades(Fachada fachada) {
		this.fachada = fachada;
	}

	public Fachada getFachada() {
		return fachada;
	}

	public void setFachada(Fachada fachada) {
		this.fachada = fachada;
	}

	@Action(value = "/ABMLocalidades", results = { @Result(location = "/paginas/simpleecho.jsp") })
	public String execute() {
		logger.debug("Entro al excecute");
		try {
			if (oper.equals("add")) {
				logger.debug("Entro al add");
				fachada.altaLocalidad(buildLocalidad());

			} else if (oper.equals("del")) {
				logger.debug("Entro al del");
				fachada.borrarLocalidad(Integer.parseInt(id));

			} else if (oper.equals("edit")) {
				logger.debug("Entro al edit");
				fachada.modificarLocalidad(buildLocalidad());
			} else if (oper.equals("")) {
				return SUCCESS;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8422017680192948407L;

	/**
	 * @return the pais
	 */
	public Pais getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the aduana
	 */
	public Boolean getAduana() {
		return aduana;
	}

	/**
	 * @param aduana
	 *            the aduana to set
	 */
	public void setAduana(Boolean aduana) {
		this.aduana = aduana;
	}

	/**
	 * @return the oper
	 */
	public String getOper() {
		return oper;
	}

	/**
	 * @param oper
	 *            the oper to set
	 */
	public void setOper(String oper) {
		this.oper = oper;
	}

	public Localidad buildLocalidad() {
		if (this.pais != null) {
			ArrayList<Pais> paises = new ArrayList<Pais>();
			fachada.darTodosLosPaises(paises);
			for (Pais pais : paises) {
				if (this.pais.getDescripcion().equals(pais.getDescripcion())) {
					this.pais.setIdPais(pais.getIdPais());
				}
			}
		} else {
			this.pais = new Pais();
			pais.setIdPais(0);
		}
		return new Localidad(id.equals("_empty") ? 0 : Integer.parseInt(id),
				pais, descripcion, aduana);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
