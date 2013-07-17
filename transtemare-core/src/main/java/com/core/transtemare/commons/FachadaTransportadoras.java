package com.core.transtemare.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.transtemare.daos.daoCamiones;
import com.core.transtemare.daos.daoResponsable;
import com.core.transtemare.daos.daoTransportadoras;
import com.core.transtemare.entidades.Camion;
import com.core.transtemare.entidades.Remolque;
import com.core.transtemare.entidades.Responsable;
import com.core.transtemare.entidades.Transportadora;
import com.core.transtemare.excepciones.FachadaException;

@Transactional
public class FachadaTransportadoras {

	private daoResponsable daoResponsable;
	private daoTransportadoras daoTransportadora;
	private daoCamiones daoCamiones;

	// private daoRemolque daoRemolque;

	public FachadaTransportadoras(daoResponsable daoRes,
			daoTransportadoras daoT, daoCamiones daoCamiones
	/* ,daoRemolque daoRemolque */) {

		this.daoResponsable = daoRes;
		this.daoTransportadora = daoT;
		this.daoCamiones = daoCamiones;
		// this.daoRemolque = daoRemolque;
	}

	public FachadaTransportadoras() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean altaTransportadora(String nombreTransportadora,
			String domicilio, String rolDelContribuyente, int codLocalidad,
			String prefijo, String numerador, String imagen)
			throws FachadaException {

		boolean check = false;

		if (nombreTransportadora != null || domicilio != null
				|| rolDelContribuyente != null) {

			int resultado = daoTransportadora.altaTransportadora(
					nombreTransportadora, domicilio, rolDelContribuyente,
					codLocalidad, prefijo, imagen);
			int resultado2 = daoTransportadora.insertarNumerador(resultado,
					numerador);
			System.out.println("modificarTransportadora: resultado2"
					+ resultado2);
			// TODO flas ver que hacer con resultado2
			if (resultado != 0) {
				check = true;
			}

		}

		return check;
	}

	public void obtenerTodasLasTransportadoras(ArrayList<Transportadora> tra)
			throws FachadaException {

		daoTransportadora.obtenerTodasLasTransportadoras(tra);

	}

	public void obtenerTodasLasTransportadoras(ArrayList<Transportadora> tra,
			int desde, int hasta) throws FachadaException {
		daoTransportadora.obtenerTodasLasTransportadoras(tra, desde, hasta);
	}

	public void obtenerTodasLasTransportadoras(
			HashMap<String, String> transportadoras) throws FachadaException {

		daoTransportadora.obtenerTodasLasTransportadoras(transportadoras);
	}

	public boolean borrarTransportadora(int id) throws FachadaException {

		boolean check = false;

		int resultado = daoTransportadora.borrarTransportadora(id);

		if (resultado != 0) {
			check = true;

		}

		return check;

	}

	public Transportadora obtenerTransportadoraPorId(int id)
			throws FachadaException {

		Transportadora tran = daoTransportadora.obtenerTransportadoraPorId(id);

		return tran;
	}

	public boolean altaChofer(Responsable responsable) throws FachadaException {

		boolean check = false;
		int resultadoChofer = 0;

		resultadoChofer = daoResponsable.altaDeChofer(responsable);

		if (resultadoChofer != 0) {
			check = true;

		}

		return check;

	}

	public void obtenerTodosLosChoferes(ArrayList<Responsable> choferes)
			throws FachadaException {

		daoResponsable.obtenerTodosLosChoferes(choferes);

	}

	public List<Responsable> obtenerTodosLosChoferes(Responsable responsable,
			int desde, int hasta) throws FachadaException {
		return daoResponsable
				.obtenerTodosLosChoferes(responsable, desde, hasta);
	}

	public void obtenerTodosLosChoferes(HashMap<String, String> choferes)
			throws FachadaException {
		daoResponsable.obtenerTodosLosChoferes(choferes);
	}

	public void obtenerTodosLosCamiones(ArrayList<Camion> listaCamiones)
			throws FachadaException {

		daoCamiones.obtenerTodosLosCamiones(listaCamiones);

	}

