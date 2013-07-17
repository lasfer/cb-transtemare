<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%--<%@ taglib prefix="jo" uri="/struts-json-tags" %>--%>

<table>



	<s:iterator value="map.entrySet()">

<tr>
	<td>
		<%="<a  id='Editar_"%><s:property value="%{value}"/><%="' href='javascript:void(0)' role='button' aria-disabled='false' class='ui-icon-newwin' >"%><s:property value='%{key}'/>-<s:property value='%{value}'/>-<%="</a>" %>
	</td>	
</tr>	
	
	<%="<script type='text/javascript'> $(document).ready(function () { var options_Editar_"%><s:property value="%{value}"/><%="= {}; options_Editar_"%><s:property value="%{value}"/><%=".jqueryaction = 'anchor';"%> 
	<%="options_Editar_"%><s:property value="%{value}"/><%=".id = 'Editar_"%><s:property value="%{value}"/><%="';" %>
	<%="options_Editar_"%><s:property value="%{value}"/><%=".targets = 'main';" %>
	<%="options_Editar_"%><s:property value="%{value}"/><%=".hrefparameter = 'id="%><s:property value="%{value}"/><%="';"%>
	<%="options_Editar_"%><s:property value="%{value}"/><%=".href = '" + request.getContextPath() + "/editarCarpeta.action';" %>
	<%="$.struts2_jquery.bind($('#Editar_"%><s:property value="%{value}"/><%="'),options_Editar_"%><s:property value="%{value}"/><%=");" %>
	<%="});" %>
	<%="</script>" %>

   </s:iterator>



</table>   
   
<!--<a href="editarCarpeta?id=<s:property value='%{value}'/>" > <s:property value="%{key}"/>-<s:property value="%{value}"/></a>-->
<!--	   <p></p>-->
<!--	   -->
