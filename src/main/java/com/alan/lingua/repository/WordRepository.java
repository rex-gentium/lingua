package com.alan.lingua.repository;

import com.alan.lingua.model.Word;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface WordRepository extends R2dbcRepository<Word, Long> {
    Mono<Word> findFirstByLanguageIdAndText(Long languageId, String text);
}
