package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.OpinionTuristaDTO;
import com.upc.authjwt20251.Service.OpinionTuristaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/opinionTurista")
@CrossOrigin
public class OpinionTuristaController {

    private final OpinionTuristaService opinionTuristaService;

    public OpinionTuristaController(OpinionTuristaService opinionTuristaService) {
        this.opinionTuristaService = opinionTuristaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TURISTA')")
    public ResponseEntity<List<OpinionTuristaDTO>> findAll() {
        return new ResponseEntity<>(opinionTuristaService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TURISTA')")
    public ResponseEntity<OpinionTuristaDTO> create(@Valid @RequestBody OpinionTuristaDTO dto) {
        OpinionTuristaDTO result = opinionTuristaService.create(dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TURISTA')")
    public ResponseEntity<OpinionTuristaDTO> update(@PathVariable Long id, @Valid @RequestBody OpinionTuristaDTO dto) {
        OpinionTuristaDTO updated = opinionTuristaService.update(id, dto);

        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = opinionTuristaService.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}