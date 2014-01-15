package com.core.transtemare.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.core.transtemare.daos.daoCarpeta;
import com.core.transtemare.daos.daoEmpresas;
import com.core.transtemare.daos.daoLocalidades;
import com.core.transtemare.entidades.Bulto;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.entidades.Historico;
import com.core.transtemare.entidades.NumerosCarpetaDocumento;
import com.core.transtemare.entidades.Terminal;
import com.core.transtemare.entidades.Transportadora;
import com.core.transtemare.excepciones.FachadaException;

@Transactional
public class FachadaCarpetas {

	private static final Logger logger = Logger
			.getLogger(FachadaCarpetas.class);
	private daoCarpeta daoCa;
	private daoEmpresas daoEm;
	private daoLocalidades daoLoc;

	public FachadaCarpetas(daoCarpeta daoCa, daoEmpresas daoEm,
			daoLocalidades daoLoc) {
		this.daoCa = daoCa;
		this.daoEm = daoEm;
		this.daoLoc = daoLoc;
	}

	public FachadaCarpetas() {
		super();
	}

	public List<Carpeta> alarmaCarpetas(int desde, int hasta)
			throws FachadaException {
		try {
			return daoCa.AlarmaCarpetas(desde, hasta);

		} catch (Exception e) {

			throw new FachadaException(e.getMessage());
		}
	}

