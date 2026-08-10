package com.ufide.vetzone.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ufide.vetzone.entity.Rol;
import com.ufide.vetzone.entity.Usuario;
import com.ufide.vetzone.service.RolService;
import com.ufide.vetzone.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {


    private final UsuarioService usuarioService;

    private final RolService rolService;



    public UsuarioController(
            UsuarioService usuarioService,
            RolService rolService) {

        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }



    // Listar usuarios
    @GetMapping
    public String listarUsuarios(Model model) {


        List<Usuario> usuarios = usuarioService.listarUsuarios();

        model.addAttribute("usuarios", usuarios);


        return "usuarios";
    }



    // Formulario nuevo usuario
    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {


        model.addAttribute("usuario", new Usuario());


        List<Rol> roles = rolService.listarRoles();

        model.addAttribute("roles", roles);


        return "nuevo_usuario";
    }



    // Guardar usuario
    @PostMapping
    public String guardarUsuario(
            @ModelAttribute Usuario usuario) {


        usuario.setActivo(true);


        usuarioService.guardarUsuario(usuario);


        return "redirect:/usuarios";
    }

}