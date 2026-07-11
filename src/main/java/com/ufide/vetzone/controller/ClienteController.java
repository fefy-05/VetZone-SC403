package com.ufide.vetzone.controller;

import jakarta.validation.Valid;

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

import com.ufide.vetzone.entity.Cliente;
import com.ufide.vetzone.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listar());
        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult result,
            RedirectAttributes ra) {

        if (result.hasErrors()) {
            return "clientes/form";
        }

        service.guardar(cliente);
        ra.addFlashAttribute("ok", "Cliente guardado correctamente");
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormEditar(@PathVariable Long id, Model model) {
        Cliente cliente = service.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        model.addAttribute("cliente", cliente);
        return "clientes/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
            @Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult result,
            RedirectAttributes ra) {

        if (result.hasErrors()) {
            return "clientes/form";
        }

        cliente.setId(id);
        service.guardar(cliente);
        ra.addFlashAttribute("ok", "Cliente actualizado correctamente");
        return "redirect:/clientes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        service.eliminar(id);
        ra.addFlashAttribute("ok", "Cliente eliminado correctamente");
        return "redirect:/clientes";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", service.buscarPorId(id).orElse(null));
        return "clientes/detalle";
    }
}
