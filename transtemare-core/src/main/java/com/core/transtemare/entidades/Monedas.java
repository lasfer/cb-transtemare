package com.core.transtemare.entidades;

public enum Monedas {
	
	US$(1),US(2),EUR(3);
	
	private int codigo;
	
	Monedas(int codigo)
	{
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}


	
	
}
