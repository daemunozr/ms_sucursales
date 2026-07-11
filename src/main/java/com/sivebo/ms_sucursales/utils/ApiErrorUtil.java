package com.sivebo.ms_sucursales.utils;

import java.util.Map;

public final class ApiErrorUtil {

	private ApiErrorUtil() {
	}

	public static Map<String, String> of(String message) {
		return Map.of("error", message);
	}
}
