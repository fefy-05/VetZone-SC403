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
        return repo.findByClienteIdAndActivaTrue(clienteId);
    }

    public Mascota guardar(Mascota mascota) {
        return repo.save(mascota);
    }

    // eliminacion logica, la mascota se conserva para no perder el historial

    public void eliminar(Long id) {
        Mascota mascota = repo.findById(id).orElseThrow();
        mascota.setActiva(false);
        repo.save(mascota);
    }

    // al desactivar un cliente sus mascotas tambien quedan inactivas

    public void desactivarPorCliente(Long clienteId) {
        List<Mascota> mascotas = repo.findByClienteIdAndActivaTrue(clienteId);
        for (Mascota mascota : mascotas) {
            mascota.setActiva(false);
        }
        repo.saveAll(mascotas);
    }

}