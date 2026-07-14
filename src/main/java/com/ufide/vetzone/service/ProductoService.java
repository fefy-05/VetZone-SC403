package com.ufide.vetzone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufide.vetzone.entity.Producto;
import com.ufide.vetzone.repository.ProductoRepository;
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    public List<Producto> listar() {
        return repo.findByActivoTrue();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return repo.findByCategoriaId(categoriaId);
    }

    public Producto guardar(Producto producto) {
        return repo.save(producto);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}