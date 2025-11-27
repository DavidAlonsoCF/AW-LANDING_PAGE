package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.PaqueteTuristicoDTO;
import com.upc.authjwt20251.Service.PaqueteTuristicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/paqueteTuristico")
@CrossOrigin
public class PaqueteTuristicoController {

    private final PaqueteTuristicoService service;

    public PaqueteTuristicoController(PaqueteTuristicoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TURISTA','LOCAL', 'ADMINISTRADOR')")
    public ResponseEntity<List<PaqueteTuristicoDTO>> findAll() {
        return new ResponseEntity<>(service.findAllDTO(), HttpStatus.OK);
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR')")
    public ResponseEntity<PaqueteTuristicoDTO> create(@Valid @RequestBody PaqueteTuristicoDTO dto) {
        PaqueteTuristicoDTO saved = service.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR')")
    public ResponseEntity<PaqueteTuristicoDTO> update(@PathVariable Long id, @Valid @RequestBody PaqueteTuristicoDTO dto) {
        PaqueteTuristicoDTO updated = service.update(id, dto);

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

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public PaqueteTuristicoDTO findById(@PathVariable Long id) {
        return service.findByIdDTO(id);
    }
}