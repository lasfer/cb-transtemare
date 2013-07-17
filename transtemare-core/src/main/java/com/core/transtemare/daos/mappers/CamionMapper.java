package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.Camion;

public class CamionMapper implements RowMapper<Camion> {

	public Camion mapRow(ResultSet rs, int rowNum) throws SQLException {
		Camion camion = new Camion();
		camion.setIdCamion(rs.getInt("idCamion"));
		camion.setCapacidad(rs.getString("capacidad"));
		camion.setMatricula(rs.getString("MatriculaCamion"));
		camion.setMarca(rs.getString("marca"));
		camion.setAnio(rs.getString("anio"));
		camion.setTipo(rs.getString("tipo"));
		camion.setNumeroPoliza(rs.getString("numeroPoliza"));
		camion.setVencimientoPoliza(rs.getDate("vencimientoPoliza"));
		return camion;
	}
}