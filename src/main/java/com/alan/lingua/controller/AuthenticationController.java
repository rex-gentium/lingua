package com.alan.lingua.controller;

import com.alan.lingua.dto.request.UserCredentialsDto;
import com.alan.lingua.dto.response.TokenDto;
import com.alan.lingua.exception.NotAuthenticatedException;
import com.alan.lingua.service.AuthenticationService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/auth")
public class AuthenticationController extends AbstractController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<TokenDto>> login(@RequestBody UserCredentialsDto userCredentials) {
        return authenticationService.authenticate(userCredentials)
                .map(jwt -> ResponseEntity.ok().body(jwt))
                .onErrorResume(e -> e instanceof NotAuthenticatedException,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
