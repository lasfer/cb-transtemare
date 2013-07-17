package com.core.transtemare.daos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.CollectionUtils;

import com.core.transtemare.daos.mappers.ResponsableMapper;
import com.core.transtemare.daos.mappers.UserMapper;
import com.core.transtemare.entidades.Responsable;
import com.core.transtemare.entidades.User;
import com.core.transtemare.persistencia.sql.SQLComunes;
import com.core.transtemare.persistencia.sql.SQLTransportadoras;

public class daoResponsable {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(daoResponsable.class);

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

	public daoResponsable() {
	};

	public int altaDeChofer(Responsable responsable) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("altaDeChofer(chofer=" + responsable.toString()
					+ ") - start");
		}

		int returnint = this.jdbcTemplate.update(
				SQLTransportadoras.INSERTAR_CHOFER,
				new Object[] { responsable.getNombre(),
						responsable.getApellido(), responsable.getDocumento(),
						responsable.getLocalidad().getIdLocalidad(),
						responsable.getTelefono() });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("altaDeChofer(String, String, String, int) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public void obtenerTodosLosChoferes(ArrayList<Responsable> choferes) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosChoferes(ArrayList<Responsable>=" + choferes + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Collection<Responsable> responsables = jdbcTemplate.query(
				SQLTransportadoras.OBTENER_TODOS_LOS_CHOFERES,
				new ResponsableMapper());
		choferes.addAll(responsables);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosChoferes(ArrayList<Responsable>) - end"); //$NON-NLS-1$
		}
	}

	public List<Responsable> obtenerTodosLosChoferes(Responsable responsable,
			int desde, int hasta) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosChoferes(Responsable=" + responsable
					+ ", int=" + desde + ", int=" + hasta + ") - start");
		}
		String nombre = responsable.getNombre() != null ? responsable
				.getNombre() : "";
		String apellido = responsable.getApellido() != null ? responsable
				.getApellido() : "";

		final Object[] params = new Object[] { "%" + nombre + "%",
				"%" + apellido + "%", desde, hasta };
		List<Responsable> responsables = jdbcTemplate.query(
				SQLTransportadoras.OBTENER_TODOS_LOS_CHOFERES_DESDE_HASTA,
				params, new ResponsableMapper());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosChoferes(List<Responsable>, int, int) - end"); //$NON-NLS-1$
		}
		return responsables;
	}

	public void obtenerTodosLosChoferes(HashMap<String, String> choferes) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosChoferes(HashMap<String,String>=" + choferes + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Collection<Responsable> responsables = jdbcTemplate.query(
				SQLTransportadoras.OBTENER_TODOS_LOS_CHOFERES,
				new ResponsableMapper());

		for (Responsable resp : responsables) {
			choferes.put(resp.getDocumento() + " " + resp.getNombre() + " "
					+ resp.getApellido(),
					String.valueOf(resp.getIdResponsable()));
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosChoferes(HashMap<String,String>) - end"); //$NON-NLS-1$
		}
	}

	public int modificarChofer(Responsable responsable) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarChofer(chofer=" + responsable.toString()
					+ ") - start");
		}

		int returnint = this.jdbcTemplate.update(
				SQLTransportadoras.MODIFICAR_CHOFER,
				new Object[] { responsable.getNombre(),
						responsable.getApellido(), responsable.getDocumento(),
						responsable.getLocalidad().getIdLocalidad(),
						responsable.getTelefono(),
						responsable.getIdResponsable() });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarChofer(int, String, String, String, int) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;

	}

	public Responsable obtenerResponsablePorId(int id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerResponsablePorId(int=" + id + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Responsable responsable = this.jdbcTemplate.queryForObject(
				SQLTransportadoras.OBTENER_RESPONSABLE_POR_ID,
				new Object[] { new Long(id) }, new ResponsableMapper());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerResponsablePorId(int) - end - return value=" + responsable); //$NON-NLS-1$
		}
		return responsable;
	}

	public int borrarResponsable(int codResponsable) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarResponsable(int=" + codResponsable + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		int returnint = this.jdbcTemplate.update(
				SQLTransportadoras.BORRAR_RESPONSABLE, new Object[] { new Long(
						codResponsable) });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarResponsable(int) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public User obtenerUsuarioRegistrado(String usuario, String contrasena) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerUsuarioRegistrado(String=" + usuario + ", String=" + contrasena + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		// Se usa query en vez de query for object para que no de error cuando
		// los datos del usuario ingresado no coinciden con ninguno.
		List<User> users = this.jdbcTemplate.query(
				SQLComunes.OBTENER_RESPONSABLE_USUARIO_CONTRASENA,
				new Object[] { usuario, contrasena }, new UserMapper());
		User user = null;
		if (!CollectionUtils.isEmpty(users)) {
			user = users.get(0);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerUsuarioRegistrado(String, String) - end - return value=" + user); //$NON-NLS-1$
		}
		return user;
	}

	public Integer totalChoferes() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalChoferes() - start"); //$NON-NLS-1$
		}

		Integer returnInteger = this.jdbcTemplate
				.queryForInt(SQLTransportadoras.OBTENER_CANTIDAD_TOTAL_RESPONSABLE);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalChoferes() - end - return value=" + returnInteger); //$NON-NLS-1$
		}
		return returnInteger;
	}
}
