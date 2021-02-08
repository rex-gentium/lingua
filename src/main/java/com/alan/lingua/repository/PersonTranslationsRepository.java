package com.alan.lingua.repository;

import com.alan.lingua.model.PersonTranslations;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PersonTranslationsRepository extends R2dbcRepository<PersonTranslations, Long> {
    Mono<PersonTranslations> findFirstByPersonIdAndTranslationId(Long personId, Long translationId);
    Flux<PersonTranslations> findByPersonId(Long personId);
}
