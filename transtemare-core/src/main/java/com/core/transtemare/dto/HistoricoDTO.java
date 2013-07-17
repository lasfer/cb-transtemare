package com.core.transtemare.dto;

import java.util.Date;

public class HistoricoDTO {
	
	
	private String idCarpeta;
	private String numeroContenedor;
	private Date fechaModificacion;
	private Date fechaCreacion;
	private Date fechaPasadoHistorico;
	
	
	public HistoricoDTO(Integer idCarpeta2, Integer carpetaPadre,Date fechaAltaCarpeta, Date fechaModificacion2,Date fechaPasadoHistorico2, String numeroContenedor2) {
		setIdCarpeta(String.valueOf(idCarpeta2));
		setNumeroContenedor(String.valueOf(carpetaPadre));
		setFechaCreacion(fechaAltaCarpeta);
		setFechaModificacion(fechaModificacion2);
		setFechaPasadoHistorico(fechaPasadoHistorico2);
		
		
	}
	
	
	public String getIdCarpeta() {
		return idCarpeta;
	}
	public void setIdCarpeta(String idCarpeta) {
		this.idCarpeta = idCarpeta;
	}
	public String getNumeroContenedor() {
		return numeroContenedor;
	}
	public void setNumeroContenedor(String numeroContenedor) {
		this.numeroContenedor = numeroContenedor;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaPasadoHistorico() {
		return fechaPasadoHistorico;
	}
	public void setFechaPasadoHistorico(Date fechaPasadoHistorico) {
		this.fechaPasadoHistorico = fechaPasadoHistorico;
	}
	
	
	
	
	
	
	
	

}
