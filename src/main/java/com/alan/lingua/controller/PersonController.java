package com.alan.lingua.controller;

import com.alan.lingua.dto.request.CreatePersonDto;
import com.alan.lingua.dto.response.PersonDto;
import com.alan.lingua.service.PersonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Log4j2
@Controller
@RequestMapping("/api/user")
public class PersonController extends AbstractController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PersonDto>> createPerson(@RequestBody CreatePersonDto userDto) {
        return personService.createPerson(userDto)
                .map(ResponseEntity::ok);
    }
}
