package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.ActividadDTO;
import com.upc.authjwt20251.Entities.Actividad;
import com.upc.authjwt20251.Repository.ActividadRepository;
import com.upc.authjwt20251.Repository.PaqueteActividadRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActividadService {

    private final ActividadRepository actividadRepository;
    private final PaqueteActividadRepository paqueteActividadRepository;

    public ActividadService(ActividadRepository actividadRepository,
                            PaqueteActividadRepository paqueteActividadRepository) {
        this.actividadRepository = actividadRepository;
        this.paqueteActividadRepository = paqueteActividadRepository;
    }

    public List<ActividadDTO> findAll() {
        List<Actividad> actividades = actividadRepository.findAll();
        List<ActividadDTO> result = new ArrayList<>();
        for (Actividad a : actividades) {
            ActividadDTO dto = new ActividadDTO();
            dto.setId(a.getId());
            dto.setNombre(a.getNombre());
            dto.setDescripcion(a.getDescripcion());
            dto.setPrecio(a.getPrecio());
            dto.setPaqueteActividadId(a.getPaqueteActividad().getId());
            result.add(dto);
        }
        return result;
    }

    public ActividadDTO create(ActividadDTO dto) {
        Actividad a = new Actividad();
        a.setNombre(dto.getNombre());
        a.setDescripcion(dto.getDescripcion());
        a.setPrecio(dto.getPrecio());
        a.setPaqueteActividad(paqueteActividadRepository.findById(dto.getPaqueteActividadId())
                .orElseThrow(() -> new RuntimeException("PaqueteActividad no encontrado")));
        actividadRepository.save(a);
        dto.setId(a.getId());
        return dto;
    }

    public ActividadDTO update(Long id, ActividadDTO dto) {
        Actividad a = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
        a.setNombre(dto.getNombre());
        a.setDescripcion(dto.getDescripcion());
        a.setPrecio(dto.getPrecio());
        a.setPaqueteActividad(paqueteActividadRepository.findById(dto.getPaqueteActividadId())
                .orElseThrow(() -> new RuntimeException("PaqueteActividad no encontrado")));
        actividadRepository.save(a);
        dto.setId(a.getId());
        return dto;
    }

    public void delete(Long id) {
        Actividad a = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
        actividadRepository.delete(a);
    }

    public ActividadDTO findByIdDTO(Long id) {
        Actividad act = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        ActividadDTO dto = new ActividadDTO();
        dto.setId(act.getId());
        dto.setNombre(act.getNombre());
        dto.setDescripcion(act.getDescripcion());
        dto.setPrecio(act.getPrecio());
        dto.setPaqueteActividadId(act.getPaqueteActividad().getId());
        return dto;
    }

}