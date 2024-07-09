package com.romeoDjoman.inscicecom;

import com.romeoDjoman.inscicecom.ennum.UserRoleType;
import com.romeoDjoman.inscicecom.entity.User;
import com.romeoDjoman.inscicecom.entity.UserRole;
import com.romeoDjoman.inscicecom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
@EnableScheduling
@SpringBootApplication
public class InscicEcomApplication {
	private BCryptPasswordEncoder passwordEncoder;
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(InscicEcomApplication.class, args);
	}

	public void run(String... args) throws Exception {
		User admin = User.builder()
				.actif(true)
				.firstName("admin")
				.password(passwordEncoder.encode("admin"))
				.email("admin@gmail.com")
				.userRole(
						UserRole.builder()
								.roleName(UserRoleType.ADMINISTRATOR)
								.build()
				)
				.build();
		admin = this.userRepository.findByEmail("admin@gmail.com")
				.orElse(admin);
		this.userRepository.save(admin);

		User publisher = User.builder()
				.actif(true)
				.firstName("publisher")
				.password(passwordEncoder.encode("publisher"))
				.email("publisher@gmail.com")
				.userRole(
						UserRole.builder()
								.roleName(UserRoleType.PUBLISHER)
								.build()
				)
				.build();
		publisher = this.userRepository.findByEmail("publisher@gmail.com")
				.orElse(publisher);
		this.userRepository.save(publisher);
	}

}
