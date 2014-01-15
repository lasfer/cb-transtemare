package com.web.transtemare.acciones.transportadora;

import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.dto.TransportadoraDTO;
import com.core.transtemare.entidades.Transportadora;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class JsonTableTransportadora extends ActionSupport implements
		SessionAware {

	private static final long serialVersionUID = 1L;
	private ArrayList<TransportadoraDTO> gridModel;
	// get how many rows we want to have into the grid - rowNum attribute in the
	// grid
	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// sorting order - asc or desc
	private String sord;
	// get index row - i.e. user click to sort.
	private String sidx;
	// Search Field
	private String searchField;
	// The Search String
	private String searchString;
	// he Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper;
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private Fachada fac;
	private Map<String, Object> session;

	public JsonTableTransportadora(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonTableTransportadoras", results = { @Result(type = "json", name = "success") })
	public String execute() {

		int to = (rows * page);
		int from = to - rows;
		gridModel = new ArrayList<TransportadoraDTO>();
		ArrayList<Transportadora> trans = new ArrayList<Transportadora>();

		// Your logic to search and select the required data.
		try {

			fac.obtenerTodasLasTransportadoras(trans, from, to);
			records = fac.totalTransportadoras();
			for (Transportadora t : trans) {
				gridModel.add(new TransportadoraDTO(t.getIdTransportadora(), t
						.getNombreTransportadora(), t.getDomicilio(), t
						.getPrefijo(), t.getRolDelContribuyente(), t
						.getLocalidad().getDescripcion(), t.getLocalidad()
						.getPais().getDescripcion(), t.getNumerador(), t
						.getNombreArchivo()));
			}

		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Error al consultar las transportadoras");
			return ERROR;
		}

		// calculate the total pages for the query
		total = (int) Math.ceil((double) records / (double) rows);
		// total = 10;

		return SUCCESS;
	}

	public void validateExecute() {
		
	}

	public void validateDoExecute() {
		System.out.println("Entro Validate do execute"
				+ JsonTableTransportadora.class);
	}

	public String getJSON() {
		return execute();
	}

	public ArrayList<TransportadoraDTO> getGridModel() {
		return gridModel;
	}

	public void setGridModel(ArrayList<TransportadoraDTO> gridModel) {
		this.gridModel = gridModel;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}

}