package com.core.transtemare.dto;

import java.util.Date;

public class AlarmasDTO {

	private Integer numeroCarpeta;   
	private Date fecVto;
	private Date fecAlta;
	private Date fecMod;
	private String nroContenedor;
	

	public AlarmasDTO(Integer nroCar,Date fecvto,Date fecAlta,Date fecMod,String nroCont){
		this.fecVto=fecvto;
		this.numeroCarpeta=nroCar;
		this.fecAlta= fecAlta;
		this.fecMod = fecMod;
		this.nroContenedor = nroCont;
		
		
	}
	
	public Date getFecAlta() {
		return fecAlta;
	}


	public void setFecAlta(Date fecAlta) {
		this.fecAlta = fecAlta;
	}


	public Date getFecMod() {
		return fecMod;
	}


	public void setFecMod(Date fecMod) {
		this.fecMod = fecMod;
	}


	public String getNroContenedor() {
		return nroContenedor;
	}


	public void setNroContenedor(String nroContenedor) {
		this.nroContenedor = nroContenedor;
	}


	
	


	public Integer getNumeroCarpeta() {
		return numeroCarpeta;
	}


	public void setNumeroCarpeta(Integer numeroCarpeta) {
		this.numeroCarpeta = numeroCarpeta;
	}


	public Date getFecVto() {
		return fecVto;
	}


	public void setFecVto(Date fecVto) {
		this.fecVto = fecVto;
	}


	
	
	
	
}
