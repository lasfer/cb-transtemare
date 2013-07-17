package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.Bulto;

public class BultoMapper implements RowMapper<Bulto> {

	public Bulto mapRow(ResultSet rs, int rowNum) throws SQLException {
		Bulto bulto = new Bulto();
		bulto.setCodBulto(rs.getInt("idBulto"));
		bulto.setDescripcion(rs.getString("descripcion"));
		return bulto;
	}
}
