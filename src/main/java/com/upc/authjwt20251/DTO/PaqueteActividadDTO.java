package com.upc.authjwt20251.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaqueteActividadDTO {
    private Long id;

    @NotBlank(message = "El nombre del paquete de actividad es obligatorio.")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
    private String nombre;

    @NotNull(message = "El precio es obligatorio.")
    @Positive(message = "El precio debe ser un valor positivo.")
    private Double precio;

    @NotNull(message = "El ID del paquete turístico es obligatorio.")
    @Min(value = 1, message = "El ID del paquete turístico debe ser un valor positivo.")
    private Long paqueteTuristicoId;
}