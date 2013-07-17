package com.web.transtemare.acciones.reportes;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Historico;
import com.core.transtemare.excepciones.FachadaException;
import com.thoughtworks.xstream.XStream;

public class CaratulaHistoricoV1 extends Caratula implements
		ServletResponseAware {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(CaratulaHistoricoV1.class);

	public CaratulaHistoricoV1(Fachada fac) {
		super(fac);
	}

	public String execute() {
		try {
			Historico historico = super.getFac()
					.obtenerCarpetaDesdeHistoricoPorId(super.getId());
			XStream xstream = new XStream();
			super.setC((Carpeta) xstream.fromXML(historico.getXml()));
			logger.info("Se obtuvo la carpeta del xml correctamente");
		} catch (FachadaException e) {
			logger.error(e.getMessage());
			logger.error("Error al consultar en la base: " + e.getMessage());
		}
		HashMap<String, Object> parametrosReporte = new HashMap<String, Object>();

		super.cargarParametrosReporte(parametrosReporte);

		this.generarCaratula_(parametrosReporte);
		return SUCCESS;
	}
}
