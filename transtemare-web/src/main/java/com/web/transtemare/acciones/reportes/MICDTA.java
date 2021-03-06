package com.web.transtemare.acciones.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.commons.Utils;
import com.core.transtemare.entidades.Camion;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.Responsable;
import com.core.transtemare.entidades.Transportadora;
import com.core.transtemare.enums.TipoContenedor;
import com.core.transtemare.excepciones.FachadaException;
import com.opensymphony.xwork2.ActionSupport;

public class MICDTA extends ActionSupport implements ServletResponseAware {

	public static JasperReport jasperReport;
	public static JasperReport jasperReportCamionSust;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MICDTA.class);
	private static String EXTENSION_ARCHIVO;
	private static final String EN_LASTRE="En Lastre";
	private HttpServletResponse response;
	private InputStream inputStream;
	private Fachada fac;
	private Carpeta c;
	private Integer id;
	private Boolean camionSust;

	public MICDTA(Fachada fac) {
		super();
		this.fac = fac;
	}

	static {
		try {
			ResourceBundle rb2 = ResourceBundle.getBundle("propiedades");
			EXTENSION_ARCHIVO = rb2.getString("extension.archivos.logos");
			String fileReport = MICDTA.class.getClassLoader()
					.getResource("documentos/MICDTA.jrxml").getFile();
			String fileReportCamionSust = MICDTA.class.getClassLoader()
					.getResource("documentos/MICDTACamionSust.jrxml").getFile();
			logger.info("Compilando el fuente: " + fileReport);
			jasperReport = JasperCompileManager.compileReport(fileReport);
			jasperReportCamionSust=JasperCompileManager.compileReport(fileReportCamionSust);
			logger.info("Se compilo el micdta correctamente");
		} catch (Exception e) {
			logger.error("No se pudo compilar el micdta o el micdta camion sust", e);
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
		HashMap<String, String> paises = new HashMap<String, String>();
		fac.darTodosLosPaisesHash(paises);

		HashMap<String, Object> param = new HashMap<String, Object>();
		ResourceBundle rb = ResourceBundle.getBundle("propiedades");
		this.cargarParametros(param, c, paises, rb);
		logger.debug("Se cargaron los datos del MICDTA");
		this.CrearMICDTA(param);
		return SUCCESS;
	}

	public void CrearMICDTA(HashMap<String, Object> parametros) {
		try {
			JRStyle styles[] = jasperReport.getStyles();
			for (int i = 0; i < styles.length; i++) {
				if ("MarcaYNumeroStyle".equals(styles[i].getName())) {
					styles[i].setFontSize(c.getTamanioLetraMarcaYNumero());
				}
			}
			JasperPrint jp=null;
			if (BooleanUtils.isTrue(this.camionSust)) {
				jp = JasperFillManager.fillReport(jasperReportCamionSust,
						parametros, new JREmptyDataSource());
			} else {
				jp = JasperFillManager.fillReport(jasperReport, parametros,
						new JREmptyDataSource());
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jp, baos);
			logger.debug("Reporte exportado OK.");
			response.setContentLength(baos.size());

			ByteArrayInputStream bis = new ByteArrayInputStream(
					baos.toByteArray());
			inputStream = bis;

		} catch (Exception ex) {
			logger.error("Error al crear MICDTA " + ex.getMessage() + "/"
					+ ex.getStackTrace() + "!" + ex.getLocalizedMessage());
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
			HashMap<String, String> paises, ResourceBundle rb) {
		
		boolean isLastre=TipoContenedor.LASTRE.equals(c.getTipoContenedor());

		param.put("logoPath",
				(c.getTrans().getNombreArchivo() != null) ? MICDTA.class
						.getClassLoader().getResource("documentos/logos")
						.getFile().concat(c.getTrans().getNombreArchivo())
						.concat(EXTENSION_ARCHIVO) : "");

		if (c.getEsCRT()) {
			param.put("nroMICDTA",
					c.getTrans().getPrefijo() + c.getNroDocumento());
		} else {
			param.put("nroMICDTA",
					c.getTrans().getPrefijo() + c.getNumeroDeMic());
		}

		param.put("nroTransmision",
				c.getNroTransmision());
		
		if (c.getTrans().getRolDelContribuyente() != null)
			param.put("RolContribuyente", c.getTrans().getRolDelContribuyente());

		if (c.getChoferOriginal() != null
				&& StringUtils.isNotEmpty(c.getChoferOriginal().getNombre())) {
			Responsable chofer = fac.obtenerChoferPorId(c.getChoferOriginal()
					.getIdResponsable());
			StringBuilder sbChofer = new StringBuilder();
			Transportadora transCamion = c.getTransportadoraCamion();
			if (transCamion != null) {
				sbChofer.append(transCamion.getNombreTransportadora())
						.append("\n")
						.append(transCamion.getLocalidad().getPais()
								.getDescripcion().toUpperCase()).append("\n");
			}

			sbChofer.append("SR. ").append(chofer.getNombre()).append(" ")
					.append(chofer.getApellido()).append(" ");
			if (StringUtils.isNotEmpty(chofer.getDocumento())) {
				sbChofer.append("C.I. ".concat(chofer.getDocumento())).append(
						" ");
			}
			if (StringUtils.isNotEmpty(chofer.getTelefono())) {
				sbChofer.append("\n").append(chofer.getTelefono());
			}

			param.put("responCamionOrig", sbChofer.toString());

		}
		if (c.getChoferSubstituto() != null
				&& StringUtils.isNotEmpty(c.getChoferSubstituto().getNombre())) {
			Responsable chofer2 = fac.obtenerChoferPorId(c
					.getChoferSubstituto().getIdResponsable());
			StringBuilder sbChofer = new StringBuilder();
			Transportadora transCamionSus = c
					.getTransportadoraCamionSustituto();
			if (transCamionSus != null)
				sbChofer.append(transCamionSus.getNombreTransportadora())
						.append("\n")
						.append(transCamionSus.getLocalidad().getPais()
								.getDescripcion().toUpperCase()).append("\n");

			sbChofer.append("SR. ").append(chofer2.getNombre()).append(" ")
					.append(chofer2.getApellido()).append(" ");

			if (StringUtils.isNotEmpty(chofer2.getDocumento())) {
				sbChofer.append("C.I. ".concat(chofer2.getDocumento())).append(
						" ");
			}
			if (StringUtils.isNotEmpty(chofer2.getTelefono())) {
				sbChofer.append("\n").append(chofer2.getTelefono());
			}
			param.put("responCamionSust", sbChofer.toString());
		}
		final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		if (c.getCamionOriginal() != null
				&& c.getCamionOriginal().getIdCamion() != 0) {
			Camion camion = fac.obtenerCamionPorId(c.getCamionOriginal()
					.getIdCamion());
			param.put("matriculaCamionOrig", camion.getMatricula());
			param.put("marcaCamionOrig", camion.getMarca());
			if (camion.getCapacidad() != null)
				param.put("capacidadCamionOrig",
						String.valueOf(camion.getCapacidad()) + " TON");
			if (camion.getAnio() != null) {
				param.put("anioCamionOrig", String.valueOf(camion.getAnio()));
			}
			
		}
		if(c.getRemolqueOriginal()!=null && c.getRemolqueOriginal().getIdCamion()!=0){
			if (c.getRemolqueOriginal().getMatricula() != null) {
				param.put("remolqueCamionOrig", c.getRemolqueOriginal()
						.getMatricula());
			}			
			Camion remolque = fac.obtenerCamionPorId(c.getRemolqueOriginal()
					.getIdCamion());
			StringBuilder sbDatosPolizaCamionOrig = new StringBuilder();
			if (StringUtils.isNotEmpty(remolque.getNumeroPoliza())) {
				sbDatosPolizaCamionOrig.append("POLIZA Nro. "
						+ remolque.getNumeroPoliza());
			}
			if (remolque.getVencimientoPoliza() != null) {
				sbDatosPolizaCamionOrig.append(" VTO: "
						+ DATE_FORMAT.format(remolque.getVencimientoPoliza()));
			}
			param.put("nroVencCamionOrig", sbDatosPolizaCamionOrig.toString());
		}
		if (!"0".equals(c.getTransportadoraCamion().getRolDelContribuyente()))
			param.put("rolContribuyenteOrig", c.getTransportadoraCamion()
					.getRolDelContribuyente());

		if (c.getCamionSubstituto() != null
				&& c.getCamionSubstituto().getIdCamion() != 0) {
			Camion camion = fac.obtenerCamionPorId(c.getCamionSubstituto()
					.getIdCamion());
			if (camion.getMatricula() != null
					&& !"".equals(camion.getMatricula())) {
				param.put("matriculaCamionSust", camion.getMatricula());
				param.put("marcaCamionSust", camion.getMarca());
				if (camion.getCapacidad() != null)
					param.put("capacidadCamionSust",
							String.valueOf(camion.getCapacidad()) + " TON");
				if (camion.getAnio() != null)
					param.put("anioCamionSust",
							String.valueOf(camion.getAnio()));
			
				if (!"0".equals(c.getTransportadoraCamionSustituto().getRolDelContribuyente()))
					param.put("rolContribuyenteSust", c.getTransportadoraCamionSustituto()
							.getRolDelContribuyente());
			}
		}
		if(c.getRemolqueSubstituto()!=null && c.getRemolqueSubstituto().getIdCamion()!=0){
			if (c.getRemolqueSubstituto().getMatricula() != null) {
				param.put("remolqueCamionSust", c.getRemolqueSubstituto()
						.getMatricula());
			}			
			Camion remolque = fac.obtenerCamionPorId(c.getRemolqueSubstituto()
					.getIdCamion());
			StringBuilder sbDatosPolizaCamionOrig = new StringBuilder();
			if (StringUtils.isNotEmpty(remolque.getNumeroPoliza())) {
				sbDatosPolizaCamionOrig.append("POLIZA Nro. "
						+ remolque.getNumeroPoliza());
			}
			if (remolque.getVencimientoPoliza() != null) {
				sbDatosPolizaCamionOrig.append(" VTO: "
						+ DATE_FORMAT.format(remolque.getVencimientoPoliza()));
			}
			param.put("nroVencCamionSust", sbDatosPolizaCamionOrig.toString());
		}

		if (!StringUtils.isEmpty(c.getAduanaDestino().getDescripcion())) {
			param.put("aduanaDestino", c.getAduanaDestino().getDescripcion()
					.toUpperCase());
			param.put("aduanaDestinoCodigo", c.getAduanaDestino().getCodigoAduana());
		}
		if(!isLastre)
			param.put("moneda", c.getMoneda());
		if (c.getOrigenMercaderia().getDescripcion() != null
				&& !c.getOrigenMercaderia().getDescripcion().isEmpty()) {
			param.put("paisOrigen", (c.getOrigenMercaderia().toString()
					.toUpperCase()).split(Utils.ID)[1]);
			param.put("paisOrigenCodigo", c.getOrigenMercaderia().getCodigo());
		}
		if (c.getCiudadPaisDestino() != null
				&& !StringUtils.isEmpty(c.getCiudadPaisDestino()
						.getDescripcion())) {
			param.put("CiudadPaisDestino", (c.getCiudadPaisDestino().toString()
					.toUpperCase()).split(Utils.ID)[1]);
			param.put("ciudadPaisDestinoCodigo", c.getCiudadPaisDestino()
					.getPais().getCodigo());
		}

		param.put("rutasCorto", c.getRutasCorto());
		param.put("validoHasta", c.getValidoHasta());
		

		Map<String, String> listaRutas = new HashMap<String, String>();
		try {
			fac.obtenerTodasLasRutas(listaRutas);
		} catch (FachadaException e) {
			logger.error("Error obteniendo rutas");
		}
		if (StringUtils.isEmpty(c.getRutasLargo())) {
			param.put("Ruta", c.getRuta().getDescripcion());
		} else {
			param.put("Ruta", c.getRutasLargo());
		}

		if (c.getTransitoAduanero() != null && c.getTransitoAduanero()) {
			param.put("transitoAduaneroSI", "x");
		} else if (c.getTransitoAduanero() != null && !c.getTransitoAduanero()) {
			param.put("transitoAduaneroNO", "x");
		}
		// Aduana aduanaOrigen=c.getAduanaOrigen();
		// if(aduanaOrigen.getDescripcion()!=null &&
		// !aduanaOrigen.getDescripcion().equals("")){
		// param.put("aduanaOrigen",
		// (aduanaOrigen.getDescripcion().toString().toUpperCase()).split(Utils.ID)[1]);
		// }

		Localidad aduanaOrigen = c.getCiudadPaisPartida();
		if (aduanaOrigen != null && !aduanaOrigen.toString().equals("")) {
			param.put("aduanaOrigen",
					(aduanaOrigen.toString().toUpperCase()).split(Utils.ID)[1]);
			param.put("aduanaOrigenCodigo", aduanaOrigen.getPais().getCodigo());
		}

		// INICIO DESTINATARIO
		param.put("nombreDestinatario",
				getEmpresaDomicilio(c.getDestinatario()));

		// INICIO EMPRESA CONSIGNATARIA
		param.put("nombreConsignatario",
				getEmpresaDomicilio(c.getConsignatario()));

		// INICIO-NOMBRE Y DOMICILIO DEL REMITENTE
		param.put("nombreRemitente", getEmpresaDomicilio(c.getRemitente()));

		StringBuilder marcaYnumerobultosSB = new StringBuilder();
		if (StringUtils.isNotEmpty(c.getMarcaYnumerobultos()) && !isLastre) {
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
		}else if(isLastre) {
			marcaYnumerobultosSB.append(EN_LASTRE);
		}
		param.put("marcaYNumeroBultos", marcaYnumerobultosSB.toString());
		if(!isLastre) {
			param.put("documentosAnexos", c.getDocumentosAnexos());
			param.put("valorFOT", c.getValorFOT());
			param.put("costoFlete", c.getCostoFlete());
			param.put("seguro", c.getSeguro());
			if (StringUtils.isNotEmpty(c.getTipoBulto().getDescripcion())) {
				param.put("tipo_bulto", c.getTipoBulto().getDescripcion());
			}
			param.put("cant_bultos", c.getCantidadBultos());
			param.put("Peso_bruto", c.getPesoBruto());
		}else {
			param.put("documentosAnexos", EN_LASTRE);
			param.put("valorFOT", EN_LASTRE);
			param.put("costoFlete", EN_LASTRE);
			param.put("seguro", EN_LASTRE);
			param.put("tipo_bulto", EN_LASTRE);
			param.put("cant_bultos", EN_LASTRE);
			param.put("Peso_bruto", EN_LASTRE);
		}
		if(!isLastre)
			param.put("nroCRT", c.getTrans().getPrefijo() + c.getNroDocumento());
		param.put("transitoAduanero", c.getTransitoAduanero());
		param.put("isLastre", isLastre);
		param.put("nroCarpeta", String.valueOf(c.getIdCarpeta()));
		param.put("referenciaDestino", c.getReferenciaDestino());
		param.put("nombreTransportadora", c.getTrans()
				.getNombreTransportadora());
		param.put("nombreCortoDespachante", c.getDespachante() != null ? c
				.getDespachante().getNombreCorto() : null);
		param.put("firmante", StringUtils.isNotBlank(c.getFirmante()) ?
					c.getFirmante() : "Flavio Skunca");
	}

	public String getEmpresaDomicilio(Empresa empresa) {
		StringBuffer ndr = new StringBuffer();
		if (empresa.getIdEmpresa() != 0) {
			ndr.append(StringUtils.isNotEmpty(empresa.getNombre()) ? empresa
					.getNombre() : "");
			ndr.append(" - ");
			if (StringUtils.isNotEmpty(empresa.getDireccion())
					&& !"-".equals(empresa.getDireccion().trim())) {
				ndr.append(StringUtils.isNotEmpty(empresa.getDireccion()) ? empresa
						.getDireccion() : "");
				ndr.append(" - ");
			}
			ndr.append((empresa.getLocalidad() != null && StringUtils
					.isNotEmpty(empresa.getLocalidad().getDescripcion())) ? new String(
					empresa.getLocalidad().toString().toUpperCase())
					.split(Utils.ID)[1] : "");
		}
		return ndr.toString();
	}

	/**
	 * @return the camionSust
	 */
	public Boolean getCamionSust() {
		return camionSust;
	}

	/**
	 * @param camionSust the camionSust to set
	 */
	public void setCamionSust(Boolean camionSust) {
		this.camionSust = camionSust;
	}

}