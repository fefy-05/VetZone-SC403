package com.ufide.vetzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufide.vetzone.entity.ConsultaMedica;

public interface ConsultaMedicaRepository
        extends JpaRepository<ConsultaMedica, Long> {
}