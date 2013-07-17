<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%--<%@ taglib prefix="jo" uri="/struts-json-tags" %>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/commons.js" ></script> 
<s:url id="urlUsuarios" action="jsonListaUsuarios"  />


<style>
      .column { 
	width: 80%;
	float: left;
	padding-bottom: 100px;
	}
	
	</style> 



<div id="col1" style="width: 40%" >
  <div id="col1_content" class="clearfix">
 
   <sjg:grid  autoencode="false" id="gridedittable" caption="Carpetas" dataType="json" href="%{urlUsuarios}" pager="true" 
         navigator="true" gridModel="gridModel" rowList="15" rowNum="15"   navigatorDelete="false" navigatorAdd="true"
          navigatorEdit="true"   navigatorSearchOptions="{sopt:['eq']}"  >
    	
	    	<sjg:gridColumn  name="user" index="user" search="true"  title="Usuario"  width="60" key="true" required="false"  />
	    	<sjg:gridColumn name="password"  index="password" search="false"  title="Password"  width="90"  />
	    	<sjg:gridColumn name="profile"  index="profile" title="Perfil"   edittype="select" editoptions="{ dataUrl:'%{ciudades}' }" />
   		 </sjg:grid>
    
  </div>
</div>

<div id="col3">
  <div id="col3_content" >


    
<div id='previewID' class='result ui-widget-content ui-corner-all' ></div>

    
  </div>    
  </div>
  <!-- IE Column Clearing -->
  <div id="ie_clearing"> &#160; </div>




