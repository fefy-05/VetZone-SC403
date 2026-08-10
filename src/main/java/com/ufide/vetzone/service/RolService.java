package com.ufide.vetzone.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.ufide.vetzone.entity.Rol;
import com.ufide.vetzone.repository.RolRepository;


@Service
public class RolService {


    private final RolRepository rolRepository;



    public RolService(RolRepository rolRepository) {

        this.rolRepository = rolRepository;
    }



    // Buscar rol por id
    public Rol buscarPorId(Long id) {

        return rolRepository.findById(id)
                .orElse(null);
    }



    // Listar roles
    public List<Rol> listarRoles() {

        return rolRepository.findAll();
    }

}