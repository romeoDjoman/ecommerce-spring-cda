package com.romeoDjoman.inscicecom.repository;

import com.romeoDjoman.inscicecom.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
