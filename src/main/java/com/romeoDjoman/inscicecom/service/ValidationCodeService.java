package com.romeoDjoman.inscicecom.service;

import com.romeoDjoman.inscicecom.entity.User;
import com.romeoDjoman.inscicecom.entity.ValidationCode;
import com.romeoDjoman.inscicecom.repository.ValidationCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@AllArgsConstructor
@Service
public class ValidationCodeService {

    private ValidationCodeRepository validationCodeRepository;
    private NotificationService notificationService;

    public void saveValidationCode(User user) {
        ValidationCode validationCode = new ValidationCode();
        validationCode.setUser(user);

        Instant creationDate = Instant.now();
        validationCode.setCreationDate(creationDate);
        Instant expirationDate = creationDate.plus(10, MINUTES);
        validationCode.setExpirationDate(expirationDate);

        // Create the coDde
        String code;
        do {
            Random random = new Random();
            int randomInteger = random.nextInt(999999);
            code = String.format("%06d", randomInteger);
        } while (validationCodeRepository.findByCode(code).isPresent());

        validationCode.setCode(code);
        this.validationCodeRepository.save(validationCode);
        this.notificationService.sendNotification(validationCode);
    }

    public ValidationCode readAccordingCode(String code) {
        return this.validationCodeRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    }

}
