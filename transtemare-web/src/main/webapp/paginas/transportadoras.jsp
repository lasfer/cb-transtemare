<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>



<div  align="center">

<s:url id="urlTransportadora" action="jsonTableTransportadoras"  />
<s:url id="editurl" action="ABMTransportadora"/>
<s:url id="paises" action="lp"/>
<s:url id="ciudades" action="lc"/>

        <sjg:grid  autoencode="false"
    	id="gridedittable" caption="Transportadoras" dataType="json" href="%{urlTransportadora}" pager="true" navigator="true" 
    	navigatorAddOptions="{height:300,reloadAfterSubmit:true,top:1,left:1,width:350}"
    	navigatorEditOptions="{height:300,width:350, reloadAfterSubmit:true}" navigatorDelete="true" navigatorSearch="false"
    	navigatorDeleteOptions="{height:150,reloadAfterSubmit:true}"  gridModel="gridModel" rowList="15" rowNum="15" editurl="%{editurl}"  editinline="false"  >
    	
    	<sjg:gridColumn name="id" index="id"  title="ID"  editable="false"  width="30" key="true" required="false"  />
    	<sjg:gridColumn name="nombre"  index="nombre" id="nombre" title="Nombre"   editable="true" edittype="text"  editrules="{required: true}" />
    	<sjg:gridColumn name="domicilio" index="domicilio" id="domicilio" title="Domicilio"   editable="true" edittype="text"  />
		<sjg:gridColumn name="pais" index="pais" title="Pais" editable="false"  edittype="select" editoptions="{ dataUrl:'%{paises}' }" width="85" />
		<sjg:gridColumn name="localidad"  index="localidad" title="Localidad"   editable="true" edittype="select" editoptions="{ dataUrl:'%{ciudades}' }" />
    	<sjg:gridColumn name="prefijo"  index="prefijo" id="prefijo" title="Prefijo"  width="60"  />
    	<sjg:gridColumn name="prefijo"  index="prefijo" title="Prefijo" editable="true" hidden="true"  editrules="{edithidden:true}" edittype="text" />
    	<sjg:gridColumn name="rol" index="rol" id="rol" title="Rol del contribuyente"  editable="true" edittype="text"  />
    	<sjg:gridColumn name="numerador"  index="numerador" title="Numerador" editable="true" hidden="true"  editrules="{edithidden:true,required: true}" edittype="text" />
    	<sjg:gridColumn name="numerador" width="70" index="numerador" title="Numerador"  />
    	<sjg:gridColumn name="imagen" width="90" index="imagen" title="Imagen" editable="true" />
    	
    </sjg:grid>
<!--    <sj:submit id="grid_edit_addbutton" value="Crear Transportadora" onClickTopics="rowadd" button="true"  />-->
    

  </div>
  
 
<div id="ie_clearing"> &#160; </div>








