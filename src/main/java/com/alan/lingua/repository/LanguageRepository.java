package com.alan.lingua.repository;

import com.alan.lingua.model.Language;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Repository
public interface LanguageRepository extends R2dbcRepository<Language, Long> {
    Mono<Language> findFirstByCode(String code);
    Flux<Language> findByCodeIn(Collection<String> codes);
}
