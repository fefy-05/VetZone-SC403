package com.ufide.vetzone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufide.vetzone.entity.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    // Buscar usuario por correo para el login
    Optional<Usuario> findByCorreo(String correo);


}