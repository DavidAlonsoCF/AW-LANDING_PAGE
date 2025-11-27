package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.TransporteDTO;
import com.upc.authjwt20251.Entities.Reserva;
import com.upc.authjwt20251.Entities.Transporte;
import com.upc.authjwt20251.Entities.PaqueteTuristico;
import com.upc.authjwt20251.Repository.ReservaRepository;
import com.upc.authjwt20251.Repository.TransporteRepository;
import com.upc.authjwt20251.Repository.PaqueteTuristicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransporteService {

    @Autowired
    private TransporteRepository repo;

    public List<TransporteDTO> findAllDTO() {
        List<TransporteDTO> lista = new ArrayList<>();

        for (Transporte t : repo.findAll()) {
            TransporteDTO dto = new TransporteDTO();
            dto.setId(t.getId());
            dto.setTipo(t.getTipo());
            dto.setEmpresa(t.getEmpresa());
            dto.setPrecio(t.getPrecio());
            lista.add(dto);
        }
        return lista;
    }

    public TransporteDTO create(TransporteDTO dto) {
        Transporte t = new Transporte();
        t.setTipo(dto.getTipo());
        t.setEmpresa(dto.getEmpresa());
        t.setPrecio(dto.getPrecio());
        repo.save(t);

        dto.setId(t.getId());
        return dto;
    }

    public TransporteDTO update(Long id, TransporteDTO dto) {
        Transporte t = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe transporte"));

        t.setTipo(dto.getTipo());
        t.setEmpresa(dto.getEmpresa());
        t.setPrecio(dto.getPrecio());

        repo.save(t);
        dto.setId(t.getId());

        return dto;
    }

    public boolean delete(Long id) {
        Transporte t = repo.findById(id).orElse(null);

        if (t == null) {
            return false;
        }

        repo.delete(t);
        return true;
    }
}
