package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.UsuarioDTO;
import com.upc.authjwt20251.Entities.Usuario;
import com.upc.authjwt20251.Service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Map<String, Object>> listAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Usuario u : usuarios) {
            Map<String, Object> resp = new LinkedHashMap<>();
            resp.put("id", u.getId());
            resp.put("username", u.getUsername());
            resp.put("password", u.getPassword());
            resp.put("enabled", u.getEnabled());
            resp.put("rolId", u.getRol().getId());
            response.add(resp);
        }

        return response;
    }

    @PostMapping
    public Map<String, Object> create(@RequestBody UsuarioDTO dto) {
        Usuario u = usuarioService.create(dto);

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("id", u.getId());
        resp.put("username", u.getUsername());
        resp.put("password", u.getPassword());
        resp.put("enabled", u.getEnabled());
        resp.put("rolId", u.getRol().getId());

        return resp;
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        Usuario u = usuarioService.update(id, dto);
        if (u == null) return null;

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("id", u.getId());
        resp.put("username", u.getUsername());
        resp.put("password", u.getPassword());
        resp.put("enabled", u.getEnabled());
        resp.put("rolId", u.getRol().getId());

        return resp;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuarioService.delete(id);
    }
}