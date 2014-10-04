$.subscribe('completediv2', function(event, data) {
	if (event.originalEvent.status == "success") {
		setTimeout(function() {
			$.publish('reloaddiv2');
		}, 60000);
	}
});

$.subscribe('rowselectAlarmas', function(event, data) {
	var id = jQuery("#gridmultitable").jqGrid('getGridParam', 'selrow');
	if (id) {
		var ret = jQuery("#gridmultitable").jqGrid('getRowData', id);
		$("#gridinfo").html(
				"<a  id='Editar' href='javascript:void(0)'>EDITAR CARPETA "
						+ ret.numeroCarpeta + "</a>"
						+ "<script type='text/javascript'>"
						+ "$(document).ready(function () { "
						+ "var options_Editar = {};"
						+ "options_Editar.jqueryaction = 'anchor';"
						+ "options_Editar.id = 'Editar';"
						+ "options_Editar.targets = 'main';"
						+ "options_Editar.hrefparameter = 'id="
						+ ret.numeroCarpeta + "';" + "options_Editar.href = '"
						+ ctx + "/editarCarpeta.action';"
						+ "$.struts2_jquery.bind($('#Editar'),options_Editar);"
						+ "                 " + "});" + "</script>" + "");
	}
});

// Crea el link dinamico para editar la carpeta
$
		.subscribe(
				'editarSubCarpetaSelect',
				function(event, data) {
					var id = jQuery("#editarSubCarpeta").jqGrid('getGridParam',
							'selrow');
					if (id) {
						var ret = jQuery("#editarSubCarpeta").jqGrid(
								'getRowData', id);
						$("#gridEditarCarpetas")
								.html(
										"<a  id='Editar' href='javascript:void(0)' role='button' aria-disabled='false' class='ui-icon-newwin'    >EDITAR CARPETA "
												+ ret.numeroCarpeta
												+ "</a>"
												+ "<script type='text/javascript'>"
												+ "$(document).ready(function () { "
												+ "var options_Editar = {};"
												+ "options_Editar.jqueryaction = 'anchor';"
												+ "options_Editar.id = 'Editar';"
												+ "options_Editar.targets = 'main';"
												+ "options_Editar.hrefparameter = 'id="
												+ ret.numeroCarpeta
												+ "';"
												+ "options_Editar.href = '"
												+ ctx
												+ "/editarCarpeta.action';"
												+ "$.struts2_jquery.bind($('#Editar'),options_Editar);"
												+ "                 "
												+ "});"
												+ "</script>" + "");
					}
				});

// Crea el link dinamico para editar la carpeta
$
		.subscribe(
				'rowselectCarpetasEditar',
				function(event, data) {

					var id = jQuery("#gridedittable").jqGrid('getGridParam',
							'selrow');
					if (id) {
						var ret = jQuery("#gridedittable").jqGrid('getRowData',
								id);
						$("#gridEditarCarpetas")
								.html(
										"<a  id='Editar' href='javascript:void(0)' role='button' aria-disabled='false' class='ui-icon-newwin'    >EDITAR CARPETA "
												+ ret.numeroCarpeta
												+ "</a>"
												+ "<script type='text/javascript'>"
												+ "$(document).ready(function () { "
												+ "var options_Editar = {};"
												+ "options_Editar.jqueryaction = 'anchor';"
												+ "options_Editar.id = 'Editar';"
												+ "options_Editar.targets = 'main';"
												+ "options_Editar.hrefparameter = 'id="
												+ ret.numeroCarpeta
												+ "';"
												+ "options_Editar.href = '"
												+ ctx
												+ "/editarCarpeta.action';"
												+ "$.struts2_jquery.bind($('#Editar'),options_Editar);"
												+ "                 "
												+ "});"
												+ "</script>" + "");
					}
				});

