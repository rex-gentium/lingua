package com.alan.lingua.service;

import com.alan.lingua.dto.response.WordDto;
import com.alan.lingua.mapper.WordMapper;
import com.alan.lingua.model.Word;
import com.alan.lingua.repository.PersonRepository;
import com.alan.lingua.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
public class WordService extends PrincipalService {
    private final WordRepository wordRepository;
    private final WordMapper wordMapper;

    @Autowired
    public WordService(PersonRepository personRepository, WordRepository wordRepository, WordMapper wordMapper) {
        super(personRepository);
        this.wordRepository = wordRepository;
        this.wordMapper = wordMapper;
    }

    public Mono<WordDto> createWord(Principal principal, WordDto wordDto) {
        return getPerson(principal)
                .flatMap(person -> {
                    Word word = new Word();
                    word.setText(wordDto.getText());
                    word.setLanguageId(wordDto.getLanguageId());
                    return wordRepository.save(word);
                })
                .map(wordMapper::toDto);
    }

}
