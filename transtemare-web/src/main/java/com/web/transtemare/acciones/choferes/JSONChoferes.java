package com.web.transtemare.acciones.choferes;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.dto.ChoferDTO;
import com.core.transtemare.entidades.Responsable;
import com.core.transtemare.excepciones.FachadaException;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class JSONChoferes extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(JSONChoferes.class);
	private ArrayList<ChoferDTO> gridModel;
	private Integer rows = 0;
	private Integer page = 0;
	private String sord;
	private String sidx;
	private String searchField;
	private String searchString;
	private String searchOper;
	private Integer total = 0;
	private Integer records = 0;
	private Fachada fac;

	public JSONChoferes(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonChoferes", results = { @Result(type = "json", name = "success") })
	public String execute() {

		int to = (rows * page);
		int from = to - rows;
		gridModel = new ArrayList<ChoferDTO>();
		List<Responsable> choferes = null;
		// Your logic to search and select the required data.
		try {
			Responsable responsable = new Responsable();
			if ("nombre".equals(searchField)) {
				responsable.setNombre(searchString);
			} else if ("apellido".equals(searchField)) {
				responsable.setApellido(searchString);
			}
			choferes = fac.obtenerTodosLosChoferes(responsable, from, to);
			records = fac.totalChoferes();
			for (Responsable t : choferes) {
				gridModel.add(new ChoferDTO(t.getIdResponsable(),
						t.getNombre(), t.getApellido(), t.getDocumento(), t
								.getLocalidad().getPais().getDescripcion(), t
								.getLocalidad().getDescripcion(), t
								.getTelefono()));
			}

		} catch (FachadaException e) {
			logger.error(e.getMessage());
			addActionError("Error al consultar las transportadoras");
			return ERROR;
		}
		total = (int) Math.ceil((double) records / (double) rows);
		return SUCCESS;
	}

	public void validateExecute() {
	}

	public void validateDoExecute() {
	}

	public String getJSON() {
		return execute();
	}

	public ArrayList<ChoferDTO> getGridModel() {
		return gridModel;
	}

	public void setGridModel(ArrayList<ChoferDTO> gridModel) {
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

	public Fachada getFac() {
		return fac;
	}

	public void setFac(Fachada fac) {
		this.fac = fac;
	}
}
