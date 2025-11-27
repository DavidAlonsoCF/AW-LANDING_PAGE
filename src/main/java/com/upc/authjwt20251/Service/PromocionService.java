package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.PromocionDTO;
import com.upc.authjwt20251.Entities.Promocion;
import com.upc.authjwt20251.Repository.PaqueteTuristicoRepository;
import com.upc.authjwt20251.Repository.ProductoLocalRepository;
import com.upc.authjwt20251.Repository.PromocionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromocionService {

    private final PromocionRepository promocionRepository;
    private final PaqueteTuristicoRepository paqueteTuristicoRepository;
    private final ProductoLocalRepository productoLocalRepository;

    public PromocionService(PromocionRepository promocionRepository,
                            PaqueteTuristicoRepository paqueteTuristicoRepository,
                            ProductoLocalRepository productoLocalRepository) {
        this.promocionRepository = promocionRepository;
        this.paqueteTuristicoRepository = paqueteTuristicoRepository;
        this.productoLocalRepository = productoLocalRepository;
    }

    private PromocionDTO toDTO(Promocion p) {
        if (p == null) return null;
        PromocionDTO dto = new PromocionDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setDescuento(p.getDescuento());

        dto.setPaqueteTuristicoId(p.getPaqueteTuristico() != null ? p.getPaqueteTuristico().getId() : null);
        dto.setProductoLocalId(p.getProductoLocal() != null ? p.getProductoLocal().getId() : null);

        return dto;
    }

    private Promocion toEntity(PromocionDTO dto) {
        Promocion p = new Promocion();
        p.setNombre(dto.getNombre());
        p.setDescuento(dto.getDescuento());

        p.setPaqueteTuristico(
                paqueteTuristicoRepository.findById(dto.getPaqueteTuristicoId()).orElse(null)
        );
        p.setProductoLocal(
                productoLocalRepository.findById(dto.getProductoLocalId()).orElse(null)
        );

        return p;
    }

    public List<PromocionDTO> findAllDTO() {
        return promocionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PromocionDTO create(PromocionDTO dto) {
        Promocion pToSave = toEntity(dto);
        Promocion saved = promocionRepository.save(pToSave);
        return toDTO(saved);
    }

    public PromocionDTO update(Long id, PromocionDTO dto) {
        Promocion p = promocionRepository.findById(id).orElse(null);

        if (p != null) {
            p.setNombre(dto.getNombre());
            p.setDescuento(dto.getDescuento());

            p.setPaqueteTuristico(
                    paqueteTuristicoRepository.findById(dto.getPaqueteTuristicoId()).orElse(null)
            );
            p.setProductoLocal(
                    productoLocalRepository.findById(dto.getProductoLocalId()).orElse(null)
            );

            Promocion updated = promocionRepository.save(p);
            return toDTO(updated);
        }
        return null;
    }

    public boolean delete(Long id) {
        Promocion p = promocionRepository.findById(id).orElse(null);

        if (p == null) {
            return false;
        }

        promocionRepository.delete(p);
        return true;
    }
}