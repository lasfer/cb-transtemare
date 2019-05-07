package com.core.transtemare.persistencia.sql;

public class SQLComunes {

	// PAISES
	public static final String LISTAR_TODOS_PAISES = "SELECT * FROM "
			+ SQLTablas.TABLA_PAISES + " where CodPais > 0 ";
	public static final String OBTENER_COD_PAIS_POR_NOMBRE = "SELECT COD_PAIS FROM "
			+ SQLTablas.TABLA_PAISES + " WHERE DESCRIPCION = ?";
	// LOCALIDADES
	public static final String LISTAR_TODAS_LOCALIDADES = "SELECT l.CodLocalidad as CodLocalidad , l.CodPais_FK as CodPais_FK, l.Descripcion as Descripcion, l.esAduana as esAduana, l.codigoAduana, p.CodPais as CodPais, p.Descripcion as DescripcionPais, p.codigo FROM localidad l , pais p  WHERE l.Activo=1 and l.CodPais_FK=p.CodPais and  CodLocalidad != 0;";
	public static final String LISTAR_LOCALIDADES_DESDE_HASTA = "SELECT l.CodLocalidad as CodLocalidad , l.CodPais_FK as CodPais_FK, l.Descripcion as Descripcion, l.esAduana as esAduana, l.codigoAduana, p.CodPais as CodPais, p.Descripcion as DescripcionPais, p.codigo FROM localidad l , pais p  WHERE l.Activo=1 and l.CodPais_FK=p.CodPais and  CodLocalidad != 0 LIMIT ?, ?;";
	public static final String OBTENER_TODAS_LAS_ADUANAS = "SELECT l.CodLocalidad as CodLocalidad , l.CodPais_FK as CodPais_FK, l.Descripcion as Descripcion, l.esAduana as esAduana, l.codigoAduana, p.CodPais as CodPais, p.Descripcion as DescripcionPais, p.codigo  FROM localidad l , pais p  WHERE l.Activo=1 and l.CodPais_FK=p.CodPais and  CodLocalidad != 0 AND esAduana=true";
	public static final String OBTENER_LOCALIDADES_POR_ID = "SELECT l.CodLocalidad as codLocalidad, l.Descripcion as Descripcion, l.esAduana as esAduana, l.codigoAduana, l.CodPais_FK, p.CodPais, p.Descripcion as descripcionPais, p.codigo FROM localidad l inner join pais p on l.CodPais_FK=p.CodPais  WHERE CodLocalidad IN(?);";
	public static final String OBTENER_COD_LOCALIDAD_POR_NOMBRE = "SELECT CodLocalidad FROM "
			+ SQLTablas.TABLA_LOCALIDADES
			+ " WHERE activo=1 AND Descripcion = ?";
	// ABM
	public static final String MODIFICAR_LOCALIDAD = "UPDATE "
			+ SQLTablas.TABLA_LOCALIDADES
			+ " set Descripcion=? , CodPais_FK=? ,esAduana=?, codigoAduana=? WHERE CodLocalidad=?";
	public static final String INSERTAR_LOCALIDAD = "INSERT INTO "
			+ SQLTablas.TABLA_LOCALIDADES + " VALUES(null,?,?,?,1,?)";
	public static final String BORRAR_LOCALIDAD = "UPDATE "
			+ SQLTablas.TABLA_LOCALIDADES
			+ " SET Activo=0 WHERE CodLocalidad=?";

	// RUTAS
	public static final String OBTENER_TODAS_TODAS_LAS_RUTAS = "SELECT * FROM "
			+ SQLTablas.TABLA_RUTAS
			+ " WHERE codRuta != 0 ORDER BY DescripcionCorta";
	public static final String OTENER_RUTA = "SELECT COD_RUTA, DESCRIPCION FROM  "
			+ SQLTablas.TABLA_RUTAS + "  WHERE COD_RUTA = ?";

	public static final String ALTA_NUEVO_RESPONSABLE = "INERT INTO"
			+ SQLTablas.TABLA_RESPONSABLE
			+ "(CodResponsable,Nombre,Apellido,Cedula,telefono) VALUES(null,?,?,?,?)";
	public static final String OBTENER_ULTIMO_ID = "SELECT MAX(CodResponsable) as ID FROM "
			+ SQLTablas.TABLA_RESPONSABLE;
	public static final String OBTENER_RESPONSABLE_USUARIO_CONTRASENA = "Select * from user where user=? and password=?";

}
