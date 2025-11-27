package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.ProveedorServicioDTO;
import com.upc.authjwt20251.Entities.ProductoLocal;
import com.upc.authjwt20251.Entities.ProveedorServicio;
import com.upc.authjwt20251.Repository.ProveedorServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServicioService {

    private final ProveedorServicioRepository proveedorServicioRepository;

    public ProveedorServicioService(ProveedorServicioRepository proveedorServicioRepository) {
        this.proveedorServicioRepository = proveedorServicioRepository;
    }

    private ProveedorServicioDTO toDTO(ProveedorServicio p) {
        if (p == null) return null;
        ProveedorServicioDTO dto = new ProveedorServicioDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setContacto(p.getContacto());

        dto.setProductoLocalIds(p.getProductos() != null ? p.getProductos().stream().map(ProductoLocal::getId).toList() : List.of());
        return dto;
    }

    private ProveedorServicio toEntity(ProveedorServicioDTO dto) {
        ProveedorServicio p = new ProveedorServicio();
        p.setNombre(dto.getNombre());
        p.setContacto(dto.getContacto());
        return p;
    }

    public List<ProveedorServicioDTO> findAllDTO() {
        return proveedorServicioRepository.findAll().stream().map(this::toDTO).toList();
    }

    public ProveedorServicioDTO create(ProveedorServicioDTO dto) {
        ProveedorServicio pToSave = toEntity(dto);
        ProveedorServicio saved = proveedorServicioRepository.save(pToSave);
        return toDTO(saved);
    }

    public ProveedorServicioDTO update(Long id, ProveedorServicioDTO dto) {
        ProveedorServicio p = proveedorServicioRepository.findById(id).orElse(null);
        if (p == null) return null;

        p.setNombre(dto.getNombre());
        p.setContacto(dto.getContacto());

        ProveedorServicio updated = proveedorServicioRepository.save(p);
        return toDTO(updated);
    }

    public boolean delete(Long id) {
        ProveedorServicio p = proveedorServicioRepository.findById(id).orElse(null);

        if (p == null) {
            return false;
        }

        proveedorServicioRepository.delete(p);
        return true;
    }

    public ProveedorServicioDTO findByIdDTO(Long id) {
        ProveedorServicio proveedor = proveedorServicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        return toDTO(proveedor);
    }
}