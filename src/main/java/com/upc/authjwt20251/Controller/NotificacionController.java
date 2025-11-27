package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.NotificacionDTO;
import com.upc.authjwt20251.Service.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notificacion")
@CrossOrigin
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROVEEDOR')")
    public ResponseEntity<List<NotificacionDTO>> findAll() {
        return new ResponseEntity<>(notificacionService.findAllDTO(), HttpStatus.OK);
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROVEEDOR')")
    public ResponseEntity<NotificacionDTO> create(@Valid @RequestBody NotificacionDTO dto) {
        return new ResponseEntity<>(notificacionService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROVEEDOR')")
    public ResponseEntity<NotificacionDTO> update(@PathVariable Long id, @Valid @RequestBody NotificacionDTO dto) {
        NotificacionDTO updated = notificacionService.update(id, dto);
        if(updated == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = notificacionService.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}