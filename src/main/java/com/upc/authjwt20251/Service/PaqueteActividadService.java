package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.PaqueteActividadDTO;
import com.upc.authjwt20251.Entities.PaqueteActividad;
import com.upc.authjwt20251.Repository.PaqueteActividadRepository;
import com.upc.authjwt20251.Repository.PaqueteTuristicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaqueteActividadService {

    private final PaqueteActividadRepository repository;
    private final PaqueteTuristicoRepository paqueteTuristicoRepository;

    public PaqueteActividadService(PaqueteActividadRepository repository,
                                   PaqueteTuristicoRepository paqueteTuristicoRepository) {
        this.repository = repository;
        this.paqueteTuristicoRepository = paqueteTuristicoRepository;
    }

    private PaqueteActividadDTO toDTO(PaqueteActividad p) {
        if (p == null) return null;

        PaqueteActividadDTO dto = new PaqueteActividadDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());

        dto.setPaqueteTuristicoId(
                p.getPaqueteTuristico() != null ? p.getPaqueteTuristico().getId() : null
        );

        return dto;
    }

    private PaqueteActividad toEntity(PaqueteActividadDTO dto) {
        PaqueteActividad p = new PaqueteActividad();
        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setPaqueteTuristico(paqueteTuristicoRepository.findById(dto.getPaqueteTuristicoId()).orElse(null));

        return p;
    }

    public List<PaqueteActividadDTO> findAllDTO() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PaqueteActividadDTO create(PaqueteActividadDTO dto) {
        PaqueteActividad saved = repository.save(toEntity(dto));
        return toDTO(saved);
    }

    public PaqueteActividadDTO update(Long id, PaqueteActividadDTO dto) {
        PaqueteActividad original = repository.findById(id).orElse(null);

        if (original == null) return null;

        original.setNombre(dto.getNombre());
        original.setPrecio(dto.getPrecio());
        original.setPaqueteTuristico(
                paqueteTuristicoRepository.findById(dto.getPaqueteTuristicoId()).orElse(null)
        );

        PaqueteActividad updated = repository.save(original);
        return toDTO(updated);
    }

    public boolean delete(Long id) {
        PaqueteActividad p = repository.findById(id).orElse(null);

        if (p == null) return false;

        repository.delete(p);
        return true;
    }

    public PaqueteActividadDTO findByIdDTO(Long id) {
        return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("No encontrado"));
    }
}