	public void obtenerTodasLasAduanas(HashMap<String, String> aduanas)
			throws FachadaException {
		try {
			daoLoc.obtenerTodasLasAduanas(aduanas);
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
	}

	public void obtenerTodasLasEmpresas(HashMap<String, String> empresas,
			byte tipo) throws FachadaException {
		try {
			daoEm.obtenerTodasLasEmpresas(empresas, tipo);
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
	}

	public void obtenerTodasLasEmpresas(ArrayList<Empresa> empresas)
			throws FachadaException {
		try {
			daoEm.obtenerTodasLasEmpresas(empresas);
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
	}

	public void obtenerEmpresasPorTipo(ArrayList<Empresa> empresas, byte tipo)
			throws FachadaException {
		try {
			daoEm.obtenerEmpresasPorTipo(empresas, tipo);
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
	}

	public List<Empresa> obtenerEmpresasTabla(Empresa empresa, int desde,
			int hasta) throws FachadaException {
		try {
			return daoEm.obtenerEmpresasTabla(empresa, desde, hasta);
		} catch (Exception e) {
			logger.error("Error al obtener empresas", e);
			throw new FachadaException(e.getMessage());
		}
	}

	public void obtenerTodosLosBultos(HashMap<String, String> tipoDeBultos)
			throws FachadaException {
		try {
			daoCa.obtenerTodosLosBultos(tipoDeBultos);
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
	}

	public void obtenerTodosLosBultos(List<Bulto> tipoDeBultos)
			throws FachadaException {
		try {
			daoCa.obtenerTodosLosBultos(tipoDeBultos);
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
	}

	public boolean modificarCarpeta(Carpeta carpeta) throws FachadaException {
		boolean check = false;

		try {

			logger.info("Modificando carpeta: " + carpeta.getIdCarpeta());
			int resultado = daoCa.modificarCarpeta(carpeta);

			if (resultado != 0) {
				check = true;
				logger.info("Modificando carpeta: " + carpeta.getIdCarpeta()
						+ " - Realizando el commit");

			}

		} catch (Exception e) {
			logger.error("Modificando carpeta: " + carpeta.getIdCarpeta(), e);
			throw new FachadaException(e.getMessage());
		}
		return check;
	}

	public NumerosCarpetaDocumento obtenerNumerosCarpetaDocumento(
			Transportadora transportadora, Integer cantidadMIC)
			throws FachadaException {
		NumerosCarpetaDocumento numeros;
		try {
			numeros = daoCa.obtenerNumerosCarpetaDocumento(transportadora,
					cantidadMIC);
		} catch (Exception e) {
			logger.error(e);
			throw new FachadaException(e.getMessage());
		}
		return numeros;
	}

	public List<Historico> obtenerHistoricoCarpetas(int desde, int hasta) {

		try {
			return daoCa.obtenerHistoricoCarpetas(desde, hasta);

		} catch (Exception e) {
			logger.error(e);
			throw new FachadaException(e.getMessage());
		}

	}

	public List<Carpeta> obtenerCarpetas(Carpeta carpeta, boolean historico,
			int desde, int hasta) {
		try {
			return daoCa.obtenerCarpetas(carpeta, historico, desde, hasta);

		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
	}

	public List<Carpeta> obtenerCarpetasCargaGarantia(Carpeta carpeta) {
		try {
			return daoCa.obtenerCarpetasCargaGarantia(carpeta);

		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
	}

	public List<Carpeta> obtenerSubCarpetas(boolean historico,
			int idCarpetaPadre) {

		try {
			return daoCa.obtenerSubCarpetas(historico, idCarpetaPadre);

		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}

	}

	public Carpeta obtenerCarpeta(int id) throws FachadaException {
		Carpeta carpeta = null;
		try {
			carpeta = daoCa.obtenerCarpetaPorId(id);
			daoLoc.getLocalidadesPorIds(carpeta.getLocalidades());
			daoLoc.getLocalidadesPorIds(carpeta.getAduanas());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FachadaException(e.getMessage());
		}
		return carpeta;
	}

	public Historico obtenerCarpetaDesdeHistoricoPorId(int id)
			throws FachadaException {
		Historico historico = null;
		try {
			historico = daoCa.obtenerCarpetaDesdeHistoricoPorId(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new FachadaException(e.getMessage());
		}
		return historico;
	}

	public Empresa obtenerEmpresaPorId(int id) throws FachadaException {
		Empresa empresa = null;
		try {
			empresa = daoEm.obtenerEmpresaId(id);
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
		return empresa;
	}

	public boolean modificarEmpresa(Empresa empresa) throws FachadaException {
		boolean check = false;
		try {
			int resultado = daoEm.modificarEmpresa(empresa);

			if (resultado != 0) {
				check = true;
			}

		} catch (Exception e) {
			logger.error("Empresa:" + empresa, e);
			throw new FachadaException(e.getMessage());
		}
		return check;
	}

	public boolean altaEmpresa(Empresa empresa) throws FachadaException {
		boolean check = false;
		try {
			int resultado = daoEm.altaEmpresa(empresa);
			if (resultado != 0) {
				check = true;
			}
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
		return check;
	}

	public boolean borrarEmpresa(int id) throws FachadaException {
		boolean check = false;
		try {
			int resultado = daoEm.borrarEmpresa(id);
			if (resultado != 0) {
				check = true;
			}
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
		return check;
	}

	public Integer totalEmpresas() throws FachadaException {
		Integer resultado;
		try {
			resultado = daoEm.totalEmpresas();
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
		return resultado;
	}

	public Integer totalCarpetas() throws FachadaException {
		Integer resultado;
		try {
			resultado = daoCa.totalCarpetas();

		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
		return resultado;
	}

	public Integer totalCarpetasHistorico() throws FachadaException {
		Integer resultado;
		try {
			resultado = daoCa.totalCarpetasHistorico();

		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
		return resultado;
	}

	public Integer totalAlarmas() throws FachadaException {
		Integer resultado;
		try {
			resultado = daoCa.totalAlarmas();

		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
		return resultado;
	}

	public Integer pasarHistoricoXML(Carpeta carpeta, String xml) {
		Integer resultado;
		try {
			resultado = daoCa.pasarHistoricoXML(carpeta, xml);

		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
		return resultado;
	}

	public List<Terminal> obtenerTerminalesTabla(int from, int to) {
		return daoCa.obtenerTerminalesTabla(from, to);
	}

	public Integer totalTerminales() {
		return daoCa.totalTerminales();
	}

	public void crearModificarTerminal(String id, String nombre) {
		daoCa.crearModificarTerminal(id, nombre);
	}

	public void eliminarTerminal(Integer id) {
		daoCa.eliminarTerminal(id);
	}

	public void obtenerTodasLasTerminales(List<Terminal> terminales)
			throws FachadaException {
		try {
			daoCa.obtenerTerminales(terminales);
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
	}

}
