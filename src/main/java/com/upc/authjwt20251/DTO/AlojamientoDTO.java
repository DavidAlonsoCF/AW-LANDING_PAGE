package com.upc.authjwt20251.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlojamientoDTO {
    private Long id;

    @NotBlank(message = "El nombre del alojamiento es obligatorio y no puede estar vacío.")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
    private String nombre;

    @NotNull(message = "El precio por noche es obligatorio.")
    @DecimalMin(value = "0.01", message = "El precio por noche debe ser mayor que cero (0.01).")
    private Double precio;
}
