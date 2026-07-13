package com.ufide.vetzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ufide.vetzone.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByCorreo(String correo);

}