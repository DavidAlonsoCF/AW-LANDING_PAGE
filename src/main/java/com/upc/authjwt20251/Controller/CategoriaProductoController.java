package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.CategoriaProductoDTO;
import com.upc.authjwt20251.Service.CategoriaProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categoriaProducto")
@CrossOrigin
public class CategoriaProductoController {

    private final CategoriaProductoService service;

    public CategoriaProductoController(CategoriaProductoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROVEEDOR', 'LOCAL')")
    public ResponseEntity<List<CategoriaProductoDTO>> findAll() {
        return ResponseEntity.ok(service.findAllDTO());
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROVEEDOR')")
    public ResponseEntity<CategoriaProductoDTO> create(@Valid @RequestBody CategoriaProductoDTO dto) {
        return new ResponseEntity<>(service.createDTO(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROVEEDOR')")
    public ResponseEntity<CategoriaProductoDTO> update(@PathVariable Long id, @Valid @RequestBody CategoriaProductoDTO dto) {
        return ResponseEntity.ok(service.updateDTO(id, dto));
    }
}