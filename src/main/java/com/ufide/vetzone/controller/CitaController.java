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
import com.ufide.vetzone.entity.EstadoCita;
import com.ufide.vetzone.service.CitaService;
import com.ufide.vetzone.service.MascotaService;
import com.ufide.vetzone.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("citas", citaService.listar());
        return "citas/lista";
    }

    @GetMapping("/{id}")
    public String detalle(
            @PathVariable Long id,
            Model model) {

        Cita cita = citaService.buscarPorId(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Cita no encontrada"
                        )
                );

        model.addAttribute("cita", cita);

        return "citas/detalle";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevo(Model model) {

        model.addAttribute("cita", new Cita());

        cargarDatosFormulario(model);

        return "citas/form";
    }

    @PostMapping
    public String guardar(
            @Valid @ModelAttribute("cita") Cita cita,
            BindingResult result,
            Model model,
            RedirectAttributes ra) {

        if (result.hasErrors()) {

            cargarDatosFormulario(model);

            return "citas/form";
        }

        try {

            citaService.guardar(cita);

            ra.addFlashAttribute(
                    "ok",
                    "Cita guardada correctamente"
            );

            return "redirect:/citas";

        } catch (IllegalArgumentException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            cargarDatosFormulario(model);

            return "citas/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(
            @PathVariable Long id,
            Model model,
            RedirectAttributes ra) {

        Cita cita = citaService.buscarPorId(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Cita no encontrada"
                        )
                );

        if (cita.getEstado() == EstadoCita.ATENDIDA) {

            ra.addFlashAttribute(
                    "error",
                    "No se puede editar una cita que ya fue atendida"
            );

            return "redirect:/citas";
        }

        if (cita.getEstado() == EstadoCita.CANCELADA) {

            ra.addFlashAttribute(
                    "error",
                    "No se puede editar una cita cancelada"
            );

            return "redirect:/citas";
        }

        model.addAttribute("cita", cita);

        cargarDatosFormulario(model);

        return "citas/form";
    }

    @PostMapping("/{id}")
    public String actualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("cita") Cita cita,
            BindingResult result,
            Model model,
            RedirectAttributes ra) {

        Cita existente = citaService.buscarPorId(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Cita no encontrada"
                        )
                );

        if (existente.getEstado() == EstadoCita.ATENDIDA) {

            ra.addFlashAttribute(
                    "error",
                    "No se puede modificar una cita que ya fue atendida"
            );

            return "redirect:/citas";
        }

        if (existente.getEstado() == EstadoCita.CANCELADA) {

            ra.addFlashAttribute(
                    "error",
                    "No se puede modificar una cita cancelada"
            );

            return "redirect:/citas";
        }

        if (result.hasErrors()) {

            cargarDatosFormulario(model);

            return "citas/form";
        }

        try {

            cita.setId(id);

            citaService.guardar(cita);

            ra.addFlashAttribute(
                    "ok",
                    "Cita actualizada correctamente"
            );

            return "redirect:/citas";

        } catch (IllegalArgumentException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            cargarDatosFormulario(model);

            return "citas/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes ra) {

        try {

            citaService.eliminar(id);

            ra.addFlashAttribute(
                    "ok",
                    "Cita cancelada correctamente"
            );

        } catch (IllegalArgumentException e) {

            ra.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/citas";
    }

    private void cargarDatosFormulario(Model model) {

        model.addAttribute(
                "mascotas",
                mascotaService.listar()
        );

        model.addAttribute(
                "veterinarios",
                usuarioService.listarVeterinariosActivos()
        );
    }
}