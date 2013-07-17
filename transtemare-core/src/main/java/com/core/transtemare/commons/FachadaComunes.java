package com.core.transtemare.commons;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.core.transtemare.daos.daoLocalidades;
import com.core.transtemare.daos.daoPaises;
import com.core.transtemare.daos.daoResponsable;
import com.core.transtemare.daos.daoRutas;
import com.core.transtemare.entidades.Aduana;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.Pais;
import com.core.transtemare.entidades.Ruta;
import com.core.transtemare.entidades.User;
import com.core.transtemare.excepciones.FachadaException;

@Transactional
public class FachadaComunes {
	private static final Logger logger = Logger.getLogger(FachadaComunes.class);
	private daoPaises daoP;
	private daoLocalidades daoL;
	private daoRutas daoRut;
	private daoResponsable daoRes;

	public FachadaComunes(daoPaises daoP, daoLocalidades daoL, daoRutas daoRut,
			daoResponsable daoRes) {
		this.daoP = daoP;
		this.daoL = daoL;
		this.daoRut = daoRut;
		this.daoRes = daoRes;
	}

	public FachadaComunes() {
		super();
	}

	public int obtenerCodPaisPorNombre(String nombrePais)
			throws FachadaException {
		return daoP.obtenerPaisPorNombre(nombrePais);
	}

	public int obtenerCodLocalidad(String ciudad) throws FachadaException {

		return daoL.obtenerLocalidadPorNombre(ciudad);

	}
	
	public List<Localidad> darTodasLasLocalidades(int desde, int hasta)
			throws FachadaException {

		return daoL.getTodasLasLocalidades(desde,hasta);
	}

	public void darTodasLasLocalidades(List<Localidad> loc)
			throws FachadaException {

		daoL.getTodasLasLocalidades(loc);
	}

	public void darTodasLasAduanas(ArrayList<Aduana> loc)
			throws FachadaException {
		try {
			daoL.getTodasLasAduanas(loc);
		} catch (Exception e) {
			logger.error("error", e);
			throw new FachadaException(e.getMessage());
		}

	}

	public void darTodasLasLocalidadesHash(HashMap<String, String> local)
			throws FachadaException {

		daoL.getTodasLasLocalidadesHash(local);
	}

	public void darTodosLosPaises(ArrayList<Pais> paises)
			throws FachadaException {

		daoP.getTodosLosPaises(paises);

	}

	public void darTodosLosPaisesHash(HashMap<String, String> paises)
			throws FachadaException {

		daoP.getTodosLosPaisesHash(paises);

	}

	public void obtenerTodasLasRutas(Map<String, String> ruta)
			throws FachadaException {

		daoRut.obtenerTodasLasRutas(ruta);

	}

	public void obtenerTodasLasRutas(List<Ruta> ruta) throws FachadaException {
		try {
			daoRut.obtenerTodasLasRutas(ruta);
		} catch (Exception e) {
			logger.error("obtenerTodasLasRutas", e);
			throw new FachadaException(e.getMessage());
		}
	}

	public Ruta ObtenerRuta(Connection con, int codRuta)
			throws FachadaException {
		Ruta rut = new Ruta();
		try {
			rut = daoRut.obteneRruta(codRuta);

		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
		return rut;
	}

	public User obtenerUsuarioRegistrado(String usuario, String contrasena) {
		try {
			return daoRes.obtenerUsuarioRegistrado(usuario, contrasena);

		} catch (Exception e) {
			logger.error("Error al obtener el usuario", e);
			e.printStackTrace();
			throw new FachadaException(e.getMessage());
		}
	}
	
	public boolean altaLocalidad(Localidad localidad) throws FachadaException {
		boolean check = false;
		int resultadoLocalidad = 0;
		resultadoLocalidad = daoL.altaLocalidad(localidad);
		if (resultadoLocalidad != 0) {
			check = true;
		}
		return check;
	}

	public boolean modificarLocalidad(Localidad localidad)
			throws FachadaException {
		boolean check = false;
		int resultadoLocalidad = 0;
		try {
			resultadoLocalidad = daoL.modificarLocalidad(localidad);
			if (resultadoLocalidad != 0) {
				check = true;
			}
		} catch (Exception e) {
			throw new FachadaException(e.getMessage());
		}
		return check;
	}
	
	public boolean borrarLocalidad(int idLocalidad) throws FachadaException {
		boolean check=false;
		int resultado = daoL.borrarLocalidad(idLocalidad);
		if (resultado != 0) {
			check = true;
		}
		return check;
	}
}
