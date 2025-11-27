package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.PaqueteActividadDTO;
import com.upc.authjwt20251.Service.PaqueteActividadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/paqueteActividad")
@CrossOrigin
public class PaqueteActividadController {

    private final PaqueteActividadService service;

    public PaqueteActividadController(PaqueteActividadService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR')")
    public ResponseEntity<List<PaqueteActividadDTO>> findAll() {
        return new ResponseEntity<>(service.findAllDTO(), HttpStatus.OK);
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR')")
    public ResponseEntity<PaqueteActividadDTO> create(@Valid @RequestBody PaqueteActividadDTO dto) {
        PaqueteActividadDTO saved = service.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR')")
    public ResponseEntity<PaqueteActividadDTO> update(@PathVariable Long id, @Valid @RequestBody PaqueteActividadDTO dto) {
        PaqueteActividadDTO updated = service.update(id, dto);

        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = service.delete(id);

        if (!eliminado) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public PaqueteActividadDTO findById(@PathVariable Long id) {
        return service.findByIdDTO(id);
    }
}