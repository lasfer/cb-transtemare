package com.core.transtemare.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.transtemare.entidades.Aduana;
import com.core.transtemare.entidades.Bulto;
import com.core.transtemare.entidades.Camion;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.entidades.Historico;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.NumerosCarpetaDocumento;
import com.core.transtemare.entidades.Pais;
import com.core.transtemare.entidades.Responsable;
import com.core.transtemare.entidades.Ruta;
import com.core.transtemare.entidades.Terminal;
import com.core.transtemare.entidades.Transportadora;
import com.core.transtemare.entidades.User;
import com.core.transtemare.excepciones.FachadaException;

public class Fachada {

	private FachadaTransportadoras fT;
	private FachadaCarpetas fC;
	private FachadaComunes fCo;

	public Fachada() {
	}

	public Fachada(FachadaTransportadoras fT, FachadaCarpetas fC,
			FachadaComunes fCo) {
		this.fT = fT;
		this.fC = fC;
		this.fCo = fCo;
	}

	public boolean altaTransportadora(String nombreTransportadora,
			String domicilio, String rolDelContribuyente, String pais,
			String ciudad, String prefijo, String numerador, String imagen)
			throws FachadaException {
		int idLocalidad = 0;
		idLocalidad = fCo.obtenerCodLocalidad(ciudad);
		boolean check = false;
		if (idLocalidad == 0) {
			throw new FachadaException("Error al obtener Codigo de Localidad");
		} else {
			check = fT.altaTransportadora(nombreTransportadora, domicilio,
					rolDelContribuyente, idLocalidad, prefijo, numerador,
					imagen);
		}
		return check;
	}

	public boolean altaEmpresa(Empresa empresa) throws FachadaException {
		int idLocalidad = 0;
		idLocalidad = fCo.obtenerCodLocalidad(empresa.getLocalidad()
				.getDescripcion());
		empresa.getLocalidad().setIdLocalidad(idLocalidad);
		boolean check = false;
		if (idLocalidad == 0) {
			throw new FachadaException("Error al obtener Codigo de Localidad");
		} else {
			check = fC.altaEmpresa(empresa);
		}
		return check;
	}

	public List<Carpeta> alarmaCarpetas(int desde, int hasta)
			throws FachadaException {
		return fC.alarmaCarpetas(desde, hasta);
	}

	public List<Localidad> darTodasLasLocalidades(int desde, int hasta)
			throws FachadaException {
		return fCo.darTodasLasLocalidades(desde, hasta);
	}

	public void darTodasLasLocalidades(ArrayList<Localidad> loca)
			throws FachadaException {
		fCo.darTodasLasLocalidades(loca);
	}

	public void darTodasLasAduanas(ArrayList<Aduana> aduanas)
			throws FachadaException {
		fCo.darTodasLasAduanas(aduanas);
	}

	public void darTodasLasLocalidadesHash(HashMap<String, String> local)
			throws FachadaException {
		fCo.darTodasLasLocalidadesHash(local);
	}

	public void darTodosLosPaises(ArrayList<Pais> arr) throws FachadaException {
		fCo.darTodosLosPaises(arr);
	}

	public void darTodosLosPaisesHash(HashMap<String, String> paises)
			throws FachadaException {
		fCo.darTodosLosPaisesHash(paises);
	}

	public void obtenerTodasLasTransportadoras(ArrayList<Transportadora> tran)
			throws FachadaException {
		fT.obtenerTodasLasTransportadoras(tran);
	}

	public void obtenerTodasLasTransportadoras(ArrayList<Transportadora> tran,
			int desde, int hasta) throws FachadaException {
		fT.obtenerTodasLasTransportadoras(tran, desde, hasta);
	}

	public void obtenerTodasLasTransportadoras(
			HashMap<String, String> transportadoras) throws FachadaException {
		fT.obtenerTodasLasTransportadoras(transportadoras);
	}

	public boolean borrarTransportadora(int id) throws FachadaException {
		return fT.borrarTransportadora(id);
	}

	public boolean borrarEmpresa(int id) throws FachadaException {
		return fC.borrarEmpresa(id);
	}

	public Transportadora obtenerTransportadoraPorId(int id)
			throws FachadaException {
		return fT.obtenerTransportadoraPorId(id);

	}

	public boolean altaChofer(Responsable responsable) throws FachadaException {
		boolean check = false;
		int idLocalidad = fCo.obtenerCodLocalidad(responsable.getLocalidad()
				.getDescripcion());
		responsable.getLocalidad().setIdLocalidad(idLocalidad);
		if (idLocalidad == 0) {
			throw new FachadaException("Error al obtener Codigo de Localidad");
		} else {
			check = fT.altaChofer(responsable);
		}
		return check;

	}

