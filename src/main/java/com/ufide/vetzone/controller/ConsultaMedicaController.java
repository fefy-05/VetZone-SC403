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

import com.ufide.vetzone.entity.ConsultaMedica;
import com.ufide.vetzone.service.CitaService;
import com.ufide.vetzone.service.ConsultaMedicaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/consultas")
public class ConsultaMedicaController {

    @Autowired
    private ConsultaMedicaService consultaMedicaService;

    @Autowired
    private CitaService citaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("consultas", consultaMedicaService.listar());
        return "consultas/lista";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        ConsultaMedica consulta = consultaMedicaService.buscarPorId(id)
                .orElse(null);

        model.addAttribute("consulta", consulta);
        return "consultas/detalle";
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("consulta", new ConsultaMedica());
        model.addAttribute("citas", citaService.listar());
        return "consultas/form";
    }

    @PostMapping
    public String guardar(
            @Valid @ModelAttribute("consulta") ConsultaMedica consulta,
            BindingResult result,
            Model model,
            RedirectAttributes ra) {

        if (result.hasErrors()) {
            model.addAttribute("citas", citaService.listar());
            return "consultas/form";
        }

        consultaMedicaService.guardar(consulta);

        ra.addFlashAttribute(
                "ok",
                "Consulta médica registrada correctamente"
        );

        return "redirect:/consultas";
    }
}