package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.DetalleCarritoDTO;
import com.upc.authjwt20251.Service.DetalleCarritoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/detalleCarrito")
@CrossOrigin
public class DetalleCarritoController {

    private final DetalleCarritoService detalleCarritoService;

    public DetalleCarritoController(DetalleCarritoService detalleCarritoService) {
        this.detalleCarritoService = detalleCarritoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<List<DetalleCarritoDTO>> findAll() {
        return new ResponseEntity<>(detalleCarritoService.findAllDTO(), HttpStatus.OK);
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<DetalleCarritoDTO> create(@Valid @RequestBody DetalleCarritoDTO dto) {
        return new ResponseEntity<>(detalleCarritoService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<DetalleCarritoDTO> update(@PathVariable Long id, @Valid @RequestBody DetalleCarritoDTO dto) {
        DetalleCarritoDTO updated = detalleCarritoService.update(id, dto);
        if(updated == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = detalleCarritoService.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build(); // 404
        }

        return ResponseEntity.noContent().build(); // 204
    }
}