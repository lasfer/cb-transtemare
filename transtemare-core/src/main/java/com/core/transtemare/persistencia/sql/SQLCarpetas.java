package com.core.transtemare.persistencia.sql;

public class SQLCarpetas {

	public static final String OBTENER_EMPRESA_POR_ID = "SELECT * FROM "
			+ SQLTablas.TABLA_EMPRESAS
			+ " e , "
			+ SQLTablas.TABLA_PAISES
			+ " p , "
			+ SQLTablas.TABLA_LOCALIDADES
			+ " l WHERE e.FK_Localidad = l.CodLocalidad and l.CodPais_FK = p.CodPais AND idEmpresa = ? ";
	public static final String MODIFICAR_EMPRESA = "Update  "
			+ SQLTablas.TABLA_EMPRESAS
			+ " set nombre=?,rolDelCon=?,Direccion=?,FK_Localidad=?, tipo= ?, nombreCorto=? WHERE idEmpresa=?";
	public static final String INSERTAR_EMPRESA = "INSERT INTO "
			+ SQLTablas.TABLA_EMPRESAS + " VALUES(null,?,?,?,?,1,?,?)";
	public static final String PROCEDURE_SELECT_CARPETA_POR_ID = "CALL `skuncadb`.`sp_buscar_carpeta`(?)";
	public static final String OBTENER_CARPETAS_DESDE_HASTA = "SELECT c.idCarpeta,c.referenciaDestino,c.nroContenedor, t.Nombre as nombreTransportadora, c.terminal, e.nombre as nombreAgenciaMaritima, c.FxAlta,c.FxMod, c.fechaVencimiento, c.fechaLlegadaBuque FROM "
			+ SQLTablas.TABLA_CARPETAS
			+ " c  ,"
			+ SQLTablas.TABLA_EMPRESAS
			+ " e, "
			+ SQLTablas.TABLA_TRANSPORTADORAS
			+ " t "
			+ " WHERE c.transportadora=t.codTransportadora and c.agenciaMaritima=e.idEmpresa and c.historico = ? AND c.idCarpeta!=0 AND c.esCRT = 1  [FILTROADICIONAL]   ORDER BY c.idCarpeta DESC LIMIT ?,?";

	public static final String OBTENER_CARPETAS_CARGA_GARANTIA = "SELECT c.idCarpeta, am.nombre as nombreAgenciaMaritima, desp.nombre as nombreDespachante  FROM "
			+ SQLTablas.TABLA_CARPETAS
			+ " c  ,"
			+ SQLTablas.TABLA_EMPRESAS
			+ " am ,"
			+ SQLTablas.TABLA_EMPRESAS
			+ " desp "
			+ " WHERE  c.agenciaMaritima=am.idEmpresa and c.despachante=desp.idEmpresa and c.historico = 0 AND c.idCarpeta!=0 AND c.cargarInformacionGarantia=1 ORDER BY c.idCarpeta";

	public static final String OBTENER_SUBCARPETAS = "SELECT c.idCarpeta,c.referenciaDestino,c.nroContenedor, t.Nombre as nombreTransportadora, c.terminal, e.nombre as nombreAgenciaMaritima, c.FxAlta,c.FxMod, c.fechaVencimiento, c.fechaLlegadaBuque FROM "
			+ SQLTablas.TABLA_CARPETAS
			+ " c  ,"
			+ SQLTablas.TABLA_EMPRESAS
			+ " e, "
			+ SQLTablas.TABLA_TRANSPORTADORAS
			+ " t "
			+ " WHERE c.transportadora=t.codTransportadora and c.agenciaMaritima=e.idEmpresa and historico = ? AND idCarpetaPadre = ? AND esCRT=0";

