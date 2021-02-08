package com.alan.lingua.security;

import com.alan.lingua.service.JwtSigner;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Log4j2
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtSigner jwtSigner;

    @Autowired
    public JwtAuthenticationManager(JwtSigner jwtSigner) {
        this.jwtSigner = jwtSigner;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .flatMap(this::getCredentialsString)
                .map(credentials -> jwtSigner.validateJwt(credentials))
                .onErrorResume(e -> Mono.empty())
                .map(jws -> new UsernamePasswordAuthenticationToken(
                        jws.getBody().getSubject(),
                        authentication.getCredentials(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
    }

    private Mono<String> getCredentialsString(Authentication authentication) {
        if (authentication.getCredentials() instanceof String) {
            return Mono.just((String) authentication.getCredentials());
        } else if (authentication.getCredentials() instanceof Mono) {
            return (Mono<String>) authentication.getCredentials();
        } else {
            return Mono.empty();
        }
    }
}