// Crea el link dinamico para generar la caratula
$
		.subscribe(
				'rowselectCarpetasCaratula',
				function(event, data) {
					var id = jQuery("#gridedittable").jqGrid('getGridParam',
							'selrow');
					if (id) {
						var ret = jQuery("#gridedittable").jqGrid('getRowData',
								id);
						$("#gridCaratula")
								.html(
										"<a  id='micdta' href='"
												+ ctx
												+ "/MICDTA?id="
												+ ret.numeroCarpeta + "&camionSust=true"
												+ "' role='button' aria-disabled='false' class='ui-icon-newwin'    >CAMION SUST. "
												+ ret.numeroCarpeta + "</a>");
					}
				});

// Crea el link dinamico para generar la caratula historico
$
		.subscribe(
				'rowselectCarpetasCaratulaHistorico',
				function(event, data) {
					var id = jQuery("#gridedittable").jqGrid('getGridParam',
							'selrow');
					if (id) {
						var ret = jQuery("#gridedittable").jqGrid('getRowData',
								id);
						$("#gridCaratulaHistorico")
								.html(
										"<a  id='Caratula' href='"
												+ ctx
												+ "/CARATULAHistorico.action?id="
												+ ret.idCarpeta
												+ "' role='button' aria-disabled='false' class='ui-icon-newwin'>GENERAR CARATULA 2"
												+ ret.idCarpeta + "</a>");
					}
				});

// Crea el link dinamico para generar el crt
$
		.subscribe(
				'rowselectCarpetasCrt',
				function(event, data) {
					var id = jQuery("#gridedittable").jqGrid('getGridParam',
							'selrow');
					if (id) {
						var ret = jQuery("#gridedittable").jqGrid('getRowData',
								id);
						$("#gridCrt")
								.html(
										"<a  id='crt' href='"
												+ ctx
												+ "/CRT.action?id="
												+ ret.numeroCarpeta
												+ "' role='button' aria-disabled='false' class='ui-icon-newwin'    >GENERAR CRT "
												+ ret.numeroCarpeta + "</a>");
					}
				});

// Crea el link dinamico para generar el crt desde historico
$
		.subscribe(
				'rowselectCarpetasCrtHistorico',
				function(event, data) {
					var id = jQuery("#gridedittable").jqGrid('getGridParam',
							'selrow');
					if (id) {
						var ret = jQuery("#gridedittable").jqGrid('getRowData',
								id);
						$("#gridCrtHistorico")
								.html(
										"<a  id='crt' href='"
												+ ctx
												+ "/CRTHistorico.action?id="
												+ ret.idCarpeta
												+ "' role='button' aria-disabled='false' class='ui-icon-newwin'    >GENERAR CRT "
												+ ret.idCarpeta + "</a>");
					}
				});

// Crea el link dinamico para generar el micdta
$
		.subscribe(
				'rowselectCarpetasMicDta',
				function(event, data) {
					var id = jQuery("#gridedittable").jqGrid('getGridParam',
							'selrow');
					if (id) {
						var ret = jQuery("#gridedittable").jqGrid('getRowData',
								id);
						$("#gridMicDta")
								.html(
										"<a  id='micdta' href='"
												+ ctx
												+ "/MICDTA?id="
												+ ret.numeroCarpeta
												+ "' role='button' aria-disabled='false' class='ui-icon-newwin'    >GENERAR MICDTA "
												+ ret.numeroCarpeta + "</a>");
					}
				});

//Crea el link dinamico para generar sabana
$
		.subscribe(
				'rowselectCarpetasSabana',
				function(event, data) {
					var id = jQuery("#gridedittable").jqGrid('getGridParam',
							'selrow');
					if (id) {
						var ret = jQuery("#gridedittable").jqGrid('getRowData',
								id);
						$("#gridSabana")
								.html(
										"<a  id='sabana' href='"
												+ ctx
												+ "/SABANA?id="
												+ ret.numeroCarpeta
												+ "' role='button' aria-disabled='false' class='ui-icon-newwin'    >GENERAR SABANA "
												+ ret.numeroCarpeta + "</a>");
					}
				});


