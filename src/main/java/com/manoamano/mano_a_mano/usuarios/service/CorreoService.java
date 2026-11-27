package com.manoamano.mano_a_mano.usuarios.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CorreoService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${spring.mail.username}")
    private String remitente;

    public CorreoService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @Async
    public void enviarConfirmacion(String destino, String nombre, String token) {
        SimpleMailMessage m = new SimpleMailMessage();
        m.setFrom(remitente);
        m.setTo(destino);
        m.setSubject("Confirma tu cuenta en Mano a Mano");
        m.setText("Hola " + nombre + ",\n\nActiva tu cuenta con este enlace (vale 24 horas):\n"
                + baseUrl + "/confirmar?token=" + token + "\n\nSi no fuiste tú, ignora este correo.");
        mailSender.send(m);
    }
}