package com.web.transtemare.acciones.camiones;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Camion;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class ABMCamiones extends ActionSupport {

	private Logger logger = Logger.getLogger(ABMCamiones.class);
	private static final long serialVersionUID = 1L;
	private String oper = "";
	private String id;
	private String matricula;
	private String capacidad;
	private String marca;
	private String anio;
	private String tipo;
	private String numeroPoliza;
	private Date vencimientoPoliza;
	private Fachada fac;

	public ABMCamiones(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/ABMCamiones", results = { @Result(location = "/paginas/simpleecho.jsp") })
	public String execute() {

		logger.debug("Entro al excecute");

		try {

			if (oper.equals("add")) {
				logger.debug("Entro al add");
				fac.altaCamion(buildCamion());
			} else if (oper.equals("del")) {
				logger.debug("Entro al del");
				fac.borrarCamion(Integer.parseInt(id));
			} else if (oper.equals("edit")) {
				logger.debug("Entro al edit");
				fac.modificarCamion(buildCamion());
			} else if (oper.equals("")) {
				return SUCCESS;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}

		return SUCCESS;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	/**
	 * @return the numeroPoliza
	 */
	public String getNumeroPoliza() {
		return numeroPoliza;
	}

	/**
	 * @param numeroPoliza
	 *            the numeroPoliza to set
	 */
	public void setNumeroPoliza(String numeroPoliza) {
		this.numeroPoliza = numeroPoliza;
	}

	/**
	 * @return the vencimientoPoliza
	 */
	public Date getVencimientoPoliza() {
		return vencimientoPoliza;
	}

	/**
	 * @param vencimientoPoliza
	 *            the vencimientoPoliza to set
	 */
	public void setVencimientoPoliza(Date vencimientoPoliza) {
		this.vencimientoPoliza = vencimientoPoliza;
	}

	public Camion buildCamion() {
		Integer idCamion = id.equals("_empty") ? 0 : Integer.valueOf(id);
		return new Camion(idCamion, matricula, capacidad, marca, anio, tipo,
				numeroPoliza, vencimientoPoliza);

	}

}
