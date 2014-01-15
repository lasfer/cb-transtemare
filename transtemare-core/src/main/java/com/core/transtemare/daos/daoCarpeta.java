package com.core.transtemare.daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.core.transtemare.commons.Utils;
import com.core.transtemare.daos.mappers.BultoMapper;
import com.core.transtemare.daos.mappers.CarpetaHistoricoMapper;
import com.core.transtemare.daos.mappers.CarpetaMapper;
import com.core.transtemare.daos.mappers.CarpetaSnapGarantiaMapper;
import com.core.transtemare.daos.mappers.CarpetaSnapMapper;
import com.core.transtemare.daos.mappers.NumerosCarpetaDocumentoMapper;
import com.core.transtemare.daos.mappers.TerminalMapper;
import com.core.transtemare.entidades.Bulto;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Historico;
import com.core.transtemare.entidades.NumerosCarpetaDocumento;
import com.core.transtemare.entidades.Terminal;
import com.core.transtemare.entidades.Transportadora;
import com.core.transtemare.excepciones.FachadaException;
import com.core.transtemare.persistencia.sql.SQLCarpetas;

public class daoCarpeta {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(daoCarpeta.class);

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

	public daoCarpeta() {

	}

	public NumerosCarpetaDocumento obtenerNumerosCarpetaDocumento(
			Transportadora transportadora, Integer cantidadMIC) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerNumerosCarpetaDocumento(Transportadora=" + transportadora + ", Integer=" + cantidadMIC + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		LOGGER.info("Solicitando nros para transportadora : "
				+ transportadora.getIdTransportadora());
		final Object[] params = new Object[] {
				transportadora.getIdTransportadora(), cantidadMIC };
		NumerosCarpetaDocumento numeros = jdbcTemplate.queryForObject(
				SQLCarpetas.PROCEDURE_NUMERADORES, params,
				new NumerosCarpetaDocumentoMapper());
		LOGGER.info("Se obtuvieron los nros para transportadora : "
				+ transportadora.getIdTransportadora() + " Numero Carpeta: "
				+ numeros.getNroCarpeta() + " Numero Documento: "
				+ numeros.getNroDocumento());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerNumerosCarpetaDocumento(Transportadora, Integer) - end - return value=" + numeros); //$NON-NLS-1$
		}
		return numeros;
	}

	/*
	 * 
	 * AlarmaCarpetas retorna las carpetas que estan por vencer
	 */
	public List<Carpeta> AlarmaCarpetas(int desde, int hasta) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("AlarmaCarpetas(int=" + desde + ", int=" + hasta + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		final Object[] params = new Object[] { desde, hasta };
		List<Carpeta> listaAlarmaCarpetas = new ArrayList<Carpeta>();
		try {
			listaAlarmaCarpetas = jdbcTemplate.query(
					SQLCarpetas.ALARAMA_CARPETAS, params,
					new CarpetaSnapMapper());
		} catch (Exception e) {
			LOGGER.error(
					"AlarmaCarpetas(int desde=" + desde + ", int hasta=" + hasta + ")", e); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

			throw new RuntimeException(e.getMessage(), e);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("AlarmaCarpetas(int, int) - end - return value=" + listaAlarmaCarpetas); //$NON-NLS-1$
		}
		return listaAlarmaCarpetas;
	}

	public int modificarCarpeta(Carpeta carpeta)

	{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarCarpeta(Carpeta=" + carpeta + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		int check = 0;

		LOGGER.info("Modificando carpeta: " + carpeta.getIdCarpeta()
				+ " - Obteniendo el statement");
		final Object[] params = new Object[] {
				Utils.convertToSqlDate(carpeta.getFechaModificacion()),
				Utils.convertToSqlDate(carpeta.getFechaAlta()),
				carpeta.getTransitoAduanero(),
				carpeta.getCiudadPaisPartida().getIdLocalidad(),
				carpeta.getAduanaOrigen().getIdLocalidad(),
				carpeta.getCiudadPaisDestino().getIdLocalidad(),
				carpeta.getAduanaDestino().getIdLocalidad(),
				carpeta.getCamionOriginal().getIdCamion(),
				carpeta.getChoferOriginal().getIdResponsable(),
				carpeta.getRemolqueOriginal().getIdCamion(),
				carpeta.getCamionSubstituto().getIdCamion(),
				carpeta.getChoferSubstituto().getIdResponsable(),
				carpeta.getRemolqueSubstituto().getIdCamion(),
				carpeta.getRemitente().getIdEmpresa(),
				carpeta.getConsignatario().getIdEmpresa(),
				carpeta.getDestinatario().getIdEmpresa(),
				carpeta.getNotificar().getIdEmpresa(),
				carpeta.getPaisCiudadEmision().getIdLocalidad(),
				null,
				carpeta.getPaisCiudadPaisEntrega().getIdLocalidad(),
				carpeta.getNumeroPrecinto(),
				carpeta.getOrigenMercaderia().getIdPais(),
				carpeta.getMoneda(),
				carpeta.getValorFOT(),
				carpeta.getValorMercancias(),
				carpeta.getCostoFlete(),
				carpeta.getSeguro(),
				carpeta.getVolumenMC(),
				carpeta.getCantidadBultos(),
				carpeta.getTipoBulto().getCodBulto(),
				carpeta.getPesoBruto(),
				carpeta.getGastosPagar(),
				carpeta.getMontoRemitente(),
				carpeta.getMonedaRemitente(),
				carpeta.getMontoDestinatario(),
				carpeta.getMonedaDestinatario(),
				carpeta.getMontoFleteExterno(),
				carpeta.getMontoRembolso(),
				carpeta.getOtros(),
				carpeta.getMarcaYnumerobultos(),
				carpeta.getDocumentosAnexos(),
				carpeta.getFormalidadesAduana(),
				carpeta.getRuta().getIdRuta(),
				carpeta.getNombreBuque(),
				Utils.convertToSqlDate(carpeta.getFechaLlegadaBuque()),
				carpeta.getNroContenedor(),
				carpeta.getReferenciaDestino(),
				Utils.convertToSqlDate(carpeta.getFechaSalida()),
				Utils.convertToSqlDate(carpeta.getFechaVencimiento()),
				carpeta.getFacturaOriginal() != null ? carpeta
						.getFacturaOriginal() : false,
				carpeta.getPackingOriginal() != null ? carpeta
						.getPackingOriginal() : false,
				carpeta.getBlOriginal() != null ? carpeta.getBlOriginal()
						: false,
				Utils.convertToSqlDate(carpeta.getFechaRecibidoFactura()),
				Utils.convertToSqlDate(carpeta.getFechaRecibidoPacking()),
				Utils.convertToSqlDate(carpeta.getFechaRecibidoBL()),
				carpeta.getCliente().getIdEmpresa(),
				carpeta.getLiberacion() != null ? carpeta.getLiberacion()
						: false,
				carpeta.getLiberacionDescripcion(),
				carpeta.getPasadoAParaguay() != null ? carpeta
						.getPasadoAParaguay() : false,
				carpeta.getDespachante().getIdEmpresa(),
				carpeta.getAgenciaMaritima().getIdEmpresa(),
				carpeta.getComentarios(),
				carpeta.getTipoContenedor() != null ? carpeta
						.getTipoContenedor().getCode() : 0,
				Utils.convertToSqlDate(carpeta.getFechaSalidaSolicitudCliente()),
				carpeta.getTransportadoraCamion().getIdTransportadora(),
				carpeta.getTamanioLetraMarcaYNumero(),
				carpeta.getValidoHasta(),
				carpeta.getRutasCorto(),
				carpeta.getRutasLargo(),
				carpeta.getTransportadoraCamionSustituto()
						.getIdTransportadora(),
				carpeta.getNombreFirmaDestinatario(),
				carpeta.getTerminal() != null ? carpeta.getTerminal().getId()
						: 0,
				carpeta.getContenedorDevuelto(),
				carpeta.getCargarInformacionGarantia(),
				carpeta.getTipoGarantia() != null ? carpeta.getTipoGarantia()
						.getCode() : null, carpeta.getImporteGarantia(),
				carpeta.getBancoGarantia(), carpeta.getNroChequeGarantia(),
				carpeta.getFechaCargaGarantia(), carpeta.getGarantiaDevuelta(),
				carpeta.getIdCarpeta() };

		LOGGER.info("Creando carpeta: " + carpeta.getIdCarpeta()
				+ " - Ejecutando update");
		check = jdbcTemplate.update(SQLCarpetas.MODIFICAR_CARPETA, params);
		LOGGER.info("Creando carpeta: " + carpeta.getIdCarpeta()
				+ " - update Ejecutado");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarCarpeta(Carpeta) - end - return value=" + check); //$NON-NLS-1$
		}
		return check;
	}

	public List<Carpeta> obtenerCarpetas(Carpeta carpeta, boolean historico,
			int desde, int hasta) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTop20Carpetas(carpeta=" + carpeta + ",boolean=" + historico + ", int=" + desde + ", int=" + hasta + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		List<Object> params = new ArrayList<Object>(4);
		params.add(historico);
		String replaceConsulta = "";
		if (carpeta != null) {
			StringBuilder sb = new StringBuilder("and ");
			if (carpeta.getNroDocumento() != null) {
				sb.append("c.nroDocumento like ?");
				params.add(carpeta.getNroDocumento());
			} else if (StringUtils.isNotEmpty(carpeta.getNroContenedor())) {
				sb.append("c.nroContenedor like ?");
				params.add(carpeta.getNroContenedor());
			} else {
				throw new FachadaException(
						"Uno de los dos parametros debe estar seteado");
			}
			replaceConsulta = sb.toString();
		}
		params.add(desde);
		params.add(hasta);
		List<Carpeta> listaCarpetas = new ArrayList<Carpeta>();

		listaCarpetas = jdbcTemplate.query(
				SQLCarpetas.OBTENER_CARPETAS_DESDE_HASTA.replace(
						"[FILTROADICIONAL]", replaceConsulta),
				params.toArray(), new CarpetaSnapMapper());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTop20Carpetas(boolean, int, int) - end - return value=" + listaCarpetas); //$NON-NLS-1$
		}
		return listaCarpetas;
	}

	public List<Carpeta> obtenerCarpetasCargaGarantia(Carpeta carpeta) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerCarpetasCargaGarantia(carpeta=" + carpeta
					+ ") - start");
		}
		List<Carpeta> listaCarpetas = new ArrayList<Carpeta>();
		listaCarpetas = jdbcTemplate.query(
				SQLCarpetas.OBTENER_CARPETAS_CARGA_GARANTIA,
				new CarpetaSnapGarantiaMapper());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerCarpetasCargaGarantia(carpeta=) - end - return value="
					+ listaCarpetas);
		}
		return listaCarpetas;
	}

	public List<Carpeta> obtenerSubCarpetas(boolean historico,
			int idCarpetaPadre) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerSubCarpetas(boolean=" + historico + ", int=" + idCarpetaPadre + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		final Object[] params = new Object[] { historico, idCarpetaPadre };
		List<Carpeta> listaCarpetas = new ArrayList<Carpeta>();

		listaCarpetas = jdbcTemplate.query(SQLCarpetas.OBTENER_SUBCARPETAS,
				params, new CarpetaSnapMapper());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerSubCarpetas(boolean, int) - end - return value=" + listaCarpetas); //$NON-NLS-1$
		}
		return listaCarpetas;
	}

	public List<Historico> obtenerHistoricoCarpetas(int desde, int hasta) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerHistoricoCarpetas(int=" + desde + ", int=" + hasta + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		final Object[] params = new Object[] { desde, hasta };
		List<Historico> listaHistoricoCarpetas = new ArrayList<Historico>();
		listaHistoricoCarpetas = jdbcTemplate.query(
				SQLCarpetas.OBTENER_CARPETAS_HISTORICO, params,
				new CarpetaHistoricoMapper());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerHistoricoCarpetas(int, int) - end - return value=" + listaHistoricoCarpetas); //$NON-NLS-1$
		}
		return listaHistoricoCarpetas;
	}

	public Historico obtenerCarpetaDesdeHistoricoPorId(int id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerCarpetaDesdeHistoricoPorId(int=" + id + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		final Object[] params = new Object[] { id };

		Historico returnHistorico = jdbcTemplate.queryForObject(
				SQLCarpetas.OBTENER_CARPETA_POR_ID_HISTORICO, params,
				new CarpetaHistoricoMapper());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerCarpetaDesdeHistoricoPorId(int) - end - return value=" + returnHistorico); //$NON-NLS-1$
		}
		return returnHistorico;

	}

	public Carpeta obtenerCarpetaPorId(int id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerCarpetaPorId(int=" + id + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		Carpeta returnCarpeta = jdbcTemplate.queryForObject(
				SQLCarpetas.PROCEDURE_SELECT_CARPETA_POR_ID,
				new Object[] { id }, new CarpetaMapper());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerCarpetaPorId(int) - end - return value=" + returnCarpeta); //$NON-NLS-1$
		}
		return returnCarpeta;

	}

	public Integer pasarHistoricoXML(Carpeta carpeta, String xml) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("pasarHistoricoXML(Carpeta=" + carpeta + ", String=" + xml + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		final Object[] params = new Object[] {
				carpeta.getIdCarpeta(),
				carpeta.getIdCarpeta(),
				Utils.convertToSqlDate(new Date()),
				Utils.convertToSqlDate(carpeta.getFechaAlta()),
				Utils.convertToSqlDate(carpeta.getFechaModificacion()),
				xml,
				carpeta.getNumeroContenedorParte1() + "-"
						+ carpeta.getNumeroContenedorParte2() + "-"
						+ carpeta.getNumeroContenedorParte3() };
		int check = 0;

		check = jdbcTemplate.update(SQLCarpetas.PROCEDURE_PASAR_HISTORICO,
				params);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("pasarHistoricoXML(Carpeta, String) - end - return value=" + check); //$NON-NLS-1$
		}
		return check;
	}

	public Integer totalCarpetas() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalCarpetas() - start"); //$NON-NLS-1$
		}

		Integer returnInteger = this.jdbcTemplate
				.queryForInt(SQLCarpetas.OBTENER_CANTIDAD_TOTAL_CARPETAS);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalCarpetas() - end - return value=" + returnInteger); //$NON-NLS-1$
		}
		return returnInteger;
	}

	public Integer totalCarpetasHistorico() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalCarpetasHistorico() - start"); //$NON-NLS-1$
		}

		Integer returnInteger = this.jdbcTemplate
				.queryForInt(SQLCarpetas.OBTENER_CANTIDAD_TOTAL_CARPETAS_HISTORICO);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalCarpetasHistorico() - end - return value=" + returnInteger); //$NON-NLS-1$
		}
		return returnInteger;
	}

	public Integer totalAlarmas() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalAlarmas() - start"); //$NON-NLS-1$
		}

		Integer returnInteger = this.jdbcTemplate
				.queryForInt(SQLCarpetas.OBTENER_CANTIDAD_TOTAL_ALARMAS);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalAlarmas() - end - return value=" + returnInteger); //$NON-NLS-1$
		}
		return returnInteger;
	}

	public void obtenerTodosLosBultos(HashMap<String, String> tipoDeBultos) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosBultos(HashMap<String,String>=" + tipoDeBultos + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Bulto> bultos = new ArrayList<Bulto>();
		obtenerTodosLosBultos(bultos);
		for (Bulto bulto : bultos) {
			tipoDeBultos.put(String.valueOf(bulto.getCodBulto()),
					bulto.getDescripcion());
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosBultos(HashMap<String,String>) - end"); //$NON-NLS-1$
		}
	}

	public void obtenerTodosLosBultos(List<Bulto> bultos) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosBultos(List<Bulto>=" + bultos + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Bulto> bult = jdbcTemplate.query(
				SQLCarpetas.OBTENER_TODOS_LOS_BULTOS, new BultoMapper());
		bultos.addAll(bult);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTodosLosBultos(List<Bulto>) - end"); //$NON-NLS-1$
		}
	}

	public void obtenerTerminales(List<Terminal> terminales) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTerminales(List<Terminal>=" + terminales + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		List<Terminal> terminals = jdbcTemplate.query(
				SQLCarpetas.OBTENER_TERMINALES, new TerminalMapper());
		terminales.addAll(terminals);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTerminales(List<Terminal>=" + terminales + ") - end"); //$NON-NLS-1$
		}
	}

	public List<Terminal> obtenerTerminalesTabla(int from, int to) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerTerminalesTabla(from=" + from + ", to=" + to + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		final Object[] params = new Object[] { from, to };
		List<Terminal> terminales = new ArrayList<Terminal>();

		terminales = jdbcTemplate.query(
				SQLCarpetas.OBTENER_TERMINALES_DESDE_HASTA, params,
				new TerminalMapper());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerSubCarpetas(boolean, int) - end - return value=" + terminales); //$NON-NLS-1$
		}
		return terminales;
	}

	public Integer totalTerminales() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalTerminales() - start"); //$NON-NLS-1$
		}

		Integer total = this.jdbcTemplate
				.queryForInt(SQLCarpetas.COUNT_TERMINALES);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("totalTerminales() - end - return value=" + total); //$NON-NLS-1$
		}
		return total;
	}

	public int crearModificarTerminal(String id, String nombre) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("crearModificarTerminal(String=" + nombre + ") - start"); //$NON-NLS-1$ 
		}
		int returnint = 0;

		if (id == null || id.equals("_empty")) {
			returnint = this.jdbcTemplate.update(SQLCarpetas.ALTA_TERMINAL,
					new Object[] { nombre });
		} else {
			returnint = this.jdbcTemplate.update(
					SQLCarpetas.ACTUALIZAR_TERMINAL, new Object[] { nombre,
							Integer.valueOf(id) });
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("crearModificarTerminal(String) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;

	}

	public int eliminarTerminal(Integer id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("eliminarTerminal(String=" + id + ") - start"); //$NON-NLS-1$ 
		}
		int returnint = this.jdbcTemplate.update(SQLCarpetas.ELIMINAR_TERMINAL,
				new Object[] { id });

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("eliminarTerminal(String) - end - return value=" + returnint); //$NON-NLS-1$
		}
		return returnint;
	}

}