// Crea el link dinamico para generar el micdta historico
$
		.subscribe(
				'rowselectCarpetasMicDtaHistorico',
				function(event, data) {
					var id = jQuery("#gridedittable").jqGrid('getGridParam',
							'selrow');
					if (id) {
						var ret = jQuery("#gridedittable").jqGrid('getRowData',
								id);
						$("#gridMicDtaHistorico")
								.html(
										"<a  id='micdta' href='"
												+ ctx
												+ "/MICDTAHistorico?id="
												+ ret.idCarpeta
												+ "' role='button' aria-disabled='false' class='ui-icon-newwin'    >GENERAR MICDTA "
												+ ret.idCarpeta + "</a>");
					}
				});

// $("body").append('<div id="ajaxBusy"> <p> <img src="img/indicator.gif"> </p>
// </div>');

$(document).ajaxStart(function() {
	$('#ajaxBusy').show();
}).ajaxStop(function() {
	$('#ajaxBusy').hide();
});

$("#valorFot").blur(function() {
	alert('lalal');
	// take US format text into std number format
	var number = $(this).parseNumber({
		format : "#,###.00",
		locale : "es"
	}, false);
	// write the number out
	$("#valorFot").val(number);
});

$.subscribe('formatFot', function(event, data) {

	// take US format text into std number format
	// alert($(this));
	var number = '123456789'.parseNumber({
		format : "#,###.00",
		locale : "es"
	}, false);
	alert(number);
	// var number = $(this).parseNumber({format:"#,###.00", locale:"es"},
	// false);
	// alert(number);
	// write the number out
	$("#valorFot").val(number);

});

// Crea el link dinamico para generar el preview
$.subscribe('rowselectCarpetasPreview', function(event, data) {
	var id = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
	if (id) {
		var ret = jQuery("#gridedittable").jqGrid('getRowData', id);
		$.ajax({
			url : ctx + "/resumenCortoCarpeta?id=" + ret.numeroCarpeta,
			async : true,
			contentType : "application/x-www-form-urlencoded",
			dataType : "html",
			error : function(objeto, quepaso, otroobj) {
				alert("Estas viendo esto por que fallé");
				alert("Pasó lo siguiente: " + quepaso);
			},
			global : false,
			ifModified : false,
			processData : true,
			success : function(datos) {
				$("#previewID").html(datos);
			},
			type : "GET"
		});
	}
});

// function puntitos(donde,caracter,campo){
// var decimales = false
// campo = eval("donde.form." + campo)
// for (d =0; d < campo.length; d++)
// {
// if(campo[d].checked == true)
// {
// dec = new Number(campo[d].value)
// break;
// }
// }
// if (dec != 0)
// {decimales = true}
//
//
//
//
// pat = /[\*,\+,\(,\),\?,\\,\$,\[,\],\^]/
// valor = donde.value
// largo = valor.length
// crtr = true
// if(isNaN(caracter) || pat.test(caracter) == true)
// {
// if (pat.test(caracter)==true)
// {caracter = "\\" + caracter}
// carcter = new RegExp(caracter,"g")
// valor = valor.replace(carcter,"")
// donde.value = valor
// crtr = false
// }
// else
// {
// var nums = new Array()
// cont = 0
// for(m=0;m<largo;m++)
// {
// if(valor.charAt(m) == "." || valor.charAt(m) == " " || valor.charAt(m) ==
// ",")
// {continue;}
// else{
// nums[cont] = valor.charAt(m)
// cont++
// }
//		
// }
// }
//
// if(decimales == true) {
// ctdd = eval(1 + dec);
// nmrs = 1
// }
// else {
// ctdd = 1; nmrs = 3
// }
// var cad1="",cad2="",cad3="",tres=0
// if(largo > nmrs && crtr == true)
// {
// for (k=nums.length-ctdd;k>=0;k--){
// cad1 = nums[k]
// cad2 = cad1 + cad2
// tres++
// if((tres%3) == 0){
// if(k!=0){
// cad2 = "." + cad2
// }
// }
// }
//		
// for (dd = dec; dd > 0; dd--)
// {cad3 += nums[nums.length-dd] }
// if(decimales == true)
// {cad2 += "," + cad3}
// donde.value = cad2
// }
// donde.focus()
// }


