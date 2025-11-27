package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.AlojamientoDTO;
import com.upc.authjwt20251.Entities.Alojamiento;
import com.upc.authjwt20251.Service.AlojamientoService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/alojamiento")
@CrossOrigin
public class AlojamientoController {

    private final AlojamientoService alojamientoService;
    private final ModelMapper modelMapper = new ModelMapper();

    public AlojamientoController(AlojamientoService alojamientoService) {
        this.alojamientoService = alojamientoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TURISTA', 'LOCAL', 'ADMINISTRADOR')")
    public List<AlojamientoDTO> findAll() {
        return alojamientoService.findAll().stream()
                .map(a -> modelMapper.map(a, AlojamientoDTO.class))
                .toList();
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR')")
    public AlojamientoDTO create(@Valid @RequestBody AlojamientoDTO dto) {
        Alojamiento alojamiento = alojamientoService.create(dto);
        return modelMapper.map(alojamiento, AlojamientoDTO.class);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR')")
    public AlojamientoDTO update(@PathVariable Long id, @Valid @RequestBody AlojamientoDTO dto) {
        Alojamiento alojamiento = alojamientoService.update(id, dto);
        return modelMapper.map(alojamiento, AlojamientoDTO.class);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void delete(@PathVariable Long id) {
        alojamientoService.delete(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public AlojamientoDTO findById(@PathVariable Long id) {
        return modelMapper.map(alojamientoService.findById(id), AlojamientoDTO.class);
    }
}