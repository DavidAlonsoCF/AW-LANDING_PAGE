package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.CategoriaProductoDTO;
import com.upc.authjwt20251.Entities.CategoriaProducto;
import com.upc.authjwt20251.Entities.ProductoLocal;
import com.upc.authjwt20251.Repository.CategoriaProductoRepository;
import com.upc.authjwt20251.Repository.ProductoLocalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaProductoService {

    private final CategoriaProductoRepository repo;
    private final ProductoLocalRepository productoLocalRepository;

    public CategoriaProductoService(CategoriaProductoRepository repo, ProductoLocalRepository productoLocalRepository) {
        this.repo = repo;
        this.productoLocalRepository = productoLocalRepository;
    }

    public List<CategoriaProductoDTO> findAllDTO() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CategoriaProductoDTO createDTO(CategoriaProductoDTO dto) {
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombre(dto.getNombre());
        if(dto.getProductoLocalIds() != null) {
            List<ProductoLocal> productos = dto.getProductoLocalIds().stream()
                    .map(id -> productoLocalRepository.findById(id).orElse(null))
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
            categoria.setProductos(productos);
        }
        CategoriaProducto saved = repo.save(categoria);
        return toDTO(saved);
    }

    public CategoriaProductoDTO updateDTO(Long id, CategoriaProductoDTO dto) {
        CategoriaProducto categoria = repo.findById(id).orElse(null);
        if(categoria == null) return null;

        categoria.setNombre(dto.getNombre());
        if(dto.getProductoLocalIds() != null) {
            List<ProductoLocal> productos = dto.getProductoLocalIds().stream()
                    .map(pid -> productoLocalRepository.findById(pid).orElse(null))
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
            categoria.setProductos(productos);
        }

        CategoriaProducto updated = repo.save(categoria);
        return toDTO(updated);
    }

    private CategoriaProductoDTO toDTO(CategoriaProducto categoria) {
        List<Long> productoIds = categoria.getProductos() != null
                ? categoria.getProductos().stream().map(ProductoLocal::getId).collect(Collectors.toList())
                : List.of();

        return new CategoriaProductoDTO(categoria.getId(), categoria.getNombre(), productoIds);
    }

    public boolean delete(Long id) {
        CategoriaProducto categoria = repo.findById(id).orElse(null);

        if (categoria == null) {
            return false;
        }

        repo.delete(categoria);
        return true;
    }

    public CategoriaProductoDTO findByIdDTO(Long id) {
        CategoriaProducto categoria = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return toDTO(categoria);
    }
}