package com.web.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;


import com.opensymphony.xwork2.conversion.TypeConversionException;

public class StringToDateConverter extends StrutsTypeConverter {

	public Object convertFromString(@SuppressWarnings("rawtypes") Map context, String[] strings, @SuppressWarnings("rawtypes") Class toClass) {
		if (ArrayUtils.isEmpty(strings)
				|| StringUtils.isEmpty(strings[0].trim())) {
			return null;
		}
		DateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yy");
		try {
			return DATETIME_FORMAT.parse(strings[0]);
		} catch (ParseException e) {
			throw new TypeConversionException(
					"Unable to convert given object to date: " + strings[0]);
		}
	}

	public String convertToString(@SuppressWarnings("rawtypes") Map context, Object date) {
		DateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yy");
		if (date != null && date instanceof Date) {
			return DATETIME_FORMAT.format(date);
		} else {
			return null;
		}
	}
}
