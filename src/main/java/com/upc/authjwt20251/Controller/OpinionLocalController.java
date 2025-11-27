package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.OpinionLocalDTO;
import com.upc.authjwt20251.Service.OpinionLocalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/opinionLocal")
@CrossOrigin
public class OpinionLocalController {

    private final OpinionLocalService opinionLocalService;

    public OpinionLocalController(OpinionLocalService opinionLocalService) {
        this.opinionLocalService = opinionLocalService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'LOCAL')")
    public ResponseEntity<List<OpinionLocalDTO>> findAll() {
        return new ResponseEntity<>(opinionLocalService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'LOCAL')")
    public ResponseEntity<OpinionLocalDTO> create(@Valid @RequestBody OpinionLocalDTO dto) {
        OpinionLocalDTO result = opinionLocalService.create(dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'LOCAL')")
    public ResponseEntity<OpinionLocalDTO> update(@PathVariable Long id, @Valid @RequestBody OpinionLocalDTO dto) {
        OpinionLocalDTO updated = opinionLocalService.update(id, dto);

        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = opinionLocalService.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public OpinionLocalDTO findById(@PathVariable Long id) {
        return opinionLocalService.findById(id);
    }

}