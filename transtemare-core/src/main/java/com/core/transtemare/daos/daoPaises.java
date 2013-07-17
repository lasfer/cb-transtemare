package com.core.transtemare.daos;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.core.transtemare.daos.mappers.PaisMapper;
import com.core.transtemare.entidades.Pais;
import com.core.transtemare.persistencia.sql.SQLComunes;

public class daoPaises {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(daoPaises.class);

	private DataSourceTransactionManager txManager;
	private JdbcTemplate jdbcTemplate;

	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setTransactionManager(DataSourceTransactionManager=" + transactionManager + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		this.txManager = transactionManager;
		DataSource dataSource = txManager.getDataSource();
		jdbcTemplate = new JdbcTemplate(dataSource);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setTransactionManager(DataSourceTransactionManager) - end"); //$NON-NLS-1$
		}
	}

	public daoPaises() {
	}

	public void getTodosLosPaises(List<Pais> paises) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodosLosPaises(List<Pais>=" + paises + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Pais> paisesBase = jdbcTemplate.query(
				SQLComunes.LISTAR_TODOS_PAISES, new PaisMapper());
		paises.addAll(paisesBase);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodosLosPaises(List<Pais>) - end"); //$NON-NLS-1$
		}
	}

	public void getTodosLosPaisesHash(HashMap<String, String> paises) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodosLosPaisesHash(HashMap<String,String>=" + paises + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Pais> paisesBase = jdbcTemplate.query(
				SQLComunes.LISTAR_TODOS_PAISES, new PaisMapper());
		for (Pais pais : paisesBase) {
			paises.put(String.valueOf(pais.getIdPais()), pais.getDescripcion());
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodosLosPaisesHash(HashMap<String,String>) - end"); //$NON-NLS-1$
		}
	}

	public int obtenerPaisPorNombre(String nombrePais) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerPaisPorNombre(String=" + nombrePais + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		int returnint = this.jdbcTemplate.queryForInt(SQLComunes.OBTENER_COD_PAIS_POR_NOMBRE, new Object[] { nombrePais });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerPaisPorNombre(String) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}
}
