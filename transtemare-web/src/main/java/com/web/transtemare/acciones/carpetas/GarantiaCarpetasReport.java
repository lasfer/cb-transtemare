package com.web.transtemare.acciones.carpetas;

import static org.jmesa.limit.ExportType.EXCEL;
import static org.jmesa.limit.ExportType.PDF;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.jmesa.limit.ExportType;
import org.jmesa.model.TableModel;
import org.jmesa.model.TableModelUtils;
import org.jmesa.model.WorksheetSaver;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetCallbackHandler;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowStatus;
import org.jmesa.worksheet.editor.RemoveRowWorksheetEditor;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.enums.EnumTipoGarantia;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class GarantiaCarpetasReport extends ActionSupport implements
		ServletRequestAware, ServletResponseAware, WorksheetSaver,
		WorksheetCallbackHandler{
	public GarantiaCarpetasReport(Fachada fac) {
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

	@Action(value = "/GarantiaCarpetasReport", results = {
			@Result(location = "/paginas/carpetasACargarGarantiaReport.jsp", name = "success"),
			@Result(location = "index", name = "reload", type = "redirectAction", params = {
					"destino", "urlRep" }),
			@Result(location = "/paginas/carpetasACargarGarantiaReport.jsp", name = "error") })
	public String execute() {
		TableModel tableModel = new TableModel("tag", request, response);
		tableModel.saveWorksheet(this);
		try {
			Carpeta carpeta = new Carpeta();
			setCarpetas(fac.obtenerCarpetasGarantia(carpeta));
		} catch (Exception e) {
			e.printStackTrace();
		}
		tableModel.setItems(carpetas);
		// Para cuando no es un HTML (EXCEL)
		if (tableModel.isExporting()
				&& ExportType.EXCEL.equals(tableModel.getExportType())) {

			Table table = TableModelUtils.createTable("idCarpeta",
					"nroContenedor", "despachante.nombre",
					"trans.nombreTransportadora", "agenciaMaritima.nombre",
					"tipoGarantia", "bancoGarantia", "nroChequeGarantia",
					"importeGarantia", "contenedorDevuelto");
			GarantiaCarpetasReport.setTableData(table);
			tableModel.setTable(table);
			tableModel.render();

			return NONE;
		}

		tableModel.setEditable(true);
		tableModel.setExportTypes(PDF, EXCEL);
		
		HtmlTable table = TableModelUtils.createHtmlTable("idCarpeta",
				"nroContenedor", "despachante.nombre",
				"trans.nombreTransportadora", "agenciaMaritima.nombre",
				"tipoGarantia", "bancoGarantia", "nroChequeGarantia",
				"importeGarantia", "contenedorDevuelto");
		table.setWidth("100%");
		GarantiaCarpetasReport.setTableData(table);
		table.getRow().setUniqueProperty("idCarpeta");
		tableModel.setTable(table);
		if (tableModel.isExporting()) {
			tableModel.render();
			return NONE;
		}

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
		request.setAttribute("tabla", html);
		return SUCCESS;
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

	private static void setTableData(Table table) {

		table.setCaption("Garantías no devueltas");
		Column tmpColumn = null;
		tmpColumn = table.getRow().getColumn("idCarpeta");
		tmpColumn.setTitle("Nro Carpeta");
		if (tmpColumn instanceof HtmlColumn)
			((HtmlColumn) tmpColumn).setEditable(false);

		tmpColumn = table.getRow().getColumn("despachante.nombre");
		tmpColumn.setTitle("Despachante");
		if (tmpColumn instanceof HtmlColumn) {
			((HtmlColumn) tmpColumn).setEditable(false);
			((HtmlColumn) tmpColumn)
					.setFilterEditor(new DroplistFilterEditor());
		}

		tmpColumn = table.getRow().getColumn("trans.nombreTransportadora");
		tmpColumn.setTitle("Transportadora");
		if (tmpColumn instanceof HtmlColumn) {
			((HtmlColumn) tmpColumn).setEditable(false);
			((HtmlColumn) tmpColumn)
					.setFilterEditor(new DroplistFilterEditor());
		}

		tmpColumn = table.getRow().getColumn("agenciaMaritima.nombre");
		tmpColumn.setTitle("Agencia");
		if (tmpColumn instanceof HtmlColumn) {
			((HtmlColumn) tmpColumn).setEditable(false);
			((HtmlColumn) tmpColumn)
					.setFilterEditor(new DroplistFilterEditor());
		}

		tmpColumn = table.getRow().getColumn("tipoGarantia");
		tmpColumn.setTitle("Tipo");
		if (tmpColumn instanceof HtmlColumn) {
			((HtmlColumn) tmpColumn).setFilterable(false);
		}

		tmpColumn = table.getRow().getColumn("bancoGarantia");
		tmpColumn.setTitle("Banco");
		if (tmpColumn instanceof HtmlColumn) {
			((HtmlColumn) tmpColumn).setEditable(false);
			((HtmlColumn) tmpColumn)
					.setFilterEditor(new DroplistFilterEditor());
		}

		tmpColumn = table.getRow().getColumn("nroChequeGarantia");
		tmpColumn.setTitle("Cheque");
		if (tmpColumn instanceof HtmlColumn) {
			((HtmlColumn) tmpColumn).setEditable(false);
			((HtmlColumn) tmpColumn)
					.setFilterEditor(new DroplistFilterEditor());
		}

		tmpColumn = table.getRow().getColumn("importeGarantia");
		tmpColumn.setTitle("Importe");
		if (tmpColumn instanceof HtmlColumn) {
			((HtmlColumn) tmpColumn).setEditable(false);
			((HtmlColumn) tmpColumn)
					.setFilterEditor(new DroplistFilterEditor());
		}

		tmpColumn = table.getRow().getColumn("contenedorDevuelto");
		tmpColumn.setTitle("Cont. Devuelto");
		if (tmpColumn instanceof HtmlColumn) {
			((HtmlColumn) tmpColumn).setEditable(true);
			((HtmlColumn) tmpColumn)
					.setFilterEditor(new DroplistFilterEditor());
			((HtmlColumn) tmpColumn).setCellEditor(new CellEditor() {
				@Override
				public Object getValue(Object item, String property,
						int rowcount) {
					Object value = ItemUtils.getItemValue(item, property);
					HtmlBuilder html = new HtmlBuilder();
					html.bold().append(
							BooleanUtils.isTrue(((Carpeta) item)
									.getGarantiaDevuelta()) ? "SI" : "NO");
					return html.toString();
				}
			});
		}
		HtmlColumn remove = new HtmlColumn("remove");
		remove.setWorksheetEditor(new RemoveRowWorksheetEditor());
		remove.setTitle("borrar");
		remove.setFilterable(false);
		remove.setSortable(false);
		table.getRow().addColumn(remove);

	}

	@Override
	public void process(WorksheetRow worksheetRow) {
		if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.ADD)) {
			// would save the new President here
		} else if (worksheetRow.getRowStatus()
				.equals(WorksheetRowStatus.REMOVE)) {
			String uniqueValue = worksheetRow.getUniqueProperty().getValue();
			Carpeta carpetaBase = fac.obtenerCarpeta(Integer
					.parseInt(uniqueValue));

			Collection<WorksheetColumn> columns = worksheetRow.getColumns();

			for (WorksheetColumn worksheetColumn : columns) {
				Object changedValue = worksheetColumn.getChangedValue();
				if (!StringUtils.equals(worksheetColumn.getOriginalValue(),
						(String) changedValue)) {
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

			carpetaBase.setGarantiaDevuelta(true);
			carpetaBase.setCargarInformacionGarantia(false);
			borrarCarpetaDelReporte();
			fac.modificarCarpeta(carpetaBase);
			// would delete the President here
		} else if (worksheetRow.getRowStatus()
				.equals(WorksheetRowStatus.MODIFY)) {
		}
	}

	protected void saveWorksheetChanges(Worksheet worksheet) {
		worksheet.processRows(this);
	}

	public void saveWorksheet(Worksheet worksheet) {
		saveWorksheetChanges(worksheet);
	}

	private void borrarCarpetaDelReporte() {
		carpetas.clear();
		Carpeta carpeta = new Carpeta();
		carpeta.setCargarInformacionGarantia(true);
		carpetas.addAll(fac.obtenerCarpetasGarantia(carpeta));
	}
}
