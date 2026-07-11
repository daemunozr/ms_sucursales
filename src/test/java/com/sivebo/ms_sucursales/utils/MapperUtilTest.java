package com.sivebo.ms_sucursales.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.sivebo.ms_sucursales.dto.response.ComunaResponseDTO;
import com.sivebo.ms_sucursales.dto.response.SucursalResponseDTO;
import com.sivebo.ms_sucursales.model.Comuna;
import com.sivebo.ms_sucursales.model.EstadoSucursal;
import com.sivebo.ms_sucursales.model.Region;
import com.sivebo.ms_sucursales.model.Sucursal;

class MapperUtilTest {

    private final MapperUtil mapperUtil = new MapperUtil();

    @Test
    void mapSucursalToDTOConGrafoCompletoMapeaCorrectamente() {
        Region region = new Region(1L, "Metropolitana");
        Comuna comuna = new Comuna(1L, "Santiago", region);
        Sucursal sucursal = new Sucursal(1L, "Plaza de Armas", comuna, "Catedral 989", "+56912345678",
                        EstadoSucursal.ACTIVA);

        SucursalResponseDTO dto = mapperUtil.mapSucursalToDTO(sucursal);

        assertEquals("Plaza de Armas", dto.getNombre());
        assertEquals("Santiago", dto.getNombreComuna());
        assertEquals("Metropolitana", dto.getNombreRegion());
    }

    @Test
    void mapSucursalToDTOConComunaNulaLanzaIllegalStateException() {
        Sucursal sucursal = new Sucursal(1L, "Plaza de Armas", null, "Catedral 989", "+56912345678",
                        EstadoSucursal.ACTIVA);

        assertThrows(IllegalStateException.class, () -> mapperUtil.mapSucursalToDTO(sucursal));
    }

    @Test
    void mapSucursalToDTOConRegionNulaLanzaIllegalStateException() {
        Comuna comuna = new Comuna(1L, "Santiago", null);
        Sucursal sucursal = new Sucursal(1L, "Plaza de Armas", comuna, "Catedral 989", "+56912345678",
                        EstadoSucursal.ACTIVA);

        assertThrows(IllegalStateException.class, () -> mapperUtil.mapSucursalToDTO(sucursal));
    }

    @Test
    void mapComunaToDTOConRegionAsociadaMapeaCorrectamente() {
        Region region = new Region(1L, "Metropolitana");
        Comuna comuna = new Comuna(1L, "Santiago", region);

        ComunaResponseDTO dto = mapperUtil.mapComunaToDTO(comuna);

        assertEquals("Santiago", dto.getNombre());
        assertEquals("Metropolitana", dto.getNombreRegion());
    }

    @Test
    void mapComunaToDTOConRegionNulaLanzaIllegalStateException() {
        Comuna comuna = new Comuna(1L, "Santiago", null);

        assertThrows(IllegalStateException.class, () -> mapperUtil.mapComunaToDTO(comuna));
    }
}
