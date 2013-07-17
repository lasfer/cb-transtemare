package com.web.transtemare.acciones.historico;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.dto.HistoricoDTO;
import com.core.transtemare.entidades.Historico;
import com.core.transtemare.excepciones.ActionException;
import com.core.transtemare.excepciones.FachadaException;
import com.opensymphony.xwork2.ActionSupport;


@ParentPackage(value = "default")
public class JSONCarpetasHistorico extends ActionSupport{
	
	private Logger logger = Logger.getLogger(JSONCarpetasHistorico.class);
	private static final long serialVersionUID = 1L;
	private ArrayList<HistoricoDTO> gridModel;
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
	
	
	
	
	public JSONCarpetasHistorico(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonCarpetasHistorico", results = {
	@Result(type = "json", name = "success")})
	public String execute() throws ActionException {

		int to = (rows * page);
		int from = to - rows;
		gridModel = new ArrayList<HistoricoDTO>();
		
		
		// Your logic to search and select the required data.
		try {
			List<Historico> carp = fac.obtenerCarpetasHistorico(from,to);
			
			records = fac.totalCarpetasHistoricos();
			
			for (Historico historico : carp) {
				gridModel.add(new HistoricoDTO(historico.getIdCarpeta(),historico.getCarpetaPadre(),historico.getFechaAltaCarpeta(),historico.getFechaModificacion(),historico.getFechaPasadoHistorico(),historico.getNumeroContenedor()));
			}
			
		} catch (FachadaException e) {
			logger.error(e.getMessage());
			addActionError("Error al consultar las carpetas");
			throw new ActionException(e.getMessage());
		}

		// calculate the total pages for the query
		 total = (int) Math.ceil((double) records / (double) rows);
		return SUCCESS;
	}

	public void validateExecute(){
	}
	
	public void validateDoExecute(){
	}
	
	@JSON
	public ArrayList<HistoricoDTO> getGridModel() {
		return gridModel;
	}

	public void setGridModel(ArrayList<HistoricoDTO> gridModel) {
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
