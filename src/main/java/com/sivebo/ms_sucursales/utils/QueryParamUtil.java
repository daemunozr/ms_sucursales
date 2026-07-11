package com.sivebo.ms_sucursales.utils;

import java.util.Arrays;
import java.util.Objects;

public final class QueryParamUtil {

	private QueryParamUtil() {
	}

	public static int countNonNull(String... values) {
		return (int) Arrays.stream(values).filter(Objects::nonNull).count();
	}
}
