package com.core.transtemare.daos;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.core.transtemare.daos.mappers.CamionMapper;
import com.core.transtemare.entidades.Camion;
import com.core.transtemare.persistencia.sql.SQLTransportadoras;

public class daoCamiones {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(daoCamiones.class);

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

	public Camion obtenerCamionPorId(int id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerCamionPorId(int=" + id + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Camion camion = this.jdbcTemplate.queryForObject(
				SQLTransportadoras.OBTENER_CAMION_POR_ID,
				new Object[] { new Long(id) }, new CamionMapper());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerCamionPorId(int) - end - return value=" + camion); //$NON-NLS-1$
		}
		return camion;
	}

	public int altaCamion(Camion camion) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("altaCamion(camion=" + camion.toString() + ") - start");
		}
		int returnint = this.jdbcTemplate.update(
				SQLTransportadoras.ALTA_CAMION,
				new Object[] { camion.getCapacidad(), camion.getMarca(),
						camion.getMatricula(), camion.getAnio(),
						camion.getTipo(), camion.getNumeroPoliza(),
						camion.getVencimientoPoliza() });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("altaCamion(String, String, String, String, String) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public void obtenerTodosLosCamiones(List<Camion> camiones) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosCamiones(List<Camion>=" + camiones + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Camion> cams = jdbcTemplate.query(
				SQLTransportadoras.OBTENER_TODOS_LOS_CAMIONES,
				new CamionMapper());
		camiones.addAll(cams);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosCamiones(List<Camion>) - end"); //$NON-NLS-1$
		}
	}

	public void obtenerTodosLosRemolques(List<Camion> camiones) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosRemolques(List<Camion>=" + camiones + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Camion> cams = jdbcTemplate.query(
				SQLTransportadoras.OBTENER_TODOS_LOS_REMOLQUES,
				new CamionMapper());
		camiones.addAll(cams);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosRemolques(List<Camion>) - end"); //$NON-NLS-1$
		}
	}

	public List<Camion> obtenerTodosLosCamiones(Camion camion, int desde,
			int hasta) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosCamiones(Camion=" + camion + ", int=" + desde + ", int=" + hasta + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

		String matricula = null;
		if (camion != null) {
			matricula = camion.getMatricula() != null ? camion.getMatricula()
					: "";
		}
		final Object[] params = new Object[] { "%" + matricula + "%", desde,
				hasta };
		List<Camion> cams = jdbcTemplate.query(
				SQLTransportadoras.OBTENER_TODOS_LOS_CAMIONES_DESDE_HASTA,
				params, new CamionMapper());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosCamiones(List<Camion>, int, int) - end"); //$NON-NLS-1$
		}
		return cams;
	}

	public void obtenerTodosLosCamiones(HashMap<String, String> camiones) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosCamiones(HashMap<String,String>=" + camiones + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Camion> cams = jdbcTemplate.query(
				SQLTransportadoras.OBTENER_TODOS_LOS_CAMIONES,
				new CamionMapper());
		for (Camion camion : cams) {
			camiones.put(camion.getMatricula() + " " + camion.getMarca() + " "
					+ camion.getAnio(), String.valueOf(camion.getIdCamion()));
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosCamiones(HashMap<String,String>) - end"); //$NON-NLS-1$
		}
	}

	public int modificarCamion(Camion camion) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarCamion(camion=" + camion.toString()
					+ ") - start");
		}

		int returnint = this.jdbcTemplate.update(
				SQLTransportadoras.MODIFICAR_CAMION,
				new Object[] { camion.getCapacidad(), camion.getMarca(),
						camion.getMatricula(), camion.getAnio(),
						camion.getTipo(), camion.getNumeroPoliza(),
						camion.getVencimientoPoliza(), camion.getIdCamion() });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarCamion(int, String, String, String, int, String) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public void obtenerTodasLasMarcasDeCamiones(List<String> camiones) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasMarcasDeCamiones(List<String>=" + camiones + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<String> marcas = jdbcTemplate.queryForList(
				SQLTransportadoras.OBTENER_TODAS_LAS_MARCAS_DE_CAMIONES,
				String.class);
		camiones.addAll(marcas);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasMarcasDeCamiones(List<String>) - end"); //$NON-NLS-1$
		}
	}

	public int borrarCamion(int id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarCamion(int=" + id + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		int returnint = this.jdbcTemplate
				.update(SQLTransportadoras.BORRAR_CAMION,
						new Object[] { new Long(id) });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarCamion(int) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public Integer totalCamiones() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalCamiones() - start"); //$NON-NLS-1$
		}

		Integer returnInteger = this.jdbcTemplate
				.queryForInt(SQLTransportadoras.OBTENER_CANTIDAD_TOTAL_CAMIONES);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalCamiones() - end - return value=" + returnInteger); //$NON-NLS-1$
		}
		return returnInteger;
	}
}
