package com.alan.lingua.repository;

import com.alan.lingua.model.PersonTranslation;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PersonTranslationsRepository extends R2dbcRepository<PersonTranslation, Long> {
    Mono<PersonTranslation> findFirstByPersonIdAndTranslationId(Long personId, Long translationId);
    Flux<PersonTranslation> findByPersonId(Long personId);
}
