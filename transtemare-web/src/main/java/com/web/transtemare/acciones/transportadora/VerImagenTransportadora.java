package com.web.transtemare.acciones.transportadora;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Transportadora;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class VerImagenTransportadora extends ActionSupport implements ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(VerImagenTransportadora.class);

	private Integer id;
	private HttpServletResponse response;
	private Fachada fac;

	public VerImagenTransportadora(Fachada fac) {
		this.fac = fac;
	}

	@Action(value = "/verImagenTransportadora")
	public String execute() throws Exception {
		if (id == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "id requerido");
			return NONE;
		}
		Transportadora t = fac.obtenerTransportadoraPorId(id);
		if (t == null || t.getImagenLogo() == null || t.getImagenLogo().length == 0) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sin imagen");
			return NONE;
		}
		String contentType = LogoTransportadoraUtil.detectContentType(t.getImagenLogo());
		if (contentType == null) {
			response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Imagen no valida");
			return NONE;
		}
		response.setContentType(contentType);
		response.setHeader("X-Content-Type-Options", "nosniff");
		response.setContentLength(t.getImagenLogo().length);
		OutputStream out = response.getOutputStream();
		out.write(t.getImagenLogo());
		out.flush();
		return NONE;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}
}
