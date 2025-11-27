package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.ProductoLocalDTO;
import com.upc.authjwt20251.Entities.ProductoLocal;
import com.upc.authjwt20251.Repository.CategoriaProductoRepository;
import com.upc.authjwt20251.Repository.ProductoLocalRepository;
import com.upc.authjwt20251.Repository.ProveedorServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoLocalService {

    private final ProductoLocalRepository productoLocalRepository;
    private final CategoriaProductoRepository categoriaProductoRepository;
    private final ProveedorServicioRepository proveedorServicioRepository;

    public ProductoLocalService(ProductoLocalRepository productoLocalRepository,
                                CategoriaProductoRepository categoriaProductoRepository,
                                ProveedorServicioRepository proveedorServicioRepository) {
        this.productoLocalRepository = productoLocalRepository;
        this.categoriaProductoRepository = categoriaProductoRepository;
        this.proveedorServicioRepository = proveedorServicioRepository;
    }

    public List<ProductoLocalDTO> findAll() {
        return productoLocalRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductoLocalDTO create(ProductoLocalDTO dto) {
        ProductoLocal producto = new ProductoLocal();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());

        producto.setCategoriaProducto(
                categoriaProductoRepository.findById(dto.getCategoriaProductoId()).orElse(null)
        );
        producto.setProveedor(
                proveedorServicioRepository.findById(dto.getProveedorId()).orElse(null)
        );

        productoLocalRepository.save(producto);
        return toDTO(producto);
    }

    public ProductoLocalDTO update(Long id, ProductoLocalDTO dto) {
        ProductoLocal producto = productoLocalRepository.findById(id).orElse(null);
        if (producto == null) return null;

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoriaProducto(
                categoriaProductoRepository.findById(dto.getCategoriaProductoId()).orElse(null)
        );
        producto.setProveedor(
                proveedorServicioRepository.findById(dto.getProveedorId()).orElse(null)
        );

        productoLocalRepository.save(producto);
        return toDTO(producto);
    }

    private ProductoLocalDTO toDTO(ProductoLocal producto) {
        return new ProductoLocalDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCategoriaProducto() != null ? producto.getCategoriaProducto().getId() : null,
                producto.getProveedor() != null ? producto.getProveedor().getId() : null
        );
    }

    public boolean delete(Long id) {
        ProductoLocal producto = productoLocalRepository.findById(id).orElse(null);

        if (producto == null) {
            return false;
        }

        productoLocalRepository.delete(producto);
        return true;
    }

    public ProductoLocalDTO findByIdDTO(Long id) {
        ProductoLocal producto = productoLocalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto Local no encontrado"));

        return toDTO(producto);
    }
}