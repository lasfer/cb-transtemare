<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error de conexion a la base de datos</title>
</head>
<body>

	<h3>Error al intentar conectarse a la base de datos. Es posible
		que la base no se est&eacute; ejecutando</h3>
	<br>
	<br>
	<h4>Nombre y detalle del error:</h4>
	<s:property value="exception" />
	<br>
	<input type="button" name="volver" value="volver"
		onclick="history.back();">
</body>
</html>