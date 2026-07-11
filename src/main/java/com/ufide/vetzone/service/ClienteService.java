package com.ufide.vetzone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufide.vetzone.entity.Cliente;
import com.ufide.vetzone.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> listar() {
        return repo.findByActivoTrue();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }

    public Cliente guardar(Cliente cliente) {
        return repo.save(cliente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}