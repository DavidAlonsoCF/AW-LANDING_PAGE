package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.PuntosFidelidadDTO;
import com.upc.authjwt20251.Entities.PuntosFidelidad;
import com.upc.authjwt20251.Repository.PuntosFidelidadRepository;
import com.upc.authjwt20251.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuntosFidelidadService {

    private final PuntosFidelidadRepository puntosFidelidadRepository;
    private final UsuarioRepository usuarioRepository;

    public PuntosFidelidadService(PuntosFidelidadRepository puntosFidelidadRepository, UsuarioRepository usuarioRepository) {
        this.puntosFidelidadRepository = puntosFidelidadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private PuntosFidelidadDTO toDTO(PuntosFidelidad p) {
        if (p == null) return null;
        PuntosFidelidadDTO dto = new PuntosFidelidadDTO();
        dto.setId(p.getId());
        dto.setPuntosAcumulados(p.getPuntosAcumulados());

        dto.setUsuarioId(p.getUsuario() != null ? p.getUsuario().getId() : null);

        return dto;
    }

    private PuntosFidelidad toEntity(PuntosFidelidadDTO dto) {
        PuntosFidelidad p = new PuntosFidelidad();
        p.setPuntosAcumulados(dto.getPuntosAcumulados());

        p.setUsuario(usuarioRepository.findById(dto.getUsuarioId()).orElse(null));

        return p;
    }

    public List<PuntosFidelidadDTO> findAllDTO() {
        return puntosFidelidadRepository.findAll().stream().map(this::toDTO).toList();
    }

    public PuntosFidelidadDTO create(PuntosFidelidadDTO dto) {
        PuntosFidelidad pToSave = toEntity(dto);
        PuntosFidelidad saved = puntosFidelidadRepository.save(pToSave);
        return toDTO(saved);
    }

    public PuntosFidelidadDTO update(Long id, PuntosFidelidadDTO dto) {
        PuntosFidelidad p = puntosFidelidadRepository.findById(id).orElse(null);

        if (p != null) {
            p.setPuntosAcumulados(dto.getPuntosAcumulados());

            p.setUsuario(usuarioRepository.findById(dto.getUsuarioId()).orElse(null));

            PuntosFidelidad updated = puntosFidelidadRepository.save(p);
            return toDTO(updated);
        }
        return null;
    }

    public boolean delete(Long id) {
        PuntosFidelidad p = puntosFidelidadRepository.findById(id).orElse(null);

        if (p == null) {
            return false;
        }

        puntosFidelidadRepository.delete(p);
        return true;
    }
}