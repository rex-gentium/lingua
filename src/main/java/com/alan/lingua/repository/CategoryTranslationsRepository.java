package com.alan.lingua.repository;

import com.alan.lingua.model.CategoryTranslation;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CategoryTranslationsRepository extends R2dbcRepository<CategoryTranslation, Long> {
    Mono<CategoryTranslation> findFirstByCategoryIdAndTranslationId(Long categoryId, Long translationId);
    Flux<CategoryTranslation> findByCategoryId(Long personId);
}
