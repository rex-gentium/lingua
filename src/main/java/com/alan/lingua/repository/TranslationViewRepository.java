package com.alan.lingua.repository;

import com.alan.lingua.model.TranslationView;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Repository
public interface TranslationViewRepository extends R2dbcRepository<TranslationView, Long> {
    @Query("select * from translation_view " +
            "where source_word_id = :wordId and target_language_id = :languageId " +
            "or target_word_id = :wordId and source_language_id = :languageId")
    Flux<TranslationView> findByWordIdAndTargetLanguageId(Long wordId, Long languageId);

    Flux<TranslationView> findByIdIn(Collection<Long> ids);
}
