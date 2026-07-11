package com.ufide.vetzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufide.vetzone.entity.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    List<Mascota> findByActivaTrue();

    // -------- Importante --------
    // con esto podemos traer todas las mascotas de un mismo dueno
    // Spring lo arma solo leyendo el nombre del metodo (Cliente - Id)

    List<Mascota> findByClienteId(Long clienteId);

}