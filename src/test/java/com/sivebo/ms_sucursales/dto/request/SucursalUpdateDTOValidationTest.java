package com.sivebo.ms_sucursales.dto.request;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class SucursalUpdateDTOValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void soloNombreEstablecidoNoTieneViolaciones() {
        SucursalUpdateDTO dto = new SucursalUpdateDTO("Plaza de Armas", null, null, null);

        Set<ConstraintViolation<SucursalUpdateDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void nombreNuloProduceViolacion() {
        SucursalUpdateDTO dto = new SucursalUpdateDTO(null, "Santiago", null, null);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void nombreEnBlancoProduceViolacion() {
        SucursalUpdateDTO dto = new SucursalUpdateDTO("   ", null, null, null);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void nombreComunaVacioExplicitoProduceViolacion() {
        SucursalUpdateDTO dto = new SucursalUpdateDTO("Plaza de Armas", "", null, null);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void direccionFisicaVaciaExplicitaProduceViolacion() {
        SucursalUpdateDTO dto = new SucursalUpdateDTO("Plaza de Armas", null, "", null);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void telefonoContactoVacioExplicitoProduceViolacion() {
        SucursalUpdateDTO dto = new SucursalUpdateDTO("Plaza de Armas", null, null, "");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void actualizacionParcialValidaNoTieneViolaciones() {
        SucursalUpdateDTO dto = new SucursalUpdateDTO("Plaza de Armas", null, "Catedral 1000", null);

        assertTrue(validator.validate(dto).isEmpty());
    }
}
