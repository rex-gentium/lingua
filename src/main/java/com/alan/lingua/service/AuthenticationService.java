package com.alan.lingua.service;

import com.alan.lingua.dto.request.UserCredentialsDto;
import com.alan.lingua.dto.response.TokenDto;
import com.alan.lingua.exception.NotAuthenticatedException;
import com.alan.lingua.exception.NotFoundException;
import com.alan.lingua.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Service
public class AuthenticationService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtSigner jwtSigner;

    @Autowired
    public AuthenticationService(PersonRepository personRepository, PasswordEncoder passwordEncoder, JwtSigner jwtSigner) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtSigner = jwtSigner;
    }

    public Mono<TokenDto> authenticate(UserCredentialsDto userCredentialsDto) {
        return personRepository.findFirstByName(userCredentialsDto.getUsername())
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(MessageFormat.format(
                        "User {0} does not exist", userCredentialsDto.getUsername())))))
                .filter(user -> passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPasswordHash()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotAuthenticatedException(MessageFormat.format(
                        "User {0} password is not correct", userCredentialsDto.getUsername())))))
                .map(user -> jwtSigner.createJwt(userCredentialsDto.getUsername()));
    }
}
