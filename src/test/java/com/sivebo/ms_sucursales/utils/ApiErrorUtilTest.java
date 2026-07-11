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

    @Test
    void ofConMapaDeErroresEnvuelveEnClaveError() {
        Map<String, String> fieldErrors = Map.of("nombre", "obligatorio");

        Map<String, Object> result = ApiErrorUtil.of(fieldErrors);

        assertEquals(1, result.size());
        assertEquals(fieldErrors, result.get("error"));
    }
}
