package com.web.converters;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import com.core.transtemare.enums.TipoContenedor;

public class TipoContenedorConverter extends StrutsTypeConverter {

	public Object convertFromString(@SuppressWarnings("rawtypes") Map context,
			String[] strings, @SuppressWarnings("rawtypes") Class toClass) {
		if (ArrayUtils.isEmpty(strings)
				|| StringUtils.isEmpty(strings[0].trim())) {
			return null;
		}
		return TipoContenedor.getByValue(strings[0]);
	}

	public String convertToString(@SuppressWarnings("rawtypes") Map context,
			Object tipoContendedor) {
		if (tipoContendedor != null
				&& tipoContendedor instanceof TipoContenedor) {
			return ((TipoContenedor) tipoContendedor).getValue();
		} else {
			return null;
		}
	}
}
