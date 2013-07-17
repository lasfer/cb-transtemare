package com.core.transtemare.enums;

public enum TipoContenedor {

	SUELTA("SUELTA", 0), T20("20", 20), T40("40", 40);

	private int code;
	private String value;

	private TipoContenedor(String value, int code) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static TipoContenedor getByValue(String value) {
		for (TipoContenedor tipoContenedor : values()) {
			if (tipoContenedor.getValue().equals(value)) {
				return tipoContenedor;
			}
		}
		return null;
	}

	public static TipoContenedor getByCode(int code) {
		for (TipoContenedor tipoContenedor : values()) {
			if (tipoContenedor.getCode() == code) {
				return tipoContenedor;
			}
		}
		return null;
	}
}
