package com.romeoDjoman.inscicecom.security;

import com.romeoDjoman.inscicecom.entity.Jwt;
import com.romeoDjoman.inscicecom.entity.User;
import com.romeoDjoman.inscicecom.repository.JwtRepository;
import com.romeoDjoman.inscicecom.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class JwtService {
    public static final String BEARER = "bearer";
    private final String ENCRYPTION_KEY = "59d41e9e806ee793b3a859e4c23d9ab2b5d6c6cbcd292cfc621c89aff4eac560";
    private UserService userService;
    private JwtRepository jwtRepository;

    private static final ZoneId PARIS_ZONE_ID = ZoneId.of("Europe/Paris");

    public Jwt tokenByValue(String value) {
        return this.jwtRepository.findByValueAndDesactiveAndExpire(
                        value,
                        false, false)
                .orElseThrow(() -> new RuntimeException("Token invalide ou inconnu"));
    }

    public Map<String, String> generate(String username) {
        User user = this.userService.loadUserByUsername(username);
        this.disableTokens(user);
        final Map<String, String> jwtMap = new java.util.HashMap<>(this.generateJwt(user));

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

    private void disableTokens(User user) {
        final List<Jwt> jwtList = this.jwtRepository.findUser(user.getEmail()).peek(
                jwt -> {
                    jwt.setDesactive(true);
                    jwt.setExpire(true);
                }
        ).collect(Collectors.toList());

        this.jwtRepository.saveAll(jwtList);
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
        final long currentTime = now.toInstant().toEpochMilli();
        final long expirationTime = currentTime + 24 * 60 * 60 * 1000; // 1 jour

        final Map<String, Object> claims = Map.of(
                "nom", user.getFirstName(),
                Claims.EXPIRATION, Date.from(now.plusHours(1).toInstant()),
                Claims.SUBJECT, user.getEmail()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(now.plusHours(1).toInstant()))
                .setSubject(user.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of(BEARER, bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public void logout() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            Jwt jwt = this.jwtRepository.findUserValidToken(
                    user.getEmail(),
                    false,
                    false
            ).orElseThrow(() -> new RuntimeException("Token invalide"));
            jwt.setExpire(true);
            jwt.setDesactive(true);
            this.jwtRepository.save(jwt);
        } else {
            throw new RuntimeException("Principal is not an instance of User");
        }
    }

//
//    //  @Scheduled(cron = "0 */1 * * * *") chaque minute
//    @Scheduled(cron = "0 0 0 * * *")
//    public void removeUselessJwt() {
//        log.info("Suppression des tokens à {}", Instant.now());
//        this.jwtRepository.deleteAllByExpireAndDesactive(true, true);
//    }
}
