package com.ufide.vetzone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ufide.vetzone.entity.LoginRequest;
import com.ufide.vetzone.entity.Usuario;
import com.ufide.vetzone.service.UsuarioService;

@Controller
public class LoginController {


    private final UsuarioService usuarioService;


    public LoginController(UsuarioService usuarioService) {

        this.usuarioService = usuarioService;
    }



    // Mostrar formulario de login
    @GetMapping("/login")
    public String mostrarLogin(Model model) {

        model.addAttribute("loginRequest", new LoginRequest());

        return "login";
    }



    // Validar login
    @PostMapping("/login")
    public String iniciarSesion(
            @ModelAttribute LoginRequest loginRequest,
            Model model) {


        Usuario usuario = usuarioService.buscarPorCorreo(
                loginRequest.getCorreo()
        );


        if (usuario != null &&
            usuario.getPassword().equals(loginRequest.getPassword())) {


            return "redirect:/usuarios";

        }


        model.addAttribute(
                "error",
                "Correo o contraseña incorrectos"
        );


        return "login";
    }

}