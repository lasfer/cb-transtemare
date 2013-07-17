package com.web.transtemare.acciones.terminales;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class ABMTerminales extends ActionSupport {

	private Logger logger = Logger.getLogger(ABMTerminales.class);
	private static final long serialVersionUID = 1L;
	private String oper = "";
	private String id;
	private String nombre;
	private Fachada fac;

	public ABMTerminales(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/ABMTerminales", results = { @Result(location = "/paginas/simpleecho.jsp") })
	public String execute() throws Exception {

		try {
			if (oper.equals("add") || oper.equals("edit")) {
				fac.crearModificarTerminal(id,nombre);
			} else if (oper.equals("del")) {
				fac.eliminarTerminal(Integer.valueOf(id));
			} else if (oper.equals("")) {
				return SUCCESS;
			}
		} catch (Exception e) {
			logger.error(ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE));
			return ERROR;
		}
		return SUCCESS;
	}

	public void validate() {
	}

	public void validateExecute() {
	}

	public void validateDoExecute() {
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
