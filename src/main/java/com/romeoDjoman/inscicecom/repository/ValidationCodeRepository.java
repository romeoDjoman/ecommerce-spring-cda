package com.romeoDjoman.inscicecom.repository;

import com.romeoDjoman.inscicecom.entity.ValidationCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ValidationCodeRepository extends CrudRepository<ValidationCode, Integer> {

    Optional<ValidationCode> findByCode(String code);
}
