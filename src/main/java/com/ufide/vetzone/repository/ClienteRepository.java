package com.ufide.vetzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufide.vetzone.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByActivoTrue();

    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    
}