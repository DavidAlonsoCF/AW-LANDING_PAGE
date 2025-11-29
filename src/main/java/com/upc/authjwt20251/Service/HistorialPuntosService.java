package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.HistorialPuntosDTO;
import com.upc.authjwt20251.Entities.HistorialPuntos;
import com.upc.authjwt20251.Entities.Usuario;
import com.upc.authjwt20251.Repository.HistorialPuntosRepository;
import com.upc.authjwt20251.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialPuntosService {

    private final HistorialPuntosRepository historialPuntosRepository;
    private final UsuarioRepository usuarioRepository;

    public HistorialPuntosService(HistorialPuntosRepository historialPuntosRepository,
                                  UsuarioRepository usuarioRepository) {
        this.historialPuntosRepository = historialPuntosRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<HistorialPuntosDTO> findAllDTO() {
        return historialPuntosRepository.findAll().stream().map(h ->
                new HistorialPuntosDTO(
                        h.getId(),
                        h.getUsuario().getId(),
                        h.getPuntos(),
                        h.getFecha()
                )
        ).toList();
    }

    public HistorialPuntosDTO create(HistorialPuntosDTO dto) {
        HistorialPuntos h = new HistorialPuntos();
        h.setPuntos(dto.getPuntos());
        h.setFecha(dto.getFecha());

        Usuario u = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        h.setUsuario(u);

        HistorialPuntos saved = historialPuntosRepository.save(h);
        return new HistorialPuntosDTO(
                saved.getId(),
                saved.getUsuario().getId(),
                saved.getPuntos(),
                saved.getFecha()
        );
    }

    public HistorialPuntosDTO update(Long id, HistorialPuntosDTO dto) {
        HistorialPuntos h = historialPuntosRepository.findById(id).orElse(null);
        if (h != null) {
            h.setPuntos(dto.getPuntos());
            h.setFecha(dto.getFecha());

            Usuario u = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
            h.setUsuario(u);

            HistorialPuntos saved = historialPuntosRepository.save(h);
            return new HistorialPuntosDTO(
                    saved.getId(),
                    saved.getUsuario().getId(),
                    saved.getPuntos(),
                    saved.getFecha()
            );
        }
        return null;
    }

    public boolean delete(Long id) {
        HistorialPuntos h = historialPuntosRepository.findById(id).orElse(null);

        if (h == null) {
            return false;
        }

        historialPuntosRepository.delete(h);
        return true;
    }

    public List<HistorialPuntosDTO> findByUsuarioId(Long idUsuario) {
        return historialPuntosRepository.findByUsuarioId(idUsuario)
                .stream()
                .map(h -> new HistorialPuntosDTO(
                        h.getId(),
                        h.getUsuario().getId(),
                        h.getPuntos(),
                        h.getFecha()
                ))
                .toList();
    }
}