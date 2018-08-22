package com.web.transtemare.acciones.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.commons.Utils;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.enums.TipoContenedor;
import com.core.transtemare.excepciones.FachadaException;
import com.opensymphony.xwork2.ActionSupport;

public class CRT extends ActionSupport implements ServletResponseAware {

	public static JasperReport jasperReport;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CRT.class);
	public static String EXTENSION_ARCHIVO;

	private HttpServletResponse response;
	private InputStream inputStream2;
	private Integer id;
	private Fachada fac;

	public CRT(Fachada fac) {
		super();
		this.fac = fac;
	}

	static {
		try {
			ResourceBundle rb2 = ResourceBundle.getBundle("propiedades");
			EXTENSION_ARCHIVO = rb2.getString("extension.archivos.logos");
			jasperReport = JasperCompileManager.compileReport(CRT.class
					.getClassLoader().getResource("documentos/CRT.jrxml")
					.getFile());
			logger.info("Se compilo el reporte crt");
		} catch (Exception e1) {
			logger.error("No se pudo compilar el CRT");
			logger.error(e1.getMessage(), e1);
		}
	}

	public String execute() {
		Carpeta c = null;
		try {
			c = fac.obtenerCarpeta(id);
			logger.debug("Se obtuvo la carpeta con el id " + id);

		} catch (FachadaException e) {
			logger.error("Error al consultar en la base: " + e.getMessage());
		}

		ResourceBundle rb = ResourceBundle.getBundle("propiedades");

		HashMap<String, String> paises = new HashMap<String, String>();
		fac.darTodosLosPaisesHash(paises);
		HashMap<String, Object> param = new HashMap<String, Object>();
		this.cargarParametros(param, c, paises, rb);

		logger.debug("Se cargaron los datos en el map");
		this.crearCRT(param);
		return SUCCESS;
	}

	public void cargarParametros(HashMap<String, Object> param, Carpeta c,
			HashMap<String, String> paises, ResourceBundle rb) {

		param.put("nroDocumento", (c != null && c.getTrans() != null && c
				.getTrans().getPrefijo() != null) ? c.getTrans().getPrefijo()
				+ c.getNroDocumento() : "");
		// Path para la carpeta donde estan los logos
		param.put("logoPath",
				(c.getTrans().getNombreArchivo() != null) ? CRT.class
						.getClassLoader().getResource("documentos/logos")
						.getFile().concat(c.getTrans().getNombreArchivo())
						.concat(EXTENSION_ARCHIVO) : "");

		// param.put("Localidad", (c.getCiudadPaisDestino() != null) ?
		// c.getCiudadPaisDestino().getDescripcion().toUpperCase() + "-"+
		// paises.get(c.getCiudadPaisDestino().getIdPais() + "").toUpperCase() :
		// "");
		param.put(
				"Localidad",
				(c.getPaisCiudadEmision() != null && !c.getPaisCiudadEmision()
						.toString().isEmpty()) ? new String(c
						.getPaisCiudadEmision().toString().toUpperCase())
						.split(Utils.ID)[1] : "");

		// INICIO-NOMBRE Y DOMICILIO DEL REMITENTE
		param.put("Nombre_Remitente_Domicilio",
				getEmpresaDomicilio(c.getRemitente()));

		// INICIO DESTINATARIO
		param.put("EmpresaDestinataria",
				getEmpresaDomicilio(c.getDestinatario()));

		// INICIO EMPRESA CONSIGNATARIA
		param.put("EmpresaConsignataria",
				getEmpresaDomicilio(c.getConsignatario()));

		// INICIO EMPRESA NOTIFICAR A
		param.put("NotificarA", getEmpresaDomicilio(c.getNotificar()));

		Localidad aduanaOrigen = c.getCiudadPaisPartida();
		if (aduanaOrigen != null && !aduanaOrigen.toString().equals("")) {
			param.put("LocalidadPortador",
					(aduanaOrigen.toString().toUpperCase()).split(Utils.ID)[1]);
		}

		// param.put("LocalidadPaisEntrega",(c.getPaisCiudadPaisEntrega()!=
		// null)?c.getPaisCiudadPaisEntrega().toString():"");
		// ESTE VALOR QUE SE CARGA ABAJO HAY QUE CHEQUEARLO
		param.put(
				"LocalidadPaisEntrega",
				(c.getCiudadPaisDestino() != null && c.getCiudadPaisDestino()
						.getIdLocalidad() > 0) ? new String(c
						.getCiudadPaisDestino().toString().toUpperCase())
						.split(Utils.ID)[1] : "");

		// DICE EL FLAVIO QUE NO VA
		// param.put("PortadoresSucesivos",
		// (c.getTrans().getNombreTransportadora() != null) ?
		// c.getTrans().getNombreTransportadora() : "");

		StringBuilder marcaYnumerobultosSB = new StringBuilder();
		if (StringUtils.isNotEmpty(c.getMarcaYnumerobultos())) {
			if (!TipoContenedor.SUELTA.equals(c.getTipoContenedor())) {
				marcaYnumerobultosSB = marcaYnumerobultosSB
						.append("1(UNO) CONTENEDOR DE ")
						.append(c.getTipoContenedor().getValue())
						.append("' ")
						.append(Utils.formatearNroContenedor(c
								.getNroContenedor()));
			}
			marcaYnumerobultosSB.append("\n QUE DICE CONTENER ").append(
					c.getMarcaYnumerobultos());
		}
		param.put("marcaYnumerobultos", marcaYnumerobultosSB.toString());
		param.put("pesobruto", (c.getPesoBruto() != null) ? c.getPesoBruto()
				: "");
		param.put("Volumen", (c.getVolumenMC() != null) ? c.getVolumenMC() : "");

		param.put("Valor", (c.getValorFOT() != null) ? c.getValorFOT() : "");

		param.put("flete", (c.getCostoFlete() != null) ? c.getCostoFlete() : "");
		param.put("otros", (c.getOtros() != null) ? c.getOtros() : "");
		param.put("moneda", (c.getMoneda() != null) ? c.getMoneda() : "");
		param.put("ValorRemitente",
				(c.getMontoRemitente() != null) ? c.getMontoRemitente() : "");
		param.put("monedaRemitente",
				(c.getMonedaRemitente() != null) ? c.getMonedaRemitente() : "");
		param.put("ValorDestinatario",
				(c.getMontoDestinatario() != null) ? c.getMontoDestinatario()
						: "");
		param.put("monedaDestinatario",
				(c.getMonedaDestinatario() != null) ? c.getMonedaDestinatario()
						: "");
		param.put("documentosAnexos",
				(c.getDocumentosAnexos() != null) ? c.getDocumentosAnexos()
						: "");
		param.put("Gastosdesc",
				(c.getGastosPagar() != null) ? c.getGastosPagar() : "");
		param.put("Montofleteexterno",
				(c.getMontoFleteExterno() != null) ? c.getMontoFleteExterno()
						: "");
		param.put("valorreembolso",
				(c.getMontoRembolso() != null) ? c.getMontoRembolso() : "");
		param.put("instruccionesFormalidades",
				(c.getFormalidadesAduana() != null) ? c.getFormalidadesAduana()
						: "");
		/* param.put("nombreFirmaDestinatario", c.getNombreFirmaDestinatario()); */
		param.put(
				"nombreFirmaDestinatario",
				"SEGURO DE LA MERCADERIA POR CUENTA Y ORDEN DEL IMPORTADOR SIN PERJUICIO NI REPETICION PARA LA EMPRESA TRANSPORTADORA");

		JRStyle styles[] = jasperReport.getStyles();
		for (int i = 0; i < styles.length; i++) {
			if ("MarcaYNumeroStyle".equals(styles[i].getName())) {
				styles[i].setFontSize(c.getTamanioLetraMarcaYNumero() + 2);
			}
		}

		param.put("firmante", StringUtils.isNotBlank(c.getFirmante()) ?
				c.getFirmante() : "Flavio Skunca");
		
		param.put("transitoAduanero", c.getTransitoAduanero());
		param.put("nroCarpeta", String.valueOf(c.getIdCarpeta()));
		param.put("referenciaDestino", c.getReferenciaDestino());
		param.put("nombreTransportadora", c.getTrans()
				.getNombreTransportadora());
		param.put("nombreCortoDespachante", c.getDespachante() != null ? c
				.getDespachante().getNombreCorto() : null);
		Map<String, String> listaRutas = new HashMap<String, String>();
		try {
			fac.obtenerTodasLasRutas(listaRutas);
		} catch (FachadaException e) {
			logger.error("Error obteniendo rutas");
		}

		if (StringUtils.isEmpty(c.getRutasLargo())) {
			param.put("declaracionesObservaciones", c.getRuta()
					.getDescripcion());
		} else {
			param.put("declaracionesObservaciones", c.getRutasLargo());
		}
		try {
			Set<String> keys = param.keySet();
			List<String> list = new ArrayList<String>();
			for (Iterator<String> iterator = keys.iterator(); iterator
					.hasNext();) {
				String string = iterator.next();
				if (param.get(string) == null) {
					list.add(string);
				}

			}

			for (String s : list) {
				param.remove(s);
			}
		} catch (Exception e) {
			logger.error("Error al limpiar los null");
		}

	}

	public String getEmpresaDomicilio(Empresa empresa) {
		StringBuffer ndr = new StringBuffer();
		if (empresa.getIdEmpresa() != 0) {
			ndr.append(StringUtils.isNotEmpty(empresa.getNombre()) ? empresa
					.getNombre() : "");
			if (StringUtils.isNotEmpty(empresa.getDireccion())
					&& !"-".equals(empresa.getDireccion().trim())) {
				ndr.append(" - ");
				ndr.append(StringUtils.isNotEmpty(empresa.getDireccion()) ? empresa
						.getDireccion() : "");
			}
			ndr.append("\n");
			ndr.append((empresa.getLocalidad() != null && StringUtils
					.isNotEmpty(empresa.getLocalidad().getDescripcion())) ? new String(
					empresa.getLocalidad().toString().toUpperCase())
					.split(Utils.ID)[1] : "");
		}
		return ndr.toString();
	}

	public void crearCRT(HashMap<String, Object> parametros) {

		try {

			if (jasperReport == null) {
				jasperReport = JasperCompileManager
						.compileReport(getText("CRT"));
			}
			JasperPrint jp = JasperFillManager.fillReport(jasperReport,
					parametros, new JREmptyDataSource());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jp, baos);

			// JRXlsExporter exporterXLS = new JRXlsExporter();
			// exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
			// jp);
			// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
			// baos);
			// exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
			// Boolean.TRUE);
			// exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
			// Boolean.TRUE);
			// exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
			// Boolean.FALSE);
			// exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
			// Boolean.TRUE);
			// exporterXLS.exportReport();

			logger.debug("Reporte exportado OK.");
			response.setContentLength(baos.size());

			ByteArrayInputStream bis = new ByteArrayInputStream(
					baos.toByteArray());
			inputStream2 = bis;

		} catch (Exception ex) {
			logger.error("Error al crear CRT  " + ex.getMessage() + "/"
					+ ex.getStackTrace() + "!" + ex.getLocalizedMessage());
		}
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public HttpServletResponse getServletResponse() {
		return this.response;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public InputStream getInputStream2() {
		return inputStream2;
	}

	public void setInputStream2(InputStream inputStream2) {
		this.inputStream2 = inputStream2;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

}