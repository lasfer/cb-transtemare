package com.core.transtemare.persistencia.sql;

public class SQLTransportadoras {

	// Obtener
	public static final String OBTENER_RESPONSABLE_POR_ID = "SELECT CodResponsable , Nombre,Apellido,Documento,p.descripcion as pais,l.descripcion as ciudad, telefono FROM skuncadb.responsable r ,skuncadb.localidad l ,skuncadb.pais p WHERE r.FK_Localidad = l.CodLocalidad AND l.CodPais_FK = p.CodPais AND CodResponsable = ?";
	public static final String OBTENER_CAMION_POR_ID = "SELECT idCamion  ,  MatriculaCamion , capacidad , marca ,anio,tipo,numeroPoliza,vencimientoPoliza FROM "
			+ SQLTablas.TABLA_CAMIONES
			+ " WHERE  idCamion = ? AND idCamion != 0";
	public static final String OBTENER_TODAS_TRANSPORTADORAS = "SELECT t.* , p.* , l.* , n.numero  FROM transportadora t , pais p , localidad l , numeradores n WHERE t.CodLocallidad_FK = l.CodLocalidad and l.CodPais_FK = p.CodPais  AND t.CodTransportadora = n.CodTransportadora AND t.CodTransportadora != 0 AND t.Activo = 1 ORDER BY CodTransportadora  DESC";

	public static final String OBTENER_CANTIDAD_TOTAL_TRANSPORTADORAS = "SELECT count(*) as total FROM transportadora t , numeradores n WHERE t.CodTransportadora=n.CodTransportadora AND t.Activo = 1";
	public static final String OBTENER_CANTIDAD_TOTAL_RESPONSABLE = "SELECT count(*) as total FROM responsable t  WHERE Activo = 1 ";
	public static final String OBTENER_CANTIDAD_TOTAL_CAMIONES = "SELECT count(*) as total FROM "
			+ SQLTablas.TABLA_CAMIONES + " WHERE Activo = 1 ";
	public static final String OBTENER_TODAS_TRANSPORTADORAS_DESDE_HASTA = "SELECT t.* , p.* , l.* , n.numero FROM transportadora t , pais p , localidad l , numeradores n WHERE t.CodLocallidad_FK = l.CodLocalidad and l.CodPais_FK = p.CodPais AND t.CodTransportadora = n.CodTransportadora  AND t.Activo = 1 AND t.CodTransportadora != 0 LIMIT ? , ?";
	public static final String OBTENER_TRANSPORTADORA_POR_ID = "SELECT *, null as numero FROM "
			+ SQLTablas.TABLA_TRANSPORTADORAS
			+ " t , "
			+ SQLTablas.TABLA_PAISES
			+ " p , "
			+ SQLTablas.TABLA_LOCALIDADES
			+ " l WHERE t.CodLocallidad_FK = l.CodLocalidad and l.CodPais_FK = p.CodPais AND CodTransportadora = ? ";
	public static final String OBTENER_TODOS_LOS_CHOFERES = "SELECT r.CodResponsable , r.Nombre , r.Apellido , r.Documento , l.descripcion as ciudad, p.descripcion as pais, telefono FROM skuncadb.responsable r, skuncadb.localidad l , skuncadb.pais p WHERE r.FK_localidad = l.CodLocalidad AND l.CodPais_FK = p.CodPais AND r.Activo = 1 AND CodResponsable != 0 ORDER BY r.CodResponsable DESC";
	public static final String OBTENER_TODOS_LOS_CHOFERES_DESDE_HASTA = "SELECT r.CodResponsable , r.Nombre , r.Apellido , r.Documento , l.descripcion as ciudad, p.descripcion as pais, telefono FROM skuncadb.responsable r, skuncadb.localidad l , skuncadb.pais p WHERE r.FK_localidad = l.CodLocalidad AND l.CodPais_FK = p.CodPais AND r.Activo = 1 AND CodResponsable != 0 and (r.nombre like ? and r.apellido LIKE ?) LIMIT ? , ?";
	public static final String OBTENER_TODOS_LOS_CAMIONES = " SELECT idCamion  ,  MatriculaCamion , capacidad , marca , anio ,tipo, numeroPoliza,vencimientoPoliza FROM "
			+ SQLTablas.TABLA_CAMIONES
			+ " WHERE tipo='Camion' AND Activo = 1 AND idCamion != 0  ORDER BY idCamion DESC";
	public static final String OBTENER_TODOS_LOS_REMOLQUES = " SELECT idCamion  ,  MatriculaCamion , capacidad , marca , anio ,tipo,numeroPoliza,vencimientoPoliza FROM "
			+ SQLTablas.TABLA_CAMIONES
			+ " WHERE  tipo = 'Remolque' AND Activo = 1 AND idCamion != 0  ORDER BY idCamion DESC";
	public static final String OBTENER_TODOS_LOS_CAMIONES_DESDE_HASTA = "SELECT idCamion  ,  MatriculaCamion , capacidad , marca , anio ,tipo,numeroPoliza,vencimientoPoliza FROM "
			+ SQLTablas.TABLA_CAMIONES
			+ " WHERE MatriculaCamion LIKE ? and Activo = 1 AND idCamion != 0 LIMIT ? , ?";
	public static final String OBTENER_TODAS_LAS_MARCAS_DE_CAMIONES = "SELECT * FROM "
			+ SQLTablas.TABLA_MARCAS_CAMIONES;

	// Borrar
	public static final String BORRAR_TRANSPORTADORA = "UPDATE "
			+ SQLTablas.TABLA_TRANSPORTADORAS
			+ " SET Activo=0 WHERE CodTransportadora=?";
	public static final String BORRAR_RESPONSABLE = "UPDATE skuncadb.responsable SET Activo=0 WHERE CodResponsable=?";
	public static final String BORRAR_CAMION = "UPDATE skuncadb.camion SET Activo=0 WHERE idCamion=?";

	// Modificar
	public static final String MODIFICAR_CHOFER = "UPDATE "
			+ SQLTablas.TABLA_RESPONSABLE
			+ " set Nombre=? , Apellido=? ,Documento=? ,FK_Localidad=?, telefono=? WHERE CodResponsable=?";
	public static final String MODIFICAR_CAMION = "UPDATE "
			+ SQLTablas.TABLA_CAMIONES
			+ " SET capacidad=? ,marca=? ,MatriculaCamion=?,anio=?,tipo=?, numeroPoliza=?, vencimientoPoliza=? WHERE idCamion=?";
	public static final String MODIFICAR_TRANSPORTADORA = "UPDATE "
			+ SQLTablas.TABLA_TRANSPORTADORAS
			+ " set Nombre= ? , RolContribuyente = ? , Domicilio = ? , CodLocallidad_FK = ? ,Prefijo = ?,imagen = ? WHERE CodTransportadora = ?";

	// Crear
	public static final String INSERTAR_TRANSPORTADORA = "INSERT INTO "
			+ SQLTablas.TABLA_TRANSPORTADORAS + " VALUES(null,?,?,?,?,1,?)";
	public static final String ALTA_CAMION = "INSERT INTO "
			+ SQLTablas.TABLA_CAMIONES + " VALUES(null,?,?,?,?,1,?,?,?)";
	public static final String INSERTAR_CHOFER = "INSERT INTO "
			+ SQLTablas.TABLA_RESPONSABLE + " VALUES(null,?,?,?,?,1,?)";
}
