package com.core.transtemare.daos;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.core.transtemare.daos.mappers.RutaMapper;
import com.core.transtemare.entidades.Ruta;
import com.core.transtemare.persistencia.sql.SQLComunes;

public class daoRutas {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(daoRutas.class);

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

	public daoRutas() {
	};

	public void obtenerTodasLasRutas(Map<String, String> rutas)
			 {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasRutas(Map<String,String>=" + rutas + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Ruta> rutasBase = jdbcTemplate.query(
				SQLComunes.OBTENER_TODAS_TODAS_LAS_RUTAS, new RutaMapper());
		for (Ruta ruta : rutasBase) {
			rutas.put(String.valueOf(ruta.getIdRuta()),
					ruta.getDescripcionCorta());
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasRutas(Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

	public void obtenerTodasLasRutas(List<Ruta> rutas)  {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasRutas(List<Ruta>=" + rutas + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Ruta> rutasBase = jdbcTemplate.query(
				SQLComunes.OBTENER_TODAS_TODAS_LAS_RUTAS, new RutaMapper());
		rutas.addAll(rutasBase);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasRutas(List<Ruta>) - end"); //$NON-NLS-1$
		}
	}

	public Ruta obteneRruta(int codRuta)  {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obteneRruta(int=" + codRuta + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Ruta ruta = this.jdbcTemplate.queryForObject(SQLComunes.OTENER_RUTA,
				new Object[] { new Long(codRuta) }, new RutaMapper());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obteneRruta(int) - end - return value=" + ruta); //$NON-NLS-1$
		}
		return ruta;
	}
}
