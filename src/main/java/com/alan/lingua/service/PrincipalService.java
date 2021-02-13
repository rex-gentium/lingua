package com.alan.lingua.service;

import com.alan.lingua.exception.NotAuthenticatedException;
import com.alan.lingua.exception.NotFoundException;
import com.alan.lingua.model.Person;
import com.alan.lingua.repository.PersonRepository;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Optional;

public abstract class PrincipalService {
    protected final PersonRepository personRepository;

    protected PrincipalService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Mono<Person> getPerson(Principal principal) {
        String username = Optional.ofNullable(principal.getName())
                .orElseThrow(() -> new NotAuthenticatedException("Username not provided in token"));
        return personRepository.findFirstByName(username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(
                        "User with id '{0}' does not exist", username))));
    }
}
