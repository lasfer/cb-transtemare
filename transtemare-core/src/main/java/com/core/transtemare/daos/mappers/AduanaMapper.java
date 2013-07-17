package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.Aduana;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.Pais;

public class AduanaMapper implements RowMapper<Aduana> {

	public Aduana mapRow(ResultSet rs, int rowNum) throws SQLException {
		Aduana aduana = new Aduana();
		this.mapear(rs, aduana);
		return aduana;
	}

	public Localidad mapear(ResultSet rs, Localidad localidad)
			throws SQLException {
		Pais pais = new Pais();
		localidad.setIdLocalidad(rs.getInt("CodLocalidad"));
		localidad.setDescripcion(rs.getString("Descripcion"));
		pais.setIdPais(rs.getInt("CodPais"));
		pais.setDescripcion(rs.getString("DescripcionPais"));
		localidad.setPais(pais);
		return localidad;
	}
}
