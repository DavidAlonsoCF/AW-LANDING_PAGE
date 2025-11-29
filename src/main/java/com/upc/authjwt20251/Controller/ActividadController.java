package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.ActividadDTO;
import com.upc.authjwt20251.Service.ActividadService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/actividad")
@CrossOrigin
public class ActividadController {

    private final ActividadService actividadService;

    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TURISTA', 'LOCAL', 'ADMINISTRADOR')")
    public List<ActividadDTO> findAll() {
        return actividadService.findAll();
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'LOCAL')")
    public ActividadDTO create(@Valid @RequestBody ActividadDTO dto) {
        return actividadService.create(dto);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'LOCAL')")
    public ActividadDTO update(@PathVariable Long id, @Valid @RequestBody ActividadDTO dto) {
        return actividadService.update(id, dto);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void delete(@PathVariable Long id) {
        actividadService.delete(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ActividadDTO findById(@PathVariable Long id) {
        return actividadService.findByIdDTO(id);
    }
}