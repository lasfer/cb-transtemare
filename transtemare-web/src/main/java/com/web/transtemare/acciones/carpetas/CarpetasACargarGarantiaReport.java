package com.web.transtemare.acciones.carpetas;

import static org.jmesa.limit.ExportType.EXCEL;
import static org.jmesa.limit.ExportType.PDF;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.jmesa.limit.ExportType;
import org.jmesa.model.TableModel;
import org.jmesa.model.TableModelUtils;
import org.jmesa.model.WorksheetSaver;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetCallbackHandler;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowStatus;
import org.jmesa.worksheet.WorksheetValidation;
import org.jmesa.worksheet.WorksheetValidationType;
import org.jmesa.worksheet.editor.DroplistWorksheetEditor;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.enums.EnumTipoGarantia;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class CarpetasACargarGarantiaReport extends ActionSupport implements
		ServletRequestAware, ServletResponseAware, WorksheetSaver,
		WorksheetCallbackHandler {
	public CarpetasACargarGarantiaReport(Fachada fac) {
		super();
		this.fac = fac;
	}

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	HttpServletRequest request;
	HttpServletResponse response;
	private Fachada fac;
	private List<Carpeta> carpetas;

	public List<Carpeta> getCarpetas() {
		return carpetas;
	}

	private static final long serialVersionUID = 1L;

	public void saveWorksheet(Worksheet worksheet) {
		saveWorksheetChanges(worksheet);
	}

	@Action(value = "/CarpetasACargarGarantiaReport", results = {
			@Result(location = "/paginas/carpetasACargarGarantiaReport.jsp", name = "success"),
			@Result(location = "index", name = "reload", type = "redirectAction", params = {
					"destino", "urlRep" }),
			@Result(location = "/paginas/carpetasACargarGarantiaReport.jsp", name = "error") })
	public String execute() {
		TableModel tableModel = new TableModel("tag", request, response);
		try {
			tableModel.saveWorksheet(this);
			Carpeta carpeta=new Carpeta();
			carpeta.setCargarInformacionGarantia(true);
			setCarpetas(fac.obtenerCarpetasGarantia(carpeta));
		} catch (Exception e) {
			e.printStackTrace();
		}
		tableModel.setItems(carpetas);
		// Para cuando no es un HTML (EXCEL)
		if (tableModel.isExporting()
				&& ExportType.EXCEL.equals(tableModel.getExportType())) {

			Table table = TableModelUtils.createTable("idCarpeta",
					"nroContenedor", "despachante.nombre", "trans.nombreTransportadora",
					"agenciaMaritima.nombre", "tipoGarantia", "bancoGarantia",
					"nroChequeGarantia", "importeGarantia");
			CarpetasACargarGarantiaReport.setTableData(table);
			tableModel.setTable(table);
			tableModel.render();

			return NONE;
		}

		tableModel.setEditable(true);
		tableModel.setExportTypes(PDF, EXCEL);

		HtmlTable table = TableModelUtils.createHtmlTable("idCarpeta",
				"nroContenedor", "despachante.nombre", "trans.nombreTransportadora",
				"agenciaMaritima.nombre", "tipoGarantia", "bancoGarantia",
				"nroChequeGarantia", "importeGarantia");
		table.setWidth("100%");
		CarpetasACargarGarantiaReport.setTableData(table);
		table.getRow().setUniqueProperty("idCarpeta");
		tableModel.setTable(table);
		if (tableModel.isExporting()) {
			tableModel.render();
			return NONE;
		}

		Set<String> set = new HashSet<String>();
		for (EnumTipoGarantia tipoGarantia : EnumTipoGarantia.values()) {
			set.add(tipoGarantia.name());
		}

		table.getRow().getColumn("tipoGarantia")
				.worksheetEditor(new DroplistWorksheetEditor(set, true));
		tableModel.setTable(table);
		String html = tableModel.render();

		String ajax = request.getParameter("ajax");
		if (ajax != null && ajax.equals("true")) {
			byte[] contents = html.getBytes();
			try {
				response.getOutputStream().write(contents);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return NONE;
		}
		// TableFacade tableFacade = new TableFacade(id, request);
		// Worksheet worksheet = tableFacade.getWorksheet();
		// if(worksheet!=null && worksheet.isSaving() &&
		// !worksheet.hasErrors()){
		// return "reload";
		// }
		request.setAttribute("tabla", html);
		return SUCCESS;
	}

	protected void saveWorksheetChanges(Worksheet worksheet) {
		worksheet.processRows(this);
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;

	}

	public void setCarpetas(List<Carpeta> carpetas) {
		this.carpetas = carpetas;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		response = arg0;

	}

	@Override
	public void process(WorksheetRow worksheetRow) {
		boolean error = false;
		if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.ADD)) {
			// would save the new President here
		} else if (worksheetRow.getRowStatus()
				.equals(WorksheetRowStatus.REMOVE)) {
			// would delete the President here
		} else if (worksheetRow.getRowStatus()
				.equals(WorksheetRowStatus.MODIFY)) {
			String uniqueValue = worksheetRow.getUniqueProperty().getValue();
			Carpeta carpetaBase = fac.obtenerCarpeta(Integer
					.parseInt(uniqueValue));
			Collection<WorksheetColumn> columns = worksheetRow.getColumns();
			for (WorksheetColumn worksheetColumn : columns) {
				Object changedValue = worksheetColumn.getChangedValue();
				if (!StringUtils.equals(worksheetColumn.getOriginalValue(),
						(String) changedValue)) {
					validateColumn(worksheetColumn, (String) changedValue);
					if (worksheetColumn.hasError()) {
						error = true;
						continue;

					}

					String property = worksheetColumn.getProperty();
					try {
						if ("tipoGarantia".equals(property)) {
							changedValue = EnumTipoGarantia
									.getByName((String) changedValue);
						}
						if ("importeGarantia".equals(property)) {
							try {
								changedValue = new BigDecimal(
										(String) changedValue);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						PropertyUtils.setProperty(carpetaBase, property,
								changedValue);
					} catch (Exception ex) {
						String msg = "Not able to set the property ["
								+ property + "] when saving worksheet.";
						throw new RuntimeException(msg);
					}

				}
			}
			if (!error) {
				carpetaBase.setFechaCargaGarantia(new Date());
				carpetaBase.setCargarInformacionGarantia(false);
				fac.modificarCarpeta(carpetaBase);
				borrarCarpetaDelReporte(carpetaBase.getIdCarpeta());

			}
		}

	}

	private void borrarCarpetaDelReporte(Integer idCarpeta) {
		// for (Carpeta carpeta : carpetas) {
		// if(carpeta.getIdCarpeta().equals(idCarpeta)){
		// carpetas.remove(carpeta);
		// break;
		// }
		// }
		carpetas.clear();
		Carpeta carpeta=new Carpeta();
		carpeta.setCargarInformacionGarantia(true);
		carpetas.addAll(fac.obtenerCarpetasGarantia(carpeta));

	}

	/**
	 * An example of how to validate the worksheet column cells.
	 */
	private void validateColumn(WorksheetColumn worksheetColumn,
			String changedValue) {
		String property = worksheetColumn.getProperty();

		if ("importeGarantia".equals(property)) {
			if (!NumberUtils.isNumber(changedValue)) {
				worksheetColumn
						.setError("El valor del importe es obligatorio y debe ser numerico");
			} else {
				worksheetColumn.removeError();
			}
		}
		if ("tipoGarantia".equals(property)) {
			if (StringUtils.isEmpty(changedValue)) {
				worksheetColumn.setError("El tipo de garantia es obligatorio");
			} else {
				worksheetColumn.removeError();
			}
		}
	}

	private static void setTableData(Table table) {

		table.setCaption("Carpetas con garantias a cargar");
		Column tmpColumn = null;
		tmpColumn = table.getRow().getColumn("idCarpeta");
		tmpColumn.setTitle("Nro Carpeta");
		if (tmpColumn instanceof HtmlColumn)
			((HtmlColumn) tmpColumn).setEditable(false);

		tmpColumn = table.getRow().getColumn("despachante.nombre");
		tmpColumn.setTitle("Despachante");
		if (tmpColumn instanceof HtmlColumn)
			((HtmlColumn) tmpColumn).setEditable(false);

		tmpColumn = table.getRow().getColumn("trans.nombreTransportadora");
		tmpColumn.setTitle("Cliente");
		if (tmpColumn instanceof HtmlColumn)
			((HtmlColumn) tmpColumn).setEditable(false);

		tmpColumn = table.getRow().getColumn("agenciaMaritima.nombre");
		tmpColumn.setTitle("Agencia");
		if (tmpColumn instanceof HtmlColumn)
			((HtmlColumn) tmpColumn).setEditable(false);

		tmpColumn = table.getRow().getColumn("tipoGarantia");
		tmpColumn.setTitle("Tipo");
		if (tmpColumn instanceof HtmlColumn) {
			((HtmlColumn) tmpColumn)
					.addWorksheetValidation(new WorksheetValidation(
							WorksheetValidationType.REQUIRED,
							WorksheetValidation.TRUE));
		}

		tmpColumn = table.getRow().getColumn("bancoGarantia");
		tmpColumn.setTitle("Banco");

		tmpColumn = table.getRow().getColumn("nroChequeGarantia");
		tmpColumn.setTitle("Cheque");

		tmpColumn = table.getRow().getColumn("importeGarantia");
		tmpColumn.setTitle("Importe");
		if (tmpColumn instanceof HtmlColumn) {
			((HtmlColumn) tmpColumn)
					.addWorksheetValidation(new WorksheetValidation(
							WorksheetValidationType.REQUIRED,
							WorksheetValidation.TRUE));
			((HtmlColumn) tmpColumn)
					.addWorksheetValidation(new WorksheetValidation(
							WorksheetValidationType.NUMBER,
							WorksheetValidation.TRUE));
		}
	}

}
