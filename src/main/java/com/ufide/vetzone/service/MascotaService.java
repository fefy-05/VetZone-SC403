package com.ufide.vetzone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufide.vetzone.entity.Mascota;
import com.ufide.vetzone.repository.MascotaRepository;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository repo;

    public List<Mascota> listar() {
        return repo.findByActivaTrue();
    }

    public Optional<Mascota> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Mascota> buscarPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId);
    }

    public Mascota guardar(Mascota mascota) {
        return repo.save(mascota);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

}