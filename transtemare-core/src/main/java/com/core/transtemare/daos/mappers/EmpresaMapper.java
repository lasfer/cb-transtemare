package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.Pais;

public class EmpresaMapper implements RowMapper<Empresa> {

	public Empresa mapRow(ResultSet rs, int rowNum) throws SQLException {
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(rs.getInt("idEmpresa"));
		empresa.setDireccion(rs.getString("Direccion"));
		empresa.setRolContribuyente(rs.getString("rolDelCon"));
		empresa.setNombre(rs.getString("nombre"));
		empresa.setNombreCorto(rs.getString("nombreCorto"));
		empresa.setCodigo(rs.getString("codigo"));
		empresa.setTipo(rs.getByte("tipo"));
		// Localidad
		Localidad localidad = new Localidad();
		Pais pais = new Pais();
		localidad.setIdLocalidad(rs.getInt("e.FK_Localidad"));
		localidad.setDescripcion(rs.getString("l.descripcion"));
		pais.setIdPais(rs.getInt("p.CodPais"));
		pais.setDescripcion(rs.getString("p.descripcion"));
		localidad.setPais(pais);
		empresa.setLocalidad(localidad);
		return empresa;
	}
}
