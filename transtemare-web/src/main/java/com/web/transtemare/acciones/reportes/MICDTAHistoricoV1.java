package com.web.transtemare.acciones.reportes;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Historico;
import com.thoughtworks.xstream.XStream;

public class MICDTAHistoricoV1 extends MICDTA {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger
			.getLogger(MICDTAHistoricoV1.class);

	public MICDTAHistoricoV1(Fachada fac) {
		super(fac);
	}

	public String execute() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("execute() - start"); //$NON-NLS-1$
		}

		try {
			Historico historico = super.getFac()
					.obtenerCarpetaDesdeHistoricoPorId(super.getId());
			XStream xstream = new XStream();
			super.setC((Carpeta) xstream.fromXML(historico.getXml()));

		} catch (Exception e) {
			LOGGER.error("execute() - error", e); //$NON-NLS-1$

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("execute() - end"); //$NON-NLS-1$
			}
			return ERROR;
		}

		HashMap<String, String> paises = new HashMap<String, String>();
		super.getFac().darTodosLosPaisesHash(paises);

		HashMap<String, Object> param = new HashMap<String, Object>();
		ResourceBundle rb = ResourceBundle.getBundle("propiedades");
		super.cargarParametros(param, super.getC(), paises, rb);

		LOGGER.debug("Se cargaron los datos del MICDTA");
		super.CrearMICDTA(param);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("execute() - end"); //$NON-NLS-1$
		}
		return SUCCESS;
	}
}