	public NumerosCarpetaDocumento obtenerNumerosCarpetaDocumento(
			Transportadora transportadora, Integer cantidadMIC)
			throws FachadaException {
		return fC.obtenerNumerosCarpetaDocumento(transportadora, cantidadMIC);
	}

	public void obtenerTodosLosChoferes(ArrayList<Responsable> choferes)
			throws FachadaException {
		fT.obtenerTodosLosChoferes(choferes);
	}

	public List<Responsable> obtenerTodosLosChoferes(Responsable responsable,
			int desde, int hasta) throws FachadaException {
		return fT.obtenerTodosLosChoferes(responsable, desde, hasta);

	}

	public void obtenerTodosLosChoferes(HashMap<String, String> choferes)
			throws FachadaException {
		fT.obtenerTodosLosChoferes(choferes);

	}

	public Integer totalCamiones() throws FachadaException {
		return fT.obtenerTotalCamiones();
	}

	public List<Camion> obtenerTodosLosCamiones(Camion camion, int desde,
			int hasta) throws FachadaException {
		return fT.obtenerTodosLosCamiones(camion, desde, hasta);
	}

	public void obtenerTodosLosCamiones(ArrayList<Camion> listaCamiones)
			throws FachadaException {
		fT.obtenerTodosLosCamiones(listaCamiones);
	}

	public void obtenerTodosLosRemolques(ArrayList<Camion> listaCamiones)
			throws FachadaException {
		fT.obtenerTodosLosRemolques(listaCamiones);
	}

	public void obtenerTodosLosCamiones(HashMap<String, String> camiones)
			throws FachadaException {
		fT.obtenerTodosLosCamiones(camiones);
	}

	public boolean modificarTransportadora(Integer idTransportadora,
			String nombreTransportadora, String domicilio,
			String rolDelContribuyente, String pais, String ciudad,
			String prefijo, String numerador, String imagen)
			throws FachadaException {
		int idLocalidad = fCo.obtenerCodLocalidad(ciudad);
		boolean check = false;
		if (idLocalidad == 0) {
			throw new FachadaException("Error al obtener Codigo de Localidad");
		} else {
			check = fT.modificarTransportadora(idTransportadora,
					nombreTransportadora, domicilio, rolDelContribuyente,
					prefijo, idLocalidad, numerador, imagen);
		}
		return check;
	}

	public Camion obtenerCamionPorId(int id) throws FachadaException {
		return fT.obtenerCamionPorId(id);
	}

	public boolean modificarChofer(Responsable responsable)
			throws FachadaException {
		int idLocalidad = fCo.obtenerCodLocalidad(responsable.getLocalidad()
				.getDescripcion());
		responsable.getLocalidad().setIdLocalidad(idLocalidad);
		if (idLocalidad == 0) {
			throw new FachadaException("Error al obtener Codigo de Localidad");
		} else {
			return fT.modificarChofer(responsable);
		}
	}

	public Responsable obtenerChoferPorId(int id) throws FachadaException {
		return fT.obtenerChoferPorId(id);
	}

	public FachadaTransportadoras fachadaTransportadoras() {
		return fT;
	}

	public boolean borrarChofer(int id) throws FachadaException {
		return fT.borrarResponsable(id);
	}

	public boolean altaCamion(Camion camion) throws FachadaException {
		return fT.altaCamion(camion);
	}

	public boolean modificarCamion(Camion camion) throws FachadaException {
		return fT.modificarCamion(camion);
	}

	public boolean borrarCamion(int id) throws FachadaException {
		return fT.borrarCamion(id);
	}

	public boolean altaLocalidad(Localidad localidad) throws FachadaException {
		return fCo.altaLocalidad(localidad);
	}

	public boolean modificarLocalidad(Localidad localidad)
			throws FachadaException {
		return fCo.modificarLocalidad(localidad);
	}

	public boolean borrarLocalidad(int idLocalidad) throws FachadaException {
		return fCo.borrarLocalidad(idLocalidad);
	}

	public void obtenerTodasLasAduanas(HashMap<String, String> aduanas)
			throws FachadaException {
		fC.obtenerTodasLasAduanas(aduanas);
	}

	public void obtenerTodasLasRutas(Map<String, String> ruta)
			throws FachadaException {
		fCo.obtenerTodasLasRutas(ruta);
	}

	public void obtenerTodasLasRutas(List<Ruta> ruta) throws FachadaException {
		fCo.obtenerTodasLasRutas(ruta);
	}

