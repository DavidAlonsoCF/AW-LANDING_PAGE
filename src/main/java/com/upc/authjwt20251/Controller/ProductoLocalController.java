package com.upc.authjwt20251.Controller;

import com.upc.authjwt20251.DTO.ProductoLocalDTO;
import com.upc.authjwt20251.Service.ProductoLocalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/productoLocal")
@CrossOrigin
public class ProductoLocalController {

    private final ProductoLocalService productoLocalService;

    public ProductoLocalController(ProductoLocalService productoLocalService) {
        this.productoLocalService = productoLocalService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR', 'TURISTA')")
    public ResponseEntity<List<ProductoLocalDTO>> findAll() {
        return ResponseEntity.ok(productoLocalService.findAll());
    }

    @PostMapping("/inserta")
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR')")
    public ResponseEntity<ProductoLocalDTO> create(@Valid @RequestBody ProductoLocalDTO dto) {
        ProductoLocalDTO saved = productoLocalService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAnyRole('LOCAL', 'ADMINISTRADOR')")
    public ResponseEntity<ProductoLocalDTO> update(@PathVariable Long id, @Valid @RequestBody ProductoLocalDTO dto) {
        ProductoLocalDTO updated = productoLocalService.update(id, dto);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }
}