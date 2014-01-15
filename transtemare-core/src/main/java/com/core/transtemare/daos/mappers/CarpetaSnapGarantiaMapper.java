package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Empresa;

public class CarpetaSnapGarantiaMapper implements RowMapper<Carpeta> {

	public Carpeta mapRow(ResultSet rs, int rowNum) throws SQLException {
		Carpeta carpeta = new Carpeta();
		carpeta.setIdCarpeta(rs.getInt("idCarpeta"));
		Empresa agenciaMaritima = new Empresa();
		agenciaMaritima.setNombre(rs.getString("nombreAgenciaMaritima"));
		carpeta.setAgenciaMaritima(agenciaMaritima);
		Empresa despachante = new Empresa();
		despachante.setNombre(rs.getString("nombreDespachante"));
		carpeta.setDespachante((despachante));
		return carpeta;
	}
}
