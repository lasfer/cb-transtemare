package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.Pais;
import com.core.transtemare.entidades.Responsable;

public class ResponsableMapper implements RowMapper<Responsable> {

	public Responsable mapRow(ResultSet rs, int rowNum) throws SQLException {
		Responsable responsable = new Responsable();
		Pais pais = new Pais();
		Localidad localidad = new Localidad();
		localidad.setDescripcion(rs.getString("ciudad"));
		pais.setDescripcion(rs.getString("pais"));
		localidad.setPais(pais);
		responsable.setLocalidad(localidad);
		responsable.setIdResponsable(rs.getInt("CodResponsable"));
		responsable.setNombre(rs.getString("Nombre"));
		responsable.setApellido(rs.getString("Apellido"));
		responsable.setDocumento(rs.getString("Documento"));
		responsable.setTelefono(rs.getString("telefono"));
		return responsable;
	}
}
