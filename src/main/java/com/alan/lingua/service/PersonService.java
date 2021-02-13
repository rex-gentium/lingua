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
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
public class PersonService extends PrincipalService {

    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonMapper personMapper, PasswordEncoder passwordEncoder) {
        super(personRepository);
        this.personMapper = personMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Mono<PersonDto> createPerson(CreatePersonDto userDto) {
        return personRepository.findFirstByName(userDto.getName())
                .flatMap(__ -> Mono.error(new AlreadyExistsException(
                        "Person with name '{0}' already exists", userDto.getName())))
                .switchIfEmpty(Mono.defer(() -> savePerson(userDto.getName(), userDto.getPassword())))
                .cast(Person.class)
                .map(personMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Mono<PersonDto> getSelf(Principal principal) {
        return getPerson(principal)
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
