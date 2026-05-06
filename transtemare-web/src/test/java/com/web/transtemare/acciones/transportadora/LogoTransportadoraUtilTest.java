package com.web.transtemare.acciones.transportadora;

import junit.framework.TestCase;

public class LogoTransportadoraUtilTest extends TestCase {

	public void testDetectsPng() {
		byte[] png = new byte[] {
				(byte) 0x89, 0x50, 0x4E, 0x47,
				0x0D, 0x0A, 0x1A, 0x0A
		};

		assertEquals("image/png", LogoTransportadoraUtil.detectContentType(png));
	}

	public void testDetectsJpeg() {
		byte[] jpeg = new byte[] {
				(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0
		};

		assertEquals("image/jpeg", LogoTransportadoraUtil.detectContentType(jpeg));
	}

	public void testDetectsGif() {
		assertEquals("image/gif", LogoTransportadoraUtil.detectContentType("GIF89a".getBytes()));
	}

	public void testRejectsSvgEvenWithImageContentTypeHeader() {
		byte[] svg = "<svg onload=\"alert(1)\"></svg>".getBytes();

		assertNull(LogoTransportadoraUtil.detectContentType(svg));
	}
}
