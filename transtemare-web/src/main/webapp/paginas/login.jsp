<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<sx:div>
	<sj:dialog name="Login" title="Ingresar al sistema" autoOpen="true"
		modal="true" width="350">
		<s:form name="login" action="entrar">
			<sj:textfield name="usuario" id="usuario" label="Nombre de usuario" />
			<s:password name="contrasena" id="contrasena" label="Contrasena" />
			<s:submit value="Ingresar" />
			<div>
				<s:property value="%{#session.mensajeDeLogin}" />
			</div>
		</s:form>
	</sj:dialog>
</sx:div>