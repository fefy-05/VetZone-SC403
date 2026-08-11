package com.ufide.vetzone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufide.vetzone.dto.MascotaResponse;
import com.ufide.vetzone.service.MascotaService;

// @RestController devuelve JSON directo, no busca una vista Thymeleaf
// como el MascotaController de siempre

@RestController
@RequestMapping("/api/mascotas")
public class MascotaRestController {

    @Autowired
    private MascotaService service;

    // GET /api/mascotas -> 200 OK + JSON con las mascotas activas

    @GetMapping
    public List<MascotaResponse> listar() {
        return service.listar().stream()
                .map(MascotaResponse::new)
                .toList();
    }

    // GET /api/mascotas/{id} -> 200 OK con la mascota, o 404 si no existe

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponse> detalle(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(MascotaResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/mascotas/cliente/{clienteId} -> las mascotas activas de un cliente

    @GetMapping("/cliente/{clienteId}")
    public List<MascotaResponse> porCliente(@PathVariable Long clienteId) {
        return service.buscarPorCliente(clienteId).stream()
                .map(MascotaResponse::new)
                .toList();
    }
}