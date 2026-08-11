package com.ufide.vetzone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufide.vetzone.dto.CitaRequest;
import com.ufide.vetzone.dto.CitaResponse;
import com.ufide.vetzone.entity.Cita;
import com.ufide.vetzone.entity.EstadoCita;
import com.ufide.vetzone.entity.Mascota;
import com.ufide.vetzone.entity.Usuario;
import com.ufide.vetzone.service.CitaService;
import com.ufide.vetzone.service.MascotaService;
import com.ufide.vetzone.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/citas")
@Validated
public class CitaRestController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody CitaRequest request) {

        try {

            Mascota mascota = mascotaService.buscarPorId(
                    request.getMascotaId()
            ).orElse(null);

            if (mascota == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Mascota no encontrada");
            }

            Usuario veterinario = usuarioService.buscarPorId(
                    request.getVeterinarioId()
            );

            if (veterinario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Veterinario no encontrado");
            }

            if (veterinario.getActivo() == null ||
                !veterinario.getActivo() ||
                veterinario.getRol() == null ||
                !"VETERINARIO".equals(veterinario.getRol().getNombre())) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El usuario seleccionado no es un veterinario activo");
            }

            Cita cita = new Cita();

            cita.setFechaHora(
                    request.getFechaHora()
            );

            cita.setMotivo(
                    request.getMotivo()
            );

            cita.setMascota(
                    mascota
            );

            cita.setVeterinario(
                    veterinario
            );

            cita.setEstado(
                    EstadoCita.PENDIENTE
            );

            Cita guardada =
                    citaService.guardar(cita);

            CitaResponse response =
                    convertirResponse(guardada);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (IllegalArgumentException e) {

            String mensaje = e.getMessage();

            if (mensaje != null &&
                mensaje.contains("30 minutos")) {

                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(mensaje);
            }

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensaje);
        }
    }

    private CitaResponse convertirResponse(Cita cita) {

        CitaResponse response =
                new CitaResponse();

        response.setId(
                cita.getId()
        );

        response.setFechaHora(
                cita.getFechaHora()
        );

        response.setMotivo(
                cita.getMotivo()
        );

        response.setEstado(
                cita.getEstado().name()
        );

        response.setMascotaId(
                cita.getMascota().getId()
        );

        response.setMascotaNombre(
                cita.getMascota().getNombre()
        );

        response.setVeterinarioId(
                cita.getVeterinario().getId()
        );

        response.setVeterinarioNombre(
                cita.getVeterinario().getNombre()
                        + " "
                        + cita.getVeterinario().getApellidos()
        );

        return response;
    }
}