package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.HistorialPuntosDTO;
import com.upc.authjwt20251.Service.HistorialPuntosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/historialPuntos")
@CrossOrigin
public class HistorialPuntosController {

    private final HistorialPuntosService historialPuntosService;

    public HistorialPuntosController(HistorialPuntosService historialPuntosService) {
        this.historialPuntosService = historialPuntosService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<List<HistorialPuntosDTO>> findAll() {
        return new ResponseEntity<>(historialPuntosService.findAllDTO(), HttpStatus.OK);
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<HistorialPuntosDTO> create(@Valid @RequestBody HistorialPuntosDTO dto) {
        return new ResponseEntity<>(historialPuntosService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('TURISTA', 'ADMINISTRADOR')")
    public ResponseEntity<HistorialPuntosDTO> update(@PathVariable Long id, @Valid @RequestBody HistorialPuntosDTO dto) {
        HistorialPuntosDTO updated = historialPuntosService.update(id, dto);
        if(updated == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = historialPuntosService.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{idUsuario}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<HistorialPuntosDTO>> getPuntosPorUsuario(@PathVariable Long idUsuario) {
        return new ResponseEntity<>(historialPuntosService.findByUsuarioId(idUsuario), HttpStatus.OK);
    }
}