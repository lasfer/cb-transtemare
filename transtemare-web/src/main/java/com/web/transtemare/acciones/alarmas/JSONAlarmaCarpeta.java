package com.web.transtemare.acciones.alarmas;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.commons.Globales;
import com.core.transtemare.dto.AlarmasDTO;
import com.core.transtemare.entidades.Carpeta;
import com.core.transtemare.excepciones.FachadaException;
import com.opensymphony.xwork2.ActionSupport;


@ParentPackage(value="default")
public class JSONAlarmaCarpeta extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<AlarmasDTO> gridModel;
	private Integer rows = 0;
	private Integer page = 0;
	private String sord;
	private String sidx;
	private String searchField;
	private String searchString;
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper;
	private Integer total = 0;
	private Integer records = 0;
	private Fachada fac;
	Logger logger = Logger.getLogger(JSONAlarmaCarpeta.class);
	
	
	
	public JSONAlarmaCarpeta(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonAlarmas", results = {
			@Result(type = "json", name = "success")})
	public String execute() {

		int to = (rows * page);
		int from = to - rows;
		gridModel = new ArrayList<AlarmasDTO>();
		
		
		// Your logic to search and select the required data.
		try {
			records = fac.totalAlarmas();
			List<Carpeta> listaCarpetas =fac.alarmaCarpetas(from,to);
			for (Carpeta c : listaCarpetas){ 		
				gridModel.add(new AlarmasDTO(c.getIdCarpeta(),c.getFechaVencimiento(),c.getFechaAlta(),c.getFechaModificacion(),c.getNroContenedor()));
			}
			
		} catch (FachadaException e) {
			logger.error(e.getMessage());
			logger.error(Globales.ERROR_AL_CONSULTAR_ALARMA_CARPETAS);
			return ERROR;
		}

		 total = (int) Math.ceil((double) records / (double) rows);
		return SUCCESS;
	}

	public ArrayList<AlarmasDTO> getGridModel() {
		return gridModel;
	}

	public void setGridModel(ArrayList<AlarmasDTO> gridModel) {
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