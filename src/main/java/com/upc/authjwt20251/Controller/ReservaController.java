package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.ReservaDTO;
import com.upc.authjwt20251.Service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reserva")
@CrossOrigin
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TURISTA','ADMINISTRADOR')")
    public ResponseEntity<List<ReservaDTO>> findAll() {
        return new ResponseEntity<>(reservaService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('TURISTA','ADMINISTRADOR')")
    public ResponseEntity<ReservaDTO> create(@Valid @RequestBody ReservaDTO dto) {
        return new ResponseEntity<>(reservaService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('TURISTA','ADMINISTRADOR')")
    public ResponseEntity<ReservaDTO> update(@PathVariable Long id, @Valid @RequestBody ReservaDTO dto) {
        ReservaDTO updated = reservaService.update(id, dto);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/por-alojamiento/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','TURISTA')")
    public ResponseEntity<List<ReservaDTO>> getByAlojamiento(@PathVariable("id") Long id) {
        return new ResponseEntity<>(reservaService.findByAlojamiento(id), HttpStatus.OK);
    }

    @GetMapping("/paquetes-mas-reservas")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<List<Map<String, Object>>> getPaquetesConMasReservas(@RequestParam Long minimo) {
        return new ResponseEntity<>(reservaService.findPaquetesConMasDeNReservas(minimo), HttpStatus.OK);
    }

    @GetMapping("/entre-anios")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReservaDTO>> getReservasEntreAnios(@RequestParam int inicio, @RequestParam int fin) {
        return new ResponseEntity<>(reservaService.findReservasEntreAnios(inicio, fin), HttpStatus.OK);
    }

    @GetMapping("/por-transporte")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<List<Object[]>> getReservasPorTransporte() {
        return new ResponseEntity<>(reservaService.countReservasPorTransporte(), HttpStatus.OK);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasAnyRole('TURISTA','ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = reservaService.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}