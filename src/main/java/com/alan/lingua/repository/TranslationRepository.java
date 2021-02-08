package com.alan.lingua.repository;

import com.alan.lingua.model.Translation;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TranslationRepository extends R2dbcRepository<Translation, Long> {
    Mono<Translation> findFirstBySourceWordIdAndTargetWordId(Long source, Long target);
}
