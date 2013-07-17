package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.commons.Utils;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.entidades.Transportadora;

public class CarpetaSnapMapper implements RowMapper<Carpeta> {

	public Carpeta mapRow(ResultSet rs, int rowNum) throws SQLException {
		Carpeta carpeta = new Carpeta();
		carpeta.setIdCarpeta(rs.getInt("idCarpeta"));
		carpeta.setReferenciaDestino(rs.getString("referenciaDestino"));
		carpeta.setNroContenedor(rs.getString("nroContenedor"));
		Transportadora t = new Transportadora();
		t.setNombreTransportadora(rs.getString("nombreTransportadora"));
		carpeta.setTrans(t);
//		carpeta.setTerminal(Terminal.getByCode(rs.getInt("terminal")));
		Empresa agenciaMaritima = new Empresa();
		agenciaMaritima.setNombre(rs.getString("nombreAgenciaMaritima"));
		carpeta.setAgenciaMaritima(agenciaMaritima);
		carpeta.setFechaLlegadaBuque(Utils.convertToUtilDate(rs
				.getTimestamp("fechaLlegadaBuque")));
		carpeta.setFechaAlta(Utils.convertToUtilDate(rs.getTimestamp("FxAlta")));
		carpeta.setFechaModificacion(Utils.convertToUtilDate(rs
				.getTimestamp("FxMod")));
		carpeta.setFechaVencimiento(Utils.convertToUtilDate(rs
				.getTimestamp("fechaVencimiento")));
		return carpeta;
	}
}
