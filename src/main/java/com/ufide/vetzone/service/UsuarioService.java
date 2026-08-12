package com.ufide.vetzone.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufide.vetzone.entity.Usuario;
import com.ufide.vetzone.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado: " + id)
                );
    }

    public void cambiarEstado(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(!usuario.getActivo());
        usuarioRepository.save(usuario);
    }

    public Usuario guardarUsuario(Usuario usuario) {

        if (usuario.getPassword() != null
                && !usuario.getPassword().isBlank()) {

            usuario.setPassword(
                    passwordEncoder.encode(usuario.getPassword())
            );
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElse(null);
    }

    public List<Usuario> listarVeterinariosActivos() {
        return usuarioRepository.findByActivoTrueAndRolNombre("VETERINARIO");
    }
}