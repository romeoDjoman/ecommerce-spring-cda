package com.romeoDjoman.inscicecom.service;

import com.romeoDjoman.inscicecom.ennum.UserRoleType;
import com.romeoDjoman.inscicecom.entity.User;
import com.romeoDjoman.inscicecom.entity.UserRole;
import com.romeoDjoman.inscicecom.entity.ValidationCode;
import com.romeoDjoman.inscicecom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationCodeService validationCodeService;

    public void register(User user) {
        if (!user.getEmail().contains("@")) {
            throw new RuntimeException("Votre adresse email doit contenir un '@'.");
        }
        if (!user.getEmail().contains(".")) {
            throw new RuntimeException("Votre adresse email doit contenir un '.'.");
        }

        Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new RuntimeException("Votre adresse mail est déjà utilisée");
        }
        String passwordCrypted = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordCrypted);

        UserRole userRole = new UserRole();
        userRole.setRoleName(UserRoleType.CUSTOMER);
        user.setUserRole(userRole);

        user = this.userRepository.save(user);
        this.validationCodeService.saveValidationCode(user);
    }

    public void activationCode(Map<String, String> activationCode) {
        ValidationCode validationCode = this.validationCodeService.readAccordingCode(activationCode.get("code"));
        if (Instant.now().isAfter(validationCode.getExpirationDate())) {
            throw new RuntimeException("Votre code a expiré");
        }
        User userActivate = this.userRepository.findById(validationCode.getUser().getUserId()).orElseThrow(() -> new RuntimeException("utilisateur inconnu"));
        userActivate.setActif(true);
        this.userRepository.save(userActivate);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
    }

    public void modifyPassword(Map<String, String> parameters) {
        User user = this.loadUserByUsername(parameters.get("email"));
        this.validationCodeService.saveValidationCode(user);
    }

    public void newPassword(Map<String, String> parameters) {
        User user = this.loadUserByUsername(parameters.get("email"));
        final ValidationCode validationCode = validationCodeService.readAccordingCode(parameters.get("code"));
        if(validationCode.getUser().getEmail().equals(user.getEmail())) {
            String passwordCrypted = this.passwordEncoder.encode(parameters.get("password"));
            user.setPassword(passwordCrypted);
            this.userRepository.save(user);
        }

    }
}
