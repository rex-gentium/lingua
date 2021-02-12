package com.alan.lingua.service;

import com.alan.lingua.dto.response.TranslationDto;
import com.alan.lingua.exception.BadRequestException;
import com.alan.lingua.exception.NotFoundException;
import com.alan.lingua.mapper.TranslationMapper;
import com.alan.lingua.model.*;
import com.alan.lingua.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TranslationService extends PrincipalService {
    private final WordRepository wordRepository;
    private final LanguageRepository languageRepository;
    private final TranslationRepository translationRepository;
    private final TranslationMapper translationMapper;
    private final PersonTranslationsRepository personTranslationsRepository;
    private final TranslationViewRepository translationViewRepository;

    @Autowired
    protected TranslationService(PersonRepository personRepository, WordRepository wordRepository,
                                 LanguageRepository languageRepository, TranslationRepository translationRepository,
                                 TranslationMapper translationMapper,
                                 PersonTranslationsRepository personTranslationsRepository,
                                 TranslationViewRepository translationViewRepository) {
        super(personRepository);
        this.wordRepository = wordRepository;
        this.languageRepository = languageRepository;
        this.translationRepository = translationRepository;
        this.translationMapper = translationMapper;
        this.personTranslationsRepository = personTranslationsRepository;
        this.translationViewRepository = translationViewRepository;
    }

    @Transactional
    public Mono<TranslationDto> createTranslation(Principal principal, Map<String, String> translationDto) {
        if (translationDto.size() != 2) {
            return Mono.error(new BadRequestException("Translation object should contain exactly 2 words"));
        }
        return getPerson(principal)
                .flatMap(p -> getLanguagesByCodes(translationDto.keySet())
                        .map(m -> Tuples.of(p, m)))
                .flatMap(tuple2 -> {
                    Map<String, Language> languages = tuple2.getT2();
                    return Flux.fromIterable(translationDto.entrySet())
                            .flatMap(entry -> {
                                Language language = Optional.ofNullable(languages.get(entry.getKey()))
                                        .orElseThrow(() -> new NotFoundException(
                                                "Language with code {0} not found", entry.getKey()));
                                return createWordIfNotExists(language.getId(), entry.getValue());
                            })
                            .collectList()
                            .map(words -> Tuples.of(tuple2.getT1(), words));
                })
                .flatMap(tuple -> {
                    Person person = tuple.getT1();
                    Word source = tuple.getT2().get(0);
                    Word target = tuple.getT2().get(1);
                    return createTranslationIfNotExists(person, source, target)
                            .flatMap(t -> createTranslationLinkIfNotExists(person, t.getId())
                                    .map(l -> t))
                            .map(t -> translationMapper.toDto(t, source, target, person));
                });
    }

    @Transactional
    public Mono<TranslationDto> createTranslation(Principal principal, Long translationId) {
        return getPerson(principal)
                .flatMap(p -> translationViewRepository.findById(translationId)
                        .map(t -> Tuples.of(p, t)))
                .flatMap(tuple -> createTranslationLinkIfNotExists(tuple.getT1(), tuple.getT2().getId())
                        .map(l -> tuple.getT2()))
                .map(translationMapper::toDto);
    }

    private Mono<PersonTranslations> createTranslationLinkIfNotExists(Person person, Long translationId) {
        return personTranslationsRepository.findFirstByPersonIdAndTranslationId(person.getId(), translationId)
                .switchIfEmpty(Mono.defer(() -> {
                    PersonTranslations link = new PersonTranslations();
                    link.setPersonId(person.getId());
                    link.setTranslationId(translationId);
                    return personTranslationsRepository.save(link);
                }));
    }

    private Mono<Word> createWordIfNotExists(Long languageId, String text) {
        return wordRepository.findFirstByLanguageIdAndText(languageId, text)
                .switchIfEmpty(Mono.defer(() -> saveWord(text, languageId)));
    }

    private Mono<Word> saveWord(String text, Long languageId) {
        Word word = new Word();
        word.setLanguageId(languageId);
        word.setText(text);
        return wordRepository.save(word);
    }

    private Mono<Translation> createTranslationIfNotExists(Person person, Word sourceWord, Word targetWord) {
        return translationRepository.findFirstBySourceWordIdAndTargetWordId(sourceWord.getId(), targetWord.getId())
                .switchIfEmpty(Mono.defer(() -> {
                    Translation translation = new Translation();
                    translation.setSourceWordId(sourceWord.getId());
                    translation.setTargetWordId(targetWord.getId());
                    translation.setAuthorId(person.getId());
                    return translationRepository.save(translation);
                }));
    }

    private Mono<Map<String, Language>> getLanguagesByCodes(Set<String> codes) {
        return languageRepository.findByCodeIn(codes)
                .collect(Collectors.toMap(Language::getCode, Function.identity()));
    }

    public Flux<TranslationDto> getTranslations(Principal principal) {
        return getPerson(principal)
                .flatMapMany(p -> personTranslationsRepository.findByPersonId(p.getId()))
                .map(PersonTranslations::getTranslationId)
                .collectList()
                .flatMapMany(translationViewRepository::findByIdIn)
                .map(translationMapper::toDto);
    }

    public Flux<TranslationDto> getTranslations(Principal principal, String word,
                                                String sourceLanguageCode, String targetLanguageCode) {
        return languageRepository.findFirstByCode(sourceLanguageCode)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(
                        "Language '{0}' not found", sourceLanguageCode))))
                .flatMap(srcLanguage -> wordRepository.findFirstByLanguageIdAndText(srcLanguage.getId(), word))
                .flatMap(srcWord -> languageRepository.findFirstByCode(targetLanguageCode)
                        .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(
                                "Language '{0}' not found", targetLanguageCode))))
                        .map(l -> Tuples.of(srcWord, l)))
                .flatMapMany(tuple -> translationViewRepository.findByWordIdAndTargetLanguageId(tuple.getT1().getId(),
                        tuple.getT2().getId()))
                .map(translationMapper::toDto);
    }
}
