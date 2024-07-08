package com.romeoDjoman.inscicecom.repository;

import com.romeoDjoman.inscicecom.entity.Jwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {
    Optional<Jwt> findByValueAndDesactiveAndExpire(String value, boolean desactive, boolean expire);
    @Query("FROM Jwt j WHERE j.expire = :expire AND j.desactive = :desactive AND j.user.email = :email")
    Optional<Jwt> findUserValidToken(String email, boolean desactive, boolean expire);

    @Query("FROM Jwt j WHERE j.user.email = :email")
    Stream<Jwt> findUser(String email);

    void deleteAllByExpireAndDesactive(boolean expire, boolean desactive);


}
