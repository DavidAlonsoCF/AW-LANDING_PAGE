package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.UsuarioDTO;
import com.upc.authjwt20251.Entities.Usuario;
import com.upc.authjwt20251.Entities.Rol;
import com.upc.authjwt20251.Repository.UsuarioRepository;
import com.upc.authjwt20251.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario create(@RequestBody UsuarioDTO dto) {

        Usuario user = new Usuario();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + dto.getRolId()));

        user.setRol(rol);

        return usuarioRepository.save(user);
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable Long id, @RequestBody UsuarioDTO dto) {

        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setUsername(dto.getUsername());
        user.setEnabled(dto.getEnabled());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + dto.getRolId()));

        user.setRol(rol);

        return usuarioRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }
}
