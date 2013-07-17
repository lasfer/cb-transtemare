package com.core.transtemare.daos.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.core.transtemare.commons.Utils;
import com.core.transtemare.entidades.Aduana;
import com.core.transtemare.entidades.Bulto;
import com.core.transtemare.entidades.Camion;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.entidades.Empresa;
import com.core.transtemare.entidades.Localidad;
import com.core.transtemare.entidades.Pais;
import com.core.transtemare.entidades.Responsable;
import com.core.transtemare.entidades.Ruta;
import com.core.transtemare.entidades.Terminal;
import com.core.transtemare.entidades.Transportadora;
import com.core.transtemare.enums.TipoContenedor;

public class CarpetaMapper implements RowMapper<Carpeta> {

	public Carpeta mapRow(ResultSet rs, int rowNum) throws SQLException {

		Carpeta carpeta = null;
		Transportadora trans = null;
		Transportadora transCamion = null;
		Camion camOriginal = null;
		Camion semiOriginal = null;
		Camion semiSus = null;
		Responsable resOriginal = null;
		Camion camSus = null;
		Responsable resSus = null;
		Empresa empRem = null;
		Empresa empDest = null;
		Empresa empCons = null;
		Empresa empNotif = null;
		Aduana adOrigen = null;
		Aduana adDestino = null;
		Bulto bulto = null;
		Transportadora transportadoraCamionSustituto = null;

		HashMap<Integer, Localidad> localidades = new HashMap<Integer, Localidad>();
		HashMap<Integer, Aduana> aduanas = new HashMap<Integer, Aduana>();
		carpeta = new Carpeta();
		trans = new Transportadora();
		transCamion = new Transportadora();
		adOrigen = new Aduana();
		adDestino = new Aduana();
		camOriginal = new Camion();
		resOriginal = new Responsable();
		camSus = new Camion();
		resSus = new Responsable();
		empRem = new Empresa();
		empDest = new Empresa();
		empCons = new Empresa();
		empNotif = new Empresa();
		bulto = new Bulto();
		Ruta ruta = new Ruta();
		transportadoraCamionSustituto = new Transportadora();
		semiOriginal = new Camion();
		semiSus = new Camion();

		carpeta.setIdCarpeta(rs.getInt("idCarpeta"));
		carpeta.setFechaAlta(Utils.convertToUtilDate(rs.getTimestamp("FxAlta")));
		carpeta.setNombreFirmaDestinatario(rs
				.getString("nombreFirmaDestinatario"));
		// carpeta.setFechaEmision(Utils.convertToUtilDate(rs.getDate("FxEmision")));
		carpeta.setFechaModificacion(Utils.convertToUtilDate(rs
				.getTimestamp("FxMod")));
		carpeta.setTransitoAduanero(rs.getBoolean("TrAduanero"));

		// Pais de origen , trae pais ciudad
		int idLocalidadOrigen = rs.getInt("LocOriId");
		Localidad locOrigen = obtenerLocalidad(idLocalidadOrigen, localidades);
		// TODO sacar esto del select
		// int idLocalidadCargo = rs.getInt("LocPorCargoId");

		int idLocalidadEntrega = rs.getInt("LocEntrId");
		Localidad locEntrega = obtenerLocalidad(idLocalidadEntrega, localidades);

		// Pais localidad de emision
		int idLocalidadEmision = rs.getInt("LocEmisionId");
		Localidad locEmision = obtenerLocalidad(idLocalidadEmision, localidades);

		// pais localidad de destino
		int idLocalidadDestino = rs.getInt("LocDestId");
		Localidad locDestino = obtenerLocalidad(idLocalidadDestino, localidades);

		carpeta.setCiudadPaisPartida(locOrigen);
		carpeta.setCiudadPaisDestino(locDestino);
		carpeta.setPaisCiudadEmision(locEmision);
		carpeta.setPaisCiudadPaisEntrega(locEntrega);

		camOriginal.setMatricula(rs.getString("CamOriMatr"));
		camOriginal.setIdCamion(rs.getInt("CamOriId"));
		camOriginal.setMarca(rs.getString("CamOriMarca"));
		camOriginal.setCapacidad(rs.getString("CamOriCap"));
		camOriginal.setAnio(rs.getString("CamOriAnio"));

		resOriginal.setDocumento(rs.getString("ResOriDoc"));
		resOriginal.setIdResponsable(rs.getInt("ResOriId"));
		resOriginal.setApellido(rs.getString("ResOriApe"));
		resOriginal.setNombre(rs.getString("ResOriNom"));

		// Se cambia la parte de remolques
		// carpeta.setRemolqueOriginal(rs.getString("SemiRemolqueOrig"));
		semiOriginal.setAnio(rs.getString("remOriAnio"));
		semiOriginal.setCapacidad(rs.getString("remOriCap"));
		semiOriginal.setIdCamion(rs.getInt("remOriId"));
		semiOriginal.setMarca(rs.getString("remOriMarca"));
		semiOriginal.setMatricula(rs.getString("remOriMatr"));
		semiOriginal.setTipo(rs.getString("remOriTipo"));
		carpeta.setRemolqueOriginal(semiOriginal);

		// carpeta.setRemolqueSubstituto(rs.getString("SemiRemolqueSubstituto"));
		semiSus.setAnio(rs.getString("remSusAnio"));
		semiSus.setCapacidad(rs.getString("remSusCap"));
		semiSus.setIdCamion(rs.getInt("remSusId"));
		semiSus.setMarca(rs.getString("remSusMarca"));
		semiSus.setMatricula(rs.getString("remSusMatr"));
		semiSus.setTipo(rs.getString("remSusTipo"));
		carpeta.setRemolqueSubstituto(semiSus);

		camSus.setMatricula(rs.getString("CamSusMat"));
		camSus.setIdCamion(rs.getInt("CamSusId"));
		camSus.setMarca(rs.getString("CamSusMarca"));
		camSus.setAnio(rs.getString("CamSusAnio"));
		camSus.setCapacidad(rs.getString("CamSusCap"));
		resSus.setDocumento(rs.getString("ResSusDoc"));
		resSus.setIdResponsable(rs.getInt("ResSusId"));
		resSus.setApellido(rs.getString("ResSusApe"));
		resSus.setNombre(rs.getString("ResSusNom"));
		empRem.setNombre(rs.getString("EmpRemNom"));
		empRem.setDireccion(rs.getString("EmpRemDir"));
		empRem.setIdEmpresa(rs.getInt("EmpRemId"));

		Integer idLocEmpRem = rs.getInt("EmpRemLoc");
		Localidad localidadRem = obtenerLocalidad(idLocEmpRem, localidades);
		empRem.setLocalidad(localidadRem);

		empCons.setNombre(rs.getString("EmpConNom"));
		empCons.setDireccion(rs.getString("EmpConDir"));
		empCons.setIdEmpresa(rs.getInt("EmpConId"));

		Integer idocalidadCons = rs.getInt("EmpConLoc");
		Localidad localidadCons = obtenerLocalidad(idocalidadCons, localidades);
		empCons.setLocalidad(localidadCons);

		empDest.setNombre(rs.getString("EmpDesNom"));
		empDest.setDireccion(rs.getString("EmpDesDir"));
		empDest.setIdEmpresa(rs.getInt("EmpDesId"));

		Integer idLocEmpDest = rs.getInt("EmpDesLoc");
		Localidad localidadDest = obtenerLocalidad(idLocEmpDest, localidades);
		empDest.setLocalidad(localidadDest);

		Localidad localidadNotif = obtenerLocalidad(rs.getInt("EmpNotLoc"),
				localidades);
		empNotif.setLocalidad(localidadNotif);
		empNotif.setNombre(rs.getString("EmpNotNom"));
		empNotif.setDireccion(rs.getString("EmpNotDir"));
		empNotif.setIdEmpresa(rs.getInt("EmpNotId"));

		carpeta.setCamionOriginal(camOriginal);
		carpeta.setChoferOriginal(resOriginal);
		// carpeta.setRemolqueOriginal(rs.getString("SemiRemolqueOrig"));

		carpeta.setCamionSubstituto(camSus);
		carpeta.setChoferSubstituto(resSus);
		// carpeta.setRemolqueSubstituto(rs.getString("SemiRemolqueSubstituto"));

		carpeta.setRemitente(empRem);
		carpeta.setConsignatario(empCons);
		carpeta.setDestinatario(empDest);
		carpeta.setNotificar(empNotif);

		carpeta.setNumeroPrecinto(rs.getString("NmPrecinto"));
		carpeta.setMoneda(rs.getString("Moneda"));
		carpeta.setValorFOT(rs.getString("ValorFot"));
		carpeta.setValorMercancias(rs.getString("ValorMercaderias"));
		carpeta.setCostoFlete(rs.getString("CostoFlete"));
		carpeta.setSeguro(rs.getString("Seguro"));
		carpeta.setVolumenMC(rs.getString("Volumen"));
		carpeta.setCantidadBultos(rs.getString("CantidadDeBultos"));
		bulto.setCodBulto(rs.getInt("idBulto"));
		bulto.setDescripcion(rs.getString("descripcionBulto"));
		carpeta.setTipoBulto(bulto);
		carpeta.setTamanioLetraMarcaYNumero(rs
				.getByte("tamanioLetraMarcaYNumero"));

		carpeta.setPesoBruto(rs.getString("PesoBruto"));
		carpeta.setGastosPagar(rs.getString("Gastos"));
		carpeta.setMontoRemitente(rs.getString("MontoRemitente"));
		carpeta.setMonedaRemitente(rs.getString("MnRemitente"));
		carpeta.setMontoDestinatario(rs.getString("MontoDestinatario"));
		carpeta.setMonedaDestinatario(rs.getString("MnDestinatario"));
		carpeta.setMontoFleteExterno(rs.getString("MontoFleteExterno"));
		carpeta.setMontoRembolso(rs.getString("MontoRemobolso"));
		carpeta.setOtros(rs.getString("Otros"));
		carpeta.setMarcaYnumerobultos(rs.getString("MarcaNumeroBultos"));
		carpeta.setDocumentosAnexos(rs.getString("DocAnexos"));
		carpeta.setFormalidadesAduana(rs.getString("FormalidadesAduana"));

		ruta.setIdRuta(rs.getInt("idRuta"));
		ruta.setDescripcion(rs.getString("descRuta"));
		ruta.setDescripcionCorta(rs.getString("descCortaRuta"));
		carpeta.setRuta(ruta);
		trans.setNombreTransportadora(rs.getString("TrNom"));
		trans.setIdTransportadora(rs.getInt("TrId"));
		trans.setPrefijo(rs.getString("TrPre"));
		trans.setRolDelContribuyente(rs.getString("TrRol"));
		trans.setNombreArchivo(rs.getString("imagen"));
		carpeta.setTrans(trans);

		Integer aduanaOrigen = rs.getInt("AdOrigenId");
		adOrigen = obtenerAduana(aduanaOrigen, aduanas);

		Integer aduanaDestino = rs.getInt("AduanaDestinoId");
		adDestino = obtenerAduana(aduanaDestino, aduanas);
		;

		Pais paisOrigenMerc = new Pais();
		paisOrigenMerc.setDescripcion(rs.getString("paisDeOrigenMercaderias"));
		paisOrigenMerc.setIdPais(rs.getInt("paisDeOrigenMercaderiasId"));
		paisOrigenMerc.setCodigo(rs.getString("paisDeOrigenMercaderiasCodigo"));
		carpeta.setOrigenMercaderia(paisOrigenMerc);

		carpeta.setAduanaDestino(adDestino);
		carpeta.setAduanaOrigen(adOrigen);

		carpeta.setCantidadMic(rs.getInt("cantidadMic"));
		carpeta.setNroDocumento(rs.getInt("nroDocumento"));

		carpeta.setNombreBuque(rs.getString("nombreBuque"));
		carpeta.setFechaLlegadaBuque(Utils.convertToUtilDate(rs
				.getDate("fechaLlegadaBuque")));
		carpeta.setNroContenedor(rs.getString("nroContenedor"));
		carpeta.setReferenciaDestino(rs.getString("referenciaDestino"));
		carpeta.setFechaSalida(Utils.convertToUtilDate(rs
				.getDate("fechaSalida")));
		carpeta.setFechaVencimiento(Utils.convertToUtilDate(rs
				.getDate("fechaVencimiento")));

		carpeta.setFacturaOriginal(rs.getBoolean("facturaOriginal"));
		carpeta.setPackingOriginal(rs.getBoolean("packingOriginal"));
		carpeta.setBlOriginal(rs.getBoolean("blOriginal"));
		carpeta.setFechaRecibidoFactura(Utils.convertToUtilDate(rs
				.getDate("fechaRecibidoFactura")));
		carpeta.setFechaRecibidoPacking(Utils.convertToUtilDate(rs
				.getDate("fechaRecibidoPacking")));
		carpeta.setFechaRecibidoBL(Utils.convertToUtilDate(rs
				.getDate("fechaRecibidoBL")));

		Empresa cliente = new Empresa();
		cliente.setIdEmpresa(rs.getInt("idCliente"));
		cliente.setNombre(rs.getString("nombreCliente"));
		carpeta.setCliente(cliente);

		carpeta.setLiberacion(rs.getBoolean("liberacion"));
		carpeta.setLiberacionDescripcion(rs.getString("liberacionDescripcion"));
		carpeta.setPasadoAParaguay(rs.getBoolean("pasadoAParaguay"));

		Empresa despachante = new Empresa();
		despachante.setIdEmpresa(rs.getInt("idDespachante"));
		despachante.setNombre(rs.getString("nombreDespachante"));
		despachante.setNombreCorto(rs.getString("nombreCortoDespachante"));
		carpeta.setDespachante(despachante);

		Empresa agenciaMaritima = new Empresa();
		agenciaMaritima.setIdEmpresa(rs.getInt("idAgenciaMaritima"));
		agenciaMaritima.setNombre(rs.getString("nombreAgenciaMaritima"));
		carpeta.setAgenciaMaritima(agenciaMaritima);

		carpeta.setComentarios(rs.getString("comentarios"));
		carpeta.setTipoContenedor(TipoContenedor.getByCode(rs
				.getByte("tipoContenedor")));

		transCamion.setIdTransportadora(rs.getInt("transportadoraCamionId"));
		transCamion.setNombreTransportadora(rs
				.getString("transportadoraCamionNombre"));
		transCamion.setRolDelContribuyente(rs
				.getString("transportadoraCamionRol"));

		transportadoraCamionSustituto.setIdTransportadora(rs
				.getInt("transportadoraCamionSustitutoId"));
		transportadoraCamionSustituto.setNombreTransportadora(rs
				.getString("transportadoraCamionSustitutoNombre"));
		transportadoraCamionSustituto.setRolDelContribuyente(rs
				.getString("transportadoraCamionSustitutoRol"));
		carpeta.setTransportadoraCamionSustituto(transportadoraCamionSustituto);

		Localidad localidadTransportadoraCamionSus = obtenerLocalidad(
				rs.getInt("transportadoraCamionSustitutoLocalidad"),
				localidades);

		transportadoraCamionSustituto
				.setLocalidad(localidadTransportadoraCamionSus);

		Localidad localidadTransCamion = obtenerLocalidad(
				rs.getInt("transportadoraCamionLocalidad"), localidades);
		transCamion.setLocalidad(localidadTransCamion);

		carpeta.setTransportadoraCamion(transCamion);

		carpeta.setValidoHasta(rs.getString("validoHasta"));
		carpeta.setRutasCorto(rs.getString("rutasCorto"));
		carpeta.setRutasLargo(rs.getString("rutasLargo"));
		carpeta.setFechaSalidaSolicitudCliente(Utils.convertToUtilDate(rs
				.getDate("fechaSalidaSolicitudCliente")));
		carpeta.setEsCRT(rs.getBoolean("esCRT"));
		carpeta.setNumeroDeMic(rs.getInt("nroMIC"));
		carpeta.setIdCarpetaPadre(rs.getInt("idCarpetaPadre"));
//		carpeta.setTerminal(Terminal.getByCode(rs.getInt("terminal")));
		carpeta.setLocalidades(localidades);
		
		Terminal terminal = null;
		if(rs.getInt("idTerminal") > 0){
			terminal = new Terminal();
			terminal.setId(rs.getInt("idTerminal"));
			terminal.setNombre(rs.getString("nombreTerminal"));
		}
		
		carpeta.setTerminal(terminal);
		
		
		carpeta.setAduanas(aduanas);
		return carpeta;
	}

