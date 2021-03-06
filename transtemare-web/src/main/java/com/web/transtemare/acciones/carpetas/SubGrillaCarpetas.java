package com.web.transtemare.acciones.carpetas;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.dto.CarpetaDTO;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.excepciones.ActionException;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class SubGrillaCarpetas extends ActionSupport {

	private Logger logger = Logger.getLogger(JSONCarpetas.class);
	private static final long serialVersionUID = 1L;
	private ArrayList<CarpetaDTO> gridModel;
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
	private Integer id;

	public SubGrillaCarpetas(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonCarpetasSubLista", results = { @Result(type = "json", name = "success") })
	public String execute() throws ActionException {

		gridModel = new ArrayList<CarpetaDTO>();

		// Your logic to search and select the required data.
		try {
			List<Carpeta> carp = fac.obtenerSubCarpetas(false, id);

			for (Carpeta c : carp) {
				// if(searchString.equals(c.getIdCarpeta().toString())){
				gridModel.add(new CarpetaDTO(c));
				// }
			}

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			addActionError("Error al consultar las carpetas");
			throw new ActionException(e.getMessage());
		}

		// calculate the total pages for the query
		// total = (int) Math.ceil((double) records / (double) rows);
		return SUCCESS;
	}

	public void validateExecute() {
	}

	public void validateDoExecute() {
	}

	@JSON
	public ArrayList<CarpetaDTO> getGridModel() {
		return gridModel;
	}

	public void setGridModel(ArrayList<CarpetaDTO> gridModel) {
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

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

}
