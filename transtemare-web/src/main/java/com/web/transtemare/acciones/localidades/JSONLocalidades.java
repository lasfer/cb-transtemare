package com.web.transtemare.acciones.localidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.entidades.Localidad;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class JSONLocalidades extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private List<Localidad> gridModel;
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
	private Map<String, Object> session;
	private Logger logger = Logger.getLogger(JSONLocalidades.class);

	public JSONLocalidades(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonTableLocalidades", results = { @Result(type = "json", name = "success") })
	public String execute() {
		int to = (rows * page);
		int from = to - rows;
		try {
			gridModel = fac.darTodasLasLocalidades(from, to);
			ArrayList<Localidad> locs = new ArrayList<Localidad>();
			fac.darTodasLasLocalidades(locs);
			records = locs.size();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}
		total = (int) Math.ceil((double) records / (double) rows);
		return SUCCESS;
	}

	public void validateExecute() {
		logger.debug("Entro Validate execute" + JSONLocalidades.class);
	}

	public void validateDoExecute() {
		logger.debug("Entro Validate do execute" + JSONLocalidades.class);
	}

	public String getJSON() {
		return execute();
	}

	public List<Localidad> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<Localidad> gridModel) {
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
