package com.web.transtemare.acciones.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.excepciones.FachadaException;
import com.opensymphony.xwork2.ActionSupport;

public class SABANA extends ActionSupport implements ServletResponseAware {

	public static JasperReport jasperReport;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SABANA.class);
	private HttpServletResponse response;
	private InputStream inputStream;
	private Fachada fac;
	private Carpeta c;
	private Integer id;

	public SABANA(Fachada fac) {
		super();
		this.fac = fac;
	}

	static {
		try {
			String fileReport = SABANA.class.getClassLoader()
					.getResource("documentos/SABANA.jrxml").getFile();
			logger.info("Compilando el fuente: " + fileReport);
			jasperReport = JasperCompileManager.compileReport(fileReport);
			logger.info("Se compilo la sabana correctamente");
		} catch (Exception e) {
			logger.error("No se pudo compilar la sabana ", e);
		}
	}

	public String execute() {
		try {
			c = fac.obtenerCarpeta(id);
			logger.debug("Se obtuvo la carpeta con el id " + id);

		} catch (FachadaException e) {
			logger.error("Error al consultar la base (obtener Carpeta()): "
					+ e.getMessage());
			logger.error(e.getMessage());
		}

		HashMap<String, Object> param = new HashMap<String, Object>();
		ResourceBundle rb = ResourceBundle.getBundle("propiedades");
		this.cargarParametros(param, c, rb);
		logger.debug("Se cargaron los datos de la Sabana");
		this.CrearMICDTA(param);
		return SUCCESS;
	}

	public void CrearMICDTA(HashMap<String, Object> parametros) {
		try {

			JasperPrint jp = JasperFillManager.fillReport(jasperReport,
					parametros, new JREmptyDataSource());

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jp, baos);
			logger.debug("Reporte exportado OK.");
			response.setContentLength(baos.size());
			ByteArrayInputStream bis = new ByteArrayInputStream(
					baos.toByteArray());
			inputStream = bis;
		} catch (Exception ex) {
			logger.error("Error al crear SABANA ", ex);
		}

	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	/**
	 * 
	 * @return
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * 
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public void cargarParametros(HashMap<String, Object> param, Carpeta c,
			ResourceBundle rb) {

		if (c.getDespachante() != null
				&& c.getDespachante().getIdEmpresa() != 0) {
			param.put("nombreDespachante", c.getDespachante().getNombre());
			Empresa emp = fac.obtenerEmpresaPorId(c.getDespachante()
					.getIdEmpresa());
			param.put("codigoDespachante", emp.getCodigo());
		}
		if (c.getDestinatario() != null
				&& c.getDestinatario().getIdEmpresa() != 0) {
			param.put("nombreEmpresa", c.getDestinatario().getNombre());
			Empresa emp = fac.obtenerEmpresaPorId(c.getDestinatario()
					.getIdEmpresa());
			param.put("rutEmpresa", emp.getRolContribuyente());
		}

		Calendar hoy = DateUtils.toCalendar(new Date());
		param.put(
				"fechaActual",
				hoy.get(Calendar.DATE) + "      "
						+ (hoy.get(Calendar.MONTH) + 1) + "      "
						+ hoy.get(Calendar.YEAR) + "      ");

		if (StringUtils.isNotBlank(c.getNroContenedor())) {
			param.put(
					"nroContenedor",
					c.getNroContenedor().substring(0,
							c.getNroContenedor().length() - 1));
			param.put(
					"nroContenedorValidacion",
					""
							+ c.getNroContenedor().charAt(
									c.getNroContenedor().length() - 1));
		}

		if (c.getAduanaDestino() != null) {
			param.put("ciudadSaida", c.getAduanaDestino().getDescripcion());
		}
		param.put("nroDocumentoDNA", c.getNroDUA());

		if (c.getTransportadoraCamionSustituto() != null
				&& c.getTransportadoraCamionSustituto().getIdTransportadora() != 0) {
			param.put("nombreTransportadoraCamion", c
					.getTransportadoraCamionSustituto()
					.getNombreTransportadora());
		} else if (c.getTransportadoraCamion() != null) {
			param.put("nombreTransportadoraCamion", c.getTransportadoraCamion()
					.getNombreTransportadora());
		}

		if (c.getCamionSubstituto() != null
				&& c.getCamionSubstituto().getIdCamion() != 0) {
			param.put("matriculaCamion", c.getCamionSubstituto().getMatricula());
		} else if (c.getCamionOriginal() != null) {
			param.put("matriculaCamion", c.getCamionOriginal().getMatricula());
		}
		if (c.getRemolqueSubstituto() != null
				&& c.getRemolqueSubstituto().getIdCamion() != 0) {
			param.put("matriculaRemolque", c.getRemolqueSubstituto()
					.getMatricula());
		} else if (c.getRemolqueOriginal() != null) {
			param.put("matriculaRemolque", c.getRemolqueOriginal()
					.getMatricula());
		}
		param.put("tipoDocumentoChofer", "DP");
		if (c.getChoferSubstituto() != null
				&& c.getChoferSubstituto().getIdResponsable() != 0) {
			param.put("nombreChofer", c.getChoferSubstituto().getNombre() + " "
					+ c.getChoferSubstituto().getApellido());
			param.put("nroDocumentoChofer", c.getChoferSubstituto()
					.getDocumento());
		} else if (c.getChoferOriginal() != null) {
			param.put("nombreChofer", c.getChoferOriginal().getNombre() + " "
					+ c.getChoferOriginal().getApellido());
			param.put("nroDocumentoChofer", c.getChoferOriginal()
					.getDocumento());
		}

	}

}