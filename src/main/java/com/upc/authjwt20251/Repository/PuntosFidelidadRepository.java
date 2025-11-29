package com.upc.authjwt20251.Repository;

import com.upc.authjwt20251.Entities.PuntosFidelidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PuntosFidelidadRepository extends JpaRepository<PuntosFidelidad, Long> {

    @Query("SELECT COALESCE(SUM(p.puntosAcumulados), 0) " +
            "FROM PuntosFidelidad p " +
            "WHERE p.usuario.id = :usuarioId")
    Integer sumarPuntosPorUsuario(@Param("usuarioId") Long usuarioId);
}