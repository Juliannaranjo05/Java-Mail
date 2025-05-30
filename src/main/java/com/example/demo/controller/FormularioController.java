package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class FormularioController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    // Mostrar formulario
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("paciente", new Paciente());
        model.addAttribute("medicamentos", medicamentoRepository.findAll());
        return "formulario"; // archivo formulario.html
    }

    // Procesar datos del formulario
    @PostMapping("/formulario")
    public String procesarFormulario(@ModelAttribute Paciente paciente,
                                     @RequestParam Long medicamentoId) {

        // Guardar paciente
        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        // Obtener medicamento
        Medicamento medicamento = medicamentoRepository.findById(medicamentoId).orElseThrow();

        // Crear tratamiento
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setPaciente(pacienteGuardado);
        tratamiento.setMedicamento(medicamento);
        tratamiento.setProximaDosis(LocalDateTime.now()); // la primera dosis sería inmediata

        tratamientoRepository.save(tratamiento);

        return "redirect:/formulario?exito";
    }
}