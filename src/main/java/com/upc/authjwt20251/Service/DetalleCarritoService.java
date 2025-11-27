package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.DetalleCarritoDTO;
import com.upc.authjwt20251.Entities.CarritoCompra;
import com.upc.authjwt20251.Entities.DetalleCarrito;
import com.upc.authjwt20251.Entities.ProductoLocal;
import com.upc.authjwt20251.Repository.CarritoCompraRepository;
import com.upc.authjwt20251.Repository.DetalleCarritoRepository;
import com.upc.authjwt20251.Repository.ProductoLocalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleCarritoService {

    private final DetalleCarritoRepository detalleCarritoRepository;
    private final CarritoCompraRepository carritoCompraRepository;
    private final ProductoLocalRepository productoLocalRepository;

    public DetalleCarritoService(DetalleCarritoRepository detalleCarritoRepository,
                                 CarritoCompraRepository carritoCompraRepository,
                                 ProductoLocalRepository productoLocalRepository) {
        this.detalleCarritoRepository = detalleCarritoRepository;
        this.carritoCompraRepository = carritoCompraRepository;
        this.productoLocalRepository = productoLocalRepository;
    }

    public List<DetalleCarritoDTO> findAllDTO() {
        return detalleCarritoRepository.findAll().stream().map(d ->
                new DetalleCarritoDTO(
                        d.getId(),
                        d.getCarritoCompra().getId(),
                        d.getProductoLocal().getId(),
                        d.getCantidad()
                )
        ).toList();
    }

    public DetalleCarritoDTO create(DetalleCarritoDTO dto) {
        DetalleCarrito d = new DetalleCarrito();
        d.setCantidad(dto.getCantidad());

        CarritoCompra c = carritoCompraRepository.findById(dto.getCarritoCompraId()).orElse(null);
        ProductoLocal p = productoLocalRepository.findById(dto.getProductoLocalId()).orElse(null);

        d.setCarritoCompra(c);
        d.setProductoLocal(p);

        DetalleCarrito saved = detalleCarritoRepository.save(d);
        return new DetalleCarritoDTO(
                saved.getId(),
                saved.getCarritoCompra().getId(),
                saved.getProductoLocal().getId(),
                saved.getCantidad()
        );
    }

    public DetalleCarritoDTO update(Long id, DetalleCarritoDTO dto) {
        DetalleCarrito d = detalleCarritoRepository.findById(id).orElse(null);
        if(d != null){
            d.setCantidad(dto.getCantidad());

            CarritoCompra c = carritoCompraRepository.findById(dto.getCarritoCompraId()).orElse(null);
            ProductoLocal p = productoLocalRepository.findById(dto.getProductoLocalId()).orElse(null);

            d.setCarritoCompra(c);
            d.setProductoLocal(p);

            DetalleCarrito saved = detalleCarritoRepository.save(d);
            return new DetalleCarritoDTO(
                    saved.getId(),
                    saved.getCarritoCompra().getId(),
                    saved.getProductoLocal().getId(),
                    saved.getCantidad()
            );
        }
        return null;
    }

    public boolean delete(Long id) {
        DetalleCarrito d = detalleCarritoRepository.findById(id).orElse(null);

        if (d == null) {
            return false;
        }

        detalleCarritoRepository.delete(d);
        return true;
    }
}