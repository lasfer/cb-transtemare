package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.Pais;

public class LocalidadMapper implements RowMapper<Localidad> {

	
	public Localidad mapRow(ResultSet rs, int rowNum) throws SQLException {
		Localidad localidad = new Localidad();
		this.mapear(rs, localidad);
		return localidad;
	}

	public Localidad mapear(ResultSet rs, Localidad localidad)
			throws SQLException {
		Pais pais = new Pais();
		localidad.setIdLocalidad(rs.getInt("CodLocalidad"));
		localidad.setDescripcion(rs.getString("Descripcion"));
		localidad.setAduana(rs.getBoolean("esAduana"));
		pais.setIdPais(rs.getInt("CodPais"));
		pais.setDescripcion(rs.getString("DescripcionPais"));
		pais.setCodigo(rs.getString("codigo"));
		localidad.setPais(pais);
		return localidad;
	}
}
