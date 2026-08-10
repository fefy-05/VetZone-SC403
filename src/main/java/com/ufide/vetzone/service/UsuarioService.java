package com.ufide.vetzone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ufide.vetzone.entity.Usuario;
import com.ufide.vetzone.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Listar usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Guardar usuario
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Buscar usuario por correo (login)
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElse(null);
    }

    // Listar solamente veterinarios activos
    public List<Usuario> listarVeterinariosActivos() {
        return usuarioRepository.findByActivoTrueAndRolNombre("VETERINARIO");
    }
}