package com.ufide.vetzone.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ufide.vetzone.entity.Mascota;

// importante: usamos un DTO para no devolver toda la entidad
// asi la API no expone el Cliente completo ni el campo activa

public class MascotaResponse {

    private Long id;
    private String nombre;
    private String animal;
    private String raza;
    private LocalDate fechaNacimiento;
    private BigDecimal peso;
    private Long clienteId;
    private String dueno;

    public MascotaResponse(Mascota mascota) {
        this.id = mascota.getId();
        this.nombre = mascota.getNombre();
        this.animal = mascota.getAnimal();
        this.raza = mascota.getRaza();
        this.fechaNacimiento = mascota.getFechaNacimiento();
        this.peso = mascota.getPeso();
        this.clienteId = mascota.getCliente().getId();
        this.dueno = mascota.getCliente().getNombreCompleto();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAnimal() {
        return animal;
    }

    public String getRaza() {
        return raza;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getDueno() {
        return dueno;
    }
}