package com.example.demo.controller;

import java.util.List;
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
    @GetMapping("/")
    public String mostrarFormulario(Model model) {
        List<Medicamento> listaDeMedicamentos = medicamentoRepository.findAll();
        model.addAttribute("medicamentos", listaDeMedicamentos);
        model.addAttribute("paciente", new Paciente());
        return "formulario";
    }

    // Procesar datos del formulario
    @PostMapping("/procesar-formulario")  // ← Cambiar también el POST
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

        return "redirect:/?exito";
    }
}