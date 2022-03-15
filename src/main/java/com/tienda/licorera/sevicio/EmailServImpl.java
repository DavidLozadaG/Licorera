package com.tienda.licorera.sevicio;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 
 * Esta clase se encarga de implementar los servicios del protocolo
 * JavaMailSender
 * 
 * @author Beelz
 */
@Service
public class EmailServImpl {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Metodo con el que se configura el envio de un email
     * 
     * @param toEmail correo al que se va a enviar el mensaje
     * @param nombres nombres del usuario
     * @param link link remporal para restablecer la contraseña
     * @author Beelz
     */
    public void sendEmail(String toEmail, String nombres, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("licoreramaxlicors@gmail.com", "Maxlicor's Soporte");
        helper.setTo(toEmail);
        String subject = "Recuperar contraseña para tu cuenta Maxlicor's";
        String content = "<p>Hola, \"" + nombres + "!\"</p>"
                + "<p>Has solicitado restablecer tu contraseña.</p>"
                + "<p>Haga clic en el enlace de abajo para cambiar tu contraseña:</p>"
                + "<p><a href=\"" + link + "\">Restablecer contraseña</a></p>"
                + "<br>"
                + "<p>Ignora este correo electrónico si recuerdas tu contraseña, "
                + "o sino has hecho la solicitud.</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
        System.out.println("Mensaje enviado");
    }

}
