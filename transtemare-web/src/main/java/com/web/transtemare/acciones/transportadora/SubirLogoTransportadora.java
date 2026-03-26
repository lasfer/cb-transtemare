package com.web.transtemare.acciones.transportadora;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.excepciones.FachadaException;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class SubirLogoTransportadora extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SubirLogoTransportadora.class);

	private Integer id;
	private java.io.File logoFile;
	private String logoFileFileName;
	private String logoFileContentType;
	private Fachada fac;

	public SubirLogoTransportadora(Fachada fac) {
		this.fac = fac;
	}

	@Action(value = "/subirLogoTransportadora", results = {
			@Result(name = SUCCESS, type = "json", params = { "root", "resultado" }),
			@Result(name = ERROR, type = "json", params = { "root", "resultado" })
	})
	public String execute() throws Exception {
		if (id == null || logoFile == null || !logoFile.exists()) {
			addActionError("Faltan id o archivo de imagen.");
			return ERROR;
		}
		byte[] bytes = new byte[(int) logoFile.length()];
		try (InputStream in = new java.io.FileInputStream(logoFile)) {
			int n = 0;
			int len;
			while (n < bytes.length && (len = in.read(bytes, n, bytes.length - n)) != -1) {
				n += len;
			}
		}
		String contentType = logoFileContentType != null ? logoFileContentType : "image/png";
		if (!contentType.startsWith("image/")) {
			contentType = "image/png";
		}
		try {
			boolean ok = fac.actualizarLogoTransportadora(id, bytes, contentType);
			if (!ok) {
				addActionError("No se pudo actualizar el logo.");
				return ERROR;
			}
		} catch (FachadaException e) {
			logger.error("Error al subir logo transportadora " + id, e);
			addActionError(e.getMessage());
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

	public void setLogoFile(java.io.File logoFile) {
		this.logoFile = logoFile;
	}

	public void setLogoFileFileName(String logoFileFileName) {
		this.logoFileFileName = logoFileFileName;
	}

	public void setLogoFileContentType(String logoFileContentType) {
		this.logoFileContentType = logoFileContentType;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

	public Object getResultado() {
		return hasActionErrors() ? getActionErrors() : "OK";
	}
}