	/**
	 * Dada una idLocalidad y la lista de localidades si existe en el hash la
	 * obtiene y la retorna si no existe se crea una nueva, se agrega al map y
	 * se retorna
	 * 
	 * @param idLocalidad
	 * @param localidades
	 * @return
	 */
	private Localidad obtenerLocalidad(Integer idLocalidad,
			Map<Integer, Localidad> localidades) {
		Localidad localidad = null;
		if (localidades.containsKey(idLocalidad)) {
			localidad = localidades.get(idLocalidad);
		} else {
			localidad = new Localidad(idLocalidad);
			localidades.put(idLocalidad, localidad);
		}
		return localidad;
	}

	/**
	 * Dada una idLocalidad y la lista de localidades si existe en el hash la
	 * obtiene y la retorna si no existe se crea una nueva, se agrega al map y
	 * se retorna
	 * 
	 * @param idLocalidad
	 * @param localidades
	 * @return
	 */
	private Aduana obtenerAduana(Integer idAduana, Map<Integer, Aduana> aduanas) {
		Aduana aduana = null;
		if (aduanas.containsKey(idAduana)) {
			aduana = aduanas.get(idAduana);
		} else {
			aduana = new Aduana(idAduana);
			aduanas.put(idAduana, aduana);
		}
		return aduana;
	}
}
