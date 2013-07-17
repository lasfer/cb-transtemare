package com.web.transtemare.acciones.historico;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class URLCarpetasHistorico extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Actions({@Action(value = "/urlCarpetasHistorico", results={
			@Result(location="/paginas/carpetasHistorico.jsp")}),
   			  @Action(value = "/urlTab1", results={
			@Result(location="/paginas/tab-1.jsp")})}
			
			)
	public String execute(){
		return SUCCESS;
	}
}
