package com.tienda.licorera.sevicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 
 * Esta clase se encarga de implementar los servicios del protocolo JavaMailSender
 * 
 * @author Beelz
 */
@Service
public class EmailServImpl {
    @Autowired
    private JavaMailSender mailSender;

    
    /** 
     * Metodo con el que se configura el envio de un email
     * @param toEmail correo al que se va a enviar el mensaje
     * @param subject asunto que contendra el correo enviado
     * @param body cuerpo del mensaje
     * @author Beelz
     */
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("licoreramaxlicors@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mensaje enviado");
    }
}
