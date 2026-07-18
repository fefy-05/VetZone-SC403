package com.ufide.vetzone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufide.vetzone.entity.ConsultaMedica;
import com.ufide.vetzone.repository.ConsultaMedicaRepository;

@Service
public class ConsultaMedicaService {

    @Autowired
    private ConsultaMedicaRepository consultaMedicaRepository;

    public List<ConsultaMedica> listar() {
        return consultaMedicaRepository.findAll();
    }

    public Optional<ConsultaMedica> buscarPorId(Long id) {
        return consultaMedicaRepository.findById(id);
    }

    public ConsultaMedica guardar(ConsultaMedica consultaMedica) {
        return consultaMedicaRepository.save(consultaMedica);
    }

    public void eliminar(Long id) {
        consultaMedicaRepository.deleteById(id);
    }
}