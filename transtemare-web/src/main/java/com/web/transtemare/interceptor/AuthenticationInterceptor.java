package com.web.transtemare.interceptor;

import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AuthenticationInterceptor.class);

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		@SuppressWarnings("rawtypes")
		Map session = actionInvocation.getInvocationContext().getSession();
		String logeado = (String) session.get("loged");
		if (logeado != null && logeado.equals("logueado")) {
			return actionInvocation.invoke();

		} else {
			return Action.LOGIN;
		}
	}
}