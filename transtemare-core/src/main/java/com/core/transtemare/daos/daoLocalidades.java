package com.core.transtemare.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.core.transtemare.daos.mappers.AduanaMapper;
import com.core.transtemare.daos.mappers.LocalidadMapper;
import com.core.transtemare.entidades.Aduana;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.persistencia.sql.SQLComunes;

public class daoLocalidades {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(daoLocalidades.class);

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

	public daoLocalidades() {
	}

	public int obtenerLocalidadPorNombre(String ciudad) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerLocalidadPorNombre(String=" + ciudad + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		int returnint = this.jdbcTemplate.queryForInt(
				SQLComunes.OBTENER_COD_LOCALIDAD_POR_NOMBRE,
				ciudad);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerLocalidadPorNombre(String) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public List<Localidad> getTodasLasLocalidades(int desde, int hasta) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodasLasLocalidades(desde=" + desde + "hasta" + hasta + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		final Object[] params = new Object[] { desde, hasta };
		List<Localidad> localidadesBase = jdbcTemplate.query(
				SQLComunes.LISTAR_LOCALIDADES_DESDE_HASTA, params,
				new LocalidadMapper());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodasLasLocalidades(int,int) - end"); //$NON-NLS-1$
		}
		return localidadesBase;
	}

	public void getTodasLasLocalidades(List<Localidad> localidades) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodasLasLocalidades(List<Localidad>=" + localidades + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		List<Localidad> localidadesBase = jdbcTemplate.query(
				SQLComunes.LISTAR_TODAS_LOCALIDADES, new LocalidadMapper());
		localidades.addAll(localidadesBase);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodasLasLocalidades(List<Localidad>) - end"); //$NON-NLS-1$
		}
	}

	public void obtenerTodasLasAduanas(HashMap<String, String> aduanas) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasAduanas(HashMap<String,String>=" + aduanas + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Aduana> ads = new ArrayList<Aduana>();
		getTodasLasAduanas(ads);
		for (Aduana aduana : ads) {
			aduanas.put(String.valueOf(aduana.getIdLocalidad()),
					aduana.getDescripcion());
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasAduanas(HashMap<String,String>) - end"); //$NON-NLS-1$
		}
	}

	public void getTodasLasAduanas(List<Aduana> aduanas) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodasLasAduanas(List<Aduana>=" + aduanas + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		List<Aduana> aduanasBase = jdbcTemplate.query(
				SQLComunes.OBTENER_TODAS_LAS_ADUANAS, new AduanaMapper());
		aduanas.addAll(aduanasBase);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodasLasAduanas(List<Aduana>) - end"); //$NON-NLS-1$
		}
	}

	public void getTodasLasLocalidadesHash(HashMap<String, String> localidades) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodasLasLocalidadesHash(HashMap<String,String>=" + localidades + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Localidad> localidadesBase = jdbcTemplate.query(
				SQLComunes.LISTAR_TODAS_LOCALIDADES, new LocalidadMapper());
		for (Localidad localidad : localidadesBase) {
			localidades.put(String.valueOf(localidad.getIdLocalidad()),
					localidad.getDescripcion());
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTodasLasLocalidadesHash(HashMap<String,String>) - end"); //$NON-NLS-1$
		}
	}

	public void getLocalidadesPorIds(
			HashMap<Integer, ? extends Localidad> localidades) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getLocalidadesPorIds(HashMap<Integer,? extends Localidad>=" + localidades + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		Set<Integer> ids = localidades.keySet();
		StringBuilder parametro = new StringBuilder();
		for (Iterator<Integer> iterator = ids.iterator(); iterator.hasNext();) {
			parametro.append(iterator.next());
			if (iterator.hasNext()) {
				parametro.append(",");
			}
		}
		String consulta = SQLComunes.OBTENER_LOCALIDADES_POR_ID.replace("?",
				parametro.toString());

		LOGGER.debug("Parametro Localidades:" + parametro);
		List<Localidad> localidadesBase = jdbcTemplate.query(consulta,
				new LocalidadMapper());

		for (Localidad loc : localidadesBase) {
			Localidad localidad = localidades.get(loc.getIdLocalidad());
			localidad.setDescripcion(loc.getDescripcion());
			localidad.setPais(loc.getPais());
			localidad.setCodigoAduana(loc.getCodigoAduana());
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getLocalidadesPorIds(HashMap<Integer,? extends Localidad>) - end"); //$NON-NLS-1$
		}
	}

	public int altaLocalidad(Localidad localidad) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("altaLocalidad(Localidad=" + localidad + ") - start");
		}

		int returnint = this.jdbcTemplate.update(
				SQLComunes.INSERTAR_LOCALIDAD,
				 localidad.getPais().getIdPais(),
						localidad.getDescripcion(), localidad.isAduana(), localidad.getCodigoAduana());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("altaLocalidad(Localidad) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public int modificarLocalidad(Localidad localidad) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarLocalidad(Localidad) - start");
		}

		int returnint = this.jdbcTemplate.update(
				SQLComunes.MODIFICAR_LOCALIDAD,
				 localidad.getDescripcion(),
						localidad.getPais().getIdPais(), localidad.isAduana(),
						localidad.getCodigoAduana(), localidad.getIdLocalidad());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarLocalidad(Localidad) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;

	}

	public int borrarLocalidad(int idLocalidad) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarLocalidad(int=" + idLocalidad + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		int returnint = this.jdbcTemplate.update(SQLComunes.BORRAR_LOCALIDAD,
				new Object[] { new Long(idLocalidad) });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarLocalidad(int) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}
}
