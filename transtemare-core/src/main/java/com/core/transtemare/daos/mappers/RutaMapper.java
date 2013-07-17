package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.Ruta;

public class RutaMapper implements RowMapper<Ruta> {


	public Ruta mapRow(ResultSet rs, int rowNum) throws SQLException {
		Ruta ruta = new Ruta();
		ruta.setIdRuta(rs.getInt("CodRuta"));
		ruta.setDescripcion(rs.getString("Descripcion"));
		ruta.setDescripcionCorta(rs.getString("DescripcionCorta"));
		return ruta;
	}
}