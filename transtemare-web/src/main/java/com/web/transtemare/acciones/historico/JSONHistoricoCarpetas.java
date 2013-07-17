package com.web.transtemare.acciones.historico;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.commons.Globales;
import com.core.transtemare.entidades.Carpeta;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

@ParentPackage(value = "default")
public class JSONHistoricoCarpetas extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(JSONHistoricoCarpetas.class);
	private Integer id;
	private Fachada fac;
	private String echo;

	public JSONHistoricoCarpetas(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/pasarCarpetaHistorico", results = { @Result(location = "/paginas/simpleecho.jsp") })
	public String execute() {
		try {
			// fac.pasarAHistorico(id);
			setEcho("La carpeta numero:" + id + " se paso a historico");
			Carpeta carpeta = fac.obtenerCarpeta(id);
			XStream xstream = new XStream();
			String xml = xstream.toXML(carpeta);
			fac.pasarHistoricoXML(carpeta, xml);
			
			
			List<Carpeta> carpetasRelacionadas=fac.obtenerSubCarpetas(false, id);
			for (Carpeta carpeta2 : carpetasRelacionadas) {
				carpeta2=fac.obtenerCarpeta(carpeta2.getIdCarpeta());
				xml = xstream.toXML(carpeta2);
				fac.pasarHistoricoXML(carpeta2, xml);
			}
			logger.debug("Carpeta pasado a xml " + xml);

		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(Globales.ERROR_AL_PASAR_A_HISTORICO);
			setEcho("Hubo un error y no se paso a historico");
		}
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setEcho(String echo) {
		this.echo = echo;
	}

	public String getEcho() {
		return echo;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

}
