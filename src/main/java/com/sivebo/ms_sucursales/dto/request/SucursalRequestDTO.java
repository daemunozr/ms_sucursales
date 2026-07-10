package com.sivebo.ms_sucursales.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequestDTO {

	@NotBlank(message = "El nombre de la sucursal es obligatorio")
	String nombre;

	String nombreComuna;

	String direccionFisica;

	String telefonoContacto;
}
