package com.web.converters;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class TerminalConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(@SuppressWarnings("rawtypes")Map arg0, String[] arg1, @SuppressWarnings("rawtypes")Class arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String convertToString(@SuppressWarnings("rawtypes")Map arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

//	public Object convertFromString(@SuppressWarnings("rawtypes") Map context,
//			String[] strings, @SuppressWarnings("rawtypes") Class toClass) {
//		if (ArrayUtils.isEmpty(strings)
//				|| StringUtils.isEmpty(strings[0].trim())) {
//			return null;
//		}
//		return Terminal.getByValue(strings[0]);
//	}
//
//	public String convertToString(@SuppressWarnings("rawtypes") Map context,
//			Object terminal) {
//		if (terminal != null && terminal instanceof Terminal) {
//			return ((Terminal) terminal).getValue();
//		} else {
//			return null;
//		}
//	}
}
