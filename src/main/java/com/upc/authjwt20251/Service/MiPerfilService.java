package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.MiPerfilDTO;
import com.upc.authjwt20251.DTO.CambiarPasswordDTO;
import com.upc.authjwt20251.Entities.Usuario;
import com.upc.authjwt20251.Repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MiPerfilService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public MiPerfilService(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Usuario getUsuarioLogueado() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Usuario u = usuarioRepository.findByUsername(username);
        if (u == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return u;
    }

    public MiPerfilDTO getMiPerfil() {
        Usuario u = getUsuarioLogueado();
        MiPerfilDTO dto = new MiPerfilDTO();
        dto.setUsername(u.getUsername());
        return dto;
    }

    public MiPerfilDTO actualizarMiPerfil(MiPerfilDTO dto) {
        Usuario u = getUsuarioLogueado();
        u.setUsername(dto.getUsername());
        usuarioRepository.save(u);

        MiPerfilDTO resp = new MiPerfilDTO();
        resp.setUsername(u.getUsername());
        return resp;
    }

    public void cambiarPassword(CambiarPasswordDTO dto) {
        Usuario u = getUsuarioLogueado();

        if (!passwordEncoder.matches(dto.getPasswordActual(), u.getPassword())) {
            throw new RuntimeException("La contraseña actual no es correcta");
        }

        u.setPassword(passwordEncoder.encode(dto.getPasswordNueva()));
        usuarioRepository.save(u);
    }
}