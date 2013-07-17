package com.core.transtemare.entidades;

public class Remolque 
{

	public Remolque(int idRemolque) {
		super();
		this.idRemolque = idRemolque;
	}


	private int idRemolque;
	private String matricula;
	
	
	public Remolque()
	{
		
	}


	public int getIdRemolque() {
		return idRemolque;
	}


	public void setIdRemolque(int idRemolque) {
		this.idRemolque = idRemolque;
	}


	public String getMatricula() {
		return matricula;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	
	
	
}