	public void obtenerTodosLosRemolques(ArrayList<Camion> listaCamiones)
			throws FachadaException {

		daoCamiones.obtenerTodosLosRemolques(listaCamiones);

	}

	public List<Camion> obtenerTodosLosCamiones(Camion camion, int desde,
			int hasta) throws FachadaException {

		return daoCamiones.obtenerTodosLosCamiones(camion, desde, hasta);

	}

	public void obtenerTodosLosCamiones(HashMap<String, String> camiones)
			throws FachadaException {

		daoCamiones.obtenerTodosLosCamiones(camiones);

	}

	public boolean modificarTransportadora(Integer idTransportadora,
			String nombreTransportadora, String domicilio,
			String rolDelContribuyente, String prefijo, int idLocalidad,
			String numerador, String imagen) throws FachadaException {

		boolean check = false;

		int resultado = daoTransportadora.modificarTransportadora(
				idTransportadora, nombreTransportadora, domicilio,
				rolDelContribuyente, idLocalidad, prefijo, imagen);

		int resultado2 = daoTransportadora.modificarNumerador(idTransportadora,
				numerador);
		// TODO flas ver que hacer con resultado 2
		System.out.println("modificarTransportadora: resultado2" + resultado2);
		if (resultado != 0) {
			check = true;
		}
		return check;

	}

	public Camion obtenerCamionPorId(int id) throws FachadaException {
		return daoCamiones.obtenerCamionPorId(id);
	}

	public boolean modificarChofer(Responsable responsable)
			throws FachadaException {

		boolean check = false;
		int resultadoChofer = 0;
		try {
			resultadoChofer = daoResponsable.modificarChofer(responsable);

			if (resultadoChofer != 0) {
				check = true;
			}
		} catch (Exception e) {

			throw new FachadaException(e.getMessage());
		}
		return check;

	}

	public Responsable obtenerChoferPorId(int id) throws FachadaException {
		Responsable responsable = null;
		responsable = daoResponsable.obtenerResponsablePorId(id);

		return responsable;
	}

	public boolean borrarResponsable(int id) throws FachadaException {
		int resultado = 0;
		boolean check = false;
		resultado = daoResponsable.borrarResponsable(id);
		if (resultado != 0) {
			check = true;
		}
		return check;
	}

	public boolean altaCamion(Camion camion) throws FachadaException {
		int resultadoCamion = 0;
		resultadoCamion = daoCamiones.altaCamion(camion);

		return resultadoCamion != 0 ? true : false;
	}

	public boolean modificarCamion(Camion camion) throws FachadaException {
		boolean check = false;
		int resultadoCamion = 0;
		resultadoCamion = daoCamiones.modificarCamion(camion);

		if (resultadoCamion != 0) {
			check = true;
		}
		return check;
	}

	public boolean borrarCamion(int id) throws FachadaException {
		boolean check = false;
		int resultado = daoCamiones.borrarCamion(id);
		if (resultado != 0) {
			check = true;
		}
		return check;
	}

	@Deprecated
	public ArrayList<Remolque> obtenerTodosLosRemolques()
			throws FachadaException {
		ArrayList<Remolque> remolques = null;
		// remolque = daoRemolque.obtenerTodosLosRemolques(con);
		return remolques;
	}

	@Deprecated
	public void obtenerTodosLosRemolques(HashMap<String, String> remolques)
			throws FachadaException {
		// daoRemolque.obtenerTodosLosRemolques(remolques, con);
	}

	public void obtenerTodasLasMarcasDeCamiones(ArrayList<String> camiones)
			throws FachadaException {
		daoCamiones.obtenerTodasLasMarcasDeCamiones(camiones);
	}

	public Integer totalTransportadoras() throws FachadaException {
		return daoTransportadora.totalTransportadoras();
	}

	public Integer totalChoferes() throws FachadaException {
		return daoResponsable.totalChoferes();
	}

	public Integer obtenerTotalCamiones() throws FachadaException {
		return daoCamiones.totalCamiones();
	}

}
