package com.ufide.vetzone.controller;

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

import com.ufide.vetzone.entity.Cita;
import com.ufide.vetzone.service.CitaService;
import com.ufide.vetzone.service.MascotaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private MascotaService mascotaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("citas", citaService.listar());
        return "citas/lista";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Cita cita = citaService.buscarPorId(id).orElse(null);
        model.addAttribute("cita", cita);
        return "citas/detalle";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("mascotas", mascotaService.listar());
        return "citas/form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("cita") Cita cita,
                          BindingResult result,
                          Model model,
                          RedirectAttributes ra) {

        if (result.hasErrors()) {
            model.addAttribute("mascotas", mascotaService.listar());
            return "citas/form";
        }

        citaService.guardar(cita);
        ra.addFlashAttribute("ok", "Cita guardada correctamente");
        return "redirect:/citas";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cita cita = citaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        model.addAttribute("cita", cita);
        model.addAttribute("mascotas", mascotaService.listar());
        return "citas/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("cita") Cita cita,
                             BindingResult result,
                             Model model,
                             RedirectAttributes ra) {

        if (result.hasErrors()) {
            model.addAttribute("mascotas", mascotaService.listar());
            return "citas/form";
        }

        cita.setId(id);
        citaService.guardar(cita);
        ra.addFlashAttribute("ok", "Cita actualizada correctamente");
        return "redirect:/citas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        citaService.eliminar(id);
        ra.addFlashAttribute("ok", "Cita eliminada correctamente");
        return "redirect:/citas";
    }
}
