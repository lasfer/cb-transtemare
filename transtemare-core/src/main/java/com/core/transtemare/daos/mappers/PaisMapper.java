package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.Pais;

public class PaisMapper implements RowMapper<Pais> {


	public Pais mapRow(ResultSet rs, int rowNum) throws SQLException {
		Pais pais = new Pais();
		pais.setIdPais(rs.getInt("CodPais"));
		pais.setDescripcion(rs.getString("Descripcion"));
		pais.setCodigo(rs.getString("codigo"));
		return pais;
	}
}
