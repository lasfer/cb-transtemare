package com.web.transtemare.acciones.empresas;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.entidades.Localidad;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class ABMEmpresas extends ActionSupport {

	private Logger logger = Logger.getLogger(ABMEmpresas.class);
	private static final long serialVersionUID = 1L;
	private String oper = "";
	private String id;
	private String nombre;
	private String nombreCorto;
	private String domicilio;
	private String rol;
	private String tipo;
	private String pais;
	private String ciudad;
	private Fachada fac;

	public ABMEmpresas(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/ABMEmpresas", results = { @Result(location = "/paginas/simpleecho.jsp") })
	public String execute() throws Exception {

		try {
			if (oper.equals("add")) {
				fac.altaEmpresa(buildEmpresa());
			} else if (oper.equals("del")) {
				fac.borrarEmpresa(Integer.valueOf(id));
			} else if (oper.equals("edit")) {
				fac.modificarEmpresa(buildEmpresa());
			} else if (oper.equals("")) {
				return SUCCESS;
			}
		} catch (Exception e) {
			logger.error("id:" + id + "nombre: " + nombre + ", domicilio:"
					+ domicilio + ", rol:" + rol + "," + " pais:" + pais
					+ ", ciudad:" + ciudad + ", tipo_" + tipo
					+ " nombreCorto :" + nombreCorto + " oper :" + oper, e);
			logger.error(ToStringBuilder.reflectionToString(this,
					ToStringStyle.SHORT_PREFIX_STYLE));
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

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	public Empresa buildEmpresa() {
		int idEmpresa = id.equals("_empty") ? 0 : Integer.parseInt(id);
		Localidad localidad = new Localidad(0, null, ciudad, false);
		return new Empresa(idEmpresa, this.obtenerTipo(), nombre, rol,
				domicilio, localidad, nombreCorto);
	}

	private byte obtenerTipo() {
		byte tipo_ = 0;
		if ("Empresa".equals(tipo)) {
			tipo_ = 0;
		} else if ("Cliente".equals(tipo)) {
			tipo_ = 1;
		} else if ("Despachante".equals(tipo)) {
			tipo_ = 2;
		} else if ("Agencia Maritima".equals(tipo)) {
			tipo_ = 3;
		}
		return tipo_;
	}
}
