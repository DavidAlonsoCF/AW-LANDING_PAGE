package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.DestinoDTO;
import com.upc.authjwt20251.Entities.Destino;
import com.upc.authjwt20251.Service.DestinoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/destino")
@CrossOrigin
public class DestinoController {
    private final DestinoService destinoService;

    public DestinoController(DestinoService destinoService) {
        this.destinoService = destinoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TURISTA', 'LOCAL', 'ADMINISTRADOR')")
    public ResponseEntity<List<Destino>> findAll() {
        return new ResponseEntity<>(destinoService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR')")
    public ResponseEntity<Destino> create(@Valid @RequestBody DestinoDTO destinoDTO) {
        return new ResponseEntity<>(destinoService.create(destinoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR')")
    public ResponseEntity<Destino> update(@PathVariable Long id, @Valid @RequestBody DestinoDTO destinoDTO) {
        return new ResponseEntity<>(destinoService.update(id, destinoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        destinoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Destino findById(@PathVariable Long id) {
        return destinoService.findById(id);
    }

}