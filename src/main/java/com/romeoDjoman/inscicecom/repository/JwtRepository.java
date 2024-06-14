package com.romeoDjoman.inscicecom.repository;

import com.romeoDjoman.inscicecom.entity.Jwt;
import org.springframework.data.repository.CrudRepository;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {
}
