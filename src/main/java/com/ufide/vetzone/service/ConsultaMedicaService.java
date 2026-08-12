package com.ufide.vetzone.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufide.vetzone.entity.Cita;
import com.ufide.vetzone.entity.ConsultaMedica;
import com.ufide.vetzone.entity.EstadoCita;
import com.ufide.vetzone.repository.CitaRepository;
import com.ufide.vetzone.repository.ConsultaMedicaRepository;

@Service
public class ConsultaMedicaService {

    @Autowired
    private ConsultaMedicaRepository consultaMedicaRepository;

    @Autowired
    private CitaRepository citaRepository;

    public List<ConsultaMedica> listar() {
        return consultaMedicaRepository.findAll();
    }

    public Optional<ConsultaMedica> buscarPorId(Long id) {
        return consultaMedicaRepository.findById(id);
    }

    public ConsultaMedica guardar(ConsultaMedica consultaMedica) {

        if (consultaMedica.getCita() == null ||
            consultaMedica.getCita().getId() == null) {

            throw new IllegalArgumentException(
                    "Debe seleccionar una cita"
            );
        }

        Cita cita = citaRepository.findById(
                consultaMedica.getCita().getId()
        ).orElseThrow(() ->
                new IllegalArgumentException(
                        "La cita seleccionada no existe"
                )
        );

        /*
         * NUEVA CONSULTA
         */
        if (consultaMedica.getId() == null) {

            if (cita.getEstado() != EstadoCita.CONFIRMADA) {
                throw new IllegalArgumentException(
                        "Solo se puede registrar una consulta para una cita confirmada"
                );
            }

            consultaMedica.setFechaRegistro(
                    LocalDateTime.now()
            );

            consultaMedica.setCita(cita);

            ConsultaMedica guardada =
                    consultaMedicaRepository.save(consultaMedica);

            // Al atender la cita se conserva el historial
            cita.setEstado(EstadoCita.ATENDIDA);

            citaRepository.save(cita);

            return guardada;
        }

        /*
         * EDICIÓN
         */
        ConsultaMedica anterior =
                consultaMedicaRepository.findById(
                        consultaMedica.getId()
                ).orElseThrow(() ->
                        new IllegalArgumentException(
                                "Consulta médica no encontrada"
                        )
                );

        // La fecha original no debe cambiar
        consultaMedica.setFechaRegistro(
                anterior.getFechaRegistro()
        );

        // La consulta debe continuar asociada a su cita original
        consultaMedica.setCita(
                anterior.getCita()
        );

        return consultaMedicaRepository.save(
                consultaMedica
        );
    }

    public void eliminar(Long id) {

        if (!consultaMedicaRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Consulta médica no encontrada"
            );
        }

        consultaMedicaRepository.deleteById(id);
    }
} 