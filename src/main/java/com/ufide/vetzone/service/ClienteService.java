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

    @Autowired
    private MascotaService mascotaService;

    public List<Cliente> listar() {
        return repo.findByActivoTrue();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }

    public boolean existeCedula(String cedula) {
        return repo.existsByCedula(cedula);
    }

    public boolean existeCedulaEnOtroCliente(String cedula, Long id) {
        return repo.existsByCedulaAndIdNot(cedula, id);
    }

    public Cliente guardar(Cliente cliente) {
        return repo.save(cliente);
    }

    // eliminacion logica, el cliente sale del listado pero conserva su historial
    // sus mascotas activas se desactivan para que no se les asignen citas nuevas

    public void eliminar(Long id) {
        Cliente cliente = repo.findById(id).orElseThrow();
        cliente.setActivo(false);
        repo.save(cliente);
        mascotaService.desactivarPorCliente(id);
    }
}