package com.ufide.vetzone.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping
    public String listarUsuarios(Model model) {

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        model.addAttribute("usuarios", usuarios);

        return "usuarios";
    }

    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {

        model.addAttribute("usuario", new Usuario());

        List<Rol> roles = rolService.listarRoles();

        model.addAttribute("roles", roles);

        return "nuevo_usuario";
    }

    @PostMapping
    public String guardarUsuario(
            @ModelAttribute Usuario usuario,
            @RequestParam Long rolId) {

        usuario.setActivo(true);

        Rol rol = rolService.buscarPorId(rolId);

        usuario.setRol(rol);

        usuarioService.guardarUsuario(usuario);

        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(
            @PathVariable Long id,
            Model model) {

        Usuario usuario = usuarioService.buscarPorId(id);

        List<Rol> roles = rolService.listarRoles();

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", roles);

        return "editar_usuario";
    }

    @PostMapping("/editar/{id}")
    public String actualizarUsuario(
            @PathVariable Long id,
            @ModelAttribute Usuario usuario,
            @RequestParam Long rolId) {

        Usuario usuarioExistente =
                usuarioService.buscarPorId(id);

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellidos(usuario.getApellidos());
        usuarioExistente.setCorreo(usuario.getCorreo());

        Rol rol = rolService.buscarPorId(rolId);

        usuarioExistente.setRol(rol);

        // Solo cambia la contraseña si se escribió una nueva
        if (usuario.getPassword() != null
                && !usuario.getPassword().isBlank()) {

            usuarioExistente.setPassword(
                    usuario.getPassword()
            );
        }

        usuarioService.guardarUsuario(usuarioExistente);

        return "redirect:/usuarios";
    }

    @PostMapping("/estado/{id}")
    public String cambiarEstado(@PathVariable Long id) {

        usuarioService.cambiarEstado(id);

        return "redirect:/usuarios";
    }
}