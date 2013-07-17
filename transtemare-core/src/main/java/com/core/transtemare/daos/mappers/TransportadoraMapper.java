package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.Pais;
import com.core.transtemare.entidades.Transportadora;

public class TransportadoraMapper implements RowMapper<Transportadora> {


	public Transportadora mapRow(ResultSet rs, int rowNum) throws SQLException {
		Transportadora t = new Transportadora();
		Localidad l = new Localidad();
		Pais p = new Pais();

		t.setIdTransportadora(rs.getInt("t.CodTransportadora"));
		t.setNombreTransportadora(rs.getString("t.Nombre"));
		t.setRolDelContribuyente(rs.getString("t.RolContribuyente"));
		t.setDomicilio(rs.getString("t.Domicilio"));
		t.setPrefijo(rs.getString("t.Prefijo"));
		t.setNumerador(rs.getInt("numero"));
		t.setNombreArchivo(rs.getString("t.imagen"));
		l.setIdLocalidad(rs.getInt("t.CodLocallidad_FK"));
		l.setDescripcion(rs.getString("p.Descripcion"));
		p.setIdPais(rs.getInt("p.CodPais"));
		p.setDescripcion(rs.getString("l.Descripcion"));
		l.setPais(p);
		t.setLocalidad(l);

		return t;
	}

}
