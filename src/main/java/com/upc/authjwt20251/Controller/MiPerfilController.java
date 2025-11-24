package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.MiPerfilDTO;
import com.upc.authjwt20251.DTO.CambiarPasswordDTO;
import com.upc.authjwt20251.Service.MiPerfilService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/miperfil")
@CrossOrigin
public class MiPerfilController {

    private final MiPerfilService miPerfilService;

    public MiPerfilController(MiPerfilService miPerfilService) {
        this.miPerfilService = miPerfilService;
    }

    @GetMapping
    public MiPerfilDTO getMiPerfil() {
        return miPerfilService.getMiPerfil();
    }

    @PutMapping
    public MiPerfilDTO actualizarMiPerfil(@Valid @RequestBody MiPerfilDTO dto) {
        return miPerfilService.actualizarMiPerfil(dto);
    }

    @PutMapping("/password")
    public void cambiarPassword(@Valid @RequestBody CambiarPasswordDTO dto) {
        miPerfilService.cambiarPassword(dto);
    }
}