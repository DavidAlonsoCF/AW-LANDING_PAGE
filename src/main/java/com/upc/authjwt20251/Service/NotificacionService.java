package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.NotificacionDTO;
import com.upc.authjwt20251.Entities.Notificacion;
import com.upc.authjwt20251.Entities.Usuario;
import com.upc.authjwt20251.Repository.NotificacionRepository;
import com.upc.authjwt20251.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    public NotificacionService(NotificacionRepository notificacionRepository,
                               UsuarioRepository usuarioRepository) {
        this.notificacionRepository = notificacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<NotificacionDTO> findAllDTO() {
        return notificacionRepository.findAll().stream().map(n ->
                new NotificacionDTO(
                        n.getId(),
                        n.getMensaje(),
                        n.getUsuario().getId(),
                        n.getLeida()
                )
        ).toList();
    }

    public NotificacionDTO create(NotificacionDTO dto) {
        Notificacion n = new Notificacion();
        n.setMensaje(dto.getMensaje());
        n.setLeida(dto.getLeida());

        Usuario u = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        n.setUsuario(u);

        Notificacion saved = notificacionRepository.save(n);
        return new NotificacionDTO(
                saved.getId(),
                saved.getMensaje(),
                saved.getUsuario().getId(),
                saved.getLeida()
        );
    }

    public NotificacionDTO update(Long id, NotificacionDTO dto) {
        Notificacion n = notificacionRepository.findById(id).orElse(null);
        if (n != null) {
            n.setMensaje(dto.getMensaje());
            n.setLeida(dto.getLeida());

            Usuario u = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
            n.setUsuario(u);

            Notificacion saved = notificacionRepository.save(n);
            return new NotificacionDTO(
                    saved.getId(),
                    saved.getMensaje(),
                    saved.getUsuario().getId(),
                    saved.getLeida()
            );
        }
        return null;
    }

    public boolean delete(Long id) {
        Notificacion n = notificacionRepository.findById(id).orElse(null);

        if (n == null) {
            return false;
        }

        notificacionRepository.delete(n);
        return true;
    }
}