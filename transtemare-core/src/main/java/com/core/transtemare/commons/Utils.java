package com.core.transtemare.commons;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 
 * @author Fernando Las
 * 
 */
public class Utils {

	private static final Logger LOGGER = Logger.getLogger(Utils.class);
	public static final String ID = "--";

	public Utils() {
	}

	/**
	 * Permite cambiar la fecha de java.util.Date a java.sql.Date
	 * 
	 * @param fecha
	 * @return
	 */
	public static java.sql.Date convertToSqlDate(Date fecha) {
		if (fecha == null) {
			return null;
		}
		return new java.sql.Date(fecha.getTime());
	}

	/**
	 * Permite cambiar la fecha de java.sql.Date a java.util.Date
	 * 
	 * @param fechaSql
	 * @return
	 */
	public static Date convertToUtilDate(java.sql.Date fechaSql) {
		if (fechaSql == null) {
			return null;
		}
		return new Date(fechaSql.getTime());
	}

	/**
	 * Permite cambiar la fecha de java.util.Date a TimeStamp
	 * 
	 * @param fecha
	 * @return
	 */
	public static Timestamp convertToTimestamp(Date fecha) {
		if (fecha == null) {
			return null;
		}
		return new Timestamp(fecha.getTime());
	}

	/**
	 * Permite cambiar la fecha de Timestamp a java.util.Date
	 * 
	 * @param fechaSql
	 * @return
	 */
	public static Date convertToUtilDate(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return new Date(timestamp.getTime());
	}

	public static String construirNroContenedor(String v1, String v2, String v3) {
		LOGGER.info("Comenzando el armado de numero de contenedor");
		StringBuilder sb = new StringBuilder();
		if (v1 != null && v2 != null && v3 != null) {
			if (v1.length() == 4 && v2.length() == 6 && v3.length() == 1) {
				LOGGER.info("Construyendo numero de contenedor para los valores "
						+ v1 + " " + v2 + " " + v3);
				sb.append(v1);
				sb.append(v2);
				sb.append(v3);
				LOGGER.info("Se creo el numero de contenedor " + sb.toString());
				return sb.toString();
			} else {
				LOGGER.info("Algunos de los parametros tienen un largo menor al permitido, se devuelve un objeto vacio");
				return sb.toString();
			}
		} else {
			LOGGER.info("Algunos de los parametros enviados son nulos, se devuelve un objeto vacio");
			return sb.toString();
		}

	}

	public static String[] desarmarNroContenedor(String nc) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("desarmarNroContenedor(String=" + nc + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		String[] nroCarpeta = { "", "", "" };
		if (nc != null && nc.length() == 11) {
			nroCarpeta[0] = nc.substring(0, 4);// posicion 0 al 4
			nroCarpeta[1] = nc.substring(4, 10);// posicion 6 a 10
			nroCarpeta[2] = nc.substring(10, 11);// posicion 10 a 11

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("desarmarNroContenedor(String) - end - return value=" + nroCarpeta); //$NON-NLS-1$
			}
			return nroCarpeta;
		} else {
			LOGGER.debug("El largo del string es menor a 11 digitos, se devuelve un arreglo vacio");
			return nroCarpeta;
		}

	}

	/**
	 * Dado un numero plano lo formatea con guiones
	 * 
	 * @param nroContenedor
	 * @return nroContenedorFormateado
	 */
	public static String formatearNroContenedor(String nroContenedor) {
		if (nroContenedor == null || nroContenedor.isEmpty()) {
			return "";
		}
		String[] nroCont = Utils.desarmarNroContenedor(nroContenedor);
		StringBuilder nroContenedorSB = new StringBuilder(nroCont[0])
				.append('-').append(nroCont[1]).append('-').append(nroCont[2]);
		return nroContenedorSB.toString();
	}

	/**
	 * 
	 * @param valor
	 *            Valor a splitear
	 * @param split
	 *            String con el cual se spliteara
	 * @return Integer con el identificador
	 */
	public static Integer obtenerId(String valor, String split) {
		LOGGER.info("Parseando el valor " + valor);
		try {
			if (valor != null && !"".equals(valor)) {
				String valorParseado = valor.split(split)[0];
				LOGGER.info("Valor parseado " + valorParseado);
				return new Integer(valorParseado);
			}

		} catch (NumberFormatException e) {
			LOGGER.warn("Ocurrio un error convirtiendo el valor " + valor, e);
		}
		return 0;
	}

}
