package com.core.transtemare.entidades;

import com.core.transtemare.commons.Utils;

public class Aduana extends Localidad {

	public Aduana(int idAduana) {
		super(idAduana);
	}

	public Aduana() {
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getIdLocalidad() > 0) {
			sb.append(getIdLocalidad());
			sb.append(Utils.ID);
			sb.append(getDescripcion());
			return sb.toString();
		}
		return "";
	}
}
