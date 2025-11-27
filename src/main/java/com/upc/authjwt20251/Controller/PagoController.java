package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.PagoDTO;
import com.upc.authjwt20251.Service.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pago")
@CrossOrigin
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TURISTA')")
    public ResponseEntity<List<PagoDTO>> findAll() {
        return new ResponseEntity<>(pagoService.findAllDTO(), HttpStatus.OK);
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TURISTA')")
    public ResponseEntity<PagoDTO> create(@Valid @RequestBody PagoDTO dto) {
        return new ResponseEntity<>(pagoService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TURISTA')")
    public ResponseEntity<PagoDTO> update(@PathVariable Long id, @Valid @RequestBody PagoDTO dto) {
        PagoDTO updated = pagoService.update(id, dto);
        if(updated == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/rango-anios")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TURISTA')")
    public ResponseEntity<List<PagoDTO>> getPagosEntreAnios(
            @RequestParam int anioInicio,
            @RequestParam int anioFin) {
        return new ResponseEntity<>(pagoService.findPagosEntreAnios(anioInicio, anioFin), HttpStatus.OK);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = pagoService.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}