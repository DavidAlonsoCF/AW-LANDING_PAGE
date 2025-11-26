package com.upc.authjwt20251.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransporteDTO {
    private Long id;

    @NotBlank(message = "El tipo de transporte es obligatorio.")
    @Size(max = 50, message = "El tipo de transporte no puede exceder los 50 caracteres.")
    private String tipo;

    @NotBlank(message = "El nombre de la empresa es obligatorio.")
    @Size(max = 100, message = "La empresa no puede exceder los 100 caracteres.")
    private String empresa;

    @NotNull(message = "El precio es obligatorio.")
    @Positive(message = "El precio debe ser un valor positivo.")
    private Double precio;

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

}
