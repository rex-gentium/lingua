package com.alan.lingua.service;

import com.alan.lingua.dto.request.CreatePersonDto;
import com.alan.lingua.dto.response.PersonDto;
import com.alan.lingua.mapper.PersonMapper;
import com.alan.lingua.model.Person;
import com.alan.lingua.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonMapper personMapper, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<PersonDto> createPerson(CreatePersonDto userDto) {
        Person person = new Person();
        person.setName(userDto.getName());
        String passwordHash = passwordEncoder.encode(userDto.getPassword());
        person.setPasswordHash(passwordHash);
        return personRepository.save(person)
                .map(personMapper::toDto);
    }
}
