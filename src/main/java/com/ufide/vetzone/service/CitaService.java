package com.ufide.vetzone.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufide.vetzone.entity.Cita;
import com.ufide.vetzone.repository.CitaRepository;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> listar() {
        return citaRepository.findAll();
    }

    public Optional<Cita> buscarPorId(Long id) {
        return citaRepository.findById(id);
    }

    public Cita guardar(Cita cita) {

        if (cita.getVeterinario() == null ||
            cita.getVeterinario().getId() == null) {

            throw new IllegalArgumentException(
                    "Debe seleccionar un veterinario"
            );
        }

        if (cita.getFechaHora() == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar una fecha y hora"
            );
        }

        LocalDateTime inicio =
                cita.getFechaHora().minusMinutes(30);

        LocalDateTime fin =
                cita.getFechaHora().plusMinutes(30);

        boolean horarioOcupado;

        if (cita.getId() == null) {

            horarioOcupado =
                    citaRepository.existeChoqueHorario(
                            cita.getVeterinario().getId(),
                            inicio,
                            fin
                    );

        } else {

            horarioOcupado =
                    citaRepository.existeChoqueHorarioEditando(
                            cita.getVeterinario().getId(),
                            cita.getId(),
                            inicio,
                            fin
                    );
        }

        if (horarioOcupado) {
            throw new IllegalArgumentException(
                    "Debe existir al menos 30 minutos entre las citas del mismo veterinario"
            );
        }

        return citaRepository.save(cita);
    }
    public void eliminar(Long id) {

    Cita cita = citaRepository.findById(id)
            .orElseThrow(() ->
                    new IllegalArgumentException("Cita no encontrada")
            );

    if ("ATENDIDA".equals(cita.getEstado())) {
        throw new IllegalArgumentException(
                "No se puede cancelar una cita que ya fue atendida"
        );
    }

    cita.setEstado("CANCELADA");

    citaRepository.save(cita);
}

} 
