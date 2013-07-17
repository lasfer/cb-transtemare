<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>



<s:url id="micdtaResumen_corto" action="jsonMics"  ><s:param value="%{id}" name="id" /> </s:url>

<sj:div href="%{micdtaResumen_corto}"  />

