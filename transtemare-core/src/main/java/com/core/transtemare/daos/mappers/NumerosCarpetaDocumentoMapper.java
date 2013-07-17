package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.NumerosCarpetaDocumento;

public class NumerosCarpetaDocumentoMapper implements
		RowMapper<NumerosCarpetaDocumento> {
	
	public NumerosCarpetaDocumento mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		NumerosCarpetaDocumento numeros = new NumerosCarpetaDocumento();
		numeros.setNroDocumento(rs.getString("numDocumento"));
		numeros.setNroCarpeta(rs.getInt("idCarpeta"));
		return numeros;
	}
}
