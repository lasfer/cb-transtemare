package com.web.transtemare.acciones.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Historico;
import com.thoughtworks.xstream.XStream;

public class CRTHistoricoV1 extends CRT {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CRTHistoricoV1.class);
	private Carpeta c;

	public CRTHistoricoV1(Fachada fac) {
		super(fac);
	}

	public String execute() {

		try {
			Historico historico = super.getFac()
					.obtenerCarpetaDesdeHistoricoPorId(super.getId());
			XStream xstream = new XStream();
			c = (Carpeta) xstream.fromXML(historico.getXml());
			logger.info("Se obtuvo la carpeta del xml correctamente");

		} catch (Exception e) {
			logger.error("Error al consultar en la base: ", e);
			return ERROR;
		}

		ResourceBundle rb = ResourceBundle.getBundle("propiedades");

		HashMap<String, String> paises = new HashMap<String, String>();
		super.getFac().darTodosLosPaisesHash(paises);
		HashMap<String, Object> param = new HashMap<String, Object>();
		this.cargarParametros(param, c, paises, rb);

		logger.debug("Se cargaron los datos en el map");
		this.crearCRT(param);
		return SUCCESS;
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
			logger.debug("Reporte exportado OK.");
			super.getServletResponse().setContentLength(baos.size());

			ByteArrayInputStream bis = new ByteArrayInputStream(
					baos.toByteArray());
			super.setInputStream2(bis);

		} catch (Exception ex) {
			logger.error("Error al crear CRT  " + ex.getMessage() + "/"
					+ ex.getStackTrace() + "!" + ex.getLocalizedMessage());
		}
	}

}