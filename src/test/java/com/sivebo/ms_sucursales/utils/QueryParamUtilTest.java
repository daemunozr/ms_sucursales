package com.sivebo.ms_sucursales.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class QueryParamUtilTest {

    @Test
    void todosNulosRetornaCero() {
        assertEquals(0, QueryParamUtil.countNonNull((String) null));
        assertEquals(0, QueryParamUtil.countNonNull(null, null, null));
    }

    @Test
    void unSoloValorNoNuloRetornaUno() {
        assertEquals(1, QueryParamUtil.countNonNull("Santiago", null, null));
        assertEquals(1, QueryParamUtil.countNonNull(null, "Santiago"));
    }

    @Test
    void variosValoresNoNulosRetornaConteo() {
        assertEquals(2, QueryParamUtil.countNonNull("Santiago", "Metropolitana"));
        assertEquals(3, QueryParamUtil.countNonNull("a", "b", "c"));
    }

    @Test
    void sinArgumentosRetornaCero() {
        assertEquals(0, QueryParamUtil.countNonNull());
    }
}
