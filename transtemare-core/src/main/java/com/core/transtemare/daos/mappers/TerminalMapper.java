package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.entidades.Terminal;

public class TerminalMapper implements RowMapper<Terminal> {

	
	public Terminal mapRow(ResultSet rs, int rowNum) throws SQLException {
		Terminal terminal = new Terminal();
		terminal.setId(rs.getInt("id"));
		terminal.setNombre(rs.getString("nombre"));
		return terminal;
	}
}
