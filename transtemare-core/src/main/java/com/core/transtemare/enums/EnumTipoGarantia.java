package com.core.transtemare.enums;

public enum EnumTipoGarantia {

	CHEQUE("Cheque", 1), EFECTIVO("Efectivo", 2), VALE("Vale", 3);

	private int code;
	private String value;

	private EnumTipoGarantia(String value, int code) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static EnumTipoGarantia getByValue(String value) {
		for (EnumTipoGarantia garantia : values()) {
			if (garantia.getValue().equals(value)) {
				return garantia;
			}
		}
		return null;
	}

	public static EnumTipoGarantia getByCode(int code) {
		for (EnumTipoGarantia garamtia : values()) {
			if (garamtia.getCode() == code) {
				return garamtia;
			}
		}
		return null;
	}
	
	public static EnumTipoGarantia getByName(String value) {
		for (EnumTipoGarantia garantia : values()) {
			if (garantia.name().equals(value)) {
				return garantia;
			}
		}
		return null;
	}
}
