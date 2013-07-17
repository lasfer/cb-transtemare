package com.core.transtemare.entidades;

import java.util.Date;

public class Historico {
	
	private Integer idCarpeta;
	private Integer carpetaPadre;
	private Date fechaPasadoHistorico;
	private Date fechaAltaCarpeta;
	private Date fechaModificacion;
	private String xml;
	private String numeroContenedor;
	
	
	public Integer getIdCarpeta() {
		return idCarpeta;
	}
	public void setIdCarpeta(Integer idCarpeta) {
		this.idCarpeta = idCarpeta;
	}
	public Integer getCarpetaPadre() {
		return carpetaPadre;
	}
	public void setCarpetaPadre(Integer carpetaPadre) {
		this.carpetaPadre = carpetaPadre;
	}
	public Date getFechaPasadoHistorico() {
		return fechaPasadoHistorico;
	}
	public void setFechaPasadoHistorico(Date fechaPasadoHistorico) {
		this.fechaPasadoHistorico = fechaPasadoHistorico;
	}
	public Date getFechaAltaCarpeta() {
		return fechaAltaCarpeta;
	}
	public void setFechaAltaCarpeta(Date fechaAltaCarpeta) {
		this.fechaAltaCarpeta = fechaAltaCarpeta;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getNumeroContenedor() {
		return numeroContenedor;
	}
	public void setNumeroContenedor(String numeroContenedor) {
		this.numeroContenedor = numeroContenedor;
	}
	
	
	

}
