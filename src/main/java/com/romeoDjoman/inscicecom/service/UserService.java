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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationCodeService validationCodeService;


    public void register(User user) {
        validateEmail(user.getEmail());

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new RuntimeException("Votre adresse mail est déjà utilisée");
        }

        String passwordCrypted = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordCrypted);

        UserRole userRole = new UserRole();
        userRole.setRoleName(UserRoleType.CUSTOMER); // Set default role to USER
        user.setUserRole(userRole);
        user.setContributorApproved(false); // Initialisation explicite
        user.setPublisherApproved(false);

        user = userRepository.save(user);
        validationCodeService.saveValidationCode(user);
    }

    public void requestRoleUpgrade(String email, UserRoleType requestedRole) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (requestedRole == UserRoleType.PUBLISHER) {
            user.setPublisherRequested(true);
        } else if (requestedRole == UserRoleType.CONTRIBUTOR) {
            user.setContributorRequested(true);
        }

        userRepository.save(user);
    }

    public void approveRoleUpgrade(String email, UserRoleType approvedRole) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (approvedRole == UserRoleType.PUBLISHER) {
            user.setPublisherApproved(true);
            user.setUserRole(new UserRole(0, UserRoleType.PUBLISHER));
        } else if (approvedRole == UserRoleType.CONTRIBUTOR) {
            user.setContributorApproved(true);
            user.setUserRole(new UserRole(0, UserRoleType.CONTRIBUTOR));
        }

        userRepository.save(user);
    }


    private void validateEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Votre adresse email doit contenir un '@' et un '.'.");
        }
    }


    public void activationCode(Map<String, String> activationCode) {
        ValidationCode validationCode = validationCodeService.readAccordingCode(activationCode.get("code"));
        if (Instant.now().isAfter(validationCode.getExpirationDate())) {
            throw new RuntimeException("Votre code a expiré");
        }

        User userActivate = userRepository.findById(validationCode.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("utilisateur inconnu"));
        userActivate.setActif(true);
        userRepository.save(userActivate);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
    }


    public void modifyPassword(Map<String, String> parameters) {
        User user = loadUserByUsername(parameters.get("email"));
        validationCodeService.saveValidationCode(user);
    }


    public void newPassword(Map<String, String> parameters) {
        User user = loadUserByUsername(parameters.get("email"));
        ValidationCode validationCode = validationCodeService.readAccordingCode(parameters.get("code"));

        if (validationCode.getUser().getEmail().equals(user.getEmail())) {
            String passwordCrypted = passwordEncoder.encode(parameters.get("password"));
            user.setPassword(passwordCrypted);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Le code de validation ne correspond pas à l'utilisateur");
        }
    }


    public List<User> listUser() {
        final Iterable<User> userIterable = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for (User user : userIterable) {
            users.add(user);
        }
        return users;
    }
}
