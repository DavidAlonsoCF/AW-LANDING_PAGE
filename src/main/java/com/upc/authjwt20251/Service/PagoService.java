package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.PagoDTO;
import com.upc.authjwt20251.Entities.Pago;
import com.upc.authjwt20251.Entities.Reserva;
import com.upc.authjwt20251.Entities.Usuario;
import com.upc.authjwt20251.Repository.PagoRepository;
import com.upc.authjwt20251.Repository.ReservaRepository;
import com.upc.authjwt20251.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReservaRepository reservaRepository;

    public PagoService(PagoRepository pagoRepository,
                       UsuarioRepository usuarioRepository,
                       ReservaRepository reservaRepository) {
        this.pagoRepository = pagoRepository;
        this.usuarioRepository = usuarioRepository;
        this.reservaRepository = reservaRepository;
    }

    public List<PagoDTO> findAllDTO() {
        return pagoRepository.findAll().stream().map(pago ->
                new PagoDTO(
                        pago.getId(),
                        pago.getMonto(),
                        pago.getFecha(),
                        pago.getUsuario().getId(),
                        pago.getReserva().getId()
                )
        ).toList();
    }

    public PagoDTO create(PagoDTO dto) {
        Pago pago = new Pago();
        pago.setMonto(dto.getMonto());
        pago.setFecha(dto.getFecha());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        Reserva reserva = reservaRepository.findById(dto.getReservaId()).orElse(null);

        pago.setUsuario(usuario);
        pago.setReserva(reserva);

        Pago saved = pagoRepository.save(pago);
        return new PagoDTO(
                saved.getId(),
                saved.getMonto(),
                saved.getFecha(),
                saved.getUsuario().getId(),
                saved.getReserva().getId()
        );
    }

    public PagoDTO update(Long id, PagoDTO dto) {
        Pago pago = pagoRepository.findById(id).orElse(null);
        if (pago != null) {
            pago.setMonto(dto.getMonto());
            pago.setFecha(dto.getFecha());

            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
            Reserva reserva = reservaRepository.findById(dto.getReservaId()).orElse(null);

            pago.setUsuario(usuario);
            pago.setReserva(reserva);

            Pago saved = pagoRepository.save(pago);
            return new PagoDTO(
                    saved.getId(),
                    saved.getMonto(),
                    saved.getFecha(),
                    saved.getUsuario().getId(),
                    saved.getReserva().getId()
            );
        }
        return null;
    }

    public List<PagoDTO> findPagosEntreAnios(int anioInicio, int anioFin) {
        return pagoRepository.findPagosEntreAnios(anioInicio, anioFin)
                .stream().map(this::toDTO).toList();
    }

    private PagoDTO toDTO(Pago pago) {
        return new PagoDTO(
                pago.getId(),
                pago.getMonto(),
                pago.getFecha(),
                pago.getUsuario().getId(),
                pago.getReserva().getId()
        );
    }

    public boolean delete(Long id) {
        Pago pago = pagoRepository.findById(id).orElse(null);

        if (pago == null) {
            return false;
        }

        pagoRepository.delete(pago);
        return true;
    }
}