package com.sivebo.ms_sucursales.utils;

import java.util.Map;

public final class ApiErrorUtil {

	private ApiErrorUtil() {
	}

	public static Map<String, String> of(String message) {
		return Map.of("error", message);
	}

	public static Map<String, Object> of(Map<String, String> fieldErrors) {
		return Map.of("error", fieldErrors);
	}
}
