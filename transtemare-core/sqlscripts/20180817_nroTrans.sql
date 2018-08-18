USE `skuncadb`;
DROP procedure IF EXISTS `sp_buscar_carpeta`;

DELIMITER $$
USE `skuncadb`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscar_carpeta`(IN numero INTEGER)
BEGIN 
SELECT c.idCarpeta,c.FxAlta,c.FxMod,c.FxEmision,c.TrAduanero, 
  
 
 
 
c.LcPartida as LocOriId, 
c.AdOrigen as AdOrigenId, 
 
c.LcPortadorCargo as LocPorCargoId, 
 
c.LcEntrega as LocEntrId, 
 
c.LcEmision as LocEmisionId, 
 
c.LcDestino as LocDestId, 
 
c.AdDestino as AduanaDestinoId, 
 
cam.MatriculaCamion as CamOriMatr, 
cam.idCamion as CamOriId, 
cam.`marca` as CamOriMarca, 
cam.`anio` as CamOriAnio, 
cam.`capacidad` as CamOriCap, 
res.Documento as ResOriDoc, 
res.nombre as ResOriNom, 
res.apellido as ResOriApe, 
res.CodResponsable as ResOriId, 
 
rem.MatriculaCamion as remOriMatr, 
rem.idCamion as remOriId, 
rem.`marca` as remOriMarca, 
rem.`anio` as remOriAnio, 
rem.`capacidad` as remOriCap, 
rem.`tipo` as remOriTipo, 
 
rem1.MatriculaCamion as remSusMatr, 
rem1.idCamion as remSusId, 
rem1.`marca` as remSusMarca, 
rem1.`anio` as remSusAnio, 
rem1.`capacidad` as remSusCap, 
rem1.`capacidad` as remSusTipo, 
 
  
 
 
cam1.MatriculaCamion as CamSusMat, 
cam1.idCamion as CamSusId, 
cam1.`marca` as CamSusMarca, 
cam1.`anio` as CamSusAnio, 
cam1.`capacidad` as CamSusCap, 
res1.Documento as ResSusDoc, 
res1.CodResponsable as ResSusId, 
res1.nombre as ResSusNom, 
res1.apellido as ResSusApe, 
em1.nombre as EmpRemNom , 
em1.idEmpresa as EmpRemId, 
em1.`FK_Localidad` as EmpRemLoc, 
em1.`Direccion` as EmpRemDir, 
em2.nombre as EmpConNom, 
em2.idEmpresa as EmpConId, 
em2.`FK_Localidad` as EmpConLoc, 
em2.`Direccion` as EmpConDir, 
em3.nombre as EmpDesNom, 
em3.idEmpresa as EmpDesId, 
em3.`FK_Localidad` as EmpDesLoc, 
em3.`Direccion` as EmpDesDir, 
em4.nombre as EmpNotNom , 
em4.idEmpresa as EmpNotId, 
em4.`FK_Localidad` as EmpNotLoc, 
em4.`Direccion` as EmpNotDir, 
c.NmPrecinto,c.Moneda,c.ValorFot,c.ValorMercaderias,c.CostoFlete,c.Seguro,c.Volumen,c.CantidadDeBultos, 
b.idBulto as idBulto, 
b.`descripcion` as descripcionBulto, 
c.PesoBruto,c.Gastos,c.MontoRemitente,c.MnRemitente,c.MontoDestinatario,c.MnDestinatario,c.MontoFleteExterno, 
c.MontoRemobolso,c.Otros,c.MarcaNumeroBultos,c.DocAnexos,c.FormalidadesAduana, 
c.`idRuta`, 
r.`Descripcion` as descRuta, 
r.`DescripcionCorta` as descCortaRuta, 
trans.Nombre as TrNom, 
trans.`CodTransportadora` as TrId , 
trans.`Prefijo` as TrPre, 
trans.`RolContribuyente` as TrRol, 
trans.`CodLocallidad_FK` as TrLoc, 
trans.`imagen` as imagen, 
p5.Descripcion as paisDeOrigenMercaderias, 
p5.`CodPais` as paisDeOrigenMercaderiasId, 
p5.`codigo` as paisDeOrigenMercaderiasCodigo, 
c.`cantidadMic`, 
c.`nroDocumento`, 
c.nombreBuque, 
c.fechaLlegadaBuque, 
c.nroContenedor, 
c.referenciaDestino, 
c.fechaSalida, 
c.fechaVencimiento, 
c.facturaOriginal, 
c.packingOriginal, 
c.blOriginal, 
c.fechaRecibidoFactura, 
c.fechaRecibidoPacking, 
c.fechaRecibidoBL, 
c.cliente as idCliente, 
cliente.`nombre` as nombreCliente, 
c.liberacion, 
c.liberacionDescripcion, 
c.pasadoAParaguay, 
c.despachante as idDespachante, 
despachante.`nombre` as nombreDespachante, 
despachante.`nombreCorto` as nombreCortoDespachante, 
c.agenciaMaritima as idAgenciaMaritima, 
agenciaMaritima.`nombre` as nombreAgenciaMaritima, 
c.comentarios, 
c.tipoContenedor, 
c.`fechaSalidaSolicitudCliente`, 
c.transportadoraCamion as transportadoraCamionId, 
transCamion.`Nombre` as transportadoraCamionNombre, 
transCamion.`RolContribuyente` as transportadoraCamionRol, 
transCamion.`CodLocallidad_FK` as transportadoraCamionLocalidad, 
c.`tamanioLetraMarcaYNumero`, 
c.`validoHasta`, 
c.`rutasCorto`, 
c.`rutasLargo`, 
c.`transportadoraCamionSustituto` as transportadoraCamionSustitutoId , 
transCamionSustituto.`Nombre` as transportadoraCamionSustitutoNombre, 
transCamionSustituto.`RolContribuyente` as transportadoraCamionSustitutoRol, 
transCamionSustituto.`CodLocallidad_FK` as transportadoraCamionSustitutoLocalidad, 
c.`esCRT` as esCRT, 
c.`nroMIC` as nroMIC, 
c.`nombreFirmaDestinatario` as nombreFirmaDestinatario, 
c.`idCarpetaPadre`, 
t.`id` as idTerminal, 
t.`nombre` as nombreTerminal,
c.`contenedorDevuelto`,
c.`cargarInformacionGarantia`,
c.`tipoGarantia`, 
c.`importeGarantia`,
c.`bancoGarantia`,
c.`nroChequeGarantia`,
c.`bancoGarantia`,
c.`fechaCargaGarantia`,
c.`garantiaDevuelta`,
c.`nroDUA`,
c.`nroTransmision`,
c.`firmante`
             
FROM carpeta c ,bultos b ,camion cam ,camion cam1,camion rem,camion rem1,empresas em1,empresas em2,empresas em3, 
        empresas em4, empresas cliente, empresas despachante, empresas agenciaMaritima, 
      pais p5, responsable res,responsable res1, 
      transportadora trans, transportadora transCamion,rutas r, 
       transportadora transCamionSustituto,terminales t 
 
WHERE 
c.`idRuta`=r.`CodRuta` 
 
AND c.CmOriginal = cam.idCamion 
AND c.CmSubstituto = cam1.idCamion 
AND c.SemiRemolqueOrig = rem.idCamion 
AND c.SemiRemolqueSubstituto = rem1.idCamion 
 
AND c.RsOriginal = res.CodResponsable 
AND c.RsSubstituto = res1.CodResponsable 
 
AND c.EmRemitente = em1.idEmpresa 
AND c.EmConsignataria = em2.idEmpresa 
AND c.EmDestinataria = em3.idEmpresa 
AND c.EmNotificar = em4.idEmpresa 
AND c.cliente = cliente.idEmpresa 
AND c.despachante = despachante.idEmpresa 
AND c.`agenciaMaritima` = agenciaMaritima.idEmpresa 
 
AND c.LcOrigenMercaderia = p5.CodPais 
AND c.Bulto = b.idBulto 
AND c.transportadora = trans.CodTransportadora 
AND c.`transportadoraCamion` = transCamion.`CodTransportadora` 
AND c.`transportadoraCamionSustituto` = transCamionSustituto.`CodTransportadora` 
AND c.terminal = t.`id` 
AND c.idCarpeta = numero; 
 
END$$

DELIMITER ;




ALTER TABLE `skuncadb`.`carpeta` 
ADD COLUMN `nroTransmision` VARCHAR(20) NULL DEFAULT NULL AFTER `nroDua`;
ALTER TABLE `skuncadb`.`carpeta` 
ADD COLUMN `firmante` VARCHAR(30) NULL DEFAULT NULL AFTER `nroTransmision`;