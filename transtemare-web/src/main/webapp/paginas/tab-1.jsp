<?xml version="1.0" encoding="utf-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<div style="height: 200px;width: 200px" > 

<s:url id="jsonCamion" action="jsonListaCamiones"  />

<sj:autocompleter
	    	id="languages" 
	    	name="echo"
	    	href="%{jsonCamion}" 
	    	delay="50" 
	    	loadMinimumCount="2"
	    />

</div>




