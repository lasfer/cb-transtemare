package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.commons.Utils;
import com.core.transtemare.entidades.Historico;

public class CarpetaHistoricoMapper implements RowMapper<Historico> {

	public Historico mapRow(ResultSet rs, int rowNum) throws SQLException {
		Historico historico = new Historico();
		historico.setIdCarpeta(rs.getInt("idCarpeta"));
		historico.setCarpetaPadre(rs.getInt("carpetaPadre"));
		historico.setFechaAltaCarpeta(Utils.convertToUtilDate(rs
				.getDate("fechaAlta")));
		historico.setFechaModificacion(Utils.convertToUtilDate(rs
				.getDate("fechaModificacion")));
		historico.setFechaPasadoHistorico(Utils.convertToUtilDate(rs
				.getDate("fechaPasado")));
		historico.setXml(rs.getString("xml"));
		historico.setNumeroContenedor(rs.getString("numeroContenedor"));
		return historico;
	}
}
