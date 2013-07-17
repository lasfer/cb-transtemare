package com.core.transtemare.entidades;


public class Terminal {

//	TCP("TCP", 1), MONTECON("Montecon", 2);
	
	private int id;
	private String nombre;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


//	private Terminal(String value, int code) {
//		this.code = code;
//		this.value = value;
//	}

//	public int getCode() {
//		return code;
//	}

//	public String getValue() {
//		return value;
//	}

//	public static Terminal getByValue(String value) {
//		for (Terminal terminal : values()) {
//			if (terminal.getValue().equals(value)) {
//				return terminal;
//			}
//		}
//		return null;
//	}

//	public static Terminal getByCode(int code) {
//		for (Terminal terminal : values()) {
//			if (terminal.getCode() == code) {
//				return terminal;
//			}
//		}
//		return null;
//	}

}
