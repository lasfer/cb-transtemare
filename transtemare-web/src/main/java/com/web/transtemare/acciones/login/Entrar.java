
package com.web.transtemare.acciones.login;

import java.util.Map;

import org.apache.log4j.Logger;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Entrar extends ActionSupport {

	private Logger logger = Logger.getLogger(Entrar.class);
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String contrasena;
	private Fachada fac;
	
	
	
	public Entrar(Fachada fac) {
		super();
		this.fac = fac;
	}

	@SuppressWarnings("unchecked")
	public String execute(){
		logger.debug("Ejecutando Login");
		User user = fac.obtenerUsuarioRegistrado(usuario,contrasena);
		@SuppressWarnings("rawtypes")
		Map attibutes = ActionContext.getContext().getSession();
		String mensaje = (String) attibutes.get("mensajeDeLogin");
		if("Usuario o Contrasena incorrecta".equals(mensaje)){
			attibutes.remove("mensajeDeLogin");
		}
		
		if(user != null){
			attibutes.put("loged", "logueado");
			attibutes.put("usuario",user.getUser());
			return SUCCESS;
		}else {
			attibutes.put("mensajeDeLogin", "Usuario o Contrasena incorrecta");
			return SUCCESS;
		}
		
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}
	
	
	


	
}
