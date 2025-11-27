package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.PaqueteTuristicoDTO;
import com.upc.authjwt20251.Entities.PaqueteTuristico;
import com.upc.authjwt20251.Repository.PaqueteTuristicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaqueteTuristicoService {

    private final PaqueteTuristicoRepository repository;

    public PaqueteTuristicoService(PaqueteTuristicoRepository repository) {
        this.repository = repository;
    }

    private PaqueteTuristicoDTO toDTO(PaqueteTuristico entity) {
        if (entity == null) return null;

        PaqueteTuristicoDTO dto = new PaqueteTuristicoDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPrecio(entity.getPrecio());

        return dto;
    }

    private void updateEntityFromDTO(PaqueteTuristicoDTO dto, PaqueteTuristico entity) {
        entity.setNombre(dto.getNombre());
        entity.setPrecio(dto.getPrecio());
    }

    public List<PaqueteTuristicoDTO> findAllDTO() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PaqueteTuristicoDTO create(PaqueteTuristicoDTO dto) {
        PaqueteTuristico entity = new PaqueteTuristico();
        updateEntityFromDTO(dto, entity);
        PaqueteTuristico saved = repository.save(entity);
        return toDTO(saved);
    }

    public PaqueteTuristicoDTO update(Long id, PaqueteTuristicoDTO dto) {
        return repository.findById(id)
                .map(entity -> {
                    updateEntityFromDTO(dto, entity);
                    PaqueteTuristico updated = repository.save(entity);
                    return toDTO(updated);
                })
                .orElse(null);
    }

    public boolean delete(Long id) {
        PaqueteTuristico entity = repository.findById(id).orElse(null);

        if (entity == null) {
            return false;
        }

        repository.delete(entity);
        return true;
    }

    public PaqueteTuristicoDTO findByIdDTO(Long id) {
        return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("No encontrado"));
    }

}