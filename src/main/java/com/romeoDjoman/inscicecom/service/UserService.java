package com.romeoDjoman.inscicecom.service;

import com.romeoDjoman.inscicecom.ennum.UserRoleType;
import com.romeoDjoman.inscicecom.entity.User;
import com.romeoDjoman.inscicecom.entity.UserRole;
import com.romeoDjoman.inscicecom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    public void login(User user) {

        if (!user.getEmail().contains("@")) {
            throw new RuntimeException("Votre adresse email doit contenir un '@'.");
        }
        if (!user.getEmail().contains(".")) {
            throw new RuntimeException("Votre adresse email doit contenir un '.'.");
        }

        Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()) {
            throw new RuntimeException("Votre adresse mail est déjà utilisée");
        }
        String passwordCrypted = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordCrypted);

        UserRole userRole = new UserRole();
        userRole.setRoleName(UserRoleType.CUSTOMER);
        user.setUserRole(userRole);

        this.userRepository.save(user);
    }
}
