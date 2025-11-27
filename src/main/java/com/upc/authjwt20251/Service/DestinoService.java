package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.DestinoDTO;
import com.upc.authjwt20251.Entities.Destino;
import com.upc.authjwt20251.Repository.DestinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinoService {

    private final DestinoRepository destinoRepository;

    public DestinoService(DestinoRepository destinoRepository) {
        this.destinoRepository = destinoRepository;
    }

    public List<Destino> findAll() {
        return destinoRepository.findAll();
    }

    public Destino create(DestinoDTO destinoDTO) {
        Destino destino = new Destino();
        destino.setNombre(destinoDTO.getNombre());
        destino.setPais(destinoDTO.getPais());
        destino.setDescripcion(destinoDTO.getDescripcion());

        return destinoRepository.save(destino);
    }

    public Destino update(Long id, DestinoDTO destinoDTO) {
        Destino buscado = destinoRepository.findById(id).orElseThrow(() -> new RuntimeException("Destino no encontrado con ID: " + id));
        buscado.setNombre(destinoDTO.getNombre());
        buscado.setPais(destinoDTO.getPais());
        buscado.setDescripcion(destinoDTO.getDescripcion());

        return destinoRepository.save(buscado);
    }

    public void delete(Long id) {
        Destino buscado = destinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destino no encontrado con ID: " + id));

        destinoRepository.delete(buscado);
    }

    public Destino findById(Long id) {
        return destinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destino no encontrado"));
    }

}