package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.PuntosFidelidadDTO;
import com.upc.authjwt20251.Entities.PuntosFidelidad;
import com.upc.authjwt20251.Entities.Usuario;
import com.upc.authjwt20251.Repository.PuntosFidelidadRepository;
import com.upc.authjwt20251.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuntosFidelidadService {

    private final PuntosFidelidadRepository puntosFidelidadRepository;
    private final UsuarioRepository usuarioRepository;

    public PuntosFidelidadService(
            PuntosFidelidadRepository puntosFidelidadRepository,
            UsuarioRepository usuarioRepository) {
        this.puntosFidelidadRepository = puntosFidelidadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private PuntosFidelidadDTO toDTO(PuntosFidelidad p) {
        PuntosFidelidadDTO dto = new PuntosFidelidadDTO();
        dto.setId(p.getId());
        dto.setPuntosAcumulados(p.getPuntosAcumulados());
        dto.setUsuarioId(p.getUsuario().getId());
        return dto;
    }

    private PuntosFidelidad toEntity(PuntosFidelidadDTO dto) {
        PuntosFidelidad entity = new PuntosFidelidad();

        Usuario u = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        entity.setUsuario(u);
        entity.setPuntosAcumulados(dto.getPuntosAcumulados());
        return entity;
    }

    public List<PuntosFidelidadDTO> findAllDTO() {
        return puntosFidelidadRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public PuntosFidelidadDTO create(PuntosFidelidadDTO dto) {
        PuntosFidelidad saved = puntosFidelidadRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    public PuntosFidelidadDTO update(Long id, PuntosFidelidadDTO dto) {
        PuntosFidelidad entity = puntosFidelidadRepository.findById(id).orElse(null);
        if (entity == null) return null;

        Usuario u = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        entity.setUsuario(u);
        entity.setPuntosAcumulados(dto.getPuntosAcumulados());

        return toDTO(puntosFidelidadRepository.save(entity));
    }

    public boolean delete(Long id) {
        if (!puntosFidelidadRepository.existsById(id))
            return false;

        puntosFidelidadRepository.deleteById(id);
        return true;
    }

    public Integer obtenerPuntosTotales(Long usuarioId) {
        return puntosFidelidadRepository.sumarPuntosPorUsuario(usuarioId);
    }
}