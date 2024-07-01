package com.romeoDjoman.inscicecom.repository;

import com.romeoDjoman.inscicecom.entity.Jwt;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {
    Optional<Jwt> findByValue(String value);
}
