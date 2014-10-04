package com.core.transtemare.daos;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.core.transtemare.daos.mappers.EmpresaMapper;
import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.persistencia.sql.SQLCarpetas;

public class daoEmpresas {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(daoEmpresas.class);

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

	public daoEmpresas() {
	}

	public void obtenerTodasLasEmpresas(List<Empresa> emp) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasEmpresas(List<Empresa>=" + emp + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Empresa> empresas = jdbcTemplate.query(
				SQLCarpetas.OBTENER_TODAS_LAS_EMPRESAS, new EmpresaMapper());
		emp.addAll(empresas);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasEmpresas(List<Empresa>) - end"); //$NON-NLS-1$
		}
	}

	public List<Empresa> obtenerEmpresasTabla(Empresa empresa, int desde,
			int hasta) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasEmpresas(Empresa=" + empresa + ", int=" + desde + ", int=" + hasta + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

		final Object[] params = new Object[] { "%" + empresa.getNombre() + "%",
				desde, hasta };
		List<Empresa> empresas = jdbcTemplate.query(
				SQLCarpetas.OBTENER_TODAS_LAS_EMPRESAS_DESDE_HASTA, params,
				new EmpresaMapper());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasEmpresas(List<Empresa>, int, int) - end"); //$NON-NLS-1$
		}
		return empresas;
	}

	public void obtenerEmpresasPorTipo(List<Empresa> emp, byte tipo) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerEmpresasPorTipo(List<Empresa>=" + emp + ", byte=" + tipo + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		final Object[] params = new Object[] { tipo };
		List<Empresa> empresas = jdbcTemplate.query(
				SQLCarpetas.OBTENER_TODAS_LAS_EMPRESAS, params,
				new EmpresaMapper());
		emp.addAll(empresas);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerEmpresasPorTipo(List<Empresa>, byte) - end"); //$NON-NLS-1$
		}
	}

	public void obtenerTodasLasEmpresas(Map<String, String> emp, byte tipo) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasEmpresas(Map<String,String>=" + emp + ", byte=" + tipo + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		final Object[] params = new Object[] { tipo };
		List<Empresa> empresas = jdbcTemplate.query(
				SQLCarpetas.OBTENER_EMPRESAS_POR_TIPO, params,
				new EmpresaMapper());
		for (Empresa empresa : empresas) {
			emp.put(empresa.getNombre(), String.valueOf(empresa.getIdEmpresa()));
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodasLasEmpresas(Map<String,String>, byte) - end"); //$NON-NLS-1$
		}
	}

	public Empresa obtenerEmpresaId(int id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerEmpresaId(int=" + id + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Empresa empresa = this.jdbcTemplate.queryForObject(
				SQLCarpetas.OBTENER_EMPRESA_POR_ID,
				new Object[] { new Long(id) }, new EmpresaMapper());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerEmpresaId(int) - end - return value=" + empresa); //$NON-NLS-1$
		}
		return empresa;
	}

	public int modificarEmpresa(Empresa empresa) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarEmpresa(Empresa=" + empresa + ") - start");
		}

		int returnint = this.jdbcTemplate.update(
				SQLCarpetas.MODIFICAR_EMPRESA,
				new Object[] {
						empresa.getNombre(),
						empresa.getRolContribuyente(),
						empresa.getDireccion(),
						empresa.getLocalidad() != null ? empresa.getLocalidad()
								.getIdLocalidad() : null, empresa.getTipo(),
						empresa.getNombreCorto(),empresa.getCodigo(), empresa.getIdEmpresa() });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarEmpresa(Empresa) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public int altaEmpresa(Empresa empresa) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("altaEmpresa(Empresa=" + empresa + ") - start");
		}

		int returnint = this.jdbcTemplate.update(
				SQLCarpetas.INSERTAR_EMPRESA,
				new Object[] { empresa.getNombre(),
						empresa.getRolContribuyente(), empresa.getDireccion(),
						empresa.getLocalidad().getIdLocalidad(),
						empresa.getTipo(), empresa.getNombreCorto(),empresa.getCodigo()});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("altaEmpresa(String, String, String, int, byte) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

	public Integer totalEmpresas() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalEmpresas() - start"); //$NON-NLS-1$
		}

		Integer returnInteger = this.jdbcTemplate
				.queryForInt(SQLCarpetas.OBTENER_CANTIDAD_TOTAL_EMPRESAS);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalEmpresas() - end - return value=" + returnInteger); //$NON-NLS-1$
		}
		return returnInteger;
	}

	public int borrarEmpresa(int id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarEmpresa(int=" + id + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		int returnint = this.jdbcTemplate.update(SQLCarpetas.BORRAR_EMPRESA,
				new Object[] { new Long(id) });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarEmpresa(int) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}
}
