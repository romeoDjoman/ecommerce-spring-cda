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

    public void saveValidationCode(User user) {
        ValidationCode validationCode = new ValidationCode();
        validationCode.setUser(user);

        Instant creationDate = Instant.now();
        validationCode.setCreationDate(creationDate);
        Instant expirationDate = creationDate.plus(10, MINUTES).now();
        validationCode.setExpirationDate(expirationDate);

        // Create the code
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);

        validationCode.setCode(code);
        this.validationCodeRepository.save(validationCode);
    }

}
