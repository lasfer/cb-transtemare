package com.web.transtemare.acciones.carpetas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.commons.Globales;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.excepciones.ActionException;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class JSONCantidadMic extends ActionSupport {

	private static final Logger logger = Logger
			.getLogger(JSONCantidadMic.class);
	private static final long serialVersionUID = 1L;
	private Integer id;
	private List<String> mics;
	private Fachada fac;
	private Map<String, String> map;

	public JSONCantidadMic(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonMics", results = { @Result(name = "success", location = "/paginas/mics.jsp") })
	public String execute() throws ActionException {

		try {
			Carpeta c = fac.obtenerCarpeta(id);
			mics = new ArrayList<String>();
			map = new TreeMap<String, String>();
			int nroDocumento = c.getNroDocumento();
			for (int i = 0; i < c.getCantidadMic(); i++, nroDocumento++) {
				map.put(String
						.valueOf(c.getTrans().getPrefijo() + nroDocumento),
						String.valueOf(c.getIdCarpeta() + i));
			}

		} catch (Exception e) {
			logger.error(Globales.ERROR_EDITAR_CARPETA, e);
			return ERROR;
		}
		return SUCCESS;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getMics() {
		return mics;
	}

	public void setMics(List<String> mics) {
		this.mics = mics;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Map<String, String> getMap() {
		return map;
	}

}