package com.web.transtemare.acciones.empresas;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.core.transtemare.commons.Fachada;
import com.core.transtemare.dto.EmpresaDTO;
import com.core.transtemare.entidades.Empresa;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class JSONEmpresas extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(JSONEmpresas.class);
	private ArrayList<EmpresaDTO> gridModel;
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

	public JSONEmpresas(Fachada fac) {
		super();
		this.fac = fac;
	}

	@Action(value = "/jsonEmpresas", results = { @Result(type = "json", name = "success") })
	public String execute() {

		int to = (rows * page);
		int from = to - rows;
		gridModel = new ArrayList<EmpresaDTO>();
		List<Empresa> empresas = new ArrayList<Empresa>();
		Empresa empresa = new Empresa();
		empresa.setNombre("");

		try {
			if ("nombre".equals(searchField)) {
				empresa.setNombre(searchString);
			}
			empresas = fac.obtenerEmpresasTabla(empresa, from, to);
			records = fac.totalEmpresas();
			for (Empresa e : empresas) {
				gridModel.add(new EmpresaDTO(e.getIdEmpresa(), e.getNombre(), e
						.getRolContribuyente(), e.getTipo(), e.getDireccion(),
						e.getLocalidad().getPais().getDescripcion(), e
								.getLocalidad().getDescripcion(), e
								.getNombreCorto(), e.getCodigo()));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("Error al consultar las transportadoras");
			return ERROR;
		}

		// calculate the total pages for the query
		total = (int) Math.ceil((double) records / (double) rows);

		return SUCCESS;
	}

	public void validateExecute() {
	}

	public void validateDoExecute() {
	}

	public ArrayList<EmpresaDTO> getGridModel() {
		return gridModel;
	}

	public void setGridModel(ArrayList<EmpresaDTO> gridModel) {
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
