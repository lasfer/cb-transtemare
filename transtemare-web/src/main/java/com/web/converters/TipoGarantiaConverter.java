package com.web.converters;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import com.core.transtemare.enums.EnumTipoGarantia;
import com.core.transtemare.enums.TipoContenedor;

public class TipoGarantiaConverter extends StrutsTypeConverter {

	public Object convertFromString(@SuppressWarnings("rawtypes") Map context,
			String[] strings, @SuppressWarnings("rawtypes") Class toClass) {
		if (ArrayUtils.isEmpty(strings)
				|| StringUtils.isEmpty(strings[0].trim())) {
			return null;
		}
		return EnumTipoGarantia.getByName(strings[0]);
	}

	public String convertToString(@SuppressWarnings("rawtypes") Map context,
			Object tipoGarantia) {
		if (tipoGarantia != null && tipoGarantia instanceof EnumTipoGarantia) {
			return ((TipoContenedor) tipoGarantia).name();
		} else {
			return null;
		}
	}
}
