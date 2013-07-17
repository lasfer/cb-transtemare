<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<sj:dialog id="mydialog" title="Mensaje" autoOpen="true" modal="true" width="600"  >
	<p>Echo : <s:property value="echo" escape="%{escape}" /></p>
	<h4>Nombre de la excepcion:</h4>
	<s:property value="exception" />
	<h4>Cual fue el problema:</h4>
	<s:property value="exceptionStack" />
</sj:dialog>
</body>
</html>


