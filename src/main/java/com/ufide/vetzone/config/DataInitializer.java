package com.ufide.vetzone.config;

import com.ufide.vetzone.entity.Rol;
import com.ufide.vetzone.entity.Usuario;
import com.ufide.vetzone.repository.RolRepository;
import com.ufide.vetzone.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Crear roles si no existen
            Rol admin = rolRepository.findAll().stream()
                    .filter(r -> "ADMIN".equalsIgnoreCase(r.getNombre()))
                    .findFirst()
                    .orElseGet(() -> rolRepository.save(new Rol("ADMIN")));

            Rol recepcionista = rolRepository.findAll().stream()
                    .filter(r -> "RECEPCIONISTA".equalsIgnoreCase(r.getNombre()))
                    .findFirst()
                    .orElseGet(() -> rolRepository.save(new Rol("RECEPCIONISTA")));

            Rol veterinario = rolRepository.findAll().stream()
                    .filter(r -> "VETERINARIO".equalsIgnoreCase(r.getNombre()))
                    .findFirst()
                    .orElseGet(() -> rolRepository.save(new Rol("VETERINARIO")));

            // Crear usuario ADMIN si no existe
            if (usuarioRepository.findByCorreo("admin@vetzone.com").isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setNombre("Administrador");
                usuario.setApellidos("VetZone");
                usuario.setCorreo("admin@vetzone.com");
                usuario.setPassword(passwordEncoder.encode("Admin123"));
                usuario.setActivo(true);
                usuario.setRol(admin);

                usuarioRepository.save(usuario);
            }

            // Crear usuario RECEPCIONISTA si no existe
            if (usuarioRepository.findByCorreo("recepcion@vetzone.com").isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setNombre("Recepcionista");
                usuario.setApellidos("VetZone");
                usuario.setCorreo("recepcion@vetzone.com");
                usuario.setPassword(passwordEncoder.encode("Recep123"));
                usuario.setActivo(true);
                usuario.setRol(recepcionista);

                usuarioRepository.save(usuario);
            }

            // Crear usuario VETERINARIO si no existe
            if (usuarioRepository.findByCorreo("veterinario@vetzone.com").isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setNombre("Veterinario");
                usuario.setApellidos("VetZone");
                usuario.setCorreo("veterinario@vetzone.com");
                usuario.setPassword(passwordEncoder.encode("Vet123"));
                usuario.setActivo(true);
                usuario.setRol(veterinario);

                usuarioRepository.save(usuario);
            }

            System.out.println("Usuarios iniciales de VetZone verificados.");
        };
    }
}