package com.upc.authjwt20251.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CambiarPasswordDTO {

    @NotBlank
    @Size(max = 500)
    private String passwordActual;

    @NotBlank
    @Size(max = 500)
    private String passwordNueva;
}