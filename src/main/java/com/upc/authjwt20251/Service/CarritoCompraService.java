package com.upc.authjwt20251.Service;

import com.upc.authjwt20251.DTO.CarritoCompraDTO;
import com.upc.authjwt20251.DTO.DetalleCarritoDTO;
import com.upc.authjwt20251.Entities.CarritoCompra;
import com.upc.authjwt20251.Entities.DetalleCarrito;
import com.upc.authjwt20251.Repository.CarritoCompraRepository;
import com.upc.authjwt20251.Repository.ProductoLocalRepository;
import com.upc.authjwt20251.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoCompraService {
    private final CarritoCompraRepository carritoCompraRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoLocalRepository productoLocalRepository;

    public CarritoCompraService(CarritoCompraRepository carritoCompraRepository, UsuarioRepository usuarioRepository, ProductoLocalRepository productoLocalRepository) {
        this.carritoCompraRepository = carritoCompraRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoLocalRepository = productoLocalRepository;
    }

    public List<CarritoCompraDTO> findAllDTO() {
        return carritoCompraRepository.findAll().stream().map(this::toDTO).toList();
    }

    public CarritoCompraDTO create(CarritoCompraDTO dto) {
        CarritoCompra carrito = new CarritoCompra();
        carrito.setUsuario(usuarioRepository.findById(dto.getUsuarioId()).orElse(null));

        List<DetalleCarrito> detalles = dto.getDetalles().stream().map(d -> {
            DetalleCarrito detalle = new DetalleCarrito();
            detalle.setProductoLocal(productoLocalRepository.findById(d.getProductoLocalId()).orElse(null));
            detalle.setCantidad(d.getCantidad());
            detalle.setCarritoCompra(carrito);
            return detalle;
        }).toList();

        carrito.setDetalles(detalles);
        carritoCompraRepository.save(carrito);
        return toDTO(carrito);
    }

    public CarritoCompraDTO update(Long id, CarritoCompraDTO dto) {
        CarritoCompra carrito = carritoCompraRepository.findById(id).orElse(null);
        if (carrito == null) return null;

        carrito.setUsuario(usuarioRepository.findById(dto.getUsuarioId()).orElse(null));

        carrito.getDetalles().clear();

        for (DetalleCarritoDTO d : dto.getDetalles()) {
            DetalleCarrito detalle = new DetalleCarrito();

            if (d.getId() != null) {
                detalle.setId(d.getId());
            }

            detalle.setProductoLocal(
                    productoLocalRepository.findById(d.getProductoLocalId()).orElse(null)
            );
            detalle.setCantidad(d.getCantidad());
            detalle.setCarritoCompra(carrito);

            carrito.getDetalles().add(detalle);
        }

        carritoCompraRepository.save(carrito);
        return toDTO(carrito);
    }


    private CarritoCompraDTO toDTO(CarritoCompra carrito) {
        List<DetalleCarritoDTO> detallesDTO = carrito.getDetalles().stream()
                .map(d -> new DetalleCarritoDTO(
                        d.getId(),
                        carrito.getId(),
                        d.getProductoLocal() != null ? d.getProductoLocal().getId() : null,
                        d.getCantidad()
                )).toList();

        return new CarritoCompraDTO(
                carrito.getId(),
                carrito.getUsuario() != null ? carrito.getUsuario().getId() : null,
                detallesDTO
        );
    }
}