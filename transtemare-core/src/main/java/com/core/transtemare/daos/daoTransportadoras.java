package com.core.transtemare.daos;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.core.transtemare.daos.mappers.TransportadoraMapper;
import com.core.transtemare.entidades.Transportadora;
import com.core.transtemare.persistencia.sql.SQLTransportadoras;

public class daoTransportadoras {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(daoTransportadoras.class);

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

	public daoTransportadoras() {
	};

	public int altaTransportadora(String nombreTransportadora,
			String domicilio, String rolDelContribuyente, int codLocalidad,
			String prefijo, String archivo) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("altaTransportadora(String=" + nombreTransportadora + ", String=" + domicilio + ", String=" + rolDelContribuyente + ", int=" + codLocalidad + ", String=" + prefijo + ", String=" + archivo + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
		}
		// final String INSERT_TRANSPORTADORA =
		// "INSERT INTO transportadora VALUES(null,?,?,?,?,1,?,?);";
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("transportadora").usingGeneratedKeyColumns(
						"CodTransportadora");
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("Nombre", nombreTransportadora);
		parameters.addValue("RolContribuyente", rolDelContribuyente);
		parameters.addValue("Domicilio", domicilio);
		parameters.addValue("CodLocallidad_FK", new Long(codLocalidad));
		parameters.addValue("Prefijo", prefijo);
		parameters.addValue("imagen", archivo);
		parameters.addValue("activo", new Long(1));
		Number rowId = jdbcInsert.executeAndReturnKey(parameters);
		int returnint = rowId.intValue();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("altaTransportadora(String, String, String, int, String, String) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public void obtenerTodasLasTransportadoras(
			List<Transportadora> transportadoras, int desde, int hasta) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasTransportadoras(List<Transportadora>=" + transportadoras + ", int=" + desde + ", int=" + hasta + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

		final Object[] params = new Object[] { desde, hasta };
		List<Transportadora> transs = jdbcTemplate.query(
				SQLTransportadoras.OBTENER_TODAS_TRANSPORTADORAS_DESDE_HASTA,
				params, new TransportadoraMapper());
		transportadoras.addAll(transs);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasTransportadoras(List<Transportadora>, int, int) - end"); //$NON-NLS-1$
		}
	}

	public void obtenerTodasLasTransportadoras(
			List<Transportadora> transportadoras) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasTransportadoras(List<Transportadora>=" + transportadoras + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Transportadora> transs = jdbcTemplate.query(
				SQLTransportadoras.OBTENER_TODAS_TRANSPORTADORAS,
				new TransportadoraMapper());
		transportadoras.addAll(transs);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasTransportadoras(List<Transportadora>) - end"); //$NON-NLS-1$
		}
	}

	public void obtenerTodasLasTransportadoras(
			HashMap<String, String> transportadoras) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasTransportadoras(HashMap<String,String>=" + transportadoras + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Transportadora> transs = jdbcTemplate.query(
				SQLTransportadoras.OBTENER_TODAS_TRANSPORTADORAS,
				new TransportadoraMapper());
		for (Transportadora transportadora : transs) {
			transportadoras.put(transportadora.getNombreTransportadora(),
					String.valueOf(transportadora.getIdTransportadora()));
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasTransportadoras(HashMap<String,String>) - end"); //$NON-NLS-1$
		}
	}

	public int borrarTransportadora(int id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarTransportadora(int=" + id + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		int returnint = this.jdbcTemplate.update(SQLTransportadoras.BORRAR_TRANSPORTADORA, new Object[] { new Long(id) });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarTransportadora(int) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public Transportadora obtenerTransportadoraPorId(int id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTransportadoraPorId(int=" + id + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Transportadora transportadora = this.jdbcTemplate.queryForObject(
				SQLTransportadoras.OBTENER_TRANSPORTADORA_POR_ID,
				new Object[] { new Long(id) }, new TransportadoraMapper());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTransportadoraPorId(int) - end - return value=" + transportadora); //$NON-NLS-1$
		}
		return transportadora;
	}

	public int modificarTransportadora(Integer idTransportadora,
			String nombreTransportadora, String domicilio,
			String rolDelContribuyente, int idLocalidad, String prefijo,
			String imagen) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarTransportadora(Integer=" + idTransportadora + ", String=" + nombreTransportadora + ", String=" + domicilio + ", String=" + rolDelContribuyente + ", int=" + idLocalidad + ", String=" + prefijo + ", String=" + imagen + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		}

		int returnint = this.jdbcTemplate.update(SQLTransportadoras.MODIFICAR_TRANSPORTADORA, new Object[] { nombreTransportadora, rolDelContribuyente, domicilio, idLocalidad, prefijo, imagen, idTransportadora });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarTransportadora(Integer, String, String, String, int, String, String) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public Integer totalTransportadoras() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalTransportadoras() - start"); //$NON-NLS-1$
		}

		Integer returnInteger = this.jdbcTemplate.queryForInt(SQLTransportadoras.OBTENER_CANTIDAD_TOTAL_TRANSPORTADORAS);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalTransportadoras() - end - return value=" + returnInteger); //$NON-NLS-1$
		}
		return returnInteger;
	}

	public int insertarNumerador(int resultado, String numerador) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("insertarNumerador(int=" + resultado + ", String=" + numerador + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		int returnint = this.jdbcTemplate.update("INSERT INTO numeradores VALUES(?,?);", new Object[] { resultado, numerador });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("insertarNumerador(int, String) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public int modificarNumerador(Integer idTransportadora, String numerador) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarNumerador(Integer=" + idTransportadora + ", String=" + numerador + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		final String sql = "update  numeradores n set n.numero=? where n.CodTransportadora=?";
		int returnint = this.jdbcTemplate.update(sql, new Object[] { numerador, idTransportadora });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarNumerador(Integer, String) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}
}
