package com.web.transtemare.acciones.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.excepciones.FachadaException;
import com.opensymphony.xwork2.ActionSupport;

public class Caratula extends ActionSupport implements ServletResponseAware {
	private static final long serialVersionUID = 1L;
	private static JasperReport jasperReport;
	private static final Logger logger = Logger.getLogger(Caratula.class);
	private HttpServletResponse response;
	private InputStream inputStream2;
	private Fachada fac;
	private Carpeta c;
	private Integer id;

	public Caratula(Fachada fac) {
		super();
		this.fac = fac;
	}

	static {
		try {
			logger.info("Comenzando el compilado del reporte CARATULA.jrxml");
			jasperReport = JasperCompileManager.compileReport(Caratula.class.getClassLoader()
					.getResource("documentos/CARATULA.jrxml").getFile());
			logger.info("La caratula se compilo correctamente");
		} catch (Exception e) {
			logger.error("No se pudo compilar la caratula", e);
		}
	}

	public String execute() {
		try {
			c = fac.obtenerCarpeta(id);
			logger.debug("Se obtuvo la carpeta correctamente");
		} catch (FachadaException e) {
			logger.error("Error al consultar en la base: " + e.getMessage());
		}
		HashMap<String, Object> parametrosReporte = new HashMap<String, Object>();

		this.cargarParametrosReporte(parametrosReporte);

		this.generarCaratula_(parametrosReporte);
		return SUCCESS;
	}

	protected void generarCaratula_(HashMap<String, Object> parametros) {

		try {
			JasperPrint jp = JasperFillManager.fillReport(jasperReport,
					parametros, new JREmptyDataSource());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jp, baos);
			logger.debug("Reporte exportado OK.");
			response.setContentLength(baos.size());
			ByteArrayInputStream bis = new ByteArrayInputStream(
					baos.toByteArray());
			inputStream2 = bis;

		} catch (Exception ex) {
			logger.error("Error al crear caratula de carpeta  "
					+ ex.getMessage() + "/" + ex.getStackTrace() + "!"
					+ ex.getLocalizedMessage());
		}
	}

	protected void cargarParametrosReporte(Map<String, Object> parametrosReporte) {

		parametrosReporte
				.put("AGENCIA_MARITIMA",
						(c.getAgenciaMaritima().getNombre() != null) ? c
								.getAgenciaMaritima().getNombre() : "");
		parametrosReporte.put("NR_CONTENEDOR",
				(c.getNroContenedor() != null) ? c.getNroContenedor()
						.toString() : "");
		parametrosReporte.put("NR_CARPETA", (c.getIdCarpeta() != null) ? c
				.getIdCarpeta().toString() : "");
		parametrosReporte.put("NM_BUQUE", (c.getNombreBuque() != null) ? c
				.getNombreBuque().toString() : "");
		parametrosReporte.put("FX_LLEGADA_BUQUE", c.getFechaLlegadaBuque());
		parametrosReporte
				.put("DUENIO_CAMION_TRANSPORTISTA",
						(c.getTransportadoraCamion().getNombreTransportadora() != null) ? c
								.getTransportadoraCamion()
								.getNombreTransportadora().toString()
								: "");
		parametrosReporte.put("CHOFER",
				(c.getChoferOriginal().getNombre() != null) ? c
						.getChoferOriginal().getNombre().toString() : "");
		parametrosReporte.put("CEDULA",
				(c.getChoferOriginal().getDocumento() != null) ? c
						.getChoferOriginal().getDocumento().toString() : "");
		parametrosReporte.put("MATRICULA_CAMION",
				(c.getCamionOriginal() != null && c.getCamionOriginal()
						.getMatricula() != null) ? c.getCamionOriginal()
						.getMatricula().toString() : "");
		parametrosReporte.put("MATRICULA_ZORRA",
				(c.getRemolqueOriginal() != null) ? c.getRemolqueOriginal()
						.getMatricula() : "");
		parametrosReporte
				.put("MARCA_ANIO_CAMION",
						(c.getCamionOriginal().getMarca() != null && c
								.getCamionOriginal().getAnio() != null) ? (c
								.getCamionOriginal().getMarca().toString()
								+ " " + String.valueOf(c.getCamionOriginal()
								.getAnio())) : "");
		parametrosReporte.put("TIPO_CONTENEDOR",
				(c.getTipoContenedor() != null) ? c.getTipoContenedor()
						.getValue() : "");
		parametrosReporte.put("CODIGO_UY", "");
		parametrosReporte.put("KILAJE", (c.getPesoBruto() != null) ? c
				.getPesoBruto().toString() : "");
		parametrosReporte.put("FECHA_SALIDA_CARGA_CLIENTE",
				c.getFechaSalidaSolicitudCliente());
		parametrosReporte.put("DESTINO", (c.getDestinatario() != null) ? c
				.getDestinatario().getNombre() : "");
		parametrosReporte.put("COMENTARIOS_DETALLES",
				(c.getComentarios() != null) ? c.getComentarios().toString()
						: "");
		parametrosReporte.put("DESPACHANTE",
				(c.getDespachante().getNombre() != null) ? c.getDespachante()
						.getNombre().toString() : "");
		parametrosReporte.put("BL_ORIGINAL_COPIA", c.getBlOriginal() ? "Si"
				: "No");
		parametrosReporte.put("FACTURA", "");
		parametrosReporte.put("PACKING_LIST", c.getPackingOriginal() ? "Si"
				: "No");
		parametrosReporte.put("TRANSPORTISTA", (c.getTrans()
				.getNombreTransportadora() != null) ? c.getTrans()
				.getNombreTransportadora().toString() : "");
		parametrosReporte.put("CLIENTE",
				(c.getCliente().getNombre() != null) ? c.getCliente()
						.getNombre().toString() : "");
		parametrosReporte.put("PASADO_POR_FAX", c.getPasadoAParaguay() ? "Si"
				: "No");
		parametrosReporte.put("FECHA_HORA_RECIBIDO", c.getFechaRecibidoBL());
		parametrosReporte.put("FX_VENCIMIENTO", c.getFechaVencimiento());
		logger.debug("Se cargaron los datos en el map");

	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
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

	public Carpeta getC() {
		return c;
	}

	public void setC(Carpeta c) {
		this.c = c;
	}
}
