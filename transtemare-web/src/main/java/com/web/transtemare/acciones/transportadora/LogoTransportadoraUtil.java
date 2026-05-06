package com.web.transtemare.acciones.transportadora;

final class LogoTransportadoraUtil {

	static final int MAX_LOGO_BYTES = 2 * 1024 * 1024;

	private LogoTransportadoraUtil() {
	}

	static String detectContentType(byte[] bytes) {
		if (bytes == null || bytes.length < 2) {
			return null;
		}
		if (bytes.length >= 8
				&& (bytes[0] & 0xff) == 0x89
				&& bytes[1] == 0x50
				&& bytes[2] == 0x4e
				&& bytes[3] == 0x47
				&& bytes[4] == 0x0d
				&& bytes[5] == 0x0a
				&& bytes[6] == 0x1a
				&& bytes[7] == 0x0a) {
			return "image/png";
		}
		if (bytes.length >= 3
				&& (bytes[0] & 0xff) == 0xff
				&& (bytes[1] & 0xff) == 0xd8
				&& (bytes[2] & 0xff) == 0xff) {
			return "image/jpeg";
		}
		if (bytes.length >= 6
				&& bytes[0] == 0x47
				&& bytes[1] == 0x49
				&& bytes[2] == 0x46
				&& bytes[3] == 0x38
				&& (bytes[4] == 0x37 || bytes[4] == 0x39)
				&& bytes[5] == 0x61) {
			return "image/gif";
		}
		if (bytes[0] == 0x42 && bytes[1] == 0x4d) {
			return "image/bmp";
		}
		return null;
	}
}
