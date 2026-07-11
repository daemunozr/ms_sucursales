package com.sivebo.ms_sucursales.dto.request;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class SucursalCreateDTOValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void dtoValidoNoTieneViolaciones() {
        SucursalCreateDTO dto = new SucursalCreateDTO("Plaza de Armas", "Santiago", "Catedral 989", "+56912345678");

        Set<ConstraintViolation<SucursalCreateDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void nombreNuloProduceViolacion() {
        SucursalCreateDTO dto = new SucursalCreateDTO(null, "Santiago", "Catedral 989", "+56912345678");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void nombreComunaEnBlancoProduceViolacion() {
        SucursalCreateDTO dto = new SucursalCreateDTO("Plaza de Armas", "  ", "Catedral 989", "+56912345678");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void direccionFisicaNulaProduceViolacion() {
        SucursalCreateDTO dto = new SucursalCreateDTO("Plaza de Armas", "Santiago", null, "+56912345678");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void nombreDemasiadoLargoProduceViolacion() {
        String nombreLargo = "a".repeat(31);
        SucursalCreateDTO dto = new SucursalCreateDTO(nombreLargo, "Santiago", "Catedral 989", "+56912345678");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void direccionFisicaDemasiadoLargaProduceViolacion() {
        String direccionLarga = "a".repeat(101);
        SucursalCreateDTO dto = new SucursalCreateDTO("Plaza de Armas", "Santiago", direccionLarga, "+56912345678");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void telefonoContactoNuloEsValido() {
        SucursalCreateDTO dto = new SucursalCreateDTO("Plaza de Armas", "Santiago", "Catedral 989", null);

        assertTrue(validator.validate(dto).isEmpty());
    }
}
