package com.ufide.vetzone.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CitaRequest {

    @NotNull(message = "Debe indicar la fecha y hora")
    private LocalDateTime fechaHora;

    @Size(max = 255, message = "El motivo no puede superar 255 caracteres")
    private String motivo;

    @NotNull(message = "Debe indicar la mascota")
    private Long mascotaId;

    @NotNull(message = "Debe indicar el veterinario")
    private Long veterinarioId;

    public CitaRequest() {
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Long veterinarioId) {
        this.veterinarioId = veterinarioId;
    }
}