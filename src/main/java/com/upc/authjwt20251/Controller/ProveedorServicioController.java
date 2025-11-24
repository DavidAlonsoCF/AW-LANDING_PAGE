package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.ProveedorServicioDTO;
import com.upc.authjwt20251.Service.ProveedorServicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/proveedorServicio")
@CrossOrigin
public class ProveedorServicioController {
    private final ProveedorServicioService service;

    public ProveedorServicioController(ProveedorServicioService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PROVEEDOR', 'ADMINISTRADOR', 'LOCAL')")
    public ResponseEntity<List<ProveedorServicioDTO>> findAllDTO() {
        return ResponseEntity.ok(service.findAllDTO());
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('PROVEEDOR', 'ADMINISTRADOR')")
    public ResponseEntity<ProveedorServicioDTO> create(@Valid @RequestBody ProveedorServicioDTO dto) {
        ProveedorServicioDTO creado = service.create(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('PROVEEDOR', 'ADMINISTRADOR')")
    public ResponseEntity<ProveedorServicioDTO> update(@PathVariable Long id, @Valid @RequestBody ProveedorServicioDTO dto) {
        ProveedorServicioDTO actualizado = service.update(id, dto);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }
}