	public static final String OBTENER_CARPETAS_HISTORICO = "SELECT * FROM historicos h LIMIT ?,?";
	public static final String OBTENER_CARPETA_POR_ID_HISTORICO = "SELECT * FROM historicos where idCarpeta=?";
	public static final String OBTENER_TODAS_LAS_EMPRESAS = "SELECT e.idEmpresa , e.nombre , e.Direccion , e.rolDelCon ,e.tipo, l.descripcion, e.FK_Localidad, e.nombreCorto, p.* FROM "
			+ SQLTablas.TABLA_EMPRESAS
			+ " e, "
			+ SQLTablas.TABLA_LOCALIDADES
			+ " l , "
			+ SQLTablas.TABLA_PAISES
			+ " p WHERE e.FK_localidad = l.CodLocalidad AND l.CodPais_FK = p.CodPais AND e.Activo = 1 AND idEmpresa != 0 AND e.tipo=? ORDER BY e.idEmpresa DESC ";
	public static final String OBTENER_TODAS_LAS_EMPRESAS_DESDE_HASTA = "SELECT e.idEmpresa , e.nombre , e.Direccion , e.rolDelCon ,e.tipo, e.FK_Localidad, e.nombreCorto, l.descripcion, p.* FROM "
			+ SQLTablas.TABLA_EMPRESAS
			+ " e, "
			+ SQLTablas.TABLA_LOCALIDADES
			+ " l , "
			+ SQLTablas.TABLA_PAISES
			+ " p WHERE e.FK_localidad = l.CodLocalidad AND l.CodPais_FK = p.CodPais AND e.Activo = 1 AND idEmpresa != 0 AND e.nombre LIKE ? LIMIT ? , ?";
	public static final String OBTENER_EMPRESAS_POR_TIPO = "SELECT e.idEmpresa , e.nombre , e.Direccion , e.rolDelCon ,e.tipo, l.descripcion, e.FK_Localidad,p.* FROM "
			+ SQLTablas.TABLA_EMPRESAS
			+ " e, "
			+ SQLTablas.TABLA_LOCALIDADES
			+ " l , "
			+ SQLTablas.TABLA_PAISES
			+ " p WHERE e.FK_localidad = l.CodLocalidad AND l.CodPais_FK = p.CodPais AND e.Activo = 1 AND idEmpresa != 0 AND e.tipo=? ORDER BY e.idEmpresa DESC ";
	public static final String OBTENER_TODOS_LOS_BULTOS = "SELECT * FROM "
			+ SQLTablas.TABLA_BULTOS + " WHERE idBulto != 0";
	public static final String MODIFICAR_CARPETA = "UPDATE "
			+ SQLTablas.TABLA_CARPETAS
			+ " SET FxMod=?,FxEmision=?,TrAduanero=?,LcPartida=?,AdOrigen=?,LcDestino=?,AdDestino=?,"
			+ "CmOriginal=?,RsOriginal=?,SemiRemolqueOrig=?,CmSubstituto=?,RsSubstituto=?,SemiRemolqueSubstituto=?,EmRemitente=?,"
			+ "EmConsignataria=?,EmDestinataria=?,EmNotificar=?,LcEmision=?,LcPortadorCargo=?,LcEntrega=?,NmPrecinto=?,"
			+ "LcOrigenMercaderia=?,Moneda=?,ValorFOT=?,ValorMercaderias=?,CostoFlete=?,Seguro=?,Volumen=?,CantidadDeBultos=?,Bulto=?,"
			+ "PesoBruto=?,Gastos=?,MontoRemitente=?,MnRemitente=?,MontoDestinatario=?,MnDestinatario=?,MontoFleteExterno=?,"
			+ "MontoRemobolso=?,Otros=?,MarcaNumeroBultos=?,DocAnexos=?,FormalidadesAduana=?,idRuta=?,historico=0,nombreBuque=?,fechaLlegadaBuque=?,"
			+ "nroContenedor=?,referenciaDestino=?,fechaSalida=?, fechaVencimiento=?, "
			+ "facturaOriginal=?, packingOriginal=?, blOriginal=?,fechaRecibidoFactura=?,fechaRecibidoPacking=?, "
			+ "fechaRecibidoBL=?,cliente=?, liberacion=?,liberacionDescripcion=?,pasadoAParaguay=?,despachante =?,agenciaMaritima=?, "
			+ "comentarios=?, tipoContenedor=?, fechaSalidaSolicitudCliente=?, transportadoraCamion=?, tamanioLetraMarcaYNumero=?, "
			+ "validoHasta=?, rutasCorto=?, rutasLargo=?,transportadoraCamionSustituto=? , nombreFirmaDestinatario=?,  terminal=?, "
			+ "contenedorDevuelto=?, cargarInformacionGarantia=?, tipoGarantia=?, importeGarantia=?, bancoGarantia=?, "
			+ "nroChequeGarantia=?, fechaCargaGarantia=?, garantiaDevuelta=?  where idCarpeta=?";

	public static final String BORRAR_EMPRESA = "UPDATE empresas SET Activo=0 WHERE idEmpresa=?";
	public static final String PROCEDURE_NUMERADORES = "CALL `skuncadb`.`sp_numeradores`(?,?);";
	public static final String ALARAMA_CARPETAS = "Select *, null as nombreTransportadora, null as nombreAgenciaMaritima from carpeta where DATEDIFF(fechaVencimiento,now()) < 15 and devuelto = 0 LIMIT ? , ?;";
	public static final String OBTENER_CANTIDAD_TOTAL_EMPRESAS = "SELECT count(*) as total FROM empresas where activo=1";
	public static final String OBTENER_CANTIDAD_TOTAL_CARPETAS = "SELECT count(*) as total FROM carpeta where historico=0 and  esCRT = 1";
	public static final String OBTENER_CANTIDAD_TOTAL_ALARMAS = "SELECT count(*) as total FROM carpeta where historico=0 and esCRT = 1";
	public static final String PROCEDURE_PASAR_HISTORICO = "CALL `skuncadb`.`sp_pasar_historico`(?,?,?,?,?,?,?)";
	public static final String OBTENER_CANTIDAD_TOTAL_CARPETAS_HISTORICO = "SELECT count(*) as total from historicos";

	public static final String OBTENER_CANTIDAD_TERMINALES = "SELECT count(*) as total FROM"
			+ SQLTablas.TABLA_TERMINALES + "  where activo=1 and id !=0";
	public static final String OBTENER_TERMINALES = "SELECT * FROM "
			+ SQLTablas.TABLA_TERMINALES + "  where activo=1  and id !=0 ";
	public static final String OBTENER_TERMINALES_POR_ID = "SELECT * FROM "
			+ SQLTablas.TABLA_TERMINALES
			+ " where id=? and activo=1  and id !=0 ";
	public static final String OBTENER_TERMINALES_DESDE_HASTA = "SELECT * FROM "
			+ SQLTablas.TABLA_TERMINALES
			+ " where activo=1 and id !=0  LIMIT ? , ?";
	public static final String COUNT_TERMINALES = "SELECT count(*) as total FROM "
			+ SQLTablas.TABLA_TERMINALES + " where activo=1  and id !=0 ";
	public static final String ALTA_TERMINAL = "INSERT INTO "
			+ SQLTablas.TABLA_TERMINALES + " values(null,?,1)";
	public static final String ACTUALIZAR_TERMINAL = "UPDATE "
			+ SQLTablas.TABLA_TERMINALES + " SET nombre=? where id=? ";
	public static final String ELIMINAR_TERMINAL = "UPDATE "
			+ SQLTablas.TABLA_TERMINALES + " SET activo=false where id=? ";

}
