package com.ufide.vetzone.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ufide.vetzone.entity.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    @Query("""
        SELECT COUNT(c) > 0
        FROM Cita c
        WHERE c.veterinario.id = :veterinarioId
          AND c.estado <> 'CANCELADA'
          AND c.fechaHora > :inicio
          AND c.fechaHora < :fin
    """)
    boolean existeChoqueHorario(
            @Param("veterinarioId") Long veterinarioId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );

    @Query("""
        SELECT COUNT(c) > 0
        FROM Cita c
        WHERE c.veterinario.id = :veterinarioId
          AND c.id <> :citaId
          AND c.estado <> 'CANCELADA'
          AND c.fechaHora > :inicio
          AND c.fechaHora < :fin
    """)
    boolean existeChoqueHorarioEditando(
            @Param("veterinarioId") Long veterinarioId,
            @Param("citaId") Long citaId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
}