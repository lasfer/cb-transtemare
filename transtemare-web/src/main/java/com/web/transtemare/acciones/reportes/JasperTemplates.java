package com.web.transtemare.acciones.reportes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Loads/compiles JRXML from the classpath without using URL.getFile(), which
 * breaks on paths with spaces (e.g. C:\Program Files\...) because of %20.
 */
final class JasperTemplates {

	private JasperTemplates() {
	}

	static JasperReport compile(String classpathResource) throws JRException {
		InputStream in = JasperTemplates.class.getClassLoader()
				.getResourceAsStream(classpathResource);
		if (in == null) {
			throw new JRException("JRXML not found on classpath: " + classpathResource);
		}
		try {
			return JasperCompileManager.compileReport(in);
		} finally {
			try {
				in.close();
			} catch (IOException ignored) {
				// ignore
			}
		}
	}

	/**
	 * Absolute filesystem path to a classpath directory, with spaces decoded.
	 * Returns null if the resource is missing.
	 */
	static String resourceDirPath(String classpathDir) {
		try {
			URL url = JasperTemplates.class.getClassLoader().getResource(classpathDir);
			if (url == null) {
				return null;
			}
			return new File(url.toURI()).getAbsolutePath() + File.separator;
		} catch (Exception e) {
			return null;
		}
	}
}
