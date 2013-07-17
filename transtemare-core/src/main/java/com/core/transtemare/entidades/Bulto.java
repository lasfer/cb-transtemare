package com.core.transtemare.entidades;

import com.core.transtemare.commons.Utils;


public class Bulto 
{
	
	private int codBulto;
	private String Descripcion;
	
	public Bulto(){}
	

	public Bulto(int codBulto) {
		super();
		this.codBulto = codBulto;
	}


	public int getCodBulto() {
		return codBulto;
	}

	public void setCodBulto(int codBulto) {
		this.codBulto = codBulto;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(getCodBulto() > 0){
		sb.append(getCodBulto());
		sb.append(Utils.ID);
		sb.append(" Tipo: ");
		sb.append(getDescripcion());
		}
		return sb.toString();
	}
	
	

}
