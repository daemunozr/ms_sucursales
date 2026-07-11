package com.sivebo.ms_sucursales.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

class ApiErrorUtilTest {

    @Test
    void ofEnvuelveMensajeEnMapaConClaveError() {
        Map<String, String> error = ApiErrorUtil.of("Sucursal no encontrada");

        assertEquals(1, error.size());
        assertEquals("Sucursal no encontrada", error.get("error"));
    }
}
