package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.commons.Utils;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.entidades.Transportadora;
import com.core.transtemare.enums.EnumTipoGarantia;

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
		Transportadora transportadora = new Transportadora();
		transportadora.setNombreTransportadora(rs
				.getString("nombreTransportadora"));
		carpeta.setTrans(transportadora);
		carpeta.setContenedorDevuelto(rs.getBoolean("contenedorDevuelto"));
		carpeta.setTipoGarantia(EnumTipoGarantia.getByCode(rs
				.getByte("tipoGarantia")));
		carpeta.setImporteGarantia(rs.getBigDecimal("importeGarantia"));
		carpeta.setBancoGarantia(rs.getString("bancoGarantia"));
		carpeta.setNroChequeGarantia(rs.getString("nroChequeGarantia"));
		carpeta.setNroContenedor(Utils.formatearNroContenedor(rs.getString("nroContenedor")));		
		return carpeta;
	}
}
