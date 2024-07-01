package com.romeoDjoman.inscicecom.service;

import com.romeoDjoman.inscicecom.entity.Jwt;
import com.romeoDjoman.inscicecom.entity.User;
import com.romeoDjoman.inscicecom.repository.JwtRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JwtService {
    public static final String BEARER = "bearer";
    private final String ENCRYPTION_KEY = "59d41e9e806ee793b3a859e4c23d9ab2b5d6c6cbcd292cfc621c89aff4eac560";
    private UserService userService;
    private JwtRepository jwtRepository;

    private static final ZoneId PARIS_ZONE_ID = ZoneId.of("Europe/Paris");

    public Jwt tokenByValue(String value) {
        return this.jwtRepository.findByValue(value)
                .orElseThrow(() -> new RuntimeException("Token inconnu"));
    }

    public Map<String, String> generate(String username) {
        User user = this.userService.loadUserByUsername(username);
        final Map<String, String> jwtMap = this.generateJwt(user);

        final Jwt jwt = Jwt
                .builder()
                .value(jwtMap.get(BEARER))
                .desactive(false)
                .expire(false)
                .user(user)
                .build();
        this.jwtRepository.save(jwt);
        return jwtMap;
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        if (expirationDate == null) {
            System.err.println("Erreur : La date d'expiration est nulle.");
            return true;
        }
        return expirationDate.before(Date.from(ZonedDateTime.now(PARIS_ZONE_ID).toInstant()));
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Map<String, String> generateJwt(User user) {
        final ZonedDateTime now = ZonedDateTime.now(PARIS_ZONE_ID);
        final ZonedDateTime expirationTime = now.plusMinutes(30); // 30 minutes

        final Map<String, Object> claims = Map.of(
                "Nom", user.getFirstName(),
                "Email", user.getEmail(),
                Claims.EXPIRATION, Date.from(expirationTime.toInstant()),
                Claims.SUBJECT, user.getEmail()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expirationTime.toInstant()))
                .setSubject(user.getEmail())
                .addClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of(BEARER, bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }
}
