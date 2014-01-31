package com.web.transtemare.acciones.carpetas;

import static org.jmesa.limit.ExportType.EXCEL;
import static org.jmesa.limit.ExportType.PDF;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.jmesa.limit.ExportType;
import org.jmesa.model.TableModel;
import org.jmesa.model.TableModelUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.DroplistFilterEditor;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Carpeta;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class GarantiaCarpetasReport extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {
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

		tableModel.setEditable(false);
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
			((HtmlColumn) tmpColumn).setEditable(false);
			((HtmlColumn) tmpColumn)
					.setFilterEditor(new DroplistFilterEditor());
		}

	}

}
