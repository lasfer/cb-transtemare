package com.web.transtemare.acciones.transportadora;

import java.io.ByteArrayOutputStream;
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
		if (logoFile.length() > LogoTransportadoraUtil.MAX_LOGO_BYTES) {
			addActionError("El logo supera el tamano maximo permitido.");
			return ERROR;
		}
		byte[] bytes;
		try (InputStream in = new java.io.FileInputStream(logoFile)) {
			ByteArrayOutputStream out = new ByteArrayOutputStream((int) logoFile.length());
			byte[] buffer = new byte[8192];
			int len;
			while ((len = in.read(buffer)) != -1) {
				if (out.size() + len > LogoTransportadoraUtil.MAX_LOGO_BYTES) {
					addActionError("El logo supera el tamano maximo permitido.");
					return ERROR;
				}
				out.write(buffer, 0, len);
			}
			bytes = out.toByteArray();
		}
		String contentType = LogoTransportadoraUtil.detectContentType(bytes);
		if (contentType == null) {
			addActionError("Formato de logo no permitido.");
			return ERROR;
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
