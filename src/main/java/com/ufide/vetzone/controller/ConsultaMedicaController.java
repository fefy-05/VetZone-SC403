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

        model.addAttribute(
                "consultas",
                consultaMedicaService.listar()
        );

        return "consultas/lista";
    }

    @GetMapping("/{id}")
    public String detalle(
            @PathVariable Long id,
            Model model) {

        ConsultaMedica consulta =
                consultaMedicaService.buscarPorId(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Consulta médica no encontrada"
                                )
                        );

        model.addAttribute(
                "consulta",
                consulta
        );

        return "consultas/detalle";
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {

        model.addAttribute(
                "consulta",
                new ConsultaMedica()
        );

        model.addAttribute(
                "citas",
                citaService.listarCitasAptasParaConsulta()
        );

        return "consultas/form";
    }

    @PostMapping
    public String guardar(
            @Valid @ModelAttribute("consulta")
            ConsultaMedica consulta,
            BindingResult result,
            Model model,
            RedirectAttributes ra) {

        if (result.hasErrors()) {

            cargarCitas(model);

            return "consultas/form";
        }

        try {

            consultaMedicaService.guardar(consulta);

            ra.addFlashAttribute(
                    "ok",
                    "Consulta médica registrada correctamente"
            );

            return "redirect:/consultas";

        } catch (IllegalArgumentException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            cargarCitas(model);

            return "consultas/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(
            @PathVariable Long id,
            Model model) {

        ConsultaMedica consulta =
                consultaMedicaService.buscarPorId(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Consulta médica no encontrada"
                                )
                        );

        model.addAttribute(
                "consulta",
                consulta
        );

        /*
         * Durante la edición mantenemos la cita original.
         * Así una consulta ya atendida no se cambia a otra cita.
         */
        model.addAttribute(
                "citas",
                java.util.List.of(
                        consulta.getCita()
                )
        );

        return "consultas/form";
    }

    @PostMapping("/{id}")
    public String actualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("consulta")
            ConsultaMedica consulta,
            BindingResult result,
            Model model,
            RedirectAttributes ra) {

        consulta.setId(id);

        if (result.hasErrors()) {

            ConsultaMedica anterior =
                    consultaMedicaService.buscarPorId(id)
                            .orElse(null);

            if (anterior != null) {
                model.addAttribute(
                        "citas",
                        java.util.List.of(
                                anterior.getCita()
                        )
                );
            }

            return "consultas/form";
        }

        try {

            consultaMedicaService.guardar(consulta);

            ra.addFlashAttribute(
                    "ok",
                    "Consulta médica actualizada correctamente"
            );

            return "redirect:/consultas";

        } catch (IllegalArgumentException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            ConsultaMedica anterior =
                    consultaMedicaService.buscarPorId(id)
                            .orElse(null);

            if (anterior != null) {
                model.addAttribute(
                        "citas",
                        java.util.List.of(
                                anterior.getCita()
                        )
                );
            }

            return "consultas/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes ra) {

        try {

            consultaMedicaService.eliminar(id);

            ra.addFlashAttribute(
                    "ok",
                    "Consulta médica eliminada correctamente"
            );

        } catch (IllegalArgumentException e) {

            ra.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/consultas";
    }

    private void cargarCitas(Model model) {

        model.addAttribute(
                "citas",
                citaService.listarCitasAptasParaConsulta()
        );
    }
}