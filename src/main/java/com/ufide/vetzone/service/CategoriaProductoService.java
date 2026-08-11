package com.ufide.vetzone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufide.vetzone.entity.CategoriaProducto;
import com.ufide.vetzone.repository.CategoriaProductoRepository;

@Service
public class CategoriaProductoService {

    @Autowired
    private CategoriaProductoRepository repo;

    public List<CategoriaProducto> listar() {
        return repo.findAll();
    }

    public Optional<CategoriaProducto> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public CategoriaProducto guardar(CategoriaProducto categoria) {
        return repo.save(categoria);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

}
