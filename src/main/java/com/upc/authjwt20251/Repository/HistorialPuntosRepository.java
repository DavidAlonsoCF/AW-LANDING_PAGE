package com.upc.authjwt20251.Repository;

import com.upc.authjwt20251.Entities.HistorialPuntos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialPuntosRepository extends JpaRepository<HistorialPuntos, Long> {
    List<HistorialPuntos> findByUsuarioId(Long usuarioId);
}