package com.romeoDjoman.inscicecom.security;

import com.romeoDjoman.inscicecom.entity.User;
import com.romeoDjoman.inscicecom.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Date;
import java.util.Map;

@AllArgsConstructor
@Service
public class JwtService {
    private final String ENCRYPTION_KEY = "59d41e9e806ee793b3a859e4c23d9ab2b5d6c6cbcd292cfc621c89aff4eac560";
    private UserService userService;

    public Map<String, String>  generate(String username) {
        User user = this.userService.loadUserByUsername(username);
        return this.generateJwt(user);
    }

    private Map<String, String> generateJwt(User user) {

        final Map<String, String> claims = Map.of(
                "Nom", user.getFirstName(),
                "Email", user.getEmail()
        );

        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("token", bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }


}
