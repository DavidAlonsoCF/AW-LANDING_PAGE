package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.PromocionDTO;
import com.upc.authjwt20251.Service.PromocionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/promocion")
@CrossOrigin
public class PromocionController {
    private final PromocionService service;

    public PromocionController(PromocionService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TURISTA')")
    public ResponseEntity<List<PromocionDTO>> findAllDTO() {
        return ResponseEntity.ok(service.findAllDTO());
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PromocionDTO> create(@Valid @RequestBody PromocionDTO dto) {
        PromocionDTO creado = service.create(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PromocionDTO> update(@PathVariable Long id, @Valid @RequestBody PromocionDTO dto) {
        PromocionDTO actualizado = service.update(id, dto);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = service.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}