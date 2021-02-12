package com.alan.lingua.service;

import com.alan.lingua.dto.request.CreatePersonDto;
import com.alan.lingua.dto.response.PersonDto;
import com.alan.lingua.exception.AlreadyExistsException;
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
        return personRepository.findFirstByName(userDto.getName())
                .flatMap(__ -> Mono.error(new AlreadyExistsException(
                        "Person with name {0} already exists", userDto.getName())))
                .switchIfEmpty(Mono.defer(() -> savePerson(userDto.getName(), userDto.getPassword())))
                .cast(Person.class)
                .map(personMapper::toDto);
    }

    private Mono<Person> savePerson(String name, String password) {
        Person person = new Person();
        person.setName(name);
        String passwordHash = passwordEncoder.encode(password);
        person.setPasswordHash(passwordHash);
        return personRepository.save(person);
    }
}
