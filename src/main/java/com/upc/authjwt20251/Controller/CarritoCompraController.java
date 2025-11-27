package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.CarritoCompraDTO;
import com.upc.authjwt20251.Service.CarritoCompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carrito")
@CrossOrigin
public class CarritoCompraController {

    private final CarritoCompraService carritoCompraService;

    public CarritoCompraController(CarritoCompraService carritoCompraService) {
        this.carritoCompraService = carritoCompraService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<List<CarritoCompraDTO>> findAll() {
        return ResponseEntity.ok(carritoCompraService.findAllDTO());
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<CarritoCompraDTO> create(@Valid @RequestBody CarritoCompraDTO dto) {
        CarritoCompraDTO saved = carritoCompraService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<CarritoCompraDTO> update(@PathVariable Long id, @Valid @RequestBody CarritoCompraDTO dto) {
        CarritoCompraDTO updated = carritoCompraService.update(id, dto);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = carritoCompraService.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}