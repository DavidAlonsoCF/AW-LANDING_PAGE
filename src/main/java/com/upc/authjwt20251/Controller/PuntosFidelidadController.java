package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.PuntosFidelidadDTO;
import com.upc.authjwt20251.Service.PuntosFidelidadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/puntosFidelidad")
@CrossOrigin
public class PuntosFidelidadController {

    private final PuntosFidelidadService puntosFidelidadService;

    public PuntosFidelidadController(PuntosFidelidadService puntosFidelidadService) {
        this.puntosFidelidadService = puntosFidelidadService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<List<PuntosFidelidadDTO>> findAllDTO() {
        return new ResponseEntity<>(puntosFidelidadService.findAllDTO(), HttpStatus.OK);
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PuntosFidelidadDTO> create(@Valid @RequestBody PuntosFidelidadDTO dto) {
        PuntosFidelidadDTO saved = puntosFidelidadService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PuntosFidelidadDTO> update(@PathVariable Long id, @Valid @RequestBody PuntosFidelidadDTO dto) {
        PuntosFidelidadDTO updated = puntosFidelidadService.update(id, dto);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = puntosFidelidadService.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}