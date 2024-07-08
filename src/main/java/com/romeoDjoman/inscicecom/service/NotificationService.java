package com.romeoDjoman.inscicecom.service;

import com.romeoDjoman.inscicecom.entity.ValidationCode;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationService {

    JavaMailSender javaMailSender;
    public void sendNotification(ValidationCode validationCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@inscic.com");
        mailMessage.setTo(validationCode.getUser().getEmail());
        mailMessage.setSubject(("Votre code d'activation"));

        String text = String.format("Bonjour %s, <br /> Votre code d'activation est %s; A bientôt",
                validationCode.getUser().getFirstName(),
                validationCode.getCode()
        );
        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
    }
}
