package com.sivebo.ms_sucursales.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalCreateDTO {

	@NotBlank(message = "El nombre de la sucursal es obligatorio")
	@Size(max = 30, message = "El nombre de la sucursal no puede superar los 30 caracteres")
	String nombre;

	@NotBlank(message = "El nombre de la comuna es obligatorio")
	String nombreComuna;

	@NotBlank(message = "La dirección física es obligatoria")
	@Size(max = 100, message = "La dirección física no puede superar los 100 caracteres")
	String direccionFisica;

	@Size(max = 255, message = "El teléfono de contacto no puede superar los 255 caracteres")
	String telefonoContacto;
}
