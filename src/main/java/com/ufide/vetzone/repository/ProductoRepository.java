package com.ufide.vetzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufide.vetzone.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByActivoTrue();

    List<Producto> findByCategoriaId(Long categoriaId);

}