package com.ufide.vetzone.controller;

import jakarta.validation.Valid;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ufide.vetzone.entity.Mascota;
import com.ufide.vetzone.service.ClienteService;
import com.ufide.vetzone.service.MascotaService;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService service;

    // -------------- IMPORTANTE ----------
    // necesitamos el ClienteService para llenar el select de duenos en el
    // formulario de mascota

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("mascotas", service.listar());
        return "mascotas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormNuevo(Model model) {
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("clientes", clienteService.listar());
        return "mascotas/form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("mascota") Mascota mascota,
            BindingResult result,
            Model model,
            RedirectAttributes ra) {

        // ------------ IMPORTANTE ---------
        // si hay errores hay que volver a mandar la lista de clientes, si no el select
        // del formulario sale vacio

        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listar());
            return "mascotas/form";
        }

        service.guardar(mascota);
        ra.addFlashAttribute("ok", "Mascota guardada correctamente");
        return "redirect:/mascotas";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormEditar(@PathVariable Long id, Model model) {
        Mascota mascota = service.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        model.addAttribute("mascota", mascota);
        model.addAttribute("clientes", clienteService.listar());
        return "mascotas/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
            @Valid @ModelAttribute("mascota") Mascota mascota,
            BindingResult result,
            Model model,
            RedirectAttributes ra) {

        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listar());
            return "mascotas/form";
        }

        mascota.setId(id);
        service.guardar(mascota);
        ra.addFlashAttribute("ok", "Mascota actualizada correctamente");
        return "redirect:/mascotas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        service.eliminar(id);
        ra.addFlashAttribute("ok", "Mascota desactivada correctamente");
        return "redirect:/mascotas";
    }

@GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Optional<Mascota> mascota = service.buscarPorId(id);

        // si no existe volvemos al listado con un mensaje, en vez de mandar null a la vista

        if (mascota.isEmpty()) {
            ra.addFlashAttribute("error", "La mascota no existe");
            return "redirect:/mascotas";
        }

        model.addAttribute("mascota", mascota.get());
        return "mascotas/detalle";
    }
}