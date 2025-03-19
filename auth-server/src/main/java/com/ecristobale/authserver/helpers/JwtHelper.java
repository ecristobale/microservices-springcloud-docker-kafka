package com.ecristobale.authserver.helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtHelper {

    @Value("${application.jwt.secret}")
    private String jwtSecret;

    public String createToken(String username) {
        final var now = new Date();
        // expirationTime is set to 1 hour
        final var expirationTime = new Date(now.getTime() + (3600 * 1000));
        return Jwts
                .builder()
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(expirationTime)
                    .signWith(this.getSecretKey())
                .compact();
    }

    // Obtain token and sign it and test if it is not expired
    public boolean validateToken(String token) {
        try {
            final var expirationDate = this.getExpirationDate(token);
            return expirationDate.after(new Date());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Jwt invalid");
        }
    }

    private Date getExpirationDate(String token) {
        return this.getClaimsFromToken(token, Claims::getExpiration);
    }

    // 2nd: sign token
    private <T> T getClaimsFromToken(String token, Function<Claims, T> resolver) {
        return resolver.apply(this.signToken(token));
    }

    private Claims signToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(this.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