	public void obtenerTodasLasEmpresas(HashMap<String, String> empresas,
			byte tipo) throws FachadaException {
		fC.obtenerTodasLasEmpresas(empresas, tipo);

	}

	public void obtenerTodasLasEmpresas(ArrayList<Empresa> empresas)
			throws FachadaException {
		fC.obtenerTodasLasEmpresas(empresas);

	}

	public void obtenerEmpresasPorTipo(ArrayList<Empresa> empresas, byte tipo)
			throws FachadaException {
		fC.obtenerEmpresasPorTipo(empresas, tipo);

	}

	public List<Empresa> obtenerEmpresasTabla(Empresa empresa, int desde,
			int hasta) throws FachadaException {
		return fC.obtenerEmpresasTabla(empresa, desde, hasta);
	}

	public void obtenerTodosLosBultos(HashMap<String, String> tipoDeBultos)
			throws FachadaException {
		fC.obtenerTodosLosBultos(tipoDeBultos);

	}

	public void obtenerTodosLosBultos(List<Bulto> tipoDeBultos)
			throws FachadaException {
		fC.obtenerTodosLosBultos(tipoDeBultos);

	}

	public boolean modificarCarpeta(Carpeta carpeta) throws FachadaException {
		return fC.modificarCarpeta(carpeta);
	}

	public List<Historico> obtenerCarpetasHistorico(int desde, int hasta)
			throws FachadaException {
		return fC.obtenerHistoricoCarpetas(desde, hasta);
	}

	public List<Carpeta> obtenerCarpetas(Carpeta carpeta, boolean historico, int desde, int hasta)
			throws FachadaException {
		return fC.obtenerCarpetas(carpeta, historico, desde, hasta);
	}

	public List<Carpeta> obtenerSubCarpetas(boolean historico, int idCarpetPadre)
			throws FachadaException {
		return fC.obtenerSubCarpetas(historico, idCarpetPadre);
	}

	public Carpeta obtenerCarpeta(int id) throws FachadaException {
		return fC.obtenerCarpeta(id);

	}

	public Historico obtenerCarpetaDesdeHistoricoPorId(int id)
			throws FachadaException {
		return fC.obtenerCarpetaDesdeHistoricoPorId(id);

	}

	public void obtenerTodasLasMarcasDeCamiones(ArrayList<String> camiones)
			throws FachadaException {
		fT.obtenerTodasLasMarcasDeCamiones(camiones);

	}

	public Empresa obtenerEmpresaPorId(int id) throws FachadaException {
		return fC.obtenerEmpresaPorId(id);
	}

	public boolean modificarEmpresa(Empresa empresa) throws FachadaException {
		int idLocalidad = fCo.obtenerCodLocalidad(empresa.getLocalidad()
				.getDescripcion());
		empresa.getLocalidad().setIdLocalidad(idLocalidad);
		boolean check = false;
		if (idLocalidad == 0) {
			throw new FachadaException("Error al obtener Codigo de Localidad");
		} else {
			check = fC.modificarEmpresa(empresa);
		}
		return check;
	}

	public Integer totalTransportadoras() throws FachadaException {
		return fT.totalTransportadoras();
	}

	public Integer totalChoferes() throws FachadaException {
		return fT.totalChoferes();
	}

	public Integer totalEmpresas() throws FachadaException {
		return fC.totalEmpresas();
	}

	public Integer totalCarpetas() throws FachadaException {
		return fC.totalCarpetas();
	}

	public Integer totalCarpetasHistoricos() throws FachadaException {
		return fC.totalCarpetasHistorico();
	}

	public Integer totalAlarmas() throws FachadaException {
		return fC.totalAlarmas();
	}

	public User obtenerUsuarioRegistrado(String usuario, String contrasena) {
		return fCo.obtenerUsuarioRegistrado(usuario, contrasena);
	}

	public void pasarHistoricoXML(Carpeta carpeta, String xml) {
		fC.pasarHistoricoXML(carpeta, xml);
	}

	public void obtenerTodosLosUsuarios(ArrayList<User> users, int from, int to) {
	}

	public Integer totalUsuarios() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Terminal> obtenerTerminalesTabla(int from, int to) {
		return fC.obtenerTerminalesTabla(from, to);
	}

	public Integer totalTerminales() {
		return fC.totalTerminales();
	}

	public void crearModificarTerminal(String id, String nombre) {
		fC.crearModificarTerminal(id, nombre);

	}

	public void eliminarTerminal(Integer id) {
		fC.eliminarTerminal(id);

	}

	public void obtenerTodasLasTerminales(List<Terminal> terminales) {
		fC.obtenerTodasLasTerminales(terminales);
	}

}
