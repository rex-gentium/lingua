package com.alan.lingua.service;

import com.alan.lingua.dto.response.WordDto;
import com.alan.lingua.exception.AlreadyExistsException;
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
        Long languageId = wordDto.getLanguageId();
        String wordText = wordDto.getText();
        return getPerson(principal)
                .flatMap(person -> wordRepository.findFirstByLanguageIdAndText(languageId, wordText))
                .flatMap(__ -> Mono.error(new AlreadyExistsException(
                        "Word '{0}' already exists in language '{1}'", wordText, languageId)))
                .switchIfEmpty(Mono.defer(() -> saveWord(wordText, languageId)))
                .cast(Word.class)
                .map(wordMapper::toDto);
    }

    private Mono<Word> saveWord(String text, Long languageId) {
        Word word = new Word();
        word.setText(text);
        word.setLanguageId(languageId);
        return wordRepository.save(word);
    }

}
