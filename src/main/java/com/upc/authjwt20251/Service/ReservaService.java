package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.ReservaDTO;
import com.upc.authjwt20251.Entities.Reserva;
import com.upc.authjwt20251.Repository.AlojamientoRepository;
import com.upc.authjwt20251.Repository.PaqueteTuristicoRepository;
import com.upc.authjwt20251.Repository.ReservaRepository;
import com.upc.authjwt20251.Repository.TransporteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final AlojamientoRepository alojamientoRepository;
    private final PaqueteTuristicoRepository paqueteRepository;
    private final TransporteRepository transporteRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          AlojamientoRepository alojamientoRepository,
                          PaqueteTuristicoRepository paqueteRepository,
                          TransporteRepository transporteRepository) {
        this.reservaRepository = reservaRepository;
        this.alojamientoRepository = alojamientoRepository;
        this.paqueteRepository = paqueteRepository;
        this.transporteRepository = transporteRepository;
    }

    public List<ReservaDTO> findAll() {
        List<Reserva> reservas = reservaRepository.findAll();
        return reservas.stream().map(this::toDTO).toList();
    }

    public ReservaDTO create(ReservaDTO dto) {
        Reserva r = new Reserva();
        r.setFecha(dto.getFecha());
        r.setCantidadPersonas(dto.getCantidadPersonas());
        r.setAlojamiento(alojamientoRepository.findById(dto.getAlojamientoId())
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado")));
        r.setPaqueteTuristico(paqueteRepository.findById(dto.getPaqueteTuristicoId())
                .orElseThrow(() -> new RuntimeException("Paquete turístico no encontrado")));

        if (dto.getTransporteId() != null) {
            r.setTransporte(transporteRepository.findById(dto.getTransporteId())
                    .orElseThrow(() -> new RuntimeException("Transporte no encontrado")));
        }

        reservaRepository.save(r);
        dto.setId(r.getId());
        return dto;
    }

    public ReservaDTO update(Long id, ReservaDTO dto) {
        Reserva r = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        r.setFecha(dto.getFecha());
        r.setCantidadPersonas(dto.getCantidadPersonas());

        r.setAlojamiento(alojamientoRepository.findById(dto.getAlojamientoId())
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado")));
        r.setPaqueteTuristico(paqueteRepository.findById(dto.getPaqueteTuristicoId())
                .orElseThrow(() -> new RuntimeException("Paquete turístico no encontrado")));
        if (dto.getTransporteId() != null) {
            r.setTransporte(transporteRepository.findById(dto.getTransporteId())
                    .orElseThrow(() -> new RuntimeException("Transporte no encontrado")));
        } else {
            r.setTransporte(null);
        }

        reservaRepository.save(r);
        dto.setId(r.getId());
        return dto;
    }

    public List<ReservaDTO> findByAlojamiento(Long alojamientoId) {
        List<Reserva> reservas = reservaRepository.findByAlojamiento(alojamientoId);
        return reservas.stream().map(this::toDTO).toList();
    }

    public List<Map<String, Object>> findPaquetesConMasDeNReservas(Long minimo) {
        List<Object[]> results = reservaRepository.findPaquetesConMasDeNReservas(minimo);
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] obj : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("paquete", obj[0]);
            map.put("totalReservas", obj[1]);
            response.add(map);
        }
        return response;
    }

    public List<ReservaDTO> findReservasEntreAnios(int anioInicio, int anioFin) {
        List<Reserva> reservas = reservaRepository.findReservasEntreAnios(anioInicio, anioFin);
        return reservas.stream().map(this::toDTO).toList();
    }

    public List<Object[]> countReservasPorTransporte() {
        return reservaRepository.countReservasPorTransporte();
    }

    private ReservaDTO toDTO(Reserva r) {
        return new ReservaDTO(
                r.getId(),
                r.getAlojamiento().getId(),
                r.getPaqueteTuristico().getId(),
                r.getTransporte() != null ? r.getTransporte().getId() : null,
                r.getFecha(),
                r.getCantidadPersonas()
        );
    }

    public boolean delete(Long id) {
        Reserva r = reservaRepository.findById(id).orElse(null);

        if (r == null) {
            return false;
        }

        reservaRepository.delete(r);
        return true;
    }
}