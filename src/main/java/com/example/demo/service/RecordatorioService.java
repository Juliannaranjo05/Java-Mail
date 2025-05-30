package com.example.demo.service;

import com.example.demo.model.Tratamiento;
import com.example.demo.repository.TratamientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecordatorioService {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private JavaMailSender mailSender;

    // Se ejecuta cada 10 minutos (ajustable)
    @Scheduled(fixedRate = 600000)
    @Transactional
    public void enviarRecordatorios() {
        List<Tratamiento> tratamientos = tratamientoRepository.findByActivoTrue();

        for (Tratamiento t : tratamientos) {
            LocalDateTime ahora = LocalDateTime.now();

            if (ahora.isAfter(t.getProximaDosis())) {
                // Enviar correo
                enviarCorreo(t.getPaciente().getCorreo(), t.getMedicamento().getNombre());

                // Reducir cantidad del medicamento
                int nuevaCantidad = t.getMedicamento().getCantidad() - 1;
                t.getMedicamento().setCantidad(nuevaCantidad);

                // Desactivar si se acabó el medicamento
                if (nuevaCantidad <= 0) {
                    t.setActivo(false);
                } else {
                    // Programar próxima dosis
                    int horas = t.getMedicamento().getFrecuenciaHoras();
                    t.setProximaDosis(ahora.plusHours(horas));
                }

                tratamientoRepository.save(t);
            }
        }
    }

    public void enviarCorreo(String destinatario, String medicamento) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("¡Es hora de tomar tu medicamento!");
        mensaje.setText("Recuerda tomar tu medicamento: " + medicamento);

        mailSender.send(mensaje);
    }
}