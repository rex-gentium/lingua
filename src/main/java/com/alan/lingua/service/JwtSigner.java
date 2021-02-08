package com.alan.lingua.service;

import com.alan.lingua.dto.response.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.time.*;
import java.util.Date;

@Service
public class JwtSigner {
    private final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    public TokenDto createJwt(String userId) {
        Instant now = Instant.now();
        Instant expirationInstant = now.plus(Duration.ofMinutes(30));
        String token = Jwts.builder()
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .setSubject(userId)
                .setIssuer("identity")
                .setExpiration(Date.from(expirationInstant))
                .setIssuedAt(Date.from(now))
                .compact();
        return new TokenDto(token, LocalDateTime.ofInstant(expirationInstant, ZoneOffset.UTC));
    }

    /**
     * Проверить JWT там, где он будет выбрасывать исключения, не являясь допустимым.
     */
    public Jws<Claims> validateJwt(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(jwt);
    }
}
